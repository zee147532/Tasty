import { IPost } from 'app/shared/model/post.model';
import { IIngredient } from 'app/shared/model/ingredient.model';

export interface IIngredientOfDish {
  id?: number;
  unit?: string | null;
  quantity?: number | null;
  post?: IPost | null;
  ingredient?: IIngredient | null;
}

export const defaultValue: Readonly<IIngredientOfDish> = {};
