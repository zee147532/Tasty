import { ICustomer } from 'app/shared/model/customer.model';

export interface IProfession {
  id?: number;
  name?: string | null;
  customers?: ICustomer[] | null;
}

export const defaultValue: Readonly<IProfession> = {};
