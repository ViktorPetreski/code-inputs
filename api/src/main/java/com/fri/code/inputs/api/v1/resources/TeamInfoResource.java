package com.fri.code.inputs.api.v1.resources;

import com.fri.code.inputs.lib.InputMetadata;

import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@ApplicationScoped
@Path("/info")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class TeamInfoResource {
    @GET
    public Response getTeamInfo() {
        String response = "{\n" +
                "  \"clani\": [\"vp7417\", \"ld2463\"],\n" +
                "  \"opis_projekta \": \"Online compiler\",\n" +
                "  \"mikrostoritve\": [\"http://34.67.168.202:8080/v1/exercises/\", \"http://34.67.168.202:8081/v1/inputs/all\"],\n" +
                "  \"github \": [\"\"],\n" +
                "  \"travis \": [\"\"],\n" +
                "  \"dockerhub\": [\"Hello World\"]\n" +
                "}";
        return Response.ok(response).build();
    }
}
