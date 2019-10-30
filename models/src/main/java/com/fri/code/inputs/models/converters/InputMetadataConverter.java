package com.fri.code.inputs.models.converters;

import com.fri.code.inputs.lib.InputMetadata;
import com.fri.code.inputs.models.entities.InputMetadataEntity;

public class InputMetadataConverter {
    public static InputMetadata toDTO(InputMetadataEntity entity){
        InputMetadata inputMetadata = new InputMetadata();
        inputMetadata.setContent(entity.getContent());
        inputMetadata.setExerciseID(entity.getExerciseID());
        inputMetadata.setID(entity.getID());
        inputMetadata.setHidden(entity.getHidden());
        return inputMetadata;
    }

    public static InputMetadataEntity toEntity(InputMetadata inputMetadata) {
        InputMetadataEntity entity = new InputMetadataEntity();
        entity.setContent(inputMetadata.getContent());
        entity.setID(inputMetadata.getID());
        entity.setExerciseID(inputMetadata.getExerciseID());
        entity.setHidden(inputMetadata.getHidden());
        return entity;
    }
}
