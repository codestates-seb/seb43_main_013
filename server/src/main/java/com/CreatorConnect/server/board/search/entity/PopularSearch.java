package com.CreatorConnect.server.board.search.entity;

import com.CreatorConnect.server.audit.Auditable;
import com.CreatorConnect.server.audit.LocalDateTimeConverter;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.time.LocalDateTime;

@NoArgsConstructor
@Getter
@Setter
@Entity
public class PopularSearch extends Auditable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long popularSearchId;

    @Column
    private String keyword;

    @Column
    private int searchCount = 0;

    @LastModifiedDate
    @Column(name = "LAST_UPDATED_AT")
    @Convert(converter = LocalDateTimeConverter.class)
    private LocalDateTime modifiedAt;

}
