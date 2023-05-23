package com.CreatorConnect.server.board.promotionboard.entity;

import com.CreatorConnect.server.board.categories.category.entity.Category;
import com.CreatorConnect.server.board.tag.dto.TagDto;
import com.CreatorConnect.server.member.entity.Member;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PromotionBoard {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long memberId;
    @Column(nullable = false)
    private String title;
    @Column(nullable = false)
    private String link;
    @Column
    private String channelName;
    @Column
    private String subscriberCount;
    @Column
    private String content;
    @Column
    private Long commnetCount;
    @Column
    private Long maxCommentCount;
    @Column
    private Long likeCount;
    @Column
    private Long viewCount;

    @PrePersist
    public void prePersist() {
        this.commnetCount = this.commnetCount == null ? 0 : this.commnetCount;
        this.maxCommentCount = this.maxCommentCount == null ? 0 : this.maxCommentCount;
        this.likeCount = this.likeCount == null ? 0 : this.likeCount;
        this.viewCount = this.viewCount == null ? 0 : this.viewCount;
    }

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    public Category getCategory() {
        return category;
    }

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

}
