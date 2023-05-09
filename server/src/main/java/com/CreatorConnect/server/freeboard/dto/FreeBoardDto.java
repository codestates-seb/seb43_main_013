package com.CreatorConnect.server.freeboard.dto;

import com.CreatorConnect.server.category.entity.Category;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

public class FreeBoardDto {
    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    @Setter
    public static class Post {
        @Valid
        @NotNull
        private Long memberId; // 작성자 id

        @Valid
        @NotBlank(message = "게시글의 제목을 입력하세요.")
        private String title; // 게시글 제목

        @Valid
        @NotBlank(message = "내용을 작성하세요.")
        private String content;

        @Valid
        @NotBlank(message = "카테고리를 선택학세요.")
        private String category;

        // 태그 추가 예정
        public void addMemberId(Long memberId) {
            this.memberId = memberId;
        }
    }
    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    @Setter
    public static class Patch{
        @Valid
        private String title;

        @Valid
        private String content;
    }

    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    @Setter
    public static class Response{
        @Positive
        private Long freeboardId;

        private String title;

        private String content;

        private String category;

//        private long memberId;

        // 카테고리 추가 예정

        // 태그 추가 예정
    }
}

