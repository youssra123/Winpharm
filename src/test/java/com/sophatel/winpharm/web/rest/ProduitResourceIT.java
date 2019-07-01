package com.sophatel.winpharm.web.rest;

import com.sophatel.winpharm.WinpharmApp;
import com.sophatel.winpharm.domain.Produit;
import com.sophatel.winpharm.domain.Categorie;
import com.sophatel.winpharm.domain.FammilleTarifaire;
import com.sophatel.winpharm.domain.Forme;
import com.sophatel.winpharm.repository.ProduitRepository;
import com.sophatel.winpharm.service.ProduitService;
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
 * Integration tests for the {@Link ProduitResource} REST controller.
 */
@SpringBootTest(classes = WinpharmApp.class)
public class ProduitResourceIT {

    private static final String DEFAULT_PRODUIT_LIBELLE = "AAAAAAAAAA";
    private static final String UPDATED_PRODUIT_LIBELLE = "BBBBBBBBBB";

    private static final String DEFAULT_PRODUIT_ACTIF = "AAAAAAAAAA";
    private static final String UPDATED_PRODUIT_ACTIF = "BBBBBBBBBB";

    private static final Integer DEFAULT_PRODUIT_CODE_BARRE = 1;
    private static final Integer UPDATED_PRODUIT_CODE_BARRE = 2;

    private static final Double DEFAULT_PRODUIT_DOSAGE = 1D;
    private static final Double UPDATED_PRODUIT_DOSAGE = 2D;

    private static final String DEFAULT_PRODUIT_UNI_DOSAGE = "AAAAAAAAAA";
    private static final String UPDATED_PRODUIT_UNI_DOSAGE = "BBBBBBBBBB";

    private static final Double DEFAULT_PRODUIT_VOLUME = 1D;
    private static final Double UPDATED_PRODUIT_VOLUME = 2D;

    private static final String DEFAULT_PRODUIT_UNI_VOLUME = "AAAAAAAAAA";
    private static final String UPDATED_PRODUIT_UNI_VOLUME = "BBBBBBBBBB";

    @Autowired
    private ProduitRepository produitRepository;

    @Autowired
    private ProduitService produitService;

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

    private MockMvc restProduitMockMvc;

