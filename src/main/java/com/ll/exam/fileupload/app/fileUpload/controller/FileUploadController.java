package com.ll.exam.fileupload.app.fileUpload.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@Controller
@RequestMapping("/upload")
public class FileUploadController {
    @Value("${custom.genFileDirPath}") // 설정파일에서 원하는 값을 가져와 사용할 수 있는 어노테이션
    private String genFileDirPath;

    @RequestMapping("")
    @ResponseBody
    public String upload(@RequestParam("img1")MultipartFile img1) { // 파일을 사용할 떄 MultipartFile 사용
        File file = new File(genFileDirPath + "/1.png"); // 파일을 저장할 위치 지정

        try {
            img1.transferTo(file); // 파일을 미리 지정한 위치에 저장
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return "업로드 완료!!!";
    }
}