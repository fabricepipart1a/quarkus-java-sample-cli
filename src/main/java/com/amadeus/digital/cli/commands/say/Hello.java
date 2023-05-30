package com.amadeus.digital.cli.commands.say;

import picocli.CommandLine;

import java.util.concurrent.Callable;

@CommandLine.Command(name = "hello", description = "Say hello")
public class Hello extends GreetCommand implements Callable<Integer> {

  @Override
  public Integer call() {
    greetService.sayHello(who, times, exclamation);
    if (fail) {
      console.error("You asked to be rude");
      return CommandLine.ExitCode.SOFTWARE;
    }
    return CommandLine.ExitCode.OK;
  }
}
