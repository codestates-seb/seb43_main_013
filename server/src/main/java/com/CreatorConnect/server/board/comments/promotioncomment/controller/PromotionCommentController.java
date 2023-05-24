package com.CreatorConnect.server.board.comments.promotioncomment.controller;

import com.CreatorConnect.server.board.comments.common.dto.CommentDto;
import com.CreatorConnect.server.board.comments.common.dto.CommentResponseDto;
import com.CreatorConnect.server.board.comments.promotioncomment.service.PromotionCommentServiceImpl;
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
public class PromotionCommentController {
    private final PromotionCommentServiceImpl promotionCommentService;

    @PostMapping("/promotionboard/{promotionBoardId}/comment/new")
    public ResponseEntity<CommentResponseDto.Post> postComment(@PathVariable("promotionBoardId") @Positive Long promotionBoardId,
                                                               @Valid @RequestBody CommentDto.Post postDto,
                                                               @RequestHeader(value = "Authorization") String authorizationToken) {

        CommentResponseDto.Post response = promotionCommentService.createComment(promotionBoardId, postDto);

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PatchMapping("/promotionboard/{promotionBoardId}/comment/{commentId}")
    public ResponseEntity<HttpStatus> patchComment(@PathVariable("promotionBoardId") @Positive Long promotionBoardId,
                                                   @PathVariable("commentId") @Positive Long commentId,
                                                   @Valid @RequestBody CommentDto.Patch patchDto,
                                                   @RequestHeader(value = "Authorization") String authorizationToken){

        promotionCommentService.updateComment(promotionBoardId, commentId, patchDto);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/promotionboard/{promotionBoardId}/comment/{commentId}")
    public ResponseEntity<CommentResponseDto.Details> getComment(@PathVariable("promotionBoardId") @Positive Long promotionBoardId,
                                                                 @PathVariable("commentId") @Positive Long commentId){

        CommentResponseDto.Details response = promotionCommentService.responseComment(promotionBoardId, commentId);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/promotionboard/{promotionBoardId}/comments")
    public ResponseEntity getComments(@PathVariable("promotionBoardId") @Positive Long promotionBoardId,
                                      @RequestParam("page") @Positive int page,
                                      @RequestParam("size") @Positive int size) {

        CommentResponseDto.Multi response = promotionCommentService.responseComments(promotionBoardId, page, size);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/promotionboard/{promotionBoardId}/comment/{commentId}")
    public ResponseEntity<HttpStatus> deleteComment(@PathVariable("promotionBoardId") @Positive Long promotionBoardId,
                                                    @PathVariable("commentId") @Positive Long commentId,
                                                    @RequestHeader(value = "Authorization") String authorizationToken) {

        promotionCommentService.deleteComment(promotionBoardId, commentId);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
