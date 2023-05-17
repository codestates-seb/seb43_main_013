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
import com.CreatorConnect.server.member.entity.Member;
import com.CreatorConnect.server.member.repository.MemberRepository;
import com.CreatorConnect.server.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

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

        // 1. 회원 검증
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        memberService.verifiedAuthenticatedMember(post.getMemberId(), authentication.getName());

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
     * 2. 구인구직 카테고리 유효성 검증(존재하는 카테고리?)
     * 3. 수정
     *  3-1. 카테고리 수정
     *  3-2. 제목 수정
     *  3-3. 내용 수정
     * 4. 저장
     */
    public JobBoard updateJobBoard(JobBoardDto.Patch patch, Long jobBoardId) {
        // 1. 게시글 존쟈 여부 확인
        patch.setJobBoardId(jobBoardId);
        JobBoard jobBoard = mapper.jobBoardPatchDtoToJobBoard(patch);
        JobBoard checkedJobBoard = verifyJobBoard(jobBoard.getJobBoardId());

        // 2. 구인구직 카테고리 유효성 검증(존재하는 카테고리?)
        if (patch.getJobCategoryName() != null) { // 구인구직 카테고리 수정된 경우
            jobCategoryService.findVerifiedJobCategoryByJobCategoryName(patch.getJobCategoryName()); // 수정된 카테고리 존재 여부 확인
            // 3. 수정

            // 3-1. 카테고리 수정
            Optional<JobCategory> jobCategory = jobCategoryRepository.findByJobCategoryName(patch.getJobCategoryName());
            checkedJobBoard.setJobCategory(jobCategory.orElseThrow(() ->
                    new BusinessLogicException(ExceptionCode.CATEGORY_NOT_FOUND)));
        }

        // 3-2. 제목 수정
        Optional.ofNullable(jobBoard.getTitle())
                .ifPresent(title -> checkedJobBoard.setContent(title));

        // 3-3. 내용 수정
        Optional.ofNullable(jobBoard.getContent())
                .ifPresent(content -> checkedJobBoard.setContent(content));

        return jobBoardRepository.save(checkedJobBoard);
    }

    // 게시글 존재 여부 검증 메서드
    private JobBoard verifyJobBoard(Long jobBoardId) {
        Optional<JobBoard> optionalJobBoard = jobBoardRepository.findById(jobBoardId);
        return optionalJobBoard.orElseThrow(() ->
                new BusinessLogicException(ExceptionCode.JOBBOARD_NOT_FOUND));
    }


}