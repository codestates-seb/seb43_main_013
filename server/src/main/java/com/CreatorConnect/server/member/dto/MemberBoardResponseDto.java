package com.CreatorConnect.server.member.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class MemberBoardResponseDto {

    private String boardType;
    private Long id;
    private String title;
    private String content;

}
