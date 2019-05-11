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

/**
 * Criteria class for the Caption entity. This class is used in CaptionResource to
 * receive all the possible filtering options from the Http GET request parameters.
 * For example the following could be a valid requests:
 * <code> /captions?id.greaterThan=5&amp;attr1.contains=something&amp;attr2.specified=false</code>
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class CaptionCriteria implements Serializable {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter captionId;

    private StringFilter trackKind;

    private StringFilter language;

    private LongFilter translationLineId;

    private LongFilter videoIdId;

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getCaptionId() {
        return captionId;
    }

    public void setCaptionId(StringFilter captionId) {
        this.captionId = captionId;
    }

    public StringFilter getTrackKind() {
        return trackKind;
    }

    public void setTrackKind(StringFilter trackKind) {
        this.trackKind = trackKind;
    }

    public StringFilter getLanguage() {
        return language;
    }

    public void setLanguage(StringFilter language) {
        this.language = language;
    }

    public LongFilter getTranslationLineId() {
        return translationLineId;
    }

    public void setTranslationLineId(LongFilter translationLineId) {
        this.translationLineId = translationLineId;
    }

    public LongFilter getVideoIdId() {
        return videoIdId;
    }

    public void setVideoIdId(LongFilter videoIdId) {
        this.videoIdId = videoIdId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final CaptionCriteria that = (CaptionCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(captionId, that.captionId) &&
            Objects.equals(trackKind, that.trackKind) &&
            Objects.equals(language, that.language) &&
            Objects.equals(translationLineId, that.translationLineId) &&
            Objects.equals(videoIdId, that.videoIdId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        captionId,
        trackKind,
        language,
        translationLineId,
        videoIdId
        );
    }

    @Override
    public String toString() {
        return "CaptionCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (captionId != null ? "captionId=" + captionId + ", " : "") +
                (trackKind != null ? "trackKind=" + trackKind + ", " : "") +
                (language != null ? "language=" + language + ", " : "") +
                (translationLineId != null ? "translationLineId=" + translationLineId + ", " : "") +
                (videoIdId != null ? "videoIdId=" + videoIdId + ", " : "") +
            "}";
    }

}
