package me.numin.spirits2.commands;

import com.projectkorra.projectkorra.ability.Ability;
import com.projectkorra.projectkorra.ability.CoreAbility;
import com.projectkorra.projectkorra.command.PKCommand;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

import java.util.Arrays;
import java.util.List;

public class ConfigCommand extends PKCommand {

    private List<String> pkAliases = Arrays.asList("projectkorra", "pk", "korra");
    private List<String> spiritAliases = Arrays.asList("spirits", "sp", "s");

    public ConfigCommand() {
        super("config", "/bending config <Plugin> <Ability>", "Shows the config values for an ability.", new String[] {"con", "config", "configs", "getconfig"});
    }

    @Override
    public void execute(CommandSender commandSender, List<String> list) {
        if (commandSender == null || list == null) return;

        if (!commandSender.hasPermission("bending.command.config")) {
            commandSender.sendMessage(ChatColor.RED + "You do not have permission for this command.");
            return;
        }

        if (list.size() <= 1) {
            commandSender.sendMessage(ChatColor.RED + "" + ChatColor.BOLD + "Invalid command format!");
            commandSender.sendMessage("Format: /b config <plugin> <ability>");
            commandSender.sendMessage("");
            commandSender.sendMessage(ChatColor.GREEN + "" + ChatColor.BOLD + "Plugins & Aliases:");
            commandSender.sendMessage("ProjectKorra: projectkorra, pk, korra");
            commandSender.sendMessage("Spirits: spirits, sp, s");
            return;
        } else if (list.get(1) == null) {
            commandSender.sendMessage("That's not a valid ProjectKorra or Spirits ability.");
            return;
        }

        String pluginFrom = list.get(0);

        if (pkAliases.contains(pluginFrom)) {
            Ability ability = CoreAbility.getAbility(list.get(1));
            new FetchPKConfigValues(commandSender, ability);

        } else if (spiritAliases.contains(pluginFrom)) {
            Ability ability = CoreAbility.getAbility(list.get(1));
            new FetchSpiritsConfigValues(commandSender, ability);
        }
    }
}
