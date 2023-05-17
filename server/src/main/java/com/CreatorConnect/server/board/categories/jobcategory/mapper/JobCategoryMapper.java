package com.CreatorConnect.server.board.categories.jobcategory.mapper;

import com.CreatorConnect.server.board.categories.jobcategory.dto.JobCategoryDto;
import com.CreatorConnect.server.board.categories.jobcategory.entity.JobCategory;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface JobCategoryMapper {
    // JobCategoryDto.Post -> JobCategory
    JobCategory jobCategoryPostDtoToJobCategory(JobCategoryDto.Post post);

    // JobCategory -> JobCategoryResponseDto
    JobCategoryDto.Response jobCategoryToJobCategoryResponseDto(JobCategory jobCategory);

    // JobCategoryDto.Patch -> JobCategory
    JobCategory jobCategoryPatchDtoToJobCategory(JobCategoryDto.Patch patch);

    // JobCategory -> List<JobCategoryDto.Response>
    List<JobCategoryDto.Response> jobCategoryToJobCategoryResponseDtos(List<JobCategory> jobCategories);

}
