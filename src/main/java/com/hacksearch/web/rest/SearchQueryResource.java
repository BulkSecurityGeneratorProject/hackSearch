package com.hacksearch.web.rest;
import com.hacksearch.service.SearchQueryService;
import com.hacksearch.web.rest.errors.BadRequestAlertException;
import com.hacksearch.web.rest.util.HeaderUtil;
import com.hacksearch.service.dto.SearchQueryDTO;
import com.hacksearch.service.dto.SearchQueryCriteria;
import com.hacksearch.service.SearchQueryQueryService;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing SearchQuery.
 */
@RestController
@RequestMapping("/api")
public class SearchQueryResource {

    private final Logger log = LoggerFactory.getLogger(SearchQueryResource.class);

    private static final String ENTITY_NAME = "searchQuery";

    private final SearchQueryService searchQueryService;

    private final SearchQueryQueryService searchQueryQueryService;

    public SearchQueryResource(SearchQueryService searchQueryService, SearchQueryQueryService searchQueryQueryService) {
        this.searchQueryService = searchQueryService;
        this.searchQueryQueryService = searchQueryQueryService;
    }

    /**
     * POST  /search-queries : Create a new searchQuery.
     *
     * @param searchQueryDTO the searchQueryDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new searchQueryDTO, or with status 400 (Bad Request) if the searchQuery has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/search-queries")
    public ResponseEntity<SearchQueryDTO> createSearchQuery(@RequestBody SearchQueryDTO searchQueryDTO) throws URISyntaxException {
        log.debug("REST request to save SearchQuery : {}", searchQueryDTO);
        if (searchQueryDTO.getId() != null) {
            throw new BadRequestAlertException("A new searchQuery cannot already have an ID", ENTITY_NAME, "idexists");
        }
        SearchQueryDTO result = searchQueryService.save(searchQueryDTO);
        return ResponseEntity.created(new URI("/api/search-queries/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /search-queries : Updates an existing searchQuery.
     *
     * @param searchQueryDTO the searchQueryDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated searchQueryDTO,
     * or with status 400 (Bad Request) if the searchQueryDTO is not valid,
     * or with status 500 (Internal Server Error) if the searchQueryDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/search-queries")
    public ResponseEntity<SearchQueryDTO> updateSearchQuery(@RequestBody SearchQueryDTO searchQueryDTO) throws URISyntaxException {
        log.debug("REST request to update SearchQuery : {}", searchQueryDTO);
        if (searchQueryDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        SearchQueryDTO result = searchQueryService.save(searchQueryDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, searchQueryDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /search-queries : get all the searchQueries.
     *
     * @param criteria the criterias which the requested entities should match
     * @return the ResponseEntity with status 200 (OK) and the list of searchQueries in body
     */
    @GetMapping("/search-queries")
    public ResponseEntity<List<SearchQueryDTO>> getAllSearchQueries(SearchQueryCriteria criteria) {
        log.debug("REST request to get SearchQueries by criteria: {}", criteria);
        List<SearchQueryDTO> entityList = searchQueryQueryService.findByCriteria(criteria);
        return ResponseEntity.ok().body(entityList);
    }

    /**
    * GET  /search-queries/count : count all the searchQueries.
    *
    * @param criteria the criterias which the requested entities should match
    * @return the ResponseEntity with status 200 (OK) and the count in body
    */
    @GetMapping("/search-queries/count")
    public ResponseEntity<Long> countSearchQueries(SearchQueryCriteria criteria) {
        log.debug("REST request to count SearchQueries by criteria: {}", criteria);
        return ResponseEntity.ok().body(searchQueryQueryService.countByCriteria(criteria));
    }

    /**
     * GET  /search-queries/:id : get the "id" searchQuery.
     *
     * @param id the id of the searchQueryDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the searchQueryDTO, or with status 404 (Not Found)
     */
    @GetMapping("/search-queries/{id}")
    public ResponseEntity<SearchQueryDTO> getSearchQuery(@PathVariable Long id) {
        log.debug("REST request to get SearchQuery : {}", id);
        Optional<SearchQueryDTO> searchQueryDTO = searchQueryService.findOne(id);
        return ResponseUtil.wrapOrNotFound(searchQueryDTO);
    }

    /**
     * DELETE  /search-queries/:id : delete the "id" searchQuery.
     *
     * @param id the id of the searchQueryDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/search-queries/{id}")
    public ResponseEntity<Void> deleteSearchQuery(@PathVariable Long id) {
        log.debug("REST request to delete SearchQuery : {}", id);
        searchQueryService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
