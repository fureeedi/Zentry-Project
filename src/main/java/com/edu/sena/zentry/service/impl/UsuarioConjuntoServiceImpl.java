package com.edu.sena.zentry.service.impl;

import com.edu.sena.zentry.domain.UsuarioConjunto;
import com.edu.sena.zentry.repository.UsuarioConjuntoRepository;
import com.edu.sena.zentry.service.UsuarioConjuntoService;
import com.edu.sena.zentry.service.dto.UsuarioConjuntoDTO;
import com.edu.sena.zentry.service.mapper.UsuarioConjuntoMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

/**
 * Service Implementation for managing {@link com.edu.sena.zentry.domain.UsuarioConjunto}.
 */
@Service
public class UsuarioConjuntoServiceImpl implements UsuarioConjuntoService {

    private static final Logger LOG = LoggerFactory.getLogger(UsuarioConjuntoServiceImpl.class);

    private final UsuarioConjuntoRepository usuarioConjuntoRepository;

    private final UsuarioConjuntoMapper usuarioConjuntoMapper;

    public UsuarioConjuntoServiceImpl(UsuarioConjuntoRepository usuarioConjuntoRepository, UsuarioConjuntoMapper usuarioConjuntoMapper) {
        this.usuarioConjuntoRepository = usuarioConjuntoRepository;
        this.usuarioConjuntoMapper = usuarioConjuntoMapper;
    }

    @Override
    public UsuarioConjuntoDTO save(UsuarioConjuntoDTO usuarioConjuntoDTO) {
        LOG.debug("Request to save UsuarioConjunto : {}", usuarioConjuntoDTO);
        UsuarioConjunto usuarioConjunto = usuarioConjuntoMapper.toEntity(usuarioConjuntoDTO);
        usuarioConjunto = usuarioConjuntoRepository.save(usuarioConjunto);
        return usuarioConjuntoMapper.toDto(usuarioConjunto);
    }

    @Override
    public UsuarioConjuntoDTO update(UsuarioConjuntoDTO usuarioConjuntoDTO) {
        LOG.debug("Request to update UsuarioConjunto : {}", usuarioConjuntoDTO);
        UsuarioConjunto usuarioConjunto = usuarioConjuntoMapper.toEntity(usuarioConjuntoDTO);
        usuarioConjunto = usuarioConjuntoRepository.save(usuarioConjunto);
        return usuarioConjuntoMapper.toDto(usuarioConjunto);
    }

    @Override
    public Optional<UsuarioConjuntoDTO> partialUpdate(UsuarioConjuntoDTO usuarioConjuntoDTO) {
        LOG.debug("Request to partially update UsuarioConjunto : {}", usuarioConjuntoDTO);

        return usuarioConjuntoRepository
            .findById(usuarioConjuntoDTO.getId())
            .map(existingUsuarioConjunto -> {
                usuarioConjuntoMapper.partialUpdate(existingUsuarioConjunto, usuarioConjuntoDTO);

                return existingUsuarioConjunto;
            })
            .map(usuarioConjuntoRepository::save)
            .map(usuarioConjuntoMapper::toDto);
    }

    @Override
    public Page<UsuarioConjuntoDTO> findAll(Pageable pageable) {
        LOG.debug("Request to get all UsuarioConjuntos");
        return usuarioConjuntoRepository.findAll(pageable).map(usuarioConjuntoMapper::toDto);
    }

    public Page<UsuarioConjuntoDTO> findAllWithEagerRelationships(Pageable pageable) {
        return usuarioConjuntoRepository.findAllWithEagerRelationships(pageable).map(usuarioConjuntoMapper::toDto);
    }

    @Override
    public Optional<UsuarioConjuntoDTO> findOne(String id) {
        LOG.debug("Request to get UsuarioConjunto : {}", id);
        return usuarioConjuntoRepository.findOneWithEagerRelationships(id).map(usuarioConjuntoMapper::toDto);
    }

    @Override
    public void delete(String id) {
        LOG.debug("Request to delete UsuarioConjunto : {}", id);
        usuarioConjuntoRepository.deleteById(id);
    }
}
