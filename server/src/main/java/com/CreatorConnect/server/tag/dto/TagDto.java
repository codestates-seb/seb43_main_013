package com.CreatorConnect.server.tag.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


public class TagDto {
    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class TagInfo {
//        private long tagId;

        private String tagName;
    }


}
