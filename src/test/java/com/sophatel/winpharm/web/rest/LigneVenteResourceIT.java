package com.sophatel.winpharm.web.rest;

import com.sophatel.winpharm.WinpharmApp;
import com.sophatel.winpharm.domain.LigneVente;
import com.sophatel.winpharm.domain.EnteteVente;
import com.sophatel.winpharm.domain.Produit;
import com.sophatel.winpharm.repository.LigneVenteRepository;
import com.sophatel.winpharm.service.LigneVenteService;
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
 * Integration tests for the {@Link LigneVenteResource} REST controller.
 */
@SpringBootTest(classes = WinpharmApp.class)
public class LigneVenteResourceIT {

    private static final Integer DEFAULT_LIGNE_VENTE_QTE = 1;
    private static final Integer UPDATED_LIGNE_VENTE_QTE = 2;

    private static final Double DEFAULT_LIGNE_VENTE_TOTAL_HT = 1D;
    private static final Double UPDATED_LIGNE_VENTE_TOTAL_HT = 2D;

    private static final Double DEFAULT_LIGNE_VENTE_TOTAL_TTC = 1D;
    private static final Double UPDATED_LIGNE_VENTE_TOTAL_TTC = 2D;

    private static final Double DEFAULT_LIGNE_VENTE_PRIX_TTC = 1D;
    private static final Double UPDATED_LIGNE_VENTE_PRIX_TTC = 2D;

    private static final Double DEFAULT_LIGNE_VENTE_PRIX_HT = 1D;
    private static final Double UPDATED_LIGNE_VENTE_PRIX_HT = 2D;

    private static final String DEFAULT_LIGNE_VENTE_DESIGNATION = "AAAAAAAAAA";
    private static final String UPDATED_LIGNE_VENTE_DESIGNATION = "BBBBBBBBBB";

    @Autowired
    private LigneVenteRepository ligneVenteRepository;

    @Autowired
    private LigneVenteService ligneVenteService;

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

    private MockMvc restLigneVenteMockMvc;

