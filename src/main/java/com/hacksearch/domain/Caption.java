package com.hacksearch.domain;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * A Caption.
 */
@Entity
@Table(name = "caption")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Caption implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "caption_id")
    private String captionId;

    @Column(name = "track_kind")
    private String trackKind;

    @Column(name = "language")
    private String language;

    @OneToMany(mappedBy = "captionId")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<TranslationLine> translationLines = new HashSet<>();
    @ManyToOne
    @JsonIgnoreProperties("captionIds")
    private Video videoId;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCaptionId() {
        return captionId;
    }

    public Caption captionId(String captionId) {
        this.captionId = captionId;
        return this;
    }

    public void setCaptionId(String captionId) {
        this.captionId = captionId;
    }

    public String getTrackKind() {
        return trackKind;
    }

    public Caption trackKind(String trackKind) {
        this.trackKind = trackKind;
        return this;
    }

    public void setTrackKind(String trackKind) {
        this.trackKind = trackKind;
    }

    public String getLanguage() {
        return language;
    }

    public Caption language(String language) {
        this.language = language;
        return this;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public Set<TranslationLine> getTranslationLines() {
        return translationLines;
    }

    public Caption translationLines(Set<TranslationLine> translationLines) {
        this.translationLines = translationLines;
        return this;
    }

    public Caption addTranslationLine(TranslationLine translationLine) {
        this.translationLines.add(translationLine);
        translationLine.setCaptionId(this);
        return this;
    }

    public Caption removeTranslationLine(TranslationLine translationLine) {
        this.translationLines.remove(translationLine);
        translationLine.setCaptionId(null);
        return this;
    }

    public void setTranslationLines(Set<TranslationLine> translationLines) {
        this.translationLines = translationLines;
    }

    public Video getVideoId() {
        return videoId;
    }

    public Caption videoId(Video video) {
        this.videoId = video;
        return this;
    }

    public void setVideoId(Video video) {
        this.videoId = video;
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
        Caption caption = (Caption) o;
        if (caption.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), caption.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Caption{" +
            "id=" + getId() +
            ", captionId='" + getCaptionId() + "'" +
            ", trackKind='" + getTrackKind() + "'" +
            ", language='" + getLanguage() + "'" +
            "}";
    }
}
