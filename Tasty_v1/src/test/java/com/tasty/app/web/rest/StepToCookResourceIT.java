package com.tasty.app.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.tasty.app.IntegrationTest;
import com.tasty.app.domain.StepToCook;
import com.tasty.app.repository.StepToCookRepository;
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
 * Integration tests for the {@link StepToCookResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class StepToCookResourceIT {

    private static final String DEFAULT_CONTENT = "AAAAAAAAAA";
    private static final String UPDATED_CONTENT = "BBBBBBBBBB";

    private static final Long DEFAULT_INDEX = 1L;
    private static final Long UPDATED_INDEX = 2L;

    private static final String ENTITY_API_URL = "/api/step-to-cooks";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private StepToCookRepository stepToCookRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restStepToCookMockMvc;

    private StepToCook stepToCook;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static StepToCook createEntity(EntityManager em) {
        StepToCook stepToCook = new StepToCook().content(DEFAULT_CONTENT).index(DEFAULT_INDEX);
        return stepToCook;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static StepToCook createUpdatedEntity(EntityManager em) {
        StepToCook stepToCook = new StepToCook().content(UPDATED_CONTENT).index(UPDATED_INDEX);
        return stepToCook;
    }

    @BeforeEach
    public void initTest() {
        stepToCook = createEntity(em);
    }

    @Test
    @Transactional
    void createStepToCook() throws Exception {
        int databaseSizeBeforeCreate = stepToCookRepository.findAll().size();
        // Create the StepToCook
        restStepToCookMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(stepToCook)))
            .andExpect(status().isCreated());

        // Validate the StepToCook in the database
        List<StepToCook> stepToCookList = stepToCookRepository.findAll();
        assertThat(stepToCookList).hasSize(databaseSizeBeforeCreate + 1);
        StepToCook testStepToCook = stepToCookList.get(stepToCookList.size() - 1);
        assertThat(testStepToCook.getContent()).isEqualTo(DEFAULT_CONTENT);
        assertThat(testStepToCook.getIndex()).isEqualTo(DEFAULT_INDEX);
    }

    @Test
    @Transactional
    void createStepToCookWithExistingId() throws Exception {
        // Create the StepToCook with an existing ID
        stepToCook.setId(1L);

        int databaseSizeBeforeCreate = stepToCookRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restStepToCookMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(stepToCook)))
            .andExpect(status().isBadRequest());

        // Validate the StepToCook in the database
        List<StepToCook> stepToCookList = stepToCookRepository.findAll();
        assertThat(stepToCookList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllStepToCooks() throws Exception {
        // Initialize the database
        stepToCookRepository.saveAndFlush(stepToCook);

        // Get all the stepToCookList
        restStepToCookMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(stepToCook.getId().intValue())))
            .andExpect(jsonPath("$.[*].content").value(hasItem(DEFAULT_CONTENT)))
            .andExpect(jsonPath("$.[*].index").value(hasItem(DEFAULT_INDEX.intValue())));
    }

    @Test
    @Transactional
    void getStepToCook() throws Exception {
        // Initialize the database
        stepToCookRepository.saveAndFlush(stepToCook);

        // Get the stepToCook
        restStepToCookMockMvc
            .perform(get(ENTITY_API_URL_ID, stepToCook.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(stepToCook.getId().intValue()))
            .andExpect(jsonPath("$.content").value(DEFAULT_CONTENT))
            .andExpect(jsonPath("$.index").value(DEFAULT_INDEX.intValue()));
    }

    @Test
    @Transactional
    void getNonExistingStepToCook() throws Exception {
        // Get the stepToCook
        restStepToCookMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingStepToCook() throws Exception {
        // Initialize the database
        stepToCookRepository.saveAndFlush(stepToCook);

        int databaseSizeBeforeUpdate = stepToCookRepository.findAll().size();

        // Update the stepToCook
        StepToCook updatedStepToCook = stepToCookRepository.findById(stepToCook.getId()).get();
        // Disconnect from session so that the updates on updatedStepToCook are not directly saved in db
        em.detach(updatedStepToCook);
        updatedStepToCook.content(UPDATED_CONTENT).index(UPDATED_INDEX);

        restStepToCookMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedStepToCook.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedStepToCook))
            )
            .andExpect(status().isOk());

        // Validate the StepToCook in the database
        List<StepToCook> stepToCookList = stepToCookRepository.findAll();
        assertThat(stepToCookList).hasSize(databaseSizeBeforeUpdate);
        StepToCook testStepToCook = stepToCookList.get(stepToCookList.size() - 1);
        assertThat(testStepToCook.getContent()).isEqualTo(UPDATED_CONTENT);
        assertThat(testStepToCook.getIndex()).isEqualTo(UPDATED_INDEX);
    }

    @Test
    @Transactional
    void putNonExistingStepToCook() throws Exception {
        int databaseSizeBeforeUpdate = stepToCookRepository.findAll().size();
        stepToCook.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restStepToCookMockMvc
            .perform(
                put(ENTITY_API_URL_ID, stepToCook.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(stepToCook))
            )
            .andExpect(status().isBadRequest());

        // Validate the StepToCook in the database
        List<StepToCook> stepToCookList = stepToCookRepository.findAll();
        assertThat(stepToCookList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchStepToCook() throws Exception {
        int databaseSizeBeforeUpdate = stepToCookRepository.findAll().size();
        stepToCook.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restStepToCookMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(stepToCook))
            )
            .andExpect(status().isBadRequest());

        // Validate the StepToCook in the database
        List<StepToCook> stepToCookList = stepToCookRepository.findAll();
        assertThat(stepToCookList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamStepToCook() throws Exception {
        int databaseSizeBeforeUpdate = stepToCookRepository.findAll().size();
        stepToCook.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restStepToCookMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(stepToCook)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the StepToCook in the database
        List<StepToCook> stepToCookList = stepToCookRepository.findAll();
        assertThat(stepToCookList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateStepToCookWithPatch() throws Exception {
        // Initialize the database
        stepToCookRepository.saveAndFlush(stepToCook);

        int databaseSizeBeforeUpdate = stepToCookRepository.findAll().size();

        // Update the stepToCook using partial update
        StepToCook partialUpdatedStepToCook = new StepToCook();
        partialUpdatedStepToCook.setId(stepToCook.getId());

        partialUpdatedStepToCook.content(UPDATED_CONTENT).index(UPDATED_INDEX);

        restStepToCookMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedStepToCook.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedStepToCook))
            )
            .andExpect(status().isOk());

        // Validate the StepToCook in the database
        List<StepToCook> stepToCookList = stepToCookRepository.findAll();
        assertThat(stepToCookList).hasSize(databaseSizeBeforeUpdate);
        StepToCook testStepToCook = stepToCookList.get(stepToCookList.size() - 1);
        assertThat(testStepToCook.getContent()).isEqualTo(UPDATED_CONTENT);
        assertThat(testStepToCook.getIndex()).isEqualTo(UPDATED_INDEX);
    }

    @Test
    @Transactional
    void fullUpdateStepToCookWithPatch() throws Exception {
        // Initialize the database
        stepToCookRepository.saveAndFlush(stepToCook);

        int databaseSizeBeforeUpdate = stepToCookRepository.findAll().size();

        // Update the stepToCook using partial update
        StepToCook partialUpdatedStepToCook = new StepToCook();
        partialUpdatedStepToCook.setId(stepToCook.getId());

        partialUpdatedStepToCook.content(UPDATED_CONTENT).index(UPDATED_INDEX);

        restStepToCookMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedStepToCook.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedStepToCook))
            )
            .andExpect(status().isOk());

        // Validate the StepToCook in the database
        List<StepToCook> stepToCookList = stepToCookRepository.findAll();
        assertThat(stepToCookList).hasSize(databaseSizeBeforeUpdate);
        StepToCook testStepToCook = stepToCookList.get(stepToCookList.size() - 1);
        assertThat(testStepToCook.getContent()).isEqualTo(UPDATED_CONTENT);
        assertThat(testStepToCook.getIndex()).isEqualTo(UPDATED_INDEX);
    }

    @Test
    @Transactional
    void patchNonExistingStepToCook() throws Exception {
        int databaseSizeBeforeUpdate = stepToCookRepository.findAll().size();
        stepToCook.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restStepToCookMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, stepToCook.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(stepToCook))
            )
            .andExpect(status().isBadRequest());

        // Validate the StepToCook in the database
        List<StepToCook> stepToCookList = stepToCookRepository.findAll();
        assertThat(stepToCookList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchStepToCook() throws Exception {
        int databaseSizeBeforeUpdate = stepToCookRepository.findAll().size();
        stepToCook.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restStepToCookMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(stepToCook))
            )
            .andExpect(status().isBadRequest());

        // Validate the StepToCook in the database
        List<StepToCook> stepToCookList = stepToCookRepository.findAll();
        assertThat(stepToCookList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamStepToCook() throws Exception {
        int databaseSizeBeforeUpdate = stepToCookRepository.findAll().size();
        stepToCook.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restStepToCookMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(stepToCook))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the StepToCook in the database
        List<StepToCook> stepToCookList = stepToCookRepository.findAll();
        assertThat(stepToCookList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteStepToCook() throws Exception {
        // Initialize the database
        stepToCookRepository.saveAndFlush(stepToCook);

        int databaseSizeBeforeDelete = stepToCookRepository.findAll().size();

        // Delete the stepToCook
        restStepToCookMockMvc
            .perform(delete(ENTITY_API_URL_ID, stepToCook.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<StepToCook> stepToCookList = stepToCookRepository.findAll();
        assertThat(stepToCookList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
