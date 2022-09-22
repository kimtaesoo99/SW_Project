package com.example.sheetmusiclist.repository.sheetmusic;

import com.example.sheetmusiclist.entity.sheetmusic.SheetMusic;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SheetMusicRepository extends JpaRepository<SheetMusic, Long> {
    Page<SheetMusic> findAllByTitleContaining(String searchKeyWord, Pageable pageable);
    Page<SheetMusic> findAllByWriterContaining(String searchKeyWord,Pageable pageable);
}
