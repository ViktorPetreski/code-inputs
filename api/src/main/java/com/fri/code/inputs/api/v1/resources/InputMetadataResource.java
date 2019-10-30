package com.fri.code.inputs.api.v1.resources;

import com.fri.code.inputs.lib.InputMetadata;
import com.fri.code.inputs.services.beans.InputMetadataBean;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.util.ArrayList;
import java.util.List;

@ApplicationScoped
@Path("/inputs")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class InputMetadataResource {
    private static Response.Status STATUS_OK = Response.Status.OK;

    @Inject
    private InputMetadataBean inputMetadataBean;

    @Context
    protected UriInfo uriInfo;

    @GET
    public Response getInputsForExercise(@QueryParam("exerciseID") Integer exerciseID) {
        List<InputMetadata> inputs = inputMetadataBean.getInputsForExercise(exerciseID);
        return Response.ok(inputs).build();
    }

    @DELETE
    @Path("/{inputID}")
    public Response removeInput(@PathParam("inputID") Integer inputID) {
        if(inputMetadataBean.deleteInputMetadata(inputID))
            return Response.status(Response.Status.NO_CONTENT).build();
        else return Response.status(Response.Status.NOT_FOUND).build();
    }

    @GET
    @Path("/all")
    public Response getInputs() {
        List<InputMetadata> inputs = inputMetadataBean.getInputs();
        return Response.status(STATUS_OK).entity(inputs).build();
    }

    @POST
    public Response createInput(InputMetadata inputMetadata) {
        if (inputMetadata.getHidden() == null || inputMetadata.getExerciseID() == null || inputMetadata.getContent() == null) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
        else{
            inputMetadata = inputMetadataBean.createInputMetadata(inputMetadata);
        }
        return Response.status(STATUS_OK).entity(inputMetadata).build();
    }

}
