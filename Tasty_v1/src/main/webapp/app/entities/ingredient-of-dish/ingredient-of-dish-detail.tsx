import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import {} from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './ingredient-of-dish.reducer';

export const IngredientOfDishDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const ingredientOfDishEntity = useAppSelector(state => state.ingredientOfDish.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="ingredientOfDishDetailsHeading">Ingredient Of Dish</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">Id</span>
          </dt>
          <dd>{ingredientOfDishEntity.id}</dd>
          <dt>
            <span id="unit">Unit</span>
          </dt>
          <dd>{ingredientOfDishEntity.unit}</dd>
          <dt>
            <span id="quantity">Quantity</span>
          </dt>
          <dd>{ingredientOfDishEntity.quantity}</dd>
          <dt>Post</dt>
          <dd>{ingredientOfDishEntity.post ? ingredientOfDishEntity.post.id : ''}</dd>
          <dt>Ingredient</dt>
          <dd>{ingredientOfDishEntity.ingredient ? ingredientOfDishEntity.ingredient.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/ingredient-of-dish" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/ingredient-of-dish/${ingredientOfDishEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default IngredientOfDishDetail;
