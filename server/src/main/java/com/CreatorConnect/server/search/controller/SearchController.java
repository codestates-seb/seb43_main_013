package com.CreatorConnect.server.search.controller;

import com.CreatorConnect.server.search.dto.SearchResponseDTO;
import com.CreatorConnect.server.search.service.SearchService;
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

    // GET /search?keyword=example&page=0&size=20&sort=createdAt,desc
    @GetMapping
    public ResponseEntity search (
            @RequestParam(required = false) String keyword,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        Pageable pageable = PageRequest.of(page, size);

        Page<SearchResponseDTO> searchResults = searchService.searchPosts(keyword, pageable);
        List<SearchResponseDTO> response = searchResults.getContent().stream()
                .map(searchResult -> {
                    SearchResponseDTO dto = new SearchResponseDTO();
                    dto.setBoardType(searchResult.getBoardType());
                    dto.setId(searchResult.getId());
                    dto.setTitle(searchResult.getTitle());
                    dto.setContent(searchResult.getContent());
                    return dto;
                })
                .collect(Collectors.toList());

        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
