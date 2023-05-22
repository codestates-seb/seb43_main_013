package com.CreatorConnect.server.board.promotionboard.dto;

import com.CreatorConnect.server.board.tag.dto.TagDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

public class PromotionBoardResponseDto {

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class post {
        private long promotionboardId;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Patch {

        private long promotionboardId;
        private String title;
        private String content;
        private String categoryName;
        private List<TagDto.TagInfo> tag;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Details {
        private long promotionboardId;
        private String title;
        private String content;
        private List<TagDto.TagInfo> tag;
        private long commentCount;
        private long likeCount;
        private long viewCount;
        private String categoryName;
        private LocalDateTime createdAt;
        private LocalDateTime modifiedAt;
        private long memberId;
        private String nickname;
        private String email;
        private String profileImageUrl;
    }

    @Data
    @AllArgsConstructor
    public static class Multi<T> {
        private List<T> data;
        private PageInfo pageInfo;
    }

    @Data
    @AllArgsConstructor
    public static class PageInfo {
        private int page;
        private int siza;
        private int totalPages;
        private int totalElements;
    }

}
