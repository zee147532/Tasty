import { IPost } from 'app/shared/model/post.model';

export interface IEvaluation {
  id?: number;
  point?: number | null;
  comment?: string | null;
  post?: IPost | null;
}

export const defaultValue: Readonly<IEvaluation> = {};
