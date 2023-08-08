import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IIngredientType } from 'app/shared/model/ingredient-type.model';
import { getEntities as getIngredientTypes } from 'app/entities/ingredient-type/ingredient-type.reducer';
import { IIngredient } from 'app/shared/model/ingredient.model';
import { getEntity, updateEntity, createEntity, reset } from './ingredient.reducer';

export const IngredientUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const ingredientTypes = useAppSelector(state => state.ingredientType.entities);
  const ingredientEntity = useAppSelector(state => state.ingredient.entity);
  const loading = useAppSelector(state => state.ingredient.loading);
  const updating = useAppSelector(state => state.ingredient.updating);
  const updateSuccess = useAppSelector(state => state.ingredient.updateSuccess);

  const handleClose = () => {
    navigate('/ingredient' + location.search);
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }

    dispatch(getIngredientTypes({}));
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    const entity = {
      ...ingredientEntity,
      ...values,
      type: ingredientTypes.find(it => it.id.toString() === values.type.toString()),
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
          ...ingredientEntity,
          type: ingredientEntity?.type?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="tastyV1App.ingredient.home.createOrEditLabel" data-cy="IngredientCreateUpdateHeading">
            Create or edit a Ingredient
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <ValidatedForm defaultValues={defaultValues()} onSubmit={saveEntity}>
              {!isNew ? <ValidatedField name="id" required readOnly id="ingredient-id" label="Id" validate={{ required: true }} /> : null}
              <ValidatedField label="Name" id="ingredient-name" name="name" data-cy="name" type="text" />
              <ValidatedField label="Other Name" id="ingredient-otherName" name="otherName" data-cy="otherName" type="text" />
              <ValidatedField id="ingredient-type" name="type" data-cy="type" label="Type" type="select">
                <option value="" key="0" />
                {ingredientTypes
                  ? ingredientTypes.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/ingredient" replace color="info">
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

export default IngredientUpdate;
