package com.hacksearch.service;

import com.hacksearch.domain.Caption_;
import com.hacksearch.domain.TranslationLine;
import com.hacksearch.domain.TranslationLine_;
import com.hacksearch.repository.TranslationLineRepository;
import com.hacksearch.service.dto.TranslationLineCriteria;
import com.hacksearch.service.dto.TranslationLineDTO;
import com.hacksearch.service.mapper.TranslationLineMapper;
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
 * Service for executing complex queries for TranslationLine entities in the database.
 * The main input is a {@link TranslationLineCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link TranslationLineDTO} or a {@link Page} of {@link TranslationLineDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class TranslationLineQueryService extends QueryService<TranslationLine> {

    private final Logger log = LoggerFactory.getLogger(TranslationLineQueryService.class);

    private final TranslationLineRepository translationLineRepository;

    private final TranslationLineMapper translationLineMapper;

    public TranslationLineQueryService(TranslationLineRepository translationLineRepository, TranslationLineMapper translationLineMapper) {
        this.translationLineRepository = translationLineRepository;
        this.translationLineMapper = translationLineMapper;
    }

    /**
     * Return a {@link List} of {@link TranslationLineDTO} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<TranslationLineDTO> findByCriteria(TranslationLineCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<TranslationLine> specification = createSpecification(criteria);
        return translationLineMapper.toDto(translationLineRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link TranslationLineDTO} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<TranslationLineDTO> findByCriteria(TranslationLineCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<TranslationLine> specification = createSpecification(criteria);
        return translationLineRepository.findAll(specification, page)
            .map(translationLineMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(TranslationLineCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<TranslationLine> specification = createSpecification(criteria);
        return translationLineRepository.count(specification);
    }

    /**
     * Function to convert TranslationLineCriteria to a {@link Specification}
     */
    private Specification<TranslationLine> createSpecification(TranslationLineCriteria criteria) {
        Specification<TranslationLine> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), TranslationLine_.id));
            }
            if (criteria.getSndId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getSndId(), TranslationLine_.sndId));
            }
            if (criteria.getText() != null) {
                specification = specification.and(buildStringSpecification(criteria.getText(), TranslationLine_.text));
            }
            if (criteria.getTimeStart() != null) {
                specification = specification.and(buildStringSpecification(criteria.getTimeStart(), TranslationLine_.timeStart));
            }
            if (criteria.getTimeEnd() != null) {
                specification = specification.and(buildStringSpecification(criteria.getTimeEnd(), TranslationLine_.timeEnd));
            }
            if (criteria.getEpisode() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getEpisode(), TranslationLine_.episode));
            }
            if (criteria.getCaptionIdId() != null) {
                specification = specification.and(buildSpecification(criteria.getCaptionIdId(),
                    root -> root.join(TranslationLine_.captionId, JoinType.LEFT).get(Caption_.id)));
            }
        }
        return specification;
    }
}
