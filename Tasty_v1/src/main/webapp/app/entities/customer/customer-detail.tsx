import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import {} from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './customer.reducer';

export const CustomerDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const customerEntity = useAppSelector(state => state.customer.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="customerDetailsHeading">Customer</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{customerEntity.id}</dd>
          <dt>
            <span id="username">Username</span>
          </dt>
          <dd>{customerEntity.username}</dd>
          <dt>
            <span id="password">Password</span>
          </dt>
          <dd>{customerEntity.password}</dd>
          <dt>
            <span id="fullName">Full Name</span>
          </dt>
          <dd>{customerEntity.fullName}</dd>
          <dt>
            <span id="phoneNumber">Phone Number</span>
          </dt>
          <dd>{customerEntity.phoneNumber}</dd>
          <dt>
            <span id="email">Email</span>
          </dt>
          <dd>{customerEntity.email}</dd>
          <dt>
            <span id="status">Status</span>
          </dt>
          <dd>{customerEntity.status ? 'true' : 'false'}</dd>
          <dt>
            <span id="gender">Gender</span>
          </dt>
          <dd>{customerEntity.gender}</dd>
          <dt>
            <span id="confirmed">Confirmed</span>
          </dt>
          <dd>{customerEntity.confirmed ? 'true' : 'false'}</dd>
          <dt>Professtion</dt>
          <dd>{customerEntity.professtion ? customerEntity.professtion.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/customer" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/customer/${customerEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default CustomerDetail;
