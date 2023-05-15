package com.CreatorConnect.server.comment.controller;


import com.CreatorConnect.server.comment.dto.CommentDto;
import com.CreatorConnect.server.comment.dto.CommentResponseDto;
import com.CreatorConnect.server.comment.service.FeedbackCommentService;
import com.CreatorConnect.server.comment.service.FreeCommentService;
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
public class CommentController {
    private final FeedbackCommentService feedbackCommentService;
    private final FreeCommentService freeCommentService;
    @PostMapping("/feedbackboard/{feedbackBoardId}/comment/new")
    public ResponseEntity<CommentResponseDto.Post> postFeedbackComment(@PathVariable("feedbackBoardId") @Positive Long feedbackBoardId,
                                                                       @Valid @RequestBody CommentDto.Post postDto) {
        CommentResponseDto.Post response = feedbackCommentService.createComment(feedbackBoardId, postDto);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

//    @PatchMapping("/feedbackboard/{feedbackBoardId}/comment/{commentId}")
//    public ResponseEntity<CommentResponseDto.Patch> patchFeedbackComment(@PathVariable("feedbackBoardId") @Positive Long feedbackBoardId,
//                                                                               @PathVariable("commentId") @Positive Long commentId,
//                                                                               @Valid @RequestBody CommentDto.Patch patchDto){
//        CommentResponseDto.Patch response = commentService.updateComment(feedbackBoardId, commentId, patchDto);
//        return new ResponseEntity<>(response, HttpStatus.OK);
//    }
//    @GetMapping("/feedbackboard/{feedbackBoardId}")
//    public ResponseEntity<FeedbackBoardResponseDto.Details> getFeedback(@PathVariable("feedbackBoardId") @Positive Long feedbackBoardId){
//        FeedbackBoardResponseDto.Details response = feedbackBoardService.responseFeedback(feedbackBoardId);
//        return new ResponseEntity<>(response, HttpStatus.OK);
//    }
//    @GetMapping("/feedbackboards")
//    public ResponseEntity getFeedbacks(@RequestParam("sort") String sort,
//                                       @RequestParam("page") @Positive int page,
//                                       @RequestParam("size") @Positive int size) {
//        FeedbackBoardResponseDto.Multi response = feedbackBoardService.responseFeedbacks(sort, page, size);
//        return new ResponseEntity<>(response, HttpStatus.OK);
//    }
//    @GetMapping("/feedbackboards/feedbackcategories/{feedbackCategoryId}")
//    public ResponseEntity getFeedbacksByFeedbackCategory(@PathVariable("feedbackCategoryId") @Positive Long feedbackCategoryId,
//                                                         @RequestParam("sort") String sort,
//                                                         @RequestParam("page") @Positive int page,
//                                                         @RequestParam("size") @Positive int size) {
//        FeedbackBoardResponseDto.Multi response = feedbackBoardService.responseFeedbacksByCategory(feedbackCategoryId, sort, page, size);
//        return new ResponseEntity<>(response, HttpStatus.OK);
//    }
//    @DeleteMapping("/feedbackboard/{feedbackBoardId}")
//    public ResponseEntity<HttpStatus> deleteFeedback(@PathVariable("feedbackBoardId") @Positive Long feedbackBoardId) {
//        feedbackBoardService.deleteFeedback(feedbackBoardId);
//        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
//    }

    @PostMapping("/freeboard/{freeBoardId}/comment/new")
    public ResponseEntity<CommentResponseDto.Post> postFreeComment(@PathVariable("freeBoardId") @Positive long freeBoardId,
                                                                   @Valid @RequestBody CommentDto.Post postDto) {
        CommentResponseDto.Post response = freeCommentService.createComment(freeBoardId, postDto);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
}
