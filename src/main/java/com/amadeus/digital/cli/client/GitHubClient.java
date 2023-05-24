package com.amadeus.digital.cli.client;

import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import java.util.List;

@RegisterRestClient(configKey = "gh")
public interface GitHubClient {

  @GET
  @Produces(MediaType.APPLICATION_JSON)
  @Path("/{owner}/{repo}/branches")
  List<GitHubBranch> branches(@PathParam("owner") String owner, @PathParam("repo") String repo);

  @GET
  @Produces(MediaType.APPLICATION_JSON)
  @Path("/{owner}/{repo}/commits")
  List<GitHubCommit> commits(@PathParam("owner") String owner, @PathParam("repo") String repo);


}
