import { IPost } from 'app/shared/model/post.model';

export interface IStepToCook {
  id?: number;
  content?: string | null;
  index?: number | null;
  post?: IPost | null;
}

export const defaultValue: Readonly<IStepToCook> = {};
