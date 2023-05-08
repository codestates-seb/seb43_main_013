package com.CreatorConnect.server.feedbackboard.dto;

import com.CreatorConnect.server.member.entity.Member;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import java.time.LocalDateTime;

public class FeedbackBoardDto {

    @Getter
    @AllArgsConstructor
    public static class Post {
        private long memberId;
        private String title;
        private String link;
        private String content;
        private String tag;
        private String category;
        private String feedbackCategory;

        public Member getMember() {
            Member member = new Member();
            member.setMemberId(memberId);

            return member;
        }
    }

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class PostResponse {
        private String massage;
        private long feedbackBoardId;
    }

    @Getter
    @Setter
    public static class Patch {
        private String title;
        private String link;
        private String content;
        private String tag;
        private String category;
        private String feedbackCategory;
    }

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class PatchResponse {
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
    public static class DetailsResponse {
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
