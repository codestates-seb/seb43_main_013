package com.CreatorConnect.server.feedbackboard.entity;

import com.CreatorConnect.server.audit.Auditable;
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
    private String content;
    @Column
    private Long commentCount;
    @Column
    private Long likeCount;
    @Column
    private Long viewCount;
    @Column
    private String category;
    @Column
    private String feedbackCategory;

    @ManyToOne
    @JoinColumn(name = "member_id")
    @JsonBackReference
    private Member member;
    public long getMemberId() {
        return member.getMemberId();
    }
    public String getNickname() {
        return member.getNickname();
    }

//    Todo FeedbackBoard-tag연결
//    @OneToMany(mappedBy = "feedbackBoard", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
//    private List<TagBoard> tagBoards = new ArrayList<>();

}
