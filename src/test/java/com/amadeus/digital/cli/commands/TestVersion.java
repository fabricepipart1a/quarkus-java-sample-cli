package com.amadeus.digital.cli.commands;

import io.quarkus.test.junit.main.Launch;
import io.quarkus.test.junit.main.LaunchResult;
import io.quarkus.test.junit.main.QuarkusMainTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

@QuarkusMainTest
class TestVersion {

  @Test
  @Launch(value = {"version"}, exitCode = 0)
  void printsVersion(LaunchResult result) {
    Assertions.assertTrue(result.getOutput().contains("Version:"));
  }
}