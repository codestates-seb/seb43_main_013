package com.CreatorConnect.server.comment.entity;

import com.CreatorConnect.server.feedbackboard.entity.FeedbackBoard;
import com.CreatorConnect.server.freeboard.entity.FreeBoard;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
public class CommentBoard {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long commentBoardId;

    @ManyToOne
    @JoinColumn(name = "freeBoard_id")
    private FreeBoard freeBoard;
    public long getFreeBoardId() {return freeBoard.getFreeBoardId();}

    @ManyToOne
    @JoinColumn(name = "feedbackBoard_id")
    private FeedbackBoard feedbackBoard;
    public long getFeedBoardId() {return feedbackBoard.getFeedbackBoardId();}

    @ManyToOne
    @JoinColumn(name = "comment_id")
    private Comment comment;

}
