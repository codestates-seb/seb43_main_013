package com.CreatorConnect.server.board.freeboard.controller;

import com.CreatorConnect.server.board.freeboard.dto.FreeBoardDto;
import com.CreatorConnect.server.board.freeboard.entity.FreeBoard;
import com.CreatorConnect.server.board.freeboard.mapper.FreeBoardMapper;
import com.CreatorConnect.server.board.freeboard.service.FreeBoardService;
import com.CreatorConnect.server.board.tag.service.FreeBoardTagService;
import com.CreatorConnect.server.board.tag.entity.Tag;
import com.CreatorConnect.server.board.tag.mapper.TagMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import java.util.*;

@RestController
@RequestMapping("/api")
@Validated
@RequiredArgsConstructor
public class FreeBoardController {
    private final FreeBoardMapper mapper;
    private final FreeBoardService freeBoardService;
    private final FreeBoardTagService freeBoardTagService;
    private final TagMapper tagMapper;

    // 자유 게시판 게시글 등록
    @PostMapping("/freeboard/new")
    public ResponseEntity postFreeBoard(@Valid @RequestBody FreeBoardDto.Post post,
                                        @RequestHeader(value = "Authorization") String authorizationToken) {

        FreeBoard freeBoardPost = freeBoardService.createFreeBoard(post);
        FreeBoardDto.PostResponse postResponse = mapper.freeBoardToFreeBoardPostResponseDto(freeBoardPost);

        return new ResponseEntity<>(postResponse, HttpStatus.CREATED);
    }

    // 자유 게시판 게시글 수정
    @PatchMapping("/freeboard/{freeboardId}")
    public ResponseEntity patchFreeBoard(@Valid @RequestBody FreeBoardDto.Patch patch,
                                         @PathVariable("freeboardId") long freeBoardId,
                                         @RequestHeader(value = "Authorization") String authorizationToken) {

        List<Tag> tags = tagMapper.tagPostDtosToTag(patch.getTags());

        FreeBoard freeBoardPatch = freeBoardService.updateFreeBoard(patch,freeBoardId);
        // 태그 업데이트
        List<Tag> updatedTag = freeBoardTagService.updateFreeBoardTag(tags, freeBoardPatch);

        FreeBoardDto.Response response = mapper.freeBoardToFreeBoardResponseDto(freeBoardPatch);
        response.setTags(tagMapper.tagsToTagResponseDto(updatedTag));

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

     // 자유 게시판 게시글 목록 조회
    @GetMapping("/freeboards")
    public ResponseEntity getFreeBoards(@RequestParam String sort,
                                        @Positive @RequestParam int page,
                                        @Positive @RequestParam int size) {

        FreeBoardDto.MultiResponseDto<FreeBoardDto.Response> pageFreeBoards =
                freeBoardService.getAllFreeBoards(page, size, sort);

        return new ResponseEntity<>(pageFreeBoards, HttpStatus.OK);
    }

    // 자유 게시판 카테고리 별 목록 조회
    @GetMapping("/freeboards/categories/{categoryId}")
    public ResponseEntity getFreeBoardsByCategory(@PathVariable("categoryId") long categoryId,
                                                  @RequestParam String sort,
                                                  @Positive @RequestParam int page,
                                                  @Positive @RequestParam int size) {

        FreeBoardDto.MultiResponseDto<FreeBoardDto.Response> pageFreeBoard =
                freeBoardService.getAllFreeBoardsByCategory(categoryId, page, size, sort);

        return new ResponseEntity<>(pageFreeBoard, HttpStatus.OK);
    }

    // 자유 게시판 게시글 상세 조회
    @GetMapping("/freeboard/{freeboardId}")
    public ResponseEntity getFreeBoardDetail(@Positive @PathVariable("freeboardId") long freeBoardId) {

        return new ResponseEntity<>(freeBoardService.getFreeBoardDetail(freeBoardId), HttpStatus.OK);
    }

    // 자유 게시판 게시글 삭제
    @DeleteMapping("/freeboard/{freeboardId}")
    public ResponseEntity deleteFreeBoard(@Positive @PathVariable("freeboardId") long freeboardId,
                                          @RequestHeader(value = "Authorization") String authorizationToken) {

        freeBoardService.removeFreeBoard(freeboardId);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PostMapping("/freeboard/{freeBoardId}/like")
    public ResponseEntity likeFreeBoard (@PathVariable("freeBoardId") @Positive Long freeBoardId,
                                         @RequestHeader(value = "Authorization") String authorizationToken) {

        freeBoardService.likeFreeBoard(freeBoardId);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/freeboard/{freeBoardId}/like")
    public ResponseEntity unlikeFreeBoard (@PathVariable("freeBoardId") @Positive Long freeBoardId,
                                           @RequestHeader(value = "Authorization") String authorizationToken) {

        freeBoardService.unlikeFreeBoard(freeBoardId);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PostMapping("/freeboard/{freeBoardId}/bookmark")
    public ResponseEntity bookmarkFeedbackBoard (@PathVariable("freeBoardId") @Positive Long freeBoardId,
                                                 @RequestHeader(value = "Authorization") String authorizationToken) {

        freeBoardService.bookmarkFreeBoard(freeBoardId);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/freeboard/{freeBoardId}/bookmark")
    public ResponseEntity unbookmarkFeedbackBoard (@PathVariable("freeBoardId") @Positive Long freeBoardId,
                                                   @RequestHeader(value = "Authorization") String authorizationToken) {

        freeBoardService.unbookmarkFreeBoard(freeBoardId);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
