package com.fri.code.inputs.api.v1.resources;

import com.fri.code.inputs.api.v1.dtos.ApiError;
import com.fri.code.inputs.lib.InputMetadata;
import com.fri.code.inputs.services.beans.InputMetadataBean;
import com.kumuluz.ee.logs.cdi.Log;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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

@Log
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
    @Operation(summary = "Get all inputs for exercise", description = "Returns all inputs for specified exercise")
    @ApiResponses({
            @ApiResponse(description = "List of inputs", responseCode = "200", content = @Content(array = @ArraySchema(schema = @Schema(implementation =
                    InputMetadata.class))))
    })
    public Response getInputsForExercise(@QueryParam("exerciseID") Integer exerciseID) {
        List<InputMetadata> inputs = inputMetadataBean.getInputsForExercise(exerciseID);
        return Response.ok(inputs).build();
    }

    @PUT
    @Operation(summary = "Update input", description = "Returns updated input")
    @ApiResponses({
            @ApiResponse(description = "Updated input", responseCode = "200", content = @Content(schema = @Schema(implementation =
                    InputMetadata.class))),
            @ApiResponse(description = "Input not found", responseCode = "404")
    })
    @Path("{inputID}")
    public Response putInput(@PathParam("inputID") Integer inputID, InputMetadata updatedInput) {
        updatedInput = inputMetadataBean.putInputMetadata(inputID, updatedInput);
        if (updatedInput == null) {
            return Response.status(Response.Status.NOT_FOUND).entity(getNotFoundApiError("")).build();
        }
        return Response.status(Response.Status.NOT_MODIFIED).build();
    }

    @DELETE
    @Operation(summary = "Delete input", description = "Returns no content")
    @ApiResponses({
            @ApiResponse(description = "Successful delete", responseCode = "204"),
            @ApiResponse(description = "Input not found", responseCode = "404")
    })
    @Path("/{inputID}")
    public Response removeInput(@PathParam("inputID") Integer inputID) {
        if(inputMetadataBean.deleteInputMetadata(inputID))
            return Response.status(Response.Status.NO_CONTENT).build();
        else return Response.status(Response.Status.NOT_FOUND).entity(getNotFoundApiError("")).build();
    }

    @GET
    @Operation(summary = "Get all inputs", description = "Returns all inputs")
    @ApiResponses({
            @ApiResponse(description = "List of inputs", responseCode = "200", content = @Content(array = @ArraySchema(schema = @Schema(implementation =
                    InputMetadata.class))))
    })
    @Path("/all")
    @Timed
    public Response getInputs() {
        List<InputMetadata> inputs = inputMetadataBean.getInputs();
        return Response.status(STATUS_OK).entity(inputs).build();
    }

    @POST
    @Operation(summary = "Add input", description = "Returns new input")
    @ApiResponses({
            @ApiResponse(description = "New input with details", responseCode = "200", content = @Content(schema = @Schema(implementation =
                    InputMetadata.class))),
            @ApiResponse(description = "Can't add the input", responseCode = "400")
    })
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
