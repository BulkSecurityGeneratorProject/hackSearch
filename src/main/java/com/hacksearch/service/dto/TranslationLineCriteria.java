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
 * Criteria class for the TranslationLine entity. This class is used in TranslationLineResource to
 * receive all the possible filtering options from the Http GET request parameters.
 * For example the following could be a valid requests:
 * <code> /translation-lines?id.greaterThan=5&amp;attr1.contains=something&amp;attr2.specified=false</code>
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class TranslationLineCriteria implements Serializable {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private IntegerFilter sndId;

    private StringFilter text;

    private StringFilter timeStart;

    private StringFilter timeEnd;

    private IntegerFilter episode;

    private LongFilter captionIdId;

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public IntegerFilter getSndId() {
        return sndId;
    }

    public void setSndId(IntegerFilter sndId) {
        this.sndId = sndId;
    }

    public StringFilter getText() {
        return text;
    }

    public void setText(StringFilter text) {
        this.text = text;
    }

    public StringFilter getTimeStart() {
        return timeStart;
    }

    public void setTimeStart(StringFilter timeStart) {
        this.timeStart = timeStart;
    }

    public StringFilter getTimeEnd() {
        return timeEnd;
    }

    public void setTimeEnd(StringFilter timeEnd) {
        this.timeEnd = timeEnd;
    }

    public IntegerFilter getEpisode() {
        return episode;
    }

    public void setEpisode(IntegerFilter episode) {
        this.episode = episode;
    }

    public LongFilter getCaptionIdId() {
        return captionIdId;
    }

    public void setCaptionIdId(LongFilter captionIdId) {
        this.captionIdId = captionIdId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final TranslationLineCriteria that = (TranslationLineCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(sndId, that.sndId) &&
            Objects.equals(text, that.text) &&
            Objects.equals(timeStart, that.timeStart) &&
            Objects.equals(timeEnd, that.timeEnd) &&
            Objects.equals(episode, that.episode) &&
            Objects.equals(captionIdId, that.captionIdId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        sndId,
        text,
        timeStart,
        timeEnd,
        episode,
        captionIdId
        );
    }

    @Override
    public String toString() {
        return "TranslationLineCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (sndId != null ? "sndId=" + sndId + ", " : "") +
                (text != null ? "text=" + text + ", " : "") +
                (timeStart != null ? "timeStart=" + timeStart + ", " : "") +
                (timeEnd != null ? "timeEnd=" + timeEnd + ", " : "") +
                (episode != null ? "episode=" + episode + ", " : "") +
                (captionIdId != null ? "captionIdId=" + captionIdId + ", " : "") +
            "}";
    }

}
