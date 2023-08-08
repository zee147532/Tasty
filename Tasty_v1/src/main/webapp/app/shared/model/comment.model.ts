import { IPost } from 'app/shared/model/post.model';

export interface IComment {
  id?: number;
  isSubComment?: boolean | null;
  comment?: string | null;
  post?: IPost | null;
}

export const defaultValue: Readonly<IComment> = {
  isSubComment: false,
};
