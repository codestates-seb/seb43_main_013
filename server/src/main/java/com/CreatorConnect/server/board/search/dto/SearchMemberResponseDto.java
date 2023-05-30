package com.CreatorConnect.server.board.search.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@NoArgsConstructor
@Getter
@Setter
public class SearchMemberResponseDto implements SearchResponseDto {
    private String boardType;
    private Long id;
    private Long memberId;
    private String email;
    private String name;
    private String nickname;
    private String profileImageUrl;
    private String introduction;
    private int followers;
    private int followings;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;

    @Override
    public String getTitle() {
        return null;
    }

    @Override
    public String getContent() {
        return null;
    }

    @Override
    public Long getCommentCount() {
        return null;
    }

    @Override
    public Long getLikeCount() {
        return null;
    }

    @Override
    public Long getViewCount() {
        return null;
    }

    @Override
    public String getCategoryName() {
        return null;
    }
}
