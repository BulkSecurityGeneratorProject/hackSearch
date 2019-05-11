package com.hacksearch.repository;

import com.hacksearch.domain.Caption;
import com.hacksearch.domain.TranslationLine;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the TranslationLine entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TranslationLineRepository extends JpaRepository<TranslationLine, Long>, JpaSpecificationExecutor<TranslationLine> {

    @Query("SELECT u FROM TranslationLine u WHERE UPPER(u.text) like upper(?1)")
    Page<TranslationLine> findAllByText(String query, Pageable pageable);

    @Query("SELECT u FROM TranslationLine u WHERE UPPER(u.text) like upper(?1) and u.episode = ?2")
    Page<TranslationLine> findAllByTextAndEpisode(String query, int episode, Pageable pageable);

    @Query("SELECT count( u ) FROM TranslationLine u WHERE UPPER( u.text )like upper(?1)")
    Integer customFindAllByTextCount(String query);

    @Query("SELECT count( u ) FROM TranslationLine u WHERE UPPER( u.text )like upper(?1) and u.episode = ?2")
    Integer customFindAllByTextAndEpisodeCount(String query, int episode);

    @Query("SELECT count ( u ) from TranslationLine u where caption_id_id = ?1")
    Integer countTranslationLineByCaptionIdId(String caption_id_id);

    @Query
    Integer countTranslationLinesByCaptionIdEquals(Caption caption);

}
