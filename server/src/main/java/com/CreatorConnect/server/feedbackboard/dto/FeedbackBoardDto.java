package com.CreatorConnect.server.feedbackboard.dto;

import com.CreatorConnect.server.member.entity.Member;
import com.CreatorConnect.server.tag.dto.TagDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

public class FeedbackBoardDto {

    @Getter
    @AllArgsConstructor
    public static class Post {
        private long memberId;
        private String title;
        private String link;
        private String content;
//        private String tag;
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
    public static class Patch {
        private String title;
        private String link;
        private String content;
//        private String tag;
        private String categoryName;
        private String feedbackCategoryName;
        private List<TagDto.TagInfo> tags; // 태그
    }

}
