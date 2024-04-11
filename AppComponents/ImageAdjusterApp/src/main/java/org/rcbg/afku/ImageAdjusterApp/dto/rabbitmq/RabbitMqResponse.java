package org.rcbg.afku.ImageAdjusterApp.dto.rabbitmq;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RabbitMqResponse extends RabbitMqRequest{
    private String processedFilename;
    private ProcessStatus processStatus;
}
