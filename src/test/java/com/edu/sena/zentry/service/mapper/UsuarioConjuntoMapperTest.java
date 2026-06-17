package com.edu.sena.zentry.service.mapper;

import static com.edu.sena.zentry.domain.UsuarioConjuntoAsserts.*;
import static com.edu.sena.zentry.domain.UsuarioConjuntoTestSamples.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class UsuarioConjuntoMapperTest {

    private UsuarioConjuntoMapper usuarioConjuntoMapper;

    @BeforeEach
    void setUp() {
        usuarioConjuntoMapper = new UsuarioConjuntoMapperImpl();
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getUsuarioConjuntoSample1();
        var actual = usuarioConjuntoMapper.toEntity(usuarioConjuntoMapper.toDto(expected));
        assertUsuarioConjuntoAllPropertiesEquals(expected, actual);
    }
}
