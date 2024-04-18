package org.rcbg.afku.ImageAdjusterApp.responses.data;

import lombok.Getter;
import lombok.Setter;
import org.rcbg.afku.ImageAdjusterApp.dto.RawImageDto;

@Getter
@Setter
public class RawImageResponseData extends RawImageDto {
    private final String rawContentUri;

    public RawImageResponseData(RawImageDto dto, String rawUri){
        super(dto);
        this.rawContentUri = rawUri;
    }
}
