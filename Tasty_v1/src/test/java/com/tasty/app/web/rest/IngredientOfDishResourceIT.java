package com.tasty.app.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.tasty.app.IntegrationTest;
import com.tasty.app.domain.IngredientOfDish;
import com.tasty.app.repository.IngredientOfDishRepository;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link IngredientOfDishResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class IngredientOfDishResourceIT {

    private static final String DEFAULT_UNIT = "AAAAAAAAAA";
    private static final String UPDATED_UNIT = "BBBBBBBBBB";

    private static final Long DEFAULT_QUANTITY = 1L;
    private static final Long UPDATED_QUANTITY = 2L;

    private static final String ENTITY_API_URL = "/api/ingredient-of-dishes";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private IngredientOfDishRepository ingredientOfDishRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restIngredientOfDishMockMvc;

    private IngredientOfDish ingredientOfDish;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static IngredientOfDish createEntity(EntityManager em) {
        IngredientOfDish ingredientOfDish = new IngredientOfDish().unit(DEFAULT_UNIT).quantity(DEFAULT_QUANTITY);
        return ingredientOfDish;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static IngredientOfDish createUpdatedEntity(EntityManager em) {
        IngredientOfDish ingredientOfDish = new IngredientOfDish().unit(UPDATED_UNIT).quantity(UPDATED_QUANTITY);
        return ingredientOfDish;
    }

    @BeforeEach
    public void initTest() {
        ingredientOfDish = createEntity(em);
    }

    @Test
    @Transactional
    void createIngredientOfDish() throws Exception {
        int databaseSizeBeforeCreate = ingredientOfDishRepository.findAll().size();
        // Create the IngredientOfDish
        restIngredientOfDishMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(ingredientOfDish))
            )
            .andExpect(status().isCreated());

        // Validate the IngredientOfDish in the database
        List<IngredientOfDish> ingredientOfDishList = ingredientOfDishRepository.findAll();
        assertThat(ingredientOfDishList).hasSize(databaseSizeBeforeCreate + 1);
        IngredientOfDish testIngredientOfDish = ingredientOfDishList.get(ingredientOfDishList.size() - 1);
        assertThat(testIngredientOfDish.getUnit()).isEqualTo(DEFAULT_UNIT);
        assertThat(testIngredientOfDish.getQuantity()).isEqualTo(DEFAULT_QUANTITY);
    }

    @Test
    @Transactional
    void createIngredientOfDishWithExistingId() throws Exception {
        // Create the IngredientOfDish with an existing ID
        ingredientOfDish.setId(1L);

        int databaseSizeBeforeCreate = ingredientOfDishRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restIngredientOfDishMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(ingredientOfDish))
            )
            .andExpect(status().isBadRequest());

        // Validate the IngredientOfDish in the database
        List<IngredientOfDish> ingredientOfDishList = ingredientOfDishRepository.findAll();
        assertThat(ingredientOfDishList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllIngredientOfDishes() throws Exception {
        // Initialize the database
        ingredientOfDishRepository.saveAndFlush(ingredientOfDish);

        // Get all the ingredientOfDishList
        restIngredientOfDishMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(ingredientOfDish.getId().intValue())))
            .andExpect(jsonPath("$.[*].unit").value(hasItem(DEFAULT_UNIT)))
            .andExpect(jsonPath("$.[*].quantity").value(hasItem(DEFAULT_QUANTITY.intValue())));
    }

    @Test
    @Transactional
    void getIngredientOfDish() throws Exception {
        // Initialize the database
        ingredientOfDishRepository.saveAndFlush(ingredientOfDish);

        // Get the ingredientOfDish
        restIngredientOfDishMockMvc
            .perform(get(ENTITY_API_URL_ID, ingredientOfDish.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(ingredientOfDish.getId().intValue()))
            .andExpect(jsonPath("$.unit").value(DEFAULT_UNIT))
            .andExpect(jsonPath("$.quantity").value(DEFAULT_QUANTITY.intValue()));
    }

    @Test
    @Transactional
    void getNonExistingIngredientOfDish() throws Exception {
        // Get the ingredientOfDish
        restIngredientOfDishMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingIngredientOfDish() throws Exception {
        // Initialize the database
        ingredientOfDishRepository.saveAndFlush(ingredientOfDish);

        int databaseSizeBeforeUpdate = ingredientOfDishRepository.findAll().size();

        // Update the ingredientOfDish
        IngredientOfDish updatedIngredientOfDish = ingredientOfDishRepository.findById(ingredientOfDish.getId()).get();
        // Disconnect from session so that the updates on updatedIngredientOfDish are not directly saved in db
        em.detach(updatedIngredientOfDish);
        updatedIngredientOfDish.unit(UPDATED_UNIT).quantity(UPDATED_QUANTITY);

        restIngredientOfDishMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedIngredientOfDish.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedIngredientOfDish))
            )
            .andExpect(status().isOk());

        // Validate the IngredientOfDish in the database
        List<IngredientOfDish> ingredientOfDishList = ingredientOfDishRepository.findAll();
        assertThat(ingredientOfDishList).hasSize(databaseSizeBeforeUpdate);
        IngredientOfDish testIngredientOfDish = ingredientOfDishList.get(ingredientOfDishList.size() - 1);
        assertThat(testIngredientOfDish.getUnit()).isEqualTo(UPDATED_UNIT);
        assertThat(testIngredientOfDish.getQuantity()).isEqualTo(UPDATED_QUANTITY);
    }

    @Test
    @Transactional
    void putNonExistingIngredientOfDish() throws Exception {
        int databaseSizeBeforeUpdate = ingredientOfDishRepository.findAll().size();
        ingredientOfDish.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restIngredientOfDishMockMvc
            .perform(
                put(ENTITY_API_URL_ID, ingredientOfDish.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(ingredientOfDish))
            )
            .andExpect(status().isBadRequest());

        // Validate the IngredientOfDish in the database
        List<IngredientOfDish> ingredientOfDishList = ingredientOfDishRepository.findAll();
        assertThat(ingredientOfDishList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchIngredientOfDish() throws Exception {
        int databaseSizeBeforeUpdate = ingredientOfDishRepository.findAll().size();
        ingredientOfDish.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restIngredientOfDishMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(ingredientOfDish))
            )
            .andExpect(status().isBadRequest());

        // Validate the IngredientOfDish in the database
        List<IngredientOfDish> ingredientOfDishList = ingredientOfDishRepository.findAll();
        assertThat(ingredientOfDishList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamIngredientOfDish() throws Exception {
        int databaseSizeBeforeUpdate = ingredientOfDishRepository.findAll().size();
        ingredientOfDish.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restIngredientOfDishMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(ingredientOfDish))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the IngredientOfDish in the database
        List<IngredientOfDish> ingredientOfDishList = ingredientOfDishRepository.findAll();
        assertThat(ingredientOfDishList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateIngredientOfDishWithPatch() throws Exception {
        // Initialize the database
        ingredientOfDishRepository.saveAndFlush(ingredientOfDish);

        int databaseSizeBeforeUpdate = ingredientOfDishRepository.findAll().size();

        // Update the ingredientOfDish using partial update
        IngredientOfDish partialUpdatedIngredientOfDish = new IngredientOfDish();
        partialUpdatedIngredientOfDish.setId(ingredientOfDish.getId());

        partialUpdatedIngredientOfDish.unit(UPDATED_UNIT).quantity(UPDATED_QUANTITY);

        restIngredientOfDishMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedIngredientOfDish.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedIngredientOfDish))
            )
            .andExpect(status().isOk());

        // Validate the IngredientOfDish in the database
        List<IngredientOfDish> ingredientOfDishList = ingredientOfDishRepository.findAll();
        assertThat(ingredientOfDishList).hasSize(databaseSizeBeforeUpdate);
        IngredientOfDish testIngredientOfDish = ingredientOfDishList.get(ingredientOfDishList.size() - 1);
        assertThat(testIngredientOfDish.getUnit()).isEqualTo(UPDATED_UNIT);
        assertThat(testIngredientOfDish.getQuantity()).isEqualTo(UPDATED_QUANTITY);
    }

    @Test
    @Transactional
    void fullUpdateIngredientOfDishWithPatch() throws Exception {
        // Initialize the database
        ingredientOfDishRepository.saveAndFlush(ingredientOfDish);

        int databaseSizeBeforeUpdate = ingredientOfDishRepository.findAll().size();

        // Update the ingredientOfDish using partial update
        IngredientOfDish partialUpdatedIngredientOfDish = new IngredientOfDish();
        partialUpdatedIngredientOfDish.setId(ingredientOfDish.getId());

        partialUpdatedIngredientOfDish.unit(UPDATED_UNIT).quantity(UPDATED_QUANTITY);

        restIngredientOfDishMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedIngredientOfDish.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedIngredientOfDish))
            )
            .andExpect(status().isOk());

        // Validate the IngredientOfDish in the database
        List<IngredientOfDish> ingredientOfDishList = ingredientOfDishRepository.findAll();
        assertThat(ingredientOfDishList).hasSize(databaseSizeBeforeUpdate);
        IngredientOfDish testIngredientOfDish = ingredientOfDishList.get(ingredientOfDishList.size() - 1);
        assertThat(testIngredientOfDish.getUnit()).isEqualTo(UPDATED_UNIT);
        assertThat(testIngredientOfDish.getQuantity()).isEqualTo(UPDATED_QUANTITY);
    }

    @Test
    @Transactional
    void patchNonExistingIngredientOfDish() throws Exception {
        int databaseSizeBeforeUpdate = ingredientOfDishRepository.findAll().size();
        ingredientOfDish.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restIngredientOfDishMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, ingredientOfDish.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(ingredientOfDish))
            )
            .andExpect(status().isBadRequest());

        // Validate the IngredientOfDish in the database
        List<IngredientOfDish> ingredientOfDishList = ingredientOfDishRepository.findAll();
        assertThat(ingredientOfDishList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchIngredientOfDish() throws Exception {
        int databaseSizeBeforeUpdate = ingredientOfDishRepository.findAll().size();
        ingredientOfDish.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restIngredientOfDishMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(ingredientOfDish))
            )
            .andExpect(status().isBadRequest());

        // Validate the IngredientOfDish in the database
        List<IngredientOfDish> ingredientOfDishList = ingredientOfDishRepository.findAll();
        assertThat(ingredientOfDishList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamIngredientOfDish() throws Exception {
        int databaseSizeBeforeUpdate = ingredientOfDishRepository.findAll().size();
        ingredientOfDish.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restIngredientOfDishMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(ingredientOfDish))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the IngredientOfDish in the database
        List<IngredientOfDish> ingredientOfDishList = ingredientOfDishRepository.findAll();
        assertThat(ingredientOfDishList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteIngredientOfDish() throws Exception {
        // Initialize the database
        ingredientOfDishRepository.saveAndFlush(ingredientOfDish);

        int databaseSizeBeforeDelete = ingredientOfDishRepository.findAll().size();

        // Delete the ingredientOfDish
        restIngredientOfDishMockMvc
            .perform(delete(ENTITY_API_URL_ID, ingredientOfDish.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<IngredientOfDish> ingredientOfDishList = ingredientOfDishRepository.findAll();
        assertThat(ingredientOfDishList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
