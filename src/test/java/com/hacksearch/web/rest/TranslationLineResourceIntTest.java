package com.hacksearch.web.rest;

import com.hacksearch.HackSearchApp;
import com.hacksearch.domain.Caption;
import com.hacksearch.domain.TranslationLine;
import com.hacksearch.repository.TranslationLineRepository;
import com.hacksearch.service.TranslationLineQueryService;
import com.hacksearch.service.TranslationLineService;
import com.hacksearch.service.dto.TranslationLineDTO;
import com.hacksearch.service.mapper.TranslationLineMapper;
import com.hacksearch.web.rest.errors.ExceptionTranslator;
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
 * Test class for the TranslationLineResource REST controller.
 *
 * @see TranslationLineResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = HackSearchApp.class)
public class TranslationLineResourceIntTest {

    private static final Integer DEFAULT_SND_ID = 1;
    private static final Integer UPDATED_SND_ID = 2;

    private static final String DEFAULT_TEXT = "AAAAAAAAAA";
    private static final String UPDATED_TEXT = "BBBBBBBBBB";

    private static final String DEFAULT_TIME_START = "AAAAAAAAAA";
    private static final String UPDATED_TIME_START = "BBBBBBBBBB";

    private static final String DEFAULT_TIME_END = "AAAAAAAAAA";
    private static final String UPDATED_TIME_END = "BBBBBBBBBB";

    private static final Integer DEFAULT_EPISODE = 1;
    private static final Integer UPDATED_EPISODE = 2;

    @Autowired
    private TranslationLineRepository translationLineRepository;

    @Autowired
    private TranslationLineMapper translationLineMapper;

    @Autowired
    private TranslationLineService translationLineService;

    @Autowired
    private TranslationLineQueryService translationLineQueryService;

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

    private MockMvc restTranslationLineMockMvc;

    private TranslationLine translationLine;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final TranslationLineResource translationLineResource = new TranslationLineResource(translationLineService, translationLineQueryService);
        this.restTranslationLineMockMvc = MockMvcBuilders.standaloneSetup(translationLineResource)
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
    public static TranslationLine createEntity(EntityManager em) {
        TranslationLine translationLine = new TranslationLine()
            .sndId(DEFAULT_SND_ID)
            .text(DEFAULT_TEXT)
            .timeStart(DEFAULT_TIME_START)
            .timeEnd(DEFAULT_TIME_END)
            .episode(DEFAULT_EPISODE);
        return translationLine;
    }

    @Before
    public void initTest() {
        translationLine = createEntity(em);
    }

    @Test
    @Transactional
    public void createTranslationLine() throws Exception {
        int databaseSizeBeforeCreate = translationLineRepository.findAll().size();

        // Create the TranslationLine
        TranslationLineDTO translationLineDTO = translationLineMapper.toDto(translationLine);
        restTranslationLineMockMvc.perform(post("/api/translation-lines")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(translationLineDTO)))
            .andExpect(status().isCreated());

