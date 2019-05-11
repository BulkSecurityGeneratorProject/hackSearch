package com.hacksearch.repository;

import com.hacksearch.domain.Caption;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Caption entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CaptionRepository extends JpaRepository<Caption, Long>, JpaSpecificationExecutor<Caption> {

}
