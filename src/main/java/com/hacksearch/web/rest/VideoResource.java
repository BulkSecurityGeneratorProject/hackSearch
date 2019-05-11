package com.hacksearch.web.rest;
import com.hacksearch.service.VideoService;
import com.hacksearch.web.rest.errors.BadRequestAlertException;
import com.hacksearch.web.rest.util.HeaderUtil;
import com.hacksearch.service.dto.VideoDTO;
import com.hacksearch.service.dto.VideoCriteria;
import com.hacksearch.service.VideoQueryService;
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
 * REST controller for managing Video.
 */
@RestController
@RequestMapping("/api")
public class VideoResource {

    private final Logger log = LoggerFactory.getLogger(VideoResource.class);

    private static final String ENTITY_NAME = "video";

    private final VideoService videoService;

    private final VideoQueryService videoQueryService;

    public VideoResource(VideoService videoService, VideoQueryService videoQueryService) {
        this.videoService = videoService;
        this.videoQueryService = videoQueryService;
    }

    /**
     * POST  /videos : Create a new video.
     *
     * @param videoDTO the videoDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new videoDTO, or with status 400 (Bad Request) if the video has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/videos")
    public ResponseEntity<VideoDTO> createVideo(@RequestBody VideoDTO videoDTO) throws URISyntaxException {
        log.debug("REST request to save Video : {}", videoDTO);
        if (videoDTO.getId() != null) {
            throw new BadRequestAlertException("A new video cannot already have an ID", ENTITY_NAME, "idexists");
        }
        VideoDTO result = videoService.save(videoDTO);
        return ResponseEntity.created(new URI("/api/videos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /videos : Updates an existing video.
     *
     * @param videoDTO the videoDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated videoDTO,
     * or with status 400 (Bad Request) if the videoDTO is not valid,
     * or with status 500 (Internal Server Error) if the videoDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/videos")
    public ResponseEntity<VideoDTO> updateVideo(@RequestBody VideoDTO videoDTO) throws URISyntaxException {
        log.debug("REST request to update Video : {}", videoDTO);
        if (videoDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        VideoDTO result = videoService.save(videoDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, videoDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /videos : get all the videos.
     *
     * @param criteria the criterias which the requested entities should match
     * @return the ResponseEntity with status 200 (OK) and the list of videos in body
     */
    @GetMapping("/videos")
    public ResponseEntity<List<VideoDTO>> getAllVideos(VideoCriteria criteria) {
        log.debug("REST request to get Videos by criteria: {}", criteria);
        List<VideoDTO> entityList = videoQueryService.findByCriteria(criteria);
        return ResponseEntity.ok().body(entityList);
    }

    /**
    * GET  /videos/count : count all the videos.
    *
    * @param criteria the criterias which the requested entities should match
    * @return the ResponseEntity with status 200 (OK) and the count in body
    */
    @GetMapping("/videos/count")
    public ResponseEntity<Long> countVideos(VideoCriteria criteria) {
        log.debug("REST request to count Videos by criteria: {}", criteria);
        return ResponseEntity.ok().body(videoQueryService.countByCriteria(criteria));
    }

    /**
     * GET  /videos/:id : get the "id" video.
     *
     * @param id the id of the videoDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the videoDTO, or with status 404 (Not Found)
     */
    @GetMapping("/videos/{id}")
    public ResponseEntity<VideoDTO> getVideo(@PathVariable Long id) {
        log.debug("REST request to get Video : {}", id);
        Optional<VideoDTO> videoDTO = videoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(videoDTO);
    }

    /**
     * DELETE  /videos/:id : delete the "id" video.
     *
     * @param id the id of the videoDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/videos/{id}")
    public ResponseEntity<Void> deleteVideo(@PathVariable Long id) {
        log.debug("REST request to delete Video : {}", id);
        videoService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
