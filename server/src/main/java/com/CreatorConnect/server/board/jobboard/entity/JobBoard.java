package com.CreatorConnect.server.board.jobboard.entity;

import com.CreatorConnect.server.audit.Auditable;
import com.CreatorConnect.server.board.Board;
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

    @Column
    private String title; // 게시글 제목

    @Column
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
}
