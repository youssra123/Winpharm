package com.sophatel.winpharm.web.rest;

import com.sophatel.winpharm.WinpharmApp;
import com.sophatel.winpharm.domain.Stock;
import com.sophatel.winpharm.repository.StockRepository;
import com.sophatel.winpharm.service.StockService;
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
 * Integration tests for the {@Link StockResource} REST controller.
 */
@SpringBootTest(classes = WinpharmApp.class)
public class StockResourceIT {

    private static final Integer DEFAULT_STOCK_COUVERTURE_MIN = 1;
    private static final Integer UPDATED_STOCK_COUVERTURE_MIN = 2;

    private static final Integer DEFAULT_STOCK_COUVERTURE_MAX = 1;
    private static final Integer UPDATED_STOCK_COUVERTURE_MAX = 2;

    private static final Integer DEFAULT_STOCK_QTE_1 = 0;
    private static final Integer UPDATED_STOCK_QTE_1 = 1;

    private static final Integer DEFAULT_STOCK_QTE_2 = 0;
    private static final Integer UPDATED_STOCK_QTE_2 = 1;

    private static final Integer DEFAULT_STOCK_QTE_3 = 0;
    private static final Integer UPDATED_STOCK_QTE_3 = 1;

    private static final Double DEFAULT_STOCK_PRIX_1 = 0D;
    private static final Double UPDATED_STOCK_PRIX_1 = 1D;

    private static final Double DEFAULT_STOCK_PRIX_2 = 0D;
    private static final Double UPDATED_STOCK_PRIX_2 = 1D;

    private static final Double DEFAULT_STOCK_PRIX_3 = 0D;
    private static final Double UPDATED_STOCK_PRIX_3 = 1D;

    private static final ZonedDateTime DEFAULT_STOCK_DATE_PEREMPTION_1 = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_STOCK_DATE_PEREMPTION_1 = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final ZonedDateTime DEFAULT_STOCK_DATE_PEREMPTION_2 = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_STOCK_DATE_PEREMPTION_2 = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final ZonedDateTime DEFAULT_STOCK_DATE_PEREMPTION_3 = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_STOCK_DATE_PEREMPTION_3 = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final Double DEFAULT_STOCK_PRIX_HT_1 = 0D;
    private static final Double UPDATED_STOCK_PRIX_HT_1 = 1D;

    private static final Double DEFAULT_STOCK_PRIX_HT_2 = 0D;
    private static final Double UPDATED_STOCK_PRIX_HT_2 = 1D;

    private static final Double DEFAULT_STOCK_PRIX_HT_3 = 1D;
    private static final Double UPDATED_STOCK_PRIX_HT_3 = 2D;

    private static final ZonedDateTime DEFAULT_STOCK_DATE_CREATION = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_STOCK_DATE_CREATION = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    @Autowired
    private StockRepository stockRepository;

    @Autowired
    private StockService stockService;

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

    private MockMvc restStockMockMvc;

