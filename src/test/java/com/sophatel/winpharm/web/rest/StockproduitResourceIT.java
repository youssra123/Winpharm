package com.sophatel.winpharm.web.rest;

import com.sophatel.winpharm.WinpharmApp;
import com.sophatel.winpharm.domain.Stockproduit;
import com.sophatel.winpharm.domain.Produit;
import com.sophatel.winpharm.domain.Stock;
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
 * Integration tests for the {@Link StockproduitResource} REST controller.
 */
@SpringBootTest(classes = WinpharmApp.class)
public class StockproduitResourceIT {

    private static final Integer DEFAULT_STOCK_PRODUIT_QUANTITE = 1;
    private static final Integer UPDATED_STOCK_PRODUIT_QUANTITE = 2;

    private static final ZonedDateTime DEFAULT_STOCK_PRODUIT_DATE_CREATION = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_STOCK_PRODUIT_DATE_CREATION = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final ZonedDateTime DEFAULT_STOCK_PRODUIT_DATE_PEREMPTION = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_STOCK_PRODUIT_DATE_PEREMPTION = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final Double DEFAULT_STOCK_PRODUIT_PRIX_VENTE = 1D;
    private static final Double UPDATED_STOCK_PRODUIT_PRIX_VENTE = 2D;

    private static final Double DEFAULT_STOCK_PRODUIT_PRIX_HORS_TAXE = 1D;
    private static final Double UPDATED_STOCK_PRODUIT_PRIX_HORS_TAXE = 2D;

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
        Stockproduit stockproduit = new Stockproduit()
            .stockProduitQuantite(DEFAULT_STOCK_PRODUIT_QUANTITE)
            .stockProduitDateCreation(DEFAULT_STOCK_PRODUIT_DATE_CREATION)
            .stockProduitDatePeremption(DEFAULT_STOCK_PRODUIT_DATE_PEREMPTION)
            .stockProduitPrixVente(DEFAULT_STOCK_PRODUIT_PRIX_VENTE)
            .stockProduitPrixHorsTaxe(DEFAULT_STOCK_PRODUIT_PRIX_HORS_TAXE);
        // Add required entity
        Produit produit;
        if (TestUtil.findAll(em, Produit.class).isEmpty()) {
            produit = ProduitResourceIT.createEntity(em);
            em.persist(produit);
            em.flush();
        } else {
            produit = TestUtil.findAll(em, Produit.class).get(0);
        }
        stockproduit.setStock_produit_produit(produit);
        // Add required entity
        Stock stock;
        if (TestUtil.findAll(em, Stock.class).isEmpty()) {
            stock = StockResourceIT.createEntity(em);
            em.persist(stock);
            em.flush();
        } else {
            stock = TestUtil.findAll(em, Stock.class).get(0);
        }
        stockproduit.setStock_produit_stock(stock);
        return stockproduit;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Stockproduit createUpdatedEntity(EntityManager em) {
        Stockproduit stockproduit = new Stockproduit()
            .stockProduitQuantite(UPDATED_STOCK_PRODUIT_QUANTITE)
            .stockProduitDateCreation(UPDATED_STOCK_PRODUIT_DATE_CREATION)
            .stockProduitDatePeremption(UPDATED_STOCK_PRODUIT_DATE_PEREMPTION)
            .stockProduitPrixVente(UPDATED_STOCK_PRODUIT_PRIX_VENTE)
            .stockProduitPrixHorsTaxe(UPDATED_STOCK_PRODUIT_PRIX_HORS_TAXE);
        // Add required entity
        Produit produit;
        if (TestUtil.findAll(em, Produit.class).isEmpty()) {
            produit = ProduitResourceIT.createUpdatedEntity(em);
            em.persist(produit);
            em.flush();
        } else {
            produit = TestUtil.findAll(em, Produit.class).get(0);
        }
        stockproduit.setStock_produit_produit(produit);
        // Add required entity
        Stock stock;
        if (TestUtil.findAll(em, Stock.class).isEmpty()) {
            stock = StockResourceIT.createUpdatedEntity(em);
            em.persist(stock);
            em.flush();
        } else {
            stock = TestUtil.findAll(em, Stock.class).get(0);
        }
        stockproduit.setStock_produit_stock(stock);
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
        assertThat(testStockproduit.getStockProduitQuantite()).isEqualTo(DEFAULT_STOCK_PRODUIT_QUANTITE);
        assertThat(testStockproduit.getStockProduitDateCreation()).isEqualTo(DEFAULT_STOCK_PRODUIT_DATE_CREATION);
        assertThat(testStockproduit.getStockProduitDatePeremption()).isEqualTo(DEFAULT_STOCK_PRODUIT_DATE_PEREMPTION);
        assertThat(testStockproduit.getStockProduitPrixVente()).isEqualTo(DEFAULT_STOCK_PRODUIT_PRIX_VENTE);
        assertThat(testStockproduit.getStockProduitPrixHorsTaxe()).isEqualTo(DEFAULT_STOCK_PRODUIT_PRIX_HORS_TAXE);
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
    public void checkStockProduitQuantiteIsRequired() throws Exception {
        int databaseSizeBeforeTest = stockproduitRepository.findAll().size();
        // set the field null
        stockproduit.setStockProduitQuantite(null);

        // Create the Stockproduit, which fails.

        restStockproduitMockMvc.perform(post("/api/stockproduits")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(stockproduit)))
            .andExpect(status().isBadRequest());

        List<Stockproduit> stockproduitList = stockproduitRepository.findAll();
        assertThat(stockproduitList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkStockProduitDateCreationIsRequired() throws Exception {
        int databaseSizeBeforeTest = stockproduitRepository.findAll().size();
        // set the field null
        stockproduit.setStockProduitDateCreation(null);

        // Create the Stockproduit, which fails.

        restStockproduitMockMvc.perform(post("/api/stockproduits")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(stockproduit)))
            .andExpect(status().isBadRequest());

        List<Stockproduit> stockproduitList = stockproduitRepository.findAll();
        assertThat(stockproduitList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkStockProduitDatePeremptionIsRequired() throws Exception {
        int databaseSizeBeforeTest = stockproduitRepository.findAll().size();
        // set the field null
        stockproduit.setStockProduitDatePeremption(null);

        // Create the Stockproduit, which fails.

        restStockproduitMockMvc.perform(post("/api/stockproduits")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(stockproduit)))
            .andExpect(status().isBadRequest());

        List<Stockproduit> stockproduitList = stockproduitRepository.findAll();
        assertThat(stockproduitList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkStockProduitPrixVenteIsRequired() throws Exception {
        int databaseSizeBeforeTest = stockproduitRepository.findAll().size();
        // set the field null
        stockproduit.setStockProduitPrixVente(null);

        // Create the Stockproduit, which fails.

        restStockproduitMockMvc.perform(post("/api/stockproduits")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(stockproduit)))
            .andExpect(status().isBadRequest());

        List<Stockproduit> stockproduitList = stockproduitRepository.findAll();
        assertThat(stockproduitList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkStockProduitPrixHorsTaxeIsRequired() throws Exception {
        int databaseSizeBeforeTest = stockproduitRepository.findAll().size();
        // set the field null
        stockproduit.setStockProduitPrixHorsTaxe(null);

        // Create the Stockproduit, which fails.

        restStockproduitMockMvc.perform(post("/api/stockproduits")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(stockproduit)))
            .andExpect(status().isBadRequest());

        List<Stockproduit> stockproduitList = stockproduitRepository.findAll();
        assertThat(stockproduitList).hasSize(databaseSizeBeforeTest);
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
            .andExpect(jsonPath("$.[*].id").value(hasItem(stockproduit.getId().intValue())))
            .andExpect(jsonPath("$.[*].stockProduitQuantite").value(hasItem(DEFAULT_STOCK_PRODUIT_QUANTITE)))
            .andExpect(jsonPath("$.[*].stockProduitDateCreation").value(hasItem(sameInstant(DEFAULT_STOCK_PRODUIT_DATE_CREATION))))
            .andExpect(jsonPath("$.[*].stockProduitDatePeremption").value(hasItem(sameInstant(DEFAULT_STOCK_PRODUIT_DATE_PEREMPTION))))
            .andExpect(jsonPath("$.[*].stockProduitPrixVente").value(hasItem(DEFAULT_STOCK_PRODUIT_PRIX_VENTE.doubleValue())))
            .andExpect(jsonPath("$.[*].stockProduitPrixHorsTaxe").value(hasItem(DEFAULT_STOCK_PRODUIT_PRIX_HORS_TAXE.doubleValue())));
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
            .andExpect(jsonPath("$.id").value(stockproduit.getId().intValue()))
            .andExpect(jsonPath("$.stockProduitQuantite").value(DEFAULT_STOCK_PRODUIT_QUANTITE))
            .andExpect(jsonPath("$.stockProduitDateCreation").value(sameInstant(DEFAULT_STOCK_PRODUIT_DATE_CREATION)))
            .andExpect(jsonPath("$.stockProduitDatePeremption").value(sameInstant(DEFAULT_STOCK_PRODUIT_DATE_PEREMPTION)))
            .andExpect(jsonPath("$.stockProduitPrixVente").value(DEFAULT_STOCK_PRODUIT_PRIX_VENTE.doubleValue()))
            .andExpect(jsonPath("$.stockProduitPrixHorsTaxe").value(DEFAULT_STOCK_PRODUIT_PRIX_HORS_TAXE.doubleValue()));
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
        updatedStockproduit
            .stockProduitQuantite(UPDATED_STOCK_PRODUIT_QUANTITE)
            .stockProduitDateCreation(UPDATED_STOCK_PRODUIT_DATE_CREATION)
            .stockProduitDatePeremption(UPDATED_STOCK_PRODUIT_DATE_PEREMPTION)
            .stockProduitPrixVente(UPDATED_STOCK_PRODUIT_PRIX_VENTE)
            .stockProduitPrixHorsTaxe(UPDATED_STOCK_PRODUIT_PRIX_HORS_TAXE);

        restStockproduitMockMvc.perform(put("/api/stockproduits")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedStockproduit)))
            .andExpect(status().isOk());

        // Validate the Stockproduit in the database
        List<Stockproduit> stockproduitList = stockproduitRepository.findAll();
        assertThat(stockproduitList).hasSize(databaseSizeBeforeUpdate);
        Stockproduit testStockproduit = stockproduitList.get(stockproduitList.size() - 1);
        assertThat(testStockproduit.getStockProduitQuantite()).isEqualTo(UPDATED_STOCK_PRODUIT_QUANTITE);
        assertThat(testStockproduit.getStockProduitDateCreation()).isEqualTo(UPDATED_STOCK_PRODUIT_DATE_CREATION);
        assertThat(testStockproduit.getStockProduitDatePeremption()).isEqualTo(UPDATED_STOCK_PRODUIT_DATE_PEREMPTION);
        assertThat(testStockproduit.getStockProduitPrixVente()).isEqualTo(UPDATED_STOCK_PRODUIT_PRIX_VENTE);
        assertThat(testStockproduit.getStockProduitPrixHorsTaxe()).isEqualTo(UPDATED_STOCK_PRODUIT_PRIX_HORS_TAXE);
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
