package me.numin.spirits2.commands;

import com.projectkorra.projectkorra.command.PKCommand;
import org.bukkit.command.CommandSender;

import java.util.List;

public class SpiritsCommand extends PKCommand {

    public SpiritsCommand() {
        super("spirits", "/bending spirits", "Opens up Spirits guide.", new String[] {"s", "sp", "spirit", "spirits"});
    }

    @Override
    public void execute(CommandSender commandSender, List<String> list) {
        //TODO: Create commands.
        commandSender.sendMessage("This is the spirits command.");
    }
}
