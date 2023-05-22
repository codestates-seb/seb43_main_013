package com.CreatorConnect.server.board.search.controller;

import com.CreatorConnect.server.board.search.dto.SearchBoardResponseDto;
import com.CreatorConnect.server.board.search.dto.SearchMemberResponseDto;
import com.CreatorConnect.server.board.search.dto.SearchResponseDto;
import com.CreatorConnect.server.board.search.entity.PopularSearch;
import com.CreatorConnect.server.board.search.service.SearchService;
import com.CreatorConnect.server.member.dto.MemberFollowResponseDto;
import com.CreatorConnect.server.response.MultiResponseDto;
import org.springframework.data.domain.Page;

import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@RestController
//@CrossOrigin(origins = "*", allowedHeaders = "*")
public class SearchController {
    private final SearchService searchService;

    public SearchController(SearchService searchService) {
        this.searchService = searchService;
    }

    // GET /api/search?keyword=example&page=0&size=10
    @GetMapping("/api/search")
    public ResponseEntity search(@RequestParam(required = false) String keyword,
                                 @RequestParam(defaultValue = "0") int page,
                                 @RequestParam(defaultValue = "10") int size) {

        LocalDate searchdate = LocalDate.now();

        Page<SearchResponseDto> pageResponse = searchService.searchPosts(keyword, PageRequest.of(page, size), searchdate);

        return new ResponseEntity(new MultiResponseDto<>(pageResponse.getContent(), pageResponse), HttpStatus.OK);
    }

    @GetMapping("/api/keyword/all") // 전체 인기 검색어
    public ResponseEntity getPopularSearchKeywords(@RequestParam(defaultValue = "1") int page,
                                                   @RequestParam(defaultValue = "10") int size) {

        Page<String> pageResponse = searchService.getPopularSearchKeywords(PageRequest.of(page - 1, size));

        return new ResponseEntity(new MultiResponseDto<>(pageResponse.getContent(), pageResponse), HttpStatus.OK);
    }

    @GetMapping("/api/keyword/daily")
    public ResponseEntity<Page<String>> getDailyPopularSearchKeywords(@RequestParam(defaultValue = "1") int page,
                                                                      @RequestParam(defaultValue = "20") int size) {

        Page<String> dailyKeywords = searchService.getDailyPopularSearchKeywords(PageRequest.of(page - 1, size));

        return new ResponseEntity(new MultiResponseDto<>(dailyKeywords.getContent(), dailyKeywords), HttpStatus.OK);
    }

    @GetMapping("/api/keyword/weekly")
    public ResponseEntity<Page<String>> getWeeklyPopularSearchKeywords(@RequestParam(defaultValue = "1") int page,
                                                                       @RequestParam(defaultValue = "20") int size) {

        Page<String> weeklyKeywords = searchService.getWeeklyPopularSearchKeywords(PageRequest.of(page - 1, size));

        return new ResponseEntity(new MultiResponseDto<>(weeklyKeywords.getContent(), weeklyKeywords), HttpStatus.OK);
    }

    @GetMapping("/api/keyword/monthly")
    public ResponseEntity<Page<String>> getMonthlyPopularSearchKeywords(@RequestParam(defaultValue = "1") int page,
                                                                        @RequestParam(defaultValue = "20") int size) {

        Page<String> monthlyKeywords = searchService.getMonthlyPopularSearchKeywords(PageRequest.of(page - 1, size));

        return new ResponseEntity(new MultiResponseDto<>(monthlyKeywords.getContent(), monthlyKeywords), HttpStatus.OK);
    }

}
