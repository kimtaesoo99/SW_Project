package com.example.sheetmusiclist.dto.sheetmusic;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class SheetMusicEditRequestDto {

    @NotBlank(message = "제목을 입력해주세요.")
    private String title;

    @NotBlank(message = "작곡가를 입력해주세요")
    private String writer;


    private List<MultipartFile> addedImages = new ArrayList<>();

    private List<Integer> deletedImages = new ArrayList<>();


}
