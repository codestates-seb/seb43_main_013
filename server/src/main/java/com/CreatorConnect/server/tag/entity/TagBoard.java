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
public class TagBoard {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long tagBoardId;

    @ManyToOne
    @JoinColumn(name = "FREEBOARD_ID")
    private FreeBoard freeBoard;

    @ManyToOne
    @JoinColumn(name = "FEEDBACKBOARD_ID")
    private FeedbackBoard feedbackBoard;

    @ManyToOne
    @JoinColumn(name = "TAG_ID")
    private Tag tag;


}