package org.rcbg.afku.ImageAdjusterApp.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProcessedImageQueueResponse {
    private String processedImageName;
    private String processStatus;
    private String message;
}
