package com.CreatorConnect.server.freeboard.conroller;

import com.CreatorConnect.server.category.service.CategoryService;
import com.CreatorConnect.server.freeboard.dto.FreeBoardDto;
import com.CreatorConnect.server.freeboard.entity.FreeBoard;
import com.CreatorConnect.server.freeboard.mapper.FreeBoardMapper;
import com.CreatorConnect.server.freeboard.service.FreeBoardService;
import com.CreatorConnect.server.tag.dto.TagDto;
import com.CreatorConnect.server.tag.entity.Tag;
import com.CreatorConnect.server.tag.mapper.TagMapper;
import com.CreatorConnect.server.tag.service.TagService;
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
    private final TagMapper tagMapper;
    private final TagService tagService;


    public FreeBoardController(FreeBoardService freeBoardService, FreeBoardMapper mapper,
                               CategoryService categoryService, TagMapper tagMapper,
                               TagService tagService) {
        this.freeBoardService = freeBoardService;
        this.mapper = mapper;
        this.categoryService = categoryService;
        this.tagMapper = tagMapper;
        this.tagService = tagService;
    }

    // 자유 게시판 게시글 등록
    @PostMapping("/freeboard/new")
    public ResponseEntity postFreeBoard(@Valid @RequestBody FreeBoardDto.Post post) {
        FreeBoard freeBoardPost = freeBoardService.createFreeBoard(post);
        FreeBoardDto.PostResponse postResponse = mapper.freeBoardToFreeBoardPostResponseDto(freeBoardPost);

        return new ResponseEntity<>(postResponse, HttpStatus.CREATED);
    }

    // 자유 게시판 게시글 수정
    @PatchMapping("/freeboard/{freeboardId}")
    public ResponseEntity patchFreeBoard(@Valid @RequestBody FreeBoardDto.Patch patch,
                                         @PathVariable("freeboardId") long freeBoardId) {

        List<Tag> tags = tagMapper.tagPostDtosToTag(patch.getTags());

        FreeBoard freeBoardPatch = freeBoardService.updateFreeBoard(patch,freeBoardId);
        List<Tag> updatedTag = tagService.updateFreeBoardTag(tags, freeBoardPatch);

        FreeBoardDto.Response response = mapper.freeBoardToFreeBoardResponseDto(freeBoardPatch);
        response.setTags(tagMapper.tagsToTagResponseDto(updatedTag));

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    // 자유 게시판 게시글 목록 조회
    @GetMapping("/freeboards")
    public FreeBoardDto.MultiResponseDto<FreeBoardDto.Response> getFreeBoards(@RequestParam String sort,
                                                                              @Positive @RequestParam int page,
                                                                              @Positive @RequestParam int size) {
        FreeBoardDto.MultiResponseDto<FreeBoardDto.Response> pageFreeBoards = freeBoardService.getAllFreeBoards(page, size, sort);

        return pageFreeBoards;
    }

    // 자유 게시판 카테고리 별 목록 조회
    @GetMapping("/freboards/category/{categoryId}")
    public FreeBoardDto.MultiResponseDto<FreeBoardDto.Response> getFreeBoardsByCategory(@PathVariable("categoryId") long categoryId,
                                                  @Positive @RequestParam int page,
                                                @Positive @RequestParam int size) {
        FreeBoardDto.MultiResponseDto<FreeBoardDto.Response> pageFreeBoard = freeBoardService.getAllFreeBoardsByCategory(categoryId,page - 1, size);
        return pageFreeBoard;
    }

    // 자유 게시판 게시글 상세 조회
    @GetMapping("/freeboard/{freeboardId}")
    public FreeBoardDto.Response getFreeBoardDetail(@Positive @PathVariable("freeboardId") long freeBoardId) {


        return freeBoardService.getFreeBoardDetail(freeBoardId);
    }

    // 자유 게시판 게시글 삭제
    @DeleteMapping("/freeboard/{freeboardId}")
    public ResponseEntity deleteFreeBoard(@Positive @PathVariable("freeboardId") long freeboardId) {
        freeBoardService.removeFreeBoard(freeboardId);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
