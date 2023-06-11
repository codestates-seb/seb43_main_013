package com.CreatorConnect.server.audit;

import lombok.Getter;
import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import java.time.LocalDateTime;

@Getter
@MappedSuperclass // JPA에서 엔티티 클래스 간에 공통 매핑 정보를 공유하기 위해 사용하는 어노테이션 , 하위 클래스에서 상속
@EntityListeners(AuditingEntityListener.class) // 변경 이벤트를 처리하기 위해 AuditingEntityListener 를 등록
public abstract class Auditable { // JPA 엔티티 클래스에서 생성일과 최종 수정일을 자동으로 관리하는 추상클래스

    @CreatedDate
    @Column(name = "CREATED_AT", updatable = false)
    @Convert(converter = LocalDateTimeConverter.class)
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(name = "LAST_UPDATED_AT")
    @Convert(converter = LocalDateTimeConverter.class)
    private LocalDateTime modifiedAt;

}
