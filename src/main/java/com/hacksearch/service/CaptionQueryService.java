package com.hacksearch.service;

import com.hacksearch.domain.Caption;
import com.hacksearch.domain.Caption_;
import com.hacksearch.domain.TranslationLine_;
import com.hacksearch.domain.Video_;
import com.hacksearch.repository.CaptionRepository;
import com.hacksearch.service.dto.CaptionCriteria;
import com.hacksearch.service.dto.CaptionDTO;
import com.hacksearch.service.mapper.CaptionMapper;
import io.github.jhipster.service.QueryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.JoinType;
import java.util.List;

/**
 * Service for executing complex queries for Caption entities in the database.
 * The main input is a {@link CaptionCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link CaptionDTO} or a {@link Page} of {@link CaptionDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class CaptionQueryService extends QueryService<Caption> {

    private final Logger log = LoggerFactory.getLogger(CaptionQueryService.class);

    private final CaptionRepository captionRepository;

    private final CaptionMapper captionMapper;

    public CaptionQueryService(CaptionRepository captionRepository, CaptionMapper captionMapper) {
        this.captionRepository = captionRepository;
        this.captionMapper = captionMapper;
    }

    /**
     * Return a {@link List} of {@link CaptionDTO} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<CaptionDTO> findByCriteria(CaptionCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Caption> specification = createSpecification(criteria);
        return captionMapper.toDto(captionRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link CaptionDTO} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<CaptionDTO> findByCriteria(CaptionCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Caption> specification = createSpecification(criteria);
        return captionRepository.findAll(specification, page)
            .map(captionMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(CaptionCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Caption> specification = createSpecification(criteria);
        return captionRepository.count(specification);
    }

    /**
     * Function to convert CaptionCriteria to a {@link Specification}
     */
    private Specification<Caption> createSpecification(CaptionCriteria criteria) {
        Specification<Caption> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), Caption_.id));
            }
            if (criteria.getCaptionId() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCaptionId(), Caption_.captionId));
            }
            if (criteria.getTrackKind() != null) {
                specification = specification.and(buildStringSpecification(criteria.getTrackKind(), Caption_.trackKind));
            }
            if (criteria.getLanguage() != null) {
                specification = specification.and(buildStringSpecification(criteria.getLanguage(), Caption_.language));
            }
            if (criteria.getTranslationLineId() != null) {
                specification = specification.and(buildSpecification(criteria.getTranslationLineId(),
                    root -> root.join(Caption_.translationLines, JoinType.LEFT).get(TranslationLine_.id)));
            }
            if (criteria.getVideoIdId() != null) {
                specification = specification.and(buildSpecification(criteria.getVideoIdId(),
                    root -> root.join(Caption_.videoId, JoinType.LEFT).get(Video_.id)));
            }
        }
        return specification;
    }
}
