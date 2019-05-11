package com.hacksearch.web.rest;

import com.hacksearch.HackSearchApp;

import com.hacksearch.domain.Caption;
import com.hacksearch.domain.TranslationLine;
import com.hacksearch.domain.Video;
import com.hacksearch.repository.CaptionRepository;
import com.hacksearch.service.CaptionService;
import com.hacksearch.service.dto.CaptionDTO;
import com.hacksearch.service.mapper.CaptionMapper;
import com.hacksearch.web.rest.errors.ExceptionTranslator;
import com.hacksearch.service.dto.CaptionCriteria;
import com.hacksearch.service.CaptionQueryService;

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
 * Test class for the CaptionResource REST controller.
 *
 * @see CaptionResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = HackSearchApp.class)
public class CaptionResourceIntTest {

    private static final String DEFAULT_CAPTION_ID = "AAAAAAAAAA";
    private static final String UPDATED_CAPTION_ID = "BBBBBBBBBB";

    private static final String DEFAULT_TRACK_KIND = "AAAAAAAAAA";
    private static final String UPDATED_TRACK_KIND = "BBBBBBBBBB";

    private static final String DEFAULT_LANGUAGE = "AAAAAAAAAA";
    private static final String UPDATED_LANGUAGE = "BBBBBBBBBB";

    @Autowired
    private CaptionRepository captionRepository;

    @Autowired
    private CaptionMapper captionMapper;

    @Autowired
    private CaptionService captionService;

    @Autowired
    private CaptionQueryService captionQueryService;

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

    private MockMvc restCaptionMockMvc;

    private Caption caption;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final CaptionResource captionResource = new CaptionResource(captionService, captionQueryService);
        this.restCaptionMockMvc = MockMvcBuilders.standaloneSetup(captionResource)
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
    public static Caption createEntity(EntityManager em) {
        Caption caption = new Caption()
            .captionId(DEFAULT_CAPTION_ID)
            .trackKind(DEFAULT_TRACK_KIND)
            .language(DEFAULT_LANGUAGE);
        return caption;
    }

    @Before
    public void initTest() {
        caption = createEntity(em);
    }