    private LigneVente ligneVente;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final LigneVenteResource ligneVenteResource = new LigneVenteResource(ligneVenteService);
        this.restLigneVenteMockMvc = MockMvcBuilders.standaloneSetup(ligneVenteResource)
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
    public static LigneVente createEntity(EntityManager em) {
        LigneVente ligneVente = new LigneVente()
            .ligneVenteQte(DEFAULT_LIGNE_VENTE_QTE)
            .ligneVenteTotalHT(DEFAULT_LIGNE_VENTE_TOTAL_HT)
            .ligneVenteTotalTTC(DEFAULT_LIGNE_VENTE_TOTAL_TTC)
            .ligneVentePrixTTC(DEFAULT_LIGNE_VENTE_PRIX_TTC)
            .ligneVentePrixHT(DEFAULT_LIGNE_VENTE_PRIX_HT)
            .ligneVenteDesignation(DEFAULT_LIGNE_VENTE_DESIGNATION);
        // Add required entity
        EnteteVente enteteVente;
        if (TestUtil.findAll(em, EnteteVente.class).isEmpty()) {
            enteteVente = EnteteVenteResourceIT.createEntity(em);
            em.persist(enteteVente);
            em.flush();
        } else {
            enteteVente = TestUtil.findAll(em, EnteteVente.class).get(0);
        }
        ligneVente.setEnteteVente(enteteVente);
        // Add required entity
        Produit produit;
        if (TestUtil.findAll(em, Produit.class).isEmpty()) {
            produit = ProduitResourceIT.createEntity(em);
            em.persist(produit);
            em.flush();
        } else {
            produit = TestUtil.findAll(em, Produit.class).get(0);
        }
        ligneVente.setProduit(produit);
        return ligneVente;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static LigneVente createUpdatedEntity(EntityManager em) {
        LigneVente ligneVente = new LigneVente()
            .ligneVenteQte(UPDATED_LIGNE_VENTE_QTE)
            .ligneVenteTotalHT(UPDATED_LIGNE_VENTE_TOTAL_HT)
            .ligneVenteTotalTTC(UPDATED_LIGNE_VENTE_TOTAL_TTC)
            .ligneVentePrixTTC(UPDATED_LIGNE_VENTE_PRIX_TTC)
            .ligneVentePrixHT(UPDATED_LIGNE_VENTE_PRIX_HT)
            .ligneVenteDesignation(UPDATED_LIGNE_VENTE_DESIGNATION);
        // Add required entity
        EnteteVente enteteVente;
        if (TestUtil.findAll(em, EnteteVente.class).isEmpty()) {
            enteteVente = EnteteVenteResourceIT.createUpdatedEntity(em);
            em.persist(enteteVente);
            em.flush();
        } else {
            enteteVente = TestUtil.findAll(em, EnteteVente.class).get(0);
        }
        ligneVente.setEnteteVente(enteteVente);
        // Add required entity
        Produit produit;
        if (TestUtil.findAll(em, Produit.class).isEmpty()) {
            produit = ProduitResourceIT.createUpdatedEntity(em);
            em.persist(produit);
            em.flush();
        } else {
            produit = TestUtil.findAll(em, Produit.class).get(0);
        }
        ligneVente.setProduit(produit);
        return ligneVente;
    }

    @BeforeEach
    public void initTest() {
        ligneVente = createEntity(em);
    }

    @Test
    @Transactional
    public void createLigneVente() throws Exception {
        int databaseSizeBeforeCreate = ligneVenteRepository.findAll().size();

        // Create the LigneVente
        restLigneVenteMockMvc.perform(post("/api/ligne-ventes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(ligneVente)))
            .andExpect(status().isCreated());

        // Validate the LigneVente in the database
        List<LigneVente> ligneVenteList = ligneVenteRepository.findAll();
        assertThat(ligneVenteList).hasSize(databaseSizeBeforeCreate + 1);
        LigneVente testLigneVente = ligneVenteList.get(ligneVenteList.size() - 1);
        assertThat(testLigneVente.getLigneVenteQte()).isEqualTo(DEFAULT_LIGNE_VENTE_QTE);
        assertThat(testLigneVente.getLigneVenteTotalHT()).isEqualTo(DEFAULT_LIGNE_VENTE_TOTAL_HT);
        assertThat(testLigneVente.getLigneVenteTotalTTC()).isEqualTo(DEFAULT_LIGNE_VENTE_TOTAL_TTC);
        assertThat(testLigneVente.getLigneVentePrixTTC()).isEqualTo(DEFAULT_LIGNE_VENTE_PRIX_TTC);
        assertThat(testLigneVente.getLigneVentePrixHT()).isEqualTo(DEFAULT_LIGNE_VENTE_PRIX_HT);
        assertThat(testLigneVente.getLigneVenteDesignation()).isEqualTo(DEFAULT_LIGNE_VENTE_DESIGNATION);
    }

    @Test
    @Transactional
    public void createLigneVenteWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = ligneVenteRepository.findAll().size();

        // Create the LigneVente with an existing ID
        ligneVente.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restLigneVenteMockMvc.perform(post("/api/ligne-ventes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(ligneVente)))
            .andExpect(status().isBadRequest());

        // Validate the LigneVente in the database
        List<LigneVente> ligneVenteList = ligneVenteRepository.findAll();
        assertThat(ligneVenteList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkLigneVenteQteIsRequired() throws Exception {
        int databaseSizeBeforeTest = ligneVenteRepository.findAll().size();
        // set the field null
        ligneVente.setLigneVenteQte(null);

        // Create the LigneVente, which fails.

        restLigneVenteMockMvc.perform(post("/api/ligne-ventes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(ligneVente)))
            .andExpect(status().isBadRequest());

        List<LigneVente> ligneVenteList = ligneVenteRepository.findAll();
        assertThat(ligneVenteList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkLigneVenteTotalHTIsRequired() throws Exception {
        int databaseSizeBeforeTest = ligneVenteRepository.findAll().size();
        // set the field null
        ligneVente.setLigneVenteTotalHT(null);

        // Create the LigneVente, which fails.

        restLigneVenteMockMvc.perform(post("/api/ligne-ventes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(ligneVente)))
            .andExpect(status().isBadRequest());

        List<LigneVente> ligneVenteList = ligneVenteRepository.findAll();
        assertThat(ligneVenteList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkLigneVenteTotalTTCIsRequired() throws Exception {
        int databaseSizeBeforeTest = ligneVenteRepository.findAll().size();
        // set the field null
        ligneVente.setLigneVenteTotalTTC(null);

        // Create the LigneVente, which fails.

        restLigneVenteMockMvc.perform(post("/api/ligne-ventes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(ligneVente)))
            .andExpect(status().isBadRequest());

        List<LigneVente> ligneVenteList = ligneVenteRepository.findAll();
        assertThat(ligneVenteList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkLigneVentePrixTTCIsRequired() throws Exception {
        int databaseSizeBeforeTest = ligneVenteRepository.findAll().size();
        // set the field null
        ligneVente.setLigneVentePrixTTC(null);

        // Create the LigneVente, which fails.

        restLigneVenteMockMvc.perform(post("/api/ligne-ventes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(ligneVente)))
            .andExpect(status().isBadRequest());

        List<LigneVente> ligneVenteList = ligneVenteRepository.findAll();
        assertThat(ligneVenteList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkLigneVentePrixHTIsRequired() throws Exception {
        int databaseSizeBeforeTest = ligneVenteRepository.findAll().size();
        // set the field null
        ligneVente.setLigneVentePrixHT(null);

        // Create the LigneVente, which fails.

        restLigneVenteMockMvc.perform(post("/api/ligne-ventes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(ligneVente)))
            .andExpect(status().isBadRequest());

        List<LigneVente> ligneVenteList = ligneVenteRepository.findAll();
        assertThat(ligneVenteList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkLigneVenteDesignationIsRequired() throws Exception {
        int databaseSizeBeforeTest = ligneVenteRepository.findAll().size();
        // set the field null
        ligneVente.setLigneVenteDesignation(null);

        // Create the LigneVente, which fails.

        restLigneVenteMockMvc.perform(post("/api/ligne-ventes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(ligneVente)))
            .andExpect(status().isBadRequest());

        List<LigneVente> ligneVenteList = ligneVenteRepository.findAll();
        assertThat(ligneVenteList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllLigneVentes() throws Exception {
        // Initialize the database
        ligneVenteRepository.saveAndFlush(ligneVente);

        // Get all the ligneVenteList
        restLigneVenteMockMvc.perform(get("/api/ligne-ventes?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(ligneVente.getId().intValue())))
            .andExpect(jsonPath("$.[*].ligneVenteQte").value(hasItem(DEFAULT_LIGNE_VENTE_QTE)))
            .andExpect(jsonPath("$.[*].ligneVenteTotalHT").value(hasItem(DEFAULT_LIGNE_VENTE_TOTAL_HT.doubleValue())))
            .andExpect(jsonPath("$.[*].ligneVenteTotalTTC").value(hasItem(DEFAULT_LIGNE_VENTE_TOTAL_TTC.doubleValue())))
            .andExpect(jsonPath("$.[*].ligneVentePrixTTC").value(hasItem(DEFAULT_LIGNE_VENTE_PRIX_TTC.doubleValue())))
            .andExpect(jsonPath("$.[*].ligneVentePrixHT").value(hasItem(DEFAULT_LIGNE_VENTE_PRIX_HT.doubleValue())))
            .andExpect(jsonPath("$.[*].ligneVenteDesignation").value(hasItem(DEFAULT_LIGNE_VENTE_DESIGNATION.toString())));
    }
    
    @Test
    @Transactional
    public void getLigneVente() throws Exception {
        // Initialize the database
        ligneVenteRepository.saveAndFlush(ligneVente);

        // Get the ligneVente
        restLigneVenteMockMvc.perform(get("/api/ligne-ventes/{id}", ligneVente.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(ligneVente.getId().intValue()))
            .andExpect(jsonPath("$.ligneVenteQte").value(DEFAULT_LIGNE_VENTE_QTE))
            .andExpect(jsonPath("$.ligneVenteTotalHT").value(DEFAULT_LIGNE_VENTE_TOTAL_HT.doubleValue()))
            .andExpect(jsonPath("$.ligneVenteTotalTTC").value(DEFAULT_LIGNE_VENTE_TOTAL_TTC.doubleValue()))
            .andExpect(jsonPath("$.ligneVentePrixTTC").value(DEFAULT_LIGNE_VENTE_PRIX_TTC.doubleValue()))
            .andExpect(jsonPath("$.ligneVentePrixHT").value(DEFAULT_LIGNE_VENTE_PRIX_HT.doubleValue()))
            .andExpect(jsonPath("$.ligneVenteDesignation").value(DEFAULT_LIGNE_VENTE_DESIGNATION.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingLigneVente() throws Exception {
        // Get the ligneVente
        restLigneVenteMockMvc.perform(get("/api/ligne-ventes/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateLigneVente() throws Exception {
        // Initialize the database
        ligneVenteService.save(ligneVente);

        int databaseSizeBeforeUpdate = ligneVenteRepository.findAll().size();

        // Update the ligneVente
        LigneVente updatedLigneVente = ligneVenteRepository.findById(ligneVente.getId()).get();
        // Disconnect from session so that the updates on updatedLigneVente are not directly saved in db
        em.detach(updatedLigneVente);
        updatedLigneVente
            .ligneVenteQte(UPDATED_LIGNE_VENTE_QTE)
            .ligneVenteTotalHT(UPDATED_LIGNE_VENTE_TOTAL_HT)
            .ligneVenteTotalTTC(UPDATED_LIGNE_VENTE_TOTAL_TTC)
            .ligneVentePrixTTC(UPDATED_LIGNE_VENTE_PRIX_TTC)
            .ligneVentePrixHT(UPDATED_LIGNE_VENTE_PRIX_HT)
            .ligneVenteDesignation(UPDATED_LIGNE_VENTE_DESIGNATION);

        restLigneVenteMockMvc.perform(put("/api/ligne-ventes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedLigneVente)))
            .andExpect(status().isOk());

        // Validate the LigneVente in the database
        List<LigneVente> ligneVenteList = ligneVenteRepository.findAll();
        assertThat(ligneVenteList).hasSize(databaseSizeBeforeUpdate);
        LigneVente testLigneVente = ligneVenteList.get(ligneVenteList.size() - 1);
        assertThat(testLigneVente.getLigneVenteQte()).isEqualTo(UPDATED_LIGNE_VENTE_QTE);
        assertThat(testLigneVente.getLigneVenteTotalHT()).isEqualTo(UPDATED_LIGNE_VENTE_TOTAL_HT);
        assertThat(testLigneVente.getLigneVenteTotalTTC()).isEqualTo(UPDATED_LIGNE_VENTE_TOTAL_TTC);
        assertThat(testLigneVente.getLigneVentePrixTTC()).isEqualTo(UPDATED_LIGNE_VENTE_PRIX_TTC);
        assertThat(testLigneVente.getLigneVentePrixHT()).isEqualTo(UPDATED_LIGNE_VENTE_PRIX_HT);
        assertThat(testLigneVente.getLigneVenteDesignation()).isEqualTo(UPDATED_LIGNE_VENTE_DESIGNATION);
    }

    @Test
    @Transactional
    public void updateNonExistingLigneVente() throws Exception {
        int databaseSizeBeforeUpdate = ligneVenteRepository.findAll().size();

        // Create the LigneVente

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restLigneVenteMockMvc.perform(put("/api/ligne-ventes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(ligneVente)))
            .andExpect(status().isBadRequest());

        // Validate the LigneVente in the database
        List<LigneVente> ligneVenteList = ligneVenteRepository.findAll();
        assertThat(ligneVenteList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteLigneVente() throws Exception {
        // Initialize the database
        ligneVenteService.save(ligneVente);

        int databaseSizeBeforeDelete = ligneVenteRepository.findAll().size();

        // Delete the ligneVente
        restLigneVenteMockMvc.perform(delete("/api/ligne-ventes/{id}", ligneVente.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database is empty
        List<LigneVente> ligneVenteList = ligneVenteRepository.findAll();
        assertThat(ligneVenteList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(LigneVente.class);
        LigneVente ligneVente1 = new LigneVente();
        ligneVente1.setId(1L);
        LigneVente ligneVente2 = new LigneVente();
        ligneVente2.setId(ligneVente1.getId());
        assertThat(ligneVente1).isEqualTo(ligneVente2);
        ligneVente2.setId(2L);
        assertThat(ligneVente1).isNotEqualTo(ligneVente2);
        ligneVente1.setId(null);
        assertThat(ligneVente1).isNotEqualTo(ligneVente2);
    }
}
