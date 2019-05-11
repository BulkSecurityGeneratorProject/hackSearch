package com.hacksearch.service.impl;

import com.hacksearch.repository.VideoRepositoryExt;
import com.hacksearch.service.VideoServiceExt;
import com.hacksearch.service.mapper.VideoMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing Video.
 */
@Service
@Transactional
public class VideoServiceExtImpl implements VideoServiceExt {

    private final Logger log = LoggerFactory.getLogger(VideoServiceExtImpl.class);

    private final VideoRepositoryExt videoRepositoryExt;

    private final VideoMapper videoMapper;

    public VideoServiceExtImpl(VideoRepositoryExt videoRepositoryExt, VideoMapper videoMapper) {
        this.videoRepositoryExt = videoRepositoryExt;
        this.videoMapper = videoMapper;
    }
    /**
     * Get one video by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    public void setEpisodeInTranslation() {
        log.debug("Request to get Video : {}");
    }
}
