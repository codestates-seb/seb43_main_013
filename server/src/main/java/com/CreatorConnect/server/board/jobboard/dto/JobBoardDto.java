package com.CreatorConnect.server.board.jobboard.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

public class JobBoardDto {
    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    @Setter
    public static class Post{
        private Long memberId; // 게시글 작성자

        private String title; // 게시글 제목

        private String content; // 게시글 내용

        private String jobCategoryName; // 구인구직 카테고리
    }
}
