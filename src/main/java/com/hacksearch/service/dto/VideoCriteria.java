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
 * Criteria class for the Video entity. This class is used in VideoResource to
 * receive all the possible filtering options from the Http GET request parameters.
 * For example the following could be a valid requests:
 * <code> /videos?id.greaterThan=5&amp;attr1.contains=something&amp;attr2.specified=false</code>
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class VideoCriteria implements Serializable {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter videoId;

    private StringFilter title;

    private IntegerFilter episode;

    private StringFilter soundcloud;

    private LongFilter captionIdId;

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getVideoId() {
        return videoId;
    }

    public void setVideoId(StringFilter videoId) {
        this.videoId = videoId;
    }

    public StringFilter getTitle() {
        return title;
    }

    public void setTitle(StringFilter title) {
        this.title = title;
    }

    public IntegerFilter getEpisode() {
        return episode;
    }

    public void setEpisode(IntegerFilter episode) {
        this.episode = episode;
    }

    public StringFilter getSoundcloud() {
        return soundcloud;
    }

    public void setSoundcloud(StringFilter soundcloud) {
        this.soundcloud = soundcloud;
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
        final VideoCriteria that = (VideoCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(videoId, that.videoId) &&
            Objects.equals(title, that.title) &&
            Objects.equals(episode, that.episode) &&
            Objects.equals(soundcloud, that.soundcloud) &&
            Objects.equals(captionIdId, that.captionIdId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        videoId,
        title,
        episode,
        soundcloud,
        captionIdId
        );
    }

    @Override
    public String toString() {
        return "VideoCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (videoId != null ? "videoId=" + videoId + ", " : "") +
                (title != null ? "title=" + title + ", " : "") +
                (episode != null ? "episode=" + episode + ", " : "") +
                (soundcloud != null ? "soundcloud=" + soundcloud + ", " : "") +
                (captionIdId != null ? "captionIdId=" + captionIdId + ", " : "") +
            "}";
    }

}
