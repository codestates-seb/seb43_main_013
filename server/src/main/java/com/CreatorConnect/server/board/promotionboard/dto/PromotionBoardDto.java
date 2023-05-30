package com.CreatorConnect.server.board.promotionboard.dto;

import com.CreatorConnect.server.board.tag.dto.TagDto;
import com.CreatorConnect.server.member.entity.Member;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

public class PromotionBoardDto {

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Post {
        @Valid
        @NotNull
        private Long memberId;
        @Valid
        @NotBlank(message = "게시글의 제목을 입력하세요.")
        private String title;
        @Valid
        @NotBlank(message = "링크를 입력하세요.")
        private String link;
        @Valid
        @NotBlank(message = "채널명을 입력하세요.")
        private String channelName;
        private String subscriberCount;

        @Valid
        @NotBlank(message = "내용을 입력하세요.")
        private String content;

        private String categoryName;
        private List<TagDto.TagInfo> tags;

        public Member getMember() {
            Member member = new Member();
            member.setMemberId(memberId);
            return member;
        }
    }


    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Patch {
        private long memberId;
        private String title;
        private String link;
        private String channelName;
        private String subscriberCount;
        private String content;
        private String categoryName;
        private List<TagDto.TagInfo> tags;

        public Member getMember() {
            Member member = new Member();
            member.setMemberId(memberId);
            return member;
        }
    }
}

