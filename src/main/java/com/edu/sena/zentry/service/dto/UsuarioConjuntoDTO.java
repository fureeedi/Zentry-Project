package com.edu.sena.zentry.service.dto;

import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.edu.sena.zentry.domain.UsuarioConjunto} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class UsuarioConjuntoDTO implements Serializable {

    private String id;

    @NotNull
    private UserDTO user;

    @NotNull
    private ConjuntoResidencialDTO conjuntoResidencial;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public UserDTO getUser() {
        return user;
    }

    public void setUser(UserDTO user) {
        this.user = user;
    }

    public ConjuntoResidencialDTO getConjuntoResidencial() {
        return conjuntoResidencial;
    }

    public void setConjuntoResidencial(ConjuntoResidencialDTO conjuntoResidencial) {
        this.conjuntoResidencial = conjuntoResidencial;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof UsuarioConjuntoDTO)) {
            return false;
        }

        UsuarioConjuntoDTO usuarioConjuntoDTO = (UsuarioConjuntoDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, usuarioConjuntoDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "UsuarioConjuntoDTO{" +
            "id='" + getId() + "'" +
            ", user=" + getUser() +
            ", conjuntoResidencial=" + getConjuntoResidencial() +
            "}";
    }
}
