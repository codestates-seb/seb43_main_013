package com.CreatorConnect.server.feedbackboard.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

public class FeedbackBoardMultiDto {
    @Getter
    @AllArgsConstructor
    public static class Response<T> {
        private List<T> data;
        private PageInfo pageInfo;
    }
    @Getter
    @AllArgsConstructor
    public static class PageInfo {
        private int page;
        private int size;
        private long totalElements;
        private int totalPages;
    }
}
