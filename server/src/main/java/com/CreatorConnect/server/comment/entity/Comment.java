package com.CreatorConnect.server.comment.entity;


import com.CreatorConnect.server.audit.Auditable;
import com.CreatorConnect.server.member.entity.Member;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
public class Comment extends Auditable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long commentId;
    @Column
    private String content;
    @Column
    private Long reCommentCount;

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
        if (!this.member.getComments().contains(this)) {
            this.member.getComments().add(this);
        }
    }

    @OneToMany(mappedBy = "comment", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<CommentBoard> commentBoardList = new ArrayList<>();

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
