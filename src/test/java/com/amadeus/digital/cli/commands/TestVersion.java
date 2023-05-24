package com.amadeus.digital.cli.commands;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import io.quarkus.test.junit.main.Launch;
import io.quarkus.test.junit.main.LaunchResult;
import io.quarkus.test.junit.main.QuarkusMainTest;

@QuarkusMainTest
class TestVersion {

  @Test
  @Launch(value = {"version"}, exitCode = 0)
  public void printsVersion(LaunchResult result) {
    Assertions.assertTrue(result.getOutput().contains("Version:"));
  }
}