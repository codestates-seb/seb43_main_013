package com.CreatorConnect.server.board.comments.common.dto;

import com.CreatorConnect.server.board.recomments.common.dto.ReCommentResponseDto;
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
        private long recommentCount;
        private LocalDateTime createdAt;
        private LocalDateTime modifiedAt;
        private List<ReCommentResponseDto.Details> recomments;
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
