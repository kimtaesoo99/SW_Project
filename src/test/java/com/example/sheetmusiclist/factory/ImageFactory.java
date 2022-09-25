package com.example.sheetmusiclist.factory;

import com.example.sheetmusiclist.entity.pdf.Pdf;
import org.springframework.test.util.ReflectionTestUtils;

public class ImageFactory {
    public static Pdf createImage() {
        return new Pdf("origin_filename.jpg");
    }

    public static Pdf createImageWithOriginName(String originName) {
        return new Pdf(originName);
    }

    public static Pdf createImageWithIdAndOriginName(Long id, String originName) {
        Pdf image = new Pdf(originName);
        ReflectionTestUtils.setField(image, "id", id);
        return image;
    }
}

