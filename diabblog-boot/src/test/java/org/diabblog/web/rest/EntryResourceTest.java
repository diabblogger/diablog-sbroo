package org.diabblog.web.rest;

import org.diabblog.Application;
import org.diabblog.domain.Entry;
import org.diabblog.repository.EntryRepository;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.hamcrest.Matchers.hasItem;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the EntryResource REST controller.
 *
 * @see EntryResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class EntryResourceTest {

    private static final DateTimeFormatter dateTimeFormatter = DateTimeFormat.forPattern("yyyy-MM-dd'T'HH:mm:ss'Z'");


    private static final BigDecimal DEFAULT_BLOOD_SUGAR_LEVEL = BigDecimal.ZERO;
    private static final BigDecimal UPDATED_BLOOD_SUGAR_LEVEL = BigDecimal.ONE;
    private static final String DEFAULT_CARBS = "SAMPLE_TEXT";
    private static final String UPDATED_CARBS = "UPDATED_TEXT";
    private static final String DEFAULT_COMMENTS = "SAMPLE_TEXT";
    private static final String UPDATED_COMMENTS = "UPDATED_TEXT";
    private static final String DEFAULT_NOTES = "SAMPLE_TEXT";
    private static final String UPDATED_NOTES = "UPDATED_TEXT";
    private static final String DEFAULT_CORRECTION = "SAMPLE_TEXT";
    private static final String UPDATED_CORRECTION = "UPDATED_TEXT";

    private static final DateTime DEFAULT_CREATED = new DateTime(0L, DateTimeZone.UTC);
    private static final DateTime UPDATED_CREATED = new DateTime(DateTimeZone.UTC).withMillisOfSecond(0);
    private static final String DEFAULT_CREATED_STR = dateTimeFormatter.print(DEFAULT_CREATED);

    private static final Integer DEFAULT_FAST_INSULIN = 0;
    private static final Integer UPDATED_FAST_INSULIN = 1;

    private static final Integer DEFAULT_SLOW_INSULIN = 0;
    private static final Integer UPDATED_SLOW_INSULIN = 1;

    @Inject
    private EntryRepository entryRepository;

    private MockMvc restEntryMockMvc;

    private Entry entry;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        EntryResource entryResource = new EntryResource();
        ReflectionTestUtils.setField(entryResource, "entryRepository", entryRepository);
        this.restEntryMockMvc = MockMvcBuilders.standaloneSetup(entryResource).build();
    }

    @Before
    public void initTest() {
        entry = new Entry();
        entry.setBloodSugarLevel(DEFAULT_BLOOD_SUGAR_LEVEL);
        entry.setCarbs(DEFAULT_CARBS);
        entry.setComments(DEFAULT_COMMENTS);
        entry.setNotes(DEFAULT_NOTES);
        entry.setCorrection(DEFAULT_CORRECTION);
        entry.setCreated(DEFAULT_CREATED);
        entry.setFastInsulin(DEFAULT_FAST_INSULIN);
        entry.setSlowInsulin(DEFAULT_SLOW_INSULIN);
    }

    @Test
    @Transactional
    public void createEntry() throws Exception {
        int databaseSizeBeforeCreate = entryRepository.findAll().size();

        // Create the Entry
        restEntryMockMvc.perform(post("/api/entrys")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(entry)))
                .andExpect(status().isCreated());

        // Validate the Entry in the database
        List<Entry> entrys = entryRepository.findAll();
        assertThat(entrys).hasSize(databaseSizeBeforeCreate + 1);
        Entry testEntry = entrys.get(entrys.size() - 1);
        assertThat(testEntry.getBloodSugarLevel()).isEqualTo(DEFAULT_BLOOD_SUGAR_LEVEL);
        assertThat(testEntry.getCarbs()).isEqualTo(DEFAULT_CARBS);
        assertThat(testEntry.getComments()).isEqualTo(DEFAULT_COMMENTS);
        assertThat(testEntry.getNotes()).isEqualTo(DEFAULT_NOTES);
        assertThat(testEntry.getCorrection()).isEqualTo(DEFAULT_CORRECTION);
        assertThat(testEntry.getCreated().toDateTime(DateTimeZone.UTC)).isEqualTo(DEFAULT_CREATED);
        assertThat(testEntry.getFastInsulin()).isEqualTo(DEFAULT_FAST_INSULIN);
        assertThat(testEntry.getSlowInsulin()).isEqualTo(DEFAULT_SLOW_INSULIN);
    }

    @Test
    @Transactional
    public void getAllEntrys() throws Exception {
        // Initialize the database
        entryRepository.saveAndFlush(entry);

        // Get all the entrys
        restEntryMockMvc.perform(get("/api/entrys"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(entry.getId().intValue())))
                .andExpect(jsonPath("$.[*].bloodSugarLevel").value(hasItem(DEFAULT_BLOOD_SUGAR_LEVEL.intValue())))
                .andExpect(jsonPath("$.[*].carbs").value(hasItem(DEFAULT_CARBS.toString())))
                .andExpect(jsonPath("$.[*].comments").value(hasItem(DEFAULT_COMMENTS.toString())))
                .andExpect(jsonPath("$.[*].notes").value(hasItem(DEFAULT_NOTES.toString())))
                .andExpect(jsonPath("$.[*].correction").value(hasItem(DEFAULT_CORRECTION.toString())))
                .andExpect(jsonPath("$.[*].created").value(hasItem(DEFAULT_CREATED_STR)))
                .andExpect(jsonPath("$.[*].fastInsulin").value(hasItem(DEFAULT_FAST_INSULIN)))
                .andExpect(jsonPath("$.[*].slowInsulin").value(hasItem(DEFAULT_SLOW_INSULIN)));
    }

    @Test
    @Transactional
    public void getEntry() throws Exception {
        // Initialize the database
        entryRepository.saveAndFlush(entry);

        // Get the entry
        restEntryMockMvc.perform(get("/api/entrys/{id}", entry.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(entry.getId().intValue()))
            .andExpect(jsonPath("$.bloodSugarLevel").value(DEFAULT_BLOOD_SUGAR_LEVEL.intValue()))
            .andExpect(jsonPath("$.carbs").value(DEFAULT_CARBS.toString()))
            .andExpect(jsonPath("$.comments").value(DEFAULT_COMMENTS.toString()))
            .andExpect(jsonPath("$.notes").value(DEFAULT_NOTES.toString()))
            .andExpect(jsonPath("$.correction").value(DEFAULT_CORRECTION.toString()))
            .andExpect(jsonPath("$.created").value(DEFAULT_CREATED_STR))
            .andExpect(jsonPath("$.fastInsulin").value(DEFAULT_FAST_INSULIN))
            .andExpect(jsonPath("$.slowInsulin").value(DEFAULT_SLOW_INSULIN));
    }

    @Test
    @Transactional
    public void getNonExistingEntry() throws Exception {
        // Get the entry
        restEntryMockMvc.perform(get("/api/entrys/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateEntry() throws Exception {
        // Initialize the database
        entryRepository.saveAndFlush(entry);
		
		int databaseSizeBeforeUpdate = entryRepository.findAll().size();

        // Update the entry
        entry.setBloodSugarLevel(UPDATED_BLOOD_SUGAR_LEVEL);
        entry.setCarbs(UPDATED_CARBS);
        entry.setComments(UPDATED_COMMENTS);
        entry.setNotes(UPDATED_NOTES);
        entry.setCorrection(UPDATED_CORRECTION);
        entry.setCreated(UPDATED_CREATED);
        entry.setFastInsulin(UPDATED_FAST_INSULIN);
        entry.setSlowInsulin(UPDATED_SLOW_INSULIN);
        restEntryMockMvc.perform(put("/api/entrys")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(entry)))
                .andExpect(status().isOk());

        // Validate the Entry in the database
        List<Entry> entrys = entryRepository.findAll();
        assertThat(entrys).hasSize(databaseSizeBeforeUpdate);
        Entry testEntry = entrys.get(entrys.size() - 1);
        assertThat(testEntry.getBloodSugarLevel()).isEqualTo(UPDATED_BLOOD_SUGAR_LEVEL);
        assertThat(testEntry.getCarbs()).isEqualTo(UPDATED_CARBS);
        assertThat(testEntry.getComments()).isEqualTo(UPDATED_COMMENTS);
        assertThat(testEntry.getNotes()).isEqualTo(UPDATED_NOTES);
        assertThat(testEntry.getCorrection()).isEqualTo(UPDATED_CORRECTION);
        assertThat(testEntry.getCreated().toDateTime(DateTimeZone.UTC)).isEqualTo(UPDATED_CREATED);
        assertThat(testEntry.getFastInsulin()).isEqualTo(UPDATED_FAST_INSULIN);
        assertThat(testEntry.getSlowInsulin()).isEqualTo(UPDATED_SLOW_INSULIN);
    }

    @Test
    @Transactional
    public void deleteEntry() throws Exception {
        // Initialize the database
        entryRepository.saveAndFlush(entry);
		
		int databaseSizeBeforeDelete = entryRepository.findAll().size();

        // Get the entry
        restEntryMockMvc.perform(delete("/api/entrys/{id}", entry.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Entry> entrys = entryRepository.findAll();
        assertThat(entrys).hasSize(databaseSizeBeforeDelete - 1);
    }
}