    private Stock stock;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final StockResource stockResource = new StockResource(stockService);
        this.restStockMockMvc = MockMvcBuilders.standaloneSetup(stockResource)
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
    public static Stock createEntity(EntityManager em) {
        Stock stock = new Stock()
            .stockCouvertureMin(DEFAULT_STOCK_COUVERTURE_MIN)
            .stockCouvertureMax(DEFAULT_STOCK_COUVERTURE_MAX)
            .stockQte1(DEFAULT_STOCK_QTE_1)
            .stockQte2(DEFAULT_STOCK_QTE_2)
            .stockQte3(DEFAULT_STOCK_QTE_3)
            .stockPrix1(DEFAULT_STOCK_PRIX_1)
            .stockPrix2(DEFAULT_STOCK_PRIX_2)
            .stockPrix3(DEFAULT_STOCK_PRIX_3)
            .stockDatePeremption1(DEFAULT_STOCK_DATE_PEREMPTION_1)
            .stockDatePeremption2(DEFAULT_STOCK_DATE_PEREMPTION_2)
            .stockDatePeremption3(DEFAULT_STOCK_DATE_PEREMPTION_3)
            .stockPrixHT1(DEFAULT_STOCK_PRIX_HT_1)
            .stockPrixHT2(DEFAULT_STOCK_PRIX_HT_2)
            .stockPrixHT3(DEFAULT_STOCK_PRIX_HT_3)
            .stockDateCreation(DEFAULT_STOCK_DATE_CREATION);
        return stock;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Stock createUpdatedEntity(EntityManager em) {
        Stock stock = new Stock()
            .stockCouvertureMin(UPDATED_STOCK_COUVERTURE_MIN)
            .stockCouvertureMax(UPDATED_STOCK_COUVERTURE_MAX)
            .stockQte1(UPDATED_STOCK_QTE_1)
            .stockQte2(UPDATED_STOCK_QTE_2)
            .stockQte3(UPDATED_STOCK_QTE_3)
            .stockPrix1(UPDATED_STOCK_PRIX_1)
            .stockPrix2(UPDATED_STOCK_PRIX_2)
            .stockPrix3(UPDATED_STOCK_PRIX_3)
            .stockDatePeremption1(UPDATED_STOCK_DATE_PEREMPTION_1)
            .stockDatePeremption2(UPDATED_STOCK_DATE_PEREMPTION_2)
            .stockDatePeremption3(UPDATED_STOCK_DATE_PEREMPTION_3)
            .stockPrixHT1(UPDATED_STOCK_PRIX_HT_1)
            .stockPrixHT2(UPDATED_STOCK_PRIX_HT_2)
            .stockPrixHT3(UPDATED_STOCK_PRIX_HT_3)
            .stockDateCreation(UPDATED_STOCK_DATE_CREATION);
        return stock;
    }

    @BeforeEach
    public void initTest() {
        stock = createEntity(em);
    }

    @Test
    @Transactional
    public void createStock() throws Exception {
        int databaseSizeBeforeCreate = stockRepository.findAll().size();

        // Create the Stock
        restStockMockMvc.perform(post("/api/stocks")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(stock)))
            .andExpect(status().isCreated());

        // Validate the Stock in the database
        List<Stock> stockList = stockRepository.findAll();
        assertThat(stockList).hasSize(databaseSizeBeforeCreate + 1);
        Stock testStock = stockList.get(stockList.size() - 1);
        assertThat(testStock.getStockCouvertureMin()).isEqualTo(DEFAULT_STOCK_COUVERTURE_MIN);
        assertThat(testStock.getStockCouvertureMax()).isEqualTo(DEFAULT_STOCK_COUVERTURE_MAX);
        assertThat(testStock.getStockQte1()).isEqualTo(DEFAULT_STOCK_QTE_1);
        assertThat(testStock.getStockQte2()).isEqualTo(DEFAULT_STOCK_QTE_2);
        assertThat(testStock.getStockQte3()).isEqualTo(DEFAULT_STOCK_QTE_3);
        assertThat(testStock.getStockPrix1()).isEqualTo(DEFAULT_STOCK_PRIX_1);
        assertThat(testStock.getStockPrix2()).isEqualTo(DEFAULT_STOCK_PRIX_2);
        assertThat(testStock.getStockPrix3()).isEqualTo(DEFAULT_STOCK_PRIX_3);
        assertThat(testStock.getStockDatePeremption1()).isEqualTo(DEFAULT_STOCK_DATE_PEREMPTION_1);
        assertThat(testStock.getStockDatePeremption2()).isEqualTo(DEFAULT_STOCK_DATE_PEREMPTION_2);
        assertThat(testStock.getStockDatePeremption3()).isEqualTo(DEFAULT_STOCK_DATE_PEREMPTION_3);
        assertThat(testStock.getStockPrixHT1()).isEqualTo(DEFAULT_STOCK_PRIX_HT_1);
        assertThat(testStock.getStockPrixHT2()).isEqualTo(DEFAULT_STOCK_PRIX_HT_2);
        assertThat(testStock.getStockPrixHT3()).isEqualTo(DEFAULT_STOCK_PRIX_HT_3);
        assertThat(testStock.getStockDateCreation()).isEqualTo(DEFAULT_STOCK_DATE_CREATION);
    }

    @Test
    @Transactional
    public void createStockWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = stockRepository.findAll().size();

