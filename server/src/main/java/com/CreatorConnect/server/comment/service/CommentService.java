package com.CreatorConnect.server.comment.service;

import com.CreatorConnect.server.comment.dto.CommentDto;
import com.CreatorConnect.server.comment.dto.CommentResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class CommentService {
    public CommentResponseDto.Post createComment(long id, CommentDto.Post postDto) {
        return new CommentResponseDto.Post();
    }
}
