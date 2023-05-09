package com.CreatorConnect.server.feedbackboard.controller;

import com.CreatorConnect.server.feedbackboard.dto.FeedbackBoardDto;
import com.CreatorConnect.server.feedbackboard.dto.FeedbackBoardMultiDto;
import com.CreatorConnect.server.feedbackboard.dto.FeedbackBoardResponseDto;
import com.CreatorConnect.server.feedbackboard.service.FeedbackBoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Positive;

@RestController
@RequestMapping("/api/feedbackboard")
@RequiredArgsConstructor
@Validated
public class FeedbackBoardController {
    private final FeedbackBoardService feedbackBoardService;

    @PostMapping("/new")
    public ResponseEntity<FeedbackBoardResponseDto.Post> postFeedback(@Valid @RequestBody FeedbackBoardDto.Post postDto) {
        FeedbackBoardResponseDto.Post response = feedbackBoardService.createFeedback(postDto);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PatchMapping("/{feedbackBoardId}")
    public ResponseEntity<FeedbackBoardResponseDto.Patch> patchFeedback(@PathVariable("feedbackBoardId") Long feedbackBoardId,
                                                                        @Valid @RequestBody FeedbackBoardDto.Patch patchDto){
        FeedbackBoardResponseDto.Patch response = feedbackBoardService.updateFeedback(feedbackBoardId, patchDto);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/{feedbackBoardId}")
    public ResponseEntity<FeedbackBoardResponseDto.Details> getFeedback(@PathVariable("feedbackBoardId") @Positive Long feedbackBoardId){
        FeedbackBoardResponseDto.Details response = feedbackBoardService.responseFeedback(feedbackBoardId);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    @GetMapping("s")
    public ResponseEntity getFeedbacks(@RequestParam("page") @Positive int page,
                                       @RequestParam("size") @Positive int size) {
        FeedbackBoardMultiDto.Response response = feedbackBoardService.responseFeedbacks(page, size);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
      //Todo 피드백 카테고리 아이디로 피드백 목록 조회하는 기능
//    @GetMapping("s/category/{feedbackCategory-id}")
//    public ResponseEntity getFeedbacksByCategory(@PathVariable("feedbackCategory-id") @Positive Long feedbackCategoryId,
//                                                 @RequestParam("page") @Positive int page,
//                                                 @RequestParam("size") @Positive int size) {
//        FeedbackBoardMultiDto.Response response = feedbackBoardService.responseFeedbacksByCategory(feedbackCategoryId, page, size);
//        return new ResponseEntity<>(response, HttpStatus.OK);
//    }
      @DeleteMapping("/{feedbackBoardId}")
      public ResponseEntity<Void> deleteFeedback(@PathVariable("feedbackBoardId") @Positive Long feedbackBoardId) {
//          questionService.deleteQuestion(questionId);
          feedbackBoardService.deleteFeedback(feedbackBoardId);
          return new ResponseEntity<>(HttpStatus.NO_CONTENT);
      }
}
