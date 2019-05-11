package com.hacksearch.domain;


import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * A Video.
 */
@Entity
@Table(name = "video")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Video implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "video_id")
    private String videoId;

    @Column(name = "title")
    private String title;

    @Column(name = "episode")
    private Integer episode;

    @Column(name = "soundcloud")
    private String soundcloud;

    @OneToMany(mappedBy = "videoId")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Caption> captionIds = new HashSet<>();
    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getVideoId() {
        return videoId;
    }

    public Video videoId(String videoId) {
        this.videoId = videoId;
        return this;
    }

    public void setVideoId(String videoId) {
        this.videoId = videoId;
    }

    public String getTitle() {
        return title;
    }

    public Video title(String title) {
        this.title = title;
        return this;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getEpisode() {
        return episode;
    }

    public Video episode(Integer episode) {
        this.episode = episode;
        return this;
    }

    public void setEpisode(Integer episode) {
        this.episode = episode;
    }

    public String getSoundcloud() {
        return soundcloud;
    }

    public Video soundcloud(String soundcloud) {
        this.soundcloud = soundcloud;
        return this;
    }

    public void setSoundcloud(String soundcloud) {
        this.soundcloud = soundcloud;
    }

    public Set<Caption> getCaptionIds() {
        return captionIds;
    }

    public Video captionIds(Set<Caption> captions) {
        this.captionIds = captions;
        return this;
    }

    public Video addCaptionId(Caption caption) {
        this.captionIds.add(caption);
        caption.setVideoId(this);
        return this;
    }

    public Video removeCaptionId(Caption caption) {
        this.captionIds.remove(caption);
        caption.setVideoId(null);
        return this;
    }

    public void setCaptionIds(Set<Caption> captions) {
        this.captionIds = captions;
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
        Video video = (Video) o;
        if (video.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), video.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Video{" +
            "id=" + getId() +
            ", videoId='" + getVideoId() + "'" +
            ", title='" + getTitle() + "'" +
            ", episode=" + getEpisode() +
            ", soundcloud='" + getSoundcloud() + "'" +
            "}";
    }
}
