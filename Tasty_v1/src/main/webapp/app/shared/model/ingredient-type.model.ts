import { IIngredient } from 'app/shared/model/ingredient.model';

export interface IIngredientType {
  id?: number;
  name?: string | null;
  ingredients?: IIngredient[] | null;
}

export const defaultValue: Readonly<IIngredientType> = {};
