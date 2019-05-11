package com.hacksearch.web.rest;

import com.hacksearch.HackSearchApp;

import com.hacksearch.domain.SearchQuery;
import com.hacksearch.repository.SearchQueryRepository;
import com.hacksearch.service.SearchQueryService;
import com.hacksearch.service.dto.SearchQueryDTO;
import com.hacksearch.service.mapper.SearchQueryMapper;
import com.hacksearch.web.rest.errors.ExceptionTranslator;
import com.hacksearch.service.dto.SearchQueryCriteria;
import com.hacksearch.service.SearchQueryQueryService;

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
import java.time.Instant;
import java.time.ZonedDateTime;
import java.time.ZoneOffset;
import java.time.ZoneId;
import java.util.List;


import static com.hacksearch.web.rest.TestUtil.sameInstant;
import static com.hacksearch.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the SearchQueryResource REST controller.
 *
 * @see SearchQueryResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = HackSearchApp.class)
public class SearchQueryResourceIntTest {

    private static final String DEFAULT_QUERY = "AAAAAAAAAA";
    private static final String UPDATED_QUERY = "BBBBBBBBBB";

    private static final Integer DEFAULT_EPISODE = 1;
    private static final Integer UPDATED_EPISODE = 2;

    private static final ZonedDateTime DEFAULT_CREATED_AT = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_CREATED_AT = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    @Autowired
    private SearchQueryRepository searchQueryRepository;

    @Autowired
    private SearchQueryMapper searchQueryMapper;

    @Autowired
    private SearchQueryService searchQueryService;

    @Autowired
    private SearchQueryQueryService searchQueryQueryService;

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

    private MockMvc restSearchQueryMockMvc;

    private SearchQuery searchQuery;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final SearchQueryResource searchQueryResource = new SearchQueryResource(searchQueryService, searchQueryQueryService);
        this.restSearchQueryMockMvc = MockMvcBuilders.standaloneSetup(searchQueryResource)
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
    public static SearchQuery createEntity(EntityManager em) {
        SearchQuery searchQuery = new SearchQuery()
            .query(DEFAULT_QUERY)
            .episode(DEFAULT_EPISODE)
            .createdAt(DEFAULT_CREATED_AT);
        return searchQuery;
    }

    @Before
    public void initTest() {
        searchQuery = createEntity(em);
    }

    @Test
    @Transactional
    public void createSearchQuery() throws Exception {
        int databaseSizeBeforeCreate = searchQueryRepository.findAll().size();

        // Create the SearchQuery
        SearchQueryDTO searchQueryDTO = searchQueryMapper.toDto(searchQuery);
        restSearchQueryMockMvc.perform(post("/api/search-queries")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(searchQueryDTO)))
            .andExpect(status().isCreated());

