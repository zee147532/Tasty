export interface IAdmin {
  id?: number;
  username?: string | null;
  passord?: string | null;
}

export const defaultValue: Readonly<IAdmin> = {};