        // Validate the TranslationLine in the database
        List<TranslationLine> translationLineList = translationLineRepository.findAll();
        assertThat(translationLineList).hasSize(databaseSizeBeforeCreate + 1);
        TranslationLine testTranslationLine = translationLineList.get(translationLineList.size() - 1);
        assertThat(testTranslationLine.getSndId()).isEqualTo(DEFAULT_SND_ID);
        assertThat(testTranslationLine.getText()).isEqualTo(DEFAULT_TEXT);
        assertThat(testTranslationLine.getTimeStart()).isEqualTo(DEFAULT_TIME_START);
        assertThat(testTranslationLine.getTimeEnd()).isEqualTo(DEFAULT_TIME_END);
        assertThat(testTranslationLine.getEpisode()).isEqualTo(DEFAULT_EPISODE);
    }

    @Test
    @Transactional
    public void createTranslationLineWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = translationLineRepository.findAll().size();

        // Create the TranslationLine with an existing ID
        translationLine.setId(1L);
        TranslationLineDTO translationLineDTO = translationLineMapper.toDto(translationLine);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTranslationLineMockMvc.perform(post("/api/translation-lines")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(translationLineDTO)))
            .andExpect(status().isBadRequest());

        // Validate the TranslationLine in the database
        List<TranslationLine> translationLineList = translationLineRepository.findAll();
        assertThat(translationLineList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllTranslationLines() throws Exception {
        // Initialize the database
        translationLineRepository.saveAndFlush(translationLine);

        // Get all the translationLineList
        restTranslationLineMockMvc.perform(get("/api/translation-lines?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(translationLine.getId().intValue())))
            .andExpect(jsonPath("$.[*].sndId").value(hasItem(DEFAULT_SND_ID)))
            .andExpect(jsonPath("$.[*].text").value(hasItem(DEFAULT_TEXT.toString())))
            .andExpect(jsonPath("$.[*].timeStart").value(hasItem(DEFAULT_TIME_START.toString())))
            .andExpect(jsonPath("$.[*].timeEnd").value(hasItem(DEFAULT_TIME_END.toString())))
            .andExpect(jsonPath("$.[*].episode").value(hasItem(DEFAULT_EPISODE)));
    }
    
    @Test
    @Transactional
    public void getTranslationLine() throws Exception {
        // Initialize the database
        translationLineRepository.saveAndFlush(translationLine);

        // Get the translationLine
        restTranslationLineMockMvc.perform(get("/api/translation-lines/{id}", translationLine.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(translationLine.getId().intValue()))
            .andExpect(jsonPath("$.sndId").value(DEFAULT_SND_ID))
            .andExpect(jsonPath("$.text").value(DEFAULT_TEXT.toString()))
            .andExpect(jsonPath("$.timeStart").value(DEFAULT_TIME_START.toString()))
            .andExpect(jsonPath("$.timeEnd").value(DEFAULT_TIME_END.toString()))
            .andExpect(jsonPath("$.episode").value(DEFAULT_EPISODE));
    }

    @Test
    @Transactional
    public void getAllTranslationLinesBySndIdIsEqualToSomething() throws Exception {
        // Initialize the database
        translationLineRepository.saveAndFlush(translationLine);

        // Get all the translationLineList where sndId equals to DEFAULT_SND_ID
        defaultTranslationLineShouldBeFound("sndId.equals=" + DEFAULT_SND_ID);

        // Get all the translationLineList where sndId equals to UPDATED_SND_ID
        defaultTranslationLineShouldNotBeFound("sndId.equals=" + UPDATED_SND_ID);
    }

    @Test
    @Transactional
    public void getAllTranslationLinesBySndIdIsInShouldWork() throws Exception {
        // Initialize the database
        translationLineRepository.saveAndFlush(translationLine);

        // Get all the translationLineList where sndId in DEFAULT_SND_ID or UPDATED_SND_ID
        defaultTranslationLineShouldBeFound("sndId.in=" + DEFAULT_SND_ID + "," + UPDATED_SND_ID);

        // Get all the translationLineList where sndId equals to UPDATED_SND_ID
        defaultTranslationLineShouldNotBeFound("sndId.in=" + UPDATED_SND_ID);
    }

    @Test
    @Transactional
    public void getAllTranslationLinesBySndIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        translationLineRepository.saveAndFlush(translationLine);

        // Get all the translationLineList where sndId is not null
        defaultTranslationLineShouldBeFound("sndId.specified=true");

        // Get all the translationLineList where sndId is null
        defaultTranslationLineShouldNotBeFound("sndId.specified=false");
    }

    @Test
    @Transactional
    public void getAllTranslationLinesBySndIdIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        translationLineRepository.saveAndFlush(translationLine);

        // Get all the translationLineList where sndId greater than or equals to DEFAULT_SND_ID
        defaultTranslationLineShouldBeFound("sndId.greaterOrEqualThan=" + DEFAULT_SND_ID);

        // Get all the translationLineList where sndId greater than or equals to UPDATED_SND_ID
        defaultTranslationLineShouldNotBeFound("sndId.greaterOrEqualThan=" + UPDATED_SND_ID);
    }

    @Test
    @Transactional
    public void getAllTranslationLinesBySndIdIsLessThanSomething() throws Exception {
        // Initialize the database
        translationLineRepository.saveAndFlush(translationLine);

        // Get all the translationLineList where sndId less than or equals to DEFAULT_SND_ID
        defaultTranslationLineShouldNotBeFound("sndId.lessThan=" + DEFAULT_SND_ID);

        // Get all the translationLineList where sndId less than or equals to UPDATED_SND_ID
        defaultTranslationLineShouldBeFound("sndId.lessThan=" + UPDATED_SND_ID);
    }


    @Test
    @Transactional
    public void getAllTranslationLinesByTextIsEqualToSomething() throws Exception {
        // Initialize the database
        translationLineRepository.saveAndFlush(translationLine);

        // Get all the translationLineList where text equals to DEFAULT_TEXT
        defaultTranslationLineShouldBeFound("text.equals=" + DEFAULT_TEXT);

        // Get all the translationLineList where text equals to UPDATED_TEXT
        defaultTranslationLineShouldNotBeFound("text.equals=" + UPDATED_TEXT);
    }

    @Test
    @Transactional
    public void getAllTranslationLinesByTextIsInShouldWork() throws Exception {
        // Initialize the database
        translationLineRepository.saveAndFlush(translationLine);

        // Get all the translationLineList where text in DEFAULT_TEXT or UPDATED_TEXT
        defaultTranslationLineShouldBeFound("text.in=" + DEFAULT_TEXT + "," + UPDATED_TEXT);

        // Get all the translationLineList where text equals to UPDATED_TEXT
        defaultTranslationLineShouldNotBeFound("text.in=" + UPDATED_TEXT);
    }

    @Test
    @Transactional
    public void getAllTranslationLinesByTextIsNullOrNotNull() throws Exception {
        // Initialize the database
        translationLineRepository.saveAndFlush(translationLine);

        // Get all the translationLineList where text is not null
        defaultTranslationLineShouldBeFound("text.specified=true");

        // Get all the translationLineList where text is null
        defaultTranslationLineShouldNotBeFound("text.specified=false");
    }

    @Test
    @Transactional
    public void getAllTranslationLinesByTimeStartIsEqualToSomething() throws Exception {
        // Initialize the database
        translationLineRepository.saveAndFlush(translationLine);

        // Get all the translationLineList where timeStart equals to DEFAULT_TIME_START
        defaultTranslationLineShouldBeFound("timeStart.equals=" + DEFAULT_TIME_START);

        // Get all the translationLineList where timeStart equals to UPDATED_TIME_START
        defaultTranslationLineShouldNotBeFound("timeStart.equals=" + UPDATED_TIME_START);
    }

    @Test
    @Transactional
    public void getAllTranslationLinesByTimeStartIsInShouldWork() throws Exception {
        // Initialize the database
        translationLineRepository.saveAndFlush(translationLine);

        // Get all the translationLineList where timeStart in DEFAULT_TIME_START or UPDATED_TIME_START
        defaultTranslationLineShouldBeFound("timeStart.in=" + DEFAULT_TIME_START + "," + UPDATED_TIME_START);

        // Get all the translationLineList where timeStart equals to UPDATED_TIME_START
        defaultTranslationLineShouldNotBeFound("timeStart.in=" + UPDATED_TIME_START);
    }

    @Test
    @Transactional
    public void getAllTranslationLinesByTimeStartIsNullOrNotNull() throws Exception {
        // Initialize the database
        translationLineRepository.saveAndFlush(translationLine);

        // Get all the translationLineList where timeStart is not null
        defaultTranslationLineShouldBeFound("timeStart.specified=true");

        // Get all the translationLineList where timeStart is null
        defaultTranslationLineShouldNotBeFound("timeStart.specified=false");
    }

    @Test
    @Transactional
    public void getAllTranslationLinesByTimeEndIsEqualToSomething() throws Exception {
        // Initialize the database
        translationLineRepository.saveAndFlush(translationLine);

        // Get all the translationLineList where timeEnd equals to DEFAULT_TIME_END
        defaultTranslationLineShouldBeFound("timeEnd.equals=" + DEFAULT_TIME_END);

        // Get all the translationLineList where timeEnd equals to UPDATED_TIME_END
        defaultTranslationLineShouldNotBeFound("timeEnd.equals=" + UPDATED_TIME_END);
    }

    @Test
    @Transactional
    public void getAllTranslationLinesByTimeEndIsInShouldWork() throws Exception {
        // Initialize the database
        translationLineRepository.saveAndFlush(translationLine);

        // Get all the translationLineList where timeEnd in DEFAULT_TIME_END or UPDATED_TIME_END
        defaultTranslationLineShouldBeFound("timeEnd.in=" + DEFAULT_TIME_END + "," + UPDATED_TIME_END);

        // Get all the translationLineList where timeEnd equals to UPDATED_TIME_END
        defaultTranslationLineShouldNotBeFound("timeEnd.in=" + UPDATED_TIME_END);
    }

    @Test
    @Transactional
    public void getAllTranslationLinesByTimeEndIsNullOrNotNull() throws Exception {
        // Initialize the database
        translationLineRepository.saveAndFlush(translationLine);

        // Get all the translationLineList where timeEnd is not null
        defaultTranslationLineShouldBeFound("timeEnd.specified=true");

        // Get all the translationLineList where timeEnd is null
        defaultTranslationLineShouldNotBeFound("timeEnd.specified=false");
    }

    @Test
    @Transactional
    public void getAllTranslationLinesByEpisodeIsEqualToSomething() throws Exception {
        // Initialize the database
        translationLineRepository.saveAndFlush(translationLine);

        // Get all the translationLineList where episode equals to DEFAULT_EPISODE
        defaultTranslationLineShouldBeFound("episode.equals=" + DEFAULT_EPISODE);

        // Get all the translationLineList where episode equals to UPDATED_EPISODE
        defaultTranslationLineShouldNotBeFound("episode.equals=" + UPDATED_EPISODE);
    }

    @Test
    @Transactional
    public void getAllTranslationLinesByEpisodeIsInShouldWork() throws Exception {
        // Initialize the database
        translationLineRepository.saveAndFlush(translationLine);

        // Get all the translationLineList where episode in DEFAULT_EPISODE or UPDATED_EPISODE
        defaultTranslationLineShouldBeFound("episode.in=" + DEFAULT_EPISODE + "," + UPDATED_EPISODE);

        // Get all the translationLineList where episode equals to UPDATED_EPISODE
        defaultTranslationLineShouldNotBeFound("episode.in=" + UPDATED_EPISODE);
    }

    @Test
    @Transactional
    public void getAllTranslationLinesByEpisodeIsNullOrNotNull() throws Exception {
        // Initialize the database
        translationLineRepository.saveAndFlush(translationLine);

        // Get all the translationLineList where episode is not null
        defaultTranslationLineShouldBeFound("episode.specified=true");

        // Get all the translationLineList where episode is null
        defaultTranslationLineShouldNotBeFound("episode.specified=false");
    }

    @Test
    @Transactional
    public void getAllTranslationLinesByEpisodeIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        translationLineRepository.saveAndFlush(translationLine);

        // Get all the translationLineList where episode greater than or equals to DEFAULT_EPISODE
        defaultTranslationLineShouldBeFound("episode.greaterOrEqualThan=" + DEFAULT_EPISODE);

        // Get all the translationLineList where episode greater than or equals to UPDATED_EPISODE
        defaultTranslationLineShouldNotBeFound("episode.greaterOrEqualThan=" + UPDATED_EPISODE);
    }

    @Test
    @Transactional
    public void getAllTranslationLinesByEpisodeIsLessThanSomething() throws Exception {
        // Initialize the database
        translationLineRepository.saveAndFlush(translationLine);

        // Get all the translationLineList where episode less than or equals to DEFAULT_EPISODE
        defaultTranslationLineShouldNotBeFound("episode.lessThan=" + DEFAULT_EPISODE);

        // Get all the translationLineList where episode less than or equals to UPDATED_EPISODE
        defaultTranslationLineShouldBeFound("episode.lessThan=" + UPDATED_EPISODE);
    }


    @Test
    @Transactional
    public void getAllTranslationLinesByCaptionIdIsEqualToSomething() throws Exception {
        // Initialize the database
        Caption captionId = CaptionResourceIntTest.createEntity(em);
        em.persist(captionId);
        em.flush();
        translationLine.setCaptionId(captionId);
        translationLineRepository.saveAndFlush(translationLine);
        Long captionIdId = captionId.getId();

        // Get all the translationLineList where captionId equals to captionIdId
        defaultTranslationLineShouldBeFound("captionIdId.equals=" + captionIdId);

        // Get all the translationLineList where captionId equals to captionIdId + 1
        defaultTranslationLineShouldNotBeFound("captionIdId.equals=" + (captionIdId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned
     */
    private void defaultTranslationLineShouldBeFound(String filter) throws Exception {
        restTranslationLineMockMvc.perform(get("/api/translation-lines?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(translationLine.getId().intValue())))
            .andExpect(jsonPath("$.[*].sndId").value(hasItem(DEFAULT_SND_ID)))
            .andExpect(jsonPath("$.[*].text").value(hasItem(DEFAULT_TEXT)))
            .andExpect(jsonPath("$.[*].timeStart").value(hasItem(DEFAULT_TIME_START)))
            .andExpect(jsonPath("$.[*].timeEnd").value(hasItem(DEFAULT_TIME_END)))
            .andExpect(jsonPath("$.[*].episode").value(hasItem(DEFAULT_EPISODE)));

        // Check, that the count call also returns 1
        restTranslationLineMockMvc.perform(get("/api/translation-lines/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned
     */
    private void defaultTranslationLineShouldNotBeFound(String filter) throws Exception {
        restTranslationLineMockMvc.perform(get("/api/translation-lines?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restTranslationLineMockMvc.perform(get("/api/translation-lines/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingTranslationLine() throws Exception {
        // Get the translationLine
        restTranslationLineMockMvc.perform(get("/api/translation-lines/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTranslationLine() throws Exception {
        // Initialize the database
        translationLineRepository.saveAndFlush(translationLine);

        int databaseSizeBeforeUpdate = translationLineRepository.findAll().size();

        // Update the translationLine
        TranslationLine updatedTranslationLine = translationLineRepository.findById(translationLine.getId()).get();
        // Disconnect from session so that the updates on updatedTranslationLine are not directly saved in db
        em.detach(updatedTranslationLine);
        updatedTranslationLine
            .sndId(UPDATED_SND_ID)
            .text(UPDATED_TEXT)
            .timeStart(UPDATED_TIME_START)
            .timeEnd(UPDATED_TIME_END)
            .episode(UPDATED_EPISODE);
        TranslationLineDTO translationLineDTO = translationLineMapper.toDto(updatedTranslationLine);

        restTranslationLineMockMvc.perform(put("/api/translation-lines")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(translationLineDTO)))
            .andExpect(status().isOk());

        // Validate the TranslationLine in the database
        List<TranslationLine> translationLineList = translationLineRepository.findAll();
        assertThat(translationLineList).hasSize(databaseSizeBeforeUpdate);
        TranslationLine testTranslationLine = translationLineList.get(translationLineList.size() - 1);
        assertThat(testTranslationLine.getSndId()).isEqualTo(UPDATED_SND_ID);
        assertThat(testTranslationLine.getText()).isEqualTo(UPDATED_TEXT);
        assertThat(testTranslationLine.getTimeStart()).isEqualTo(UPDATED_TIME_START);
        assertThat(testTranslationLine.getTimeEnd()).isEqualTo(UPDATED_TIME_END);
        assertThat(testTranslationLine.getEpisode()).isEqualTo(UPDATED_EPISODE);
    }

    @Test
    @Transactional
    public void updateNonExistingTranslationLine() throws Exception {
        int databaseSizeBeforeUpdate = translationLineRepository.findAll().size();

        // Create the TranslationLine
        TranslationLineDTO translationLineDTO = translationLineMapper.toDto(translationLine);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTranslationLineMockMvc.perform(put("/api/translation-lines")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(translationLineDTO)))
            .andExpect(status().isBadRequest());

        // Validate the TranslationLine in the database
        List<TranslationLine> translationLineList = translationLineRepository.findAll();
        assertThat(translationLineList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteTranslationLine() throws Exception {
        // Initialize the database
        translationLineRepository.saveAndFlush(translationLine);

        int databaseSizeBeforeDelete = translationLineRepository.findAll().size();

        // Delete the translationLine
        restTranslationLineMockMvc.perform(delete("/api/translation-lines/{id}", translationLine.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<TranslationLine> translationLineList = translationLineRepository.findAll();
        assertThat(translationLineList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TranslationLine.class);
        TranslationLine translationLine1 = new TranslationLine();
        translationLine1.setId(1L);
        TranslationLine translationLine2 = new TranslationLine();
        translationLine2.setId(translationLine1.getId());
        assertThat(translationLine1).isEqualTo(translationLine2);
        translationLine2.setId(2L);
        assertThat(translationLine1).isNotEqualTo(translationLine2);
        translationLine1.setId(null);
        assertThat(translationLine1).isNotEqualTo(translationLine2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(TranslationLineDTO.class);
        TranslationLineDTO translationLineDTO1 = new TranslationLineDTO();
        translationLineDTO1.setId(1L);
        TranslationLineDTO translationLineDTO2 = new TranslationLineDTO();
        assertThat(translationLineDTO1).isNotEqualTo(translationLineDTO2);
        translationLineDTO2.setId(translationLineDTO1.getId());
        assertThat(translationLineDTO1).isEqualTo(translationLineDTO2);
        translationLineDTO2.setId(2L);
        assertThat(translationLineDTO1).isNotEqualTo(translationLineDTO2);
        translationLineDTO1.setId(null);
        assertThat(translationLineDTO1).isNotEqualTo(translationLineDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(translationLineMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(translationLineMapper.fromId(null)).isNull();
    }
}
