package com.CreatorConnect.server.board.comments.feedbackcomment.entity;


import com.CreatorConnect.server.audit.Auditable;
import com.CreatorConnect.server.board.comments.common.entity.CommentPK;
import com.CreatorConnect.server.board.feedbackboard.entity.FeedbackBoard;
import com.CreatorConnect.server.board.recomments.feedbackrecomment.entity.FeedbackReComment;
import com.CreatorConnect.server.member.entity.Member;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
public class FeedbackComment extends Auditable {
    @EmbeddedId
    private CommentPK commentPK;
    @Column(nullable = false, columnDefinition = "TEXT")
    private String content;
    @Column
    private Long reCommentCount;
    @Column
    private Long maxReCommentCount;
    @PrePersist
    public void prePersist() {
        this.reCommentCount = this.reCommentCount == null ? 0 : this.reCommentCount;
        this.maxReCommentCount = this.maxReCommentCount == null ? 0 : this.maxReCommentCount;
    }
    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;
    //매핑용
    public long getMemberId() {
        return member.getMemberId();
    }
    public String getNickname() {
        return member.getNickname();
    }
    public String getEmail() {return member.getEmail();}
    public String getProfileImageUrl() {return member.getProfileImageUrl();}
    public void setMember(Member member) {
        this.member = member;
        if (!this.member.getFeedbackComments().contains(this)) {
            this.member.getFeedbackComments().add(this);
        }
    }
    @MapsId("boardId")
    @ManyToOne
    @JoinColumn(name = "feedback_board_id")
    private FeedbackBoard feedbackBoard;

    public void setFeedbackBoard(FeedbackBoard feedbackBoard) {
        this.feedbackBoard = feedbackBoard;
        if (!this.feedbackBoard.getFeedbackComments().contains(this)) {
            this.feedbackBoard.getFeedbackComments().add(this);
        }
    }
    @OneToMany(mappedBy = "feedbackComment", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<FeedbackReComment> feedbackReComments = new ArrayList<>();
}
