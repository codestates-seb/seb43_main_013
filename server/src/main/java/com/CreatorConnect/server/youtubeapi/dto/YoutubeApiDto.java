package com.CreatorConnect.server.youtubeapi.dto;

import lombok.*;

import java.util.List;

public class YoutubeApiDto {
    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Details {
        private String videoCategory;
        private Long videoId;
        private String youtubeUrl;
        private String thumbnailUrl;
        private String title;
        private String youtubeId;
    }

    @Getter
    @AllArgsConstructor
    public static class Multi {
        private List<Details> data;
    }

}
