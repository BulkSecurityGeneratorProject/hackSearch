package com.hacksearch.service.dto;

import java.io.Serializable;
import java.util.Objects;
import io.github.jhipster.service.filter.BooleanFilter;
import io.github.jhipster.service.filter.DoubleFilter;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.FloatFilter;
import io.github.jhipster.service.filter.IntegerFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;
import io.github.jhipster.service.filter.ZonedDateTimeFilter;

/**
 * Criteria class for the SearchQuery entity. This class is used in SearchQueryResource to
 * receive all the possible filtering options from the Http GET request parameters.
 * For example the following could be a valid requests:
 * <code> /search-queries?id.greaterThan=5&amp;attr1.contains=something&amp;attr2.specified=false</code>
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class SearchQueryCriteria implements Serializable {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter query;

    private IntegerFilter episode;

    private ZonedDateTimeFilter createdAt;

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getQuery() {
        return query;
    }

    public void setQuery(StringFilter query) {
        this.query = query;
    }

    public IntegerFilter getEpisode() {
        return episode;
    }

    public void setEpisode(IntegerFilter episode) {
        this.episode = episode;
    }

    public ZonedDateTimeFilter getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(ZonedDateTimeFilter createdAt) {
        this.createdAt = createdAt;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final SearchQueryCriteria that = (SearchQueryCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(query, that.query) &&
            Objects.equals(episode, that.episode) &&
            Objects.equals(createdAt, that.createdAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        query,
        episode,
        createdAt
        );
    }

    @Override
    public String toString() {
        return "SearchQueryCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (query != null ? "query=" + query + ", " : "") +
                (episode != null ? "episode=" + episode + ", " : "") +
                (createdAt != null ? "createdAt=" + createdAt + ", " : "") +
            "}";
    }

}
