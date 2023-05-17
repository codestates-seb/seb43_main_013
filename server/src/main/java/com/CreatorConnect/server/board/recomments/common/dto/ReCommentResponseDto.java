package com.CreatorConnect.server.board.recomments.common.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

public class ReCommentResponseDto {
    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Post {
        private long recommentId;
    }

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Details {
        private long recommentId;
        private String content;
        private long memberId;
        private String nickname;
        private String email;
        private String profileImageUrl;
        private LocalDateTime createdAt;
        private LocalDateTime modifiedAt;
    }
}
