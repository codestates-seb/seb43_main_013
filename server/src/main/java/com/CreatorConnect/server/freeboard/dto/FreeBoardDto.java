package com.CreatorConnect.server.freeboard.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.domain.Page;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.time.LocalDateTime;
import java.util.List;

public class FreeBoardDto {
    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    @Setter
    public static class Post {
        @Valid
        @NotNull
        private long memberId; // 작성자 id

        @Valid
        @NotBlank(message = "게시글의 제목을 입력하세요.")
        private String title; // 게시글 제목

        @Valid
        @NotBlank(message = "내용을 작성하세요.")
        private String content;

        @Valid
        @NotNull(message = "카테고리를 선택학세요.")
        // 현재 카테고리 이름을 받아서 id 추출해서 저장하면 Category 테이블에 카테고리가 중복 저장되는 이슈로 인해 request로 categoryId를 받도록 임시조치
        //private long categoryId;
        private String categoryName;

        // 태그 추가 예정
//        public void addMemberId(Long memberId) {
//            this.memberId = memberId;
//        }
    }
    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    @Setter
    public static class Patch{
        private long freeBoardId;
  //      private long memberId;
        @Valid
        private String title;

        @Valid
        private String content;

        @Valid
        private String categoryName;

        // 태그 추가 예정
    }

    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    @Setter
    public static class Response{
        @Positive
        private long freeBoardId;

        private String title;

        private String content;

        private long commentCount;

        private long likeCount;

        private long viewCount;

        // 현재 카테고리 이름을 받아서 id 추출해서 저장하면 Category 테이블에 카테고리가 중복 저장되는 이슈로 인해 request로 categoryId를 받도록 임시조치
        private String categoryName;

        // 태그 추가 예정

        private LocalDateTime createdAt;

        private LocalDateTime modifiedAt;

        private long memberId;

        private String email;

        private String nickname;

        private String profileImageUrl;
    }

    // 페이지네이션 관련 dto
    @Getter
    @NoArgsConstructor
    public static class MultiResponseDto<T> {
        private List<T> data;
        private PageInfo pageInfo;

        public MultiResponseDto(List<T> data, Page page) {
            this.data = data;
            this.pageInfo = new PageInfo(page.getNumber() + 1,
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