        // Validate the SearchQuery in the database
        List<SearchQuery> searchQueryList = searchQueryRepository.findAll();
        assertThat(searchQueryList).hasSize(databaseSizeBeforeCreate + 1);
        SearchQuery testSearchQuery = searchQueryList.get(searchQueryList.size() - 1);
        assertThat(testSearchQuery.getQuery()).isEqualTo(DEFAULT_QUERY);
        assertThat(testSearchQuery.getEpisode()).isEqualTo(DEFAULT_EPISODE);
        assertThat(testSearchQuery.getCreatedAt()).isEqualTo(DEFAULT_CREATED_AT);
    }

    @Test
    @Transactional
    public void createSearchQueryWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = searchQueryRepository.findAll().size();

        // Create the SearchQuery with an existing ID
        searchQuery.setId(1L);
        SearchQueryDTO searchQueryDTO = searchQueryMapper.toDto(searchQuery);

        // An entity with an existing ID cannot be created, so this API call must fail
        restSearchQueryMockMvc.perform(post("/api/search-queries")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(searchQueryDTO)))
            .andExpect(status().isBadRequest());

        // Validate the SearchQuery in the database
        List<SearchQuery> searchQueryList = searchQueryRepository.findAll();
        assertThat(searchQueryList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllSearchQueries() throws Exception {
        // Initialize the database
        searchQueryRepository.saveAndFlush(searchQuery);

        // Get all the searchQueryList
        restSearchQueryMockMvc.perform(get("/api/search-queries?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(searchQuery.getId().intValue())))
            .andExpect(jsonPath("$.[*].query").value(hasItem(DEFAULT_QUERY.toString())))
            .andExpect(jsonPath("$.[*].episode").value(hasItem(DEFAULT_EPISODE)))
            .andExpect(jsonPath("$.[*].createdAt").value(hasItem(sameInstant(DEFAULT_CREATED_AT))));
    }
    
    @Test
    @Transactional
    public void getSearchQuery() throws Exception {
        // Initialize the database
        searchQueryRepository.saveAndFlush(searchQuery);

        // Get the searchQuery
        restSearchQueryMockMvc.perform(get("/api/search-queries/{id}", searchQuery.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(searchQuery.getId().intValue()))
            .andExpect(jsonPath("$.query").value(DEFAULT_QUERY.toString()))
            .andExpect(jsonPath("$.episode").value(DEFAULT_EPISODE))
            .andExpect(jsonPath("$.createdAt").value(sameInstant(DEFAULT_CREATED_AT)));
    }

    @Test
    @Transactional
    public void getAllSearchQueriesByQueryIsEqualToSomething() throws Exception {
        // Initialize the database
        searchQueryRepository.saveAndFlush(searchQuery);

        // Get all the searchQueryList where query equals to DEFAULT_QUERY
        defaultSearchQueryShouldBeFound("query.equals=" + DEFAULT_QUERY);

        // Get all the searchQueryList where query equals to UPDATED_QUERY
        defaultSearchQueryShouldNotBeFound("query.equals=" + UPDATED_QUERY);
    }

    @Test
    @Transactional
    public void getAllSearchQueriesByQueryIsInShouldWork() throws Exception {
        // Initialize the database
        searchQueryRepository.saveAndFlush(searchQuery);

        // Get all the searchQueryList where query in DEFAULT_QUERY or UPDATED_QUERY
        defaultSearchQueryShouldBeFound("query.in=" + DEFAULT_QUERY + "," + UPDATED_QUERY);

        // Get all the searchQueryList where query equals to UPDATED_QUERY
        defaultSearchQueryShouldNotBeFound("query.in=" + UPDATED_QUERY);
    }

    @Test
    @Transactional
    public void getAllSearchQueriesByQueryIsNullOrNotNull() throws Exception {
        // Initialize the database
        searchQueryRepository.saveAndFlush(searchQuery);

        // Get all the searchQueryList where query is not null
        defaultSearchQueryShouldBeFound("query.specified=true");

        // Get all the searchQueryList where query is null
        defaultSearchQueryShouldNotBeFound("query.specified=false");
    }

    @Test
    @Transactional
    public void getAllSearchQueriesByEpisodeIsEqualToSomething() throws Exception {
        // Initialize the database
        searchQueryRepository.saveAndFlush(searchQuery);

        // Get all the searchQueryList where episode equals to DEFAULT_EPISODE
        defaultSearchQueryShouldBeFound("episode.equals=" + DEFAULT_EPISODE);

        // Get all the searchQueryList where episode equals to UPDATED_EPISODE
        defaultSearchQueryShouldNotBeFound("episode.equals=" + UPDATED_EPISODE);
    }

    @Test
    @Transactional
    public void getAllSearchQueriesByEpisodeIsInShouldWork() throws Exception {
        // Initialize the database
        searchQueryRepository.saveAndFlush(searchQuery);

        // Get all the searchQueryList where episode in DEFAULT_EPISODE or UPDATED_EPISODE
        defaultSearchQueryShouldBeFound("episode.in=" + DEFAULT_EPISODE + "," + UPDATED_EPISODE);

        // Get all the searchQueryList where episode equals to UPDATED_EPISODE
        defaultSearchQueryShouldNotBeFound("episode.in=" + UPDATED_EPISODE);
    }

    @Test
    @Transactional
    public void getAllSearchQueriesByEpisodeIsNullOrNotNull() throws Exception {
        // Initialize the database
        searchQueryRepository.saveAndFlush(searchQuery);

        // Get all the searchQueryList where episode is not null
        defaultSearchQueryShouldBeFound("episode.specified=true");

        // Get all the searchQueryList where episode is null
        defaultSearchQueryShouldNotBeFound("episode.specified=false");
    }

    @Test
    @Transactional
    public void getAllSearchQueriesByEpisodeIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        searchQueryRepository.saveAndFlush(searchQuery);

        // Get all the searchQueryList where episode greater than or equals to DEFAULT_EPISODE
        defaultSearchQueryShouldBeFound("episode.greaterOrEqualThan=" + DEFAULT_EPISODE);

        // Get all the searchQueryList where episode greater than or equals to UPDATED_EPISODE
        defaultSearchQueryShouldNotBeFound("episode.greaterOrEqualThan=" + UPDATED_EPISODE);
    }

    @Test
    @Transactional
    public void getAllSearchQueriesByEpisodeIsLessThanSomething() throws Exception {
        // Initialize the database
        searchQueryRepository.saveAndFlush(searchQuery);

        // Get all the searchQueryList where episode less than or equals to DEFAULT_EPISODE
        defaultSearchQueryShouldNotBeFound("episode.lessThan=" + DEFAULT_EPISODE);

        // Get all the searchQueryList where episode less than or equals to UPDATED_EPISODE
        defaultSearchQueryShouldBeFound("episode.lessThan=" + UPDATED_EPISODE);
    }


    @Test
    @Transactional
    public void getAllSearchQueriesByCreatedAtIsEqualToSomething() throws Exception {
        // Initialize the database
        searchQueryRepository.saveAndFlush(searchQuery);

        // Get all the searchQueryList where createdAt equals to DEFAULT_CREATED_AT
        defaultSearchQueryShouldBeFound("createdAt.equals=" + DEFAULT_CREATED_AT);

        // Get all the searchQueryList where createdAt equals to UPDATED_CREATED_AT
        defaultSearchQueryShouldNotBeFound("createdAt.equals=" + UPDATED_CREATED_AT);
    }

    @Test
    @Transactional
    public void getAllSearchQueriesByCreatedAtIsInShouldWork() throws Exception {
        // Initialize the database
        searchQueryRepository.saveAndFlush(searchQuery);

        // Get all the searchQueryList where createdAt in DEFAULT_CREATED_AT or UPDATED_CREATED_AT
        defaultSearchQueryShouldBeFound("createdAt.in=" + DEFAULT_CREATED_AT + "," + UPDATED_CREATED_AT);

        // Get all the searchQueryList where createdAt equals to UPDATED_CREATED_AT
        defaultSearchQueryShouldNotBeFound("createdAt.in=" + UPDATED_CREATED_AT);
    }

    @Test
    @Transactional
    public void getAllSearchQueriesByCreatedAtIsNullOrNotNull() throws Exception {
        // Initialize the database
        searchQueryRepository.saveAndFlush(searchQuery);

        // Get all the searchQueryList where createdAt is not null
        defaultSearchQueryShouldBeFound("createdAt.specified=true");

        // Get all the searchQueryList where createdAt is null
        defaultSearchQueryShouldNotBeFound("createdAt.specified=false");
    }

    @Test
    @Transactional
    public void getAllSearchQueriesByCreatedAtIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        searchQueryRepository.saveAndFlush(searchQuery);

        // Get all the searchQueryList where createdAt greater than or equals to DEFAULT_CREATED_AT
        defaultSearchQueryShouldBeFound("createdAt.greaterOrEqualThan=" + DEFAULT_CREATED_AT);

        // Get all the searchQueryList where createdAt greater than or equals to UPDATED_CREATED_AT
        defaultSearchQueryShouldNotBeFound("createdAt.greaterOrEqualThan=" + UPDATED_CREATED_AT);
    }

    @Test
    @Transactional
    public void getAllSearchQueriesByCreatedAtIsLessThanSomething() throws Exception {
        // Initialize the database
        searchQueryRepository.saveAndFlush(searchQuery);

        // Get all the searchQueryList where createdAt less than or equals to DEFAULT_CREATED_AT
        defaultSearchQueryShouldNotBeFound("createdAt.lessThan=" + DEFAULT_CREATED_AT);

        // Get all the searchQueryList where createdAt less than or equals to UPDATED_CREATED_AT
        defaultSearchQueryShouldBeFound("createdAt.lessThan=" + UPDATED_CREATED_AT);
    }

    /**
     * Executes the search, and checks that the default entity is returned
     */
    private void defaultSearchQueryShouldBeFound(String filter) throws Exception {
        restSearchQueryMockMvc.perform(get("/api/search-queries?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(searchQuery.getId().intValue())))
            .andExpect(jsonPath("$.[*].query").value(hasItem(DEFAULT_QUERY)))
            .andExpect(jsonPath("$.[*].episode").value(hasItem(DEFAULT_EPISODE)))
            .andExpect(jsonPath("$.[*].createdAt").value(hasItem(sameInstant(DEFAULT_CREATED_AT))));

        // Check, that the count call also returns 1
        restSearchQueryMockMvc.perform(get("/api/search-queries/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned
     */
    private void defaultSearchQueryShouldNotBeFound(String filter) throws Exception {
        restSearchQueryMockMvc.perform(get("/api/search-queries?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restSearchQueryMockMvc.perform(get("/api/search-queries/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingSearchQuery() throws Exception {
        // Get the searchQuery
        restSearchQueryMockMvc.perform(get("/api/search-queries/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateSearchQuery() throws Exception {
        // Initialize the database
        searchQueryRepository.saveAndFlush(searchQuery);

        int databaseSizeBeforeUpdate = searchQueryRepository.findAll().size();

        // Update the searchQuery
        SearchQuery updatedSearchQuery = searchQueryRepository.findById(searchQuery.getId()).get();
        // Disconnect from session so that the updates on updatedSearchQuery are not directly saved in db
        em.detach(updatedSearchQuery);
        updatedSearchQuery
            .query(UPDATED_QUERY)
            .episode(UPDATED_EPISODE)
            .createdAt(UPDATED_CREATED_AT);
        SearchQueryDTO searchQueryDTO = searchQueryMapper.toDto(updatedSearchQuery);

        restSearchQueryMockMvc.perform(put("/api/search-queries")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(searchQueryDTO)))
            .andExpect(status().isOk());

        // Validate the SearchQuery in the database
        List<SearchQuery> searchQueryList = searchQueryRepository.findAll();
        assertThat(searchQueryList).hasSize(databaseSizeBeforeUpdate);
        SearchQuery testSearchQuery = searchQueryList.get(searchQueryList.size() - 1);
        assertThat(testSearchQuery.getQuery()).isEqualTo(UPDATED_QUERY);
        assertThat(testSearchQuery.getEpisode()).isEqualTo(UPDATED_EPISODE);
        assertThat(testSearchQuery.getCreatedAt()).isEqualTo(UPDATED_CREATED_AT);
    }

    @Test
    @Transactional
    public void updateNonExistingSearchQuery() throws Exception {
        int databaseSizeBeforeUpdate = searchQueryRepository.findAll().size();

        // Create the SearchQuery
        SearchQueryDTO searchQueryDTO = searchQueryMapper.toDto(searchQuery);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSearchQueryMockMvc.perform(put("/api/search-queries")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(searchQueryDTO)))
            .andExpect(status().isBadRequest());

        // Validate the SearchQuery in the database
        List<SearchQuery> searchQueryList = searchQueryRepository.findAll();
        assertThat(searchQueryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteSearchQuery() throws Exception {
        // Initialize the database
        searchQueryRepository.saveAndFlush(searchQuery);

        int databaseSizeBeforeDelete = searchQueryRepository.findAll().size();

        // Delete the searchQuery
        restSearchQueryMockMvc.perform(delete("/api/search-queries/{id}", searchQuery.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<SearchQuery> searchQueryList = searchQueryRepository.findAll();
        assertThat(searchQueryList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(SearchQuery.class);
        SearchQuery searchQuery1 = new SearchQuery();
        searchQuery1.setId(1L);
        SearchQuery searchQuery2 = new SearchQuery();
        searchQuery2.setId(searchQuery1.getId());
        assertThat(searchQuery1).isEqualTo(searchQuery2);
        searchQuery2.setId(2L);
        assertThat(searchQuery1).isNotEqualTo(searchQuery2);
        searchQuery1.setId(null);
        assertThat(searchQuery1).isNotEqualTo(searchQuery2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(SearchQueryDTO.class);
        SearchQueryDTO searchQueryDTO1 = new SearchQueryDTO();
        searchQueryDTO1.setId(1L);
        SearchQueryDTO searchQueryDTO2 = new SearchQueryDTO();
        assertThat(searchQueryDTO1).isNotEqualTo(searchQueryDTO2);
        searchQueryDTO2.setId(searchQueryDTO1.getId());
        assertThat(searchQueryDTO1).isEqualTo(searchQueryDTO2);
        searchQueryDTO2.setId(2L);
        assertThat(searchQueryDTO1).isNotEqualTo(searchQueryDTO2);
        searchQueryDTO1.setId(null);
        assertThat(searchQueryDTO1).isNotEqualTo(searchQueryDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(searchQueryMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(searchQueryMapper.fromId(null)).isNull();
    }
}
