package com.CreatorConnect.server.board.comments.recomment.entity;

import com.CreatorConnect.server.board.comments.comment.entity.CommentPK;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;


@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReCommentPK implements Serializable  {
    private CommentPK commentPK;
    @Column(name = "re_comment_id")
    private Long reCommentId;
}


