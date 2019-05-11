package com.hacksearch.service.dto;
import java.time.ZonedDateTime;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the SearchQuery entity.
 */
public class SearchQueryDTO implements Serializable {

    private Long id;

    private String query;

    private Integer episode;

    private ZonedDateTime createdAt;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        this.query = query;
    }

    public Integer getEpisode() {
        return episode;
    }

    public void setEpisode(Integer episode) {
        this.episode = episode;
    }

    public ZonedDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(ZonedDateTime createdAt) {
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

        SearchQueryDTO searchQueryDTO = (SearchQueryDTO) o;
        if (searchQueryDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), searchQueryDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "SearchQueryDTO{" +
            "id=" + getId() +
            ", query='" + getQuery() + "'" +
            ", episode=" + getEpisode() +
            ", createdAt='" + getCreatedAt() + "'" +
            "}";
    }
}
