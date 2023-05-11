package com.CreatorConnect.server.category.mapper;

import com.CreatorConnect.server.category.dto.CategoryDto;
import com.CreatorConnect.server.category.entity.Category;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

@Mapper(componentModel = "spring")
public interface CategoryMapper {
    // CategoryDto.Post -> Category
    Category categoryPostDtoToCategory(CategoryDto.Post post);

    // Category -> CategoryDto.Response
    CategoryDto.Response categoryToCategoryResponseDto(Category category);
}

