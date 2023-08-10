import { IDishType } from 'app/shared/model/dish-type.model';
import { IPost } from 'app/shared/model/post.model';

export interface ITypeOfDish {
  id?: number;
  type?: IDishType | null;
  post?: IPost | null;
}

export const defaultValue: Readonly<ITypeOfDish> = {};
