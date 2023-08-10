package com.tasty.app.web.rest;

import com.tasty.app.domain.StepToCook;
import com.tasty.app.repository.StepToCookRepository;
import com.tasty.app.service.StepToCookService;
import com.tasty.app.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.tasty.app.domain.StepToCook}.
 */
@RestController
@RequestMapping("/api")
public class StepToCookResource {

    private final Logger log = LoggerFactory.getLogger(StepToCookResource.class);

    private static final String ENTITY_NAME = "stepToCook";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final StepToCookService stepToCookService;

    private final StepToCookRepository stepToCookRepository;

    public StepToCookResource(StepToCookService stepToCookService, StepToCookRepository stepToCookRepository) {
        this.stepToCookService = stepToCookService;
        this.stepToCookRepository = stepToCookRepository;
    }

    /**
     * {@code POST  /step-to-cooks} : Create a new stepToCook.
     *
     * @param stepToCook the stepToCook to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new stepToCook, or with status {@code 400 (Bad Request)} if the stepToCook has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/step-to-cooks")
    public ResponseEntity<StepToCook> createStepToCook(@RequestBody StepToCook stepToCook) throws URISyntaxException {
        log.debug("REST request to save StepToCook : {}", stepToCook);
        if (stepToCook.getId() != null) {
            throw new BadRequestAlertException("A new stepToCook cannot already have an ID", ENTITY_NAME, "idexists");
        }
        StepToCook result = stepToCookService.save(stepToCook);
        return ResponseEntity
            .created(new URI("/api/step-to-cooks/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /step-to-cooks/:id} : Updates an existing stepToCook.
     *
     * @param id the id of the stepToCook to save.
     * @param stepToCook the stepToCook to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated stepToCook,
     * or with status {@code 400 (Bad Request)} if the stepToCook is not valid,
     * or with status {@code 500 (Internal Server Error)} if the stepToCook couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/step-to-cooks/{id}")
    public ResponseEntity<StepToCook> updateStepToCook(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody StepToCook stepToCook
    ) throws URISyntaxException {
        log.debug("REST request to update StepToCook : {}, {}", id, stepToCook);
        if (stepToCook.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, stepToCook.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!stepToCookRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        StepToCook result = stepToCookService.update(stepToCook);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, stepToCook.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /step-to-cooks/:id} : Partial updates given fields of an existing stepToCook, field will ignore if it is null
     *
     * @param id the id of the stepToCook to save.
     * @param stepToCook the stepToCook to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated stepToCook,
     * or with status {@code 400 (Bad Request)} if the stepToCook is not valid,
     * or with status {@code 404 (Not Found)} if the stepToCook is not found,
     * or with status {@code 500 (Internal Server Error)} if the stepToCook couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/step-to-cooks/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<StepToCook> partialUpdateStepToCook(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody StepToCook stepToCook
    ) throws URISyntaxException {
        log.debug("REST request to partial update StepToCook partially : {}, {}", id, stepToCook);
        if (stepToCook.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, stepToCook.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!stepToCookRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<StepToCook> result = stepToCookService.partialUpdate(stepToCook);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, stepToCook.getId().toString())
        );
    }

    /**
     * {@code GET  /step-to-cooks} : get all the stepToCooks.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of stepToCooks in body.
     */
    @GetMapping("/step-to-cooks")
    public ResponseEntity<List<StepToCook>> getAllStepToCooks(@org.springdoc.api.annotations.ParameterObject Pageable pageable) {
        log.debug("REST request to get a page of StepToCooks");
        Page<StepToCook> page = stepToCookService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /step-to-cooks/:id} : get the "id" stepToCook.
     *
     * @param id the id of the stepToCook to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the stepToCook, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/step-to-cooks/{id}")
    public ResponseEntity<StepToCook> getStepToCook(@PathVariable Long id) {
        log.debug("REST request to get StepToCook : {}", id);
        Optional<StepToCook> stepToCook = stepToCookService.findOne(id);
        return ResponseUtil.wrapOrNotFound(stepToCook);
    }

    /**
     * {@code DELETE  /step-to-cooks/:id} : delete the "id" stepToCook.
     *
     * @param id the id of the stepToCook to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/step-to-cooks/{id}")
    public ResponseEntity<Void> deleteStepToCook(@PathVariable Long id) {
        log.debug("REST request to delete StepToCook : {}", id);
        stepToCookService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
