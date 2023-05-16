package com.CreatorConnect.server.freeboard.conroller;

import com.CreatorConnect.server.category.service.CategoryService;
import com.CreatorConnect.server.feedbackboard.entity.FeedbackBoard;
import com.CreatorConnect.server.freeboard.dto.FreeBoardDto;
import com.CreatorConnect.server.freeboard.entity.FreeBoard;
import com.CreatorConnect.server.freeboard.mapper.FreeBoardMapper;
import com.CreatorConnect.server.freeboard.service.FreeBoardService;
import com.CreatorConnect.server.member.entity.Member;
import com.CreatorConnect.server.member.like.entity.Like;
import com.CreatorConnect.server.member.like.repository.LikeRepository;
import com.CreatorConnect.server.member.repository.MemberRepository;
import com.CreatorConnect.server.member.service.MemberService;
import com.CreatorConnect.server.tag.dto.TagDto;
import com.CreatorConnect.server.tag.entity.Tag;
import com.CreatorConnect.server.tag.mapper.TagMapper;
import com.CreatorConnect.server.tag.service.TagService;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
@Validated
public class FreeBoardController {
    private final FreeBoardService freeBoardService;
    private final FreeBoardMapper mapper;
    private final CategoryService categoryService;
    private final TagMapper tagMapper;
    private final TagService tagService;
    private final MemberService memberService;
    private final MemberRepository memberRepository;
    private final LikeRepository likeRepository;

    public FreeBoardController(FreeBoardService freeBoardService, FreeBoardMapper mapper, CategoryService categoryService, TagMapper tagMapper, TagService tagService, MemberService memberService, MemberRepository memberRepository, LikeRepository likeRepository) {
        this.freeBoardService = freeBoardService;
        this.mapper = mapper;
        this.categoryService = categoryService;
        this.tagMapper = tagMapper;
        this.tagService = tagService;
        this.memberService = memberService;
        this.memberRepository = memberRepository;
        this.likeRepository = likeRepository;
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
                                                  @RequestParam String sort,
                                                  @Positive @RequestParam int page,
                                                @Positive @RequestParam int size) {
        FreeBoardDto.MultiResponseDto<FreeBoardDto.Response> pageFreeBoard = freeBoardService.getAllFreeBoardsByCategory(categoryId, page, size, sort);
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

    @PostMapping("/freeboard/{freeBoardId}/like")
    public ResponseEntity likeFreeBoard (@PathVariable("freeBoardId") @Positive Long freeBoardId) {

        // 현재 로그인한 사용자 정보 가져오기
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Member currentMember = memberService.findVerifiedMember(authentication.getName());

        FreeBoard foundfreeBoard = freeBoardService.verifyFreeBoard(freeBoardId);

        // 현재 로그인한 사용자가 해당 게시물을 좋아요 했는지 확인
        boolean isAlreadyLiked = currentMember.getLikes().stream()
                .filter(Objects::nonNull) // null인 요소 필터링
                .map(Like::getFreeBoard)
                .filter(Objects::nonNull) // null인 FreeBoard 필터링
                .anyMatch(freeBoard -> freeBoard.getFreeBoardId().equals(freeBoardId));

        if (isAlreadyLiked) {
            return ResponseEntity.badRequest().body("Already liked.");
        }

        Like like = new Like();
        like.setBoardType(Like.BoardType.FREEBOARD);
        like.setMember(currentMember);
        like.setFreeBoard(foundfreeBoard);
        likeRepository.save(like);

        // 현재 사용자의 likes 컬렉션에 좋아요 추가
        currentMember.getLikes().add(like);
        memberRepository.save(currentMember);

        return new ResponseEntity<>(HttpStatus.OK);

    }

    @DeleteMapping("/freeboard/{freeBoardId}/like")
    public ResponseEntity unlikeFreeBoard (@PathVariable("freeBoardId") @Positive Long freeBoardId) {

        // 현재 로그인한 사용자 정보 가져오기
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Member currentMember = memberService.findVerifiedMember(authentication.getName());

        FreeBoard freeBoard = freeBoardService.verifyFreeBoard(freeBoardId);

        Optional<Set<Like>> likes = Optional.ofNullable(currentMember.getLikes());

        Set<Like> foundLikes = likes.orElse(Collections.emptySet())
                .stream()
                .filter(l -> l != null && l.getFreeBoard() != null && l.getFreeBoard().getFreeBoardId().equals(freeBoard.getFreeBoardId()))
                .collect(Collectors.toSet());

        if (foundLikes.isEmpty()) {
            return ResponseEntity.badRequest().body("Not liked.");
        }

        // 현재 사용자의 likes 컬렉션에서 좋아요 삭제
        for (Like foundLike : foundLikes) {
            currentMember.getLikes().remove(foundLike);
            likeRepository.delete(foundLike);
        }

        memberRepository.save(currentMember);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);

    }

}
