package com.CreatorConnect.server.board.comments.jobcomment.controller;

import com.CreatorConnect.server.board.comments.common.dto.CommentDto;
import com.CreatorConnect.server.board.comments.common.dto.CommentResponseDto;
import com.CreatorConnect.server.board.comments.jobcomment.service.JobCommentServiceImpl;
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
public class JobCommentController {
    private final JobCommentServiceImpl jobCommentService;

    @PostMapping("/jobboard/{jobBoardId}/comment/new")
    public ResponseEntity<CommentResponseDto.Post> postComment(@PathVariable("jobBoardId") @Positive Long jobBoardId,
                                                               @Valid @RequestBody CommentDto.Post postDto,
                                                               @RequestHeader(value = "Authorization") String authorizationToken) {

        CommentResponseDto.Post response = jobCommentService.createComment(jobBoardId, postDto);

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PatchMapping("/jobboard/{jobBoardId}/comment/{commentId}")
    public ResponseEntity<HttpStatus> patchComment(@PathVariable("jobBoardId") @Positive Long jobBoardId,
                                                   @PathVariable("commentId") @Positive Long commentId,
                                                   @Valid @RequestBody CommentDto.Patch patchDto,
                                                   @RequestHeader(value = "Authorization") String authorizationToken){

        jobCommentService.updateComment(jobBoardId, commentId, patchDto);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/jobboard/{jobBoardId}/comment/{commentId}")
    public ResponseEntity<CommentResponseDto.Details> getComment(@PathVariable("jobBoardId") @Positive Long jobBoardId,
                                                                 @PathVariable("commentId") @Positive Long commentId){

        CommentResponseDto.Details response = jobCommentService.responseComment(jobBoardId, commentId);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/jobboard/{jobBoardId}/comments")
    public ResponseEntity getComments(@PathVariable("jobBoardId") @Positive Long jobBoardId,
                                      @RequestParam("page") @Positive int page,
                                      @RequestParam("size") @Positive int size) {

        CommentResponseDto.Multi response = jobCommentService.responseComments(jobBoardId, page, size);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/jobboard/{jobBoardId}/comment/{commentId}")
    public ResponseEntity<HttpStatus> deleteComment(@PathVariable("jobBoardId") @Positive Long jobBoardId,
                                                    @PathVariable("commentId") @Positive Long commentId,
                                                    @RequestHeader(value = "Authorization") String authorizationToken) {

        jobCommentService.deleteComment(jobBoardId, commentId);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
