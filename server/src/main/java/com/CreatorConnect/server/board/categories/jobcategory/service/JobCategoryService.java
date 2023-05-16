package com.CreatorConnect.server.board.categories.jobcategory.service;

import com.CreatorConnect.server.board.categories.jobcategory.entity.JobCategory;
import com.CreatorConnect.server.board.categories.jobcategory.repository.JobCategoryRepository;
import com.CreatorConnect.server.exception.BusinessLogicException;
import com.CreatorConnect.server.exception.ExceptionCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class JobCategoryService {
    private final JobCategoryRepository jobCategoryRepository;
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

    // 중복 카테고리 존재 여부 검증 메서드
    private void verifyExistsJobCategory(String jobCategoryName) {
        Optional<JobCategory> optionalJobCategory = jobCategoryRepository.findByJobCategoryName(jobCategoryName);
        if (optionalJobCategory.isPresent()) { // 구인 구직 카테고리가 이미 존재하면 예외 처리
            throw new BusinessLogicException(ExceptionCode.CATEGORY_EXISTS);
        }
    }
}
