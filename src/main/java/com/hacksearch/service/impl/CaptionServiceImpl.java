package com.hacksearch.service.impl;

import com.hacksearch.service.CaptionService;
import com.hacksearch.domain.Caption;
import com.hacksearch.repository.CaptionRepository;
import com.hacksearch.service.dto.CaptionDTO;
import com.hacksearch.service.mapper.CaptionMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing Caption.
 */
@Service
@Transactional
public class CaptionServiceImpl implements CaptionService {

    private final Logger log = LoggerFactory.getLogger(CaptionServiceImpl.class);

    private final CaptionRepository captionRepository;

    private final CaptionMapper captionMapper;

    public CaptionServiceImpl(CaptionRepository captionRepository, CaptionMapper captionMapper) {
        this.captionRepository = captionRepository;
        this.captionMapper = captionMapper;
    }

    /**
     * Save a caption.
     *
     * @param captionDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public CaptionDTO save(CaptionDTO captionDTO) {
        log.debug("Request to save Caption : {}", captionDTO);
        Caption caption = captionMapper.toEntity(captionDTO);
        caption = captionRepository.save(caption);
        return captionMapper.toDto(caption);
    }

    /**
     * Get all the captions.
     *
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<CaptionDTO> findAll() {
        log.debug("Request to get all Captions");
        return captionRepository.findAll().stream()
            .map(captionMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one caption by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<CaptionDTO> findOne(Long id) {
        log.debug("Request to get Caption : {}", id);
        return captionRepository.findById(id)
            .map(captionMapper::toDto);
    }

    /**
     * Delete the caption by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Caption : {}", id);
        captionRepository.deleteById(id);
    }
}
