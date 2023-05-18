package com.CreatorConnect.server.board.categories.jobcategory.controller;

import com.CreatorConnect.server.board.categories.jobcategory.dto.JobCategoryDto;
import com.CreatorConnect.server.board.categories.jobcategory.entity.JobCategory;
import com.CreatorConnect.server.board.categories.jobcategory.mapper.JobCategoryMapper;
import com.CreatorConnect.server.board.categories.jobcategory.repository.JobCategoryRepository;
import com.CreatorConnect.server.board.categories.jobcategory.service.JobCategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@Validated
public class JobCategoryController {
    private final JobCategoryService jobCategoryService;
    private final JobCategoryMapper mapper;

    // 구인구직 카테고리 등록
    @Secured("ROLE_ADMIN")
    @PostMapping("/jobcategory/new")
    public ResponseEntity postJobCategory(@Valid @RequestBody JobCategoryDto.Post post) {
        JobCategory jobCategory = mapper.jobCategoryPostDtoToJobCategory(post);
        JobCategory createdJobCategory = jobCategoryService.createJobCategory(jobCategory);

        return new ResponseEntity<>(mapper.jobCategoryToJobCategoryResponseDto(createdJobCategory), HttpStatus.CREATED);
    }

    // 구인구직 카테고리 수정
    @Secured("ROLE_ADMIN")
    @PatchMapping("/jobcategory/{jobCategoryId}")
    public ResponseEntity patchJobCategory(@PathVariable("jobCategoryId") @Positive Long jobCategoryId,
                                           @Valid @RequestBody JobCategoryDto.Patch patch) {
        JobCategory jobCategory = mapper.jobCategoryPatchDtoToJobCategory(patch);
        jobCategory.setJobCategoryId(jobCategoryId);
        JobCategory updatedJobCategory = jobCategoryService.updateJobCategory(jobCategory, jobCategoryId);

        return new ResponseEntity<>(mapper.jobCategoryToJobCategoryResponseDto(updatedJobCategory), HttpStatus.OK);
    }

    // 구인구직 카테고리 조회
    @GetMapping("/jobcategory/{jobCategoryId}")
    public ResponseEntity getJobCategory(@PathVariable("jobCategoryId") @Positive Long jobCategoryId) {
        JobCategoryDto.Response response = jobCategoryService.getJobCategory(jobCategoryId);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    // 구인구직 카테고리 목록 조회
    @GetMapping("/jobcategories")
    public ResponseEntity getJobCategories() {
        List<JobCategoryDto.Response> responses = jobCategoryService.getJobCategories();

        return new ResponseEntity<>(responses, HttpStatus.OK);
    }
}
