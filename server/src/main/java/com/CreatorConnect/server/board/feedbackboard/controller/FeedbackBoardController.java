package com.CreatorConnect.server.board.feedbackboard.controller;

import com.CreatorConnect.server.board.feedbackboard.service.FeedbackBoardService;
import com.CreatorConnect.server.board.feedbackboard.dto.FeedbackBoardDto;
import com.CreatorConnect.server.board.feedbackboard.dto.FeedbackBoardResponseDto;
import com.CreatorConnect.server.board.feedbackboard.entity.FeedbackBoard;
import com.CreatorConnect.server.member.bookmark.entity.Bookmark;
import com.CreatorConnect.server.member.bookmark.repository.BookmarkRepository;
import com.CreatorConnect.server.member.entity.Member;
import com.CreatorConnect.server.member.like.entity.Like;
import com.CreatorConnect.server.member.like.repository.LikeRepository;
import com.CreatorConnect.server.member.repository.MemberRepository;
import com.CreatorConnect.server.member.service.MemberService;
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
@RequiredArgsConstructor
@Validated
public class FeedbackBoardController {
    private final FeedbackBoardService feedbackBoardService;
    private final MemberService memberService;
    private final MemberRepository memberRepository;
    private final LikeRepository likeRepository;
    private final BookmarkRepository bookmarkRepository;

