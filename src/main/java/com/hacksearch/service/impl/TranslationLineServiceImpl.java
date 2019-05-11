package com.hacksearch.service.impl;

import com.hacksearch.domain.Caption;
import com.hacksearch.domain.TranslationLine;
import com.hacksearch.repository.TranslationLineRepository;
import com.hacksearch.service.TranslationLineService;
import com.hacksearch.service.dto.TranslationLineDTO;
import com.hacksearch.service.mapper.TranslationLineMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing TranslationLine.
 */
@Service
@Transactional
public class TranslationLineServiceImpl implements TranslationLineService {



    private final Logger log = LoggerFactory.getLogger(TranslationLineServiceImpl.class);

    private final TranslationLineRepository translationLineRepository;

    private final TranslationLineMapper translationLineMapper;

    private final SearchQueryServiceImpl searchQueryService;

    public TranslationLineServiceImpl(TranslationLineRepository translationLineRepository, TranslationLineMapper translationLineMapper, SearchQueryServiceImpl searchQueryService) {
        this.translationLineRepository = translationLineRepository;
        this.translationLineMapper = translationLineMapper;
        this.searchQueryService = searchQueryService;
    }

    /**
     * Save a translationLine.
     *
     * @param translationLineDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public TranslationLineDTO save(TranslationLineDTO translationLineDTO) {
        log.debug("Request to save TranslationLine : {}", translationLineDTO);
        TranslationLine translationLine = translationLineMapper.toEntity(translationLineDTO);
        translationLine = translationLineRepository.save(translationLine);
        return translationLineMapper.toDto(translationLine);
    }

    /**
     * Get all the translationLines.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<TranslationLineDTO> findAll(Pageable pageable) {
        log.debug("Request to get all TranslationLines");
        return translationLineRepository.findAll(pageable)
            .map(translationLineMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<TranslationLineDTO> findByText(String query,String episode,Pageable pageable) {
        log.debug("Request to get all TranslationLines");
        String newQuery = '%'+query+'%';
        log.info("selected episode is:" + episode);
        try {
            int episodeInt = Integer.parseInt(episode.trim());
            if(episodeInt == 0){
                return translationLineRepository.findAllByText(newQuery,pageable).map(translationLineMapper::toDto);
            }
            return translationLineRepository.findAllByTextAndEpisode(newQuery,episodeInt,pageable).map(translationLineMapper::toDto);
        } catch (NumberFormatException e) {
            log.error("Error parsing Episode String");
        }
        return translationLineRepository.findAllByText(newQuery,pageable).map(translationLineMapper::toDto);
    }


    @Override
    @Transactional(readOnly = true)
    public Integer findByTextCount(String query, String episode) {
        log.debug("Request to get all TranslationLines");
        query = '%'+query+'%';
        try {
            int episodeInt = Integer.parseInt(episode.trim());
            if(episodeInt == 0){
                Integer allTextByQueryCount = translationLineRepository.customFindAllByTextCount(query);
                return allTextByQueryCount;
            }
            Integer allTextByQueryAndEpisodeCount = translationLineRepository.customFindAllByTextAndEpisodeCount(query,episodeInt);
            return allTextByQueryAndEpisodeCount;
        } catch (NumberFormatException e) {
            log.error("Error parsing Episode String");
        }

        Integer allTextByQueryCount = translationLineRepository.customFindAllByTextCount(query);
        return allTextByQueryCount;
    }


    /**
     * Get one translationLine by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<TranslationLineDTO> findOne(Long id) {
        log.debug("Request to get TranslationLine : {}", id);
        return translationLineRepository.findById(id)
            .map(translationLineMapper::toDto);
    }

    /**
     * Delete the translationLine by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete TranslationLine : {}", id);
        translationLineRepository.deleteById(id);
    }


    public Integer countTranslationLineByCaptionIdId(Caption caption){
        String captionId = String.valueOf(caption.getId());
//        Integer integer = translationLineRepository.countTranslationLineByCaptionIdId(captionId);
        Integer integer = translationLineRepository.countTranslationLinesByCaptionIdEquals(caption);
        return integer;
    }
}
