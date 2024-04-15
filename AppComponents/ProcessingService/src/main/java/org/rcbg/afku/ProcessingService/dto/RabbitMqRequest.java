package org.rcbg.afku.ProcessingService.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RabbitMqRequest{
    private String rawFilename;
    private ImageProcessAttributes attributes;
}