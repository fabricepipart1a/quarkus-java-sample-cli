package com.amadeus.digital.cli.commands.say;

import picocli.CommandLine;

import java.util.concurrent.Callable;

@CommandLine.Command(name = "goodbye", description = "Say you're leaving")
public class Goodbye extends GreetCommand implements Callable<Integer> {


  @Override
  public Integer call() {
    greetService.sayGoodbye(who, times, exclamation);
    if (fail) {
      console.error("You asked to fail");
      return CommandLine.ExitCode.SOFTWARE;
    }
    return CommandLine.ExitCode.OK;
  }
}
