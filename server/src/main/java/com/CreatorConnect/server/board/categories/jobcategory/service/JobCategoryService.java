package com.CreatorConnect.server.board.categories.jobcategory.service;

import com.CreatorConnect.server.board.categories.jobcategory.dto.JobCategoryDto;
import com.CreatorConnect.server.board.categories.jobcategory.entity.JobCategory;
import com.CreatorConnect.server.board.categories.jobcategory.mapper.JobCategoryMapper;
import com.CreatorConnect.server.board.categories.jobcategory.repository.JobCategoryRepository;
import com.CreatorConnect.server.exception.BusinessLogicException;
import com.CreatorConnect.server.exception.ExceptionCode;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class JobCategoryService {
    private final JobCategoryRepository jobCategoryRepository;
    private final JobCategoryMapper mapper;
    /**
     * <구인 구직 카티고리 등록>
     * 1. 카테고리 중복 검증
     * 2. 카테고리 등록
     */
    public JobCategory createJobCategory(JobCategory jobCategory) {
        // 1. 카테고리 중복 검증
        verifyExistsJobCategory(jobCategory.getJobCategoryName());

        // 2. 카테고리 등록
        return jobCategoryRepository.save(jobCategory);
    }

    /**
     * <구인 구직 카테고리 수정>
     * 1. 수정된 카테고리의 존재 여부 검증
     * 2. 카테고리 수정
     * 3. 수정된 데이터 저장
     */
    public JobCategory updateJobCategory(JobCategory jobCategory, Long jobCategoryId) {
        // 1. 수정된 카테고리의 존재 여부 검증
        JobCategory verfiedJobCategory = findverifiedJobCategory(jobCategoryId);

        // 2. 카테고리 수정
        Optional.ofNullable(jobCategory.getJobCategoryName())
                .ifPresent(jobCategoryName -> verfiedJobCategory.setJobCategoryName(jobCategoryName));

        return jobCategoryRepository.save(verfiedJobCategory);

    }

    /**
     * <구인구직 카테고리 조회>
     * 1.카테고리 존재 여부 검증
     * 2. 조회
     */
    public JobCategoryDto.Response getJobCategory(Long jobCategoryId) {
        // 1. 카테고리 존재 여부 검증
        JobCategory jobCategory = findverifiedJobCategory(jobCategoryId);

        // 2. 조회
        return mapper.jobCategoryToJobCategoryResponseDto(jobCategory);

    }

    /**
     * <구인구직 카테고리 목록 조회>
     * 1. 카테고리 목록 정렬
     */
    public List<JobCategoryDto.Response> getJobCategories() {
        // 1. 카테고리 목록 정렬
        PageRequest pageRequest = PageRequest.of(0, 20, Sort.by("jobCategoryId").descending());
        Page<JobCategory> jobCategories = jobCategoryRepository.findAll(pageRequest);
        return mapper.jobCategoryToJobCategoryResponseDtos(jobCategories.getContent());
    }

    // 중복 카테고리 존재 여부 검증 메서드
    public void verifyExistsJobCategory(String jobCategoryName) {
        Optional<JobCategory> optionalJobCategory = jobCategoryRepository.findByJobCategoryName(jobCategoryName);
        if (optionalJobCategory.isPresent()) { // 구인 구직 카테고리가 이미 존재하면 예외 처리
            throw new BusinessLogicException(ExceptionCode.CATEGORY_EXISTS);
        }
    }

    // 카테고리 검증 메서드
    private JobCategory findverifiedJobCategory(Long jobCategoryId) {
        Optional<JobCategory> optionalJobCategory = jobCategoryRepository.findById(jobCategoryId);
        return optionalJobCategory.orElseThrow(() ->
                new BusinessLogicException(ExceptionCode.CATEGORY_NOT_FOUND));
    }

    // 카테고리 검증 메서드 CategoryName을 통해 검증
    public void findVerifiedJobCategoryByJobCategoryName(String jobCategoryName) {
        Optional<JobCategory> optionalJobCategory = jobCategoryRepository.findByJobCategoryName(jobCategoryName);
        if (!optionalJobCategory.isPresent()) { // 해당 구인구직 카테고리가 존재하지 않는 경우
            throw new BusinessLogicException(ExceptionCode.CATEGORY_NOT_FOUND);
        }
    }



}
