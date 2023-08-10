import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IPost } from 'app/shared/model/post.model';
import { getEntities as getPosts } from 'app/entities/post/post.reducer';
import { IIngredient } from 'app/shared/model/ingredient.model';
import { getEntities as getIngredients } from 'app/entities/ingredient/ingredient.reducer';
import { IIngredientOfDish } from 'app/shared/model/ingredient-of-dish.model';
import { getEntity, updateEntity, createEntity, reset } from './ingredient-of-dish.reducer';

export const IngredientOfDishUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const posts = useAppSelector(state => state.post.entities);
  const ingredients = useAppSelector(state => state.ingredient.entities);
  const ingredientOfDishEntity = useAppSelector(state => state.ingredientOfDish.entity);
  const loading = useAppSelector(state => state.ingredientOfDish.loading);
  const updating = useAppSelector(state => state.ingredientOfDish.updating);
  const updateSuccess = useAppSelector(state => state.ingredientOfDish.updateSuccess);

  const handleClose = () => {
    navigate('/ingredient-of-dish' + location.search);
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }

    dispatch(getPosts({}));
    dispatch(getIngredients({}));
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    const entity = {
      ...ingredientOfDishEntity,
      ...values,
      post: posts.find(it => it.id.toString() === values.post.toString()),
      ingredient: ingredients.find(it => it.id.toString() === values.ingredient.toString()),
    };

    if (isNew) {
      dispatch(createEntity(entity));
    } else {
      dispatch(updateEntity(entity));
    }
  };

  const defaultValues = () =>
    isNew
      ? {}
      : {
          ...ingredientOfDishEntity,
          post: ingredientOfDishEntity?.post?.id,
          ingredient: ingredientOfDishEntity?.ingredient?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="tastyV1App.ingredientOfDish.home.createOrEditLabel" data-cy="IngredientOfDishCreateUpdateHeading">
            Create or edit a Ingredient Of Dish
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <ValidatedForm defaultValues={defaultValues()} onSubmit={saveEntity}>
              {!isNew ? (
                <ValidatedField name="id" required readOnly id="ingredient-of-dish-id" label="Id" validate={{ required: true }} />
              ) : null}
              <ValidatedField label="Unit" id="ingredient-of-dish-unit" name="unit" data-cy="unit" type="text" />
              <ValidatedField label="Quantity" id="ingredient-of-dish-quantity" name="quantity" data-cy="quantity" type="text" />
              <ValidatedField id="ingredient-of-dish-post" name="post" data-cy="post" label="Post" type="select">
                <option value="" key="0" />
                {posts
                  ? posts.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField id="ingredient-of-dish-ingredient" name="ingredient" data-cy="ingredient" label="Ingredient" type="select">
                <option value="" key="0" />
                {ingredients
                  ? ingredients.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/ingredient-of-dish" replace color="info">
                <FontAwesomeIcon icon="arrow-left" />
                &nbsp;
                <span className="d-none d-md-inline">Back</span>
              </Button>
              &nbsp;
              <Button color="primary" id="save-entity" data-cy="entityCreateSaveButton" type="submit" disabled={updating}>
                <FontAwesomeIcon icon="save" />
                &nbsp; Save
              </Button>
            </ValidatedForm>
          )}
        </Col>
      </Row>
    </div>
  );
};

export default IngredientOfDishUpdate;
