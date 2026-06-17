package com.edu.sena.zentry.service;

import com.edu.sena.zentry.service.dto.UsuarioConjuntoDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.edu.sena.zentry.domain.UsuarioConjunto}.
 */
public interface UsuarioConjuntoService {
    /**
     * Save a usuarioConjunto.
     *
     * @param usuarioConjuntoDTO the entity to save.
     * @return the persisted entity.
     */
    UsuarioConjuntoDTO save(UsuarioConjuntoDTO usuarioConjuntoDTO);

    /**
     * Updates a usuarioConjunto.
     *
     * @param usuarioConjuntoDTO the entity to update.
     * @return the persisted entity.
     */
    UsuarioConjuntoDTO update(UsuarioConjuntoDTO usuarioConjuntoDTO);

    /**
     * Partially updates a usuarioConjunto.
     *
     * @param usuarioConjuntoDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<UsuarioConjuntoDTO> partialUpdate(UsuarioConjuntoDTO usuarioConjuntoDTO);

    /**
     * Get all the usuarioConjuntos.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<UsuarioConjuntoDTO> findAll(Pageable pageable);

    /**
     * Get all the usuarioConjuntos with eager load of many-to-many relationships.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<UsuarioConjuntoDTO> findAllWithEagerRelationships(Pageable pageable);

    /**
     * Get the "id" usuarioConjunto.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<UsuarioConjuntoDTO> findOne(String id);

    /**
     * Delete the "id" usuarioConjunto.
     *
     * @param id the id of the entity.
     */
    void delete(String id);
}
