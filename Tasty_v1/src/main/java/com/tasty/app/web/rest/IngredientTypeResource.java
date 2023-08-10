package com.tasty.app.web.rest;

import com.tasty.app.domain.IngredientType;
import com.tasty.app.repository.IngredientTypeRepository;
import com.tasty.app.service.IngredientTypeService;
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
 * REST controller for managing {@link com.tasty.app.domain.IngredientType}.
 */
@RestController
@RequestMapping("/api")
public class IngredientTypeResource {

    private final Logger log = LoggerFactory.getLogger(IngredientTypeResource.class);

    private static final String ENTITY_NAME = "ingredientType";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final IngredientTypeService ingredientTypeService;

    private final IngredientTypeRepository ingredientTypeRepository;

    public IngredientTypeResource(IngredientTypeService ingredientTypeService, IngredientTypeRepository ingredientTypeRepository) {
        this.ingredientTypeService = ingredientTypeService;
        this.ingredientTypeRepository = ingredientTypeRepository;
    }

    /**
     * {@code POST  /ingredient-types} : Create a new ingredientType.
     *
     * @param ingredientType the ingredientType to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new ingredientType, or with status {@code 400 (Bad Request)} if the ingredientType has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/ingredient-types")
    public ResponseEntity<IngredientType> createIngredientType(@RequestBody IngredientType ingredientType) throws URISyntaxException {
        log.debug("REST request to save IngredientType : {}", ingredientType);
        if (ingredientType.getId() != null) {
            throw new BadRequestAlertException("A new ingredientType cannot already have an ID", ENTITY_NAME, "idexists");
        }
        IngredientType result = ingredientTypeService.save(ingredientType);
        return ResponseEntity
            .created(new URI("/api/ingredient-types/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /ingredient-types/:id} : Updates an existing ingredientType.
     *
     * @param id the id of the ingredientType to save.
     * @param ingredientType the ingredientType to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated ingredientType,
     * or with status {@code 400 (Bad Request)} if the ingredientType is not valid,
     * or with status {@code 500 (Internal Server Error)} if the ingredientType couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/ingredient-types/{id}")
    public ResponseEntity<IngredientType> updateIngredientType(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody IngredientType ingredientType
    ) throws URISyntaxException {
        log.debug("REST request to update IngredientType : {}, {}", id, ingredientType);
        if (ingredientType.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, ingredientType.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!ingredientTypeRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        IngredientType result = ingredientTypeService.update(ingredientType);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, ingredientType.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /ingredient-types/:id} : Partial updates given fields of an existing ingredientType, field will ignore if it is null
     *
     * @param id the id of the ingredientType to save.
     * @param ingredientType the ingredientType to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated ingredientType,
     * or with status {@code 400 (Bad Request)} if the ingredientType is not valid,
     * or with status {@code 404 (Not Found)} if the ingredientType is not found,
     * or with status {@code 500 (Internal Server Error)} if the ingredientType couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/ingredient-types/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<IngredientType> partialUpdateIngredientType(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody IngredientType ingredientType
    ) throws URISyntaxException {
        log.debug("REST request to partial update IngredientType partially : {}, {}", id, ingredientType);
        if (ingredientType.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, ingredientType.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!ingredientTypeRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<IngredientType> result = ingredientTypeService.partialUpdate(ingredientType);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, ingredientType.getId().toString())
        );
    }

    /**
     * {@code GET  /ingredient-types} : get all the ingredientTypes.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of ingredientTypes in body.
     */
    @GetMapping("/ingredient-types")
    public ResponseEntity<List<IngredientType>> getAllIngredientTypes(@org.springdoc.api.annotations.ParameterObject Pageable pageable) {
        log.debug("REST request to get a page of IngredientTypes");
        Page<IngredientType> page = ingredientTypeService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /ingredient-types/:id} : get the "id" ingredientType.
     *
     * @param id the id of the ingredientType to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the ingredientType, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/ingredient-types/{id}")
    public ResponseEntity<IngredientType> getIngredientType(@PathVariable Long id) {
        log.debug("REST request to get IngredientType : {}", id);
        Optional<IngredientType> ingredientType = ingredientTypeService.findOne(id);
        return ResponseUtil.wrapOrNotFound(ingredientType);
    }

    /**
     * {@code DELETE  /ingredient-types/:id} : delete the "id" ingredientType.
     *
     * @param id the id of the ingredientType to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/ingredient-types/{id}")
    public ResponseEntity<Void> deleteIngredientType(@PathVariable Long id) {
        log.debug("REST request to delete IngredientType : {}", id);
        ingredientTypeService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
