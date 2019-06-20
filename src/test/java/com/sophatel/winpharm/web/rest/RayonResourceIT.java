package com.sophatel.winpharm.web.rest;

import com.sophatel.winpharm.WinpharmApp;
import com.sophatel.winpharm.domain.Rayon;
import com.sophatel.winpharm.repository.RayonRepository;
import com.sophatel.winpharm.service.RayonService;
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
 * Integration tests for the {@Link RayonResource} REST controller.
 */
@SpringBootTest(classes = WinpharmApp.class)
public class RayonResourceIT {

    private static final String DEFAULT_RAYON_LIBELLE = "AAAAAAAAAA";
    private static final String UPDATED_RAYON_LIBELLE = "BBBBBBBBBB";

    @Autowired
    private RayonRepository rayonRepository;

    @Autowired
    private RayonService rayonService;

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

    private MockMvc restRayonMockMvc;

    private Rayon rayon;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final RayonResource rayonResource = new RayonResource(rayonService);
        this.restRayonMockMvc = MockMvcBuilders.standaloneSetup(rayonResource)
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
    public static Rayon createEntity(EntityManager em) {
        Rayon rayon = new Rayon()
            .rayonLibelle(DEFAULT_RAYON_LIBELLE);
        return rayon;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Rayon createUpdatedEntity(EntityManager em) {
        Rayon rayon = new Rayon()
            .rayonLibelle(UPDATED_RAYON_LIBELLE);
        return rayon;
    }

    @BeforeEach
    public void initTest() {
        rayon = createEntity(em);
    }

    @Test
    @Transactional
    public void createRayon() throws Exception {
        int databaseSizeBeforeCreate = rayonRepository.findAll().size();

        // Create the Rayon
        restRayonMockMvc.perform(post("/api/rayons")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(rayon)))
            .andExpect(status().isCreated());

        // Validate the Rayon in the database
        List<Rayon> rayonList = rayonRepository.findAll();
        assertThat(rayonList).hasSize(databaseSizeBeforeCreate + 1);
        Rayon testRayon = rayonList.get(rayonList.size() - 1);
        assertThat(testRayon.getRayonLibelle()).isEqualTo(DEFAULT_RAYON_LIBELLE);
    }

    @Test
    @Transactional
    public void createRayonWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = rayonRepository.findAll().size();

        // Create the Rayon with an existing ID
        rayon.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restRayonMockMvc.perform(post("/api/rayons")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(rayon)))
            .andExpect(status().isBadRequest());

        // Validate the Rayon in the database
        List<Rayon> rayonList = rayonRepository.findAll();
        assertThat(rayonList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkRayonLibelleIsRequired() throws Exception {
        int databaseSizeBeforeTest = rayonRepository.findAll().size();
        // set the field null
        rayon.setRayonLibelle(null);

        // Create the Rayon, which fails.

        restRayonMockMvc.perform(post("/api/rayons")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(rayon)))
            .andExpect(status().isBadRequest());

        List<Rayon> rayonList = rayonRepository.findAll();
        assertThat(rayonList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllRayons() throws Exception {
        // Initialize the database
        rayonRepository.saveAndFlush(rayon);

        // Get all the rayonList
        restRayonMockMvc.perform(get("/api/rayons?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(rayon.getId().intValue())))
            .andExpect(jsonPath("$.[*].rayonLibelle").value(hasItem(DEFAULT_RAYON_LIBELLE.toString())));
    }
    
    @Test
    @Transactional
    public void getRayon() throws Exception {
        // Initialize the database
        rayonRepository.saveAndFlush(rayon);

        // Get the rayon
        restRayonMockMvc.perform(get("/api/rayons/{id}", rayon.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(rayon.getId().intValue()))
            .andExpect(jsonPath("$.rayonLibelle").value(DEFAULT_RAYON_LIBELLE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingRayon() throws Exception {
        // Get the rayon
        restRayonMockMvc.perform(get("/api/rayons/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateRayon() throws Exception {
        // Initialize the database
        rayonService.save(rayon);

        int databaseSizeBeforeUpdate = rayonRepository.findAll().size();

        // Update the rayon
        Rayon updatedRayon = rayonRepository.findById(rayon.getId()).get();
        // Disconnect from session so that the updates on updatedRayon are not directly saved in db
        em.detach(updatedRayon);
        updatedRayon
            .rayonLibelle(UPDATED_RAYON_LIBELLE);

        restRayonMockMvc.perform(put("/api/rayons")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedRayon)))
            .andExpect(status().isOk());

        // Validate the Rayon in the database
        List<Rayon> rayonList = rayonRepository.findAll();
        assertThat(rayonList).hasSize(databaseSizeBeforeUpdate);
        Rayon testRayon = rayonList.get(rayonList.size() - 1);
        assertThat(testRayon.getRayonLibelle()).isEqualTo(UPDATED_RAYON_LIBELLE);
    }

    @Test
    @Transactional
    public void updateNonExistingRayon() throws Exception {
        int databaseSizeBeforeUpdate = rayonRepository.findAll().size();

        // Create the Rayon

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRayonMockMvc.perform(put("/api/rayons")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(rayon)))
            .andExpect(status().isBadRequest());

        // Validate the Rayon in the database
        List<Rayon> rayonList = rayonRepository.findAll();
        assertThat(rayonList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteRayon() throws Exception {
        // Initialize the database
        rayonService.save(rayon);

        int databaseSizeBeforeDelete = rayonRepository.findAll().size();

        // Delete the rayon
        restRayonMockMvc.perform(delete("/api/rayons/{id}", rayon.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database is empty
        List<Rayon> rayonList = rayonRepository.findAll();
        assertThat(rayonList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Rayon.class);
        Rayon rayon1 = new Rayon();
        rayon1.setId(1L);
        Rayon rayon2 = new Rayon();
        rayon2.setId(rayon1.getId());
        assertThat(rayon1).isEqualTo(rayon2);
        rayon2.setId(2L);
        assertThat(rayon1).isNotEqualTo(rayon2);
        rayon1.setId(null);
        assertThat(rayon1).isNotEqualTo(rayon2);
    }
}
