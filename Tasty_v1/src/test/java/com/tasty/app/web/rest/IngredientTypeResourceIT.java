package com.tasty.app.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.tasty.app.IntegrationTest;
import com.tasty.app.domain.IngredientType;
import com.tasty.app.repository.IngredientTypeRepository;
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
 * Integration tests for the {@link IngredientTypeResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class IngredientTypeResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/ingredient-types";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private IngredientTypeRepository ingredientTypeRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restIngredientTypeMockMvc;

    private IngredientType ingredientType;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static IngredientType createEntity(EntityManager em) {
        IngredientType ingredientType = new IngredientType().name(DEFAULT_NAME);
        return ingredientType;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static IngredientType createUpdatedEntity(EntityManager em) {
        IngredientType ingredientType = new IngredientType().name(UPDATED_NAME);
        return ingredientType;
    }

    @BeforeEach
    public void initTest() {
        ingredientType = createEntity(em);
    }

    @Test
    @Transactional
    void createIngredientType() throws Exception {
        int databaseSizeBeforeCreate = ingredientTypeRepository.findAll().size();
        // Create the IngredientType
        restIngredientTypeMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(ingredientType))
            )
            .andExpect(status().isCreated());

        // Validate the IngredientType in the database
        List<IngredientType> ingredientTypeList = ingredientTypeRepository.findAll();
        assertThat(ingredientTypeList).hasSize(databaseSizeBeforeCreate + 1);
        IngredientType testIngredientType = ingredientTypeList.get(ingredientTypeList.size() - 1);
        assertThat(testIngredientType.getName()).isEqualTo(DEFAULT_NAME);
    }

    @Test
    @Transactional
    void createIngredientTypeWithExistingId() throws Exception {
        // Create the IngredientType with an existing ID
        ingredientType.setId(1L);

        int databaseSizeBeforeCreate = ingredientTypeRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restIngredientTypeMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(ingredientType))
            )
            .andExpect(status().isBadRequest());

        // Validate the IngredientType in the database
        List<IngredientType> ingredientTypeList = ingredientTypeRepository.findAll();
        assertThat(ingredientTypeList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllIngredientTypes() throws Exception {
        // Initialize the database
        ingredientTypeRepository.saveAndFlush(ingredientType);

        // Get all the ingredientTypeList
        restIngredientTypeMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(ingredientType.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)));
    }

    @Test
    @Transactional
    void getIngredientType() throws Exception {
        // Initialize the database
        ingredientTypeRepository.saveAndFlush(ingredientType);

        // Get the ingredientType
        restIngredientTypeMockMvc
            .perform(get(ENTITY_API_URL_ID, ingredientType.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(ingredientType.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME));
    }

    @Test
    @Transactional
    void getNonExistingIngredientType() throws Exception {
        // Get the ingredientType
        restIngredientTypeMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingIngredientType() throws Exception {
        // Initialize the database
        ingredientTypeRepository.saveAndFlush(ingredientType);

        int databaseSizeBeforeUpdate = ingredientTypeRepository.findAll().size();

        // Update the ingredientType
        IngredientType updatedIngredientType = ingredientTypeRepository.findById(ingredientType.getId()).get();
        // Disconnect from session so that the updates on updatedIngredientType are not directly saved in db
        em.detach(updatedIngredientType);
        updatedIngredientType.name(UPDATED_NAME);

        restIngredientTypeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedIngredientType.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedIngredientType))
            )
            .andExpect(status().isOk());

        // Validate the IngredientType in the database
        List<IngredientType> ingredientTypeList = ingredientTypeRepository.findAll();
        assertThat(ingredientTypeList).hasSize(databaseSizeBeforeUpdate);
        IngredientType testIngredientType = ingredientTypeList.get(ingredientTypeList.size() - 1);
        assertThat(testIngredientType.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    @Transactional
    void putNonExistingIngredientType() throws Exception {
        int databaseSizeBeforeUpdate = ingredientTypeRepository.findAll().size();
        ingredientType.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restIngredientTypeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, ingredientType.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(ingredientType))
            )
            .andExpect(status().isBadRequest());

        // Validate the IngredientType in the database
        List<IngredientType> ingredientTypeList = ingredientTypeRepository.findAll();
        assertThat(ingredientTypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchIngredientType() throws Exception {
        int databaseSizeBeforeUpdate = ingredientTypeRepository.findAll().size();
        ingredientType.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restIngredientTypeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(ingredientType))
            )
            .andExpect(status().isBadRequest());

        // Validate the IngredientType in the database
        List<IngredientType> ingredientTypeList = ingredientTypeRepository.findAll();
        assertThat(ingredientTypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamIngredientType() throws Exception {
        int databaseSizeBeforeUpdate = ingredientTypeRepository.findAll().size();
        ingredientType.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restIngredientTypeMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(ingredientType)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the IngredientType in the database
        List<IngredientType> ingredientTypeList = ingredientTypeRepository.findAll();
        assertThat(ingredientTypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateIngredientTypeWithPatch() throws Exception {
        // Initialize the database
        ingredientTypeRepository.saveAndFlush(ingredientType);

        int databaseSizeBeforeUpdate = ingredientTypeRepository.findAll().size();

        // Update the ingredientType using partial update
        IngredientType partialUpdatedIngredientType = new IngredientType();
        partialUpdatedIngredientType.setId(ingredientType.getId());

        restIngredientTypeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedIngredientType.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedIngredientType))
            )
            .andExpect(status().isOk());

        // Validate the IngredientType in the database
        List<IngredientType> ingredientTypeList = ingredientTypeRepository.findAll();
        assertThat(ingredientTypeList).hasSize(databaseSizeBeforeUpdate);
        IngredientType testIngredientType = ingredientTypeList.get(ingredientTypeList.size() - 1);
        assertThat(testIngredientType.getName()).isEqualTo(DEFAULT_NAME);
    }

    @Test
    @Transactional
    void fullUpdateIngredientTypeWithPatch() throws Exception {
        // Initialize the database
        ingredientTypeRepository.saveAndFlush(ingredientType);

        int databaseSizeBeforeUpdate = ingredientTypeRepository.findAll().size();

        // Update the ingredientType using partial update
        IngredientType partialUpdatedIngredientType = new IngredientType();
        partialUpdatedIngredientType.setId(ingredientType.getId());

        partialUpdatedIngredientType.name(UPDATED_NAME);

        restIngredientTypeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedIngredientType.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedIngredientType))
            )
            .andExpect(status().isOk());

        // Validate the IngredientType in the database
        List<IngredientType> ingredientTypeList = ingredientTypeRepository.findAll();
        assertThat(ingredientTypeList).hasSize(databaseSizeBeforeUpdate);
        IngredientType testIngredientType = ingredientTypeList.get(ingredientTypeList.size() - 1);
        assertThat(testIngredientType.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    @Transactional
    void patchNonExistingIngredientType() throws Exception {
        int databaseSizeBeforeUpdate = ingredientTypeRepository.findAll().size();
        ingredientType.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restIngredientTypeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, ingredientType.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(ingredientType))
            )
            .andExpect(status().isBadRequest());

        // Validate the IngredientType in the database
        List<IngredientType> ingredientTypeList = ingredientTypeRepository.findAll();
        assertThat(ingredientTypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchIngredientType() throws Exception {
        int databaseSizeBeforeUpdate = ingredientTypeRepository.findAll().size();
        ingredientType.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restIngredientTypeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(ingredientType))
            )
            .andExpect(status().isBadRequest());

        // Validate the IngredientType in the database
        List<IngredientType> ingredientTypeList = ingredientTypeRepository.findAll();
        assertThat(ingredientTypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamIngredientType() throws Exception {
        int databaseSizeBeforeUpdate = ingredientTypeRepository.findAll().size();
        ingredientType.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restIngredientTypeMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(ingredientType))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the IngredientType in the database
        List<IngredientType> ingredientTypeList = ingredientTypeRepository.findAll();
        assertThat(ingredientTypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteIngredientType() throws Exception {
        // Initialize the database
        ingredientTypeRepository.saveAndFlush(ingredientType);

        int databaseSizeBeforeDelete = ingredientTypeRepository.findAll().size();

        // Delete the ingredientType
        restIngredientTypeMockMvc
            .perform(delete(ENTITY_API_URL_ID, ingredientType.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<IngredientType> ingredientTypeList = ingredientTypeRepository.findAll();
        assertThat(ingredientTypeList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
