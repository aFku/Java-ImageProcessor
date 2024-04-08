package org.rcbg.afku.ImageAdjusterApp.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProcessedImageDto extends ImageProcessAttributes {
    private Integer imageId;
    private Integer rawImageId;
}
