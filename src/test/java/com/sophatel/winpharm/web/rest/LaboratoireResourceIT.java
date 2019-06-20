package com.sophatel.winpharm.web.rest;

import com.sophatel.winpharm.WinpharmApp;
import com.sophatel.winpharm.domain.Laboratoire;
import com.sophatel.winpharm.repository.LaboratoireRepository;
import com.sophatel.winpharm.service.LaboratoireService;
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
 * Integration tests for the {@Link LaboratoireResource} REST controller.
 */
@SpringBootTest(classes = WinpharmApp.class)
public class LaboratoireResourceIT {

    private static final String DEFAULT_LABORATOIRE_RAIS_SOC = "AAAAAAAAAA";
    private static final String UPDATED_LABORATOIRE_RAIS_SOC = "BBBBBBBBBB";

    private static final String DEFAULT_LABORATOIRE_ADRESSE = "AAAAAAAAAA";
    private static final String UPDATED_LABORATOIRE_ADRESSE = "BBBBBBBBBB";

    private static final Integer DEFAULT_LABORATOIRE_TELEPHONE = 10;
    private static final Integer UPDATED_LABORATOIRE_TELEPHONE = 11;

    @Autowired
    private LaboratoireRepository laboratoireRepository;

    @Autowired
    private LaboratoireService laboratoireService;

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

    private MockMvc restLaboratoireMockMvc;

    private Laboratoire laboratoire;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final LaboratoireResource laboratoireResource = new LaboratoireResource(laboratoireService);
        this.restLaboratoireMockMvc = MockMvcBuilders.standaloneSetup(laboratoireResource)
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
    public static Laboratoire createEntity(EntityManager em) {
        Laboratoire laboratoire = new Laboratoire()
            .laboratoireRaisSoc(DEFAULT_LABORATOIRE_RAIS_SOC)
            .laboratoireAdresse(DEFAULT_LABORATOIRE_ADRESSE)
            .laboratoireTelephone(DEFAULT_LABORATOIRE_TELEPHONE);
        return laboratoire;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Laboratoire createUpdatedEntity(EntityManager em) {
        Laboratoire laboratoire = new Laboratoire()
            .laboratoireRaisSoc(UPDATED_LABORATOIRE_RAIS_SOC)
            .laboratoireAdresse(UPDATED_LABORATOIRE_ADRESSE)
            .laboratoireTelephone(UPDATED_LABORATOIRE_TELEPHONE);
        return laboratoire;
    }

    @BeforeEach
    public void initTest() {
        laboratoire = createEntity(em);
    }

    @Test
    @Transactional
    public void createLaboratoire() throws Exception {
        int databaseSizeBeforeCreate = laboratoireRepository.findAll().size();

        // Create the Laboratoire
        restLaboratoireMockMvc.perform(post("/api/laboratoires")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(laboratoire)))
            .andExpect(status().isCreated());

        // Validate the Laboratoire in the database
        List<Laboratoire> laboratoireList = laboratoireRepository.findAll();
        assertThat(laboratoireList).hasSize(databaseSizeBeforeCreate + 1);
        Laboratoire testLaboratoire = laboratoireList.get(laboratoireList.size() - 1);
        assertThat(testLaboratoire.getLaboratoireRaisSoc()).isEqualTo(DEFAULT_LABORATOIRE_RAIS_SOC);
        assertThat(testLaboratoire.getLaboratoireAdresse()).isEqualTo(DEFAULT_LABORATOIRE_ADRESSE);
        assertThat(testLaboratoire.getLaboratoireTelephone()).isEqualTo(DEFAULT_LABORATOIRE_TELEPHONE);
    }

    @Test
    @Transactional
    public void createLaboratoireWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = laboratoireRepository.findAll().size();

