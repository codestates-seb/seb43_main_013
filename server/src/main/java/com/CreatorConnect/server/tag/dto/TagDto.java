package com.CreatorConnect.server.tag.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

public class TagDto {
    @Getter
    @NoArgsConstructor
    public static class TagInfo {
        private long tagId;

        @NotBlank
        private String tagName;

        // @QueryProjection


        public TagInfo(long tagId, String tagName) {
            this.tagId = tagId;
            this.tagName = tagName;
        }
    }
}
