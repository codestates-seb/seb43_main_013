package com.CreatorConnect.server.freeboard.entity;

import com.CreatorConnect.server.member.Member;
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
public class FreeBoard {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long freeboardId;

    @Column(nullable = false)
    private String title; // 게시글 제목

    @Column(nullable = false)
    private String content; // 게시글 내용

    // 태그(추가 예정)

    private long commentCount; // 댓글수

    private long likeCount; // 좋아요수

    private long viewCount; // 조회수

    // 카테고리 (추가 예정)

    @ManyToOne
    @JoinColumn(name = "MEMBER_ID")
    private Member member;

}
