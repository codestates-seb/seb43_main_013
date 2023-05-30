package com.CreatorConnect.server.board.recomments.promotionrecomment.entity;

import com.CreatorConnect.server.audit.Auditable;
import com.CreatorConnect.server.board.comments.promotioncomment.entity.PromotionComment;
import com.CreatorConnect.server.board.recomments.common.entity.ReCommentPK;
import com.CreatorConnect.server.member.entity.Member;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
public class PromotionReComment extends Auditable {
    @EmbeddedId
    private ReCommentPK reCommentPK;
    @Column(nullable = false, columnDefinition = "TEXT")
    private String content;
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
        if (!this.member.getPromotionReComments().contains(this)) {
            this.member.getPromotionReComments().add(this);
        }
    }

    @MapsId("commentPK")
    @ManyToOne
    @JoinColumns({
            @JoinColumn(name = "promotion_board_id"),
            @JoinColumn(name = "promo_comment_id")
    })
    private PromotionComment promotionComment;
    public void setPromotionComment(PromotionComment promotionComment){
        this.promotionComment = promotionComment;
        if (!this.promotionComment.getPromotionReComments().contains(this)) {
            this.promotionComment.getPromotionReComments().add(this);
        }
    }
}