        // Create the Laboratoire with an existing ID
        laboratoire.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restLaboratoireMockMvc.perform(post("/api/laboratoires")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(laboratoire)))
            .andExpect(status().isBadRequest());

        // Validate the Laboratoire in the database
        List<Laboratoire> laboratoireList = laboratoireRepository.findAll();
        assertThat(laboratoireList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkLaboratoireRaisSocIsRequired() throws Exception {
        int databaseSizeBeforeTest = laboratoireRepository.findAll().size();
        // set the field null
        laboratoire.setLaboratoireRaisSoc(null);

        // Create the Laboratoire, which fails.

        restLaboratoireMockMvc.perform(post("/api/laboratoires")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(laboratoire)))
            .andExpect(status().isBadRequest());

        List<Laboratoire> laboratoireList = laboratoireRepository.findAll();
        assertThat(laboratoireList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkLaboratoireAdresseIsRequired() throws Exception {
        int databaseSizeBeforeTest = laboratoireRepository.findAll().size();
        // set the field null
        laboratoire.setLaboratoireAdresse(null);

        // Create the Laboratoire, which fails.

        restLaboratoireMockMvc.perform(post("/api/laboratoires")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(laboratoire)))
            .andExpect(status().isBadRequest());

        List<Laboratoire> laboratoireList = laboratoireRepository.findAll();
        assertThat(laboratoireList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkLaboratoireTelephoneIsRequired() throws Exception {
        int databaseSizeBeforeTest = laboratoireRepository.findAll().size();
        // set the field null
        laboratoire.setLaboratoireTelephone(null);

        // Create the Laboratoire, which fails.

        restLaboratoireMockMvc.perform(post("/api/laboratoires")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(laboratoire)))
            .andExpect(status().isBadRequest());

        List<Laboratoire> laboratoireList = laboratoireRepository.findAll();
        assertThat(laboratoireList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllLaboratoires() throws Exception {
        // Initialize the database
        laboratoireRepository.saveAndFlush(laboratoire);

        // Get all the laboratoireList
        restLaboratoireMockMvc.perform(get("/api/laboratoires?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(laboratoire.getId().intValue())))
            .andExpect(jsonPath("$.[*].laboratoireRaisSoc").value(hasItem(DEFAULT_LABORATOIRE_RAIS_SOC.toString())))
            .andExpect(jsonPath("$.[*].laboratoireAdresse").value(hasItem(DEFAULT_LABORATOIRE_ADRESSE.toString())))
            .andExpect(jsonPath("$.[*].laboratoireTelephone").value(hasItem(DEFAULT_LABORATOIRE_TELEPHONE)));
    }
    
    @Test
    @Transactional
    public void getLaboratoire() throws Exception {
        // Initialize the database
        laboratoireRepository.saveAndFlush(laboratoire);

        // Get the laboratoire
        restLaboratoireMockMvc.perform(get("/api/laboratoires/{id}", laboratoire.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(laboratoire.getId().intValue()))
            .andExpect(jsonPath("$.laboratoireRaisSoc").value(DEFAULT_LABORATOIRE_RAIS_SOC.toString()))
            .andExpect(jsonPath("$.laboratoireAdresse").value(DEFAULT_LABORATOIRE_ADRESSE.toString()))
            .andExpect(jsonPath("$.laboratoireTelephone").value(DEFAULT_LABORATOIRE_TELEPHONE));
    }

    @Test
    @Transactional
    public void getNonExistingLaboratoire() throws Exception {
        // Get the laboratoire
        restLaboratoireMockMvc.perform(get("/api/laboratoires/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateLaboratoire() throws Exception {
        // Initialize the database
        laboratoireService.save(laboratoire);

        int databaseSizeBeforeUpdate = laboratoireRepository.findAll().size();

        // Update the laboratoire
        Laboratoire updatedLaboratoire = laboratoireRepository.findById(laboratoire.getId()).get();
        // Disconnect from session so that the updates on updatedLaboratoire are not directly saved in db
        em.detach(updatedLaboratoire);
        updatedLaboratoire
            .laboratoireRaisSoc(UPDATED_LABORATOIRE_RAIS_SOC)
            .laboratoireAdresse(UPDATED_LABORATOIRE_ADRESSE)
            .laboratoireTelephone(UPDATED_LABORATOIRE_TELEPHONE);

        restLaboratoireMockMvc.perform(put("/api/laboratoires")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedLaboratoire)))
            .andExpect(status().isOk());

        // Validate the Laboratoire in the database
        List<Laboratoire> laboratoireList = laboratoireRepository.findAll();
        assertThat(laboratoireList).hasSize(databaseSizeBeforeUpdate);
        Laboratoire testLaboratoire = laboratoireList.get(laboratoireList.size() - 1);
        assertThat(testLaboratoire.getLaboratoireRaisSoc()).isEqualTo(UPDATED_LABORATOIRE_RAIS_SOC);
        assertThat(testLaboratoire.getLaboratoireAdresse()).isEqualTo(UPDATED_LABORATOIRE_ADRESSE);
        assertThat(testLaboratoire.getLaboratoireTelephone()).isEqualTo(UPDATED_LABORATOIRE_TELEPHONE);
    }

    @Test
    @Transactional
    public void updateNonExistingLaboratoire() throws Exception {
        int databaseSizeBeforeUpdate = laboratoireRepository.findAll().size();

        // Create the Laboratoire

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restLaboratoireMockMvc.perform(put("/api/laboratoires")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(laboratoire)))
            .andExpect(status().isBadRequest());

        // Validate the Laboratoire in the database
        List<Laboratoire> laboratoireList = laboratoireRepository.findAll();
        assertThat(laboratoireList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteLaboratoire() throws Exception {
        // Initialize the database
        laboratoireService.save(laboratoire);

        int databaseSizeBeforeDelete = laboratoireRepository.findAll().size();

        // Delete the laboratoire
        restLaboratoireMockMvc.perform(delete("/api/laboratoires/{id}", laboratoire.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database is empty
        List<Laboratoire> laboratoireList = laboratoireRepository.findAll();
        assertThat(laboratoireList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Laboratoire.class);
        Laboratoire laboratoire1 = new Laboratoire();
        laboratoire1.setId(1L);
        Laboratoire laboratoire2 = new Laboratoire();
        laboratoire2.setId(laboratoire1.getId());
        assertThat(laboratoire1).isEqualTo(laboratoire2);
        laboratoire2.setId(2L);
        assertThat(laboratoire1).isNotEqualTo(laboratoire2);
        laboratoire1.setId(null);
        assertThat(laboratoire1).isNotEqualTo(laboratoire2);
    }
}
