package com.example.sheetmusiclist.entity.sheetmusic;


import com.example.sheetmusiclist.dto.sheetmusic.SheetMusicEditRequestDto;
import com.example.sheetmusiclist.entity.common.EntityDate;
import com.example.sheetmusiclist.entity.pdf.Pdf;
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


    @OneToMany(mappedBy = "sheetMusic", cascade = CascadeType.PERSIST, orphanRemoval = true)
    private List<Pdf> pdfs;

    public SheetMusic(Member member, String title, String writer,  List<Pdf> pdfs) {
        this.member = member;
        this.title = title;
        this.writer = writer;
        this.pdfs = new ArrayList<>();
        addPdfs(pdfs);
    }

    private void addPdfs(List<Pdf> added) {
        added.forEach(i -> {
            pdfs.add(i);
            i.initSheetMusic(this);
        });
    }

    //수정
    public PdfUpdatedResult update(SheetMusicEditRequestDto req) {
        // 수정하기 위해 웹 페이지에서 editrequestdto로 값을 받아오고 title, writer는 바로 받고
        // pdf는 새로 업로드 할 pdf와 삭제할 pdf를 각각 작성해서 처리해준다.

        this.title = req.getTitle();
        this.writer = req.getWriter();
        PdfUpdatedResult result = findPdfUpdatedResult(req.getAddedPdfs(), req.getDeletedPdfs());
        addPdfs(result.getAddedPdfs());
        deletePdfs(result.getDeletedPdfs());
        return result;

    }

    private void deletePdfs(List<Pdf> deleted) {
        deleted.stream().forEach(id -> this.pdfs.remove(id));
    }

    private PdfUpdatedResult findPdfUpdatedResult(List<MultipartFile> addedPdfFiles, List<Integer> deletedPdfIds) {
        List<Pdf> addedPdfs = convertPdfFilesToPdfs(addedPdfFiles);
        List<Pdf> deletedPdfs = convertPdfIdsToPdfs(deletedPdfIds);
        // convertImageIdsToImages로 multipartfile _> Pdf로 변경
        return new PdfUpdatedResult(addedPdfFiles, addedPdfs, deletedPdfs);
    }

    private List<Pdf> convertPdfIdsToPdfs(List<Integer> pdfIds) {
        return pdfIds.stream()
                .map(id -> convertPdfIdToPdf(id))
                .filter(i -> i.isPresent()) // 리턴으로 받은 Optional<Pdf>값이 있는지 비교
                .map(i -> i.get())
                .collect(toList());
    }

    private Optional<Pdf> convertPdfIdToPdf(int id) { //null값 허용(Optional)
        return this.pdfs.stream().filter(i -> i.getId() == (id)).findAny();
        // sheetmusic에 있는 모든 images들을 검색해서 getId()로 전체 다 가져와서 id랑 같은게 있는지 비교하는 건가?
        // findAny()는 Stream에서 가장 먼저 탐색되는 요소를 리턴한다.]
        //fintFirst()와 findAny()의 차이는 findFirst는 여러 요소가 조건에 부합해도 Stream의 순서를 고려해서
        // 가장 앞에 있는 요소를 리턴하자만, findAny는 멀티 쓰레드에서 스트림을 처리할 때 가장 먼저 찾은 요소를
        // 반환한다.
        // id값을 찾았다면 stream종료
    }

    private List<Pdf> convertPdfFilesToPdfs(List<MultipartFile> pdfFiles) {
        return pdfFiles.stream().map(pdfFile -> new Pdf(pdfFile.getOriginalFilename())).collect(toList());
    }

    @Getter
    @AllArgsConstructor
    public static class PdfUpdatedResult {
        private List<MultipartFile> addedPdfFiles; // 추가할 파일()
        private List<Pdf> addedPdfs; // 추가된 파일 // convertImageIdsToImages로 multipartfile _> Pdf로 변경
        private List<Pdf> deletedPdfs; // 삭제 파일
    }


}
