import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import {} from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './evaluation.reducer';

export const EvaluationDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const evaluationEntity = useAppSelector(state => state.evaluation.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="evaluationDetailsHeading">Evaluation</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">Id</span>
          </dt>
          <dd>{evaluationEntity.id}</dd>
          <dt>
            <span id="point">Point</span>
          </dt>
          <dd>{evaluationEntity.point}</dd>
          <dt>
            <span id="comment">Comment</span>
          </dt>
          <dd>{evaluationEntity.comment}</dd>
          <dt>Post</dt>
          <dd>{evaluationEntity.post ? evaluationEntity.post.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/evaluation" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/evaluation/${evaluationEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default EvaluationDetail;
