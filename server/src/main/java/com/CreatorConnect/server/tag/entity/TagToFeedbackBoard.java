package com.CreatorConnect.server.tag.entity;

import com.CreatorConnect.server.feedbackboard.entity.FeedbackBoard;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TagToFeedbackBoard { // 피드백 게시판 - 태그 매핑 테이블
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long feedbackBoardId;

    @ManyToOne
    @JoinColumn(name = "FEEDBACKBOARD_ID")
    private FeedbackBoard feedbackBoard;

    @ManyToOne
    @JoinColumn(name = "TAG_ID")
    private Tag tag;

    public TagToFeedbackBoard(FeedbackBoard feedbackBoard, Tag tag) {
        this.feedbackBoard = feedbackBoard;
        this.tag = tag;
    }
}
