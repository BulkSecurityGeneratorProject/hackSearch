package com.hacksearch.service;

import java.util.List;

import javax.persistence.criteria.JoinType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.github.jhipster.service.QueryService;

import com.hacksearch.domain.SearchQuery;
import com.hacksearch.domain.*; // for static metamodels
import com.hacksearch.repository.SearchQueryRepository;
import com.hacksearch.service.dto.SearchQueryCriteria;
import com.hacksearch.service.dto.SearchQueryDTO;
import com.hacksearch.service.mapper.SearchQueryMapper;

/**
 * Service for executing complex queries for SearchQuery entities in the database.
 * The main input is a {@link SearchQueryCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link SearchQueryDTO} or a {@link Page} of {@link SearchQueryDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class SearchQueryQueryService extends QueryService<SearchQuery> {

    private final Logger log = LoggerFactory.getLogger(SearchQueryQueryService.class);

    private final SearchQueryRepository searchQueryRepository;

    private final SearchQueryMapper searchQueryMapper;

    public SearchQueryQueryService(SearchQueryRepository searchQueryRepository, SearchQueryMapper searchQueryMapper) {
        this.searchQueryRepository = searchQueryRepository;
        this.searchQueryMapper = searchQueryMapper;
    }

    /**
     * Return a {@link List} of {@link SearchQueryDTO} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<SearchQueryDTO> findByCriteria(SearchQueryCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<SearchQuery> specification = createSpecification(criteria);
        return searchQueryMapper.toDto(searchQueryRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link SearchQueryDTO} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<SearchQueryDTO> findByCriteria(SearchQueryCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<SearchQuery> specification = createSpecification(criteria);
        return searchQueryRepository.findAll(specification, page)
            .map(searchQueryMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(SearchQueryCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<SearchQuery> specification = createSpecification(criteria);
        return searchQueryRepository.count(specification);
    }

    /**
     * Function to convert SearchQueryCriteria to a {@link Specification}
     */
    private Specification<SearchQuery> createSpecification(SearchQueryCriteria criteria) {
        Specification<SearchQuery> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), SearchQuery_.id));
            }
            if (criteria.getQuery() != null) {
                specification = specification.and(buildStringSpecification(criteria.getQuery(), SearchQuery_.query));
            }
            if (criteria.getEpisode() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getEpisode(), SearchQuery_.episode));
            }
            if (criteria.getCreatedAt() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCreatedAt(), SearchQuery_.createdAt));
            }
        }
        return specification;
    }
}
