package com.CreatorConnect.server.board.comments.promotioncomment.entity;

import com.CreatorConnect.server.audit.Auditable;
import com.CreatorConnect.server.board.comments.common.entity.CommentPK;
import com.CreatorConnect.server.board.promotionboard.entity.PromotionBoard;
import com.CreatorConnect.server.board.recomments.promotionrecomment.entity.PromotionReComment;
import com.CreatorConnect.server.member.entity.Member;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
public class PromotionComment extends Auditable {

    @EmbeddedId
    private CommentPK commentPK;
    @Column(nullable = false, columnDefinition = "TEXT")
    private String content;
    @Column
    private Long reCommentCount;
    @Column
    private Long maxReCommentCount;
    @PrePersist
    public void prePersist() {
        this.reCommentCount = this.reCommentCount == null ? 0 : this.reCommentCount;
        this.maxReCommentCount = this.maxReCommentCount == null ? 0 : this.maxReCommentCount;
    }
    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;
    //매핑용
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
        if (!this.member.getPromotionComments().contains(this)) {
            this.member.getPromotionComments().add(this);
        }
    }
    @MapsId("boardId")
    @ManyToOne
    @JoinColumn(name = "promotion_board_id")
    private PromotionBoard promotionBoard;

    public void setPromotionBoard(PromotionBoard promotionBoard) {
        this.promotionBoard = promotionBoard;
        if (!this.promotionBoard.getPromotionComments().contains(this)) {
            this.promotionBoard.getPromotionComments().add(this);
        }
    }
    @OneToMany(mappedBy = "promotionComment", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<PromotionReComment> promotionReComments = new ArrayList<>();
}
