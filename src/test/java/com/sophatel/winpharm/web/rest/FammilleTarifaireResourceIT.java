package com.sophatel.winpharm.web.rest;

import com.sophatel.winpharm.WinpharmApp;
import com.sophatel.winpharm.domain.FammilleTarifaire;
import com.sophatel.winpharm.repository.FammilleTarifaireRepository;
import com.sophatel.winpharm.service.FammilleTarifaireService;
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
 * Integration tests for the {@Link FammilleTarifaireResource} REST controller.
 */
@SpringBootTest(classes = WinpharmApp.class)
public class FammilleTarifaireResourceIT {

    private static final String DEFAULT_FAMI_TARIF_LIBELLE = "AAAAAAAAAA";
    private static final String UPDATED_FAMI_TARIF_LIBELLE = "BBBBBBBBBB";

    private static final Double DEFAULT_FAMI_TARIF_FORFAIT = 1D;
    private static final Double UPDATED_FAMI_TARIF_FORFAIT = 2D;

    private static final Integer DEFAULT_FAMI_TARIF_CODE_TVA = 1;
    private static final Integer UPDATED_FAMI_TARIF_CODE_TVA = 2;

    private static final Double DEFAULT_FAMI_TARIF_TAUX_TVA = 1D;
    private static final Double UPDATED_FAMI_TARIF_TAUX_TVA = 2D;

    @Autowired
    private FammilleTarifaireRepository fammilleTarifaireRepository;

    @Autowired
    private FammilleTarifaireService fammilleTarifaireService;

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

    private MockMvc restFammilleTarifaireMockMvc;

    private FammilleTarifaire fammilleTarifaire;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final FammilleTarifaireResource fammilleTarifaireResource = new FammilleTarifaireResource(fammilleTarifaireService);
        this.restFammilleTarifaireMockMvc = MockMvcBuilders.standaloneSetup(fammilleTarifaireResource)
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
    public static FammilleTarifaire createEntity(EntityManager em) {
        FammilleTarifaire fammilleTarifaire = new FammilleTarifaire()
            .famiTarifLibelle(DEFAULT_FAMI_TARIF_LIBELLE)
            .famiTarifForfait(DEFAULT_FAMI_TARIF_FORFAIT)
            .famiTarifCodeTVA(DEFAULT_FAMI_TARIF_CODE_TVA)
            .famiTarifTauxTVA(DEFAULT_FAMI_TARIF_TAUX_TVA);
        return fammilleTarifaire;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static FammilleTarifaire createUpdatedEntity(EntityManager em) {
        FammilleTarifaire fammilleTarifaire = new FammilleTarifaire()
            .famiTarifLibelle(UPDATED_FAMI_TARIF_LIBELLE)
            .famiTarifForfait(UPDATED_FAMI_TARIF_FORFAIT)
            .famiTarifCodeTVA(UPDATED_FAMI_TARIF_CODE_TVA)
            .famiTarifTauxTVA(UPDATED_FAMI_TARIF_TAUX_TVA);
        return fammilleTarifaire;
    }

    @BeforeEach
    public void initTest() {
        fammilleTarifaire = createEntity(em);
    }

    @Test
    @Transactional
    public void createFammilleTarifaire() throws Exception {
        int databaseSizeBeforeCreate = fammilleTarifaireRepository.findAll().size();

        // Create the FammilleTarifaire
        restFammilleTarifaireMockMvc.perform(post("/api/fammille-tarifaires")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(fammilleTarifaire)))
            .andExpect(status().isCreated());

        // Validate the FammilleTarifaire in the database
        List<FammilleTarifaire> fammilleTarifaireList = fammilleTarifaireRepository.findAll();
        assertThat(fammilleTarifaireList).hasSize(databaseSizeBeforeCreate + 1);
        FammilleTarifaire testFammilleTarifaire = fammilleTarifaireList.get(fammilleTarifaireList.size() - 1);
        assertThat(testFammilleTarifaire.getFamiTarifLibelle()).isEqualTo(DEFAULT_FAMI_TARIF_LIBELLE);
        assertThat(testFammilleTarifaire.getFamiTarifForfait()).isEqualTo(DEFAULT_FAMI_TARIF_FORFAIT);
        assertThat(testFammilleTarifaire.getFamiTarifCodeTVA()).isEqualTo(DEFAULT_FAMI_TARIF_CODE_TVA);
        assertThat(testFammilleTarifaire.getFamiTarifTauxTVA()).isEqualTo(DEFAULT_FAMI_TARIF_TAUX_TVA);
    }

