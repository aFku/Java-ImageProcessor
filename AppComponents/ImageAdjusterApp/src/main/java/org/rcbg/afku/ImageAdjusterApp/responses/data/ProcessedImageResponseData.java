package org.rcbg.afku.ImageAdjusterApp.responses.data;

import org.rcbg.afku.ImageAdjusterApp.dto.ProcessedImageDto;
import org.rcbg.afku.ImageAdjusterApp.dto.RawImageDto;

public class ProcessedImageResponseData extends ProcessedImageDto {
    private final String contentUri;

    public ProcessedImageResponseData(ProcessedImageDto dto, String uri){
        super(dto);
        this.contentUri = uri;
    }
}
