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

    @JsonIgnore
    private Integer imageId;

    private String rawFilename;

    @JsonIgnore
    private String ownerUuid;

    public RawImageDto(RawImageDto dto){
        this.imageId = dto.getImageId();
        this.rawFilename = dto.getRawFilename();
        this.ownerUuid = dto.getOwnerUuid();
    }

    @JsonIgnore
    @Override
    public String getFilename() {
        return this.rawFilename;
    }
}
