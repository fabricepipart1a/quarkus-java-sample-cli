package com.amadeus.digital.cli.commands;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import io.quarkus.test.junit.main.Launch;
import io.quarkus.test.junit.main.LaunchResult;
import io.quarkus.test.junit.main.QuarkusMainTest;

@QuarkusMainTest
class TestMain {

  @Test
  @Launch(value = {}, exitCode = 2)
  public void printsHelp(LaunchResult result) {
    Assertions.assertTrue(result.getErrorOutput().contains("Missing required subcommand"));
    Assertions.assertTrue(result.getErrorOutput().contains("Usage:"));
  }
}