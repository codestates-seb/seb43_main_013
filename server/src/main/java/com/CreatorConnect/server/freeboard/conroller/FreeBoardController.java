package com.CreatorConnect.server.freeboard.conroller;

import com.CreatorConnect.server.category.service.CategoryService;
import com.CreatorConnect.server.freeboard.dto.FreeBoardDto;
import com.CreatorConnect.server.freeboard.entity.FreeBoard;
import com.CreatorConnect.server.freeboard.mapper.FreeBoardMapper;
import com.CreatorConnect.server.freeboard.service.FreeBoardService;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import java.util.List;

@RestController
@RequestMapping("/api")
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
    @PostMapping("/freeboard/new")
    public ResponseEntity postFreeBoard(@Valid @RequestBody FreeBoardDto.Post post) {
        FreeBoard freeBoardPost = freeBoardService.createFreeBoard(post);

        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    // 자유 게시판 게시글 수정
    @PatchMapping("/freeboard/{freeboardId}")
    public ResponseEntity patchFreeBoard(@Valid @RequestBody FreeBoardDto.Patch patch,
                                         @PathVariable("freeboardId") long freeboardId) {
//        patch.setFreeBoardId(freeboardId);
        FreeBoard freeBoardPatch = freeBoardService.updateFreeBoard(patch,freeboardId);

        return new ResponseEntity<>(mapper.freeBoardToFreeBoardResponseDto(freeBoardPatch), HttpStatus.OK);
    }

    // 자유 게시판 게시글 목록 조회
    @GetMapping("/freeboards")
    public ResponseEntity getFreeBoards(@Positive @RequestParam int page,
                                        @Positive @RequestParam int size) {
        Page<FreeBoard> pageFreeBoards = freeBoardService.getFreeBoards(page - 1, size);
        List<FreeBoard> freeBoards = pageFreeBoards.getContent();
        return new ResponseEntity<>(
                new FreeBoardDto.MultiResponseDto<>(mapper.freeBoardToFreeBoardResponseDtos(freeBoards),
                        pageFreeBoards), HttpStatus.OK);
    }
}
