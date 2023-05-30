package com.CreatorConnect.server.board.jobboard.service;

import com.CreatorConnect.server.board.categories.jobcategory.entity.JobCategory;
import com.CreatorConnect.server.board.categories.jobcategory.repository.JobCategoryRepository;
import com.CreatorConnect.server.board.categories.jobcategory.service.JobCategoryService;
import com.CreatorConnect.server.board.jobboard.dto.JobBoardDto;
import com.CreatorConnect.server.board.jobboard.entity.JobBoard;
import com.CreatorConnect.server.board.jobboard.mapper.JobBoardMapper;
import com.CreatorConnect.server.board.jobboard.repository.JobBoardRepository;
import com.CreatorConnect.server.exception.BusinessLogicException;
import com.CreatorConnect.server.exception.ExceptionCode;
import com.CreatorConnect.server.member.bookmark.entity.Bookmark;
import com.CreatorConnect.server.member.bookmark.repository.BookmarkRepository;
import com.CreatorConnect.server.member.entity.Member;
import com.CreatorConnect.server.member.like.entity.Like;
import com.CreatorConnect.server.member.like.repository.LikeRepository;
import com.CreatorConnect.server.member.repository.MemberRepository;
import com.CreatorConnect.server.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class JobBoardService {
    private final JobBoardMapper mapper;
    private final JobBoardRepository jobBoardRepository;
    private final JobCategoryService jobCategoryService;
    private final JobCategoryRepository jobCategoryRepository;
    private final MemberService memberService;
    private final MemberRepository memberRepository;
    private final LikeRepository likeRepository;
    private final BookmarkRepository bookmarkRepository;

    /**
     * <구인구직 게시판 등록>
     * 1. 회원 검증
     * 2. 회원 매핑
     * 3. 구인구직 카테고리 매핑
     * 4. 게시글 등록
     */
    public JobBoard createJobBoard(JobBoardDto.Post post) {

        JobBoard jobBoard = mapper.jobBoardPostDtoToJobBoard(post);

        // 1. 회원 검증 post dto 의 memberId 와 로그인 한 유저 비교
        memberService.verifiedAuthenticatedMember(post.getMemberId());

        // 2. 회원 매핑
        Optional<Member> member = memberRepository.findById(post.getMemberId());
        jobBoard.setMember(member.orElseThrow(() ->
                new BusinessLogicException(ExceptionCode.MEMBER_NOT_FOUND)));

        // 3. 구인구직 카테고리 매핑
        Optional<JobCategory> jobCategory = jobCategoryRepository.findByJobCategoryName(post.getJobCategoryName());
        jobBoard.setJobCategory(jobCategory.orElseThrow(() ->
                new BusinessLogicException(ExceptionCode.CATEGORY_NOT_FOUND)));

        return jobBoardRepository.save(jobBoard);
    }

    /**
     * <구인구직 게시판 수정>
     * 1. 게시글 존재 여부 검증
     * 2. 작성자와 로그인한 멤버 비교
     * 3. 구인구직 카테고리 유효성 검증(존재하는 카테고리?)
     * 4. 수정
     *  4-1. 카테고리 수정
     *  4-2. 제목 수정
     *  4-3. 내용 수정
     * 5. 저장
     */
    public JobBoard updateJobBoard(JobBoardDto.Patch patch, Long jobBoardId) {

        // 1. 게시글 존재 여부 확인
        patch.setJobBoardId(jobBoardId);
        JobBoard jobBoard = mapper.jobBoardPatchDtoToJobBoard(patch);
        JobBoard checkedJobBoard = verifyJobBoard(jobBoard.getJobBoardId());

        // 2. 작성자와 로그인한 멤버 비교
        memberService.verifiedAuthenticatedMember(checkedJobBoard.getMember().getMemberId());

        // 3. 구인구직 카테고리 유효성 검증(존재하는 카테고리?)
        if (patch.getJobCategoryName() != null) { // 구인구직 카테고리 수정된 경우
            jobCategoryService.findVerifiedJobCategoryByJobCategoryName(patch.getJobCategoryName()); // 수정된 카테고리 존재 여부 확인
            // 4. 수정

            // 4-1. 카테고리 수정
            Optional<JobCategory> jobCategory = jobCategoryRepository.findByJobCategoryName(patch.getJobCategoryName());
            checkedJobBoard.setJobCategory(jobCategory.orElseThrow(() ->
                    new BusinessLogicException(ExceptionCode.CATEGORY_NOT_FOUND)));
        }

        // 4-2. 제목 수정
        Optional.ofNullable(jobBoard.getTitle())
                .ifPresent(title -> checkedJobBoard.setTitle(title));

        // 4-3. 내용 수정
        Optional.ofNullable(jobBoard.getContent())
                .ifPresent(content -> checkedJobBoard.setContent(content));

        return jobBoardRepository.save(checkedJobBoard);
    }

    /**
     * <구인구직 게시판 게시글 목록>
     * 1. 페이지네이션 적용 - 최신순 / 등록순 / 인기순
     * 2. 게시글 목록 가져오기
     */
    public JobBoardDto.MultiResponseDto<JobBoardDto.Response> getAllJobBoards(int page, int size, String sort, HttpServletRequest request) {

        // 1. 페이지네이션 적용 - 최신순 / 등록순 / 인기순
        Page<JobBoard> jobBoards = jobBoardRepository.findAll(sortedBy(page, size, sort));

        // 2. 로그인한 멤버확인하고 게시글 목록 가져오기
        String accessToken = request.getHeader("Authorization");

        Member loggedinMember = null;
        boolean bookmarked = false;
        boolean liked = false;

        List<JobBoardDto.Response> responses = new ArrayList<>();

        if (accessToken != null){

            loggedinMember = memberService.getLoggedinMember(accessToken);

            for (JobBoard jobBoard : jobBoards.getContent()) {
                bookmarked = loggedinMember.getBookmarks().stream()
                        .anyMatch(bookmark -> jobBoard.equals(bookmark.getJobBoard()));

                liked = loggedinMember.getLikes().stream()
                        .anyMatch(like -> jobBoard.equals(like.getJobBoard()));

                JobBoardDto.Response jobBoardResponse = mapper.jobBoardToJobBoardResponseDto(jobBoard);
                jobBoardResponse.setBookmarked(bookmarked);
                jobBoardResponse.setLiked(liked);
                responses.add(jobBoardResponse);
            }
        } else {
            responses = mapper.jobBoardsToJobBoardResponseDtos(jobBoards.getContent());
        }

        return new JobBoardDto.MultiResponseDto<>(responses, jobBoards);
    }

    /**
     * <구인구직 게시판 게시글 목록>
     * 1. 페이지네이션 적용 - 최신순 / 등록순 / 인기순
     * 2. 게시글 목록 가져오기
     */
    public JobBoardDto.MultiResponseDto<JobBoardDto.Response> getAllJobBoardsByCategory(Long jobCategoryId, int page, int size, String sort, HttpServletRequest request) {

        // 1. 페이지네이션 적용 - 최신순 / 등록순 / 인기순
        Page<JobBoard> jobBoards =
                jobBoardRepository.findJobBoardsByCategoryId(jobCategoryId, sortedBy(page, size, sort));

        // 2. 로그인한 멤버확인하고 게시글 목록 가져오기
        String accessToken = request.getHeader("Authorization");

        Member loggedinMember = null;
        boolean bookmarked = false;
        boolean liked = false;

        List<JobBoardDto.Response> responses = new ArrayList<>();

        if (accessToken != null){

            loggedinMember = memberService.getLoggedinMember(accessToken);

            for (JobBoard jobBoard : jobBoards.getContent()) {
                bookmarked = loggedinMember.getBookmarks().stream()
                        .anyMatch(bookmark -> jobBoard.equals(bookmark.getJobBoard()));

                liked = loggedinMember.getLikes().stream()
                        .anyMatch(like -> jobBoard.equals(like.getJobBoard()));

                JobBoardDto.Response jobBoardResponse = mapper.jobBoardToJobBoardResponseDto(jobBoard);
                jobBoardResponse.setBookmarked(bookmarked);
                jobBoardResponse.setLiked(liked);
                responses.add(jobBoardResponse);
            }
        } else {
            responses = mapper.jobBoardsToJobBoardResponseDtos(jobBoards.getContent());
        }

        return new JobBoardDto.MultiResponseDto<>(responses, jobBoards);
    }

    /**
     * <구인구직 게시판 게시글 상세조회>
     * 1. 게시글 존재 여부 확인
     * 2. 조회수 증가
     * 3. 호그인한 멤버
     * 4. 매핑
     */
    public JobBoardDto.Response getJobBoardDetail(Long jobBoardId, HttpServletRequest request) {

        // 1. 게시글 존재 여부 확인
        JobBoard jobBoard = verifyJobBoard(jobBoardId);

        // 2. 조회수 증가
        addViews(jobBoard);

        // 로그인한 멤버
        String accessToken = request.getHeader("Authorization");

        Member loggedinMember = null;
        boolean bookmarked = false;
        boolean liked = false;

        // 로그인 여부 확인
        if (accessToken != null) {
            // 로그인 했을 때
            loggedinMember = memberService.getLoggedinMember(accessToken);

            if (loggedinMember != null) {
                // 게시물을 북마크한 경우
                if (loggedinMember.getBookmarks() != null) {
                    bookmarked = loggedinMember.getBookmarks().stream()
                            .anyMatch(bookmark -> jobBoard.equals(bookmark.getJobBoard()));
                }

                // 게시물을 좋아요한 경우
                if (loggedinMember.getLikes() != null) {
                    liked = loggedinMember.getLikes().stream()
                            .anyMatch(like -> jobBoard.equals(like.getJobBoard()));
                }
            }
        }

        // 4. 매핑
        JobBoardDto.Response response = mapper.jobBoardToJobBoardResponseDto(jobBoard);
        response.setBookmarked(bookmarked);
        response.setLiked(liked);

        return response;
    }

    /**
     * <구인구직 게시판 게시글 삭제>
     * 1. 게시글 존재 여부 확인
     * 2. 삭제하려는 유저와 게시글을 작성한 유저가 같은 유저인지 검증
     * 3. 삭제
     */
    public void removeJobBoard(Long jobBoardId) {

        // 1. 게시글 존재 여부 확인
        JobBoard jobBoard = verifyJobBoard(jobBoardId);

        // 2. 삭제하려는 유저와 게시글을 작성한 유저가 같은 유저인지 검증
        memberService.verifiedAuthenticatedMember(jobBoard.getMember().getMemberId());

        jobBoardRepository.delete(jobBoard);
    }

    // 게시글 존재 여부 검증 메서드
    public JobBoard verifyJobBoard(Long jobBoardId) {

        Optional<JobBoard> optionalJobBoard = jobBoardRepository.findById(jobBoardId);

        return optionalJobBoard.orElseThrow(() ->
                new BusinessLogicException(ExceptionCode.JOBBOARD_NOT_FOUND));
    }

    // 페이지네이션 정렬 기준 선택 메서드
    private PageRequest sortedBy(int page, int size, String sort) {

        if (sort.equals("최신순")) {
            return PageRequest.of(page - 1, size, Sort.by("jobBoardId").descending());
        } else if (sort.equals("등록순")) {
            return PageRequest.of(page - 1, size, Sort.by("jobBoardId").ascending());
        } else if (sort.equals("인기순")) {
            return PageRequest.of(page - 1, size, Sort.by("viewCount", "jobBoardId").descending());
        } else {
            return PageRequest.of(page - 1, size, Sort.by("jobBoardId").ascending());
        }
    }

    // 조회수 증가 메서드
    private void addViews(JobBoard jobBoard) {

        jobBoard.setViewCount(jobBoard.getViewCount() + 1);
        jobBoardRepository.save(jobBoard);
    }

    public void likeJobBoard (Long jobBoardId, String authorizationToken) {

        JobBoard findjobBoard = verifyJobBoard(jobBoardId);
        Member currentMember = memberService.getLoggedinMember(authorizationToken);

        // 현재 로그인한 사용자가 해당 게시물을 좋아요 했는지 확인
        boolean isAlreadyLiked = currentMember.getLikes().stream()
                .filter(Objects::nonNull) // null인 요소 필터링
                .map(Like::getJobBoard)
                .filter(Objects::nonNull) // null인 FreeBoard 필터링
                .anyMatch(jobBoard -> findjobBoard.getJobBoardId().equals(jobBoard.getJobBoardId()));

        if (isAlreadyLiked) {
            throw new BusinessLogicException(ExceptionCode.LIKE_ALREADY_EXISTS);
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
    }

    public void unlikeJobBoard (Long jobBoardId, String authorizationToken) {

        JobBoard findjobBoard = verifyJobBoard(jobBoardId);
        Member currentMember = memberService.getLoggedinMember(authorizationToken);

        // 현재 로그인한 사용자가 해당 게시물을 좋아요 했는지 확인
        Optional<Set<Like>> likes = Optional.ofNullable(currentMember.getLikes());

        Set<Like> foundLikes = likes.orElse(Collections.emptySet())
                .stream()
                .filter(l -> l != null && l.getJobBoard() != null && l.getJobBoard().getJobBoardId().equals(findjobBoard.getJobBoardId()))
                .collect(Collectors.toSet());

        if (foundLikes.isEmpty()) {
            throw new BusinessLogicException(ExceptionCode.LIKE_NOT_FOUND);
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
    }

    public void bookmarkJobBoard (Long jobBoardId, String authorizationToken) {

        JobBoard findjobBoard = verifyJobBoard(jobBoardId);
        Member currentMember = memberService.getLoggedinMember(authorizationToken);

        // 현재 로그인한 사용자가 해당 게시물을 북마크 했는지 확인
        boolean isAlreadyBookMarked = currentMember.getBookmarks().stream()
                .filter(Objects::nonNull) // null인 요소 필터링
                .map(Bookmark::getJobBoard)
                .filter(Objects::nonNull) // null인 FeedbackBoard 필터링
                .anyMatch(jobBoard -> findjobBoard.getJobBoardId().equals(jobBoard.getJobBoardId()));

        if (isAlreadyBookMarked) {
            throw new BusinessLogicException(ExceptionCode.BOOKMARK_ALREADY_EXISTS);
        }

        Bookmark bookmark = new Bookmark();
        bookmark.setBoardType(Bookmark.BoardType.JOBBOARD);
        bookmark.setMember(currentMember);
        bookmark.setJobBoard(findjobBoard);
        bookmarkRepository.save(bookmark);

        // 현재 사용자의 bookmark 컬렉션에 bookmark 추가
        currentMember.getBookmarks().add(bookmark);
        memberRepository.save(currentMember);
    }

    public void unbookmarkJobBoard (Long jobBoardId, String authorizationToken) {

        JobBoard findjobBoard = verifyJobBoard(jobBoardId);
        Member currentMember = memberService.getLoggedinMember(authorizationToken);

        // 현재 로그인한 사용자가 해당 게시물을 북마크 했는지 확인
        Optional<Set<Bookmark>> bookmarks = Optional.ofNullable(currentMember.getBookmarks());

        Set<Bookmark> foundBookmarks = bookmarks.orElse(Collections.emptySet())
                .stream()
                .filter(l -> l != null && l.getJobBoard() != null && l.getJobBoard().getJobBoardId().equals(findjobBoard.getJobBoardId()))
                .collect(Collectors.toSet());

        if (foundBookmarks.isEmpty()) {
            throw new BusinessLogicException(ExceptionCode.BOOKMARK_NOT_FOUND);
        }

        // 현재 사용자의 bookmarks 컬렉션에서 북마크 삭제
        for (Bookmark foundBookmark : foundBookmarks) {
            currentMember.getBookmarks().remove(foundBookmark);
            bookmarkRepository.delete(foundBookmark);
        }
        memberRepository.save(currentMember);
    }

}
