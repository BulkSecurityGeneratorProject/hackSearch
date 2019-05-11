package com.hacksearch.domain;


import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;

/**
 * A SearchQuery.
 */
@Entity
@Table(name = "search_query")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class SearchQuery implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "query")
    private String query;

    @Column(name = "episode")
    private Integer episode;

    @Column(name = "created_at")
    private ZonedDateTime createdAt;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getQuery() {
        return query;
    }

    public SearchQuery query(String query) {
        this.query = query;
        return this;
    }

    public void setQuery(String query) {
        this.query = query;
    }

    public Integer getEpisode() {
        return episode;
    }

    public SearchQuery episode(Integer episode) {
        this.episode = episode;
        return this;
    }

    public void setEpisode(Integer episode) {
        this.episode = episode;
    }

    public ZonedDateTime getCreatedAt() {
        return createdAt;
    }

    public SearchQuery createdAt(ZonedDateTime createdAt) {
        this.createdAt = createdAt;
        return this;
    }

    public void setCreatedAt(ZonedDateTime createdAt) {
        this.createdAt = createdAt;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        SearchQuery searchQuery = (SearchQuery) o;
        if (searchQuery.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), searchQuery.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "SearchQuery{" +
            "id=" + getId() +
            ", query='" + getQuery() + "'" +
            ", episode=" + getEpisode() +
            ", createdAt='" + getCreatedAt() + "'" +
            "}";
    }
}
