package com.hacksearch.web.rest;

import com.hacksearch.HackSearchApp;

import com.hacksearch.domain.Video;
import com.hacksearch.domain.Caption;
import com.hacksearch.repository.VideoRepository;
import com.hacksearch.service.VideoService;
import com.hacksearch.service.dto.VideoDTO;
import com.hacksearch.service.mapper.VideoMapper;
import com.hacksearch.web.rest.errors.ExceptionTranslator;
import com.hacksearch.service.dto.VideoCriteria;
import com.hacksearch.service.VideoQueryService;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Validator;

import javax.persistence.EntityManager;
import java.util.List;


import static com.hacksearch.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the VideoResource REST controller.
 *
 * @see VideoResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = HackSearchApp.class)
public class VideoResourceIntTest {

    private static final String DEFAULT_VIDEO_ID = "AAAAAAAAAA";
    private static final String UPDATED_VIDEO_ID = "BBBBBBBBBB";

    private static final String DEFAULT_TITLE = "AAAAAAAAAA";
    private static final String UPDATED_TITLE = "BBBBBBBBBB";

    private static final Integer DEFAULT_EPISODE = 1;
    private static final Integer UPDATED_EPISODE = 2;

    private static final String DEFAULT_SOUNDCLOUD = "AAAAAAAAAA";
    private static final String UPDATED_SOUNDCLOUD = "BBBBBBBBBB";

    @Autowired
    private VideoRepository videoRepository;

    @Autowired
    private VideoMapper videoMapper;

    @Autowired
    private VideoService videoService;

    @Autowired
    private VideoQueryService videoQueryService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    @Autowired
    private Validator validator;

    private MockMvc restVideoMockMvc;

    private Video video;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final VideoResource videoResource = new VideoResource(videoService, videoQueryService);
        this.restVideoMockMvc = MockMvcBuilders.standaloneSetup(videoResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter)
            .setValidator(validator).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Video createEntity(EntityManager em) {
        Video video = new Video()
            .videoId(DEFAULT_VIDEO_ID)
            .title(DEFAULT_TITLE)
            .episode(DEFAULT_EPISODE)
            .soundcloud(DEFAULT_SOUNDCLOUD);
        return video;
    }

    @Before
    public void initTest() {
        video = createEntity(em);
    }

