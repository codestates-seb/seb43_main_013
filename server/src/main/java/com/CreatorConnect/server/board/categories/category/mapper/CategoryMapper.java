package com.CreatorConnect.server.board.categories.category.mapper;

import com.CreatorConnect.server.board.categories.category.dto.CategoryResponseDto;
import com.CreatorConnect.server.board.categories.category.dto.CategoryDto;
import com.CreatorConnect.server.board.categories.category.entity.Category;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CategoryMapper {
    // CategoryDto.Post -> Category
    Category categoryPostDtoToCategory(CategoryDto.Post post);

    // Category -> CategoryDto.Response
    CategoryResponseDto.Post categoryToCategoryResponseDto(Category category);

    Category categoryPatchDtoToCategory(CategoryDto.Patch categoryPatchDto);
    CategoryResponseDto.Patch categoryToCategoryPatchResponse(Category category);
    CategoryResponseDto.Details categoryToCategoryDetailsResponse(Category category);
    List<CategoryResponseDto.Details> categorysToCategoryDetailsResponses (List<Category> categorys);
}

