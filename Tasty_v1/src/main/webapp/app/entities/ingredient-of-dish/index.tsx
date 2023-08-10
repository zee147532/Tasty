import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import IngredientOfDish from './ingredient-of-dish';
import IngredientOfDishDetail from './ingredient-of-dish-detail';
import IngredientOfDishUpdate from './ingredient-of-dish-update';
import IngredientOfDishDeleteDialog from './ingredient-of-dish-delete-dialog';

const IngredientOfDishRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<IngredientOfDish />} />
    <Route path="new" element={<IngredientOfDishUpdate />} />
    <Route path=":id">
      <Route index element={<IngredientOfDishDetail />} />
      <Route path="edit" element={<IngredientOfDishUpdate />} />
      <Route path="delete" element={<IngredientOfDishDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default IngredientOfDishRoutes;
