package com.CreatorConnect.server.board.jobboard.controller;

import com.CreatorConnect.server.board.freeboard.entity.FreeBoard;
import com.CreatorConnect.server.board.jobboard.dto.JobBoardDto;
import com.CreatorConnect.server.board.jobboard.entity.JobBoard;
import com.CreatorConnect.server.board.jobboard.mapper.JobBoardMapper;
import com.CreatorConnect.server.board.jobboard.repository.JobBoardRepository;
import com.CreatorConnect.server.board.jobboard.service.JobBoardService;
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
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import java.util.Collections;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@Validated
public class JobBoardController {
    private final JobBoardService jobBoardService;
    private final JobBoardMapper mapper;
    private final JobBoardRepository jobBoardRepository;
    private final MemberService memberService;
    private final MemberRepository memberRepository;
    private final LikeRepository likeRepository;
    private final BookmarkRepository bookmarkRepository;

    // 구인구직 게시판 게시글 등록
    @PostMapping("/jobboard/new")
    @Secured("ROLE_USER")
    public ResponseEntity postJobBoard(@Valid @RequestBody JobBoardDto.Post post,
                                       @RequestHeader("Authorization") String authorizationToken) {
        String token = authorizationToken.substring(7);
        JobBoard createdJobBoard = jobBoardService.createJobBoard(post);

        return new ResponseEntity<>(mapper.jobBoardToJobBoardPostResposneDto(createdJobBoard), HttpStatus.CREATED);
    }

    // 구인구직 게시판 게시글 수정
    @PatchMapping("/jobboard/{jobBoardId}")
    @Secured("ROLE_USER")
    public ResponseEntity patchJobBoard(@PathVariable("jobBoardId") @Positive Long jobBoardId,
                                        @Valid @RequestBody JobBoardDto.Patch patch,
                                        @RequestHeader("Authorization") String authorizationToken) {
        String token = authorizationToken.substring(7);
        JobBoard updatedJobBoard = jobBoardService.updateJobBoard(patch, jobBoardId);

        return new ResponseEntity<>(mapper.jobBoardToJobBoardResponseDto(updatedJobBoard), HttpStatus.OK);
    }

    // 구인구직 게시판 게시글 목록
    @GetMapping("/jobboards")
    public ResponseEntity getJobBoards(@RequestParam String sort,
                                       @RequestParam @Positive int page,
                                       @RequestParam @Positive int size) {
        JobBoardDto.MultiResponseDto<JobBoardDto.Response> pageJobBoards = jobBoardService.getAllJobBoards(page, size, sort);

        return new ResponseEntity<>(pageJobBoards, HttpStatus.OK);
    }

    // 구인구직 게시판 카테고리 별 목록
    @GetMapping("/jobboards/jobcategories/{jobCategoryId}")
    public ResponseEntity getJobBoardsByCategory(@PathVariable("jobCategoryId") @Positive Long categoryId,
                                                 @RequestParam String sort,
                                                 @RequestParam @Positive int page,
                                                 @RequestParam @Positive int size) {
        JobBoardDto.MultiResponseDto<JobBoardDto.Response> pageJobBoards =
                jobBoardService.getAllJobBoardsByCategory(categoryId, page, size, sort);

        return new ResponseEntity<>(pageJobBoards, HttpStatus.OK);
    }

    // 구인구직 게시판 게시글 상세조회
    @GetMapping("/jobboard/{jobBoardId}")
    public ResponseEntity getJobBoardDetail(@PathVariable("jobBoardId") @Positive Long jobBoardId) {

        return new ResponseEntity<>(jobBoardService.getJobBoardDetail(jobBoardId), HttpStatus.OK);
    }

