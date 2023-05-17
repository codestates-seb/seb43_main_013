package com.CreatorConnect.server.board.jobboard.service;

import com.CreatorConnect.server.board.categories.jobcategory.entity.JobCategory;
import com.CreatorConnect.server.board.categories.jobcategory.repository.JobCategoryRepository;
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
}
