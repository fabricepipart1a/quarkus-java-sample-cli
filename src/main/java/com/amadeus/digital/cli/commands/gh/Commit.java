package com.amadeus.digital.cli.commands.gh;

import com.amadeus.digital.cli.CommandWithHelp;
import com.amadeus.digital.cli.Configuration;
import com.amadeus.digital.cli.Main;
import com.amadeus.digital.cli.client.GitHubClient;
import com.amadeus.digital.cli.client.GitHubCommit;
import io.quarkus.arc.log.LoggerName;
import jakarta.inject.Inject;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.jboss.logging.Logger;
import picocli.CommandLine;

import java.util.List;
import java.util.concurrent.Callable;

@CommandLine.Command(name = "commit", description = "Lists commits")
public class Commit extends CommandWithHelp implements Callable<Integer> {

  @CommandLine.Option(names = {"-o", "--owner"}, description = "Owner of the GitHub repository")
  public String owner;

  @CommandLine.Option(names = {"-r", "--repository"}, description = "Name of the GitHub repository")
  public String repository;

  private final GitHubClient client;
  private final Logger log;
  private final Logger console;
  private final Configuration configuration;

  @Inject
  public Commit(Logger log, @LoggerName(Main.CONSOLE_OUTPUT_LOGGER) Logger console, @RestClient GitHubClient client,
                Configuration configuration) {
    this.log = log;
    this.console = console;
    this.client = client;
    this.configuration = configuration;
  }

  @Override
  public Integer call() {
    String ghCommitApiOwner = configuration.ghApiOwner;
    if (owner != null) {
      ghCommitApiOwner = owner;
    }
    String ghCommitApiRepo = configuration.ghApiRepo;
    if (repository != null) {
      ghCommitApiRepo = repository;
    }
    List<GitHubCommit> commits = client.commits(ghCommitApiOwner, ghCommitApiRepo);
    if (commits == null || commits.isEmpty()) {
      log.error("Could not find any commit");
      return CommandLine.ExitCode.SOFTWARE;
    }
    for (GitHubCommit commit : commits) {
      console.info(commit.sha);
    }
    return CommandLine.ExitCode.OK;
  }
}
