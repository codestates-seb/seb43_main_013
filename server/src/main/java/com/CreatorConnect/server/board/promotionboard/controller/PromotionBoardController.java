package com.CreatorConnect.server.board.promotionboard.controller;

import com.CreatorConnect.server.board.promotionboard.dto.PromotionBoardDto;
import com.CreatorConnect.server.board.promotionboard.dto.PromotionBoardResponseDto;
import com.CreatorConnect.server.board.promotionboard.service.PromotionBoardService;
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
public class PromotionBoardController {
    private final PromotionBoardService promotionBoardService;
    //등록
    @PostMapping("/promotionboard/new")
    public ResponseEntity<PromotionBoardResponseDto.Post> postPromotion(@Valid @RequestBody PromotionBoardDto.Post postDto,
                                                                        @RequestHeader(value = "Authorization") String outhorizationToken) {

        PromotionBoardResponseDto.Post response = promotionBoardService.createPromotion(postDto);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
    //수정
    @PatchMapping("/promotionboard/{promotionBoardId}")
    public ResponseEntity<PromotionBoardResponseDto.Patch> patchPromotion(@PathVariable("promotionBoardId") Long promotionBoardId,
                                                                          @Valid @RequestBody PromotionBoardDto.Patch patchDto,
                                                                          @RequestHeader(value = "Authorization") String authorizationToken){

        PromotionBoardResponseDto.Patch response = promotionBoardService.updatePromotion(promotionBoardId, patchDto);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    // 목록조회
    @GetMapping("/promotionboard/{promotionBoardId}")
    public ResponseEntity<PromotionBoardResponseDto.Details> getPromotion(@PathVariable("promotionBoardId") @Positive Long promotionBoardId){

        PromotionBoardResponseDto.Details response = promotionBoardService.responsePromotion(promotionBoardId);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/promotionboards")
    public ResponseEntity getpromotion(@RequestParam("sort") String sort,
                                       @RequestParam("page") @Positive int page,
                                       @RequestParam("size") @Positive int size) {

        PromotionBoardResponseDto.Multi response = promotionBoardService.responsePromotions(sort, page, size);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/promotionboards/promotioncategories/{categoryId}")
    public ResponseEntity getPromotionByCategory(@PathVariable("categoryId") long categoryId,
                                                 @RequestParam String sort,
                                                 @Positive @RequestParam int page,
                                                 @Positive @RequestParam int size) {

         PromotionBoardResponseDto.Multi response = promotionBoardService.getPromotionByCategory(categoryId, sort, page, size);

         return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/promotionboard/{promotionboard-id}")
    public ResponseEntity deletePromotionBoard (@Positive @PathVariable("promotionboard-id") Long promotionBoardId,
                                          @RequestHeader(value = "Authorization") String authorizationToken) {

        promotionBoardService.deletePromotion(promotionBoardId);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PostMapping("/promotionboard/{promotionboard-id}/like")
    public ResponseEntity likePromotionBoard (@PathVariable("promotionboard-id") @Positive Long promotionBoardId,
                                         @RequestHeader(value = "Authorization") String authorizationToken) {

        promotionBoardService.likePromotionBoard(promotionBoardId);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/promotionboard/{promotionboard-id}/like")
    public ResponseEntity unLikePromotionBoard (@PathVariable("promotionboard-id") @Positive Long promotionBoardId,
                                           @RequestHeader(value = "Authorization") String authorizationToken) {

        promotionBoardService.unlikePromotionBoard(promotionBoardId);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PostMapping("/promotionboard/{promotionboard-id}/bookmark")
    public ResponseEntity bookmarkPromotionBoard (@PathVariable("promotionboard-id") @Positive Long promotionBoardId,
                                                 @RequestHeader(value = "Authorization") String authorizationToken) {

        promotionBoardService.bookmarkPromotionBoard(promotionBoardId);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/promotionboard/{promotionboard-id}/bookmark")
    public ResponseEntity unbookmarkPromotionBoard (@PathVariable("promotionboard-id") @Positive Long promotionBoardId,
                                                   @RequestHeader(value = "Authorization") String authorizationToken) {

        promotionBoardService.unbookmarkpromotionBoard(promotionBoardId);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}