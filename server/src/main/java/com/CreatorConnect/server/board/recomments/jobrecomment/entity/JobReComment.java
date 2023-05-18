package com.CreatorConnect.server.board.recomments.jobrecomment.entity;

import com.CreatorConnect.server.audit.Auditable;
import com.CreatorConnect.server.board.comments.jobcomment.entity.JobComment;
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
public class JobReComment extends Auditable {
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
        if (!this.member.getJobReComments().contains(this)) {
            this.member.getJobReComments().add(this);
        }
    }

    @MapsId("commentPK")
    @ManyToOne
    @JoinColumns({
            @JoinColumn(name = "job_board_id"),
            @JoinColumn(name = "job_comment_id")
    })
    private JobComment jobComment;
    public void setJobComment(JobComment jobComment){
        this.jobComment = jobComment;
        if (!this.jobComment.getJobReComments().contains(this)) {
            this.jobComment.getJobReComments().add(this);
        }
    }
}