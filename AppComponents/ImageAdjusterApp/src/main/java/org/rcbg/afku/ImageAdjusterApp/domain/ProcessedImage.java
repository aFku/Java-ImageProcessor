package org.rcbg.afku.ImageAdjusterApp.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.rcbg.afku.ImageAdjusterApp.dto.ImageColorConversion;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "processedImageRecords")
public class ProcessedImage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer imageId;
    @OneToOne
    private RawImage rawImage;
    private String filename;
    private ImageColorConversion colorConversion;
    private int cropHeight;
    private int cropWidth;
    private boolean watermark;
}
