import { ITypeOfDish } from 'app/shared/model/type-of-dish.model';

export interface IDishType {
  id?: number;
  name?: string | null;
  typeOfDishes?: ITypeOfDish[] | null;
}

export const defaultValue: Readonly<IDishType> = {};
