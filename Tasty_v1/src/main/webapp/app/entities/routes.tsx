import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Admin from './admin';
import Comment from './comment';
import Customer from './customer';
import Evaluation from './evaluation';
import Favorites from './favorites';
import Ingredient from './ingredient';
import IngredientOfDish from './ingredient-of-dish';
import IngredientType from './ingredient-type';
import Post from './post';
import Profession from './profession';
import StepToCook from './step-to-cook';
import DishType from './dish-type';
import TypeOfDish from './type-of-dish';
/* jhipster-needle-add-route-import - JHipster will add routes here */

export default () => {
  return (
    <div>
      <ErrorBoundaryRoutes>
        {/* prettier-ignore */}
        <Route path="admin/*" element={<Admin />} />
        <Route path="comment/*" element={<Comment />} />
        <Route path="customer/*" element={<Customer />} />
        <Route path="evaluation/*" element={<Evaluation />} />
        <Route path="favorites/*" element={<Favorites />} />
        <Route path="ingredient/*" element={<Ingredient />} />
        <Route path="ingredient-of-dish/*" element={<IngredientOfDish />} />
        <Route path="ingredient-type/*" element={<IngredientType />} />
        <Route path="post/*" element={<Post />} />
        <Route path="profession/*" element={<Profession />} />
        <Route path="step-to-cook/*" element={<StepToCook />} />
        <Route path="dish-type/*" element={<DishType />} />
        <Route path="type-of-dish/*" element={<TypeOfDish />} />
        {/* jhipster-needle-add-route-path - JHipster will add routes here */}
      </ErrorBoundaryRoutes>
    </div>
  );
};
