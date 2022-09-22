package com.ll.exam.fileupload.app.member.entity;

import com.ll.exam.fileupload.app.base.AppConfig;
import com.ll.exam.fileupload.app.base.entity.BaseEntity;
import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.Column;
import javax.persistence.Entity;
import java.io.File;

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

    public void removeProfileImgOnStorage() {
        if(profileImg == null || profileImg.trim().length() == 0) return;

        String profileImgPath = getProfileImgPath(); // 파일 경로를 가져와

        new File(profileImgPath).delete(); // 해당 경로의 파일을 삭제한다.
    }

    private String getProfileImgPath() { // genFileDirPath는 AppConfig에서 참조한다.
        return AppConfig.GET_FILE_DIR_PATH + "/" + profileImg;
    }
}