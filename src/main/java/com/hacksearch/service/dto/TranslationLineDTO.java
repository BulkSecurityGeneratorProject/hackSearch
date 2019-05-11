package com.hacksearch.service.dto;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the TranslationLine entity.
 */
public class TranslationLineDTO implements Serializable {

    private Long id;

    private Integer sndId;

    private String text;

    private String timeStart;

    private String timeEnd;

    private Integer episode;


    private Long captionIdId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getSndId() {
        return sndId;
    }

    public void setSndId(Integer sndId) {
        this.sndId = sndId;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getTimeStart() {
        return timeStart;
    }

    public void setTimeStart(String timeStart) {
        this.timeStart = timeStart;
    }

    public String getTimeEnd() {
        return timeEnd;
    }

    public void setTimeEnd(String timeEnd) {
        this.timeEnd = timeEnd;
    }

    public Integer getEpisode() {
        return episode;
    }

    public void setEpisode(Integer episode) {
        this.episode = episode;
    }

    public Long getCaptionIdId() {
        return captionIdId;
    }

    public void setCaptionIdId(Long captionId) {
        this.captionIdId = captionId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        TranslationLineDTO translationLineDTO = (TranslationLineDTO) o;
        if (translationLineDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), translationLineDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "TranslationLineDTO{" +
            "id=" + getId() +
            ", sndId=" + getSndId() +
            ", text='" + getText() + "'" +
            ", timeStart='" + getTimeStart() + "'" +
            ", timeEnd='" + getTimeEnd() + "'" +
            ", episode=" + getEpisode() +
            ", captionId=" + getCaptionIdId() +
            "}";
    }
}
