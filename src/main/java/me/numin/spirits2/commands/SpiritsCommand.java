package me.numin.spirits2.commands;

import com.projectkorra.projectkorra.command.PKCommand;
import me.numin.spirits2.Spirits2;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

import java.util.List;

public class SpiritsCommand extends PKCommand {

    public SpiritsCommand() {
        super("spirits", "/bending spirits", "Opens up Spirits guide.", new String[] {"s", "sp", "spirit", "spirits"});
    }

    @Override
    public void execute(CommandSender cmdSender, List<String> list) {
        //TODO: Create commands (ability helper, etc).
        cmdSender.sendMessage(ChatColor.DARK_AQUA + "" + ChatColor.BOLD + "Spirits 2" +
                ChatColor.DARK_PURPLE + "" + " - " +
                ChatColor.DARK_AQUA + "" + ChatColor.BOLD + "By: Numin");
        cmdSender.sendMessage("");
        cmdSender.sendMessage(ChatColor.GOLD + "" + ChatColor.BOLD + "Commands:");
        cmdSender.sendMessage(ChatColor.YELLOW + "/b spirits " + ChatColor.RESET + "Opens this prompt.");
        cmdSender.sendMessage("");

        if (cmdSender.hasPermission("bending.command.spirits.admin")) {
            cmdSender.sendMessage(ChatColor.DARK_RED + "" + ChatColor.BOLD + "Admin Commands:");
            cmdSender.sendMessage(ChatColor.RED + "/b config <plugin> <ability> " + ChatColor.RESET + "Displays config variables for an ability.");
            cmdSender.sendMessage("");
        }

        cmdSender.sendMessage(ChatColor.GRAY + "" + ChatColor.ITALIC + "Plugin version: " + Spirits2.getInstance().getDescription().getVersion());
    }
}
