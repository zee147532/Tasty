import admin from 'app/entities/admin/admin.reducer';
import comment from 'app/entities/comment/comment.reducer';
import customer from 'app/entities/customer/customer.reducer';
import evaluation from 'app/entities/evaluation/evaluation.reducer';
import favorites from 'app/entities/favorites/favorites.reducer';
import ingredient from 'app/entities/ingredient/ingredient.reducer';
import ingredientOfDish from 'app/entities/ingredient-of-dish/ingredient-of-dish.reducer';
import ingredientType from 'app/entities/ingredient-type/ingredient-type.reducer';
import post from 'app/entities/post/post.reducer';
import profession from 'app/entities/profession/profession.reducer';
import stepToCook from 'app/entities/step-to-cook/step-to-cook.reducer';
/* jhipster-needle-add-reducer-import - JHipster will add reducer here */

const entitiesReducers = {
  admin,
  comment,
  customer,
  evaluation,
  favorites,
  ingredient,
  ingredientOfDish,
  ingredientType,
  post,
  profession,
  stepToCook,
  /* jhipster-needle-add-reducer-combine - JHipster will add reducer here */
};

export default entitiesReducers;
