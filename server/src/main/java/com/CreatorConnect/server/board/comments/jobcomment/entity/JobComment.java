package com.CreatorConnect.server.board.comments.jobcomment.entity;

import com.CreatorConnect.server.audit.Auditable;
import com.CreatorConnect.server.board.comments.common.entity.CommentPK;
import com.CreatorConnect.server.board.jobboard.entity.JobBoard;
import com.CreatorConnect.server.board.recomments.jobrecomment.entity.JobReComment;
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
public class JobComment extends Auditable {
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
        if (!this.member.getJobComments().contains(this)) {
            this.member.getJobComments().add(this);
        }
    }
    @MapsId("boardId")
    @ManyToOne
    @JoinColumn(name = "job_board_id")
    private JobBoard jobBoard;

    public void setJobBoard(JobBoard jobBoard) {
        this.jobBoard = jobBoard;
        if (!this.jobBoard.getJobComments().contains(this)) {
            this.jobBoard.getJobComments().add(this);
        }
    }
    @OneToMany(mappedBy = "jobComment", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<JobReComment> jobReComments = new ArrayList<>();
}