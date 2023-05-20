package com.CreatorConnect.server.board.notice.controller;

import com.CreatorConnect.server.board.notice.dto.NoticeDto;
import com.CreatorConnect.server.board.notice.entity.Notice;
import com.CreatorConnect.server.board.notice.mapper.NoticeMapper;
import com.CreatorConnect.server.board.notice.service.NoticeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
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
    @Secured("ROLE_ADMIN")
    public ResponseEntity postNotice(@RequestBody @Valid NoticeDto.Post post,
                                     @RequestHeader("Authorization") String authorizationToken) {

        Notice notice = noticeService.createNotice(post);

        return new ResponseEntity<>(mapper.noticeToNoticePostResponseDto(notice), HttpStatus.CREATED);
    }

    // 공지사항 수정
    @PatchMapping("/notice/{noticeId}")
    @Secured("ROLE_ADMIN")
    public ResponseEntity patchNotice(@PathVariable("noticeId") @Positive Long noticeId,
                                      @RequestBody @Valid NoticeDto.Patch patch,
                                      @RequestHeader("Authorization") String authorizationToken) {

        Notice notice = noticeService.updateNotice(patch, noticeId);

        return new ResponseEntity<>(mapper.noticeToNoticeResponseDto(notice), HttpStatus.OK);
    }

    // 공지사항 목록
    @GetMapping("/notices")
    public ResponseEntity getNotices(@RequestParam String sort,
                                     @RequestParam int page,
                                     @RequestParam int size) {

        NoticeDto.MultiResponseDto<NoticeDto.Response> response = noticeService.getAllNotices(page, size, sort);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    // 공지사항 상세 조회
    @GetMapping("/notice/{noticeId}")
    public ResponseEntity getNoticeDetail(@PathVariable("noticeId") @Positive Long noticeId) {

        Notice notice = noticeService.getNoticeDetail(noticeId);
        NoticeDto.Response response = mapper.noticeToNoticeResponseDto(notice);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    // 공지사항 삭제
    @DeleteMapping("/notice/{noticeId}")
    @Secured("ROLE_ADMIN")
    public ResponseEntity deleteNotice(@PathVariable("noticeId") @Positive Long noticeId,
                                       @RequestHeader("Authorization") String authorizationToken) {

        noticeService.removeNotice(noticeId);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}