        // Create the Stock with an existing ID
        stock.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restStockMockMvc.perform(post("/api/stocks")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(stock)))
            .andExpect(status().isBadRequest());

        // Validate the Stock in the database
        List<Stock> stockList = stockRepository.findAll();
        assertThat(stockList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkStockCouvertureMinIsRequired() throws Exception {
        int databaseSizeBeforeTest = stockRepository.findAll().size();
        // set the field null
        stock.setStockCouvertureMin(null);

        // Create the Stock, which fails.

        restStockMockMvc.perform(post("/api/stocks")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(stock)))
            .andExpect(status().isBadRequest());

        List<Stock> stockList = stockRepository.findAll();
        assertThat(stockList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkStockCouvertureMaxIsRequired() throws Exception {
        int databaseSizeBeforeTest = stockRepository.findAll().size();
        // set the field null
        stock.setStockCouvertureMax(null);

        // Create the Stock, which fails.

        restStockMockMvc.perform(post("/api/stocks")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(stock)))
            .andExpect(status().isBadRequest());

        List<Stock> stockList = stockRepository.findAll();
        assertThat(stockList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkStockQte1IsRequired() throws Exception {
        int databaseSizeBeforeTest = stockRepository.findAll().size();
        // set the field null
        stock.setStockQte1(null);

        // Create the Stock, which fails.

        restStockMockMvc.perform(post("/api/stocks")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(stock)))
            .andExpect(status().isBadRequest());

        List<Stock> stockList = stockRepository.findAll();
        assertThat(stockList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkStockPrix1IsRequired() throws Exception {
        int databaseSizeBeforeTest = stockRepository.findAll().size();
        // set the field null
        stock.setStockPrix1(null);

        // Create the Stock, which fails.

        restStockMockMvc.perform(post("/api/stocks")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(stock)))
            .andExpect(status().isBadRequest());

        List<Stock> stockList = stockRepository.findAll();
        assertThat(stockList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkStockDatePeremption1IsRequired() throws Exception {
        int databaseSizeBeforeTest = stockRepository.findAll().size();
        // set the field null
        stock.setStockDatePeremption1(null);

        // Create the Stock, which fails.

        restStockMockMvc.perform(post("/api/stocks")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(stock)))
            .andExpect(status().isBadRequest());

        List<Stock> stockList = stockRepository.findAll();
        assertThat(stockList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkStockPrixHT1IsRequired() throws Exception {
        int databaseSizeBeforeTest = stockRepository.findAll().size();
        // set the field null
        stock.setStockPrixHT1(null);

        // Create the Stock, which fails.

        restStockMockMvc.perform(post("/api/stocks")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(stock)))
            .andExpect(status().isBadRequest());

        List<Stock> stockList = stockRepository.findAll();
        assertThat(stockList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkStockDateCreationIsRequired() throws Exception {
        int databaseSizeBeforeTest = stockRepository.findAll().size();
        // set the field null
        stock.setStockDateCreation(null);

        // Create the Stock, which fails.

        restStockMockMvc.perform(post("/api/stocks")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(stock)))
            .andExpect(status().isBadRequest());

        List<Stock> stockList = stockRepository.findAll();
        assertThat(stockList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllStocks() throws Exception {
        // Initialize the database
        stockRepository.saveAndFlush(stock);

        // Get all the stockList
        restStockMockMvc.perform(get("/api/stocks?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(stock.getId().intValue())))
            .andExpect(jsonPath("$.[*].stockCouvertureMin").value(hasItem(DEFAULT_STOCK_COUVERTURE_MIN)))
            .andExpect(jsonPath("$.[*].stockCouvertureMax").value(hasItem(DEFAULT_STOCK_COUVERTURE_MAX)))
            .andExpect(jsonPath("$.[*].stockQte1").value(hasItem(DEFAULT_STOCK_QTE_1)))
            .andExpect(jsonPath("$.[*].stockQte2").value(hasItem(DEFAULT_STOCK_QTE_2)))
            .andExpect(jsonPath("$.[*].stockQte3").value(hasItem(DEFAULT_STOCK_QTE_3)))
            .andExpect(jsonPath("$.[*].stockPrix1").value(hasItem(DEFAULT_STOCK_PRIX_1.doubleValue())))
            .andExpect(jsonPath("$.[*].stockPrix2").value(hasItem(DEFAULT_STOCK_PRIX_2.doubleValue())))
            .andExpect(jsonPath("$.[*].stockPrix3").value(hasItem(DEFAULT_STOCK_PRIX_3.doubleValue())))
            .andExpect(jsonPath("$.[*].stockDatePeremption1").value(hasItem(sameInstant(DEFAULT_STOCK_DATE_PEREMPTION_1))))
            .andExpect(jsonPath("$.[*].stockDatePeremption2").value(hasItem(sameInstant(DEFAULT_STOCK_DATE_PEREMPTION_2))))
            .andExpect(jsonPath("$.[*].stockDatePeremption3").value(hasItem(sameInstant(DEFAULT_STOCK_DATE_PEREMPTION_3))))
            .andExpect(jsonPath("$.[*].stockPrixHT1").value(hasItem(DEFAULT_STOCK_PRIX_HT_1.doubleValue())))
            .andExpect(jsonPath("$.[*].stockPrixHT2").value(hasItem(DEFAULT_STOCK_PRIX_HT_2.doubleValue())))
            .andExpect(jsonPath("$.[*].stockPrixHT3").value(hasItem(DEFAULT_STOCK_PRIX_HT_3.doubleValue())))
            .andExpect(jsonPath("$.[*].stockDateCreation").value(hasItem(sameInstant(DEFAULT_STOCK_DATE_CREATION))));
    }
    
    @Test
    @Transactional
    public void getStock() throws Exception {
        // Initialize the database
        stockRepository.saveAndFlush(stock);

        // Get the stock
        restStockMockMvc.perform(get("/api/stocks/{id}", stock.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(stock.getId().intValue()))
            .andExpect(jsonPath("$.stockCouvertureMin").value(DEFAULT_STOCK_COUVERTURE_MIN))
            .andExpect(jsonPath("$.stockCouvertureMax").value(DEFAULT_STOCK_COUVERTURE_MAX))
            .andExpect(jsonPath("$.stockQte1").value(DEFAULT_STOCK_QTE_1))
            .andExpect(jsonPath("$.stockQte2").value(DEFAULT_STOCK_QTE_2))
            .andExpect(jsonPath("$.stockQte3").value(DEFAULT_STOCK_QTE_3))
            .andExpect(jsonPath("$.stockPrix1").value(DEFAULT_STOCK_PRIX_1.doubleValue()))
            .andExpect(jsonPath("$.stockPrix2").value(DEFAULT_STOCK_PRIX_2.doubleValue()))
            .andExpect(jsonPath("$.stockPrix3").value(DEFAULT_STOCK_PRIX_3.doubleValue()))
            .andExpect(jsonPath("$.stockDatePeremption1").value(sameInstant(DEFAULT_STOCK_DATE_PEREMPTION_1)))
            .andExpect(jsonPath("$.stockDatePeremption2").value(sameInstant(DEFAULT_STOCK_DATE_PEREMPTION_2)))
            .andExpect(jsonPath("$.stockDatePeremption3").value(sameInstant(DEFAULT_STOCK_DATE_PEREMPTION_3)))
            .andExpect(jsonPath("$.stockPrixHT1").value(DEFAULT_STOCK_PRIX_HT_1.doubleValue()))
            .andExpect(jsonPath("$.stockPrixHT2").value(DEFAULT_STOCK_PRIX_HT_2.doubleValue()))
            .andExpect(jsonPath("$.stockPrixHT3").value(DEFAULT_STOCK_PRIX_HT_3.doubleValue()))
            .andExpect(jsonPath("$.stockDateCreation").value(sameInstant(DEFAULT_STOCK_DATE_CREATION)));
    }

    @Test
    @Transactional
    public void getNonExistingStock() throws Exception {
        // Get the stock
        restStockMockMvc.perform(get("/api/stocks/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateStock() throws Exception {
        // Initialize the database
        stockService.save(stock);

        int databaseSizeBeforeUpdate = stockRepository.findAll().size();

        // Update the stock
        Stock updatedStock = stockRepository.findById(stock.getId()).get();
        // Disconnect from session so that the updates on updatedStock are not directly saved in db
        em.detach(updatedStock);
        updatedStock
            .stockCouvertureMin(UPDATED_STOCK_COUVERTURE_MIN)
            .stockCouvertureMax(UPDATED_STOCK_COUVERTURE_MAX)
            .stockQte1(UPDATED_STOCK_QTE_1)
            .stockQte2(UPDATED_STOCK_QTE_2)
            .stockQte3(UPDATED_STOCK_QTE_3)
            .stockPrix1(UPDATED_STOCK_PRIX_1)
            .stockPrix2(UPDATED_STOCK_PRIX_2)
            .stockPrix3(UPDATED_STOCK_PRIX_3)
            .stockDatePeremption1(UPDATED_STOCK_DATE_PEREMPTION_1)
            .stockDatePeremption2(UPDATED_STOCK_DATE_PEREMPTION_2)
            .stockDatePeremption3(UPDATED_STOCK_DATE_PEREMPTION_3)
            .stockPrixHT1(UPDATED_STOCK_PRIX_HT_1)
            .stockPrixHT2(UPDATED_STOCK_PRIX_HT_2)
            .stockPrixHT3(UPDATED_STOCK_PRIX_HT_3)
            .stockDateCreation(UPDATED_STOCK_DATE_CREATION);

        restStockMockMvc.perform(put("/api/stocks")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedStock)))
            .andExpect(status().isOk());

        // Validate the Stock in the database
        List<Stock> stockList = stockRepository.findAll();
        assertThat(stockList).hasSize(databaseSizeBeforeUpdate);
        Stock testStock = stockList.get(stockList.size() - 1);
        assertThat(testStock.getStockCouvertureMin()).isEqualTo(UPDATED_STOCK_COUVERTURE_MIN);
        assertThat(testStock.getStockCouvertureMax()).isEqualTo(UPDATED_STOCK_COUVERTURE_MAX);
        assertThat(testStock.getStockQte1()).isEqualTo(UPDATED_STOCK_QTE_1);
        assertThat(testStock.getStockQte2()).isEqualTo(UPDATED_STOCK_QTE_2);
        assertThat(testStock.getStockQte3()).isEqualTo(UPDATED_STOCK_QTE_3);
        assertThat(testStock.getStockPrix1()).isEqualTo(UPDATED_STOCK_PRIX_1);
        assertThat(testStock.getStockPrix2()).isEqualTo(UPDATED_STOCK_PRIX_2);
        assertThat(testStock.getStockPrix3()).isEqualTo(UPDATED_STOCK_PRIX_3);
        assertThat(testStock.getStockDatePeremption1()).isEqualTo(UPDATED_STOCK_DATE_PEREMPTION_1);
        assertThat(testStock.getStockDatePeremption2()).isEqualTo(UPDATED_STOCK_DATE_PEREMPTION_2);
        assertThat(testStock.getStockDatePeremption3()).isEqualTo(UPDATED_STOCK_DATE_PEREMPTION_3);
        assertThat(testStock.getStockPrixHT1()).isEqualTo(UPDATED_STOCK_PRIX_HT_1);
        assertThat(testStock.getStockPrixHT2()).isEqualTo(UPDATED_STOCK_PRIX_HT_2);
        assertThat(testStock.getStockPrixHT3()).isEqualTo(UPDATED_STOCK_PRIX_HT_3);
        assertThat(testStock.getStockDateCreation()).isEqualTo(UPDATED_STOCK_DATE_CREATION);
    }

    @Test
    @Transactional
    public void updateNonExistingStock() throws Exception {
        int databaseSizeBeforeUpdate = stockRepository.findAll().size();

        // Create the Stock

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restStockMockMvc.perform(put("/api/stocks")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(stock)))
            .andExpect(status().isBadRequest());

        // Validate the Stock in the database
        List<Stock> stockList = stockRepository.findAll();
        assertThat(stockList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteStock() throws Exception {
        // Initialize the database
        stockService.save(stock);

        int databaseSizeBeforeDelete = stockRepository.findAll().size();

        // Delete the stock
        restStockMockMvc.perform(delete("/api/stocks/{id}", stock.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database is empty
        List<Stock> stockList = stockRepository.findAll();
        assertThat(stockList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Stock.class);
        Stock stock1 = new Stock();
        stock1.setId(1L);
        Stock stock2 = new Stock();
        stock2.setId(stock1.getId());
        assertThat(stock1).isEqualTo(stock2);
        stock2.setId(2L);
        assertThat(stock1).isNotEqualTo(stock2);
        stock1.setId(null);
        assertThat(stock1).isNotEqualTo(stock2);
    }
}
