package org.rcbg.afku.ImageAdjusterApp.responses;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.rcbg.afku.ImageAdjusterApp.dto.rabbitmq.RabbitMqRequest;

@Getter
@Setter
@NoArgsConstructor
public class RabbitMqRequestResponse extends MetaDataResponse{
    private RabbitMqRequest data;
}
