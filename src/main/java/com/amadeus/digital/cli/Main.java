package com.amadeus.digital.cli;

import com.amadeus.digital.cli.commands.GitHub;
import com.amadeus.digital.cli.commands.Say;
import com.amadeus.digital.cli.commands.Version;
import io.quarkus.picocli.runtime.annotations.TopCommand;
import picocli.CommandLine;

@TopCommand
@CommandLine.Command(versionProvider = Version.class, scope = CommandLine.ScopeType.INHERIT,
        name = "samplecli",
        headerHeading = "Sample CLI - a CLI to help you%n%n",
        usageHelpAutoWidth = true,
        footer = "%nUse \"samplecli <command> --help\" or \"samplecli <command> -h\" for more information about a given " +
                "command.",
        subcommands = {GitHub.class, Say.class, Version.class})
public class Main extends CommandWithHelp {

  public static final String CONSOLE_OUTPUT_LOGGER = "OUTPUT";

  @CommandLine.Option(names = {"-V", "--version"}, versionHelp = true, description = "display version info")
  boolean versionInfoRequested;
}
