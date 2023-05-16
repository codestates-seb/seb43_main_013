package com.CreatorConnect.server.board.categories.feedbackcategory.entity;

import com.CreatorConnect.server.board.feedbackboard.entity.FeedbackBoard;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FeedbackCategory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long feedbackCategoryId;

    @Column(nullable = false)
    private String feedbackCategoryName; // 카테고리 이름

    @OneToMany(mappedBy = "feedbackCategory", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<FeedbackBoard> feedbackBoards = new ArrayList<>();

}
