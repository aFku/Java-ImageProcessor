package org.rcbg.afku.ImageAdjusterApp.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RawImageDto{

    private Integer imageId;
    private String ownerUuid;
    private String filename;

}
