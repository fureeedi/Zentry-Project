import dayjs from 'dayjs';

import { IConjuntoResidencial } from 'app/shared/model/conjunto-residencial.model';
import { IUser } from 'app/shared/model/user.model';

export interface IAnuncios {
  id?: string;
  titulo?: string;
  descripcion?: string;
  fecha?: dayjs.Dayjs;
  imagenContentType?: string | null;
  imagen?: string | null;
  conjuntoResidencial?: IConjuntoResidencial;
  user?: IUser;
}

export const defaultValue: Readonly<IAnuncios> = {};
