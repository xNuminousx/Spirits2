package me.numin.spirits2.commands;

import com.projectkorra.projectkorra.command.PKCommand;
import me.numin.spirits2.Spirits2;
import me.numin.spirits2.inventories.AbilityHelper;
import me.numin.spirits2.listeners.AbilityHelperListener;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Arrays;
import java.util.List;

public class SpiritsCommand extends PKCommand {

    private final List<String> abilityHelperAliases = Arrays.asList("ability", "abilities", "abil", "a");

    public SpiritsCommand() {
        super("spirits", "/bending spirits", "Opens up Spirits guide.", new String[] {"s", "sp", "spirit", "spirits"});
    }

    @Override
    public void execute(CommandSender cmdSender, List<String> list) {
        //TODO: Create commands (ability helper, etc).

        if (!(cmdSender instanceof Player)) {
            cmdSender.sendMessage("You must be a player to perform spirit commands.");
            return;
        }

        if (list.size() < 1) {
            cmdSender.sendMessage(ChatColor.DARK_AQUA + "" + ChatColor.BOLD + "Spirits 2" +
                    ChatColor.DARK_PURPLE + "" + " - " +
                    ChatColor.DARK_AQUA + "" + ChatColor.BOLD + "By: Numin");
            cmdSender.sendMessage("");
            cmdSender.sendMessage(ChatColor.GOLD + "" + ChatColor.BOLD + "Commands:");
            cmdSender.sendMessage(ChatColor.YELLOW + "/b spirits " + ChatColor.GRAY + "Opens this prompt.");
            cmdSender.sendMessage(ChatColor.GRAY + "" + ChatColor.ITALIC + "- Aliases: s, sp, spirit, spirits");
            cmdSender.sendMessage(ChatColor.YELLOW + "/b spirits abil " + ChatColor.GRAY + "Opens an Ability Helper to help with understanding and binding abilities.");
            cmdSender.sendMessage(ChatColor.GRAY + "" + ChatColor.ITALIC + "- Aliases: a, abil, ability, abilities");
            cmdSender.sendMessage("");

            if (cmdSender.hasPermission("bending.command.spirits.admin")) {
                cmdSender.sendMessage(ChatColor.DARK_RED + "" + ChatColor.BOLD + "Admin Commands:");
                cmdSender.sendMessage(ChatColor.RED + "/b config <plugin> <ability> " + ChatColor.GRAY + "Displays config variables for an ability. Do '/b config' for more information.");
                cmdSender.sendMessage(ChatColor.GRAY + "" + ChatColor.ITALIC + "- Aliases: con, config, configs, getconfig");
                cmdSender.sendMessage("");
            }

            cmdSender.sendMessage(ChatColor.GRAY + "" + ChatColor.ITALIC + "Plugin version: " + Spirits2.getInstance().getDescription().getVersion());
        } else if (list.size() == 1) {
            String arg = list.get(0);

            if (abilityHelperAliases.contains(arg)) {
                AbilityHelperListener.abilityHelper = new AbilityHelper((Player)cmdSender);
            }
        }
    }
}
