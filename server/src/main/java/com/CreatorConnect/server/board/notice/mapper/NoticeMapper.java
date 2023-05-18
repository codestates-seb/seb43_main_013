package com.CreatorConnect.server.board.notice.mapper;

import com.CreatorConnect.server.board.notice.dto.NoticeDto;
import com.CreatorConnect.server.board.notice.entity.Notice;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface NoticeMapper {
    // NoticeDto.Post -> Notice
    Notice noticePostDtoToNotice(NoticeDto.Post post);

    // Notice -> NoticeDto.PostResponse
    NoticeDto.PostResponse noticeToNoticePostResponseDto(Notice notice);

    // NoticeDto.Patch -> Notice
    Notice noticePatchDtoToNotice(NoticeDto.Patch patch);

    // Notice -> NoticeDto.Response
    NoticeDto.Response noticeToNoticeResponseDto(Notice notice);
}
