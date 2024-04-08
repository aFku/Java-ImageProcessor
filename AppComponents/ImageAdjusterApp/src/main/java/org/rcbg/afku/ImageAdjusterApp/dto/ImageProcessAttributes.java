package org.rcbg.afku.ImageAdjusterApp.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ImageProcessAttributes {
    private ImageColorConversion colorConversion;
    private int cropHeight;
    private int cropWidth;
    private boolean watermark;
}
