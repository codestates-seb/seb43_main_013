package com.CreatorConnect.server.category.service;

import com.CreatorConnect.server.category.dto.CategoryDto;
import com.CreatorConnect.server.category.dto.CategoryResponseDto;
import com.CreatorConnect.server.category.entity.Category;
import com.CreatorConnect.server.category.mapper.CategoryMapper;
import com.CreatorConnect.server.category.repository.CategoryRepository;
import com.CreatorConnect.server.exception.BusinessLogicException;
import com.CreatorConnect.server.exception.ExceptionCode;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Transactional
@Service
public class CategoryService {
    private final CategoryRepository categoryRepository;
    private final CategoryMapper mapper;

    /**
     * <카테고리 등록 - 임시 기능>
     * 1. 카테고리 중복 검사
     * 2. 등록
     */
    public Category createCategory(Category category) {
        // 1. 카테고리 중복 검사
        verifyExistsCategory(category.getCategoryName());

        // 2. 등록
        return categoryRepository.save(category);
    }

    // 패치
    public CategoryResponseDto.Patch updateCategory(Long categoryId, CategoryDto.Patch patchDto){
        Category category = mapper.categoryPatchDtoToCategory(patchDto);
        category.setCategoryId(categoryId);
        Category foundCategory = findVerifiedCategory(category.getCategoryId());

        Optional.ofNullable(category.getCategoryName())
                .ifPresent(foundCategory::setCategoryName);

        Category updatedCategory = categoryRepository.save(foundCategory);
        CategoryResponseDto.Patch responseDto = mapper.categoryToCategoryPatchResponse(updatedCategory);
        return responseDto;
    }

    //조회
    public CategoryResponseDto.Details responseCategory(Long CategoryId){
        Category foundCategory = findVerifiedCategory(CategoryId);
        return mapper.categoryToCategoryDetailsResponse(foundCategory);
    }

    //목록 조회
    public  List<CategoryResponseDto.Details> responseCategorys(){
        PageRequest pageRequest = PageRequest.of(0, 100, Sort.by("categoryId").descending());
        Page<Category> categorysPage = categoryRepository.findAll(pageRequest);
        List<CategoryResponseDto.Details> responses = mapper.categorysToCategoryDetailsResponses(categorysPage.getContent());
        return responses;
    }

    //삭제
    public void deleteCategory(Long categoryId) {
        Category category = findVerifiedCategory(categoryId);
        categoryRepository.delete(category);
    }

    // 카티고리 이름 중복 검사 메서드
    private void verifyExistsCategory(String name) {
        Optional<Category> optionalCategory = categoryRepository.findByCategoryName(name);
        if (optionalCategory.isPresent()) {
            throw new BusinessLogicException(ExceptionCode.CATEGORY_EXISTS);
        }
    }

    // 카테고리 이름을 통해 해당 카테고리의 id 추출
    public long findCategoryId(String name) {
        Optional<Category> optionalCategory = categoryRepository.findByCategoryName(name);

        // 카테고리 이름이 존재하지 않는 경우
        if (!optionalCategory.isPresent()) {
            throw new BusinessLogicException(ExceptionCode.CATEGORY_NOT_FOUND);
        }

        Long categoryId = optionalCategory.get().getCategoryId();

        return categoryId;
    }

    // 카테고리 존재 여부 검증 메서드
    public void verifyCategory(String name) {
        long categoryId = findCategoryId(name);
        Optional<Category> optionalCategory = categoryRepository.findById(categoryId);

        if (!optionalCategory.isPresent()) {
            throw new BusinessLogicException(ExceptionCode.CATEGORY_NOT_FOUND);
        }
    }
    // 카테고리 조회 메서드
    public Category findVerifiedCategory(Long categoryId){
        Optional<Category> optionalCategory = categoryRepository.findById(categoryId);
        Category category = optionalCategory.orElseThrow(() -> new BusinessLogicException(ExceptionCode.CATEGORY_NOT_FOUND));
        return  category;
    }
}