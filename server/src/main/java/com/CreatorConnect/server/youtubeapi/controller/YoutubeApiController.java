package com.CreatorConnect.server.youtubeapi.controller;

import com.CreatorConnect.server.youtubeapi.dto.YoutubeApiDto;
import com.CreatorConnect.server.youtubeapi.service.YoutubeApiService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Positive;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@Validated
public class YoutubeApiController {
    private final YoutubeApiService youtubeService;

    /**
     * <카테고리별 유튜브 인기 급상승 영상 10개 조회>
     * youtubeCategoryId 값에 따른 카테고리 종류
     * 1 = Film & Animation
     * 2 = Autos & Vehicles
     * 3 = 전체 카테고리(entity ID 저장을 위해 임의 추가)
     * 10 = Music
     * 15 = Pets & Animals
     * 17 = Sports
     * 20 = Gaming
     * 22 = people & Blogs
     * 23 = Comedy
     * 24 = Entertainment
     * 25 = News & Politics
     * 26 = Howto & Style
     * 28 = Science & Technology
     * 이외 카테고리는 한국 인기 급상승 영상 api 에서 제공하지 않음
     */
    @GetMapping("/youtubevideos/categories/{youtubeCategoryId}")
    public ResponseEntity getFeedbacksByFeedbackCategory(@PathVariable("youtubeCategoryId") @Positive String youtubeCategoryId) {

        YoutubeApiDto.Multi response = youtubeService.get(youtubeCategoryId);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
