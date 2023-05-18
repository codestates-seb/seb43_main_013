package com.CreatorConnect.server.board.jobboard.controller;

import com.CreatorConnect.server.board.jobboard.dto.JobBoardDto;
import com.CreatorConnect.server.board.jobboard.entity.JobBoard;
import com.CreatorConnect.server.board.jobboard.mapper.JobBoardMapper;
import com.CreatorConnect.server.board.jobboard.service.JobBoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Positive;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@Validated
public class JobBoardController {
    private final JobBoardService jobBoardService;
    private final JobBoardMapper mapper;

    // 구인구직 게시판 게시글 등록
    @PostMapping("/jobboard/new")
    @Secured("ROLE_USER")
    public ResponseEntity postJobBoard(@Valid @RequestBody JobBoardDto.Post post,
                                       @RequestHeader("Authorization") String authorizationToken) {
        String token = authorizationToken.substring(7);
        JobBoard createdJobBoard = jobBoardService.createJobBoard(post);

        return new ResponseEntity<>(mapper.jobBoardToJobBoardPostResposneDto(createdJobBoard), HttpStatus.CREATED);
    }

    // 구인구직 게시판 게시글 수정
    @PatchMapping("/jobboard/{jobBoardId}")
    @Secured("ROLE_USER")
    public ResponseEntity patchJobBoard(@PathVariable("jobBoardId") @Positive Long jobBoardId,
                                        @Valid @RequestBody JobBoardDto.Patch patch,
                                        @RequestHeader("Authorization") String authorizationToken) {
        String token = authorizationToken.substring(7);
        JobBoard updatedJobBoard = jobBoardService.updateJobBoard(patch, jobBoardId);

        return new ResponseEntity<>(mapper.jobBoardToJobBoardResponseDto(updatedJobBoard), HttpStatus.OK);
    }

    // 구인구직 게시판 게시글 목록
    @GetMapping("/jobboards")
    public ResponseEntity getJobBoards(@RequestParam String sort,
                                       @RequestParam @Positive int page,
                                       @RequestParam @Positive int size) {
        JobBoardDto.MultiResponseDto<JobBoardDto.Response> pageJobBoards = jobBoardService.getAllJobBoards(page, size, sort);

        return new ResponseEntity<>(pageJobBoards, HttpStatus.OK);
    }

    // 구인구직 게시판 카테고리 별 목록
    @GetMapping("/jobboards/jobcategories/{jobCategoryId}")
    public ResponseEntity getJobBoardsByCategory(@PathVariable("jobCategoryId") @Positive Long categoryId,
                                                 @RequestParam String sort,
                                                 @RequestParam @Positive int page,
                                                 @RequestParam @Positive int size) {
        JobBoardDto.MultiResponseDto<JobBoardDto.Response> pageJobBoards =
                jobBoardService.getAllJobBoardsByCategory(categoryId, page, size, sort);

        return new ResponseEntity<>(pageJobBoards, HttpStatus.OK);
    }

    // 구인구직 게시판 게시글 상세조회
    @GetMapping("/jobboard/{jobBoardId}")
    public ResponseEntity getJobBoardDetail(@PathVariable("jobBoardId") @Positive Long jobBoardId) {
        JobBoard jobBoard = jobBoardService.getJobBoardDetail(jobBoardId);
        JobBoardDto.Response response = mapper.jobBoardToJobBoardResponseDto(jobBoard);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    // 구인구직 게시판 삭제
    @DeleteMapping("/jobboard/{jobBoardId}")
    public ResponseEntity deleteJobBoard(@PathVariable("jobBoardId") @Positive Long jobBoardId,
                                         @RequestHeader("Authorization") String authorizationToken) {
        String token = authorizationToken.substring(7);
        jobBoardService.removeJobBoard(jobBoardId);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
