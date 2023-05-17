package com.CreatorConnect.server.board.feedbackboard.entity;

import com.CreatorConnect.server.audit.Auditable;
import com.CreatorConnect.server.board.Board;
import com.CreatorConnect.server.board.categories.category.entity.Category;
import com.CreatorConnect.server.board.comments.comment.entity.FeedbackComment;
import com.CreatorConnect.server.board.categories.feedbackcategory.entity.FeedbackCategory;
import com.CreatorConnect.server.member.bookmark.entity.Bookmark;
import com.CreatorConnect.server.member.entity.Member;
import com.CreatorConnect.server.board.tag.entity.TagToFeedbackBoard;
import com.CreatorConnect.server.member.like.entity.Like;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
public class FeedbackBoard extends Auditable implements Board {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "feedback_Board_id")
    private Long feedbackBoardId;
    @Column(nullable = false)
    private String title;
    @Column
    private String link;
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
    @Column
    private String tag;

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
    //매핑용
    public String getCategoryName() {
        return category.getCategoryName();
    }

    public void setCategory(Category category) {
        this.category = category;
        if (!this.category.getFeedbackBoards().contains(this)) {
            this.category.getFeedbackBoards().add(this);
        }
    }

    @ManyToOne
    @JoinColumn(name = "feedback_category_id")
    private FeedbackCategory feedbackCategory;
    //매핑용
    public String getFeedbackCategoryName() {
        return feedbackCategory.getFeedbackCategoryName();
    }

    public void setFeedbackCategory(FeedbackCategory feedbackCategory) {
        this.feedbackCategory = feedbackCategory;
        if (!this.feedbackCategory.getFeedbackBoards().contains(this)) {
            this.feedbackCategory.getFeedbackBoards().add(this);
        }
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
        if (!this.member.getFeedbackBoards().contains(this)) {
            this.member.getFeedbackBoards().add(this);
        }
    }

    @OneToMany(mappedBy = "feedbackBoard", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<FeedbackComment> feedbackComments = new ArrayList<>();

    @OneToMany(mappedBy = "feedbackBoard", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<TagToFeedbackBoard> tagBoards = new ArrayList<>();

    // FeedbackBoard - Bookmark 일대다 매핑
    @OneToMany(mappedBy = "freeBoard", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<Bookmark> bookmarks = new HashSet<>();

    // FeedbackBoard - Like 일대다 매핑
    @OneToMany(mappedBy = "freeBoard", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<Like> likes = new HashSet<>();

}
