package com.CreatorConnect.server.comment.service;

import com.CreatorConnect.server.comment.dto.CommentDto;
import com.CreatorConnect.server.comment.dto.CommentResponseDto;

public interface CommentService {
    CommentResponseDto.CommentContent createComment(Long id, CommentDto.Post postDto);
    CommentResponseDto.CommentContent updateComment(Long boardId, Long commentId, CommentDto.Patch patchDto);
    CommentResponseDto.Details responseComment(Long boardId, Long commentId);
    CommentResponseDto.Multi<CommentResponseDto.Details> responseComments(Long boardId, int page, int size);
    void deleteComment(Long boardId, Long commentId);

}
