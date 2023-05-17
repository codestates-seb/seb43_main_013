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
    public ResponseEntity postJobBoard(@Valid @RequestBody JobBoardDto.Post post) {
        JobBoard createdJobBoard = jobBoardService.createJobBoard(post);

        return new ResponseEntity<>(mapper.jobBoardToJobBoardPostResposneDto(createdJobBoard), HttpStatus.CREATED);
    }

    // 구인구직 게시판 게시글 수정
    @PatchMapping("/jobboard/{jobBoardId}")
    @Secured("ROLE_USER")
    public ResponseEntity patchJobBoard(@PathVariable("jobBoardId") @Positive Long jobBoardId,
                                        @Valid @RequestBody JobBoardDto.Patch patch) {
        JobBoard updatedJobBoard = jobBoardService.updateJobBoard(patch, jobBoardId);

        return new ResponseEntity<>(mapper.jobBoardToJobBoardPostResposneDto(updatedJobBoard), HttpStatus.OK);
    }
}