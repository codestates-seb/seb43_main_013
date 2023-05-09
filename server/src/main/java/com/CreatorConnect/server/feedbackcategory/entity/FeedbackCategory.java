package com.CreatorConnect.server.feedbackcategory.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FeedbackCategory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long feedbackCategoryId;

    @Column(nullable = false)
    private String feedbackCategoryName; // 카테고리 이름

//    // category - freeboard 일대다 양방향 매핑
//    @OneToMany(mappedBy = "category")
//    private List<FreeBoard> freeBoards = new ArrayList<>();

}
