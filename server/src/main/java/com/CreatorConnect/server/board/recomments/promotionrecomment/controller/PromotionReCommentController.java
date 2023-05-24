package com.CreatorConnect.server.board.recomments.promotionrecomment.controller;

import com.CreatorConnect.server.board.comments.common.dto.CommentDto;
import com.CreatorConnect.server.board.recomments.common.dto.ReCommentDto;
import com.CreatorConnect.server.board.recomments.common.dto.ReCommentResponseDto;
import com.CreatorConnect.server.board.recomments.promotionrecomment.service.PromotionReCommentServiceImpl;
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
public class PromotionReCommentController {
    private final PromotionReCommentServiceImpl promotionReCommentService;

    @PostMapping("/promotionboard/{promotionBoardId}/comment/{commentId}/recomment/new")
    public ResponseEntity<ReCommentResponseDto.Post> postReComment(@PathVariable("promotionBoardId") @Positive Long promotionBoardId,
                                                                   @PathVariable("commentId") @Positive Long commentId,
                                                                   @Valid @RequestBody ReCommentDto.Post postDto,
                                                                   @RequestHeader(value = "Authorization") String authorizationToken) {

        ReCommentResponseDto.Post response = promotionReCommentService.createReComment(promotionBoardId, commentId, postDto);

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PatchMapping("/promotionboard/{promotionBoardId}/comment/{commentId}/recomment/{reCommentId}")
    public ResponseEntity<HttpStatus> patchComment(@PathVariable("promotionBoardId") @Positive Long promotionBoardId,
                                                   @PathVariable("commentId") @Positive Long commentId,
                                                   @PathVariable("reCommentId") @Positive Long reCommentId,
                                                   @Valid @RequestBody CommentDto.Patch patchDto,
                                                   @RequestHeader(value = "Authorization") String authorizationToken){

        promotionReCommentService.updateReComment(promotionBoardId, commentId, reCommentId, patchDto);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/promotionboard/{promotionBoardId}/comment/{commentId}/recomment/{reCommentId}")
    public ResponseEntity<ReCommentResponseDto.Details> getComment(@PathVariable("promotionBoardId") @Positive Long promotionBoardId,
                                                                   @PathVariable("commentId") @Positive Long commentId,
                                                                   @PathVariable("reCommentId") @Positive Long reCommentId){

        ReCommentResponseDto.Details response = promotionReCommentService.responseReComment(promotionBoardId, commentId, reCommentId);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    @DeleteMapping("/promotionboard/{promotionBoardId}/comment/{commentId}/recomment/{reCommentId}")
    public ResponseEntity<HttpStatus> deleteComment(@PathVariable("promotionBoardId") @Positive Long promotionBoardId,
                                                    @PathVariable("commentId") @Positive Long commentId,
                                                    @PathVariable("reCommentId") @Positive Long reCommentId,
                                                    @RequestHeader(value = "Authorization") String authorizationToken) {

        promotionReCommentService.deleteReComment(promotionBoardId, commentId, reCommentId);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
