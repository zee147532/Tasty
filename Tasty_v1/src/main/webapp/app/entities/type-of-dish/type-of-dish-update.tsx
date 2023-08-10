import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IDishType } from 'app/shared/model/dish-type.model';
import { getEntities as getDishTypes } from 'app/entities/dish-type/dish-type.reducer';
import { IPost } from 'app/shared/model/post.model';
import { getEntities as getPosts } from 'app/entities/post/post.reducer';
import { ITypeOfDish } from 'app/shared/model/type-of-dish.model';
import { getEntity, updateEntity, createEntity, reset } from './type-of-dish.reducer';

export const TypeOfDishUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const dishTypes = useAppSelector(state => state.dishType.entities);
  const posts = useAppSelector(state => state.post.entities);
  const typeOfDishEntity = useAppSelector(state => state.typeOfDish.entity);
  const loading = useAppSelector(state => state.typeOfDish.loading);
  const updating = useAppSelector(state => state.typeOfDish.updating);
  const updateSuccess = useAppSelector(state => state.typeOfDish.updateSuccess);

  const handleClose = () => {
    navigate('/type-of-dish' + location.search);
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }

    dispatch(getDishTypes({}));
    dispatch(getPosts({}));
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    const entity = {
      ...typeOfDishEntity,
      ...values,
      type: dishTypes.find(it => it.id.toString() === values.type.toString()),
      post: posts.find(it => it.id.toString() === values.post.toString()),
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
          ...typeOfDishEntity,
          type: typeOfDishEntity?.type?.id,
          post: typeOfDishEntity?.post?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="tastyV1App.typeOfDish.home.createOrEditLabel" data-cy="TypeOfDishCreateUpdateHeading">
            Create or edit a Type Of Dish
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <ValidatedForm defaultValues={defaultValues()} onSubmit={saveEntity}>
              {!isNew ? <ValidatedField name="id" required readOnly id="type-of-dish-id" label="Id" validate={{ required: true }} /> : null}
              <ValidatedField id="type-of-dish-type" name="type" data-cy="type" label="Type" type="select">
                <option value="" key="0" />
                {dishTypes
                  ? dishTypes.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField id="type-of-dish-post" name="post" data-cy="post" label="Post" type="select">
                <option value="" key="0" />
                {posts
                  ? posts.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/type-of-dish" replace color="info">
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

export default TypeOfDishUpdate;
