package com.CreatorConnect.server.freeboard.conroller;

import com.CreatorConnect.server.category.service.CategoryService;
import com.CreatorConnect.server.freeboard.dto.FreeBoardDto;
import com.CreatorConnect.server.freeboard.entity.FreeBoard;
import com.CreatorConnect.server.freeboard.mapper.FreeBoardMapper;
import com.CreatorConnect.server.freeboard.service.FreeBoardService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/freeboard")
@Validated
public class FreeBoardController {
    private final FreeBoardService freeBoardService;
    private final FreeBoardMapper mapper;
    private final CategoryService categoryService;

    public FreeBoardController(FreeBoardService freeBoardService, FreeBoardMapper mapper,
                               CategoryService categoryService) {
        this.freeBoardService = freeBoardService;
        this.mapper = mapper;
        this.categoryService = categoryService;
    }

    // 자유 게시판 게시글 등록
    @PostMapping("/new")
    public ResponseEntity postFreeBoard(@Valid @RequestBody FreeBoardDto.Post post) {
        FreeBoard freeBoardPost = freeBoardService.createFreeBoard(post);

        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