    @Test
    @Transactional
    public void createVideo() throws Exception {
        int databaseSizeBeforeCreate = videoRepository.findAll().size();

        // Create the Video
        VideoDTO videoDTO = videoMapper.toDto(video);
        restVideoMockMvc.perform(post("/api/videos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(videoDTO)))
            .andExpect(status().isCreated());

        // Validate the Video in the database
        List<Video> videoList = videoRepository.findAll();
        assertThat(videoList).hasSize(databaseSizeBeforeCreate + 1);
        Video testVideo = videoList.get(videoList.size() - 1);
        assertThat(testVideo.getVideoId()).isEqualTo(DEFAULT_VIDEO_ID);
        assertThat(testVideo.getTitle()).isEqualTo(DEFAULT_TITLE);
        assertThat(testVideo.getEpisode()).isEqualTo(DEFAULT_EPISODE);
        assertThat(testVideo.getSoundcloud()).isEqualTo(DEFAULT_SOUNDCLOUD);
    }

    @Test
    @Transactional
    public void createVideoWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = videoRepository.findAll().size();

        // Create the Video with an existing ID
        video.setId(1L);
        VideoDTO videoDTO = videoMapper.toDto(video);

        // An entity with an existing ID cannot be created, so this API call must fail
        restVideoMockMvc.perform(post("/api/videos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(videoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Video in the database
        List<Video> videoList = videoRepository.findAll();
        assertThat(videoList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllVideos() throws Exception {
        // Initialize the database
        videoRepository.saveAndFlush(video);

        // Get all the videoList
        restVideoMockMvc.perform(get("/api/videos?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(video.getId().intValue())))
            .andExpect(jsonPath("$.[*].videoId").value(hasItem(DEFAULT_VIDEO_ID.toString())))
            .andExpect(jsonPath("$.[*].title").value(hasItem(DEFAULT_TITLE.toString())))
            .andExpect(jsonPath("$.[*].episode").value(hasItem(DEFAULT_EPISODE)))
            .andExpect(jsonPath("$.[*].soundcloud").value(hasItem(DEFAULT_SOUNDCLOUD.toString())));
    }
    
    @Test
    @Transactional
    public void getVideo() throws Exception {
        // Initialize the database
        videoRepository.saveAndFlush(video);

        // Get the video
        restVideoMockMvc.perform(get("/api/videos/{id}", video.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(video.getId().intValue()))
            .andExpect(jsonPath("$.videoId").value(DEFAULT_VIDEO_ID.toString()))
            .andExpect(jsonPath("$.title").value(DEFAULT_TITLE.toString()))
            .andExpect(jsonPath("$.episode").value(DEFAULT_EPISODE))
            .andExpect(jsonPath("$.soundcloud").value(DEFAULT_SOUNDCLOUD.toString()));
    }

    @Test
    @Transactional
    public void getAllVideosByVideoIdIsEqualToSomething() throws Exception {
        // Initialize the database
        videoRepository.saveAndFlush(video);

        // Get all the videoList where videoId equals to DEFAULT_VIDEO_ID
        defaultVideoShouldBeFound("videoId.equals=" + DEFAULT_VIDEO_ID);

        // Get all the videoList where videoId equals to UPDATED_VIDEO_ID
        defaultVideoShouldNotBeFound("videoId.equals=" + UPDATED_VIDEO_ID);
    }

    @Test
    @Transactional
    public void getAllVideosByVideoIdIsInShouldWork() throws Exception {
        // Initialize the database
        videoRepository.saveAndFlush(video);

        // Get all the videoList where videoId in DEFAULT_VIDEO_ID or UPDATED_VIDEO_ID
        defaultVideoShouldBeFound("videoId.in=" + DEFAULT_VIDEO_ID + "," + UPDATED_VIDEO_ID);

        // Get all the videoList where videoId equals to UPDATED_VIDEO_ID
        defaultVideoShouldNotBeFound("videoId.in=" + UPDATED_VIDEO_ID);
    }

    @Test
    @Transactional
    public void getAllVideosByVideoIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        videoRepository.saveAndFlush(video);

        // Get all the videoList where videoId is not null
        defaultVideoShouldBeFound("videoId.specified=true");

        // Get all the videoList where videoId is null
        defaultVideoShouldNotBeFound("videoId.specified=false");
    }

    @Test
    @Transactional
    public void getAllVideosByTitleIsEqualToSomething() throws Exception {
        // Initialize the database
        videoRepository.saveAndFlush(video);

        // Get all the videoList where title equals to DEFAULT_TITLE
        defaultVideoShouldBeFound("title.equals=" + DEFAULT_TITLE);

        // Get all the videoList where title equals to UPDATED_TITLE
        defaultVideoShouldNotBeFound("title.equals=" + UPDATED_TITLE);
    }

    @Test
    @Transactional
    public void getAllVideosByTitleIsInShouldWork() throws Exception {
        // Initialize the database
        videoRepository.saveAndFlush(video);

        // Get all the videoList where title in DEFAULT_TITLE or UPDATED_TITLE
        defaultVideoShouldBeFound("title.in=" + DEFAULT_TITLE + "," + UPDATED_TITLE);

        // Get all the videoList where title equals to UPDATED_TITLE
        defaultVideoShouldNotBeFound("title.in=" + UPDATED_TITLE);
    }

    @Test
    @Transactional
    public void getAllVideosByTitleIsNullOrNotNull() throws Exception {
        // Initialize the database
        videoRepository.saveAndFlush(video);

        // Get all the videoList where title is not null
        defaultVideoShouldBeFound("title.specified=true");

        // Get all the videoList where title is null
        defaultVideoShouldNotBeFound("title.specified=false");
    }

    @Test
    @Transactional
    public void getAllVideosByEpisodeIsEqualToSomething() throws Exception {
        // Initialize the database
        videoRepository.saveAndFlush(video);

        // Get all the videoList where episode equals to DEFAULT_EPISODE
        defaultVideoShouldBeFound("episode.equals=" + DEFAULT_EPISODE);

        // Get all the videoList where episode equals to UPDATED_EPISODE
        defaultVideoShouldNotBeFound("episode.equals=" + UPDATED_EPISODE);
    }

    @Test
    @Transactional
    public void getAllVideosByEpisodeIsInShouldWork() throws Exception {
        // Initialize the database
        videoRepository.saveAndFlush(video);

        // Get all the videoList where episode in DEFAULT_EPISODE or UPDATED_EPISODE
        defaultVideoShouldBeFound("episode.in=" + DEFAULT_EPISODE + "," + UPDATED_EPISODE);

        // Get all the videoList where episode equals to UPDATED_EPISODE
        defaultVideoShouldNotBeFound("episode.in=" + UPDATED_EPISODE);
    }

    @Test
    @Transactional
    public void getAllVideosByEpisodeIsNullOrNotNull() throws Exception {
        // Initialize the database
        videoRepository.saveAndFlush(video);

        // Get all the videoList where episode is not null
        defaultVideoShouldBeFound("episode.specified=true");

        // Get all the videoList where episode is null
        defaultVideoShouldNotBeFound("episode.specified=false");
    }

    @Test
    @Transactional
    public void getAllVideosByEpisodeIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        videoRepository.saveAndFlush(video);

        // Get all the videoList where episode greater than or equals to DEFAULT_EPISODE
        defaultVideoShouldBeFound("episode.greaterOrEqualThan=" + DEFAULT_EPISODE);

        // Get all the videoList where episode greater than or equals to UPDATED_EPISODE
        defaultVideoShouldNotBeFound("episode.greaterOrEqualThan=" + UPDATED_EPISODE);
    }

    @Test
    @Transactional
    public void getAllVideosByEpisodeIsLessThanSomething() throws Exception {
        // Initialize the database
        videoRepository.saveAndFlush(video);

        // Get all the videoList where episode less than or equals to DEFAULT_EPISODE
        defaultVideoShouldNotBeFound("episode.lessThan=" + DEFAULT_EPISODE);

        // Get all the videoList where episode less than or equals to UPDATED_EPISODE
        defaultVideoShouldBeFound("episode.lessThan=" + UPDATED_EPISODE);
    }


    @Test
    @Transactional
    public void getAllVideosBySoundcloudIsEqualToSomething() throws Exception {
        // Initialize the database
        videoRepository.saveAndFlush(video);

        // Get all the videoList where soundcloud equals to DEFAULT_SOUNDCLOUD
        defaultVideoShouldBeFound("soundcloud.equals=" + DEFAULT_SOUNDCLOUD);

        // Get all the videoList where soundcloud equals to UPDATED_SOUNDCLOUD
        defaultVideoShouldNotBeFound("soundcloud.equals=" + UPDATED_SOUNDCLOUD);
    }

    @Test
    @Transactional
    public void getAllVideosBySoundcloudIsInShouldWork() throws Exception {
        // Initialize the database
        videoRepository.saveAndFlush(video);

        // Get all the videoList where soundcloud in DEFAULT_SOUNDCLOUD or UPDATED_SOUNDCLOUD
        defaultVideoShouldBeFound("soundcloud.in=" + DEFAULT_SOUNDCLOUD + "," + UPDATED_SOUNDCLOUD);

        // Get all the videoList where soundcloud equals to UPDATED_SOUNDCLOUD
        defaultVideoShouldNotBeFound("soundcloud.in=" + UPDATED_SOUNDCLOUD);
    }

    @Test
    @Transactional
    public void getAllVideosBySoundcloudIsNullOrNotNull() throws Exception {
        // Initialize the database
        videoRepository.saveAndFlush(video);

        // Get all the videoList where soundcloud is not null
        defaultVideoShouldBeFound("soundcloud.specified=true");

        // Get all the videoList where soundcloud is null
        defaultVideoShouldNotBeFound("soundcloud.specified=false");
    }

    @Test
    @Transactional
    public void getAllVideosByCaptionIdIsEqualToSomething() throws Exception {
        // Initialize the database
        Caption captionId = CaptionResourceIntTest.createEntity(em);
        em.persist(captionId);
        em.flush();
        video.addCaptionId(captionId);
        videoRepository.saveAndFlush(video);
        Long captionIdId = captionId.getId();

        // Get all the videoList where captionId equals to captionIdId
        defaultVideoShouldBeFound("captionIdId.equals=" + captionIdId);

        // Get all the videoList where captionId equals to captionIdId + 1
        defaultVideoShouldNotBeFound("captionIdId.equals=" + (captionIdId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned
     */
    private void defaultVideoShouldBeFound(String filter) throws Exception {
        restVideoMockMvc.perform(get("/api/videos?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(video.getId().intValue())))
            .andExpect(jsonPath("$.[*].videoId").value(hasItem(DEFAULT_VIDEO_ID)))
            .andExpect(jsonPath("$.[*].title").value(hasItem(DEFAULT_TITLE)))
            .andExpect(jsonPath("$.[*].episode").value(hasItem(DEFAULT_EPISODE)))
            .andExpect(jsonPath("$.[*].soundcloud").value(hasItem(DEFAULT_SOUNDCLOUD)));

        // Check, that the count call also returns 1
        restVideoMockMvc.perform(get("/api/videos/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned
     */
    private void defaultVideoShouldNotBeFound(String filter) throws Exception {
        restVideoMockMvc.perform(get("/api/videos?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restVideoMockMvc.perform(get("/api/videos/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingVideo() throws Exception {
        // Get the video
        restVideoMockMvc.perform(get("/api/videos/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateVideo() throws Exception {
        // Initialize the database
        videoRepository.saveAndFlush(video);

        int databaseSizeBeforeUpdate = videoRepository.findAll().size();

        // Update the video
        Video updatedVideo = videoRepository.findById(video.getId()).get();
        // Disconnect from session so that the updates on updatedVideo are not directly saved in db
        em.detach(updatedVideo);
        updatedVideo
            .videoId(UPDATED_VIDEO_ID)
            .title(UPDATED_TITLE)
            .episode(UPDATED_EPISODE)
            .soundcloud(UPDATED_SOUNDCLOUD);
        VideoDTO videoDTO = videoMapper.toDto(updatedVideo);

        restVideoMockMvc.perform(put("/api/videos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(videoDTO)))
            .andExpect(status().isOk());

        // Validate the Video in the database
        List<Video> videoList = videoRepository.findAll();
        assertThat(videoList).hasSize(databaseSizeBeforeUpdate);
        Video testVideo = videoList.get(videoList.size() - 1);
        assertThat(testVideo.getVideoId()).isEqualTo(UPDATED_VIDEO_ID);
        assertThat(testVideo.getTitle()).isEqualTo(UPDATED_TITLE);
        assertThat(testVideo.getEpisode()).isEqualTo(UPDATED_EPISODE);
        assertThat(testVideo.getSoundcloud()).isEqualTo(UPDATED_SOUNDCLOUD);
    }

    @Test
    @Transactional
    public void updateNonExistingVideo() throws Exception {
        int databaseSizeBeforeUpdate = videoRepository.findAll().size();

        // Create the Video
        VideoDTO videoDTO = videoMapper.toDto(video);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restVideoMockMvc.perform(put("/api/videos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(videoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Video in the database
        List<Video> videoList = videoRepository.findAll();
        assertThat(videoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteVideo() throws Exception {
        // Initialize the database
        videoRepository.saveAndFlush(video);

        int databaseSizeBeforeDelete = videoRepository.findAll().size();

        // Delete the video
        restVideoMockMvc.perform(delete("/api/videos/{id}", video.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Video> videoList = videoRepository.findAll();
        assertThat(videoList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Video.class);
        Video video1 = new Video();
        video1.setId(1L);
        Video video2 = new Video();
        video2.setId(video1.getId());
        assertThat(video1).isEqualTo(video2);
        video2.setId(2L);
        assertThat(video1).isNotEqualTo(video2);
        video1.setId(null);
        assertThat(video1).isNotEqualTo(video2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(VideoDTO.class);
        VideoDTO videoDTO1 = new VideoDTO();
        videoDTO1.setId(1L);
        VideoDTO videoDTO2 = new VideoDTO();
        assertThat(videoDTO1).isNotEqualTo(videoDTO2);
        videoDTO2.setId(videoDTO1.getId());
        assertThat(videoDTO1).isEqualTo(videoDTO2);
        videoDTO2.setId(2L);
        assertThat(videoDTO1).isNotEqualTo(videoDTO2);
        videoDTO1.setId(null);
        assertThat(videoDTO1).isNotEqualTo(videoDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(videoMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(videoMapper.fromId(null)).isNull();
    }
}
