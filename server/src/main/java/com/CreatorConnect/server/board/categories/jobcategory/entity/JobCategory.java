package com.CreatorConnect.server.board.categories.jobcategory.entity;

import com.CreatorConnect.server.board.jobboard.entity.JobBoard;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class JobCategory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long jobCategoryId;

    @Column
    private String jobCategoryName; // 구인구직 카테고리 이름

    @OneToMany(mappedBy = "jobCategory", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<JobBoard> jobBoards = new ArrayList<>();
}
