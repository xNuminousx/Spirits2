package me.numin.spirits2.commands;

import com.projectkorra.projectkorra.Element;
import com.projectkorra.projectkorra.ProjectKorra;
import com.projectkorra.projectkorra.ability.Ability;
import com.projectkorra.projectkorra.ability.ComboAbility;
import com.projectkorra.projectkorra.ability.CoreAbility;
import com.projectkorra.projectkorra.command.PKCommand;
import me.numin.spirits2.Spirits2;
import me.numin.spirits2.utils.SpiritElement;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.MemorySection;

import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.regex.Pattern;

public class ConfigCommand extends PKCommand {

    private final List<String> pkAliases = Arrays.asList("projectkorra", "pk", "korra");
    private final List<String> spiritAliases = Arrays.asList("spirits", "sp", "s");

    private CommandSender commandSender;
    private String section = "";

    public ConfigCommand() {
        super("config", "/bending config <Plugin> <Ability>", "Shows the config values for an ability.", new String[] {"con", "config", "configs", "getconfig"});
    }

    @Override
    public void execute(CommandSender cmdSender, List<String> list) {
        if (cmdSender == null || list == null) return;

        this.commandSender = cmdSender;

        if (!commandSender.hasPermission("bending.command.config")) {
            commandSender.sendMessage(ChatColor.RED + "You do not have permission for this command.");
            return;
        }

        if (list.size() <= 1) {
            commandSender.sendMessage(ChatColor.RED + "" + ChatColor.BOLD + "Configuration Command");
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

        if (pkAliases.contains(pluginFrom) || spiritAliases.contains(pluginFrom)) {
            Ability ability = CoreAbility.getAbility(list.get(1));
            fetchConfigValues(pluginFrom, ability);
        } else {
            commandSender.sendMessage(pluginFrom);
            commandSender.sendMessage("Invalid plugin provided.");
        }
    }

    public void fetchConfigValues(String plugin, Ability ability) {
        if (ability == null) {
            commandSender.sendMessage("That is not a valid ProjectKorra or Spirits ability.");
            return;
        }

        Element element = ability.getElement();
        Object value;
        String configPath;
        String elementName;
        Set<String> configKeys;

        if (pkAliases.contains(plugin)) {
            elementName = element instanceof Element.SubElement ?
                    ((Element.SubElement)element).getParentElement().getName() :
                    element.getName();
            configPath = "Abilities." + elementName + "." + ability.getName();
            configKeys = ProjectKorra.plugin.getConfig().getKeys(true);
        } else {
            elementName = getSpiritElementString(ability.getElement());
            configPath = ability instanceof ComboAbility ?
                    "Abilities." + elementName + ".Combo." + ability.getName() :
                    "Abilities." + elementName + "." + ability.getName();
            configKeys = Spirits2.getInstance().getConfig().getKeys(true);
        }

        commandSender.sendMessage(ability.getElement().getColor() + "" + ChatColor.UNDERLINE + "" + ChatColor.BOLD + ability.getName());
        commandSender.sendMessage("");

        for (String key : configKeys) {
            if (key.contains(configPath)) {
                String[] parts = key.split(Pattern.quote("."));

                String label = parts[parts.length - 1];
                value = pkAliases.contains(plugin) ?
                        ProjectKorra.plugin.getConfig().get(key) :
                        Spirits2.getInstance().getConfig().get(key);

                if (value == null ||
                        value instanceof MemorySection ||
                        label.equalsIgnoreCase("Description") ||
                        label.equalsIgnoreCase("Instructions"))
                    continue;

                if (parts.length >= 5) {
                    String sectionName = parts[parts.length - 2];

                    if (!section.equals(sectionName) && !sectionName.equals(ability.getName())) {
                        section = sectionName;
                        commandSender.sendMessage("");
                        commandSender.sendMessage(ability.getElement().getColor() + "" + ChatColor.BOLD + sectionName);
                    }
                }
                commandSender.sendMessage(ability.getElement().getColor() + label + ": " + value.toString());
            }
        }
    }

    private String getSpiritElementString(Element element) {
        if (element.equals(SpiritElement.SPIRIT))
            return "Neutral";
        else if (element.equals(SpiritElement.LIGHT_SPIRIT))
            return "LightSpirit";
        else if (element.equals(SpiritElement.DARK_SPIRIT))
            return "DarkSpirit";
        else return null;
    }
}
