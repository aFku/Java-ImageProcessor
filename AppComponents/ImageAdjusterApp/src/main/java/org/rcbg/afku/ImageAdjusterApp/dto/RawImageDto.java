package org.rcbg.afku.ImageAdjusterApp.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RawImageDto implements IFile {

    private Integer imageId;
    private String filename;

    @JsonIgnore
    private String ownerUuid;

    public RawImageDto(RawImageDto dto){
        this.imageId = dto.getImageId();
        this.filename = dto.getFilename();
        this.ownerUuid = dto.getOwnerUuid();
    }
}
