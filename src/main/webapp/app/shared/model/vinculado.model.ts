import { ITipoDocumento } from 'app/shared/model/tipo-documento.model';
import { IUser } from 'app/shared/model/user.model';

export interface IVinculado {
  id?: string;
  nombres?: string;
  apellidos?: string;
  numeroDocumento?: string;
  telefono?: string | null;
  activo?: boolean;
  user?: IUser;
  tipoDocumento?: ITipoDocumento;
}

export const defaultValue: Readonly<IVinculado> = {
  activo: false,
};
