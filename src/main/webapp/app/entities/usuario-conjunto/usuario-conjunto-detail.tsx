import React, { useEffect } from 'react';
import { Button, Col, Row } from 'react-bootstrap';
import { Link, useParams } from 'react-router';
import {} from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './usuario-conjunto.reducer';

export const UsuarioConjuntoDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const usuarioConjuntoEntity = useAppSelector(state => state.usuarioConjunto.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="usuarioConjuntoDetailsHeading">Usuario Conjunto</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{usuarioConjuntoEntity.id}</dd>
          <dt>User</dt>
          <dd>{usuarioConjuntoEntity.user ? usuarioConjuntoEntity.user.login : ''}</dd>
          <dt>Conjunto Residencial</dt>
          <dd>{usuarioConjuntoEntity.conjuntoResidencial ? usuarioConjuntoEntity.conjuntoResidencial.nombreConjunto : ''}</dd>
        </dl>
        <Button as={Link as any} to="/usuario-conjunto" replace variant="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Volver</span>
        </Button>
        &nbsp;
        <Button as={Link as any} to={`/usuario-conjunto/${usuarioConjuntoEntity.id}/edit`} replace variant="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Editar</span>
        </Button>
      </Col>
    </Row>
  );
};

export default UsuarioConjuntoDetail;
