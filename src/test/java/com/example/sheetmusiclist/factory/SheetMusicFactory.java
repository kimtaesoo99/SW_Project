package com.example.sheetmusiclist.factory;

import com.example.sheetmusiclist.entity.image.Image;
import com.example.sheetmusiclist.entity.member.Member;
import com.example.sheetmusiclist.entity.sheetmusic.SheetMusic;

import java.util.ArrayList;
import java.util.List;

import static com.example.sheetmusiclist.factory.ImageFactory.createImage;

public class SheetMusicFactory {

    public static SheetMusic createSheetMusic(Member member) {
        List<Image> images = new ArrayList<>();
        images.add(createImage());
        return new SheetMusic(member, "제목", "내용", images);
    }

    public static SheetMusic createSheetMusicWithImages(Member member, List<Image> images) {
        return new SheetMusic(member, "제목", "내용", images);
    }
}
