package com.sophatel.winpharm.web.rest;

import com.sophatel.winpharm.WinpharmApp;
import com.sophatel.winpharm.domain.EnteteVente;
import com.sophatel.winpharm.repository.EnteteVenteRepository;
import com.sophatel.winpharm.service.EnteteVenteService;
import com.sophatel.winpharm.web.rest.errors.ExceptionTranslator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
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

import static com.sophatel.winpharm.web.rest.TestUtil.sameInstant;
import static com.sophatel.winpharm.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@Link EnteteVenteResource} REST controller.
 */
@SpringBootTest(classes = WinpharmApp.class)
public class EnteteVenteResourceIT {

    private static final Double DEFAULT_ENTETE_VENTE_TOTAL_HT = 1D;
    private static final Double UPDATED_ENTETE_VENTE_TOTAL_HT = 2D;

    private static final Double DEFAULT_ENTETE_VENTE_TOTAL_TTC = 1D;
    private static final Double UPDATED_ENTETE_VENTE_TOTAL_TTC = 2D;

    private static final String DEFAULT_ENTETE_VENTE_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_ENTETE_VENTE_TYPE = "BBBBBBBBBB";

    private static final ZonedDateTime DEFAULT_ENTETE_VENTE_DATE_CREATION = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_ENTETE_VENTE_DATE_CREATION = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    @Autowired
    private EnteteVenteRepository enteteVenteRepository;

    @Autowired
    private EnteteVenteService enteteVenteService;

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

    private MockMvc restEnteteVenteMockMvc;

