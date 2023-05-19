package com.CreatorConnect.server.board.categories.category.controller;

import com.CreatorConnect.server.board.categories.category.dto.CategoryDto;
import com.CreatorConnect.server.board.categories.category.dto.CategoryResponseDto;
import com.CreatorConnect.server.board.categories.category.entity.Category;
import com.CreatorConnect.server.board.categories.category.mapper.CategoryMapper;
import com.CreatorConnect.server.board.categories.category.service.CategoryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import java.util.List;

@RestController
@RequestMapping("/api")
@Validated
public class CategoryController {
    private final CategoryService categoryService;
    private final CategoryMapper mapper;

    public CategoryController(CategoryService categoryService, CategoryMapper mapper) {
        this.categoryService = categoryService;
        this.mapper = mapper;
    }
    @Secured("ROLE_ADMIN")
    @PostMapping("/category/new")
    public ResponseEntity postCategory(@Valid @RequestBody CategoryDto.Post categoryPost) {

        Category category = mapper.categoryPostDtoToCategory(categoryPost);
        Category createdCategory = categoryService.createCategory(category);

        return new ResponseEntity<>(mapper.categoryToCategoryResponseDto(createdCategory), HttpStatus.CREATED);
    }

    @Secured("ROLE_ADMIN")
    @PatchMapping("/category/{categoryId}")
    public ResponseEntity<CategoryResponseDto.Patch> patchCategory(@PathVariable("categoryId") Long categoryId,
                                                                   @Valid @RequestBody CategoryDto.Patch patchDto){

        CategoryResponseDto.Patch response = categoryService.updateCategory(categoryId, patchDto);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/category/{categoryId}")
    public ResponseEntity<CategoryResponseDto.Details> getCategory(@PathVariable("categoryId") @Positive Long categoryId){

        CategoryResponseDto.Details response = categoryService.responseCategory(categoryId);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/categories")
    public ResponseEntity getCategorys() {

        List<CategoryResponseDto.Details> response = categoryService.responseCategorys();

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Secured("ROLE_ADMIN")
    @DeleteMapping("/category/{categoryId}")
    public ResponseEntity<HttpStatus> deleteCategory(@PathVariable("categoryId") @Positive Long categoryId) {

        categoryService.deleteCategory(categoryId);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}

