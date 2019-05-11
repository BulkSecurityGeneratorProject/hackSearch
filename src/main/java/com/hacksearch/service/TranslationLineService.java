package com.hacksearch.service;

import com.hacksearch.domain.Caption;
import com.hacksearch.service.dto.TranslationLineDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing TranslationLine.
 */
public interface TranslationLineService {

    /**
     * Save a translationLine.
     *
     * @param translationLineDTO the entity to save
     * @return the persisted entity
     */
    TranslationLineDTO save(TranslationLineDTO translationLineDTO);

    /**
     * Get all the translationLines.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<TranslationLineDTO> findAll(Pageable pageable);

    Page<TranslationLineDTO> findByText(String query, String episode,Pageable pageable);

    Integer findByTextCount(String query, String episode);

    /**
     * Get the "id" translationLine.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<TranslationLineDTO> findOne(Long id);

    /**
     * Delete the "id" translationLine.
     *
     * @param id the id of the entity
     */
    void delete(Long id);

    Integer countTranslationLineByCaptionIdId(Caption caption);
}
