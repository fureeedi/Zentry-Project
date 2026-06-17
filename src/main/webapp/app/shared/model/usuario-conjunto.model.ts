import { IConjuntoResidencial } from 'app/shared/model/conjunto-residencial.model';
import { IUser } from 'app/shared/model/user.model';

export interface IUsuarioConjunto {
  id?: string;
  user?: IUser;
  conjuntoResidencial?: IConjuntoResidencial;
}

export const defaultValue: Readonly<IUsuarioConjunto> = {};
