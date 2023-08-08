import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Profession from './profession';
import ProfessionDetail from './profession-detail';
import ProfessionUpdate from './profession-update';
import ProfessionDeleteDialog from './profession-delete-dialog';

const ProfessionRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Profession />} />
    <Route path="new" element={<ProfessionUpdate />} />
    <Route path=":id">
      <Route index element={<ProfessionDetail />} />
      <Route path="edit" element={<ProfessionUpdate />} />
      <Route path="delete" element={<ProfessionDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default ProfessionRoutes;
