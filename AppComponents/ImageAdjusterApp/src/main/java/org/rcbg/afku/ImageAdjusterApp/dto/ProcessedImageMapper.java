package org.rcbg.afku.ImageAdjusterApp.dto;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import org.rcbg.afku.ImageAdjusterApp.domain.ProcessedImage;
import org.rcbg.afku.ImageAdjusterApp.domain.RawImage;

@Mapper
public interface ProcessedImageMapper {

    ProcessedImageMapper INSTANCE = Mappers.getMapper(ProcessedImageMapper.class);

    @Mapping(source = "processedImage.rawImage.rawFilename", target = "rawFilename")
    @Mapping(source = "processedImage.rawImage.ownerUuid", target = "ownerUuid")
    @Mapping(source = "processedImage.processedFilename", target = "processedFilename")
    public ProcessedImageDto toDto(ProcessedImage processedImage);

    @Mapping(source = "attributes.colorConversion", target = "colorConversion")
    @Mapping(source = "attributes.cropHeight", target = "cropHeight")
    @Mapping(source = "attributes.cropWidth", target = "cropWidth")
    @Mapping(source = "attributes.watermark", target = "watermark")
    @Mapping(source = "rawImage", target = "rawImage")
    @Mapping(source = "processedFilename", target = "processedFilename")
    public ProcessedImage toEntity(String processedFilename, RawImage rawImage, ImageProcessAttributes attributes);
}