    private Produit produit;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ProduitResource produitResource = new ProduitResource(produitService);
        this.restProduitMockMvc = MockMvcBuilders.standaloneSetup(produitResource)
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
    public static Produit createEntity(EntityManager em) {
        Produit produit = new Produit()
            .produitLibelle(DEFAULT_PRODUIT_LIBELLE)
            .produitActif(DEFAULT_PRODUIT_ACTIF)
            .produitCodeBarre(DEFAULT_PRODUIT_CODE_BARRE)
            .produitDosage(DEFAULT_PRODUIT_DOSAGE)
            .produitUniDosage(DEFAULT_PRODUIT_UNI_DOSAGE)
            .produitVolume(DEFAULT_PRODUIT_VOLUME)
            .produitUniVolume(DEFAULT_PRODUIT_UNI_VOLUME);
        // Add required entity
        Categorie categorie;
        if (TestUtil.findAll(em, Categorie.class).isEmpty()) {
            categorie = CategorieResourceIT.createEntity(em);
            em.persist(categorie);
            em.flush();
        } else {
            categorie = TestUtil.findAll(em, Categorie.class).get(0);
        }
        produit.setProduit_categorie(categorie);
        // Add required entity
        FammilleTarifaire fammilleTarifaire;
        if (TestUtil.findAll(em, FammilleTarifaire.class).isEmpty()) {
            fammilleTarifaire = FammilleTarifaireResourceIT.createEntity(em);
            em.persist(fammilleTarifaire);
            em.flush();
        } else {
            fammilleTarifaire = TestUtil.findAll(em, FammilleTarifaire.class).get(0);
        }
        produit.setProduit_fam_tar(fammilleTarifaire);
        // Add required entity
        Forme forme;
        if (TestUtil.findAll(em, Forme.class).isEmpty()) {
            forme = FormeResourceIT.createEntity(em);
            em.persist(forme);
            em.flush();
        } else {
            forme = TestUtil.findAll(em, Forme.class).get(0);
        }
        produit.setProform(forme);
        return produit;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Produit createUpdatedEntity(EntityManager em) {
        Produit produit = new Produit()
            .produitLibelle(UPDATED_PRODUIT_LIBELLE)
            .produitActif(UPDATED_PRODUIT_ACTIF)
            .produitCodeBarre(UPDATED_PRODUIT_CODE_BARRE)
            .produitDosage(UPDATED_PRODUIT_DOSAGE)
            .produitUniDosage(UPDATED_PRODUIT_UNI_DOSAGE)
            .produitVolume(UPDATED_PRODUIT_VOLUME)
            .produitUniVolume(UPDATED_PRODUIT_UNI_VOLUME);
        // Add required entity
        Categorie categorie;
        if (TestUtil.findAll(em, Categorie.class).isEmpty()) {
            categorie = CategorieResourceIT.createUpdatedEntity(em);
            em.persist(categorie);
            em.flush();
        } else {
            categorie = TestUtil.findAll(em, Categorie.class).get(0);
        }
        produit.setProduit_categorie(categorie);
        // Add required entity
        FammilleTarifaire fammilleTarifaire;
        if (TestUtil.findAll(em, FammilleTarifaire.class).isEmpty()) {
            fammilleTarifaire = FammilleTarifaireResourceIT.createUpdatedEntity(em);
            em.persist(fammilleTarifaire);
            em.flush();
        } else {
            fammilleTarifaire = TestUtil.findAll(em, FammilleTarifaire.class).get(0);
        }
        produit.setProduit_fam_tar(fammilleTarifaire);
        // Add required entity
        Forme forme;
        if (TestUtil.findAll(em, Forme.class).isEmpty()) {
            forme = FormeResourceIT.createUpdatedEntity(em);
            em.persist(forme);
            em.flush();
        } else {
            forme = TestUtil.findAll(em, Forme.class).get(0);
        }
        produit.setProform(forme);
        return produit;
    }

    @BeforeEach
    public void initTest() {
        produit = createEntity(em);
    }

    @Test
    @Transactional
    public void createProduit() throws Exception {
        int databaseSizeBeforeCreate = produitRepository.findAll().size();

        // Create the Produit
        restProduitMockMvc.perform(post("/api/produits")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(produit)))
            .andExpect(status().isCreated());

        // Validate the Produit in the database
        List<Produit> produitList = produitRepository.findAll();
        assertThat(produitList).hasSize(databaseSizeBeforeCreate + 1);
        Produit testProduit = produitList.get(produitList.size() - 1);
        assertThat(testProduit.getProduitLibelle()).isEqualTo(DEFAULT_PRODUIT_LIBELLE);
        assertThat(testProduit.getProduitActif()).isEqualTo(DEFAULT_PRODUIT_ACTIF);
        assertThat(testProduit.getProduitCodeBarre()).isEqualTo(DEFAULT_PRODUIT_CODE_BARRE);
        assertThat(testProduit.getProduitDosage()).isEqualTo(DEFAULT_PRODUIT_DOSAGE);
        assertThat(testProduit.getProduitUniDosage()).isEqualTo(DEFAULT_PRODUIT_UNI_DOSAGE);
        assertThat(testProduit.getProduitVolume()).isEqualTo(DEFAULT_PRODUIT_VOLUME);
        assertThat(testProduit.getProduitUniVolume()).isEqualTo(DEFAULT_PRODUIT_UNI_VOLUME);
    }

    @Test
    @Transactional
    public void createProduitWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = produitRepository.findAll().size();

