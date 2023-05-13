package com.CreatorConnect.server.comment.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

public class CommentDto {

    @Getter
    @AllArgsConstructor
    public static class Post {
        private long memberId;
        private String content;
    }

    @Getter
    @Setter
    public static class Patch {
        private long memberId;
        private String content;
    }
}
