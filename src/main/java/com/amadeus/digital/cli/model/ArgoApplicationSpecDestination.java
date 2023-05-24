package com.amadeus.digital.cli.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import io.quarkus.runtime.annotations.RegisterForReflection;

@RegisterForReflection
@JsonIgnoreProperties(ignoreUnknown = true)
public class ArgoApplicationSpecDestination {
  public static final String IN_CLUSTER = "in-cluster";
  public String namespace;
  public String name;
}
