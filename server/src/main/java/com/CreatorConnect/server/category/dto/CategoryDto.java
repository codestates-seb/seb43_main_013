package com.CreatorConnect.server.category.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;

public class CategoryDto {
    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    @Setter
    public static class Post{
        @Valid
        @NotBlank(message = "카테고리를 입력하세요.")
        private String categoryName;

    }

    @Getter
    @Setter
    public static class Patch {
        @Valid
        @NotBlank(message = "카테고리를 입력하세요.")
        private String categoryName;
    }
}