    @PostMapping("/feedbackboard/new")
    public ResponseEntity<FeedbackBoardResponseDto.Post> postFeedback(@Valid @RequestBody FeedbackBoardDto.Post postDto) {
        FeedbackBoardResponseDto.Post response = feedbackBoardService.createFeedback(postDto);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
    @PatchMapping("/feedbackboard/{feedbackBoardId}")
    public ResponseEntity<FeedbackBoardResponseDto.Patch> patchFeedback(@PathVariable("feedbackBoardId") Long feedbackBoardId,
                                                                        @Valid @RequestBody FeedbackBoardDto.Patch patchDto){
        FeedbackBoardResponseDto.Patch response = feedbackBoardService.updateFeedback(feedbackBoardId, patchDto);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    @GetMapping("/feedbackboard/{feedbackBoardId}")
    public ResponseEntity<FeedbackBoardResponseDto.Details> getFeedback(@PathVariable("feedbackBoardId") @Positive Long feedbackBoardId){
        FeedbackBoardResponseDto.Details response = feedbackBoardService.responseFeedback(feedbackBoardId);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    @GetMapping("/feedbackboards")
    public ResponseEntity getFeedbacks(@RequestParam("sort") String sort,
                                       @RequestParam("page") @Positive int page,
                                       @RequestParam("size") @Positive int size) {
        FeedbackBoardResponseDto.Multi response = feedbackBoardService.responseFeedbacks(sort, page, size);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    @GetMapping("/feedbackboards/feedbackcategories/{feedbackCategoryId}")
    public ResponseEntity getFeedbacksByFeedbackCategory(@PathVariable("feedbackCategoryId") @Positive Long feedbackCategoryId,
                                                         @RequestParam("sort") String sort,
                                                         @RequestParam("page") @Positive int page,
                                                         @RequestParam("size") @Positive int size) {
        FeedbackBoardResponseDto.Multi response = feedbackBoardService.responseFeedbacksByCategory(feedbackCategoryId, sort, page, size);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    @DeleteMapping("/feedbackboard/{feedbackBoardId}")
    public ResponseEntity<HttpStatus> deleteFeedback(@PathVariable("feedbackBoardId") @Positive Long feedbackBoardId) {
        feedbackBoardService.deleteFeedback(feedbackBoardId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PostMapping("/feedbackboard/{feedbackBoardId}/like")
    public ResponseEntity likeFeedbackBoard (@PathVariable("feedbackBoardId") @Positive Long feedbackBoardId) {

        // 현재 로그인한 사용자 정보 가져오기
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Member currentMember = memberService.findVerifiedMember(authentication.getName());

        FeedbackBoard foundfeedbackBoard = feedbackBoardService.findVerifiedFeedbackBoard(feedbackBoardId);

        // 현재 로그인한 사용자가 해당 게시물을 좋아요 했는지 확인
        boolean isAlreadyLiked = currentMember.getLikes().stream()
                .filter(Objects::nonNull) // null인 요소 필터링
                .map(Like::getFeedbackBoard)
                .filter(Objects::nonNull) // null인 FeedbackBoard 필터링
                .anyMatch(feedbackBoard -> feedbackBoard.getFeedbackBoardId().equals(feedbackBoardId));

        if (isAlreadyLiked) {
            return ResponseEntity.badRequest().body("Already liked.");
        }

        Like like = new Like();
        like.setBoardType(Like.BoardType.FEEDBACKBOARD);
        like.setMember(currentMember);
        like.setFeedbackBoard(foundfeedbackBoard);
        likeRepository.save(like);

        // 현재 사용자의 likes 컬렉션에 좋아요 추가
        currentMember.getLikes().add(like);
        memberRepository.save(currentMember);

        return new ResponseEntity<>(HttpStatus.OK);

    }

    @DeleteMapping("/feedbackboard/{feedbackBoardId}/like")
    public ResponseEntity unlikeFeedbackBoard (@PathVariable("feedbackBoardId") @Positive Long feedbackBoardId) {

        // 현재 로그인한 사용자 정보 가져오기
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Member currentMember = memberService.findVerifiedMember(authentication.getName());

        FeedbackBoard feedbackBoard = feedbackBoardService.findVerifiedFeedbackBoard(feedbackBoardId);

        // 현재 로그인한 사용자가 해당 게시물을 좋아요 했는지 확인

        Optional<Set<Like>> likes = Optional.ofNullable(currentMember.getLikes());

        Set<Like> foundLikes = likes.orElse(Collections.emptySet())
                .stream()
                .filter(l -> l != null && l.getFeedbackBoard() != null && l.getFeedbackBoard().getFeedbackBoardId().equals(feedbackBoard.getFeedbackBoardId()))
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

    @PostMapping("/feedbackboard/{feedbackBoardId}/bookmark")
    public ResponseEntity bookmarkFeedbackBoard (@PathVariable("feedbackBoardId") @Positive Long feedbackBoardId) {

        // 현재 로그인한 사용자 정보 가져오기
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Member currentMember = memberService.findVerifiedMember(authentication.getName());

        FeedbackBoard foundfeedbackBoard = feedbackBoardService.findVerifiedFeedbackBoard(feedbackBoardId);

        // 현재 로그인한 사용자가 해당 게시물을 북마크 했는지 확인
        boolean isAlreadyBookMarked = currentMember.getBookmarks().stream()
                .filter(Objects::nonNull) // null인 요소 필터링
                .map(Bookmark::getFeedbackBoard)
                .filter(Objects::nonNull) // null인 FeedbackBoard 필터링
                .anyMatch(feedbackBoard -> feedbackBoard.getFeedbackBoardId().equals(feedbackBoardId));

        if (isAlreadyBookMarked) {
            return ResponseEntity.badRequest().body("Already bookmarked.");
        }

        Bookmark bookmark = new Bookmark();
        bookmark.setBoardType(Like.BoardType.FEEDBACKBOARD);
        bookmark.setMember(currentMember);
        bookmark.setFeedbackBoard(foundfeedbackBoard);
        bookmarkRepository.save(bookmark);

        // 현재 사용자의 bookmark 컬렉션에 bookmark 추가
        currentMember.getBookmarks().add(bookmark);
        memberRepository.save(currentMember);

        return new ResponseEntity<>(HttpStatus.OK);

    }

    @DeleteMapping("/feedbackboard/{feedbackBoardId}/bookmark")
    public ResponseEntity unbookmarkFeedbackBoard (@PathVariable("feedbackBoardId") @Positive Long feedbackBoardId) {

        // 현재 로그인한 사용자 정보 가져오기
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Member currentMember = memberService.findVerifiedMember(authentication.getName());

        FeedbackBoard feedbackBoard = feedbackBoardService.findVerifiedFeedbackBoard(feedbackBoardId);

        // 현재 로그인한 사용자가 해당 게시물을 북마크 했는지 확인

        Optional<Set<Bookmark>> bookmarks = Optional.ofNullable(currentMember.getBookmarks());

        Set<Bookmark> foundBookmarks = bookmarks.orElse(Collections.emptySet())
                .stream()
                .filter(l -> l != null && l.getFeedbackBoard() != null && l.getFeedbackBoard().getFeedbackBoardId().equals(feedbackBoard.getFeedbackBoardId()))
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
