import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IProfession } from 'app/shared/model/profession.model';
import { getEntities as getProfessions } from 'app/entities/profession/profession.reducer';
import { ICustomer } from 'app/shared/model/customer.model';
import { Gender } from 'app/shared/model/enumerations/gender.model';
import { getEntity, updateEntity, createEntity, reset } from './customer.reducer';

export const CustomerUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const professions = useAppSelector(state => state.profession.entities);
  const customerEntity = useAppSelector(state => state.customer.entity);
  const loading = useAppSelector(state => state.customer.loading);
  const updating = useAppSelector(state => state.customer.updating);
  const updateSuccess = useAppSelector(state => state.customer.updateSuccess);
  const genderValues = Object.keys(Gender);

  const handleClose = () => {
    navigate('/customer' + location.search);
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }

    dispatch(getProfessions({}));
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    const entity = {
      ...customerEntity,
      ...values,
      professtion: professions.find(it => it.id.toString() === values.professtion.toString()),
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
          gender: 'NAM',
          ...customerEntity,
          professtion: customerEntity?.professtion?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="tastyV1App.customer.home.createOrEditLabel" data-cy="CustomerCreateUpdateHeading">
            Create or edit a Customer
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <ValidatedForm defaultValues={defaultValues()} onSubmit={saveEntity}>
              {!isNew ? <ValidatedField name="id" required readOnly id="customer-id" label="ID" validate={{ required: true }} /> : null}
              <ValidatedField label="Username" id="customer-username" name="username" data-cy="username" type="text" />
              <ValidatedField label="Password" id="customer-password" name="password" data-cy="password" type="text" />
              <ValidatedField label="Full Name" id="customer-fullName" name="fullName" data-cy="fullName" type="text" />
              <ValidatedField label="Phone Number" id="customer-phoneNumber" name="phoneNumber" data-cy="phoneNumber" type="text" />
              <ValidatedField label="Email" id="customer-email" name="email" data-cy="email" type="text" />
              <ValidatedField label="Status" id="customer-status" name="status" data-cy="status" check type="checkbox" />
              <ValidatedField label="Gender" id="customer-gender" name="gender" data-cy="gender" type="select">
                {genderValues.map(gender => (
                  <option value={gender} key={gender}>
                    {gender}
                  </option>
                ))}
              </ValidatedField>
              <ValidatedField id="customer-professtion" name="professtion" data-cy="professtion" label="Professtion" type="select">
                <option value="" key="0" />
                {professions
                  ? professions.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/customer" replace color="info">
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

export default CustomerUpdate;
