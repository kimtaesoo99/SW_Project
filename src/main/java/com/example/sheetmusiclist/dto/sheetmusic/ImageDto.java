
package com.example.sheetmusiclist.dto.sheetmusic;


import com.example.sheetmusiclist.entity.pdf.Pdf;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@AllArgsConstructor
@NoArgsConstructor
@Data
public class ImageDto {
    @ApiModelProperty(notes = "악보 고유 번호", example = "1")
    private int imageId;

    @ApiModelProperty(notes = "악보 고유 이름", example = "사진.jpg")
    private String originName;

    @ApiModelProperty(notes = "악보 변환 이름", example = "123213.jpg")
    private String uniqueName;

    public static ImageDto toDto(Pdf image){
        return new ImageDto(image.getId(),image.getOriginName(),image.getUniqueName());
    }
}