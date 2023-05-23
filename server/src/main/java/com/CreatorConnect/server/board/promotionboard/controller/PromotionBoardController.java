package com.CreatorConnect.server.board.promotionboard.controller;

import com.CreatorConnect.server.board.promotionboard.dto.PromotionBoardDto;
import com.CreatorConnect.server.board.promotionboard.dto.PromotionBoardResponseDto;
import com.CreatorConnect.server.board.promotionboard.service.PromotionBoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@Validated
public class PromotionBoardController {
    public ResponseEntity<PromotionBoardResponseDto.Post> postPromotion(@Valid @RequestBody PromotionBoardDto.Post postDto,
                                                                        @RequestHeader(value = "Authorization") String outhorizationToken) {
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

}
