package com.edu.sena.zentry.service.mapper;

import com.edu.sena.zentry.domain.Anuncios;
import com.edu.sena.zentry.domain.ConjuntoResidencial;
import com.edu.sena.zentry.domain.User;
import com.edu.sena.zentry.service.dto.AnunciosDTO;
import com.edu.sena.zentry.service.dto.ConjuntoResidencialDTO;
import com.edu.sena.zentry.service.dto.UserDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Anuncios} and its DTO {@link AnunciosDTO}.
 */
@Mapper(componentModel = "spring")
public interface AnunciosMapper extends EntityMapper<AnunciosDTO, Anuncios> {
    @Mapping(target = "conjuntoResidencial", source = "conjuntoResidencial", qualifiedByName = "conjuntoResidencialNombreConjunto")
    @Mapping(target = "user", source = "user", qualifiedByName = "userLogin")
    AnunciosDTO toDto(Anuncios s);

    @Named("conjuntoResidencialNombreConjunto")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "nombreConjunto", source = "nombreConjunto")
    ConjuntoResidencialDTO toDtoConjuntoResidencialNombreConjunto(ConjuntoResidencial conjuntoResidencial);

    @Named("userLogin")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "login", source = "login")
    UserDTO toDtoUserLogin(User user);
}
