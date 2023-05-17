package com.CreatorConnect.server.board.comments.freecomment.entity;


import com.CreatorConnect.server.audit.Auditable;
import com.CreatorConnect.server.board.comments.common.entity.CommentPK;
import com.CreatorConnect.server.board.recomments.freerecomment.entity.FreeReComment;
import com.CreatorConnect.server.board.freeboard.entity.FreeBoard;
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
public class FreeComment extends Auditable {
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
        if (!this.member.getFreeComments().contains(this)) {
            this.member.getFreeComments().add(this);
        }
    }

    @MapsId("boardId")
    @ManyToOne
    @JoinColumn(name = "free_board_id")
    private FreeBoard freeBoard;
    public long getFreeBoardId() {return freeBoard.getFreeBoardId();}

    public void setFreeBoard(FreeBoard freeBoard) {
        this.freeBoard = freeBoard;
        if (!this.freeBoard.getFreeComments().contains(this)) {
            this.freeBoard.getFreeComments().add(this);
        }
    }

    @OneToMany(mappedBy = "freeComment", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<FreeReComment> freeReComments = new ArrayList<>();

}
