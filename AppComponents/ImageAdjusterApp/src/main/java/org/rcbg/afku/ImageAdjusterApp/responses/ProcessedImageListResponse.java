package org.rcbg.afku.ImageAdjusterApp.responses;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.rcbg.afku.ImageAdjusterApp.responses.data.ProcessedImageResponseData;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class ProcessedImageListResponse extends MetaDataResponse{
    List<ProcessedImageResponseData> data;
}
