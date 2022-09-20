package com.example.sheetmusiclist.entity.sheetmusic;


import com.example.sheetmusiclist.dto.sheetmusic.SheetMusicEditRequestDto;
import com.example.sheetmusiclist.entity.common.EntityDate;
import com.example.sheetmusiclist.entity.image.Image;
import com.example.sheetmusiclist.entity.member.Member;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static java.util.stream.Collectors.toList;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Entity
@Builder
public class SheetMusic extends EntityDate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.NO_ACTION)
    @JoinColumn(name = "member_id")
    private Member member; // 등록한 사람

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String writer;


    @OneToMany(mappedBy = "sheetmusic", cascade = CascadeType.PERSIST, orphanRemoval = true)
    private List<Image> images;

    public SheetMusic(Member member, String title, String writer,  List<Image> images) {
        this.member = member;
        this.title = title;
        this.writer = writer;
        this.images = new ArrayList<>();
        addImages(images);
    }
    private void addImages(List<Image> added) {
        added.forEach(i -> {
            images.add(i);
            i.initSheetMusic(this);
        });
    }

    //수정
    public ImageUpdatedResult update(SheetMusicEditRequestDto req) {
        this.title = req.getTitle();
        this.writer = req.getWriter();
        ImageUpdatedResult result = findImageUpdatedResult(req.getAddedImages(), req.getDeletedImages());
        addImages(result.getAddedImages());
        deleteImages(result.getDeletedImages());
        return result;
    }

    private void deleteImages(List<Image> deleted) {
        deleted.stream().forEach(id -> this.images.remove(id));
    }

    private ImageUpdatedResult findImageUpdatedResult(List<MultipartFile> addedImageFiles, List<Integer> deletedImageIds) {
        List<Image> addedImages = convertImageFilesToImages(addedImageFiles);
        List<Image> deletedImages = convertImageIdsToImages(deletedImageIds);
        return new ImageUpdatedResult(addedImageFiles, addedImages, deletedImages);
    }

    private List<Image> convertImageIdsToImages(List<Integer> imageIds) {
        return imageIds.stream()
                .map(id -> convertImageIdToImage(id))
                .filter(i -> i.isPresent())
                .map(i -> i.get())
                .collect(toList());
    }

    private Optional<Image> convertImageIdToImage(int id) {
        return this.images.stream().filter(i -> i.getId() == (id)).findAny();
    }

    private List<Image> convertImageFilesToImages(List<MultipartFile> imageFiles) {
        return imageFiles.stream().map(imageFile -> new Image(imageFile.getOriginalFilename())).collect(toList());
    }

    @Getter
    @AllArgsConstructor
    public static class ImageUpdatedResult {
        private List<MultipartFile> addedImageFiles;
        private List<Image> addedImages;
        private List<Image> deletedImages;
    }


}
