package me.numin.spirits2;

import com.projectkorra.projectkorra.ability.CoreAbility;
import me.numin.spirits2.configuration.Configuration;
import me.numin.spirits2.listeners.AbilityListener;
import me.numin.spirits2.listeners.PassiveListener;
import me.numin.spirits2.listeners.ProjectKorraListener;
import me.numin.spirits2.utils.SpiritElement;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Logger;

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

    public void registerCommands() {
        //TODO: Implement commands.
    }

    public void registerListeners() {
        getServer().getPluginManager().registerEvents(new AbilityListener(), plugin);
        getServer().getPluginManager().registerEvents(new PassiveListener(), plugin);
        getServer().getPluginManager().registerEvents(new ProjectKorraListener(), plugin);
    }
}
