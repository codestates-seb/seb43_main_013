package com.CreatorConnect.server.category.service;

import com.CreatorConnect.server.category.entity.Category;
import com.CreatorConnect.server.category.repository.CategoryRepository;
import com.CreatorConnect.server.exception.BusinessLogicException;
import com.CreatorConnect.server.exception.ExceptionCode;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CategoryService {
    private final CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }
    /**
     * <카테고리 등록 - 임시 기능>
     * 1. 카테고리 중복 검사
     * 2. 등록
     */
    public Category createCategory(Category category) {
        // 1. 카테고리 중복 검사
        verifyCategory(category.getCategoryName());

        // 2. 등록
        return categoryRepository.save(category);
    }

    // 카티고리 이름 중복 검사 메서드
    private void verifyCategory(String categoryName) {
        Optional<Category> optionalCategory = categoryRepository.findByCategoryName(categoryName);
        if (optionalCategory.isPresent()) {
            throw new BusinessLogicException(ExceptionCode.CATEGORY_EXISTS);
        }
    }
}

