package com.amadeus.digital.cli;

import org.eclipse.microprofile.config.inject.ConfigProperty;

import jakarta.inject.Singleton;

@Singleton
public class Configuration {

  @ConfigProperty(name = "samplecli.gh.client.owner")
  public String ghApiOwner;


  @ConfigProperty(name = "samplecli.gh.client.repo")
  public String ghApiRepo;


  @ConfigProperty(name = "quarkus.application.version")
  public String version;

}
