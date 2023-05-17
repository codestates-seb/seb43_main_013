package com.CreatorConnect.server.board.recomments.freerecomment.controller;

import com.CreatorConnect.server.board.comments.common.dto.CommentDto;
import com.CreatorConnect.server.board.recomments.common.dto.ReCommentDto;
import com.CreatorConnect.server.board.recomments.common.dto.ReCommentResponseDto;
import com.CreatorConnect.server.board.recomments.freerecomment.service.FreeReCommentServiceImpl;
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
public class FreeReCommentController {
    private final FreeReCommentServiceImpl freeReCommentService;

    @PostMapping("/freeboard/{freeBoardId}/comment/{commentId}/recomment/new")
    public ResponseEntity<ReCommentResponseDto.Post> postReComment(@PathVariable("freeBoardId") @Positive Long freeBoardId,
                                                                   @PathVariable("commentId") @Positive Long commentId,
                                                                   @Valid @RequestBody ReCommentDto.Post postDto) {
        ReCommentResponseDto.Post response = freeReCommentService.createReComment(freeBoardId, commentId, postDto);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PatchMapping("/freeboard/{freeBoardId}/comment/{commentId}/recomment/{reCommentId}")
    public ResponseEntity<HttpStatus> patchComment(@PathVariable("freeBoardId") @Positive Long freeBoardId,
                                                   @PathVariable("commentId") @Positive Long commentId,
                                                   @PathVariable("reCommentId") @Positive Long reCommentId,
                                                   @Valid @RequestBody CommentDto.Patch patchDto,
                                                   @RequestHeader(value = "Authorization") String authorizationToken){

        String token = authorizationToken.substring(7);
        freeReCommentService.updateReComment(token, freeBoardId, commentId, reCommentId, patchDto);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/freeboard/{freeBoardId}/comment/{commentId}/recomment/{reCommentId}")
    public ResponseEntity<ReCommentResponseDto.Details> getComment(@PathVariable("freeBoardId") @Positive Long freeBoardId,
                                                                   @PathVariable("commentId") @Positive Long commentId,
                                                                   @PathVariable("reCommentId") @Positive Long reCommentId){
        ReCommentResponseDto.Details response = freeReCommentService.responseReComment(freeBoardId, commentId, reCommentId);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    @DeleteMapping("/freeboard/{freeBoardId}/comment/{commentId}/recomment/{reCommentId}")
    public ResponseEntity<HttpStatus> deleteComment(@PathVariable("freeBoardId") @Positive Long freeBoardId,
                                                    @PathVariable("commentId") @Positive Long commentId,
                                                    @PathVariable("reCommentId") @Positive Long reCommentId,
                                                    @RequestHeader(value = "Authorization") String authorizationToken) {

        String token = authorizationToken.substring(7);
        freeReCommentService.deleteReComment(token, freeBoardId, commentId, reCommentId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}