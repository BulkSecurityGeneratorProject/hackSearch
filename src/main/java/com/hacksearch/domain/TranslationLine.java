package com.hacksearch.domain;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A TranslationLine.
 */
@Entity
@Table(name = "translation_line")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class TranslationLine implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "snd_id")
    private Integer sndId;

    @Column(name = "text")
    private String text;

    @Column(name = "time_start")
    private String timeStart;

    @Column(name = "time_end")
    private String timeEnd;

    @Column(name = "episode")
    private Integer episode;

    @ManyToOne
    @JsonIgnoreProperties("translationLines")
    private Caption captionId;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getSndId() {
        return sndId;
    }

    public TranslationLine sndId(Integer sndId) {
        this.sndId = sndId;
        return this;
    }

    public void setSndId(Integer sndId) {
        this.sndId = sndId;
    }

    public String getText() {
        return text;
    }

    public TranslationLine text(String text) {
        this.text = text;
        return this;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getTimeStart() {
        return timeStart;
    }

    public TranslationLine timeStart(String timeStart) {
        this.timeStart = timeStart;
        return this;
    }

    public void setTimeStart(String timeStart) {
        this.timeStart = timeStart;
    }

    public String getTimeEnd() {
        return timeEnd;
    }

    public TranslationLine timeEnd(String timeEnd) {
        this.timeEnd = timeEnd;
        return this;
    }

    public void setTimeEnd(String timeEnd) {
        this.timeEnd = timeEnd;
    }

    public Integer getEpisode() {
        return episode;
    }

    public TranslationLine episode(Integer episode) {
        this.episode = episode;
        return this;
    }

    public void setEpisode(Integer episode) {
        this.episode = episode;
    }

    public Caption getCaptionId() {
        return captionId;
    }

    public TranslationLine captionId(Caption caption) {
        this.captionId = caption;
        return this;
    }

    public void setCaptionId(Caption caption) {
        this.captionId = caption;
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
        TranslationLine translationLine = (TranslationLine) o;
        if (translationLine.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), translationLine.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "TranslationLine{" +
            "id=" + getId() +
            ", sndId=" + getSndId() +
            ", text='" + getText() + "'" +
            ", timeStart='" + getTimeStart() + "'" +
            ", timeEnd='" + getTimeEnd() + "'" +
            ", episode=" + getEpisode() +
            "}";
    }
}
