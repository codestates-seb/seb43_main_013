package com.CreatorConnect.server.board.feedbackboard.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

public class FeedbackBoardResponseDto {
    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Post {
        private long feedbackBoardId;
    }

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Patch {
        private long feedbackBoardId;
        private String title;
        private String content;
        private String tag;
        private String categoryName;
        private String feedbackCategoryName;
    }

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Details {
        private Long feedbackBoardId;
        private String title;
        private String link;
        private String content;
        private String tag;
        private Long commentCount;
        private Long likeCount;
        private Long viewCount;
        private String categoryName;
        private String feedbackCategoryName;
        private LocalDateTime createdAt;
        private LocalDateTime modifiedAt;
        private long memberId;
        private String nickname;
        private String email;
        private String profileImageUrl;
    }

    @Getter
    @AllArgsConstructor
    public static class Multi<T> {
        private List<T> data;
        private PageInfo pageInfo;
    }

    @Getter
    @AllArgsConstructor
    public static class PageInfo {
        private int page;
        private int size;
        private long totalElements;
        private int totalPages;
    }
}
