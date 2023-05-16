package com.CreatorConnect.server.board.categories.jobcategory.controller;

import com.CreatorConnect.server.board.categories.jobcategory.dto.JobCategoryDto;
import com.CreatorConnect.server.board.categories.jobcategory.entity.JobCategory;
import com.CreatorConnect.server.board.categories.jobcategory.mapper.JobCategoryMapper;
import com.CreatorConnect.server.board.categories.jobcategory.service.JobCategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/jobcategory")
@RequiredArgsConstructor
public class JobCategoryController {
    private final JobCategoryService jobCategoryService;
    private final JobCategoryMapper mapper;

    // 구인구직 카테고리 등록
    @Secured("ROLE_ADMIN")
    @PostMapping("/new")
    public ResponseEntity postJobCategory(@Valid @RequestBody JobCategoryDto.Post post) {
        JobCategory jobCategory = mapper.jobCategoryPostDtoToJobCategory(post);
        JobCategory createdJobCategory = jobCategoryService.createJobCategory(jobCategory);

        return new ResponseEntity<>(mapper.jobCategoryToJobCategoryResponseDto(createdJobCategory), HttpStatus.OK);

    }


}
