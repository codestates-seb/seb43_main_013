package com.CreatorConnect.server.comment.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

public class CommentResponseDto {

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Post {
        private long commentId;
        private String content;
    }

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Patch {
        private long commentId;
        private String content;
    }

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Details {
        private long commentId;
        private String content;
        private long memberId;
        private String nickname;
        private String email;
        private String profileImageUrl;
        private long reCommentCount;
        private LocalDateTime createdAt;
        private LocalDateTime modifiedAt;
        private List<CommentResponseDto.Multi> reComments;
    }
    @Getter
    @AllArgsConstructor
    public static class Multi<T> {
        private List<T> reComments;
    }
}
