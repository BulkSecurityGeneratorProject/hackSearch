package com.hacksearch.service;

import com.hacksearch.service.dto.SearchQueryDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing SearchQuery.
 */
public interface SearchQueryService {

    /**
     * Save a searchQuery.
     *
     * @param searchQueryDTO the entity to save
     * @return the persisted entity
     */
    SearchQueryDTO save(SearchQueryDTO searchQueryDTO);

    /**
     * Get all the searchQueries.
     *
     * @return the list of entities
     */
    List<SearchQueryDTO> findAll();


    /**
     * Get the "id" searchQuery.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<SearchQueryDTO> findOne(Long id);

    /**
     * Delete the "id" searchQuery.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