    @Test
    @Transactional
    public void createFammilleTarifaireWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = fammilleTarifaireRepository.findAll().size();

        // Create the FammilleTarifaire with an existing ID
        fammilleTarifaire.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restFammilleTarifaireMockMvc.perform(post("/api/fammille-tarifaires")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(fammilleTarifaire)))
            .andExpect(status().isBadRequest());

        // Validate the FammilleTarifaire in the database
        List<FammilleTarifaire> fammilleTarifaireList = fammilleTarifaireRepository.findAll();
        assertThat(fammilleTarifaireList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkFamiTarifLibelleIsRequired() throws Exception {
        int databaseSizeBeforeTest = fammilleTarifaireRepository.findAll().size();
        // set the field null
        fammilleTarifaire.setFamiTarifLibelle(null);

        // Create the FammilleTarifaire, which fails.

        restFammilleTarifaireMockMvc.perform(post("/api/fammille-tarifaires")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(fammilleTarifaire)))
            .andExpect(status().isBadRequest());

        List<FammilleTarifaire> fammilleTarifaireList = fammilleTarifaireRepository.findAll();
        assertThat(fammilleTarifaireList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkFamiTarifForfaitIsRequired() throws Exception {
        int databaseSizeBeforeTest = fammilleTarifaireRepository.findAll().size();
        // set the field null
        fammilleTarifaire.setFamiTarifForfait(null);

        // Create the FammilleTarifaire, which fails.

        restFammilleTarifaireMockMvc.perform(post("/api/fammille-tarifaires")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(fammilleTarifaire)))
            .andExpect(status().isBadRequest());

        List<FammilleTarifaire> fammilleTarifaireList = fammilleTarifaireRepository.findAll();
        assertThat(fammilleTarifaireList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllFammilleTarifaires() throws Exception {
        // Initialize the database
        fammilleTarifaireRepository.saveAndFlush(fammilleTarifaire);

        // Get all the fammilleTarifaireList
        restFammilleTarifaireMockMvc.perform(get("/api/fammille-tarifaires?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(fammilleTarifaire.getId().intValue())))
            .andExpect(jsonPath("$.[*].famiTarifLibelle").value(hasItem(DEFAULT_FAMI_TARIF_LIBELLE.toString())))
            .andExpect(jsonPath("$.[*].famiTarifForfait").value(hasItem(DEFAULT_FAMI_TARIF_FORFAIT.doubleValue())))
            .andExpect(jsonPath("$.[*].famiTarifCodeTVA").value(hasItem(DEFAULT_FAMI_TARIF_CODE_TVA)))
            .andExpect(jsonPath("$.[*].famiTarifTauxTVA").value(hasItem(DEFAULT_FAMI_TARIF_TAUX_TVA.doubleValue())));
    }
    
    @Test
    @Transactional
    public void getFammilleTarifaire() throws Exception {
        // Initialize the database
        fammilleTarifaireRepository.saveAndFlush(fammilleTarifaire);

        // Get the fammilleTarifaire
        restFammilleTarifaireMockMvc.perform(get("/api/fammille-tarifaires/{id}", fammilleTarifaire.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(fammilleTarifaire.getId().intValue()))
            .andExpect(jsonPath("$.famiTarifLibelle").value(DEFAULT_FAMI_TARIF_LIBELLE.toString()))
            .andExpect(jsonPath("$.famiTarifForfait").value(DEFAULT_FAMI_TARIF_FORFAIT.doubleValue()))
            .andExpect(jsonPath("$.famiTarifCodeTVA").value(DEFAULT_FAMI_TARIF_CODE_TVA))
            .andExpect(jsonPath("$.famiTarifTauxTVA").value(DEFAULT_FAMI_TARIF_TAUX_TVA.doubleValue()));
    }

    @Test
    @Transactional
    public void getNonExistingFammilleTarifaire() throws Exception {
        // Get the fammilleTarifaire
        restFammilleTarifaireMockMvc.perform(get("/api/fammille-tarifaires/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateFammilleTarifaire() throws Exception {
        // Initialize the database
        fammilleTarifaireService.save(fammilleTarifaire);

        int databaseSizeBeforeUpdate = fammilleTarifaireRepository.findAll().size();

        // Update the fammilleTarifaire
        FammilleTarifaire updatedFammilleTarifaire = fammilleTarifaireRepository.findById(fammilleTarifaire.getId()).get();
        // Disconnect from session so that the updates on updatedFammilleTarifaire are not directly saved in db
        em.detach(updatedFammilleTarifaire);
        updatedFammilleTarifaire
            .famiTarifLibelle(UPDATED_FAMI_TARIF_LIBELLE)
            .famiTarifForfait(UPDATED_FAMI_TARIF_FORFAIT)
            .famiTarifCodeTVA(UPDATED_FAMI_TARIF_CODE_TVA)
            .famiTarifTauxTVA(UPDATED_FAMI_TARIF_TAUX_TVA);

        restFammilleTarifaireMockMvc.perform(put("/api/fammille-tarifaires")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedFammilleTarifaire)))
            .andExpect(status().isOk());

        // Validate the FammilleTarifaire in the database
        List<FammilleTarifaire> fammilleTarifaireList = fammilleTarifaireRepository.findAll();
        assertThat(fammilleTarifaireList).hasSize(databaseSizeBeforeUpdate);
        FammilleTarifaire testFammilleTarifaire = fammilleTarifaireList.get(fammilleTarifaireList.size() - 1);
        assertThat(testFammilleTarifaire.getFamiTarifLibelle()).isEqualTo(UPDATED_FAMI_TARIF_LIBELLE);
        assertThat(testFammilleTarifaire.getFamiTarifForfait()).isEqualTo(UPDATED_FAMI_TARIF_FORFAIT);
        assertThat(testFammilleTarifaire.getFamiTarifCodeTVA()).isEqualTo(UPDATED_FAMI_TARIF_CODE_TVA);
        assertThat(testFammilleTarifaire.getFamiTarifTauxTVA()).isEqualTo(UPDATED_FAMI_TARIF_TAUX_TVA);
    }

    @Test
    @Transactional
    public void updateNonExistingFammilleTarifaire() throws Exception {
        int databaseSizeBeforeUpdate = fammilleTarifaireRepository.findAll().size();

        // Create the FammilleTarifaire

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFammilleTarifaireMockMvc.perform(put("/api/fammille-tarifaires")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(fammilleTarifaire)))
            .andExpect(status().isBadRequest());

        // Validate the FammilleTarifaire in the database
        List<FammilleTarifaire> fammilleTarifaireList = fammilleTarifaireRepository.findAll();
        assertThat(fammilleTarifaireList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteFammilleTarifaire() throws Exception {
        // Initialize the database
        fammilleTarifaireService.save(fammilleTarifaire);

        int databaseSizeBeforeDelete = fammilleTarifaireRepository.findAll().size();

        // Delete the fammilleTarifaire
        restFammilleTarifaireMockMvc.perform(delete("/api/fammille-tarifaires/{id}", fammilleTarifaire.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database is empty
        List<FammilleTarifaire> fammilleTarifaireList = fammilleTarifaireRepository.findAll();
        assertThat(fammilleTarifaireList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(FammilleTarifaire.class);
        FammilleTarifaire fammilleTarifaire1 = new FammilleTarifaire();
        fammilleTarifaire1.setId(1L);
        FammilleTarifaire fammilleTarifaire2 = new FammilleTarifaire();
        fammilleTarifaire2.setId(fammilleTarifaire1.getId());
        assertThat(fammilleTarifaire1).isEqualTo(fammilleTarifaire2);
        fammilleTarifaire2.setId(2L);
        assertThat(fammilleTarifaire1).isNotEqualTo(fammilleTarifaire2);
        fammilleTarifaire1.setId(null);
        assertThat(fammilleTarifaire1).isNotEqualTo(fammilleTarifaire2);
    }
}
