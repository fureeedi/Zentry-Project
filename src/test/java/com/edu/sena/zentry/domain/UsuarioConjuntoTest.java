package com.edu.sena.zentry.domain;

import static com.edu.sena.zentry.domain.ConjuntoResidencialTestSamples.*;
import static com.edu.sena.zentry.domain.UsuarioConjuntoTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.edu.sena.zentry.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class UsuarioConjuntoTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(UsuarioConjunto.class);
        UsuarioConjunto usuarioConjunto1 = getUsuarioConjuntoSample1();
        UsuarioConjunto usuarioConjunto2 = new UsuarioConjunto();
        assertThat(usuarioConjunto1).isNotEqualTo(usuarioConjunto2);

        usuarioConjunto2.setId(usuarioConjunto1.getId());
        assertThat(usuarioConjunto1).isEqualTo(usuarioConjunto2);

        usuarioConjunto2 = getUsuarioConjuntoSample2();
        assertThat(usuarioConjunto1).isNotEqualTo(usuarioConjunto2);
    }

    @Test
    void conjuntoResidencialTest() {
        UsuarioConjunto usuarioConjunto = getUsuarioConjuntoRandomSampleGenerator();
        ConjuntoResidencial conjuntoResidencialBack = getConjuntoResidencialRandomSampleGenerator();

        usuarioConjunto.setConjuntoResidencial(conjuntoResidencialBack);
        assertThat(usuarioConjunto.getConjuntoResidencial()).isEqualTo(conjuntoResidencialBack);

        usuarioConjunto.conjuntoResidencial(null);
        assertThat(usuarioConjunto.getConjuntoResidencial()).isNull();
    }
}
