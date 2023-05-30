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
  void printsHelp(LaunchResult result) {
    Assertions.assertTrue(result.getErrorOutput().contains("Missing required subcommand"));
    Assertions.assertTrue(result.getErrorOutput().contains("Usage:"));
  }

  @Test
  @Launch(value = "say", exitCode = 2)
  void proposesSubcommands(LaunchResult result) {
    Assertions.assertTrue(result.getErrorOutput().contains("hello"));
    Assertions.assertTrue(result.getErrorOutput().contains("goodbye"));
  }

  @Test
  @Launch(value = {"say", "hello"}, exitCode = 0)
  void sayHello(LaunchResult result) {
    Assertions.assertTrue(result.getOutput().contains("Hello"));
  }


  @Test
  @Launch(value = {"say", "hello", "-e"}, exitCode = 0)
  void sayHelloWithExclamation(LaunchResult result) {
    Assertions.assertTrue(result.getOutput().contains("!"));
  }

  @Test
  @Launch(value = {"say", "hello", "you"}, exitCode = 0)
  void sayHelloYou(LaunchResult result) {
    Assertions.assertTrue(result.getOutput().contains("Hello you"));
  }

  @Test
  @Launch(value = {"say", "hello", "-f"}, exitCode = 1)
  void sayHelloFAils(LaunchResult result) {
    Assertions.assertTrue(result.getOutput().contains("You asked"));
  }


  @Test
  @Launch(value = {"say", "goodbye"}, exitCode = 0)
  void sayGoodbyeWorld(LaunchResult result) {
    Assertions.assertTrue(result.getOutput().contains("World"));
  }


  @Test
  @Launch(value = {"say", "goodbye", "you"}, exitCode = 0)
  void sayGoodbye(LaunchResult result) {
    Assertions.assertTrue(result.getOutput().contains("Goodbye you"));
  }

  @Test
  @Launch(value = {"say", "goodbye", "you", "-f"}, exitCode = 1)
  void sayGoodbyeFAils(LaunchResult result) {
    Assertions.assertTrue(result.getOutput().contains("You asked"));
  }


}