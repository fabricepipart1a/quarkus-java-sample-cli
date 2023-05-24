package com.amadeus.digital.cli.commands.gh;

import com.amadeus.digital.cli.CommandWithHelp;
import com.amadeus.digital.cli.Configuration;
import com.amadeus.digital.cli.Main;
import com.amadeus.digital.cli.client.GitHubBranch;
import com.amadeus.digital.cli.client.GitHubClient;
import io.quarkus.arc.log.LoggerName;
import jakarta.inject.Inject;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.jboss.logging.Logger;
import picocli.CommandLine;

import java.util.List;
import java.util.concurrent.Callable;

@CommandLine.Command(name = "branch", description = "Lists branches of the repository in conf")
public class Branch extends CommandWithHelp implements Callable<Integer> {

  @CommandLine.Option(names = {"-o", "--owner"}, description = "Owner of the GitHub repository where the branches are")
  public String owner;

  @CommandLine.Option(names = {"-r", "--repository"}, description = "Name of the GitHub repository where to look for " +
          "the branches")
  public String repository;

  private final GitHubClient client;
  private final Logger log;
  private final Logger console;
  private final Configuration configuration;

  @Inject
  public Branch(Logger log, @LoggerName(Main.CONSOLE_OUTPUT_LOGGER) Logger console, @RestClient GitHubClient client,
                Configuration configuration) {
    this.log = log;
    this.console = console;
    this.client = client;
    this.configuration = configuration;
  }

  @Override
  public Integer call() {
    String ghBranchApiOwner = configuration.ghApiOwner;
    if (owner != null) {
      ghBranchApiOwner = owner;
    }
    String ghBranchApiRepo = configuration.ghApiRepo;
    if (repository != null) {
      ghBranchApiRepo = repository;
    }
    List<GitHubBranch> branches = client.branches(ghBranchApiOwner, ghBranchApiRepo);
    if (branches == null || branches.isEmpty()) {
      log.error("Could not find any branch");
      return CommandLine.ExitCode.SOFTWARE;
    }
    for (GitHubBranch branch : branches) {
      console.info(branch.name);
    }
    return CommandLine.ExitCode.OK;
  }
}
