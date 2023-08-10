import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import {} from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './type-of-dish.reducer';

export const TypeOfDishDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const typeOfDishEntity = useAppSelector(state => state.typeOfDish.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="typeOfDishDetailsHeading">Type Of Dish</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">Id</span>
          </dt>
          <dd>{typeOfDishEntity.id}</dd>
          <dt>Type</dt>
          <dd>{typeOfDishEntity.type ? typeOfDishEntity.type.id : ''}</dd>
          <dt>Post</dt>
          <dd>{typeOfDishEntity.post ? typeOfDishEntity.post.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/type-of-dish" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/type-of-dish/${typeOfDishEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default TypeOfDishDetail;
