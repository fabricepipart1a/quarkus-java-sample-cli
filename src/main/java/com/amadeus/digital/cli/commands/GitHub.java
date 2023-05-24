package com.amadeus.digital.cli.commands;

import com.amadeus.digital.cli.CommandWithHelp;
import com.amadeus.digital.cli.commands.gh.Branch;
import com.amadeus.digital.cli.commands.gh.Commit;
import picocli.CommandLine;

@CommandLine.Command(name = "github", description = "Interacts with GitHub",
        subcommands = {Commit.class, Branch.class})
public class GitHub extends CommandWithHelp {
}
