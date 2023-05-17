package com.CreatorConnect.server.board.freeboard.conroller;

import com.CreatorConnect.server.board.categories.category.service.CategoryService;
import com.CreatorConnect.server.board.freeboard.dto.FreeBoardDto;
import com.CreatorConnect.server.board.freeboard.entity.FreeBoard;
import com.CreatorConnect.server.board.freeboard.mapper.FreeBoardMapper;
import com.CreatorConnect.server.board.freeboard.repository.FreeBoardRepository;
import com.CreatorConnect.server.board.freeboard.service.FreeBoardService;
import com.CreatorConnect.server.board.tag.service.FeedbackBoardTagService;
import com.CreatorConnect.server.board.tag.service.FreeBoardTagService;
import com.CreatorConnect.server.member.bookmark.entity.Bookmark;
import com.CreatorConnect.server.member.bookmark.repository.BookmarkRepository;
import com.CreatorConnect.server.member.entity.Member;
import com.CreatorConnect.server.member.like.entity.Like;
import com.CreatorConnect.server.member.like.repository.LikeRepository;
import com.CreatorConnect.server.member.repository.MemberRepository;
import com.CreatorConnect.server.member.service.MemberService;
import com.CreatorConnect.server.board.tag.entity.Tag;
import com.CreatorConnect.server.board.tag.mapper.TagMapper;
import lombok.RequiredArgsConstructor;
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
@RequiredArgsConstructor
public class FreeBoardController {
    private final FreeBoardService freeBoardService;
    private final FreeBoardRepository freeBoardRepository;
    private final FreeBoardMapper mapper;
    private final CategoryService categoryService;
    private final TagMapper tagMapper;
    private final FreeBoardTagService freeBoardTagService;
    private final FeedbackBoardTagService tagService;
    private final MemberService memberService;
    private final MemberRepository memberRepository;
    private final LikeRepository likeRepository;
    private final BookmarkRepository bookmarkRepository;

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
        // 태그 업데이트
        List<Tag> updatedTag = freeBoardTagService.updateFreeBoardTag(tags, freeBoardPatch);

        FreeBoardDto.Response response = mapper.freeBoardToFreeBoardResponseDto(freeBoardPatch);
        response.setTags(tagMapper.tagsToTagResponseDto(updatedTag));

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

     // 자유 게시판 게시글 목록 조회
    @GetMapping("/freeboards")
    public ResponseEntity getFreeBoards(@RequestParam String sort,
                                        @Positive @RequestParam int page,
                                        @Positive @RequestParam int size) {
        FreeBoardDto.MultiResponseDto<FreeBoardDto.Response> pageFreeBoards = freeBoardService.getAllFreeBoards(page, size, sort);

        return new ResponseEntity<>(pageFreeBoards, HttpStatus.OK);
    }

    // 자유 게시판 카테고리 별 목록 조회
    @GetMapping("/freboards/category/{categoryId}")
    public ResponseEntity getFreeBoardsByCategory(@PathVariable("categoryId") long categoryId,
                                                  @RequestParam String sort,
                                                  @Positive @RequestParam int page,
                                                @Positive @RequestParam int size) {
        FreeBoardDto.MultiResponseDto<FreeBoardDto.Response> pageFreeBoard = freeBoardService.getAllFreeBoardsByCategory(categoryId, page, size, sort);
        return new ResponseEntity<>(pageFreeBoard, HttpStatus.OK);
    }


    // 자유 게시판 게시글 상세 조회
    @GetMapping("/freeboard/{freeboardId}")
    public ResponseEntity getFreeBoardDetail(@Positive @PathVariable("freeboardId") long freeBoardId) {


        return new ResponseEntity<>(freeBoardService.getFreeBoardDetail(freeBoardId), HttpStatus.OK);
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

        FreeBoard findfreeBoard = freeBoardService.verifyFreeBoard(freeBoardId);

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
        like.setFreeBoard(findfreeBoard);
        likeRepository.save(like);

        // 현재 사용자의 likes 컬렉션에 좋아요 추가
        currentMember.getLikes().add(like);
        memberRepository.save(currentMember);

        // 게시물의 likeCount 증가
        findfreeBoard.setLikeCount(findfreeBoard.getLikeCount() + 1);
        freeBoardRepository.save(findfreeBoard);

        return new ResponseEntity<>(HttpStatus.OK);

    }

