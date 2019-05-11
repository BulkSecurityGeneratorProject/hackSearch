package com.hacksearch.service.mapper;

import com.hacksearch.domain.*;
import com.hacksearch.service.dto.VideoDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Video and its DTO VideoDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface VideoMapper extends EntityMapper<VideoDTO, Video> {


    @Mapping(target = "captionIds", ignore = true)
    Video toEntity(VideoDTO videoDTO);

    default Video fromId(Long id) {
        if (id == null) {
            return null;
        }
        Video video = new Video();
        video.setId(id);
        return video;
    }
}
