package com.CreatorConnect.server.board.feedbackboard.dto;

import com.CreatorConnect.server.member.entity.Member;
import com.CreatorConnect.server.board.tag.dto.TagDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

public class FeedbackBoardDto {

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Post {
        private long memberId;
        private String title;
        private String link;
        private String content;
        private String categoryName;
        private String feedbackCategoryName;
        private List<TagDto.TagInfo> tags; // 태그

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
        private String title;
        private String link;
        private String content;
        private String categoryName;
        private String feedbackCategoryName;
        private List<TagDto.TagInfo> tags; // 태그

        public Member getMember() {
            Member member = new Member();
            member.setMemberId(memberId);
            return member;
        }
    }

}
