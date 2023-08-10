package com.tasty.app.web.rest;

import com.tasty.app.domain.IngredientOfDish;
import com.tasty.app.repository.IngredientOfDishRepository;
import com.tasty.app.service.IngredientOfDishService;
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
 * REST controller for managing {@link com.tasty.app.domain.IngredientOfDish}.
 */
@RestController
@RequestMapping("/api")
public class IngredientOfDishResource {

    private final Logger log = LoggerFactory.getLogger(IngredientOfDishResource.class);

    private static final String ENTITY_NAME = "ingredientOfDish";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final IngredientOfDishService ingredientOfDishService;

    private final IngredientOfDishRepository ingredientOfDishRepository;

    public IngredientOfDishResource(
        IngredientOfDishService ingredientOfDishService,
        IngredientOfDishRepository ingredientOfDishRepository
    ) {
        this.ingredientOfDishService = ingredientOfDishService;
        this.ingredientOfDishRepository = ingredientOfDishRepository;
    }

    /**
     * {@code POST  /ingredient-of-dishes} : Create a new ingredientOfDish.
     *
     * @param ingredientOfDish the ingredientOfDish to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new ingredientOfDish, or with status {@code 400 (Bad Request)} if the ingredientOfDish has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/ingredient-of-dishes")
    public ResponseEntity<IngredientOfDish> createIngredientOfDish(@RequestBody IngredientOfDish ingredientOfDish)
        throws URISyntaxException {
        log.debug("REST request to save IngredientOfDish : {}", ingredientOfDish);
        if (ingredientOfDish.getId() != null) {
            throw new BadRequestAlertException("A new ingredientOfDish cannot already have an ID", ENTITY_NAME, "idexists");
        }
        IngredientOfDish result = ingredientOfDishService.save(ingredientOfDish);
        return ResponseEntity
            .created(new URI("/api/ingredient-of-dishes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /ingredient-of-dishes/:id} : Updates an existing ingredientOfDish.
     *
     * @param id the id of the ingredientOfDish to save.
     * @param ingredientOfDish the ingredientOfDish to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated ingredientOfDish,
     * or with status {@code 400 (Bad Request)} if the ingredientOfDish is not valid,
     * or with status {@code 500 (Internal Server Error)} if the ingredientOfDish couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/ingredient-of-dishes/{id}")
    public ResponseEntity<IngredientOfDish> updateIngredientOfDish(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody IngredientOfDish ingredientOfDish
    ) throws URISyntaxException {
        log.debug("REST request to update IngredientOfDish : {}, {}", id, ingredientOfDish);
        if (ingredientOfDish.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, ingredientOfDish.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!ingredientOfDishRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        IngredientOfDish result = ingredientOfDishService.update(ingredientOfDish);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, ingredientOfDish.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /ingredient-of-dishes/:id} : Partial updates given fields of an existing ingredientOfDish, field will ignore if it is null
     *
     * @param id the id of the ingredientOfDish to save.
     * @param ingredientOfDish the ingredientOfDish to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated ingredientOfDish,
     * or with status {@code 400 (Bad Request)} if the ingredientOfDish is not valid,
     * or with status {@code 404 (Not Found)} if the ingredientOfDish is not found,
     * or with status {@code 500 (Internal Server Error)} if the ingredientOfDish couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/ingredient-of-dishes/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<IngredientOfDish> partialUpdateIngredientOfDish(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody IngredientOfDish ingredientOfDish
    ) throws URISyntaxException {
        log.debug("REST request to partial update IngredientOfDish partially : {}, {}", id, ingredientOfDish);
        if (ingredientOfDish.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, ingredientOfDish.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!ingredientOfDishRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<IngredientOfDish> result = ingredientOfDishService.partialUpdate(ingredientOfDish);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, ingredientOfDish.getId().toString())
        );
    }

    /**
     * {@code GET  /ingredient-of-dishes} : get all the ingredientOfDishes.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of ingredientOfDishes in body.
     */
    @GetMapping("/ingredient-of-dishes")
    public ResponseEntity<List<IngredientOfDish>> getAllIngredientOfDishes(
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get a page of IngredientOfDishes");
        Page<IngredientOfDish> page = ingredientOfDishService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /ingredient-of-dishes/:id} : get the "id" ingredientOfDish.
     *
     * @param id the id of the ingredientOfDish to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the ingredientOfDish, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/ingredient-of-dishes/{id}")
    public ResponseEntity<IngredientOfDish> getIngredientOfDish(@PathVariable Long id) {
        log.debug("REST request to get IngredientOfDish : {}", id);
        Optional<IngredientOfDish> ingredientOfDish = ingredientOfDishService.findOne(id);
        return ResponseUtil.wrapOrNotFound(ingredientOfDish);
    }

    /**
     * {@code DELETE  /ingredient-of-dishes/:id} : delete the "id" ingredientOfDish.
     *
     * @param id the id of the ingredientOfDish to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/ingredient-of-dishes/{id}")
    public ResponseEntity<Void> deleteIngredientOfDish(@PathVariable Long id) {
        log.debug("REST request to delete IngredientOfDish : {}", id);
        ingredientOfDishService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
