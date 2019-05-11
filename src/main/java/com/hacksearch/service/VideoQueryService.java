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

import com.hacksearch.domain.Video;
import com.hacksearch.domain.*; // for static metamodels
import com.hacksearch.repository.VideoRepository;
import com.hacksearch.service.dto.VideoCriteria;
import com.hacksearch.service.dto.VideoDTO;
import com.hacksearch.service.mapper.VideoMapper;

/**
 * Service for executing complex queries for Video entities in the database.
 * The main input is a {@link VideoCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link VideoDTO} or a {@link Page} of {@link VideoDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class VideoQueryService extends QueryService<Video> {

    private final Logger log = LoggerFactory.getLogger(VideoQueryService.class);

    private final VideoRepository videoRepository;

    private final VideoMapper videoMapper;

    public VideoQueryService(VideoRepository videoRepository, VideoMapper videoMapper) {
        this.videoRepository = videoRepository;
        this.videoMapper = videoMapper;
    }

    /**
     * Return a {@link List} of {@link VideoDTO} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<VideoDTO> findByCriteria(VideoCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Video> specification = createSpecification(criteria);
        return videoMapper.toDto(videoRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link VideoDTO} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<VideoDTO> findByCriteria(VideoCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Video> specification = createSpecification(criteria);
        return videoRepository.findAll(specification, page)
            .map(videoMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(VideoCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Video> specification = createSpecification(criteria);
        return videoRepository.count(specification);
    }

    /**
     * Function to convert VideoCriteria to a {@link Specification}
     */
    private Specification<Video> createSpecification(VideoCriteria criteria) {
        Specification<Video> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), Video_.id));
            }
            if (criteria.getVideoId() != null) {
                specification = specification.and(buildStringSpecification(criteria.getVideoId(), Video_.videoId));
            }
            if (criteria.getTitle() != null) {
                specification = specification.and(buildStringSpecification(criteria.getTitle(), Video_.title));
            }
            if (criteria.getEpisode() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getEpisode(), Video_.episode));
            }
            if (criteria.getSoundcloud() != null) {
                specification = specification.and(buildStringSpecification(criteria.getSoundcloud(), Video_.soundcloud));
            }
            if (criteria.getCaptionIdId() != null) {
                specification = specification.and(buildSpecification(criteria.getCaptionIdId(),
                    root -> root.join(Video_.captionIds, JoinType.LEFT).get(Caption_.id)));
            }
        }
        return specification;
    }
}
