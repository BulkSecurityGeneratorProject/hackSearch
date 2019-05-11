package com.hacksearch.web.rest;
import com.hacksearch.service.SearchQueryService;
import com.hacksearch.service.TranslationLineQueryService;
import com.hacksearch.service.TranslationLineService;
import com.hacksearch.service.dto.SearchQueryDTO;
import com.hacksearch.service.dto.TranslationLineCriteria;
import com.hacksearch.service.dto.TranslationLineDTO;
import com.hacksearch.web.rest.errors.BadRequestAlertException;
import com.hacksearch.web.rest.util.HeaderUtil;
import com.hacksearch.web.rest.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing TranslationLine.
 */
@RestController
@RequestMapping("/api")
public class TranslationLineResource {

    @Autowired
    SearchQueryService searchQueryService;

    private final Logger log = LoggerFactory.getLogger(TranslationLineResource.class);

    private static final String ENTITY_NAME = "translationLine";

    private final TranslationLineService translationLineService;

    private final TranslationLineQueryService translationLineQueryService;

    public TranslationLineResource(TranslationLineService translationLineService, TranslationLineQueryService translationLineQueryService) {
        this.translationLineService = translationLineService;
        this.translationLineQueryService = translationLineQueryService;
    }

    /**
     * POST  /translation-lines : Create a new translationLine.
     *
     * @param translationLineDTO the translationLineDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new translationLineDTO, or with status 400 (Bad Request) if the translationLine has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/translation-lines")
    public ResponseEntity<TranslationLineDTO> createTranslationLine(@RequestBody TranslationLineDTO translationLineDTO) throws URISyntaxException {
        log.debug("REST request to save TranslationLine : {}", translationLineDTO);
        if (translationLineDTO.getId() != null) {
            throw new BadRequestAlertException("A new translationLine cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TranslationLineDTO result = translationLineService.save(translationLineDTO);
        return ResponseEntity.created(new URI("/api/translation-lines/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /translation-lines : Updates an existing translationLine.
     *
     * @param translationLineDTO the translationLineDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated translationLineDTO,
     * or with status 400 (Bad Request) if the translationLineDTO is not valid,
     * or with status 500 (Internal Server Error) if the translationLineDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/translation-lines")
    public ResponseEntity<TranslationLineDTO> updateTranslationLine(@RequestBody TranslationLineDTO translationLineDTO) throws URISyntaxException {
        log.debug("REST request to update TranslationLine : {}", translationLineDTO);
        if (translationLineDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        TranslationLineDTO result = translationLineService.save(translationLineDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, translationLineDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /translation-lines : get all the translationLines.
     *
     * @param pageable the pagination information
     * @param criteria the criterias which the requested entities should match
     * @return the ResponseEntity with status 200 (OK) and the list of translationLines in body
     */
    @GetMapping("/translation-lines")
    public ResponseEntity<List<TranslationLineDTO>> getAllTranslationLines(TranslationLineCriteria criteria, Pageable pageable) {
        log.debug("REST request to get TranslationLines by criteria: {}", criteria);
        Page<TranslationLineDTO> page = translationLineQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/translation-lines");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
    * GET  /translation-lines/count : count all the translationLines.
    *
    * @param criteria the criterias which the requested entities should match
    * @return the ResponseEntity with status 200 (OK) and the count in body
    */
    @GetMapping("/translation-lines/count")
    public ResponseEntity<Long> countTranslationLines(TranslationLineCriteria criteria) {
        log.debug("REST request to count TranslationLines by criteria: {}", criteria);
        return ResponseEntity.ok().body(translationLineQueryService.countByCriteria(criteria));
    }

    /**
     * GET  /translation-lines/:id : get the "id" translationLine.
     *
     * @param id the id of the translationLineDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the translationLineDTO, or with status 404 (Not Found)
     */
    @GetMapping("/translation-lines/{id}")
    public ResponseEntity<TranslationLineDTO> getTranslationLine(@PathVariable Long id) {
        log.debug("REST request to get TranslationLine : {}", id);
        Optional<TranslationLineDTO> translationLineDTO = translationLineService.findOne(id);
        return ResponseUtil.wrapOrNotFound(translationLineDTO);
    }

    /**
     * DELETE  /translation-lines/:id : delete the "id" translationLine.
     *
     * @param id the id of the translationLineDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/translation-lines/{id}")
    public ResponseEntity<Void> deleteTranslationLine(@PathVariable Long id) {
        log.debug("REST request to delete TranslationLine : {}", id);
        translationLineService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

//    /**
//     * SEARCH  /_search/translation-lines?query=:query : search for the translationLine corresponding
//     * to the query.
//     *
//     * @param query the query of the translationLine search
//     * @param pageable the pagination information
//     * @return the result of the search
//     */
//    @GetMapping("/_search/translation-lines")
//    public ResponseEntity<List<TranslationLineDTO>> searchTranslationLines(@RequestParam String query, Pageable pageable) {
//        log.debug("REST request to search for a page of TranslationLines for query {}", query);
//        Page<TranslationLineDTO> page = translationLineService.search(query, pageable);
//        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, query);
//        return ResponseEntity.ok().headers(headers).body(page.getContent());
//    }

    @GetMapping("/translation-lines/searchText")
    public ResponseEntity<List<TranslationLineDTO>> searchTranslationLinesText(@RequestParam String query,String episode, Pageable pageable) {
        log.debug("REST request to search for a page of TranslationLines for query {}", query);
        int episodeInt = Integer.parseInt(episode.trim());
        saveQuery(query,episodeInt);
        Page<TranslationLineDTO> page = translationLineService.findByText(query,episode,pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, query);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    @Async
    public void saveQuery(String query,Integer episode){
        SearchQueryDTO searchQuery = new SearchQueryDTO();
        searchQuery.setEpisode(episode);
        searchQuery.setQuery(query);
        ZoneId zone1 = ZoneId.of("Europe/Berlin");
        searchQuery.setCreatedAt(ZonedDateTime.now(zone1));
        searchQueryService.save(searchQuery);

    }

    @GetMapping("/translation-lines/searchTextCount")
    public ResponseEntity<Integer> searchTranslationLinesTextCount(@RequestParam String query, String episode) {
        log.debug("REST request to search for a page of TranslationLines for query {}", query);
        Integer counter = translationLineService.findByTextCount(query,episode);
        return ResponseEntity.ok().body(counter);
    }

}
