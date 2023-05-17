package com.CreatorConnect.server.board.categories.jobcategory.controller;

import com.CreatorConnect.server.board.categories.jobcategory.dto.JobCategoryDto;
import com.CreatorConnect.server.board.categories.jobcategory.entity.JobCategory;
import com.CreatorConnect.server.board.categories.jobcategory.mapper.JobCategoryMapper;
import com.CreatorConnect.server.board.categories.jobcategory.service.JobCategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Positive;

@RestController
@RequestMapping("/api/jobcategory")
@RequiredArgsConstructor
@Validated
public class JobCategoryController {
    private final JobCategoryService jobCategoryService;
    private final JobCategoryMapper mapper;

    // 구인구직 카테고리 등록
    @Secured("ROLE_ADMIN")
    @PostMapping("/new")
    public ResponseEntity postJobCategory(@Valid @RequestBody JobCategoryDto.Post post) {
        JobCategory jobCategory = mapper.jobCategoryPostDtoToJobCategory(post);
        JobCategory createdJobCategory = jobCategoryService.createJobCategory(jobCategory);

        return new ResponseEntity<>(mapper.jobCategoryToJobCategoryResponseDto(createdJobCategory), HttpStatus.CREATED);
    }

    // 구인구직 카테고리 수정
    @Secured("ROLE_ADMIN")
    @PatchMapping("/{jobCategoryId}")
    public ResponseEntity patchJobCategory(@PathVariable("jobCategoryId") Long jobCategoryId,
                                           @Valid @RequestBody JobCategoryDto.Patch patch) {
        JobCategory jobCategory = mapper.jobCategoryPatchDtoToJobCategory(patch);
        jobCategory.setJobCategoryId(jobCategoryId);
        JobCategory updatedJobCategory = jobCategoryService.updateJobCategory(jobCategory, jobCategoryId);

        return new ResponseEntity<>(mapper.jobCategoryToJobCategoryResponseDto(updatedJobCategory), HttpStatus.OK);
    }


}
