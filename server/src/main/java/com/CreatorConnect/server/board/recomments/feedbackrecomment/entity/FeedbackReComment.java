package com.CreatorConnect.server.board.recomments.feedbackrecomment.entity;

import com.CreatorConnect.server.audit.Auditable;
import com.CreatorConnect.server.board.comments.feedbackcomment.entity.FeedbackComment;
import com.CreatorConnect.server.board.recomments.common.entity.ReCommentPK;
import com.CreatorConnect.server.member.entity.Member;
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
public class FeedbackReComment extends Auditable {
    @EmbeddedId
    private ReCommentPK reCommentPK;
    @Column(nullable = false, columnDefinition = "TEXT")
    private String content;
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
        if (!this.member.getFeedbackReComments().contains(this)) {
            this.member.getFeedbackReComments().add(this);
        }
    }

    @MapsId("commentPK")
    @ManyToOne
    @JoinColumns({
            @JoinColumn(name = "feedback_board_id"),
            @JoinColumn(name = "feedback_comment_id")
    })
    private FeedbackComment feedbackComment;
    public void setFeedbackComment(FeedbackComment feedbackComment){
        this.feedbackComment = feedbackComment;
        if (!this.feedbackComment.getFeedbackReComments().contains(this)) {
            this.feedbackComment.getFeedbackReComments().add(this);
        }
    }
}
