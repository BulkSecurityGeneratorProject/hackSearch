package com.hacksearch.service.impl;

import com.hacksearch.service.SearchQueryService;
import com.hacksearch.domain.SearchQuery;
import com.hacksearch.repository.SearchQueryRepository;
import com.hacksearch.service.dto.SearchQueryDTO;
import com.hacksearch.service.mapper.SearchQueryMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing SearchQuery.
 */
@Service
@Transactional
public class SearchQueryServiceImpl implements SearchQueryService {

    private final Logger log = LoggerFactory.getLogger(SearchQueryServiceImpl.class);

    private final SearchQueryRepository searchQueryRepository;

    private final SearchQueryMapper searchQueryMapper;

    public SearchQueryServiceImpl(SearchQueryRepository searchQueryRepository, SearchQueryMapper searchQueryMapper) {
        this.searchQueryRepository = searchQueryRepository;
        this.searchQueryMapper = searchQueryMapper;
    }

    /**
     * Save a searchQuery.
     *
     * @param searchQueryDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public SearchQueryDTO save(SearchQueryDTO searchQueryDTO) {
        log.debug("Request to save SearchQuery : {}", searchQueryDTO);
        SearchQuery searchQuery = searchQueryMapper.toEntity(searchQueryDTO);
        searchQuery = searchQueryRepository.save(searchQuery);
        return searchQueryMapper.toDto(searchQuery);
    }

    /**
     * Get all the searchQueries.
     *
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<SearchQueryDTO> findAll() {
        log.debug("Request to get all SearchQueries");
        return searchQueryRepository.findAll().stream()
            .map(searchQueryMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one searchQuery by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<SearchQueryDTO> findOne(Long id) {
        log.debug("Request to get SearchQuery : {}", id);
        return searchQueryRepository.findById(id)
            .map(searchQueryMapper::toDto);
    }

    /**
     * Delete the searchQuery by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete SearchQuery : {}", id);
        searchQueryRepository.deleteById(id);
    }
}
