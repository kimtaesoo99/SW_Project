package com.example.sheetmusiclist.repository.sheetmusic;

import com.example.sheetmusiclist.entity.sheetmusic.SheetMusic;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SheetMusicRepository extends JpaRepository<SheetMusic, Long> {

}
