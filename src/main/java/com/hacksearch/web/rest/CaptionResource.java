package com.hacksearch.web.rest;
import com.hacksearch.service.CaptionService;
import com.hacksearch.web.rest.errors.BadRequestAlertException;
import com.hacksearch.web.rest.util.HeaderUtil;
import com.hacksearch.service.dto.CaptionDTO;
import com.hacksearch.service.dto.CaptionCriteria;
import com.hacksearch.service.CaptionQueryService;
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
 * REST controller for managing Caption.
 */
@RestController
@RequestMapping("/api")
public class CaptionResource {

    private final Logger log = LoggerFactory.getLogger(CaptionResource.class);

    private static final String ENTITY_NAME = "caption";

    private final CaptionService captionService;

    private final CaptionQueryService captionQueryService;

    public CaptionResource(CaptionService captionService, CaptionQueryService captionQueryService) {
        this.captionService = captionService;
        this.captionQueryService = captionQueryService;
    }

    /**
     * POST  /captions : Create a new caption.
     *
     * @param captionDTO the captionDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new captionDTO, or with status 400 (Bad Request) if the caption has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/captions")
    public ResponseEntity<CaptionDTO> createCaption(@RequestBody CaptionDTO captionDTO) throws URISyntaxException {
        log.debug("REST request to save Caption : {}", captionDTO);
        if (captionDTO.getId() != null) {
            throw new BadRequestAlertException("A new caption cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CaptionDTO result = captionService.save(captionDTO);
        return ResponseEntity.created(new URI("/api/captions/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /captions : Updates an existing caption.
     *
     * @param captionDTO the captionDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated captionDTO,
     * or with status 400 (Bad Request) if the captionDTO is not valid,
     * or with status 500 (Internal Server Error) if the captionDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/captions")
    public ResponseEntity<CaptionDTO> updateCaption(@RequestBody CaptionDTO captionDTO) throws URISyntaxException {
        log.debug("REST request to update Caption : {}", captionDTO);
        if (captionDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        CaptionDTO result = captionService.save(captionDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, captionDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /captions : get all the captions.
     *
     * @param criteria the criterias which the requested entities should match
     * @return the ResponseEntity with status 200 (OK) and the list of captions in body
     */
    @GetMapping("/captions")
    public ResponseEntity<List<CaptionDTO>> getAllCaptions(CaptionCriteria criteria) {
        log.debug("REST request to get Captions by criteria: {}", criteria);
        List<CaptionDTO> entityList = captionQueryService.findByCriteria(criteria);
        return ResponseEntity.ok().body(entityList);
    }

    /**
    * GET  /captions/count : count all the captions.
    *
    * @param criteria the criterias which the requested entities should match
    * @return the ResponseEntity with status 200 (OK) and the count in body
    */
    @GetMapping("/captions/count")
    public ResponseEntity<Long> countCaptions(CaptionCriteria criteria) {
        log.debug("REST request to count Captions by criteria: {}", criteria);
        return ResponseEntity.ok().body(captionQueryService.countByCriteria(criteria));
    }

    /**
     * GET  /captions/:id : get the "id" caption.
     *
     * @param id the id of the captionDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the captionDTO, or with status 404 (Not Found)
     */
    @GetMapping("/captions/{id}")
    public ResponseEntity<CaptionDTO> getCaption(@PathVariable Long id) {
        log.debug("REST request to get Caption : {}", id);
        Optional<CaptionDTO> captionDTO = captionService.findOne(id);
        return ResponseUtil.wrapOrNotFound(captionDTO);
    }

    /**
     * DELETE  /captions/:id : delete the "id" caption.
     *
     * @param id the id of the captionDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/captions/{id}")
    public ResponseEntity<Void> deleteCaption(@PathVariable Long id) {
        log.debug("REST request to delete Caption : {}", id);
        captionService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
