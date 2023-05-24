package com.amadeus.digital.cli.commands;

import io.quarkus.test.junit.main.Launch;
import io.quarkus.test.junit.main.LaunchResult;
import io.quarkus.test.junit.main.QuarkusMainTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

@QuarkusMainTest
class TestSay {

  @Test
  @Launch(value = "say", exitCode = 2)
  public void printsHelp(LaunchResult result) {
    Assertions.assertTrue(result.getErrorOutput().contains("Missing required subcommand"));
    Assertions.assertTrue(result.getErrorOutput().contains("Usage:"));
  }

  @Test
  @Launch(value = "say", exitCode = 2)
  public void proposesSubcommands(LaunchResult result) {
    Assertions.assertTrue(result.getErrorOutput().contains("hello"));
    Assertions.assertTrue(result.getErrorOutput().contains("goodbye"));
  }

  @Test
  @Launch(value = {"say", "hello"}, exitCode = 0)
  public void sayHello(LaunchResult result) {
    Assertions.assertTrue(result.getOutput().contains("Hello"));
  }


  @Test
  @Launch(value = {"say", "hello", "-e"}, exitCode = 0)
  public void sayHelloWithExclamation(LaunchResult result) {
    Assertions.assertTrue(result.getOutput().contains("!"));
  }

  @Test
  @Launch(value = {"say", "hello", "you"}, exitCode = 0)
  public void sayHelloYou(LaunchResult result) {
    Assertions.assertTrue(result.getOutput().contains("Hello you"));
  }

  @Test
  @Launch(value = {"say", "hello", "-f"}, exitCode = 1)
  public void sayHelloFAils(LaunchResult result) {
    Assertions.assertTrue(result.getOutput().contains("You asked"));
  }


  @Test
  @Launch(value = {"say", "goodbye"}, exitCode = 2)
  public void sayGoodbyeRequiresParameter(LaunchResult result) {
    Assertions.assertTrue(result.getErrorOutput().contains("Missing required parameter"));
  }


  @Test
  @Launch(value = {"say", "goodbye", "you"}, exitCode = 0)
  public void sayGoodbye(LaunchResult result) {
    Assertions.assertTrue(result.getOutput().contains("Goodbye you"));
  }

  @Test
  @Launch(value = {"say", "goodbye", "you", "-f"}, exitCode = 1)
  public void sayGoodbyeFAils(LaunchResult result) {
    Assertions.assertTrue(result.getOutput().contains("You asked"));
  }


}