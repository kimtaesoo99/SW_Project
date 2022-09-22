package com.example.sheetmusiclist.entity.image;
import com.example.sheetmusiclist.entity.common.EntityDate;
import com.example.sheetmusiclist.entity.sheetmusic.SheetMusic;
import com.example.sheetmusiclist.exception.UnsupportedImageFormatException;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import javax.persistence.*;
import java.util.Arrays;
import java.util.UUID;
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Data
public class Image extends EntityDate {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(nullable = false)
    private String uniqueName;
    @Column(nullable = false)
    private String originName;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sheetMusic_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private SheetMusic sheetMusic;
    private final static String supportedExtension[] = {"jpg", "jpeg", "gif", "bmp", "png"};
    public Image(String originName) {
        // 세팅
        this.originName = originName;
        this.uniqueName = generateUniqueName(extractExtension(originName));
    }

    public void initSheetMusic(SheetMusic sheetMusic) {
        if(this.sheetMusic == null) {
            this.sheetMusic = sheetMusic;
        }
    }
    private String generateUniqueName(String extension) {
        return UUID.randomUUID().toString() + "." + extension;
    }
    private String extractExtension(String originName) {
        try {
            String ext = originName.substring(originName.lastIndexOf(".") + 1);
            if(isSupportedFormat(ext)) return ext;
        } catch (StringIndexOutOfBoundsException e) { }
        throw new UnsupportedImageFormatException();
    }
    private boolean isSupportedFormat(String ext) {
        return Arrays.stream(supportedExtension).anyMatch(e -> e.equalsIgnoreCase(ext));
    }
}
