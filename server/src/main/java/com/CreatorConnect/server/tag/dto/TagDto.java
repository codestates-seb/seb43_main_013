package com.CreatorConnect.server.tag.dto;

import com.CreatorConnect.server.tag.entity.Tag;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;


public class TagDto {
    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Post{
//        private long tagId;

        private String tagName;
    }

}
