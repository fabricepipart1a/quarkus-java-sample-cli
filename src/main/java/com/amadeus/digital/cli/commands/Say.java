package com.amadeus.digital.cli.commands;

import com.amadeus.digital.cli.CommandWithHelp;
import com.amadeus.digital.cli.commands.say.Hello;
import com.amadeus.digital.cli.commands.say.Goodbye;

import picocli.CommandLine;

@CommandLine.Command(name = "say",
        description = "Just some examples of subcommands",
        subcommands = {Hello.class, Goodbye.class})
public class Say extends CommandWithHelp {
}
