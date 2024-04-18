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
public class ProcessedImageDto extends ImageProcessAttributes implements IFile {

    @JsonIgnore
    private Integer imageId;
    private String processedFilename;
    private String rawFilename;

    @JsonIgnore
    private String ownerUuid;

    public ProcessedImageDto(ProcessedImageDto dto){
        super(dto);
        this.imageId = dto.getImageId();
        this.processedFilename = dto.getProcessedFilename();
        this.ownerUuid = dto.getOwnerUuid();
        this.rawFilename = dto.getRawFilename();
    }

    @JsonIgnore
    @Override
    public String getFilename() {
        return this.processedFilename;
    }
}
