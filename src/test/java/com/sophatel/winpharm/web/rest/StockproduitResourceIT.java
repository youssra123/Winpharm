package com.sophatel.winpharm.web.rest;

import com.sophatel.winpharm.WinpharmApp;
import com.sophatel.winpharm.domain.Stockproduit;
import com.sophatel.winpharm.repository.StockproduitRepository;
import com.sophatel.winpharm.service.StockproduitService;
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
 * Integration tests for the {@Link StockproduitResource} REST controller.
 */
@SpringBootTest(classes = WinpharmApp.class)
public class StockproduitResourceIT {

    @Autowired
    private StockproduitRepository stockproduitRepository;

    @Autowired
    private StockproduitService stockproduitService;

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

    private MockMvc restStockproduitMockMvc;

    private Stockproduit stockproduit;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final StockproduitResource stockproduitResource = new StockproduitResource(stockproduitService);
        this.restStockproduitMockMvc = MockMvcBuilders.standaloneSetup(stockproduitResource)
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
    public static Stockproduit createEntity(EntityManager em) {
        Stockproduit stockproduit = new Stockproduit();
        return stockproduit;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Stockproduit createUpdatedEntity(EntityManager em) {
        Stockproduit stockproduit = new Stockproduit();
        return stockproduit;
    }

    @BeforeEach
    public void initTest() {
        stockproduit = createEntity(em);
    }

    @Test
    @Transactional
    public void createStockproduit() throws Exception {
        int databaseSizeBeforeCreate = stockproduitRepository.findAll().size();

        // Create the Stockproduit
        restStockproduitMockMvc.perform(post("/api/stockproduits")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(stockproduit)))
            .andExpect(status().isCreated());

        // Validate the Stockproduit in the database
        List<Stockproduit> stockproduitList = stockproduitRepository.findAll();
        assertThat(stockproduitList).hasSize(databaseSizeBeforeCreate + 1);
        Stockproduit testStockproduit = stockproduitList.get(stockproduitList.size() - 1);
    }

    @Test
    @Transactional
    public void createStockproduitWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = stockproduitRepository.findAll().size();

        // Create the Stockproduit with an existing ID
        stockproduit.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restStockproduitMockMvc.perform(post("/api/stockproduits")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(stockproduit)))
            .andExpect(status().isBadRequest());

        // Validate the Stockproduit in the database
        List<Stockproduit> stockproduitList = stockproduitRepository.findAll();
        assertThat(stockproduitList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllStockproduits() throws Exception {
        // Initialize the database
        stockproduitRepository.saveAndFlush(stockproduit);

        // Get all the stockproduitList
        restStockproduitMockMvc.perform(get("/api/stockproduits?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(stockproduit.getId().intValue())));
    }
    
    @Test
    @Transactional
    public void getStockproduit() throws Exception {
        // Initialize the database
        stockproduitRepository.saveAndFlush(stockproduit);

        // Get the stockproduit
        restStockproduitMockMvc.perform(get("/api/stockproduits/{id}", stockproduit.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(stockproduit.getId().intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingStockproduit() throws Exception {
        // Get the stockproduit
        restStockproduitMockMvc.perform(get("/api/stockproduits/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateStockproduit() throws Exception {
        // Initialize the database
        stockproduitService.save(stockproduit);

        int databaseSizeBeforeUpdate = stockproduitRepository.findAll().size();

        // Update the stockproduit
        Stockproduit updatedStockproduit = stockproduitRepository.findById(stockproduit.getId()).get();
        // Disconnect from session so that the updates on updatedStockproduit are not directly saved in db
        em.detach(updatedStockproduit);

        restStockproduitMockMvc.perform(put("/api/stockproduits")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedStockproduit)))
            .andExpect(status().isOk());

        // Validate the Stockproduit in the database
        List<Stockproduit> stockproduitList = stockproduitRepository.findAll();
        assertThat(stockproduitList).hasSize(databaseSizeBeforeUpdate);
        Stockproduit testStockproduit = stockproduitList.get(stockproduitList.size() - 1);
    }

    @Test
    @Transactional
    public void updateNonExistingStockproduit() throws Exception {
        int databaseSizeBeforeUpdate = stockproduitRepository.findAll().size();

        // Create the Stockproduit

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restStockproduitMockMvc.perform(put("/api/stockproduits")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(stockproduit)))
            .andExpect(status().isBadRequest());

        // Validate the Stockproduit in the database
        List<Stockproduit> stockproduitList = stockproduitRepository.findAll();
        assertThat(stockproduitList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteStockproduit() throws Exception {
        // Initialize the database
        stockproduitService.save(stockproduit);

        int databaseSizeBeforeDelete = stockproduitRepository.findAll().size();

        // Delete the stockproduit
        restStockproduitMockMvc.perform(delete("/api/stockproduits/{id}", stockproduit.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database is empty
        List<Stockproduit> stockproduitList = stockproduitRepository.findAll();
        assertThat(stockproduitList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Stockproduit.class);
        Stockproduit stockproduit1 = new Stockproduit();
        stockproduit1.setId(1L);
        Stockproduit stockproduit2 = new Stockproduit();
        stockproduit2.setId(stockproduit1.getId());
        assertThat(stockproduit1).isEqualTo(stockproduit2);
        stockproduit2.setId(2L);
        assertThat(stockproduit1).isNotEqualTo(stockproduit2);
        stockproduit1.setId(null);
        assertThat(stockproduit1).isNotEqualTo(stockproduit2);
    }
}
