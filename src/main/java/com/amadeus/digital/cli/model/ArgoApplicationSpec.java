package com.amadeus.digital.cli.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import io.quarkus.runtime.annotations.RegisterForReflection;

@RegisterForReflection
@JsonIgnoreProperties(ignoreUnknown = true)
public class ArgoApplicationSpec {
  public ArgoApplicationSpecDestination destination;
  public ArgoApplicationSpecSource source;
}
