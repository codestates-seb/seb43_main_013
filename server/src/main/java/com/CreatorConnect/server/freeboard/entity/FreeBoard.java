package com.CreatorConnect.server.freeboard.entity;

import com.CreatorConnect.server.audit.Auditable;
import com.CreatorConnect.server.category.entity.Category;
import com.CreatorConnect.server.member.entity.Member;
import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FreeBoard extends Auditable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long freeBoardId;

    @Column(nullable = false)
    private String title; // 게시글 제목

    @Column(nullable = false)
    private String content; // 게시글 내용

    // 태그(추가 예정)

    @Column
    private long commentCount; // 댓글수

    @Column
    private long likeCount; // 좋아요수

    @Column
    private long viewCount; // 조회수

    @CreatedDate
    @Column(updatable = false, name = "CREATED_AT")
    private LocalDateTime createdAt = LocalDateTime.now();

    @LastModifiedDate
    @Column(name = "MODIFIED_AT")
    private LocalDateTime modifiedAt = LocalDateTime.now();

    // 카테고리 (추가 예정)

    // FreeBoard - Member 다대일 매핑
    @ManyToOne
    @JoinColumn(name = "MEMBER_ID")
    @JsonBackReference
    private Member member;
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
        if (!this.member.getFreeBoards().contains(this)) {
            this.member.getFreeBoards().add(this);
        }
    }

    // FreeBoard - Category 다대일 매핑
    @ManyToOne//(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
    @JoinColumn(name = "CATEGORY_ID")
    @JsonBackReference
    private Category category;

    public long getCategoryId() {
        return category.getCategoryId();
    }

    public String getCategoryName(){
        return category.getCategoryName();
    }

    public void setCategory(Category category) {
        this.category = category;
        if (!this.category.getFreeBoards().contains(this)) {
            this.category.getFreeBoards().add(this);
        }
    }

}
