package com.CreatorConnect.server.board.promotionboard.entity;

import com.CreatorConnect.server.audit.Auditable;
import com.CreatorConnect.server.board.Board;
import com.CreatorConnect.server.board.categories.category.entity.Category;
import com.CreatorConnect.server.board.comments.promotioncomment.entity.PromotionComment;
import com.CreatorConnect.server.board.tag.dto.TagDto;
import com.CreatorConnect.server.board.tag.entity.TagToFeedbackBoard;
import com.CreatorConnect.server.board.tag.entity.TagToPromotionBoard;
import com.CreatorConnect.server.member.bookmark.entity.Bookmark;
import com.CreatorConnect.server.member.entity.Member;
import com.CreatorConnect.server.member.like.entity.Like;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
public class PromotionBoard extends Auditable implements Board {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long promotionBoardId;
    @Column(nullable = false)
    private String title;
    @Column(nullable = false)
    private String link;
    @Column
    private String channelName;
    @Column
    private String subscriberCount;
    @Column(nullable = false, columnDefinition = "TEXT")
    private String content;
    @Column
    private Long commentCount;
    @Column
    private Long maxCommentCount;
    @Column
    private Long likeCount;
    @Column
    private Long viewCount;

    @PrePersist
    public void prePersist() {
        this.commentCount = this.commentCount == null ? 0 : this.commentCount;
        this.maxCommentCount = this.maxCommentCount == null ? 0 : this.maxCommentCount;
        this.likeCount = this.likeCount == null ? 0 : this.likeCount;
        this.viewCount = this.viewCount == null ? 0 : this.viewCount;
    }

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    public String getCategoryName() { return category.getCategoryName();}

    public void setCategory(Category category) {
        this.category = category;
        if (!this.category.getPromotionBoards().contains(this)) {
            this.category.getPromotionBoards().add(this);
        }
    }

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    public long getMemberId() {
        return member.getMemberId();
    }

    public String getNickname() {
        return member.getNickname();
    }

    public String getEmail() {
        return member.getEmail();
    }

    public String getProfileImageUrl() {
        return member.getProfileImageUrl();
    }

    public void setMember(Member member) {
        this.member = member;
        if (!this.member.getPromotionBoards().contains(this)) {
            this.member.getPromotionBoards().add(this);
        }
    }

    @OneToMany(mappedBy = "promotionBoard", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PromotionComment> promotionComments = new ArrayList<>();

    @OneToMany(mappedBy = "promotionBoard", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<TagToPromotionBoard> tagBoards = new ArrayList<>();

    // promotionBoard - Bookmark 일대다 매핑
    @OneToMany(mappedBy = "promotionBoard", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<Bookmark> bookmarks = new HashSet<>();

    // promotionBoard - Like 일대다 매핑
    @OneToMany(mappedBy = "promotionBoard", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<Like> likes = new HashSet<>();


}
