package com.CreatorConnect.server.board.recomments.freerecomment.entity;

import com.CreatorConnect.server.audit.Auditable;
import com.CreatorConnect.server.board.comments.freecomment.entity.FreeComment;
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
public class FreeReComment extends Auditable {
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
        if (!this.member.getFreeReComments().contains(this)) {
            this.member.getFreeReComments().add(this);
        }
    }
    @MapsId("commentPK")
    @ManyToOne
    @JoinColumns({
            @JoinColumn(name = "free_board_id"),
            @JoinColumn(name = "free_comment_id")
    })
    private FreeComment freeComment;
    public void setFreeComment(FreeComment freeComment){
        this.freeComment = freeComment;
        if (!this.freeComment.getFreeReComments().contains(this)) {
            this.freeComment.getFreeReComments().add(this);
        }
    }
}