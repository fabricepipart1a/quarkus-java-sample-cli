package com.amadeus.digital.cli;

import picocli.CommandLine;

public class CommandWithHelp {

  @CommandLine.Option(names = {"-h", "--help"}, usageHelp = true, hidden = true,
          description = "display this help message")
  boolean usageHelpRequested;
}
