package org.rcbg.afku.ImageAdjusterApp.dto.rabbitmq;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.rcbg.afku.ImageAdjusterApp.dto.ImageProcessAttributes;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RabbitMqRequest{
    private String rawFilename;
    private ImageProcessAttributes attributes;
}
