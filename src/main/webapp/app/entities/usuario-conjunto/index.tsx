import React from 'react';
import { Route } from 'react-router';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import UsuarioConjunto from './usuario-conjunto';
import UsuarioConjuntoDeleteDialog from './usuario-conjunto-delete-dialog';
import UsuarioConjuntoDetail from './usuario-conjunto-detail';
import UsuarioConjuntoUpdate from './usuario-conjunto-update';

const UsuarioConjuntoRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<UsuarioConjunto />} />
    <Route path="new" element={<UsuarioConjuntoUpdate />} />
    <Route path=":id">
      <Route index element={<UsuarioConjuntoDetail />} />
      <Route path="edit" element={<UsuarioConjuntoUpdate />} />
      <Route path="delete" element={<UsuarioConjuntoDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default UsuarioConjuntoRoutes;
