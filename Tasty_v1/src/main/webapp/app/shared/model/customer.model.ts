import { IFavorites } from 'app/shared/model/favorites.model';
import { IPost } from 'app/shared/model/post.model';
import { IProfession } from 'app/shared/model/profession.model';
import { Gender } from 'app/shared/model/enumerations/gender.model';

export interface ICustomer {
  id?: number;
  username?: string | null;
  password?: string | null;
  fullName?: string | null;
  phoneNumber?: string | null;
  email?: string | null;
  status?: boolean | null;
  gender?: Gender | null;
  confirmed?: boolean | null;
  favorites?: IFavorites[] | null;
  posts?: IPost[] | null;
  professtion?: IProfession | null;
}

export const defaultValue: Readonly<ICustomer> = {
  status: false,
  confirmed: false,
};
