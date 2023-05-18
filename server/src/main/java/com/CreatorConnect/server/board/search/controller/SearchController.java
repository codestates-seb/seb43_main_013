package com.CreatorConnect.server.board.search.controller;

import com.CreatorConnect.server.board.search.dto.SearchBoardResponseDto;
import com.CreatorConnect.server.board.search.dto.SearchMemberResponseDto;
import com.CreatorConnect.server.board.search.dto.SearchResponseDto;
import com.CreatorConnect.server.board.search.service.SearchService;
import org.springframework.data.domain.Page;

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

    // GET /search?keyword=example&page=0&size=10
    @GetMapping
    public ResponseEntity search (
            @RequestParam(required = false) String keyword,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        Pageable pageable = PageRequest.of(page, size);

        Page<SearchResponseDto> searchResults = searchService.searchPosts(keyword, pageable);

        List<SearchResponseDto> response = searchResults.getContent().stream()
                .map(searchResult -> {
                    if ("MEMBER".equals(searchResult.getBoardType())) {
                        SearchMemberResponseDto memberDto = new SearchMemberResponseDto();
                        memberDto.setBoardType(searchResult.getBoardType());
                        memberDto.setId(searchResult.getId());
                        memberDto.setMemberId(searchResult.getMemberId());
                        memberDto.setEmail(searchResult.getEmail());
                        memberDto.setName(searchResult.getName());
                        memberDto.setNickname(searchResult.getNickname());
                        memberDto.setProfileImageUrl(searchResult.getProfileImageUrl());
                        memberDto.setCreatedAt(searchResult.getCreatedAt());
                        memberDto.setModifiedAt(searchResult.getModifiedAt());
                        return memberDto;
                    } else {
                        SearchBoardResponseDto boardDto = new SearchBoardResponseDto();
                        boardDto.setBoardType(searchResult.getBoardType());
                        boardDto.setId(searchResult.getId());
                        boardDto.setTitle(searchResult.getTitle());
                        boardDto.setContent(searchResult.getContent());
                        boardDto.setCommentCount(searchResult.getCommentCount());
                        boardDto.setLikeCount(searchResult.getLikeCount());
                        boardDto.setViewCount(searchResult.getViewCount());
                        boardDto.setCategoryName(searchResult.getCategoryName());
                        boardDto.setMemberId(searchResult.getMemberId());
                        boardDto.setProfileImageUrl(searchResult.getProfileImageUrl());
                        boardDto.setCreatedAt(searchResult.getCreatedAt());
                        boardDto.setModifiedAt(searchResult.getModifiedAt());
                        return boardDto;
                    }
                })
                .collect(Collectors.toList());

        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
