package com.edu.sena.zentry.web.rest;

import com.edu.sena.zentry.repository.UsuarioConjuntoRepository;
import com.edu.sena.zentry.security.AuthoritiesConstants;
import com.edu.sena.zentry.service.UsuarioConjuntoService;
import com.edu.sena.zentry.service.dto.UsuarioConjuntoDTO;
import com.edu.sena.zentry.web.rest.errors.BadRequestAlertException;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.edu.sena.zentry.domain.UsuarioConjunto}.
 */
@RestController
@RequestMapping("/api/usuario-conjuntos")
public class UsuarioConjuntoResource {

    private static final Logger LOG = LoggerFactory.getLogger(UsuarioConjuntoResource.class);

    private static final String ENTITY_NAME = "usuarioConjunto";

    @Value("${jhipster.clientApp.name:zentry}")
    private String applicationName;

    private final UsuarioConjuntoService usuarioConjuntoService;

    private final UsuarioConjuntoRepository usuarioConjuntoRepository;

    public UsuarioConjuntoResource(UsuarioConjuntoService usuarioConjuntoService, UsuarioConjuntoRepository usuarioConjuntoRepository) {
        this.usuarioConjuntoService = usuarioConjuntoService;
        this.usuarioConjuntoRepository = usuarioConjuntoRepository;
    }

    /**
     * {@code POST  /usuario-conjuntos} : Create a new usuarioConjunto.
     *
     * @param usuarioConjuntoDTO the usuarioConjuntoDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new usuarioConjuntoDTO, or with status {@code 400 (Bad Request)} if the usuarioConjunto has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    @PreAuthorize("hasAuthority(\"" + AuthoritiesConstants.ADMIN + "\")")
    public ResponseEntity<UsuarioConjuntoDTO> createUsuarioConjunto(@Valid @RequestBody UsuarioConjuntoDTO usuarioConjuntoDTO)
        throws URISyntaxException {
        LOG.debug("REST request to save UsuarioConjunto : {}", usuarioConjuntoDTO);
        if (usuarioConjuntoDTO.getId() != null) {
            throw new BadRequestAlertException("A new usuarioConjunto cannot already have an ID", ENTITY_NAME, "idexists");
        }
        usuarioConjuntoDTO = usuarioConjuntoService.save(usuarioConjuntoDTO);
        return ResponseEntity.created(new URI("/api/usuario-conjuntos/" + usuarioConjuntoDTO.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, usuarioConjuntoDTO.getId()))
            .body(usuarioConjuntoDTO);
    }

    /**
     * {@code PUT  /usuario-conjuntos/:id} : Updates an existing usuarioConjunto.
     *
     * @param id the id of the usuarioConjuntoDTO to save.
     * @param usuarioConjuntoDTO the usuarioConjuntoDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated usuarioConjuntoDTO,
     * or with status {@code 400 (Bad Request)} if the usuarioConjuntoDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the usuarioConjuntoDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority(\"" + AuthoritiesConstants.ADMIN + "\")")
    public ResponseEntity<UsuarioConjuntoDTO> updateUsuarioConjunto(
        @PathVariable(value = "id", required = false) final String id,
        @Valid @RequestBody UsuarioConjuntoDTO usuarioConjuntoDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to update UsuarioConjunto : {}, {}", id, usuarioConjuntoDTO);
        if (usuarioConjuntoDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, usuarioConjuntoDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!usuarioConjuntoRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        usuarioConjuntoDTO = usuarioConjuntoService.update(usuarioConjuntoDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, usuarioConjuntoDTO.getId()))
            .body(usuarioConjuntoDTO);
    }

    /**
     * {@code PATCH  /usuario-conjuntos/:id} : Partial updates given fields of an existing usuarioConjunto, field will ignore if it is null
     *
     * @param id the id of the usuarioConjuntoDTO to save.
     * @param usuarioConjuntoDTO the usuarioConjuntoDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated usuarioConjuntoDTO,
     * or with status {@code 400 (Bad Request)} if the usuarioConjuntoDTO is not valid,
     * or with status {@code 404 (Not Found)} if the usuarioConjuntoDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the usuarioConjuntoDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    @PreAuthorize("hasAuthority(\"" + AuthoritiesConstants.ADMIN + "\")")
    public ResponseEntity<UsuarioConjuntoDTO> partialUpdateUsuarioConjunto(
        @PathVariable(value = "id", required = false) final String id,
        @NotNull @RequestBody UsuarioConjuntoDTO usuarioConjuntoDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to partial update UsuarioConjunto partially : {}, {}", id, usuarioConjuntoDTO);
        if (usuarioConjuntoDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, usuarioConjuntoDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!usuarioConjuntoRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<UsuarioConjuntoDTO> result = usuarioConjuntoService.partialUpdate(usuarioConjuntoDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, usuarioConjuntoDTO.getId())
        );
    }

    /**
     * {@code GET  /usuario-conjuntos} : get all the Usuario Conjuntos.
     *
     * @param pageable the pagination information.
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of Usuario Conjuntos in body.
     */
    @GetMapping("")
    @PreAuthorize("hasAuthority(\"" + AuthoritiesConstants.ADMIN + "\")")
    public ResponseEntity<List<UsuarioConjuntoDTO>> getAllUsuarioConjuntos(
        @org.springdoc.core.annotations.ParameterObject Pageable pageable,
        @RequestParam(name = "eagerload", required = false, defaultValue = "true") boolean eagerload
    ) {
        LOG.debug("REST request to get a page of UsuarioConjuntos");
        Page<UsuarioConjuntoDTO> page;
        if (eagerload) {
            page = usuarioConjuntoService.findAllWithEagerRelationships(pageable);
        } else {
            page = usuarioConjuntoService.findAll(pageable);
        }
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /usuario-conjuntos/:id} : get the "id" usuarioConjunto.
     *
     * @param id the id of the usuarioConjuntoDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the usuarioConjuntoDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority(\"" + AuthoritiesConstants.ADMIN + "\")")
    public ResponseEntity<UsuarioConjuntoDTO> getUsuarioConjunto(@PathVariable("id") String id) {
        LOG.debug("REST request to get UsuarioConjunto : {}", id);
        Optional<UsuarioConjuntoDTO> usuarioConjuntoDTO = usuarioConjuntoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(usuarioConjuntoDTO);
    }

    /**
     * {@code DELETE  /usuario-conjuntos/:id} : delete the "id" usuarioConjunto.
     *
     * @param id the id of the usuarioConjuntoDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority(\"" + AuthoritiesConstants.ADMIN + "\")")
    public ResponseEntity<Void> deleteUsuarioConjunto(@PathVariable("id") String id) {
        LOG.debug("REST request to delete UsuarioConjunto : {}", id);
        usuarioConjuntoService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id)).build();
    }
}