        // Create the Produit with an existing ID
        produit.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restProduitMockMvc.perform(post("/api/produits")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(produit)))
            .andExpect(status().isBadRequest());

        // Validate the Produit in the database
        List<Produit> produitList = produitRepository.findAll();
        assertThat(produitList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkProduitLibelleIsRequired() throws Exception {
        int databaseSizeBeforeTest = produitRepository.findAll().size();
        // set the field null
        produit.setProduitLibelle(null);

        // Create the Produit, which fails.

        restProduitMockMvc.perform(post("/api/produits")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(produit)))
            .andExpect(status().isBadRequest());

        List<Produit> produitList = produitRepository.findAll();
        assertThat(produitList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkProduitActifIsRequired() throws Exception {
        int databaseSizeBeforeTest = produitRepository.findAll().size();
        // set the field null
        produit.setProduitActif(null);

        // Create the Produit, which fails.

        restProduitMockMvc.perform(post("/api/produits")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(produit)))
            .andExpect(status().isBadRequest());

        List<Produit> produitList = produitRepository.findAll();
        assertThat(produitList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllProduits() throws Exception {
        // Initialize the database
        produitRepository.saveAndFlush(produit);

        // Get all the produitList
        restProduitMockMvc.perform(get("/api/produits?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(produit.getId().intValue())))
            .andExpect(jsonPath("$.[*].produitLibelle").value(hasItem(DEFAULT_PRODUIT_LIBELLE.toString())))
            .andExpect(jsonPath("$.[*].produitActif").value(hasItem(DEFAULT_PRODUIT_ACTIF.toString())))
            .andExpect(jsonPath("$.[*].produitCodeBarre").value(hasItem(DEFAULT_PRODUIT_CODE_BARRE)))
            .andExpect(jsonPath("$.[*].produitDosage").value(hasItem(DEFAULT_PRODUIT_DOSAGE.doubleValue())))
            .andExpect(jsonPath("$.[*].produitUniDosage").value(hasItem(DEFAULT_PRODUIT_UNI_DOSAGE.toString())))
            .andExpect(jsonPath("$.[*].produitVolume").value(hasItem(DEFAULT_PRODUIT_VOLUME.doubleValue())))
            .andExpect(jsonPath("$.[*].produitUniVolume").value(hasItem(DEFAULT_PRODUIT_UNI_VOLUME.toString())));
    }
    
    @Test
    @Transactional
    public void getProduit() throws Exception {
        // Initialize the database
        produitRepository.saveAndFlush(produit);

        // Get the produit
        restProduitMockMvc.perform(get("/api/produits/{id}", produit.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(produit.getId().intValue()))
            .andExpect(jsonPath("$.produitLibelle").value(DEFAULT_PRODUIT_LIBELLE.toString()))
            .andExpect(jsonPath("$.produitActif").value(DEFAULT_PRODUIT_ACTIF.toString()))
            .andExpect(jsonPath("$.produitCodeBarre").value(DEFAULT_PRODUIT_CODE_BARRE))
            .andExpect(jsonPath("$.produitDosage").value(DEFAULT_PRODUIT_DOSAGE.doubleValue()))
            .andExpect(jsonPath("$.produitUniDosage").value(DEFAULT_PRODUIT_UNI_DOSAGE.toString()))
            .andExpect(jsonPath("$.produitVolume").value(DEFAULT_PRODUIT_VOLUME.doubleValue()))
            .andExpect(jsonPath("$.produitUniVolume").value(DEFAULT_PRODUIT_UNI_VOLUME.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingProduit() throws Exception {
        // Get the produit
        restProduitMockMvc.perform(get("/api/produits/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateProduit() throws Exception {
        // Initialize the database
        produitService.save(produit);

        int databaseSizeBeforeUpdate = produitRepository.findAll().size();

        // Update the produit
        Produit updatedProduit = produitRepository.findById(produit.getId()).get();
        // Disconnect from session so that the updates on updatedProduit are not directly saved in db
        em.detach(updatedProduit);
        updatedProduit
            .produitLibelle(UPDATED_PRODUIT_LIBELLE)
            .produitActif(UPDATED_PRODUIT_ACTIF)
            .produitCodeBarre(UPDATED_PRODUIT_CODE_BARRE)
            .produitDosage(UPDATED_PRODUIT_DOSAGE)
            .produitUniDosage(UPDATED_PRODUIT_UNI_DOSAGE)
            .produitVolume(UPDATED_PRODUIT_VOLUME)
            .produitUniVolume(UPDATED_PRODUIT_UNI_VOLUME);

        restProduitMockMvc.perform(put("/api/produits")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedProduit)))
            .andExpect(status().isOk());

        // Validate the Produit in the database
        List<Produit> produitList = produitRepository.findAll();
        assertThat(produitList).hasSize(databaseSizeBeforeUpdate);
        Produit testProduit = produitList.get(produitList.size() - 1);
        assertThat(testProduit.getProduitLibelle()).isEqualTo(UPDATED_PRODUIT_LIBELLE);
        assertThat(testProduit.getProduitActif()).isEqualTo(UPDATED_PRODUIT_ACTIF);
        assertThat(testProduit.getProduitCodeBarre()).isEqualTo(UPDATED_PRODUIT_CODE_BARRE);
        assertThat(testProduit.getProduitDosage()).isEqualTo(UPDATED_PRODUIT_DOSAGE);
        assertThat(testProduit.getProduitUniDosage()).isEqualTo(UPDATED_PRODUIT_UNI_DOSAGE);
        assertThat(testProduit.getProduitVolume()).isEqualTo(UPDATED_PRODUIT_VOLUME);
        assertThat(testProduit.getProduitUniVolume()).isEqualTo(UPDATED_PRODUIT_UNI_VOLUME);
    }

    @Test
    @Transactional
    public void updateNonExistingProduit() throws Exception {
        int databaseSizeBeforeUpdate = produitRepository.findAll().size();

        // Create the Produit

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restProduitMockMvc.perform(put("/api/produits")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(produit)))
            .andExpect(status().isBadRequest());

        // Validate the Produit in the database
        List<Produit> produitList = produitRepository.findAll();
        assertThat(produitList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteProduit() throws Exception {
        // Initialize the database
        produitService.save(produit);

        int databaseSizeBeforeDelete = produitRepository.findAll().size();

        // Delete the produit
        restProduitMockMvc.perform(delete("/api/produits/{id}", produit.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database is empty
        List<Produit> produitList = produitRepository.findAll();
        assertThat(produitList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Produit.class);
        Produit produit1 = new Produit();
        produit1.setId(1L);
        Produit produit2 = new Produit();
        produit2.setId(produit1.getId());
        assertThat(produit1).isEqualTo(produit2);
        produit2.setId(2L);
        assertThat(produit1).isNotEqualTo(produit2);
        produit1.setId(null);
        assertThat(produit1).isNotEqualTo(produit2);
    }
}
