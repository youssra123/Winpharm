package com.sophatel.winpharm.web.rest;

import com.sophatel.winpharm.WinpharmApp;
import com.sophatel.winpharm.domain.Grossiste;
import com.sophatel.winpharm.domain.Ville;
import com.sophatel.winpharm.repository.GrossisteRepository;
import com.sophatel.winpharm.service.GrossisteService;
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
import java.util.List;

import static com.sophatel.winpharm.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@Link GrossisteResource} REST controller.
 */
@SpringBootTest(classes = WinpharmApp.class)
public class GrossisteResourceIT {

    private static final String DEFAULT_GROSSISTE_RAIS_SOC = "AAAAAAAAAA";
    private static final String UPDATED_GROSSISTE_RAIS_SOC = "BBBBBBBBBB";

    private static final String DEFAULT_GROSSISTE_ADRESSE = "AAAAAAAAAA";
    private static final String UPDATED_GROSSISTE_ADRESSE = "BBBBBBBBBB";

    private static final String DEFAULT_GROSSISTE_TELEPHONE = "AAAAAAAAAA";
    private static final String UPDATED_GROSSISTE_TELEPHONE = "BBBBBBBBBB";

    @Autowired
    private GrossisteRepository grossisteRepository;

    @Autowired
    private GrossisteService grossisteService;

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

    private MockMvc restGrossisteMockMvc;

