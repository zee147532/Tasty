import { IIngredientOfDish } from 'app/shared/model/ingredient-of-dish.model';
import { IIngredientType } from 'app/shared/model/ingredient-type.model';

export interface IIngredient {
  id?: number;
  name?: string | null;
  otherName?: string | null;
  ingredientOfDishes?: IIngredientOfDish[] | null;
  type?: IIngredientType | null;
}

export const defaultValue: Readonly<IIngredient> = {};
