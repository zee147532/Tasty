import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import IngredientType from './ingredient-type';
import IngredientTypeDetail from './ingredient-type-detail';
import IngredientTypeUpdate from './ingredient-type-update';
import IngredientTypeDeleteDialog from './ingredient-type-delete-dialog';

const IngredientTypeRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<IngredientType />} />
    <Route path="new" element={<IngredientTypeUpdate />} />
    <Route path=":id">
      <Route index element={<IngredientTypeDetail />} />
      <Route path="edit" element={<IngredientTypeUpdate />} />
      <Route path="delete" element={<IngredientTypeDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default IngredientTypeRoutes;
