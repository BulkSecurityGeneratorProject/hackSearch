package com.hacksearch.service.mapper;

import com.hacksearch.domain.*;
import com.hacksearch.service.dto.CaptionDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Caption and its DTO CaptionDTO.
 */
@Mapper(componentModel = "spring", uses = {VideoMapper.class})
public interface CaptionMapper extends EntityMapper<CaptionDTO, Caption> {

    @Mapping(source = "videoId.id", target = "videoIdId")
    CaptionDTO toDto(Caption caption);

    @Mapping(target = "translationLines", ignore = true)
    @Mapping(source = "videoIdId", target = "videoId")
    Caption toEntity(CaptionDTO captionDTO);

    default Caption fromId(Long id) {
        if (id == null) {
            return null;
        }
        Caption caption = new Caption();
        caption.setId(id);
        return caption;
    }
}
