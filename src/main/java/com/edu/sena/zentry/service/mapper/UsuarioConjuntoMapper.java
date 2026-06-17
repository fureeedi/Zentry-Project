package com.edu.sena.zentry.service.mapper;

import com.edu.sena.zentry.domain.ConjuntoResidencial;
import com.edu.sena.zentry.domain.User;
import com.edu.sena.zentry.domain.UsuarioConjunto;
import com.edu.sena.zentry.service.dto.ConjuntoResidencialDTO;
import com.edu.sena.zentry.service.dto.UserDTO;
import com.edu.sena.zentry.service.dto.UsuarioConjuntoDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link UsuarioConjunto} and its DTO {@link UsuarioConjuntoDTO}.
 */
@Mapper(componentModel = "spring")
public interface UsuarioConjuntoMapper extends EntityMapper<UsuarioConjuntoDTO, UsuarioConjunto> {
    @Mapping(target = "user", source = "user", qualifiedByName = "userLogin")
    @Mapping(target = "conjuntoResidencial", source = "conjuntoResidencial", qualifiedByName = "conjuntoResidencialNombreConjunto")
    UsuarioConjuntoDTO toDto(UsuarioConjunto s);

    @Named("userLogin")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "login", source = "login")
    UserDTO toDtoUserLogin(User user);

    @Named("conjuntoResidencialNombreConjunto")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "nombreConjunto", source = "nombreConjunto")
    ConjuntoResidencialDTO toDtoConjuntoResidencialNombreConjunto(ConjuntoResidencial conjuntoResidencial);
}
