import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import {} from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './step-to-cook.reducer';

export const StepToCookDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const stepToCookEntity = useAppSelector(state => state.stepToCook.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="stepToCookDetailsHeading">Step To Cook</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">Id</span>
          </dt>
          <dd>{stepToCookEntity.id}</dd>
          <dt>
            <span id="content">Content</span>
          </dt>
          <dd>{stepToCookEntity.content}</dd>
          <dt>
            <span id="index">Index</span>
          </dt>
          <dd>{stepToCookEntity.index}</dd>
          <dt>Post</dt>
          <dd>{stepToCookEntity.post ? stepToCookEntity.post.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/step-to-cook" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/step-to-cook/${stepToCookEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default StepToCookDetail;
