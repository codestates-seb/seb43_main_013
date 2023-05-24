package com.CreatorConnect.server.board.promotionboard.dto;

import com.CreatorConnect.server.board.tag.dto.TagDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import java.time.LocalDateTime;
import java.util.List;

public class PromotionBoardResponseDto {

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Post {
        private long promotionboardId;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Patch {

        private long promotionBoardId;
        private String title;
        private String content;
        private String categoryName;
        private List<TagDto.TagInfo> tags;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Details {
        private long promotionBoardId;
        private String title;
        private String content;
        private List<TagDto.TagInfo> tags;
        private long commentCount;
        private long likeCount;
        private long viewCount;
        private String categoryName;
        private String link;
        private String channelName;
        private String subscriberCount;
        private LocalDateTime createdAt;
        private LocalDateTime modifiedAt;
        private long memberId;
        private String nickname;
        private String email;
        private String profileImageUrl;
        private Boolean bookmarked = false;
        private Boolean liked = false;
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
        private long totalElements;
        private int totalPages;
    }

}
