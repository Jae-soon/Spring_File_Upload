package com.ll.exam.fileupload.app.base;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {
    public static String GET_FILE_DIR_PATH; // static이면 Value의 값이 들어가지 않는다. 이 때 setter 사용

    @Value("${custom.genFileDirPath}") // 설정파일에서 원하는 값을 가져와 사용할 수 있는 어노테이션
    public void setFileDirPath(String genFileDirPath) {
        GET_FILE_DIR_PATH = genFileDirPath;
    }
}
