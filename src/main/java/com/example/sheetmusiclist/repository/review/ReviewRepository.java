package com.example.sheetmusiclist.repository.review;

import com.example.sheetmusiclist.entity.review.Review;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewRepository extends JpaRepository<Review,Long> {
}
