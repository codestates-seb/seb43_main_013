package com.CreatorConnect.server.feedbackboard.entity;

import com.CreatorConnect.server.audit.Auditable;
import com.CreatorConnect.server.category.entity.Category;
import com.CreatorConnect.server.feedbackcategory.entity.FeedbackCategory;
import com.CreatorConnect.server.member.entity.Member;
import com.fasterxml.jackson.annotation.JsonBackReference;
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
public class FeedbackBoard extends Auditable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long feedbackBoardId;
    @Column
    private String title;
    @Column
    private String link;
    @Column
    private String content;
    @Column
    private Long commentCount;
    @Column
    private Long likeCount;
    @Column
    private Long viewCount;

    @ManyToOne
    @JoinColumn(name = "category_id")
    @JsonBackReference
    private Category category;
    //매핑용
    public long getCategoryId() {
        return category.getCategoryId();
    }
    public String getCategoryName() {
        return category.getCategoryName();
    }

    @ManyToOne
    @JoinColumn(name = "feedbackcategory_id")
    @JsonBackReference
    private FeedbackCategory feedbackCategory;
    //매핑용
    public long getFeedbackCategoryId() {
        return feedbackCategory.getFeedbackCategoryId();
    }
    public String getFeedbackCategoryName() {
        return feedbackCategory.getFeedbackCategoryName();
    }


    @ManyToOne
    @JoinColumn(name = "member_id")
    @JsonBackReference
    private Member member;
    //매핑용
    public long getMemberId() {
        return member.getMemberId();
    }
    public String getNickname() {
        return member.getNickname();
    }

    public void setMember(Member member) {
        this.member = member;
        if (!this.member.getFeedbackBoards().contains(this)) {
            this.member.getFeedbackBoards().add(this);
        }
    }
//    Todo FeedbackBoard-tag연결
//    @OneToMany(mappedBy = "feedbackBoard", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
//    private List<TagBoard> tagBoards = new ArrayList<>();

}
