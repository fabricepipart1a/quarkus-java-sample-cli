package com.amadeus.digital.cli.model;

import io.quarkus.runtime.annotations.RegisterForReflection;

@RegisterForReflection
public class OpenidLoginResponse {
  public String access_token;
}