    private Grossiste grossiste;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final GrossisteResource grossisteResource = new GrossisteResource(grossisteService);
        this.restGrossisteMockMvc = MockMvcBuilders.standaloneSetup(grossisteResource)
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
    public static Grossiste createEntity(EntityManager em) {
        Grossiste grossiste = new Grossiste()
            .grossisteRaisSoc(DEFAULT_GROSSISTE_RAIS_SOC)
            .grossisteAdresse(DEFAULT_GROSSISTE_ADRESSE)
            .grossisteTelephone(DEFAULT_GROSSISTE_TELEPHONE);
        // Add required entity
        Ville ville;
        if (TestUtil.findAll(em, Ville.class).isEmpty()) {
            ville = VilleResourceIT.createEntity(em);
            em.persist(ville);
            em.flush();
        } else {
            ville = TestUtil.findAll(em, Ville.class).get(0);
        }
        grossiste.setGrossis_ville(ville);
        return grossiste;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Grossiste createUpdatedEntity(EntityManager em) {
        Grossiste grossiste = new Grossiste()
            .grossisteRaisSoc(UPDATED_GROSSISTE_RAIS_SOC)
            .grossisteAdresse(UPDATED_GROSSISTE_ADRESSE)
            .grossisteTelephone(UPDATED_GROSSISTE_TELEPHONE);
        // Add required entity
        Ville ville;
        if (TestUtil.findAll(em, Ville.class).isEmpty()) {
            ville = VilleResourceIT.createUpdatedEntity(em);
            em.persist(ville);
            em.flush();
        } else {
            ville = TestUtil.findAll(em, Ville.class).get(0);
        }
        grossiste.setGrossis_ville(ville);
        return grossiste;
    }

    @BeforeEach
    public void initTest() {
        grossiste = createEntity(em);
    }

    @Test
    @Transactional
    public void createGrossiste() throws Exception {
        int databaseSizeBeforeCreate = grossisteRepository.findAll().size();

        // Create the Grossiste
        restGrossisteMockMvc.perform(post("/api/grossistes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(grossiste)))
            .andExpect(status().isCreated());

        // Validate the Grossiste in the database
        List<Grossiste> grossisteList = grossisteRepository.findAll();
        assertThat(grossisteList).hasSize(databaseSizeBeforeCreate + 1);
        Grossiste testGrossiste = grossisteList.get(grossisteList.size() - 1);
        assertThat(testGrossiste.getGrossisteRaisSoc()).isEqualTo(DEFAULT_GROSSISTE_RAIS_SOC);
        assertThat(testGrossiste.getGrossisteAdresse()).isEqualTo(DEFAULT_GROSSISTE_ADRESSE);
        assertThat(testGrossiste.getGrossisteTelephone()).isEqualTo(DEFAULT_GROSSISTE_TELEPHONE);
    }

    @Test
    @Transactional
    public void createGrossisteWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = grossisteRepository.findAll().size();

        // Create the Grossiste with an existing ID
        grossiste.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restGrossisteMockMvc.perform(post("/api/grossistes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(grossiste)))
            .andExpect(status().isBadRequest());

        // Validate the Grossiste in the database
        List<Grossiste> grossisteList = grossisteRepository.findAll();
        assertThat(grossisteList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkGrossisteRaisSocIsRequired() throws Exception {
        int databaseSizeBeforeTest = grossisteRepository.findAll().size();
        // set the field null
        grossiste.setGrossisteRaisSoc(null);

        // Create the Grossiste, which fails.

        restGrossisteMockMvc.perform(post("/api/grossistes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(grossiste)))
            .andExpect(status().isBadRequest());

        List<Grossiste> grossisteList = grossisteRepository.findAll();
        assertThat(grossisteList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkGrossisteAdresseIsRequired() throws Exception {
        int databaseSizeBeforeTest = grossisteRepository.findAll().size();
        // set the field null
        grossiste.setGrossisteAdresse(null);

        // Create the Grossiste, which fails.

        restGrossisteMockMvc.perform(post("/api/grossistes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(grossiste)))
            .andExpect(status().isBadRequest());

        List<Grossiste> grossisteList = grossisteRepository.findAll();
        assertThat(grossisteList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkGrossisteTelephoneIsRequired() throws Exception {
        int databaseSizeBeforeTest = grossisteRepository.findAll().size();
        // set the field null
        grossiste.setGrossisteTelephone(null);

        // Create the Grossiste, which fails.

        restGrossisteMockMvc.perform(post("/api/grossistes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(grossiste)))
            .andExpect(status().isBadRequest());

        List<Grossiste> grossisteList = grossisteRepository.findAll();
        assertThat(grossisteList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllGrossistes() throws Exception {
        // Initialize the database
        grossisteRepository.saveAndFlush(grossiste);

        // Get all the grossisteList
        restGrossisteMockMvc.perform(get("/api/grossistes?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(grossiste.getId().intValue())))
            .andExpect(jsonPath("$.[*].grossisteRaisSoc").value(hasItem(DEFAULT_GROSSISTE_RAIS_SOC.toString())))
            .andExpect(jsonPath("$.[*].grossisteAdresse").value(hasItem(DEFAULT_GROSSISTE_ADRESSE.toString())))
            .andExpect(jsonPath("$.[*].grossisteTelephone").value(hasItem(DEFAULT_GROSSISTE_TELEPHONE.toString())));
    }
    
    @Test
    @Transactional
    public void getGrossiste() throws Exception {
        // Initialize the database
        grossisteRepository.saveAndFlush(grossiste);

        // Get the grossiste
        restGrossisteMockMvc.perform(get("/api/grossistes/{id}", grossiste.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(grossiste.getId().intValue()))
            .andExpect(jsonPath("$.grossisteRaisSoc").value(DEFAULT_GROSSISTE_RAIS_SOC.toString()))
            .andExpect(jsonPath("$.grossisteAdresse").value(DEFAULT_GROSSISTE_ADRESSE.toString()))
            .andExpect(jsonPath("$.grossisteTelephone").value(DEFAULT_GROSSISTE_TELEPHONE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingGrossiste() throws Exception {
        // Get the grossiste
        restGrossisteMockMvc.perform(get("/api/grossistes/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateGrossiste() throws Exception {
        // Initialize the database
        grossisteService.save(grossiste);

        int databaseSizeBeforeUpdate = grossisteRepository.findAll().size();

        // Update the grossiste
        Grossiste updatedGrossiste = grossisteRepository.findById(grossiste.getId()).get();
        // Disconnect from session so that the updates on updatedGrossiste are not directly saved in db
        em.detach(updatedGrossiste);
        updatedGrossiste
            .grossisteRaisSoc(UPDATED_GROSSISTE_RAIS_SOC)
            .grossisteAdresse(UPDATED_GROSSISTE_ADRESSE)
            .grossisteTelephone(UPDATED_GROSSISTE_TELEPHONE);

        restGrossisteMockMvc.perform(put("/api/grossistes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedGrossiste)))
            .andExpect(status().isOk());

        // Validate the Grossiste in the database
        List<Grossiste> grossisteList = grossisteRepository.findAll();
        assertThat(grossisteList).hasSize(databaseSizeBeforeUpdate);
        Grossiste testGrossiste = grossisteList.get(grossisteList.size() - 1);
        assertThat(testGrossiste.getGrossisteRaisSoc()).isEqualTo(UPDATED_GROSSISTE_RAIS_SOC);
        assertThat(testGrossiste.getGrossisteAdresse()).isEqualTo(UPDATED_GROSSISTE_ADRESSE);
        assertThat(testGrossiste.getGrossisteTelephone()).isEqualTo(UPDATED_GROSSISTE_TELEPHONE);
    }

    @Test
    @Transactional
    public void updateNonExistingGrossiste() throws Exception {
        int databaseSizeBeforeUpdate = grossisteRepository.findAll().size();

        // Create the Grossiste

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restGrossisteMockMvc.perform(put("/api/grossistes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(grossiste)))
            .andExpect(status().isBadRequest());

        // Validate the Grossiste in the database
        List<Grossiste> grossisteList = grossisteRepository.findAll();
        assertThat(grossisteList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteGrossiste() throws Exception {
        // Initialize the database
        grossisteService.save(grossiste);

        int databaseSizeBeforeDelete = grossisteRepository.findAll().size();

        // Delete the grossiste
        restGrossisteMockMvc.perform(delete("/api/grossistes/{id}", grossiste.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database is empty
        List<Grossiste> grossisteList = grossisteRepository.findAll();
        assertThat(grossisteList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Grossiste.class);
        Grossiste grossiste1 = new Grossiste();
        grossiste1.setId(1L);
        Grossiste grossiste2 = new Grossiste();
        grossiste2.setId(grossiste1.getId());
        assertThat(grossiste1).isEqualTo(grossiste2);
        grossiste2.setId(2L);
        assertThat(grossiste1).isNotEqualTo(grossiste2);
        grossiste1.setId(null);
        assertThat(grossiste1).isNotEqualTo(grossiste2);
    }
}