    @Test
    @Transactional
    public void createCaption() throws Exception {
        int databaseSizeBeforeCreate = captionRepository.findAll().size();

        // Create the Caption
        CaptionDTO captionDTO = captionMapper.toDto(caption);
        restCaptionMockMvc.perform(post("/api/captions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(captionDTO)))
            .andExpect(status().isCreated());

        // Validate the Caption in the database
        List<Caption> captionList = captionRepository.findAll();
        assertThat(captionList).hasSize(databaseSizeBeforeCreate + 1);
        Caption testCaption = captionList.get(captionList.size() - 1);
        assertThat(testCaption.getCaptionId()).isEqualTo(DEFAULT_CAPTION_ID);
        assertThat(testCaption.getTrackKind()).isEqualTo(DEFAULT_TRACK_KIND);
        assertThat(testCaption.getLanguage()).isEqualTo(DEFAULT_LANGUAGE);
    }

    @Test
    @Transactional
    public void createCaptionWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = captionRepository.findAll().size();

        // Create the Caption with an existing ID
        caption.setId(1L);
        CaptionDTO captionDTO = captionMapper.toDto(caption);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCaptionMockMvc.perform(post("/api/captions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(captionDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Caption in the database
        List<Caption> captionList = captionRepository.findAll();
        assertThat(captionList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllCaptions() throws Exception {
        // Initialize the database
        captionRepository.saveAndFlush(caption);

        // Get all the captionList
        restCaptionMockMvc.perform(get("/api/captions?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(caption.getId().intValue())))
            .andExpect(jsonPath("$.[*].captionId").value(hasItem(DEFAULT_CAPTION_ID.toString())))
            .andExpect(jsonPath("$.[*].trackKind").value(hasItem(DEFAULT_TRACK_KIND.toString())))
            .andExpect(jsonPath("$.[*].language").value(hasItem(DEFAULT_LANGUAGE.toString())));
    }
    
    @Test
    @Transactional
    public void getCaption() throws Exception {
        // Initialize the database
        captionRepository.saveAndFlush(caption);

        // Get the caption
        restCaptionMockMvc.perform(get("/api/captions/{id}", caption.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(caption.getId().intValue()))
            .andExpect(jsonPath("$.captionId").value(DEFAULT_CAPTION_ID.toString()))
            .andExpect(jsonPath("$.trackKind").value(DEFAULT_TRACK_KIND.toString()))
            .andExpect(jsonPath("$.language").value(DEFAULT_LANGUAGE.toString()));
    }

    @Test
    @Transactional
    public void getAllCaptionsByCaptionIdIsEqualToSomething() throws Exception {
        // Initialize the database
        captionRepository.saveAndFlush(caption);

        // Get all the captionList where captionId equals to DEFAULT_CAPTION_ID
        defaultCaptionShouldBeFound("captionId.equals=" + DEFAULT_CAPTION_ID);

        // Get all the captionList where captionId equals to UPDATED_CAPTION_ID
        defaultCaptionShouldNotBeFound("captionId.equals=" + UPDATED_CAPTION_ID);
    }

    @Test
    @Transactional
    public void getAllCaptionsByCaptionIdIsInShouldWork() throws Exception {
        // Initialize the database
        captionRepository.saveAndFlush(caption);

        // Get all the captionList where captionId in DEFAULT_CAPTION_ID or UPDATED_CAPTION_ID
        defaultCaptionShouldBeFound("captionId.in=" + DEFAULT_CAPTION_ID + "," + UPDATED_CAPTION_ID);

        // Get all the captionList where captionId equals to UPDATED_CAPTION_ID
        defaultCaptionShouldNotBeFound("captionId.in=" + UPDATED_CAPTION_ID);
    }

    @Test
    @Transactional
    public void getAllCaptionsByCaptionIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        captionRepository.saveAndFlush(caption);

        // Get all the captionList where captionId is not null
        defaultCaptionShouldBeFound("captionId.specified=true");

        // Get all the captionList where captionId is null
        defaultCaptionShouldNotBeFound("captionId.specified=false");
    }

    @Test
    @Transactional
    public void getAllCaptionsByTrackKindIsEqualToSomething() throws Exception {
        // Initialize the database
        captionRepository.saveAndFlush(caption);

        // Get all the captionList where trackKind equals to DEFAULT_TRACK_KIND
        defaultCaptionShouldBeFound("trackKind.equals=" + DEFAULT_TRACK_KIND);

        // Get all the captionList where trackKind equals to UPDATED_TRACK_KIND
        defaultCaptionShouldNotBeFound("trackKind.equals=" + UPDATED_TRACK_KIND);
    }

    @Test
    @Transactional
    public void getAllCaptionsByTrackKindIsInShouldWork() throws Exception {
        // Initialize the database
        captionRepository.saveAndFlush(caption);

        // Get all the captionList where trackKind in DEFAULT_TRACK_KIND or UPDATED_TRACK_KIND
        defaultCaptionShouldBeFound("trackKind.in=" + DEFAULT_TRACK_KIND + "," + UPDATED_TRACK_KIND);

        // Get all the captionList where trackKind equals to UPDATED_TRACK_KIND
        defaultCaptionShouldNotBeFound("trackKind.in=" + UPDATED_TRACK_KIND);
    }

    @Test
    @Transactional
    public void getAllCaptionsByTrackKindIsNullOrNotNull() throws Exception {
        // Initialize the database
        captionRepository.saveAndFlush(caption);

        // Get all the captionList where trackKind is not null
        defaultCaptionShouldBeFound("trackKind.specified=true");

        // Get all the captionList where trackKind is null
        defaultCaptionShouldNotBeFound("trackKind.specified=false");
    }

    @Test
    @Transactional
    public void getAllCaptionsByLanguageIsEqualToSomething() throws Exception {
        // Initialize the database
        captionRepository.saveAndFlush(caption);

        // Get all the captionList where language equals to DEFAULT_LANGUAGE
        defaultCaptionShouldBeFound("language.equals=" + DEFAULT_LANGUAGE);

        // Get all the captionList where language equals to UPDATED_LANGUAGE
        defaultCaptionShouldNotBeFound("language.equals=" + UPDATED_LANGUAGE);
    }

    @Test
    @Transactional
    public void getAllCaptionsByLanguageIsInShouldWork() throws Exception {
        // Initialize the database
        captionRepository.saveAndFlush(caption);

        // Get all the captionList where language in DEFAULT_LANGUAGE or UPDATED_LANGUAGE
        defaultCaptionShouldBeFound("language.in=" + DEFAULT_LANGUAGE + "," + UPDATED_LANGUAGE);

        // Get all the captionList where language equals to UPDATED_LANGUAGE
        defaultCaptionShouldNotBeFound("language.in=" + UPDATED_LANGUAGE);
    }

    @Test
    @Transactional
    public void getAllCaptionsByLanguageIsNullOrNotNull() throws Exception {
        // Initialize the database
        captionRepository.saveAndFlush(caption);

        // Get all the captionList where language is not null
        defaultCaptionShouldBeFound("language.specified=true");

        // Get all the captionList where language is null
        defaultCaptionShouldNotBeFound("language.specified=false");
    }

    @Test
    @Transactional
    public void getAllCaptionsByTranslationLineIsEqualToSomething() throws Exception {
        // Initialize the database
        TranslationLine translationLine = TranslationLineResourceIntTest.createEntity(em);
        em.persist(translationLine);
        em.flush();
        caption.addTranslationLine(translationLine);
        captionRepository.saveAndFlush(caption);
        Long translationLineId = translationLine.getId();

        // Get all the captionList where translationLine equals to translationLineId
        defaultCaptionShouldBeFound("translationLineId.equals=" + translationLineId);

        // Get all the captionList where translationLine equals to translationLineId + 1
        defaultCaptionShouldNotBeFound("translationLineId.equals=" + (translationLineId + 1));
    }


    @Test
    @Transactional
    public void getAllCaptionsByVideoIdIsEqualToSomething() throws Exception {
        // Initialize the database
        Video videoId = VideoResourceIntTest.createEntity(em);
        em.persist(videoId);
        em.flush();
        caption.setVideoId(videoId);
        captionRepository.saveAndFlush(caption);
        Long videoIdId = videoId.getId();

        // Get all the captionList where videoId equals to videoIdId
        defaultCaptionShouldBeFound("videoIdId.equals=" + videoIdId);

        // Get all the captionList where videoId equals to videoIdId + 1
        defaultCaptionShouldNotBeFound("videoIdId.equals=" + (videoIdId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned
     */
    private void defaultCaptionShouldBeFound(String filter) throws Exception {
        restCaptionMockMvc.perform(get("/api/captions?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(caption.getId().intValue())))
            .andExpect(jsonPath("$.[*].captionId").value(hasItem(DEFAULT_CAPTION_ID)))
            .andExpect(jsonPath("$.[*].trackKind").value(hasItem(DEFAULT_TRACK_KIND)))
            .andExpect(jsonPath("$.[*].language").value(hasItem(DEFAULT_LANGUAGE)));

        // Check, that the count call also returns 1
        restCaptionMockMvc.perform(get("/api/captions/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned
     */
    private void defaultCaptionShouldNotBeFound(String filter) throws Exception {
        restCaptionMockMvc.perform(get("/api/captions?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restCaptionMockMvc.perform(get("/api/captions/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingCaption() throws Exception {
        // Get the caption
        restCaptionMockMvc.perform(get("/api/captions/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCaption() throws Exception {
        // Initialize the database
        captionRepository.saveAndFlush(caption);

        int databaseSizeBeforeUpdate = captionRepository.findAll().size();

        // Update the caption
        Caption updatedCaption = captionRepository.findById(caption.getId()).get();
        // Disconnect from session so that the updates on updatedCaption are not directly saved in db
        em.detach(updatedCaption);
        updatedCaption
            .captionId(UPDATED_CAPTION_ID)
            .trackKind(UPDATED_TRACK_KIND)
            .language(UPDATED_LANGUAGE);
        CaptionDTO captionDTO = captionMapper.toDto(updatedCaption);

        restCaptionMockMvc.perform(put("/api/captions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(captionDTO)))
            .andExpect(status().isOk());

        // Validate the Caption in the database
        List<Caption> captionList = captionRepository.findAll();
        assertThat(captionList).hasSize(databaseSizeBeforeUpdate);
        Caption testCaption = captionList.get(captionList.size() - 1);
        assertThat(testCaption.getCaptionId()).isEqualTo(UPDATED_CAPTION_ID);
        assertThat(testCaption.getTrackKind()).isEqualTo(UPDATED_TRACK_KIND);
        assertThat(testCaption.getLanguage()).isEqualTo(UPDATED_LANGUAGE);
    }

    @Test
    @Transactional
    public void updateNonExistingCaption() throws Exception {
        int databaseSizeBeforeUpdate = captionRepository.findAll().size();

        // Create the Caption
        CaptionDTO captionDTO = captionMapper.toDto(caption);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCaptionMockMvc.perform(put("/api/captions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(captionDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Caption in the database
        List<Caption> captionList = captionRepository.findAll();
        assertThat(captionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteCaption() throws Exception {
        // Initialize the database
        captionRepository.saveAndFlush(caption);

        int databaseSizeBeforeDelete = captionRepository.findAll().size();

        // Delete the caption
        restCaptionMockMvc.perform(delete("/api/captions/{id}", caption.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Caption> captionList = captionRepository.findAll();
        assertThat(captionList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Caption.class);
        Caption caption1 = new Caption();
        caption1.setId(1L);
        Caption caption2 = new Caption();
        caption2.setId(caption1.getId());
        assertThat(caption1).isEqualTo(caption2);
        caption2.setId(2L);
        assertThat(caption1).isNotEqualTo(caption2);
        caption1.setId(null);
        assertThat(caption1).isNotEqualTo(caption2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CaptionDTO.class);
        CaptionDTO captionDTO1 = new CaptionDTO();
        captionDTO1.setId(1L);
        CaptionDTO captionDTO2 = new CaptionDTO();
        assertThat(captionDTO1).isNotEqualTo(captionDTO2);
        captionDTO2.setId(captionDTO1.getId());
        assertThat(captionDTO1).isEqualTo(captionDTO2);
        captionDTO2.setId(2L);
        assertThat(captionDTO1).isNotEqualTo(captionDTO2);
        captionDTO1.setId(null);
        assertThat(captionDTO1).isNotEqualTo(captionDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(captionMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(captionMapper.fromId(null)).isNull();
    }
}
