package com.CreatorConnect.server.board.jobboard.dto;

import com.CreatorConnect.server.board.freeboard.dto.FreeBoardDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.domain.Page;

import javax.validation.constraints.Positive;
import java.time.LocalDateTime;
import java.util.List;

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

    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    @Setter
    public static class Patch{
        private Long memberId; // 게시글 작성자

        private Long jobBoardId; // 게시글 id

        private String title; // 게시글 제목

        private String content; // 게시글 내용

        private String jobCategoryName; // 구인구직 카테고리
    }

    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    @Setter
    public static class PostResponse{
        private Long jobBoardId;
    }

    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    @Setter
    public static class Response{
        @Positive
        private Long jobBoardId;

        private String title; // 게시글 제목

        private String content; // 게시글 내용

        private long commentCount; // 댓글수

        private long likeCount; // 좋아요수

        private long viewCount; // 조회수

        private String jobCategoryName; // 구인구직 카테고리 이름

        private LocalDateTime createdAt;

        private LocalDateTime modifiedAt;

        private Long memberId; // 게시글 작성자 id

        private String nickname; // 작성자 닉네임

        private String email; // 작성자 이메일

        private String profileImageUrl; // 작성자 프로필 이미지

        private Boolean bookmarked = false; // 북마크

        private Boolean liked = false; // 좋아요
    }

    // 페이지네이션 관련 dto
    @Getter
    @NoArgsConstructor
    public static class MultiResponseDto<T> {
        private List<T> data;
        private FreeBoardDto.PageInfo pageInfo;

        public MultiResponseDto(List<T> data, Page page) {
            this.data = data;
            this.pageInfo = new FreeBoardDto.PageInfo(page.getNumber() + 1,
                    page.getSize(), page.getTotalElements(), page.getTotalPages());
        }
    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class PageInfo {
        private int page;
        private int size;
        private long totalElements;
        private int totalPages;
    }
}
