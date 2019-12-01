package com.fri.code.inputs.api.v1.resources;

import com.fri.code.inputs.api.v1.dtos.TeamInfo;

import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;

@ApplicationScoped
@Path("/info")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class TeamInfoResource {
    @GET
    public Response getTeamInfo() {
        TeamInfo teamInfo = new TeamInfo();
        teamInfo.setClani(new ArrayList<String>(List.of("vp7417", "ld2463")));
        teamInfo.setOpis_projekta("Online compiler intended to help educational institutions in optimizing exam sessions");
        teamInfo.setMikrostoritve(new ArrayList<String>(List.of("http://34.67.168.202:8080/v1/exercises/", "http://104.198.217.23:8080/v1/inputs/all","http://34.70.28.108:8080/v1/outputs/all" )));
        teamInfo.setGithub(new ArrayList<String>(List.of("https://github.com/ViktorPetreski/code", "https://github.com/ViktorPetreski/code-inputs","https://github.com/LDodevska/code-outputs")));
        teamInfo.setTravis(new ArrayList<String>(List.of("https://travis-ci.org/ViktorPetreski/code", "https://travis-ci.org/ViktorPetreski/code-inputs", "https://travis-ci.org/LDodevska/code-outputs")));
        teamInfo.setDockerhub(new ArrayList<String>(List.of("https://hub.docker.com/r/petreskiv/code", "https://hub.docker.com/r/petreskiv/code-exercises", "https://hub.docker.com/r/ldodevska/code-outputs")));

        return Response.ok(teamInfo).build();
    }
}
