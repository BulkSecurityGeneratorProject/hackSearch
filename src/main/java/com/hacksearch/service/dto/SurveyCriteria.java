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
 * Criteria class for the Survey entity. This class is used in SurveyResource to
 * receive all the possible filtering options from the Http GET request parameters.
 * For example the following could be a valid requests:
 * <code> /surveys?id.greaterThan=5&amp;attr1.contains=something&amp;attr2.specified=false</code>
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class SurveyCriteria implements Serializable {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter text;

    private StringFilter timeStart;

    private StringFilter timeEnd;

    private IntegerFilter episode;

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
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


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final SurveyCriteria that = (SurveyCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(text, that.text) &&
            Objects.equals(timeStart, that.timeStart) &&
            Objects.equals(timeEnd, that.timeEnd) &&
            Objects.equals(episode, that.episode);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        text,
        timeStart,
        timeEnd,
        episode
        );
    }

    @Override
    public String toString() {
        return "SurveyCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (text != null ? "text=" + text + ", " : "") +
                (timeStart != null ? "timeStart=" + timeStart + ", " : "") +
                (timeEnd != null ? "timeEnd=" + timeEnd + ", " : "") +
                (episode != null ? "episode=" + episode + ", " : "") +
            "}";
    }

}
