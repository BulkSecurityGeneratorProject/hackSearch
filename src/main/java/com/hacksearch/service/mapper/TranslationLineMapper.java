package com.hacksearch.service.mapper;

import com.hacksearch.domain.*;
import com.hacksearch.service.dto.TranslationLineDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity TranslationLine and its DTO TranslationLineDTO.
 */
@Mapper(componentModel = "spring", uses = {CaptionMapper.class})
public interface TranslationLineMapper extends EntityMapper<TranslationLineDTO, TranslationLine> {

    @Mapping(source = "captionId.id", target = "captionIdId")
    TranslationLineDTO toDto(TranslationLine translationLine);

    @Mapping(source = "captionIdId", target = "captionId")
    TranslationLine toEntity(TranslationLineDTO translationLineDTO);

    default TranslationLine fromId(Long id) {
        if (id == null) {
            return null;
        }
        TranslationLine translationLine = new TranslationLine();
        translationLine.setId(id);
        return translationLine;
    }
}