    @DeleteMapping("/freeboard/{freeBoardId}/like")
    public ResponseEntity unlikeFreeBoard (@PathVariable("freeBoardId") @Positive Long freeBoardId) {

        // 현재 로그인한 사용자 정보 가져오기
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Member currentMember = memberService.findVerifiedMember(authentication.getName());

        FreeBoard findfreeBoard = freeBoardService.verifyFreeBoard(freeBoardId);

        Optional<Set<Like>> likes = Optional.ofNullable(currentMember.getLikes());

        Set<Like> foundLikes = likes.orElse(Collections.emptySet())
                .stream()
                .filter(l -> l != null && l.getFreeBoard() != null && l.getFreeBoard().getFreeBoardId().equals(findfreeBoard.getFreeBoardId()))
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

        // 게시물의 likeCount 삭제
        findfreeBoard.setLikeCount(findfreeBoard.getLikeCount() - 1);
        freeBoardRepository.save(findfreeBoard);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);

    }

    @PostMapping("/freeboard/{freeBoardId}/bookmark")
    public ResponseEntity bookmarkFeedbackBoard (@PathVariable("freeBoardId") @Positive Long freeBoardId) {

        // 현재 로그인한 사용자 정보 가져오기
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Member currentMember = memberService.findVerifiedMember(authentication.getName());

        FreeBoard findfreeBoard = freeBoardService.verifyFreeBoard(freeBoardId);

        // 현재 로그인한 사용자가 해당 게시물을 북마크 했는지 확인
        boolean isAlreadyBookMarked = currentMember.getBookmarks().stream()
                .filter(Objects::nonNull) // null인 요소 필터링
                .map(Bookmark::getFreeBoard)
                .filter(Objects::nonNull) // null인 FeedbackBoard 필터링
                .anyMatch(freeBoard -> freeBoard.getFreeBoardId().equals(findfreeBoard.getFreeBoardId()));

        if (isAlreadyBookMarked) {
            return ResponseEntity.badRequest().body("Already bookmarked.");
        }

        Bookmark bookmark = new Bookmark();
        bookmark.setBoardType(Like.BoardType.FREEBOARD);
        bookmark.setMember(currentMember);
        bookmark.setFreeBoard(findfreeBoard);
        bookmarkRepository.save(bookmark);

        // 현재 사용자의 bookmark 컬렉션에 bookmark 추가
        currentMember.getBookmarks().add(bookmark);
        memberRepository.save(currentMember);

        return new ResponseEntity<>(HttpStatus.OK);

    }

    @DeleteMapping("/freeboard/{freeBoardId}/bookmark")
    public ResponseEntity unbookmarkFeedbackBoard (@PathVariable("freeBoardId") @Positive Long freeBoardId) {

        // 현재 로그인한 사용자 정보 가져오기
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Member currentMember = memberService.findVerifiedMember(authentication.getName());

        FreeBoard findfreeBoard = freeBoardService.verifyFreeBoard(freeBoardId);
        // 현재 로그인한 사용자가 해당 게시물을 북마크 했는지 확인

        Optional<Set<Bookmark>> bookmarks = Optional.ofNullable(currentMember.getBookmarks());

        Set<Bookmark> foundBookmarks = bookmarks.orElse(Collections.emptySet())
                .stream()
                .filter(l -> l != null && l.getFreeBoard() != null && l.getFreeBoard().getFreeBoardId().equals(findfreeBoard.getFreeBoardId()))
                .collect(Collectors.toSet());

        if (foundBookmarks.isEmpty()) {
            return ResponseEntity.badRequest().body("Not bookmarked.");
        }

        // 현재 사용자의 bookmarks 컬렉션에서 북마크 삭제
        currentMember.getBookmarks().removeAll(foundBookmarks);

        // 삭제된 북마크 엔티티들을 데이터베이스에서 삭제
        bookmarkRepository.deleteAll(foundBookmarks);

        memberRepository.save(currentMember);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);

    }

}
