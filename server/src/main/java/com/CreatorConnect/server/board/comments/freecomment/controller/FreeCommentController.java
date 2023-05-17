package com.CreatorConnect.server.board.comments.freecomment.controller;

import com.CreatorConnect.server.board.comments.common.dto.CommentDto;
import com.CreatorConnect.server.board.comments.common.dto.CommentResponseDto;
import com.CreatorConnect.server.board.comments.freecomment.service.FreeCommentServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Positive;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@Validated
public class FreeCommentController {
    private final FreeCommentServiceImpl freeCommentService;

    @PostMapping("/freeboard/{freeBoardId}/comment/new")
    public ResponseEntity<CommentResponseDto.Post> postComment(@PathVariable("freeBoardId") @Positive Long freeBoardId,
                                                                             @Valid @RequestBody CommentDto.Post postDto) {
        CommentResponseDto.Post response = freeCommentService.createComment(freeBoardId, postDto);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PatchMapping("/freeboard/{freeBoardId}/comment/{commentId}")
    public ResponseEntity<HttpStatus> patchComment(@PathVariable("freeBoardId") @Positive Long freeBoardId,
                                                                          @PathVariable("commentId") @Positive Long commentId,
                                                                          @Valid @RequestBody CommentDto.Patch patchDto,
                                                   @RequestHeader(value = "Authorization") String authorizationToken) {
        String token = authorizationToken.substring(7);
        freeCommentService.updateComment(token, freeBoardId, commentId, patchDto);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/freeboard/{freeBoardId}/comment/{commentId}")
    public ResponseEntity<CommentResponseDto.Details> getComment(@PathVariable("freeBoardId") @Positive Long freeBoardId,
                                                                 @PathVariable("commentId") @Positive Long commentId){
        CommentResponseDto.Details response = freeCommentService.responseComment(freeBoardId, commentId);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/freeboard/{freeBoardId}/comments")
    public ResponseEntity getComments(@PathVariable("freeBoardId") @Positive Long freeBoardId,
                                      @RequestParam("page") @Positive int page,
                                      @RequestParam("size") @Positive int size) {
        CommentResponseDto.Multi response = freeCommentService.responseComments(freeBoardId, page, size);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/freeboard/{freeBoardId}/comment/{commentId}")
    public ResponseEntity<HttpStatus> deleteComment(@PathVariable("freeBoardId") @Positive Long freeBoardId,
                                                    @PathVariable("commentId") @Positive Long commentId,
                                                    @RequestHeader(value = "Authorization") String authorizationToken) {

        String token = authorizationToken.substring(7);
        freeCommentService.deleteComment(token, freeBoardId, commentId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
