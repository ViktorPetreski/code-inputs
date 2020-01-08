package com.fri.code.inputs.graphql;

import com.fri.code.inputs.lib.InputMetadata;
import com.fri.code.inputs.services.beans.InputMetadataBean;
import com.kumuluz.ee.graphql.annotations.GraphQLClass;
import io.leangen.graphql.annotations.GraphQLArgument;
import io.leangen.graphql.annotations.GraphQLMutation;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

@GraphQLClass
@ApplicationScoped
public class InputMetadataMutations {

    @Inject
    private InputMetadataBean inputMetadataBean;

    @GraphQLMutation
    public InputMetadata addInputMetadata(@GraphQLArgument(name = "inputMetadata") InputMetadata inputMetadata) {
        inputMetadataBean.createInputMetadata(inputMetadata);
        return inputMetadata;
    }

    @GraphQLMutation
    public DeleteResponse deleteInputMetadata(@GraphQLArgument(name = "id") Integer inputID) {
        return new DeleteResponse(inputMetadataBean.deleteInputMetadata(inputID));
    }
}
