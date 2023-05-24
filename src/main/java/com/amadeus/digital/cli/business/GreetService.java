package com.amadeus.digital.cli.business;

import com.amadeus.digital.cli.Main;
import io.quarkus.arc.log.LoggerName;
import org.jboss.logging.Logger;

import jakarta.inject.Inject;
import jakarta.inject.Singleton;

@Singleton
public class GreetService {

  @Inject
  Logger log;

  @LoggerName(Main.CONSOLE_OUTPUT_LOGGER)
  Logger console;


  public void sayHello(String who, int times, boolean exclamation) {
    say("Hello", who, times, exclamation);
  }

  public void sayGoodbye(String who, int times, boolean exclamation) {
    say("Goodbye", who, times, exclamation);
  }

  private void say(String toSay, String who, int times, boolean exclamation) {
    log.debug("Entering GreetService.greet");
    StringBuilder message = new StringBuilder(toSay).append(" ");
    if (who != null) {
      message.append(who);
    }
    if (exclamation) {
      message.append(" !");
    }
    for (int i = 0; i < times; i++) {
      console.info(message.toString());
    }
  }

}