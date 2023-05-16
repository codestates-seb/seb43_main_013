package com.CreatorConnect.server.board.comments.comment.dto;

import com.CreatorConnect.server.member.entity.Member;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

public class CommentDto {

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Post {
        private long memberId;
        private String content;
        public Member getMember() {
            Member member = new Member();
            member.setMemberId(memberId);
            return member;
        }
    }

    @Getter
    @Setter
    @NoArgsConstructor
    public static class Patch {
        private long memberId;
        private String content;
    }
}
