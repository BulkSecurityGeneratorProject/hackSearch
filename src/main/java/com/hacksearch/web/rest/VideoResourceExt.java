package com.hacksearch.web.rest;

import com.hacksearch.service.VideoService;
import com.hacksearch.service.dto.VideoDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;

/**
 * REST controller for managing Video.
 */
@RestController
@RequestMapping("/api/extended")
public class VideoResourceExt {

    private final Logger log = LoggerFactory.getLogger(VideoResourceExt.class);

    private static final String ENTITY_NAME = "video";

    private final VideoService videoService;

    public VideoResourceExt(VideoService videoService) {
        this.videoService = videoService;
    }

    @Autowired
    YoutubeAPI youtubeAPI;


    /**
     * SEARCH  /_search/videos?query=:query : search for the video corresponding
     * to the query.
     *
     * @return the result of the search
     */
    @GetMapping("/videos/checkVideos")
    public String searchVideos() {
        youtubeAPI.getNewVideos();
        log.debug("REST request to search new Videos");
        return "Success";
    }

    /**
     * SEARCH  /_search/videos?query=:query : search for the video corresponding
     * to the query.
     *
     * @return the result of the search
     */
    @GetMapping("/videos/checkNewCaptions")
    public String getNewCaptions() {
//        CaptionsGetId captionsGetId = new CaptionsGetId();
        try {
            youtubeAPI.getNewTranslations();
        } catch (IOException e) {
            e.printStackTrace();
        }
        log.debug("REST request to search new Videos");
        return "Success";
    }

    @GetMapping("/videos/checkNewCaptionLines")
    public String getNewCaptionLines() {
//        CaptionsGetId captionsGetId = new CaptionsGetId();
        try {
            youtubeAPI.getNewCaptionsDownload();
        } catch (IOException e) {
            e.printStackTrace();
        }
        log.debug("REST request to search new Videos");
        return "Success";
    }
    @GetMapping("/videos/getYouTubeService")
    public String getYouTubeService() {
//        CaptionsGetId captionsGetId = new CaptionsGetId();
        try {
            youtubeAPI.createService();
        } catch (Exception e) {
            e.printStackTrace();
        }
        log.debug("REST request get Youtube Service");
        return "Success";
    }

    @GetMapping("/videos/setEpisodeInTranslations")
    public String setEpisodeInTranslations() {
//        CaptionsGetId captionsGetId = new CaptionsGetId();
        try {
            youtubeAPI.setEpisodeInTranslations();
        } catch (Exception e) {
            e.printStackTrace();
        }
        log.debug("REST request get Youtube Service");
        return "Success";
    }

    @GetMapping("/videos/checkAll")
    public String checkAll() {
//        CaptionsGetId captionsGetId = new CaptionsGetId();
        try {
            youtubeAPI.checkAll();
        } catch (Exception e) {
            e.printStackTrace();
        }
        log.debug("REST request get Youtube Service");
        return "Success";
    }

    @GetMapping("/videos/videosOrderEpisode")
    public ResponseEntity<List<VideoDTO>> getAllVideos() {
        log.debug("REST request to get all Videos ");
        List<VideoDTO> entityList = videoService.findAllByOrOrderByEpisode();
        return ResponseEntity.ok().body(entityList);
    }
}
