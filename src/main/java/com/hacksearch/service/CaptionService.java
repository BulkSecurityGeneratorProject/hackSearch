package com.hacksearch.service;

import com.hacksearch.service.dto.CaptionDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing Caption.
 */
public interface CaptionService {

    /**
     * Save a caption.
     *
     * @param captionDTO the entity to save
     * @return the persisted entity
     */
    CaptionDTO save(CaptionDTO captionDTO);

    /**
     * Get all the captions.
     *
     * @return the list of entities
     */
    List<CaptionDTO> findAll();


    /**
     * Get the "id" caption.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<CaptionDTO> findOne(Long id);

    /**
     * Delete the "id" caption.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
