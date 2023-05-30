package com.CreatorConnect.server.board.search.dto;

import java.time.LocalDateTime;

public interface SearchResponseDto {
    String getBoardType();

    Long getId();

    Long getMemberId();

    String getEmail();

    String getName();

    String getNickname();

    String getProfileImageUrl();

    LocalDateTime getCreatedAt();

    LocalDateTime getModifiedAt();

    String getTitle();

    String getContent();

    Long getCommentCount();

    Long getLikeCount();

    Long getViewCount();

    String getCategoryName();
}
