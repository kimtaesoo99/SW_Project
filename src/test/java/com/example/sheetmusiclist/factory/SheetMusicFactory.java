package com.example.sheetmusiclist.factory;

import com.example.sheetmusiclist.entity.member.Member;
import com.example.sheetmusiclist.entity.sheetmusic.SheetMusic;

public class SheetMusicFactory {

    public static SheetMusic createSheetMusic(Member member){
        SheetMusic sheetMusic = new SheetMusic(member,"a","b");
        return sheetMusic;
    }
}
