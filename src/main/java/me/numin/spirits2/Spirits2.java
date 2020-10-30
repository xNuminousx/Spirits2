package me.numin.spirits2;

import com.projectkorra.projectkorra.ability.CoreAbility;
import me.numin.spirits2.commands.ConfigCommand;
import me.numin.spirits2.commands.SpiritsCommand;
import me.numin.spirits2.configuration.Configuration;
import me.numin.spirits2.listeners.AbilityHelperListener;
import me.numin.spirits2.listeners.AbilityListener;
import me.numin.spirits2.listeners.PassiveListener;
import me.numin.spirits2.listeners.ProjectKorraListener;
import me.numin.spirits2.utils.SpiritElement;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Stream;

public final class Spirits2 extends JavaPlugin {

    private static Spirits2 plugin;
    private Logger logger;

    @Override
    public void onEnable() {
        plugin = this;
        logger = getLogger();

        new Configuration(this);
        new SpiritElement();

        registerCommands();
        registerListeners();

        CoreAbility.registerPluginAbilities(plugin, "me.numin.spirits2.abilities");

        logger.info("Successfully enabled " + plugin.getName() + " " + plugin.getDescription().getVersion());
    }

    @Override
    public void onDisable() {
        logger.info("Successfully disabled " + plugin.getName() + " " + plugin.getDescription().getVersion());
    }

    public static Spirits2 getInstance() {
        return plugin;
    }

    public List<CoreAbility> getSpiritAbilities() {
        return CoreAbility.getAbilitiesByElement(SpiritElement.SPIRIT);
    }

    public List<CoreAbility> getDarkSpiritAbilities() {
        return CoreAbility.getAbilitiesByElement(SpiritElement.DARK_SPIRIT);
    }

    public List<CoreAbility> getLightSpiritAbilities() {
        return CoreAbility.getAbilitiesByElement(SpiritElement.LIGHT_SPIRIT);
    }

    public List<CoreAbility> getPluginAbilities() {
        List<CoreAbility> allAbilities = new ArrayList<>();
        Stream.of(getSpiritAbilities(), getLightSpiritAbilities(), getDarkSpiritAbilities()).forEach(allAbilities::addAll);
        return allAbilities;
    }

    public void registerCommands() {
        new ConfigCommand();
        new SpiritsCommand();
    }

    public void registerListeners() {
        getServer().getPluginManager().registerEvents(new AbilityListener(), plugin);
        getServer().getPluginManager().registerEvents(new AbilityHelperListener(), plugin);
        getServer().getPluginManager().registerEvents(new PassiveListener(), plugin);
        getServer().getPluginManager().registerEvents(new ProjectKorraListener(), plugin);
    }
}
