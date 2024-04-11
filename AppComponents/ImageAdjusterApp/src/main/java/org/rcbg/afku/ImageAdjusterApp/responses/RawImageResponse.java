package org.rcbg.afku.ImageAdjusterApp.responses;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.rcbg.afku.ImageAdjusterApp.responses.data.RawImageResponseData;

@Getter
@Setter
@NoArgsConstructor
public class RawImageResponse extends MetaDataResponse{
    private RawImageResponseData data;
}
