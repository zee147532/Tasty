import dayjs from 'dayjs';
import { IFavorites } from 'app/shared/model/favorites.model';
import { IStepToCook } from 'app/shared/model/step-to-cook.model';
import { IComment } from 'app/shared/model/comment.model';
import { IEvaluation } from 'app/shared/model/evaluation.model';
import { IIngredientOfDish } from 'app/shared/model/ingredient-of-dish.model';
import { ICustomer } from 'app/shared/model/customer.model';

export interface IPost {
  id?: number;
  title?: string | null;
  content?: string | null;
  description?: string | null;
  status?: boolean | null;
  createdDate?: string | null;
  favorites?: IFavorites[] | null;
  stepToCooks?: IStepToCook[] | null;
  comments?: IComment[] | null;
  evaluations?: IEvaluation[] | null;
  ingredientOfDishes?: IIngredientOfDish[] | null;
  posts?: IPost[] | null;
  author?: ICustomer | null;
  supperComment?: IPost | null;
}

export const defaultValue: Readonly<IPost> = {
  status: false,
};
