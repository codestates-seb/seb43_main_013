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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/search")
public class SearchController {
    private final SearchService searchService;

    public SearchController(SearchService searchService) {
        this.searchService = searchService;
    }

    // GET /api/search?keyword=example&page=0&size=10
    @GetMapping
    public ResponseEntity search (@RequestParam(required = false) String keyword,
                                  @RequestParam(defaultValue = "0") int page,
                                  @RequestParam(defaultValue = "10") int size) {

        Page<SearchResponseDto> pageResponse = searchService.searchPosts(keyword, PageRequest.of(page, size));

        return new ResponseEntity(new MultiResponseDto<>(pageResponse.getContent(), pageResponse), HttpStatus.OK);
    }

    @GetMapping("/popularkeywords")
    public ResponseEntity getPopularSearchKeywords(@RequestParam(defaultValue = "0") int page,
                                                   @RequestParam(defaultValue = "10") int size) {


        Page<String> pageResponse = searchService.getPopularSearchKeywords(PageRequest.of(page, size));

        return new ResponseEntity(new MultiResponseDto<>(pageResponse.getContent(), pageResponse), HttpStatus.OK);
    }
}
