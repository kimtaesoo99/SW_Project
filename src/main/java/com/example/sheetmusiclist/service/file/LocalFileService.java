package com.example.sheetmusiclist.service.file;

import com.example.sheetmusiclist.exception.FileUploadFailureException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.IOException;

@Service
@Slf4j
@PropertySource("classpath:pdf.properties")
public class LocalFileService implements FileService{


    @Value("${upload.pdf.location}")
    private String locationTemp;
    String location = System.getProperty("user.dir") + "/src/main/resources/pdfs/";

    @PostConstruct
    void postConstruct() {
        File dir = new File(location);
        if (!dir.exists()) {
            dir.mkdir();
        }
    }

    @Override
    public void upload(MultipartFile file, String filename) {
        try {
            file.transferTo(new File(location + filename));
            // file.transferTo로 파일을 저장한다.
        } catch(IOException e) {
            throw new FileUploadFailureException();
            // 예외 처리
            // 이에 해당하는 상황은? uniquename으로 만들었는데?
        }
    }

    @Override
    public void delete(String filename) {
        new File(location + filename).delete();
        // File file = new File(location + filename)
        // file.delete()랑 같으 코드
    }
}


