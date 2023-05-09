package com.CreatorConnect.server.feedbackcategory.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;

public class FeedbackCategoryDto {
    @AllArgsConstructor
    @Getter
    public static class Post{
        @Valid
        @NotBlank(message = "카테고리를 입력하세요.")
        private String feedbackCategoryName;

    }
    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    @Setter
    public static class Response{
        private String feedbackCategoryName;
    }
}

