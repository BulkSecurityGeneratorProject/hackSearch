package com.hacksearch.repository;

import com.hacksearch.domain.SearchQuery;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the SearchQuery entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SearchQueryRepository extends JpaRepository<SearchQuery, Long>, JpaSpecificationExecutor<SearchQuery> {

}
