package com.ll.exam.fileupload.app.base;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {
    @Value("${custom.genFileDirPath}")
    private String genFileDirPath;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // http://localhost:8010/gen/** -> d:temp/genFileDir/ 에서 찾는다.
        registry.addResourceHandler("/gen/**") // 주소 요청이 gen로 날라왔을 때
                .addResourceLocations("file:///" + genFileDirPath + "/"); // 그 녀석은 지정한 경로에서 찾는다.
    }
}
