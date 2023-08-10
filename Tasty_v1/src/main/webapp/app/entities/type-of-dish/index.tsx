import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import TypeOfDish from './type-of-dish';
import TypeOfDishDetail from './type-of-dish-detail';
import TypeOfDishUpdate from './type-of-dish-update';
import TypeOfDishDeleteDialog from './type-of-dish-delete-dialog';

const TypeOfDishRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<TypeOfDish />} />
    <Route path="new" element={<TypeOfDishUpdate />} />
    <Route path=":id">
      <Route index element={<TypeOfDishDetail />} />
      <Route path="edit" element={<TypeOfDishUpdate />} />
      <Route path="delete" element={<TypeOfDishDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default TypeOfDishRoutes;
