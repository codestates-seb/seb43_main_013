package com.CreatorConnect.server.board.comments.comment.entity;


import com.CreatorConnect.server.audit.Auditable;
import com.CreatorConnect.server.board.freeboard.entity.FreeBoard;
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
public class FreeComment extends Auditable {
    @EmbeddedId
    private CommentPK commentPK;
    @Column(nullable = false, columnDefinition = "TEXT")
    private String content;
    @Column
    private Long reCommentCount;

    @PrePersist
    public void prePersist() {
        this.reCommentCount = this.reCommentCount == null ? 0 : this.reCommentCount;
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
//
//    @OneToMany(mappedBy = "comment", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
//    private List<CommentBoard> commentBoardList = new ArrayList<>();

//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "parent_id")
//    private Comment parent;
//
//    @Builder.Default
//    @OneToMany(mappedBy = "parent", orphanRemoval = true)
//    private List<Comment> children = new ArrayList<>();
//
//    // 부모 댓글 수정
//    public void updateParent(Comment parent){
//        this.parent = parent;
//    }

}
