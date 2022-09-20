package com.example.sheetmusiclist.repository.sheetmusic;

import com.example.sheetmusiclist.entity.sheetmusic.SheetMusic;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SheetMusicRepository extends JpaRepository<SheetMusic, Long> {
    List<SheetMusic> findAllByTitleContaining(String searchKeyWord);
    List<SheetMusic> findAllByWriterContaining(String searchKeyWord);
}
