package com.fri.code.inputs.services.beans;

import com.fri.code.inputs.lib.InputMetadata;
import com.fri.code.inputs.models.converters.InputMetadataConverter;
import com.fri.code.inputs.models.entities.InputMetadataEntity;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
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
        compilerApiUrl = "https://api.jdoodle.com/v1/execute";
        clientId = "336e764a0d15862c64c12304e1d90687";
        clientSecret = "a886859dc6d68b2744c1b434ad7c4ceb3611f5877a905a05e5f7375665f40a73";
        httpClient = ClientBuilder.newClient();


        inputs = new ArrayList<>();
        InputMetadata testInput = new InputMetadata();
        testInput.setHidden(false);
        testInput.setContent("52");
        testInput.setExerciseID(1);
//        testInput = createInputMetadata(testInput);
        inputs.add(testInput);
        testInput = new InputMetadata();
        testInput.setHidden(true);
        testInput.setContent("test    ");
        testInput.setExerciseID(2);
        inputs.add(testInput);
    }

    public List<InputMetadata> getInputsForExercise(Integer exerciseID) {
        return inputs.stream().filter(input -> input.getExerciseID().equals(exerciseID)).collect(Collectors.toList());
    }

    public List<InputMetadata> getInputs() {
        return inputs;
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
