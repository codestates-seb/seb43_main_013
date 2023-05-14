package com.CreatorConnect.server.tag.entity;

import com.CreatorConnect.server.feedbackboard.entity.FeedbackBoard;
import com.CreatorConnect.server.freeboard.entity.FreeBoard;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TagToFreeBoard { // 자유 게시판 - 태그 매핑 테이블
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long tagBoardId;

    @ManyToOne
    @JoinColumn(name = "FREEBOARD_ID")
    private FreeBoard freeBoard;

    // 나머지 게시판 매핑 추가 예정

//    @ManyToOne
//    @JoinColumn(name = "FEEDBACKBOARD_ID")
//    private FeedbackBoard feedbackBoard;

    @ManyToOne
    @JoinColumn(name = "TAG_ID")
    private Tag tag;

    public TagToFreeBoard(FreeBoard freeBoard, Tag tag) {
        this.freeBoard = freeBoard;
        this.tag = tag;
    }
}