package com.example.sheetmusiclist.dto.review;


import com.example.sheetmusiclist.entity.review.Review;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReviewfindResponseDto {

    private String user;
    private String comment;
    private Integer rate;

    public static ReviewfindResponseDto toDto(Review review) {
        return new ReviewfindResponseDto(review.getMember().getNickname(),
                review.getComment(), review.getRate());
    }
}
