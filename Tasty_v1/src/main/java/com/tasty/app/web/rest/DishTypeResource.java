package com.tasty.app.web.rest;

import com.tasty.app.domain.DishType;
import com.tasty.app.repository.DishTypeRepository;
import com.tasty.app.service.DishTypeService;
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
 * REST controller for managing {@link com.tasty.app.domain.DishType}.
 */
@RestController
@RequestMapping("/api")
public class DishTypeResource {

    private final Logger log = LoggerFactory.getLogger(DishTypeResource.class);

    private static final String ENTITY_NAME = "dishType";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final DishTypeService dishTypeService;

    private final DishTypeRepository dishTypeRepository;

    public DishTypeResource(DishTypeService dishTypeService, DishTypeRepository dishTypeRepository) {
        this.dishTypeService = dishTypeService;
        this.dishTypeRepository = dishTypeRepository;
    }

    /**
     * {@code POST  /dish-types} : Create a new dishType.
     *
     * @param dishType the dishType to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new dishType, or with status {@code 400 (Bad Request)} if the dishType has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/dish-types")
    public ResponseEntity<DishType> createDishType(@RequestBody DishType dishType) throws URISyntaxException {
        log.debug("REST request to save DishType : {}", dishType);
        if (dishType.getId() != null) {
            throw new BadRequestAlertException("A new dishType cannot already have an ID", ENTITY_NAME, "idexists");
        }
        DishType result = dishTypeService.save(dishType);
        return ResponseEntity
            .created(new URI("/api/dish-types/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /dish-types/:id} : Updates an existing dishType.
     *
     * @param id the id of the dishType to save.
     * @param dishType the dishType to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated dishType,
     * or with status {@code 400 (Bad Request)} if the dishType is not valid,
     * or with status {@code 500 (Internal Server Error)} if the dishType couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/dish-types/{id}")
    public ResponseEntity<DishType> updateDishType(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody DishType dishType
    ) throws URISyntaxException {
        log.debug("REST request to update DishType : {}, {}", id, dishType);
        if (dishType.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, dishType.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!dishTypeRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        DishType result = dishTypeService.update(dishType);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, dishType.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /dish-types/:id} : Partial updates given fields of an existing dishType, field will ignore if it is null
     *
     * @param id the id of the dishType to save.
     * @param dishType the dishType to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated dishType,
     * or with status {@code 400 (Bad Request)} if the dishType is not valid,
     * or with status {@code 404 (Not Found)} if the dishType is not found,
     * or with status {@code 500 (Internal Server Error)} if the dishType couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/dish-types/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<DishType> partialUpdateDishType(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody DishType dishType
    ) throws URISyntaxException {
        log.debug("REST request to partial update DishType partially : {}, {}", id, dishType);
        if (dishType.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, dishType.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!dishTypeRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<DishType> result = dishTypeService.partialUpdate(dishType);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, dishType.getId().toString())
        );
    }

    /**
     * {@code GET  /dish-types} : get all the dishTypes.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of dishTypes in body.
     */
    @GetMapping("/dish-types")
    public ResponseEntity<List<DishType>> getAllDishTypes(@org.springdoc.api.annotations.ParameterObject Pageable pageable) {
        log.debug("REST request to get a page of DishTypes");
        Page<DishType> page = dishTypeService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /dish-types/:id} : get the "id" dishType.
     *
     * @param id the id of the dishType to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the dishType, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/dish-types/{id}")
    public ResponseEntity<DishType> getDishType(@PathVariable Long id) {
        log.debug("REST request to get DishType : {}", id);
        Optional<DishType> dishType = dishTypeService.findOne(id);
        return ResponseUtil.wrapOrNotFound(dishType);
    }

    /**
     * {@code DELETE  /dish-types/:id} : delete the "id" dishType.
     *
     * @param id the id of the dishType to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/dish-types/{id}")
    public ResponseEntity<Void> deleteDishType(@PathVariable Long id) {
        log.debug("REST request to delete DishType : {}", id);
        dishTypeService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
