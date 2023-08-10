import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import DishType from './dish-type';
import DishTypeDetail from './dish-type-detail';
import DishTypeUpdate from './dish-type-update';
import DishTypeDeleteDialog from './dish-type-delete-dialog';

const DishTypeRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<DishType />} />
    <Route path="new" element={<DishTypeUpdate />} />
    <Route path=":id">
      <Route index element={<DishTypeDetail />} />
      <Route path="edit" element={<DishTypeUpdate />} />
      <Route path="delete" element={<DishTypeDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default DishTypeRoutes;
