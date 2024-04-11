package org.rcbg.afku.ImageAdjusterApp.responses.data;

import lombok.Getter;
import org.rcbg.afku.ImageAdjusterApp.dto.RawImageDto;

@Getter
public class RawImageResponseData extends RawImageDto {
    private final String contentUri;

    public RawImageResponseData(RawImageDto dto, String uri){
        super(dto);
        this.contentUri = uri;
    }
}
