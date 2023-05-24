package com.amadeus.digital.cli.commands;

import java.util.Arrays;
import java.util.concurrent.Callable;

import jakarta.inject.Inject;

import org.jboss.logging.Logger;

import com.amadeus.digital.cli.Configuration;
import com.amadeus.digital.cli.Main;

import io.quarkus.arc.log.LoggerName;
import picocli.CommandLine;

@CommandLine.Command(name = "version", description = "Displays the version of the CLI")
public class Version implements CommandLine.IVersionProvider, Callable<Integer> {

  Configuration configuration;
  @LoggerName(Main.CONSOLE_OUTPUT_LOGGER)
  Logger console;

  @Inject
  public Version(Configuration configuration, Logger console) {
    this.configuration = configuration;
    this.console = console;

  }

  @Override
  public String[] getVersion() throws Exception {
    return new String[]{"Version: " + configuration.version};
  }

  @Override
  public Integer call() throws Exception {
    console.info(String.join("\n", Arrays.asList(getVersion())));
    return CommandLine.ExitCode.OK;
  }
}