    // 구인구직 게시판 삭제
    @DeleteMapping("/jobboard/{jobBoardId}")
    public ResponseEntity deleteJobBoard(@PathVariable("jobBoardId") @Positive Long jobBoardId,
                                         @RequestHeader("Authorization") String authorizationToken) {
        String token = authorizationToken.substring(7);
        jobBoardService.removeJobBoard(jobBoardId);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PostMapping("/jobboard/{jobBoardId}/like")
    public ResponseEntity likeFreeBoard (@PathVariable("jobBoardId") @Positive Long jobBoardId,
                                         @RequestHeader(value = "Authorization") String authorizationToken) {

        String token = authorizationToken.substring(7);

        // 현재 로그인한 사용자 정보 가져오기
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Member currentMember = memberService.findVerifiedMember(authentication.getName());

        JobBoard findjobBoard = jobBoardService.verifyJobBoard(jobBoardId);

        // 현재 로그인한 사용자가 해당 게시물을 좋아요 했는지 확인
        boolean isAlreadyLiked = currentMember.getLikes().stream()
                .filter(Objects::nonNull) // null인 요소 필터링
                .map(Like::getJobBoard)
                .filter(Objects::nonNull) // null인 FreeBoard 필터링
                .anyMatch(jobBoard -> jobBoard.getJobBoardId().equals(findjobBoard.getJobBoardId()));

        if (isAlreadyLiked) {
            return ResponseEntity.badRequest().body("Already liked.");
        }

        // 게시물의 likeCount 증가
        findjobBoard.setLikeCount(findjobBoard.getLikeCount() + 1);
        jobBoardRepository.save(findjobBoard);

        Like like = new Like();
        like.setBoardType(Like.BoardType.JOBBOARD);
        like.setMember(currentMember);
        like.setJobBoard(findjobBoard);
        likeRepository.save(like);

        // 현재 사용자의 likes 컬렉션에 좋아요 추가
        currentMember.getLikes().add(like);
        memberRepository.save(currentMember);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/jobboard/{jobBoardId}/like")
    public ResponseEntity unlikeFreeBoard (@PathVariable("jobBoardId") @Positive Long jobBoardId,
                                           @RequestHeader(value = "Authorization") String authorizationToken) {

        String token = authorizationToken.substring(7);

        // 현재 로그인한 사용자 정보 가져오기
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Member currentMember = memberService.findVerifiedMember(authentication.getName());

        JobBoard findjobBoard = jobBoardService.verifyJobBoard(jobBoardId);

        // 현재 로그인한 사용자가 해당 게시물을 좋아요 했는지 확인
        Optional<Set<Like>> likes = Optional.ofNullable(currentMember.getLikes());

        Set<Like> foundLikes = likes.orElse(Collections.emptySet())
                .stream()
                .filter(l -> l != null && l.getJobBoard() != null && l.getJobBoard().getJobBoardId().equals(findjobBoard.getJobBoardId()))
                .collect(Collectors.toSet());

        if (foundLikes.isEmpty()) {
            return ResponseEntity.badRequest().body("Not liked.");
        }

        // 게시글의 likeCount 감소
        if (findjobBoard.getLikeCount() > 0) {
            findjobBoard.setLikeCount(findjobBoard.getLikeCount() - 1);
            jobBoardRepository.save(findjobBoard);
        }

        // 현재 사용자의 likes 컬렉션에서 좋아요 삭제
        for (Like foundLike : foundLikes) {
            currentMember.getLikes().remove(foundLike);
            likeRepository.delete(foundLike);
        }

        memberRepository.save(currentMember);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PostMapping("/jobboard/{jobBoardId}/bookmark")
    public ResponseEntity bookmarkFeedbackBoard (@PathVariable("jobBoardId") @Positive Long jobBoardId,
                                                 @RequestHeader(value = "Authorization") String authorizationToken) {

        String token = authorizationToken.substring(7);

        // 현재 로그인한 사용자 정보 가져오기
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Member currentMember = memberService.findVerifiedMember(authentication.getName());

        JobBoard findjobBoard = jobBoardService.verifyJobBoard(jobBoardId);

        // 현재 로그인한 사용자가 해당 게시물을 북마크 했는지 확인
        boolean isAlreadyBookMarked = currentMember.getBookmarks().stream()
                .filter(Objects::nonNull) // null인 요소 필터링
                .map(Bookmark::getJobBoard)
                .filter(Objects::nonNull) // null인 FeedbackBoard 필터링
                .anyMatch(jobBoard -> jobBoard.getJobBoardId().equals(findjobBoard.getJobBoardId()));

        if (isAlreadyBookMarked) {
            return ResponseEntity.badRequest().body("Already bookmarked.");
        }

        Bookmark bookmark = new Bookmark();
        bookmark.setBoardType(Bookmark.BoardType.JOBBOARD);
        bookmark.setMember(currentMember);
        bookmark.setJobBoard(findjobBoard);
        bookmarkRepository.save(bookmark);

        // 현재 사용자의 bookmark 컬렉션에 bookmark 추가
        currentMember.getBookmarks().add(bookmark);
        memberRepository.save(currentMember);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/jobboard/{jobBoardId}/bookmark")
    public ResponseEntity unbookmarkFeedbackBoard (@PathVariable("jobBoardId") @Positive Long jobBoardId,
                                                   @RequestHeader(value = "Authorization") String authorizationToken) {

        String token = authorizationToken.substring(7);

        // 현재 로그인한 사용자 정보 가져오기
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Member currentMember = memberService.findVerifiedMember(authentication.getName());

        JobBoard findjobBoard = jobBoardService.verifyJobBoard(jobBoardId);

        // 현재 로그인한 사용자가 해당 게시물을 북마크 했는지 확인
        Optional<Set<Bookmark>> bookmarks = Optional.ofNullable(currentMember.getBookmarks());

        Set<Bookmark> foundBookmarks = bookmarks.orElse(Collections.emptySet())
                .stream()
                .filter(l -> l != null && l.getJobBoard() != null && l.getJobBoard().getJobBoardId().equals(findjobBoard.getJobBoardId()))
                .collect(Collectors.toSet());

        if (foundBookmarks.isEmpty()) {
            return ResponseEntity.badRequest().body("Not bookmarked.");
        }

        // 현재 사용자의 bookmarks 컬렉션에서 북마크 삭제
        for (Bookmark foundBookmark : foundBookmarks) {
            currentMember.getBookmarks().remove(foundBookmark);
            bookmarkRepository.delete(foundBookmark);
        }
        memberRepository.save(currentMember);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
