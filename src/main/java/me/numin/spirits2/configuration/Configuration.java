package me.numin.spirits2.configuration;

import me.numin.spirits2.Spirits2;
import org.bukkit.configuration.file.FileConfiguration;

public class Configuration {

    private static ConfigFile main;
    static Spirits2 plugin;

    public Configuration(Spirits2 plugin) {
        Configuration.plugin = plugin;
        main = new ConfigFile("config");
        loadConfig();
    }

    public static FileConfiguration getConfig() {
        return main.getConfig();
    }

    public void loadConfig() {
        FileConfiguration config = plugin.getConfig();

        //config

        config.options().copyDefaults(true);
        plugin.saveConfig();
    }
}
