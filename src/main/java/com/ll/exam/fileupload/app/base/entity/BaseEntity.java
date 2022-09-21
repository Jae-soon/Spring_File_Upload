package com.ll.exam.fileupload.app.base.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

import static javax.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PROTECTED;

@Getter
@SuperBuilder
@MappedSuperclass // 엔티티끼리 상속을 위해 사용하는 필수 어노테이션(상속용 엔티티이다 라는 것 증명)
@NoArgsConstructor(access = PROTECTED)
@EntityListeners(AuditingEntityListener.class) // CreateDate, LastModifyDate 어노테이션을 사용하기 위해 필수 -> 실행 위해 application에서 EnableJpaAuditing 어노테이션 적용
@ToString
public class BaseEntity {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;
    @CreatedDate // LocalDateTime.now() 를 자동으로 넣어줌
    private LocalDateTime createDate;
    @LastModifiedDate // 가장 마지막에 수정한 시간을 자동으로 넣어줌
    private LocalDateTime modifyDate;
}