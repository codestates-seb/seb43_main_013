package com.CreatorConnect.server.board.tag.dto;

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
        private long tagId;

        private String tagName;
    }

//    @Getter
//    @Setter
//    @AllArgsConstructor
//    @NoArgsConstructor
//    public static class TagResponse{
//        private long tagId;
//
//        private String tagName;
//    }


}
