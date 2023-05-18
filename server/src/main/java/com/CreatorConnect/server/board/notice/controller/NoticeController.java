package com.CreatorConnect.server.board.notice.controller;

import com.CreatorConnect.server.board.notice.dto.NoticeDto;
import com.CreatorConnect.server.board.notice.entity.Notice;
import com.CreatorConnect.server.board.notice.mapper.NoticeMapper;
import com.CreatorConnect.server.board.notice.service.NoticeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Positive;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@Validated
public class NoticeController {
    private final NoticeService noticeService;
    private final NoticeMapper mapper;

    // 공지사항 등록
    @PostMapping("/notice/new")
    public ResponseEntity postNotice(@RequestBody @Valid NoticeDto.Post post,
                                     @RequestHeader("Authorization") String authorizationToken) {
        String token = authorizationToken.substring(7);
        Notice notice = noticeService.createNotice(post);

        return new ResponseEntity<>(mapper.noticeToNoticePostResponseDto(notice), HttpStatus.CREATED);
    }

    // 공지사항 수정
    @PatchMapping("/notice/{noticeId}")
    public ResponseEntity patchNotice(@PathVariable("noticeId") @Positive Long noticeId,
                                      @RequestBody @Valid NoticeDto.Patch patch,
                                      @RequestHeader("Authorization") String authorizationToken) {
        String token = authorizationToken.substring(7);
        Notice notice = noticeService.updateNotice(patch, noticeId);

        return new ResponseEntity<>(mapper.noticeToNoticeResponseDto(notice), HttpStatus.OK);
    }
}