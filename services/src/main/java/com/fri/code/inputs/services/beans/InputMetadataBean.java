package com.fri.code.inputs.services.beans;

import com.fri.code.inputs.lib.InputMetadata;
import com.fri.code.inputs.models.converters.InputMetadataConverter;
import com.fri.code.inputs.models.entities.InputMetadataEntity;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriInfo;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@ApplicationScoped
public class InputMetadataBean {
    private Logger log = Logger.getLogger(InputMetadataBean.class.getName());

    @Inject
    private EntityManager em;

    private List<InputMetadata> inputs;

    private String compilerApiUrl;
    private String clientId;
    private String clientSecret;

    private Client httpClient;

    @PostConstruct
    void init() {
        httpClient = ClientBuilder.newClient();
    }

    public List<InputMetadata> getInputsForExercise(Integer exerciseID) {
        TypedQuery<InputMetadataEntity> query = em.createNamedQuery("InputMetadataEntity.getInputsForExercise", InputMetadataEntity.class).setParameter(1, exerciseID);
        return query.getResultList().stream().map(InputMetadataConverter::toDTO).collect(Collectors.toList());
    }

    public List<InputMetadata> getInputs() {
        TypedQuery<InputMetadataEntity> query = em.createNamedQuery("InputMetadataEntity.getAll", InputMetadataEntity.class);
        return query.getResultList().stream().map(InputMetadataConverter::toDTO).collect(Collectors.toList());
    }

    public InputMetadata createInputMetadata(InputMetadata inputMetadata) {
        InputMetadataEntity inputMetadataEntity = InputMetadataConverter.toEntity(inputMetadata);

        try {
            beginTx();
            em.persist(inputMetadataEntity);
            commitTx();
        } catch (Exception e) {
            rollbackTx();
        }

        if (inputMetadataEntity.getID() == null) {
            throw new RuntimeException("The input was not saved");
        }
        return InputMetadataConverter.toDTO(inputMetadataEntity);
    }

    public InputMetadata putInputMetadata(Integer id, InputMetadata inputMetadata) {

        InputMetadataEntity c = em.find(InputMetadataEntity.class, id);

        if (c == null) {
            return null;
        }

        InputMetadataEntity updatedInputMetadataEntity = InputMetadataConverter.toEntity(inputMetadata);

        try {
            beginTx();
            updatedInputMetadataEntity.setID(c.getID());
            updatedInputMetadataEntity = em.merge(updatedInputMetadataEntity);
            commitTx();
        } catch (Exception e) {
            rollbackTx();
        }

        return InputMetadataConverter.toDTO(updatedInputMetadataEntity);
    }

    public boolean deleteInputMetadata(Integer inputID) {
        InputMetadataEntity entity = em.find(InputMetadataEntity.class, inputID);
        if (entity != null) {
            try {
                beginTx();
                em.remove(entity);
                commitTx();
            } catch (Exception e) {
                rollbackTx();
            }
        }
        else return false;
        return true;
    }




    private void beginTx() {
        if (!em.getTransaction().isActive())
            em.getTransaction().begin();
    }

    private void commitTx() {
        if (em.getTransaction().isActive())
            em.getTransaction().commit();
    }

    private void rollbackTx() {
        if (em.getTransaction().isActive())
            em.getTransaction().rollback();
    }
}
