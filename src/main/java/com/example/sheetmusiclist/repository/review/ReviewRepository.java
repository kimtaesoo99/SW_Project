package com.example.sheetmusiclist.repository.review;

import com.example.sheetmusiclist.entity.review.Review;
import com.example.sheetmusiclist.entity.sheetmusic.SheetMusic;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review,Long> {

    List<Review> findAllBySheetmusic(SheetMusic sheetMusic);
}
