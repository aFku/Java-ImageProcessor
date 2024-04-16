package org.rcbg.afku.ImageAdjusterApp.dto;


import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ImageProcessAttributes {

    @NotNull(message = "You need to specify your colors")
    private ImageColorConversion colorConversion;

    @NotNull(message = "You need provide height. 0 to not use this function")
    @Min(value = 0, message = "Height cannot be negative")
    private int cropHeight;

    @NotNull(message = "You need provide Width. 0 to not use this function")
    @Min(value = 0, message = "Width cannot be negative")
    private int cropWidth;

    @NotNull(message = "Check if you want watermark")
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
