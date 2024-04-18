package org.rcbg.afku.ImageAdjusterApp.responses.data;

import lombok.Getter;
import lombok.Setter;
import org.rcbg.afku.ImageAdjusterApp.dto.ProcessedImageDto;
import org.rcbg.afku.ImageAdjusterApp.dto.RawImageDto;

@Getter
@Setter
public class ProcessedImageResponseData extends ProcessedImageDto {
    private final String processedContentUri;
    private final String rawContentUri;

    public ProcessedImageResponseData(ProcessedImageDto dto, String processedUri, String rawUri){
        super(dto);
        this.processedContentUri = processedUri;
        this.rawContentUri = rawUri;
    }
}
