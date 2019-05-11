package com.hacksearch.repository;

import com.hacksearch.domain.Video;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Video entity.
 */
@SuppressWarnings("unused")
@Repository
public interface VideoRepositoryExt extends JpaRepository<Video, Long>, JpaSpecificationExecutor<Video> {

    @Query(
        value = "update TRANSLATION_LINE tr\n" +
            "set episode=\n" +
            "(\n" +
            "    select cast(v.EPISODE as INTEGER)\n" +
            "    from TRANSLATION_LINE tra\n" +
            "             join CAPTION ca\n" +
            "                  on tra.CAPTION_ID_ID = ca.id\n" +
            "             join VIDEO v\n" +
            "                  on ca.VIDEO_ID_ID = v.ID\n" +
            "    where tr.ID = tra.ID\n" +
            ")\n" +
            "where episode is null",
        nativeQuery = true)
    void setEpisodeInTranslation();

}
