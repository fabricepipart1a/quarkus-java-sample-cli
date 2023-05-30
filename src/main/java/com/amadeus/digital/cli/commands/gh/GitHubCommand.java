package com.amadeus.digital.cli.commands.gh;

import com.amadeus.digital.cli.CommandWithHelp;
import com.amadeus.digital.cli.Configuration;
import com.amadeus.digital.cli.client.GitHubClient;
import org.jboss.logging.Logger;
import picocli.CommandLine;

public class GitHubCommand extends CommandWithHelp {

  @CommandLine.Option(names = {"-o", "--owner"}, description = "Owner of the GitHub repository where the branches are")
  public String owner;

  @CommandLine.Option(names = {"-r", "--repository"}, description = "Name of the GitHub repository where to look for " +
          "the branches")
  public String repository;

  protected GitHubClient client;
  protected Logger log;
  protected Logger console;
  protected Configuration configuration;
}
