package com.CreatorConnect.server.board.jobboard.entity;

import com.CreatorConnect.server.audit.Auditable;
import com.CreatorConnect.server.board.Board;
import com.CreatorConnect.server.board.categories.jobcategory.entity.JobCategory;
import com.CreatorConnect.server.member.entity.Member;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class JobBoard extends Auditable implements Board {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long jobBoardId;

    @Column(nullable = false)
    private String title; // 게시글 제목

    @Column(nullable = false, columnDefinition = "TEXT")
    private String content; // 게시글 내용

    @Column
    private Long commentCount; // 댓글수

    @Column
    private Long likeCount; // 좋아요수

    @Column
    private Long viewCount; // 조회수

    @ManyToOne // JobBoard - Member 다대일 매핑
    @JoinColumn(name = "MEMBER_ID")
    private Member member;
    public Long getMemberId() {
        return member.getMemberId();
    }
    public String getNickname() {
        return member.getNickname();
    }
    public String getEmail() {return member.getEmail();}
    public String getProfileImageUrl() {return member.getProfileImageUrl();}

    public void setMember(Member member) {
        this.member = member;
        if (!this.member.getJobBoards().contains(this)) {
            this.member.getJobBoards().add(this);
        }
    }

    @ManyToOne // JobBoard - JobCategory 다대일 매핑
    @JoinColumn(name = "JOBCATEGORY_ID")
    private JobCategory jobCategory;

    public String getJobCategoryName() {
        return jobCategory.getJobCategoryName();
    }

    // 매핑
    public void setJobCategory(JobCategory jobCategory) {
        this.jobCategory = jobCategory;
        if (!this.jobCategory.getJobBoards().contains(this)) {
            this.jobCategory.getJobBoards().add(this);
        }
    }

    @PrePersist
    public void prePersist() { // 조회수, 댓글수, 좋아요수가 없으면 0으로 초기화
        this.commentCount = this.commentCount == null ? 0 : this.commentCount;
        this.likeCount = this.likeCount == null ? 0 : this.likeCount;
        this.viewCount = this.viewCount == null ? 0 : this.viewCount;
    }
}
