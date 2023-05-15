package com.CreatorConnect.server.comment.dto;

import com.CreatorConnect.server.member.entity.Member;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

public class CommentDto {

    @Getter
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
    public static class Patch {
        private long memberId;
        private String content;
    }
}
