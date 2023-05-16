package com.CreatorConnect.server.comment.entity;

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
public class CommentPK implements Serializable {
    private Long boardId;
    @Column(name = "comment_id")
    private Long commentId;

}