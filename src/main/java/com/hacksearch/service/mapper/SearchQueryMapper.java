package com.hacksearch.service.mapper;

import com.hacksearch.domain.*;
import com.hacksearch.service.dto.SearchQueryDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity SearchQuery and its DTO SearchQueryDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface SearchQueryMapper extends EntityMapper<SearchQueryDTO, SearchQuery> {



    default SearchQuery fromId(Long id) {
        if (id == null) {
            return null;
        }
        SearchQuery searchQuery = new SearchQuery();
        searchQuery.setId(id);
        return searchQuery;
    }
}
