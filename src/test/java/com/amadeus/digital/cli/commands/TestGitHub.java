package com.amadeus.digital.cli.commands;

import com.amadeus.digital.cli.test.WireMockExtensions;
import io.quarkus.test.common.QuarkusTestResource;
import io.quarkus.test.junit.main.Launch;
import io.quarkus.test.junit.main.LaunchResult;
import io.quarkus.test.junit.main.QuarkusMainTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

@QuarkusMainTest
@QuarkusTestResource(WireMockExtensions.class)
class TestGitHub {

  @Test
  @Launch(value = "github", exitCode = 2)
  public void printsHelp(LaunchResult result) {
    Assertions.assertTrue(result.getErrorOutput().contains("Missing required subcommand"));
    Assertions.assertTrue(result.getErrorOutput().contains("Usage:"));
  }

  @Test
  @Launch(value = "github", exitCode = 2)
  public void proposesSubcommands(LaunchResult result) {
    Assertions.assertTrue(result.getErrorOutput().contains("branch"));
    Assertions.assertTrue(result.getErrorOutput().contains("commit"));
  }

  @Test
  @Launch(value = {"github", "branch"}, exitCode = 0)
  public void listBranches(LaunchResult result) {
    Assertions.assertTrue(result.getOutput().contains("b1"));
    Assertions.assertTrue(result.getOutput().contains("b2"));
  }

  @Test
  @Launch(value = {"github", "commit"}, exitCode = 0)
  public void listCommits(LaunchResult result) {
    Assertions.assertTrue(result.getOutput().contains("41545f620ff0cc1387d4d423e1e12b5eba949166"));
  }

  @Test
  @Launch(value = {"github", "branch", "-o", "fp", "-r", "repo"}, exitCode = 0)
  public void listBranchesWithParameters(LaunchResult result) {
    Assertions.assertTrue(result.getOutput().contains("b3"));
    Assertions.assertTrue(result.getOutput().contains("b4"));
  }

  @Test
  @Launch(value = {"github", "commit", "-o", "fp", "-r", "repo"}, exitCode = 0)
  public void listCommitsWithParameters(LaunchResult result) {
    Assertions.assertTrue(result.getOutput().contains("abcdef123456789"));
  }

  @Test
  @Launch(value = {"github", "branch", "-o", "fp", "-r", "empty"}, exitCode = 1)
  public void listBranchesEmpty(LaunchResult result) {
    Assertions.assertTrue(result.getOutput().contains("Could not find any"));
  }

  @Test
  @Launch(value = {"github", "commit", "-o", "fp", "-r", "empty"}, exitCode = 1)
  public void listCommitsEmpty(LaunchResult result) {
    Assertions.assertTrue(result.getOutput().contains("Could not find any"));
  }

}