package com.CreatorConnect.server.member.like.entity;

import com.CreatorConnect.server.audit.Auditable;
import com.CreatorConnect.server.feedbackboard.entity.FeedbackBoard;
import com.CreatorConnect.server.freeboard.entity.FreeBoard;
import com.CreatorConnect.server.member.entity.Member;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

import static javax.persistence.FetchType.LAZY;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "liked")
public class Like extends Auditable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long likeId;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "likedmember_id")
    private Member member;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "likedfeedbackboard_id")
    private FeedbackBoard feedbackBoard;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "likedfreeboard_id")
    private FreeBoard freeBoard;

    @Enumerated(EnumType.STRING)
    private BoardType boardType;

    public enum BoardType {
        FREEBOARD,
        FEEDBACKBOARD,
        PROMOTIONBOARD,
        JOBOARD
    }



}
