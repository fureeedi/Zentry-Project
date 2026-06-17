package com.edu.sena.zentry.web.rest;

import static com.edu.sena.zentry.domain.UsuarioConjuntoAsserts.*;
import static com.edu.sena.zentry.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.edu.sena.zentry.IntegrationTest;
import com.edu.sena.zentry.domain.ConjuntoResidencial;
import com.edu.sena.zentry.domain.User;
import com.edu.sena.zentry.domain.UsuarioConjunto;
import com.edu.sena.zentry.repository.UserRepository;
import com.edu.sena.zentry.repository.UsuarioConjuntoRepository;
import com.edu.sena.zentry.service.UsuarioConjuntoService;
import com.edu.sena.zentry.service.dto.UsuarioConjuntoDTO;
import com.edu.sena.zentry.service.mapper.UsuarioConjuntoMapper;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.ArrayList;
import java.util.UUID;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

/**
 * Integration tests for the {@link UsuarioConjuntoResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class UsuarioConjuntoResourceIT {

    private static final String ENTITY_API_URL = "/api/usuario-conjuntos";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    @Autowired
    private ObjectMapper om;

    @Autowired
    private UsuarioConjuntoRepository usuarioConjuntoRepository;

    @Autowired
    private UserRepository userRepository;

    @Mock
    private UsuarioConjuntoRepository usuarioConjuntoRepositoryMock;

    @Autowired
    private UsuarioConjuntoMapper usuarioConjuntoMapper;

    @Mock
    private UsuarioConjuntoService usuarioConjuntoServiceMock;

    @Autowired
    private MockMvc restUsuarioConjuntoMockMvc;

    private UsuarioConjunto usuarioConjunto;

    private UsuarioConjunto insertedUsuarioConjunto;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static UsuarioConjunto createEntity() {
        UsuarioConjunto usuarioConjunto = new UsuarioConjunto();
        // Add required entity
        User user = UserResourceIT.createEntity();
        user.setId("fixed-id-for-tests");
        usuarioConjunto.setUser(user);
        // Add required entity
        ConjuntoResidencial conjuntoResidencial;
        conjuntoResidencial = ConjuntoResidencialResourceIT.createEntity();
        conjuntoResidencial.setId("fixed-id-for-tests");
        usuarioConjunto.setConjuntoResidencial(conjuntoResidencial);
        return usuarioConjunto;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static UsuarioConjunto createUpdatedEntity() {
        UsuarioConjunto updatedUsuarioConjunto = new UsuarioConjunto();
        // Add required entity
        User user = UserResourceIT.createEntity();
        user.setId("fixed-id-for-tests");
        updatedUsuarioConjunto.setUser(user);
        // Add required entity
        ConjuntoResidencial conjuntoResidencial;
        conjuntoResidencial = ConjuntoResidencialResourceIT.createUpdatedEntity();
        conjuntoResidencial.setId("fixed-id-for-tests");
        updatedUsuarioConjunto.setConjuntoResidencial(conjuntoResidencial);
        return updatedUsuarioConjunto;
    }

    @BeforeEach
    void initTest() {
        usuarioConjunto = createEntity();
    }

    @AfterEach
    void cleanup() {
        if (insertedUsuarioConjunto != null) {
            usuarioConjuntoRepository.delete(insertedUsuarioConjunto);
            insertedUsuarioConjunto = null;
        }
        userRepository.deleteAll();
    }

    @Test
    void createUsuarioConjunto() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the UsuarioConjunto
        UsuarioConjuntoDTO usuarioConjuntoDTO = usuarioConjuntoMapper.toDto(usuarioConjunto);
        var returnedUsuarioConjuntoDTO = om.readValue(
            restUsuarioConjuntoMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(usuarioConjuntoDTO)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            UsuarioConjuntoDTO.class
        );

        // Validate the UsuarioConjunto in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedUsuarioConjunto = usuarioConjuntoMapper.toEntity(returnedUsuarioConjuntoDTO);
        assertUsuarioConjuntoUpdatableFieldsEquals(returnedUsuarioConjunto, getPersistedUsuarioConjunto(returnedUsuarioConjunto));

        insertedUsuarioConjunto = returnedUsuarioConjunto;
    }

    @Test
    void createUsuarioConjuntoWithExistingId() throws Exception {
        // Create the UsuarioConjunto with an existing ID
        usuarioConjunto.setId("existing_id");
        UsuarioConjuntoDTO usuarioConjuntoDTO = usuarioConjuntoMapper.toDto(usuarioConjunto);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restUsuarioConjuntoMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(usuarioConjuntoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the UsuarioConjunto in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    void getAllUsuarioConjuntos() throws Exception {
        // Initialize the database
        insertedUsuarioConjunto = usuarioConjuntoRepository.save(usuarioConjunto);

        // Get all the usuarioConjuntoList
        restUsuarioConjuntoMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(usuarioConjunto.getId())));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllUsuarioConjuntosWithEagerRelationshipsIsEnabled() throws Exception {
        when(usuarioConjuntoServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restUsuarioConjuntoMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(usuarioConjuntoServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllUsuarioConjuntosWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(usuarioConjuntoServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restUsuarioConjuntoMockMvc.perform(get(ENTITY_API_URL + "?eagerload=false")).andExpect(status().isOk());
        verify(usuarioConjuntoRepositoryMock, times(1)).findAll(any(Pageable.class));
    }

    @Test
    void getUsuarioConjunto() throws Exception {
        // Initialize the database
        insertedUsuarioConjunto = usuarioConjuntoRepository.save(usuarioConjunto);

        // Get the usuarioConjunto
        restUsuarioConjuntoMockMvc
            .perform(get(ENTITY_API_URL_ID, usuarioConjunto.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(usuarioConjunto.getId()));
    }

    @Test
    void getNonExistingUsuarioConjunto() throws Exception {
        // Get the usuarioConjunto
        restUsuarioConjuntoMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    void putExistingUsuarioConjunto() throws Exception {
        // Initialize the database
        insertedUsuarioConjunto = usuarioConjuntoRepository.save(usuarioConjunto);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the usuarioConjunto
        UsuarioConjunto updatedUsuarioConjunto = usuarioConjuntoRepository.findById(usuarioConjunto.getId()).orElseThrow();
        UsuarioConjuntoDTO usuarioConjuntoDTO = usuarioConjuntoMapper.toDto(updatedUsuarioConjunto);

        restUsuarioConjuntoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, usuarioConjuntoDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(usuarioConjuntoDTO))
            )
            .andExpect(status().isOk());

        // Validate the UsuarioConjunto in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedUsuarioConjuntoToMatchAllProperties(updatedUsuarioConjunto);
    }

    @Test
    void putNonExistingUsuarioConjunto() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        usuarioConjunto.setId(UUID.randomUUID().toString());

        // Create the UsuarioConjunto
        UsuarioConjuntoDTO usuarioConjuntoDTO = usuarioConjuntoMapper.toDto(usuarioConjunto);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restUsuarioConjuntoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, usuarioConjuntoDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(usuarioConjuntoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the UsuarioConjunto in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithIdMismatchUsuarioConjunto() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        usuarioConjunto.setId(UUID.randomUUID().toString());

        // Create the UsuarioConjunto
        UsuarioConjuntoDTO usuarioConjuntoDTO = usuarioConjuntoMapper.toDto(usuarioConjunto);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restUsuarioConjuntoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(usuarioConjuntoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the UsuarioConjunto in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithMissingIdPathParamUsuarioConjunto() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        usuarioConjunto.setId(UUID.randomUUID().toString());

        // Create the UsuarioConjunto
        UsuarioConjuntoDTO usuarioConjuntoDTO = usuarioConjuntoMapper.toDto(usuarioConjunto);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restUsuarioConjuntoMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(usuarioConjuntoDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the UsuarioConjunto in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void partialUpdateUsuarioConjuntoWithPatch() throws Exception {
        // Initialize the database
        insertedUsuarioConjunto = usuarioConjuntoRepository.save(usuarioConjunto);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the usuarioConjunto using partial update
        UsuarioConjunto partialUpdatedUsuarioConjunto = new UsuarioConjunto();
        partialUpdatedUsuarioConjunto.setId(usuarioConjunto.getId());

        restUsuarioConjuntoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedUsuarioConjunto.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedUsuarioConjunto))
            )
            .andExpect(status().isOk());

        // Validate the UsuarioConjunto in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertUsuarioConjuntoUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedUsuarioConjunto, usuarioConjunto),
            getPersistedUsuarioConjunto(usuarioConjunto)
        );
    }

    @Test
    void fullUpdateUsuarioConjuntoWithPatch() throws Exception {
        // Initialize the database
        insertedUsuarioConjunto = usuarioConjuntoRepository.save(usuarioConjunto);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the usuarioConjunto using partial update
        UsuarioConjunto partialUpdatedUsuarioConjunto = new UsuarioConjunto();
        partialUpdatedUsuarioConjunto.setId(usuarioConjunto.getId());

        restUsuarioConjuntoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedUsuarioConjunto.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedUsuarioConjunto))
            )
            .andExpect(status().isOk());

        // Validate the UsuarioConjunto in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertUsuarioConjuntoUpdatableFieldsEquals(
            partialUpdatedUsuarioConjunto,
            getPersistedUsuarioConjunto(partialUpdatedUsuarioConjunto)
        );
    }

    @Test
    void patchNonExistingUsuarioConjunto() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        usuarioConjunto.setId(UUID.randomUUID().toString());

        // Create the UsuarioConjunto
        UsuarioConjuntoDTO usuarioConjuntoDTO = usuarioConjuntoMapper.toDto(usuarioConjunto);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restUsuarioConjuntoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, usuarioConjuntoDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(usuarioConjuntoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the UsuarioConjunto in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithIdMismatchUsuarioConjunto() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        usuarioConjunto.setId(UUID.randomUUID().toString());

        // Create the UsuarioConjunto
        UsuarioConjuntoDTO usuarioConjuntoDTO = usuarioConjuntoMapper.toDto(usuarioConjunto);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restUsuarioConjuntoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(usuarioConjuntoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the UsuarioConjunto in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithMissingIdPathParamUsuarioConjunto() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        usuarioConjunto.setId(UUID.randomUUID().toString());

        // Create the UsuarioConjunto
        UsuarioConjuntoDTO usuarioConjuntoDTO = usuarioConjuntoMapper.toDto(usuarioConjunto);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restUsuarioConjuntoMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(usuarioConjuntoDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the UsuarioConjunto in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void deleteUsuarioConjunto() throws Exception {
        // Initialize the database
        insertedUsuarioConjunto = usuarioConjuntoRepository.save(usuarioConjunto);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the usuarioConjunto
        restUsuarioConjuntoMockMvc
            .perform(delete(ENTITY_API_URL_ID, usuarioConjunto.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return usuarioConjuntoRepository.count();
    }

    protected void assertIncrementedRepositoryCount(long countBefore) {
        assertThat(countBefore + 1).isEqualTo(getRepositoryCount());
    }

    protected void assertDecrementedRepositoryCount(long countBefore) {
        assertThat(countBefore - 1).isEqualTo(getRepositoryCount());
    }

    protected void assertSameRepositoryCount(long countBefore) {
        assertThat(countBefore).isEqualTo(getRepositoryCount());
    }

    protected UsuarioConjunto getPersistedUsuarioConjunto(UsuarioConjunto usuarioConjunto) {
        return usuarioConjuntoRepository.findById(usuarioConjunto.getId()).orElseThrow();
    }

    protected void assertPersistedUsuarioConjuntoToMatchAllProperties(UsuarioConjunto expectedUsuarioConjunto) {
        assertUsuarioConjuntoAllPropertiesEquals(expectedUsuarioConjunto, getPersistedUsuarioConjunto(expectedUsuarioConjunto));
    }

    protected void assertPersistedUsuarioConjuntoToMatchUpdatableProperties(UsuarioConjunto expectedUsuarioConjunto) {
        assertUsuarioConjuntoAllUpdatablePropertiesEquals(expectedUsuarioConjunto, getPersistedUsuarioConjunto(expectedUsuarioConjunto));
    }
}