    private EnteteVente enteteVente;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final EnteteVenteResource enteteVenteResource = new EnteteVenteResource(enteteVenteService);
        this.restEnteteVenteMockMvc = MockMvcBuilders.standaloneSetup(enteteVenteResource)
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
    public static EnteteVente createEntity(EntityManager em) {
        EnteteVente enteteVente = new EnteteVente()
            .enteteVenteTotalHT(DEFAULT_ENTETE_VENTE_TOTAL_HT)
            .enteteVenteTotalTTC(DEFAULT_ENTETE_VENTE_TOTAL_TTC)
            .enteteVenteType(DEFAULT_ENTETE_VENTE_TYPE)
            .enteteVenteDateCreation(DEFAULT_ENTETE_VENTE_DATE_CREATION);
        return enteteVente;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static EnteteVente createUpdatedEntity(EntityManager em) {
        EnteteVente enteteVente = new EnteteVente()
            .enteteVenteTotalHT(UPDATED_ENTETE_VENTE_TOTAL_HT)
            .enteteVenteTotalTTC(UPDATED_ENTETE_VENTE_TOTAL_TTC)
            .enteteVenteType(UPDATED_ENTETE_VENTE_TYPE)
            .enteteVenteDateCreation(UPDATED_ENTETE_VENTE_DATE_CREATION);
        return enteteVente;
    }

    @BeforeEach
    public void initTest() {
        enteteVente = createEntity(em);
    }

    @Test
    @Transactional
    public void createEnteteVente() throws Exception {
        int databaseSizeBeforeCreate = enteteVenteRepository.findAll().size();

        // Create the EnteteVente
        restEnteteVenteMockMvc.perform(post("/api/entete-ventes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(enteteVente)))
            .andExpect(status().isCreated());

        // Validate the EnteteVente in the database
        List<EnteteVente> enteteVenteList = enteteVenteRepository.findAll();
        assertThat(enteteVenteList).hasSize(databaseSizeBeforeCreate + 1);
        EnteteVente testEnteteVente = enteteVenteList.get(enteteVenteList.size() - 1);
        assertThat(testEnteteVente.getEnteteVenteTotalHT()).isEqualTo(DEFAULT_ENTETE_VENTE_TOTAL_HT);
        assertThat(testEnteteVente.getEnteteVenteTotalTTC()).isEqualTo(DEFAULT_ENTETE_VENTE_TOTAL_TTC);
        assertThat(testEnteteVente.getEnteteVenteType()).isEqualTo(DEFAULT_ENTETE_VENTE_TYPE);
        assertThat(testEnteteVente.getEnteteVenteDateCreation()).isEqualTo(DEFAULT_ENTETE_VENTE_DATE_CREATION);
    }

    @Test
    @Transactional
    public void createEnteteVenteWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = enteteVenteRepository.findAll().size();

        // Create the EnteteVente with an existing ID
        enteteVente.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restEnteteVenteMockMvc.perform(post("/api/entete-ventes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(enteteVente)))
            .andExpect(status().isBadRequest());

        // Validate the EnteteVente in the database
        List<EnteteVente> enteteVenteList = enteteVenteRepository.findAll();
        assertThat(enteteVenteList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkEnteteVenteTotalHTIsRequired() throws Exception {
        int databaseSizeBeforeTest = enteteVenteRepository.findAll().size();
        // set the field null
        enteteVente.setEnteteVenteTotalHT(null);

        // Create the EnteteVente, which fails.

        restEnteteVenteMockMvc.perform(post("/api/entete-ventes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(enteteVente)))
            .andExpect(status().isBadRequest());

        List<EnteteVente> enteteVenteList = enteteVenteRepository.findAll();
        assertThat(enteteVenteList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkEnteteVenteTotalTTCIsRequired() throws Exception {
        int databaseSizeBeforeTest = enteteVenteRepository.findAll().size();
        // set the field null
        enteteVente.setEnteteVenteTotalTTC(null);

        // Create the EnteteVente, which fails.

        restEnteteVenteMockMvc.perform(post("/api/entete-ventes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(enteteVente)))
            .andExpect(status().isBadRequest());

        List<EnteteVente> enteteVenteList = enteteVenteRepository.findAll();
        assertThat(enteteVenteList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkEnteteVenteTypeIsRequired() throws Exception {
        int databaseSizeBeforeTest = enteteVenteRepository.findAll().size();
        // set the field null
        enteteVente.setEnteteVenteType(null);

        // Create the EnteteVente, which fails.

        restEnteteVenteMockMvc.perform(post("/api/entete-ventes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(enteteVente)))
            .andExpect(status().isBadRequest());

        List<EnteteVente> enteteVenteList = enteteVenteRepository.findAll();
        assertThat(enteteVenteList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkEnteteVenteDateCreationIsRequired() throws Exception {
        int databaseSizeBeforeTest = enteteVenteRepository.findAll().size();
        // set the field null
        enteteVente.setEnteteVenteDateCreation(null);

        // Create the EnteteVente, which fails.

        restEnteteVenteMockMvc.perform(post("/api/entete-ventes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(enteteVente)))
            .andExpect(status().isBadRequest());

        List<EnteteVente> enteteVenteList = enteteVenteRepository.findAll();
        assertThat(enteteVenteList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllEnteteVentes() throws Exception {
        // Initialize the database
        enteteVenteRepository.saveAndFlush(enteteVente);

        // Get all the enteteVenteList
        restEnteteVenteMockMvc.perform(get("/api/entete-ventes?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(enteteVente.getId().intValue())))
            .andExpect(jsonPath("$.[*].enteteVenteTotalHT").value(hasItem(DEFAULT_ENTETE_VENTE_TOTAL_HT.doubleValue())))
            .andExpect(jsonPath("$.[*].enteteVenteTotalTTC").value(hasItem(DEFAULT_ENTETE_VENTE_TOTAL_TTC.doubleValue())))
            .andExpect(jsonPath("$.[*].enteteVenteType").value(hasItem(DEFAULT_ENTETE_VENTE_TYPE.toString())))
            .andExpect(jsonPath("$.[*].enteteVenteDateCreation").value(hasItem(sameInstant(DEFAULT_ENTETE_VENTE_DATE_CREATION))));
    }
    
    @Test
    @Transactional
    public void getEnteteVente() throws Exception {
        // Initialize the database
        enteteVenteRepository.saveAndFlush(enteteVente);

        // Get the enteteVente
        restEnteteVenteMockMvc.perform(get("/api/entete-ventes/{id}", enteteVente.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(enteteVente.getId().intValue()))
            .andExpect(jsonPath("$.enteteVenteTotalHT").value(DEFAULT_ENTETE_VENTE_TOTAL_HT.doubleValue()))
            .andExpect(jsonPath("$.enteteVenteTotalTTC").value(DEFAULT_ENTETE_VENTE_TOTAL_TTC.doubleValue()))
            .andExpect(jsonPath("$.enteteVenteType").value(DEFAULT_ENTETE_VENTE_TYPE.toString()))
            .andExpect(jsonPath("$.enteteVenteDateCreation").value(sameInstant(DEFAULT_ENTETE_VENTE_DATE_CREATION)));
    }

    @Test
    @Transactional
    public void getNonExistingEnteteVente() throws Exception {
        // Get the enteteVente
        restEnteteVenteMockMvc.perform(get("/api/entete-ventes/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateEnteteVente() throws Exception {
        // Initialize the database
        enteteVenteService.save(enteteVente);

        int databaseSizeBeforeUpdate = enteteVenteRepository.findAll().size();

        // Update the enteteVente
        EnteteVente updatedEnteteVente = enteteVenteRepository.findById(enteteVente.getId()).get();
        // Disconnect from session so that the updates on updatedEnteteVente are not directly saved in db
        em.detach(updatedEnteteVente);
        updatedEnteteVente
            .enteteVenteTotalHT(UPDATED_ENTETE_VENTE_TOTAL_HT)
            .enteteVenteTotalTTC(UPDATED_ENTETE_VENTE_TOTAL_TTC)
            .enteteVenteType(UPDATED_ENTETE_VENTE_TYPE)
            .enteteVenteDateCreation(UPDATED_ENTETE_VENTE_DATE_CREATION);

        restEnteteVenteMockMvc.perform(put("/api/entete-ventes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedEnteteVente)))
            .andExpect(status().isOk());

        // Validate the EnteteVente in the database
        List<EnteteVente> enteteVenteList = enteteVenteRepository.findAll();
        assertThat(enteteVenteList).hasSize(databaseSizeBeforeUpdate);
        EnteteVente testEnteteVente = enteteVenteList.get(enteteVenteList.size() - 1);
        assertThat(testEnteteVente.getEnteteVenteTotalHT()).isEqualTo(UPDATED_ENTETE_VENTE_TOTAL_HT);
        assertThat(testEnteteVente.getEnteteVenteTotalTTC()).isEqualTo(UPDATED_ENTETE_VENTE_TOTAL_TTC);
        assertThat(testEnteteVente.getEnteteVenteType()).isEqualTo(UPDATED_ENTETE_VENTE_TYPE);
        assertThat(testEnteteVente.getEnteteVenteDateCreation()).isEqualTo(UPDATED_ENTETE_VENTE_DATE_CREATION);
    }

    @Test
    @Transactional
    public void updateNonExistingEnteteVente() throws Exception {
        int databaseSizeBeforeUpdate = enteteVenteRepository.findAll().size();

        // Create the EnteteVente

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEnteteVenteMockMvc.perform(put("/api/entete-ventes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(enteteVente)))
            .andExpect(status().isBadRequest());

        // Validate the EnteteVente in the database
        List<EnteteVente> enteteVenteList = enteteVenteRepository.findAll();
        assertThat(enteteVenteList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteEnteteVente() throws Exception {
        // Initialize the database
        enteteVenteService.save(enteteVente);

        int databaseSizeBeforeDelete = enteteVenteRepository.findAll().size();

        // Delete the enteteVente
        restEnteteVenteMockMvc.perform(delete("/api/entete-ventes/{id}", enteteVente.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database is empty
        List<EnteteVente> enteteVenteList = enteteVenteRepository.findAll();
        assertThat(enteteVenteList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(EnteteVente.class);
        EnteteVente enteteVente1 = new EnteteVente();
        enteteVente1.setId(1L);
        EnteteVente enteteVente2 = new EnteteVente();
        enteteVente2.setId(enteteVente1.getId());
        assertThat(enteteVente1).isEqualTo(enteteVente2);
        enteteVente2.setId(2L);
        assertThat(enteteVente1).isNotEqualTo(enteteVente2);
        enteteVente1.setId(null);
        assertThat(enteteVente1).isNotEqualTo(enteteVente2);
    }
}
