import { ICustomer } from 'app/shared/model/customer.model';
import { IPost } from 'app/shared/model/post.model';

export interface IFavorites {
  id?: number;
  customer?: ICustomer | null;
  post?: IPost | null;
}

export const defaultValue: Readonly<IFavorites> = {};
