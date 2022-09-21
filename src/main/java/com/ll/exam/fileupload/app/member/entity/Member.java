package com.ll.exam.fileupload.app.member.entity;

import com.ll.exam.fileupload.app.base.entity.BaseEntity;
import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.Column;
import javax.persistence.Entity;

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@ToString(callSuper = true)
public class Member extends BaseEntity { // 중복되는 컬럼들은 상속받을 수 있다.
    @Column(unique = true)
    private String username;
    private String password;
    private String email;
    private String profileImg;
}