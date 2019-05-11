package com.hacksearch.service.dto;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the Caption entity.
 */
public class CaptionDTO implements Serializable {

    private Long id;

    private String captionId;

    private String trackKind;

    private String language;


    private Long videoIdId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCaptionId() {
        return captionId;
    }

    public void setCaptionId(String captionId) {
        this.captionId = captionId;
    }

    public String getTrackKind() {
        return trackKind;
    }

    public void setTrackKind(String trackKind) {
        this.trackKind = trackKind;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public Long getVideoIdId() {
        return videoIdId;
    }

    public void setVideoIdId(Long videoId) {
        this.videoIdId = videoId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        CaptionDTO captionDTO = (CaptionDTO) o;
        if (captionDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), captionDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "CaptionDTO{" +
            "id=" + getId() +
            ", captionId='" + getCaptionId() + "'" +
            ", trackKind='" + getTrackKind() + "'" +
            ", language='" + getLanguage() + "'" +
            ", videoId=" + getVideoIdId() +
            "}";
    }
}
