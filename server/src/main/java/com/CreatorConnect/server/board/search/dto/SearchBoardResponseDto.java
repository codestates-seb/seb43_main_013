package com.CreatorConnect.server.board.search.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@NoArgsConstructor
@Getter
@Setter
public class SearchBoardResponseDto implements SearchResponseDto {
    private String boardType;
    private Long id;
    private String title;
    private String content;
    private Long commentCount;
    private Long likeCount;
    private Long viewCount;
    private String categoryName;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;
    private Long memberId;
    private String profileImageUrl;

    @Override
    public String getEmail() {
        return null;
    }

    @Override
    public String getName() {
        return null;
    }

    @Override
    public String getNickname() {
        return null;
    }

}
