package com.edu.sena.zentry.domain;

import java.util.UUID;

public class UsuarioConjuntoTestSamples {

    public static UsuarioConjunto getUsuarioConjuntoSample1() {
        return new UsuarioConjunto().id("id1");
    }

    public static UsuarioConjunto getUsuarioConjuntoSample2() {
        return new UsuarioConjunto().id("id2");
    }

    public static UsuarioConjunto getUsuarioConjuntoRandomSampleGenerator() {
        return new UsuarioConjunto().id(UUID.randomUUID().toString());
    }
}
