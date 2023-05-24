package com.amadeus.digital.cli.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import io.quarkus.runtime.annotations.RegisterForReflection;

@RegisterForReflection
@JsonIgnoreProperties(ignoreUnknown = true)
public class ArgoApplication {
  public ArgoApplicationMetadata metadata;
  public ArgoApplicationSpec spec;
  public ArgoApplicationStatus status;
}
