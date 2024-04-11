package org.rcbg.afku.ImageAdjusterApp.responses;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.rcbg.afku.ImageAdjusterApp.responses.data.ProcessedImageResponseData;

@Getter
@Setter
@NoArgsConstructor
public class ProcessedImageResponse extends MetaDataResponse{
    private ProcessedImageResponseData data;
}
