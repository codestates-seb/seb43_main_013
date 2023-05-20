package com.CreatorConnect.server.board.notice.dto;

import com.CreatorConnect.server.board.freeboard.dto.FreeBoardDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.domain.Page;

import java.time.LocalDateTime;
import java.util.List;

public class NoticeDto {
    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    @Setter
    public static class Post{
        private Long memberId; // 공지사항 작성자(admin)

        private String title; // 제목

        private String content; // 내용
    }

    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    @Setter
    public static class PostResponse{ // 공지사항 등록 시 응답
        private Long noticeId;

    }

    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    @Setter
    public static class Patch{
        private Long memberId; // 공지사항 작성자(admin)

        private Long noticeId;

        private String title; // 제목

        private String content; // 내용
    }

    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    @Setter
    public static class Response{
        private Long noticeId;

        private String title; // 제목

        private String content; // 내용

        private Long viewCount; // 조회수

        private LocalDateTime createdAt; // 글 작성 시간

        private LocalDateTime modifiedAt; // 글 수정 시간

        private Long memberId; // 게시글 작성자 id

        private String nickname; // 작성자 닉네임

        private String email; // 작성자 이메일

        private String profileImageUrl; // 작성자 프로필 이미지

    }

    // 페이지네이션 관련 dto
    @Getter
    @NoArgsConstructor
    public static class MultiResponseDto<T> {
        private List<T> data;
        private FreeBoardDto.PageInfo pageInfo;

        public MultiResponseDto(List<T> data, Page page) {
            this.data = data;
            this.pageInfo = new FreeBoardDto.PageInfo(page.getNumber() + 1,
                    page.getSize(), page.getTotalElements(), page.getTotalPages());
        }
    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class PageInfo {
        private int page;
        private int size;
        private long totalElements;
        private int totalPages;
    }
}
