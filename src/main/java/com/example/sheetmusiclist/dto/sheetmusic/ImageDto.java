package com.example.sheetmusiclist.dto.sheetmusic;


import com.example.sheetmusiclist.entity.image.Image;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@AllArgsConstructor
@NoArgsConstructor
@Data
public class ImageDto {
    private int imageId;
    private String originName;
    private String uniqueName;

    public static ImageDto toDto(Image image){
        return new ImageDto(image.getId(),image.getOriginName(),image.getUniqueName());
    }
}
