package com.CreatorConnect.server.freeboard.entity;

import com.CreatorConnect.server.category.entity.Category;
import com.CreatorConnect.server.member.entity.Member;
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
public class FreeBoard {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long freeboardId;

    @Column(nullable = false)
    private String title; // 게시글 제목

    @Column(nullable = false)
    private String content; // 게시글 내용

    // 태그(추가 예정)

    private Long commentCount = 0L; // 댓글수

    private Long likeCount = 0L; // 좋아요수

    private Long viewCount = 0L; // 조회수

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
    private Member member;

    // FreeBoard - Category 다대일 매핑
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "CATEGORY_ID")
    private Category category;

}
