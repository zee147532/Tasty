package com.tasty.app.web.rest;

import com.tasty.app.domain.TypeOfDish;
import com.tasty.app.repository.TypeOfDishRepository;
import com.tasty.app.service.TypeOfDishService;
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
 * REST controller for managing {@link com.tasty.app.domain.TypeOfDish}.
 */
@RestController
@RequestMapping("/api")
public class TypeOfDishResource {

    private final Logger log = LoggerFactory.getLogger(TypeOfDishResource.class);

    private static final String ENTITY_NAME = "typeOfDish";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TypeOfDishService typeOfDishService;

    private final TypeOfDishRepository typeOfDishRepository;

    public TypeOfDishResource(TypeOfDishService typeOfDishService, TypeOfDishRepository typeOfDishRepository) {
        this.typeOfDishService = typeOfDishService;
        this.typeOfDishRepository = typeOfDishRepository;
    }

    /**
     * {@code POST  /type-of-dishes} : Create a new typeOfDish.
     *
     * @param typeOfDish the typeOfDish to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new typeOfDish, or with status {@code 400 (Bad Request)} if the typeOfDish has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/type-of-dishes")
    public ResponseEntity<TypeOfDish> createTypeOfDish(@RequestBody TypeOfDish typeOfDish) throws URISyntaxException {
        log.debug("REST request to save TypeOfDish : {}", typeOfDish);
        if (typeOfDish.getId() != null) {
            throw new BadRequestAlertException("A new typeOfDish cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TypeOfDish result = typeOfDishService.save(typeOfDish);
        return ResponseEntity
            .created(new URI("/api/type-of-dishes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /type-of-dishes/:id} : Updates an existing typeOfDish.
     *
     * @param id the id of the typeOfDish to save.
     * @param typeOfDish the typeOfDish to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated typeOfDish,
     * or with status {@code 400 (Bad Request)} if the typeOfDish is not valid,
     * or with status {@code 500 (Internal Server Error)} if the typeOfDish couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/type-of-dishes/{id}")
    public ResponseEntity<TypeOfDish> updateTypeOfDish(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody TypeOfDish typeOfDish
    ) throws URISyntaxException {
        log.debug("REST request to update TypeOfDish : {}, {}", id, typeOfDish);
        if (typeOfDish.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, typeOfDish.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!typeOfDishRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        TypeOfDish result = typeOfDishService.update(typeOfDish);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, typeOfDish.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /type-of-dishes/:id} : Partial updates given fields of an existing typeOfDish, field will ignore if it is null
     *
     * @param id the id of the typeOfDish to save.
     * @param typeOfDish the typeOfDish to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated typeOfDish,
     * or with status {@code 400 (Bad Request)} if the typeOfDish is not valid,
     * or with status {@code 404 (Not Found)} if the typeOfDish is not found,
     * or with status {@code 500 (Internal Server Error)} if the typeOfDish couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/type-of-dishes/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<TypeOfDish> partialUpdateTypeOfDish(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody TypeOfDish typeOfDish
    ) throws URISyntaxException {
        log.debug("REST request to partial update TypeOfDish partially : {}, {}", id, typeOfDish);
        if (typeOfDish.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, typeOfDish.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!typeOfDishRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<TypeOfDish> result = typeOfDishService.partialUpdate(typeOfDish);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, typeOfDish.getId().toString())
        );
    }

    /**
     * {@code GET  /type-of-dishes} : get all the typeOfDishes.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of typeOfDishes in body.
     */
    @GetMapping("/type-of-dishes")
    public ResponseEntity<List<TypeOfDish>> getAllTypeOfDishes(@org.springdoc.api.annotations.ParameterObject Pageable pageable) {
        log.debug("REST request to get a page of TypeOfDishes");
        Page<TypeOfDish> page = typeOfDishService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /type-of-dishes/:id} : get the "id" typeOfDish.
     *
     * @param id the id of the typeOfDish to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the typeOfDish, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/type-of-dishes/{id}")
    public ResponseEntity<TypeOfDish> getTypeOfDish(@PathVariable Long id) {
        log.debug("REST request to get TypeOfDish : {}", id);
        Optional<TypeOfDish> typeOfDish = typeOfDishService.findOne(id);
        return ResponseUtil.wrapOrNotFound(typeOfDish);
    }

    /**
     * {@code DELETE  /type-of-dishes/:id} : delete the "id" typeOfDish.
     *
     * @param id the id of the typeOfDish to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/type-of-dishes/{id}")
    public ResponseEntity<Void> deleteTypeOfDish(@PathVariable Long id) {
        log.debug("REST request to delete TypeOfDish : {}", id);
        typeOfDishService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
