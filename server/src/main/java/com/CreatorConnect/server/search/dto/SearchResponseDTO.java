package com.CreatorConnect.server.search.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SearchResponseDTO {
    private String boardType;
    private Long id;
    private String title;
    private String content;

}
