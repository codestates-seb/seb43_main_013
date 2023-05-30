package com.CreatorConnect.server.board.recomments.jobrecomment.controller;

import com.CreatorConnect.server.board.comments.common.dto.CommentDto;
import com.CreatorConnect.server.board.recomments.common.dto.ReCommentDto;
import com.CreatorConnect.server.board.recomments.common.dto.ReCommentResponseDto;
import com.CreatorConnect.server.board.recomments.jobrecomment.service.JobReCommentServiceImpl;
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
public class JobReCommentController {
    private final JobReCommentServiceImpl jobReCommentService;

    @PostMapping("/jobboard/{jobBoardId}/comment/{commentId}/recomment/new")
    public ResponseEntity<ReCommentResponseDto.Post> postReComment(@PathVariable("jobBoardId") @Positive Long jobBoardId,
                                                                   @PathVariable("commentId") @Positive Long commentId,
                                                                   @Valid @RequestBody ReCommentDto.Post postDto,
                                                                   @RequestHeader(value = "Authorization") String authorizationToken) {

        ReCommentResponseDto.Post response = jobReCommentService.createReComment(jobBoardId, commentId, postDto);

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PatchMapping("/jobboard/{jobBoardId}/comment/{commentId}/recomment/{reCommentId}")
    public ResponseEntity<HttpStatus> patchComment(@PathVariable("jobBoardId") @Positive Long jobBoardId,
                                                   @PathVariable("commentId") @Positive Long commentId,
                                                   @PathVariable("reCommentId") @Positive Long reCommentId,
                                                   @Valid @RequestBody CommentDto.Patch patchDto,
                                                   @RequestHeader(value = "Authorization") String authorizationToken){

        jobReCommentService.updateReComment(jobBoardId, commentId, reCommentId, patchDto);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/jobboard/{jobBoardId}/comment/{commentId}/recomment/{reCommentId}")
    public ResponseEntity<ReCommentResponseDto.Details> getComment(@PathVariable("jobBoardId") @Positive Long jobBoardId,
                                                                   @PathVariable("commentId") @Positive Long commentId,
                                                                   @PathVariable("reCommentId") @Positive Long reCommentId){

        ReCommentResponseDto.Details response = jobReCommentService.responseReComment(jobBoardId, commentId, reCommentId);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    @DeleteMapping("/jobboard/{jobBoardId}/comment/{commentId}/recomment/{reCommentId}")
    public ResponseEntity<HttpStatus> deleteComment(@PathVariable("jobBoardId") @Positive Long jobBoardId,
                                                    @PathVariable("commentId") @Positive Long commentId,
                                                    @PathVariable("reCommentId") @Positive Long reCommentId,
                                                    @RequestHeader(value = "Authorization") String authorizationToken) {

        jobReCommentService.deleteReComment(jobBoardId, commentId, reCommentId);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}