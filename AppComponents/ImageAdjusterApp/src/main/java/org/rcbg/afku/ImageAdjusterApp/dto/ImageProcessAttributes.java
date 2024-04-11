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

    public ImageProcessAttributes(ImageProcessAttributes attributes){
        this.colorConversion = attributes.getColorConversion();
        this.cropHeight = attributes.getCropHeight();
        this.cropWidth = attributes.getCropWidth();
        this.watermark = attributes.isWatermark();
    }

    @Override
    public String toString(){
        String template = "{\"colorConversion\": \"%s\", \"cropHeight\": %d,\"cropWidth\": %d, \"watermark\": %b}";
        return String.format(template, colorConversion, cropHeight, cropWidth, watermark);
    }
}
