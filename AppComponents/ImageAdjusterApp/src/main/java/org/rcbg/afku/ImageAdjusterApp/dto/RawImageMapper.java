package org.rcbg.afku.ImageAdjusterApp.dto;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.rcbg.afku.ImageAdjusterApp.domain.RawImage;

@Mapper
public interface RawImageMapper {

    RawImageMapper INSTANCE = Mappers.getMapper(RawImageMapper.class);

    public RawImageDto toDto(RawImage rawImage);
}
