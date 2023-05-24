package com.amadeus.digital.cli.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import io.quarkus.runtime.annotations.RegisterForReflection;

@RegisterForReflection
@JsonIgnoreProperties(ignoreUnknown = true)
public class ArgoApplicationStatusResource {
  public static final String APPLICATION_SET = "ApplicationSet";
  public String kind;
  public String name;
}
