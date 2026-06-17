import React, { useEffect } from 'react';
import { Button, Col, FormText, Row } from 'react-bootstrap';
import { ValidatedField, ValidatedForm } from 'react-jhipster';
import { Link, useNavigate, useParams } from 'react-router';

import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';
import { getEntities as getConjuntoResidencials } from 'app/entities/conjunto-residencial/conjunto-residencial.reducer';
import { getUsers } from 'app/modules/administration/user-management/user-management.reducer';

import { createEntity, getEntity, reset, updateEntity } from './usuario-conjunto.reducer';

export const UsuarioConjuntoUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const users = useAppSelector(state => state.userManagement.users);
  const conjuntoResidencials = useAppSelector(state => state.conjuntoResidencial.entities);
  const usuarioConjuntoEntity = useAppSelector(state => state.usuarioConjunto.entity);
  const loading = useAppSelector(state => state.usuarioConjunto.loading);
  const updating = useAppSelector(state => state.usuarioConjunto.updating);
  const updateSuccess = useAppSelector(state => state.usuarioConjunto.updateSuccess);

  const handleClose = () => {
    navigate(`/usuario-conjunto${location.search}`);
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }

    dispatch(getUsers({}));
    dispatch(getConjuntoResidencials({}));
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    const entity = {
      ...usuarioConjuntoEntity,
      ...values,
      user: users.find(it => it.id.toString() === values.user?.toString()),
      conjuntoResidencial: conjuntoResidencials.find(it => it.id.toString() === values.conjuntoResidencial?.toString()),
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
          ...usuarioConjuntoEntity,
          user: usuarioConjuntoEntity?.user?.id,
          conjuntoResidencial: usuarioConjuntoEntity?.conjuntoResidencial?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="zentryApp.usuarioConjunto.home.createOrEditLabel" data-cy="UsuarioConjuntoCreateUpdateHeading">
            Crear o editar Usuario Conjunto
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <ValidatedForm defaultValues={defaultValues()} onSubmit={saveEntity}>
              {!isNew && <ValidatedField name="id" required readOnly id="usuario-conjunto-id" label="ID" validate={{ required: true }} />}
              <ValidatedField id="usuario-conjunto-user" name="user" data-cy="user" label="User" type="select" required>
                <option value="" key="0" />
                {users
                  ? users.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.login}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <FormText>Este campo es obligatorio.</FormText>
              <ValidatedField
                id="usuario-conjunto-conjuntoResidencial"
                name="conjuntoResidencial"
                data-cy="conjuntoResidencial"
                label="Conjunto Residencial"
                type="select"
                required
              >
                <option value="" key="0" />
                {conjuntoResidencials
                  ? conjuntoResidencials.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.nombreConjunto}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <FormText>Este campo es obligatorio.</FormText>
              <Button as={Link as any} id="cancel-save" data-cy="entityCreateCancelButton" to="/usuario-conjunto" replace variant="info">
                <FontAwesomeIcon icon="arrow-left" />
                &nbsp;
                <span className="d-none d-md-inline">Volver</span>
              </Button>
              &nbsp;
              <Button variant="primary" id="save-entity" data-cy="entityCreateSaveButton" type="submit" disabled={updating}>
                <FontAwesomeIcon icon="save" />
                &nbsp; Guardar
              </Button>
            </ValidatedForm>
          )}
        </Col>
      </Row>
    </div>
  );
};

export default UsuarioConjuntoUpdate;
