package com.fri.code.inputs.services.clients;

import com.fri.code.inputs.services.dtos.InputProcessRequest;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import javax.enterprise.context.Dependent;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import java.util.concurrent.CompletionStage;

@Path("process")
@RegisterRestClient(configKey = "input-processing-api")
@Dependent
public interface InputProcessingApi {

    @POST
    CompletionStage<String> processInputAsync(InputProcessRequest inputProcessRequest);
}
