package com.hacksearch.service;

import com.hacksearch.service.dto.VideoDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing Video.
 */
public interface VideoService {

    /**
     * Save a video.
     *
     * @param videoDTO the entity to save
     * @return the persisted entity
     */
    VideoDTO save(VideoDTO videoDTO);

    /**
     * Get all the videos.
     *
     * @return the list of entities
     */
    List<VideoDTO> findAll();


    /**
     * Get the "id" video.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<VideoDTO> findOne(Long id);

    /**
     * Delete the "id" video.
     *
     * @param id the id of the entity
     */
    void delete(Long id);

    List<VideoDTO> findAllByOrOrderByEpisode();
}
