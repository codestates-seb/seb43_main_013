package com.CreatorConnect.server.board.jobboard.service;

import com.CreatorConnect.server.board.categories.jobcategory.entity.JobCategory;
import com.CreatorConnect.server.board.categories.jobcategory.repository.JobCategoryRepository;
import com.CreatorConnect.server.board.categories.jobcategory.service.JobCategoryService;
import com.CreatorConnect.server.board.freeboard.entity.FreeBoard;
import com.CreatorConnect.server.board.jobboard.dto.JobBoardDto;
import com.CreatorConnect.server.board.jobboard.entity.JobBoard;
import com.CreatorConnect.server.board.jobboard.mapper.JobBoardMapper;
import com.CreatorConnect.server.board.jobboard.repository.JobBoardRepository;
import com.CreatorConnect.server.exception.BusinessLogicException;
import com.CreatorConnect.server.exception.ExceptionCode;
import com.CreatorConnect.server.member.entity.Member;
import com.CreatorConnect.server.member.repository.MemberRepository;
import com.CreatorConnect.server.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class JobBoardService {
    private final JobBoardRepository jobBoardRepository;
    private final MemberService memberService;
    private final JobBoardMapper mapper;
    private final MemberRepository memberRepository;
    private final JobCategoryRepository jobCategoryRepository;
    private final JobCategoryService jobCategoryService;
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

        // 4. 게시글 등록
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
        // 1. 게시글 존쟈 여부 확인
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
    public JobBoardDto.MultiResponseDto<JobBoardDto.Response> getAllJobBoards(int page, int size, String sort) {
        // 1. 페이지네이션 적용 - 최신순 / 등록순 / 인기순
        Page<JobBoard> jobBoards = jobBoardRepository.findAll(sortedBy(page, size, sort));

        // 2. 게시글 목록 가져오기
        List<JobBoardDto.Response> response = mapper.jobBoardsToJobBoardResponseDtos(jobBoards.getContent());

        // new FreeBoardDto.MultiResponseDto<>(responses, freeBoards);
        return new JobBoardDto.MultiResponseDto<>(response, jobBoards);

    }

    /**
     * <구인구직 게시판 게시글 목록>
     * 1. 페이지네이션 적용 - 최신순 / 등록순 / 인기순
     * 2. 게시글 목록 가져오기
     */
    public JobBoardDto.MultiResponseDto<JobBoardDto.Response> getAllJobBoardsByCategory(Long jobCategoryId, int page, int size, String sort) {
        // 1. 페이지네이션 적용 - 최신순 / 등록순 / 인기순
        Page<JobBoard> jobBoards =
                jobBoardRepository.findJobBoardsByCategoryId(jobCategoryId, sortedBy(page, size, sort));

        // 2. 게시글 목록 가져오기
        List<JobBoardDto.Response> response = mapper.jobBoardsToJobBoardResponseDtos(jobBoards.getContent());

        return new JobBoardDto.MultiResponseDto<>(response, jobBoards);
    }

    /**
     * <구인구직 게시판 게시글 상세조회>
     * 1. 게시글 존재 여부 확인
     * 2. 조회수 증가
     * 3. 호그인한 멤버
     * 4. 매핑
     */
    public JobBoard getJobBoardDetail(Long jobBoardId) {
        // 1. 게시글 존재 여부 확인
        JobBoard jobBoard = verifyJobBoard(jobBoardId);

        // 2. 조회수 증가
        addViews(jobBoard);

        // 3. 로그인한 멤버
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        boolean bookmarked = false;
        boolean liked = false;

        if (authentication != null && authentication.isAuthenticated() && !"anonymousUser".equals(authentication.getName())) {
            Member loggedinMember = memberService.findVerifiedMember(authentication.getName());

            // 게시물을 북마크한 경우
            bookmarked = loggedinMember.getBookmarks().stream()
                    .anyMatch(bookmark -> bookmark.getFreeBoard().equals(jobBoard));

            // 게시물을 좋아요한 경우
            liked = loggedinMember.getLikes().stream()
                    .anyMatch(like -> like.getFreeBoard().equals(jobBoard));
        }

        // 4. 매핑
        JobBoardDto.Response response = mapper.jobBoardToJobBoardResponseDto(jobBoard);
        response.setBookmarked(bookmarked);
        response.setLiked(liked);

        return jobBoard;
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

        // 3. 삭제
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
}
