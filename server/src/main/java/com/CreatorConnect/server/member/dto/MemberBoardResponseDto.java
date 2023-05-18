package com.CreatorConnect.server.member.dto;

import com.CreatorConnect.server.board.tag.dto.TagDto;
import com.CreatorConnect.server.board.tag.entity.Tag;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class MemberBoardResponseDto {

    private String boardType;
    private Long id;
    private String title;
    private String content;
    private Long commentCount;
    private Long likeCount;
    private Long viewCount;
    private String categoryName;
    private Long memberId;
    private String email;
    private String nickname;
    private String profileImageUrl;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;

}
