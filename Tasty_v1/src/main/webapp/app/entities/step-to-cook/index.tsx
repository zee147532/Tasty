import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import StepToCook from './step-to-cook';
import StepToCookDetail from './step-to-cook-detail';
import StepToCookUpdate from './step-to-cook-update';
import StepToCookDeleteDialog from './step-to-cook-delete-dialog';

const StepToCookRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<StepToCook />} />
    <Route path="new" element={<StepToCookUpdate />} />
    <Route path=":id">
      <Route index element={<StepToCookDetail />} />
      <Route path="edit" element={<StepToCookUpdate />} />
      <Route path="delete" element={<StepToCookDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default StepToCookRoutes;
