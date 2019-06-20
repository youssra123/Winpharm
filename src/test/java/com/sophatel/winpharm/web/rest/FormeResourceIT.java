package com.sophatel.winpharm.web.rest;

import com.sophatel.winpharm.WinpharmApp;
import com.sophatel.winpharm.domain.Forme;
import com.sophatel.winpharm.repository.FormeRepository;
import com.sophatel.winpharm.service.FormeService;
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
 * Integration tests for the {@Link FormeResource} REST controller.
 */
@SpringBootTest(classes = WinpharmApp.class)
public class FormeResourceIT {

    private static final String DEFAULT_FORME_LIBELLE = "AAAAAAAAAA";
    private static final String UPDATED_FORME_LIBELLE = "BBBBBBBBBB";

    @Autowired
    private FormeRepository formeRepository;

    @Autowired
    private FormeService formeService;

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

    private MockMvc restFormeMockMvc;

    private Forme forme;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final FormeResource formeResource = new FormeResource(formeService);
        this.restFormeMockMvc = MockMvcBuilders.standaloneSetup(formeResource)
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
    public static Forme createEntity(EntityManager em) {
        Forme forme = new Forme()
            .formeLibelle(DEFAULT_FORME_LIBELLE);
        return forme;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Forme createUpdatedEntity(EntityManager em) {
        Forme forme = new Forme()
            .formeLibelle(UPDATED_FORME_LIBELLE);
        return forme;
    }

    @BeforeEach
    public void initTest() {
        forme = createEntity(em);
    }

    @Test
    @Transactional
    public void createForme() throws Exception {
        int databaseSizeBeforeCreate = formeRepository.findAll().size();

        // Create the Forme
        restFormeMockMvc.perform(post("/api/formes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(forme)))
            .andExpect(status().isCreated());

        // Validate the Forme in the database
        List<Forme> formeList = formeRepository.findAll();
        assertThat(formeList).hasSize(databaseSizeBeforeCreate + 1);
        Forme testForme = formeList.get(formeList.size() - 1);
        assertThat(testForme.getFormeLibelle()).isEqualTo(DEFAULT_FORME_LIBELLE);
    }

    @Test
    @Transactional
    public void createFormeWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = formeRepository.findAll().size();

        // Create the Forme with an existing ID
        forme.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restFormeMockMvc.perform(post("/api/formes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(forme)))
            .andExpect(status().isBadRequest());

        // Validate the Forme in the database
        List<Forme> formeList = formeRepository.findAll();
        assertThat(formeList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkFormeLibelleIsRequired() throws Exception {
        int databaseSizeBeforeTest = formeRepository.findAll().size();
        // set the field null
        forme.setFormeLibelle(null);

        // Create the Forme, which fails.

        restFormeMockMvc.perform(post("/api/formes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(forme)))
            .andExpect(status().isBadRequest());

        List<Forme> formeList = formeRepository.findAll();
        assertThat(formeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllFormes() throws Exception {
        // Initialize the database
        formeRepository.saveAndFlush(forme);

        // Get all the formeList
        restFormeMockMvc.perform(get("/api/formes?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(forme.getId().intValue())))
            .andExpect(jsonPath("$.[*].formeLibelle").value(hasItem(DEFAULT_FORME_LIBELLE.toString())));
    }
    
    @Test
    @Transactional
    public void getForme() throws Exception {
        // Initialize the database
        formeRepository.saveAndFlush(forme);

        // Get the forme
        restFormeMockMvc.perform(get("/api/formes/{id}", forme.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(forme.getId().intValue()))
            .andExpect(jsonPath("$.formeLibelle").value(DEFAULT_FORME_LIBELLE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingForme() throws Exception {
        // Get the forme
        restFormeMockMvc.perform(get("/api/formes/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateForme() throws Exception {
        // Initialize the database
        formeService.save(forme);

        int databaseSizeBeforeUpdate = formeRepository.findAll().size();

        // Update the forme
        Forme updatedForme = formeRepository.findById(forme.getId()).get();
        // Disconnect from session so that the updates on updatedForme are not directly saved in db
        em.detach(updatedForme);
        updatedForme
            .formeLibelle(UPDATED_FORME_LIBELLE);

        restFormeMockMvc.perform(put("/api/formes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedForme)))
            .andExpect(status().isOk());

        // Validate the Forme in the database
        List<Forme> formeList = formeRepository.findAll();
        assertThat(formeList).hasSize(databaseSizeBeforeUpdate);
        Forme testForme = formeList.get(formeList.size() - 1);
        assertThat(testForme.getFormeLibelle()).isEqualTo(UPDATED_FORME_LIBELLE);
    }

    @Test
    @Transactional
    public void updateNonExistingForme() throws Exception {
        int databaseSizeBeforeUpdate = formeRepository.findAll().size();

        // Create the Forme

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFormeMockMvc.perform(put("/api/formes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(forme)))
            .andExpect(status().isBadRequest());

        // Validate the Forme in the database
        List<Forme> formeList = formeRepository.findAll();
        assertThat(formeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteForme() throws Exception {
        // Initialize the database
        formeService.save(forme);

        int databaseSizeBeforeDelete = formeRepository.findAll().size();

        // Delete the forme
        restFormeMockMvc.perform(delete("/api/formes/{id}", forme.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database is empty
        List<Forme> formeList = formeRepository.findAll();
        assertThat(formeList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Forme.class);
        Forme forme1 = new Forme();
        forme1.setId(1L);
        Forme forme2 = new Forme();
        forme2.setId(forme1.getId());
        assertThat(forme1).isEqualTo(forme2);
        forme2.setId(2L);
        assertThat(forme1).isNotEqualTo(forme2);
        forme1.setId(null);
        assertThat(forme1).isNotEqualTo(forme2);
    }
}
