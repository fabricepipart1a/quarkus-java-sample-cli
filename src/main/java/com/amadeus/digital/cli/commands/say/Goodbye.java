package com.amadeus.digital.cli.commands.say;

import com.amadeus.digital.cli.CommandWithHelp;
import com.amadeus.digital.cli.Main;
import com.amadeus.digital.cli.business.GreetService;
import io.quarkus.arc.log.LoggerName;
import jakarta.inject.Inject;
import org.jboss.logging.Logger;
import picocli.CommandLine;

import java.util.concurrent.Callable;

@CommandLine.Command(name = "goodbye", description = "Say you're leaving")
public class Goodbye extends CommandWithHelp implements Callable<Integer> {

  @CommandLine.Parameters(arity = "1", description = "Name of who to greet")
  public String who;

  @CommandLine.Option(names = {"-t", "--times"}, defaultValue = "1", description = "Should we repeat?")
  public int times;

  @CommandLine.Option(names = {"-f", "--fail"}, description = "Makes the command fail to say Goodbye")
  public boolean fail;

  @CommandLine.Option(names = {"-e", "--exclamation"}, help = true, description = "Just a goodbye message or an " +
          "exclamation?")
  public boolean exclamation;

  @Inject
  @LoggerName(Main.CONSOLE_OUTPUT_LOGGER)
  Logger console;

  @Inject
  GreetService greetService;

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
