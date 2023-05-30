package com.CreatorConnect.server.member.bookmark.entity;

import com.CreatorConnect.server.audit.Auditable;
import com.CreatorConnect.server.board.feedbackboard.entity.FeedbackBoard;
import com.CreatorConnect.server.board.freeboard.entity.FreeBoard;
import com.CreatorConnect.server.board.jobboard.entity.JobBoard;
import com.CreatorConnect.server.board.promotionboard.entity.PromotionBoard;
import com.CreatorConnect.server.member.entity.Member;
import com.CreatorConnect.server.member.like.entity.Like;
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
public class Bookmark extends Auditable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long bookmarkId;

    @ManyToOne
    @JoinColumn(name = "bookmarkedmember_id")
    private Member member;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "bookmarkedfeedbackboard_id")
    private FeedbackBoard feedbackBoard;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "bookmarkedfreeboard_id")
    private FreeBoard freeBoard;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "bookmarkedjobboard_id")
    private JobBoard jobBoard;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "bookmarkedpromotionboard_id")
    private PromotionBoard promotionBoard;
    @Enumerated(EnumType.STRING)
    private BoardType boardType;

    public enum BoardType {
        FREEBOARD,
        FEEDBACKBOARD,
        PROMOTIONBOARD,
        JOBBOARD
    }

}