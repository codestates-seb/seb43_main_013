package com.CreatorConnect.server.category.controller;

import com.CreatorConnect.server.category.dto.CategoryDto;
import com.CreatorConnect.server.category.entity.Category;
import com.CreatorConnect.server.category.mapper.CategoryMapper;
import com.CreatorConnect.server.category.service.CategoryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/category")
@Validated
public class CategoryController {
    private final CategoryService categoryService;
    private final CategoryMapper mapper;

    public CategoryController(CategoryService categoryService, CategoryMapper mapper) {
        this.categoryService = categoryService;
        this.mapper = mapper;
    }

    @PostMapping("/new")
    public ResponseEntity postCategory(@Valid @RequestBody CategoryDto.Post categoryPost) {
        Category category = mapper.categoryPostDtoToCategory(categoryPost);
        Category createdCategory = categoryService.createCategory(category);

        return new ResponseEntity<>(mapper.categoryToCategoryResponseDto(createdCategory), HttpStatus.CREATED);
    }
}

