package com.amadeus.digital.cli.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import io.quarkus.runtime.annotations.RegisterForReflection;

@RegisterForReflection
@JsonIgnoreProperties(ignoreUnknown = true)
public class ArgoApplicationMetadata {
  public String name;
  public List<ArgoApplicationOwnerReference> ownerReferences;
}
