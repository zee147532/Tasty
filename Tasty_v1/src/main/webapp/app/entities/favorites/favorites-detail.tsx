import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import {} from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './favorites.reducer';

export const FavoritesDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const favoritesEntity = useAppSelector(state => state.favorites.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="favoritesDetailsHeading">Favorites</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">Id</span>
          </dt>
          <dd>{favoritesEntity.id}</dd>
          <dt>Customer</dt>
          <dd>{favoritesEntity.customer ? favoritesEntity.customer.id : ''}</dd>
          <dt>Post</dt>
          <dd>{favoritesEntity.post ? favoritesEntity.post.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/favorites" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/favorites/${favoritesEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default FavoritesDetail;
