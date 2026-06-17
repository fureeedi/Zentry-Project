package com.edu.sena.zentry.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.edu.sena.zentry.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class UsuarioConjuntoDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(UsuarioConjuntoDTO.class);
        UsuarioConjuntoDTO usuarioConjuntoDTO1 = new UsuarioConjuntoDTO();
        usuarioConjuntoDTO1.setId("id1");
        UsuarioConjuntoDTO usuarioConjuntoDTO2 = new UsuarioConjuntoDTO();
        assertThat(usuarioConjuntoDTO1).isNotEqualTo(usuarioConjuntoDTO2);
        usuarioConjuntoDTO2.setId(usuarioConjuntoDTO1.getId());
        assertThat(usuarioConjuntoDTO1).isEqualTo(usuarioConjuntoDTO2);
        usuarioConjuntoDTO2.setId("id2");
        assertThat(usuarioConjuntoDTO1).isNotEqualTo(usuarioConjuntoDTO2);
        usuarioConjuntoDTO1.setId(null);
        assertThat(usuarioConjuntoDTO1).isNotEqualTo(usuarioConjuntoDTO2);
    }
}
