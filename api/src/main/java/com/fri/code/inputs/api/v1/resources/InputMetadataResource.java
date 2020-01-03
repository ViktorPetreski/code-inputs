package com.fri.code.inputs.api.v1.resources;

import com.fri.code.inputs.api.v1.dtos.ApiError;
import com.fri.code.inputs.lib.InputMetadata;
import com.fri.code.inputs.services.beans.InputMetadataBean;
import org.eclipse.microprofile.metrics.annotation.Timed;

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

    @PUT
    @Path("{inputID}")
    public Response putInput(@PathParam("inputID") Integer inputID, InputMetadata updatedInput) {
        updatedInput = inputMetadataBean.putInputMetadata(inputID, updatedInput);
        if (updatedInput == null) {
            return Response.status(Response.Status.NOT_FOUND).entity(getNotFoundApiError("")).build();
        }
        return Response.status(Response.Status.NOT_MODIFIED).build();
    }

    @DELETE
    @Path("/{inputID}")
    public Response removeInput(@PathParam("inputID") Integer inputID) {
        if(inputMetadataBean.deleteInputMetadata(inputID))
            return Response.status(Response.Status.NO_CONTENT).build();
        else return Response.status(Response.Status.NOT_FOUND).entity(getNotFoundApiError("")).build();
    }

    @GET
    @Path("/all")
    @Timed
    public Response getInputs() {
        List<InputMetadata> inputs = inputMetadataBean.getInputs();
        return Response.status(STATUS_OK).entity(inputs).build();
    }

    @POST
    public Response createInput(InputMetadata inputMetadata) {
        ApiError error = new ApiError();
        error.setCode(Response.Status.BAD_REQUEST.toString());
        error.setMessage("You are missing some of the parameters");
        error.setStatus(Response.Status.BAD_REQUEST.getStatusCode());
        if (inputMetadata.getHidden() == null || inputMetadata.getExerciseID() == null || inputMetadata.getContent() == null) {
            return Response.status(Response.Status.BAD_REQUEST).entity(error).build();
        }
        else{
            try {
                inputMetadata = inputMetadataBean.createInputMetadata(inputMetadata);
            } catch (Exception e) {
                error.setMessage(e.getMessage());
                return Response.status(Response.Status.BAD_REQUEST).entity(error).build();
            }
        }
        return Response.status(STATUS_OK).entity(inputMetadata).build();
    }


    private ApiError getNotFoundApiError(String message) {
        ApiError error = new ApiError();
        if (message.isEmpty()) message = "The exercise was not found";
        error.setCode(Response.Status.NOT_FOUND.toString());
        error.setMessage(message);
        error.setStatus(Response.Status.NOT_FOUND.getStatusCode());
        return error;
    }

}
