package com.CreatorConnect.server.board.notice.entity;

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
public class Notice { // 공지사항 엔티티
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long noticeId;

    @Column(nullable = false)
    private String title; // 공지사항 제목

    @Column(nullable = false, columnDefinition = "TEXT")
    private String content; // 공지사항 내용

    @Column
    private Long viewCount; // 조회수

    @ManyToOne // Notice - Member 다대일 매핑
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
        if (!this.member.getNotices().contains(this)) {
            this.member.getNotices().add(this);
        }
    }
}
