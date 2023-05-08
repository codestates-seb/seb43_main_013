package com.CreatorConnect.server.feedbackboard.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

public class FeedbackBoardResponseDto {
    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Post {
        private String massage;
        private long feedbackBoardId;
    }

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Patch {
        private String massage;
        private long feedbackBoardId;
        private String title;
        private String content;
        private String tag;
        private String category;
        private String feedbackCategory;
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
        private String category;
        private String feedbackCategory;
        private LocalDateTime createdAt;
        private LocalDateTime modifiedAt;
        private long memberId;
        private String nickname;
        private String email;
    }
}
