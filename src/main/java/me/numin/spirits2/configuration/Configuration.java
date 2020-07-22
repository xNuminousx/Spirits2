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

        config.addDefault("Abilities.Neutral.Vanish.Description", "Spirits have the unique ability to disappear and reappear somewhere nearby. This ability allows you to dissolve yourself and teleport where you're looking.");
        config.addDefault("Abilities.Neutral.Vanish.Instructions", "Hold shift until you disappear. Release shift to teleport or continue holding to stay invisible for a short duration.");
        config.addDefault("Abilities.Neutral.Vanish.Enabled", true);
        config.addDefault("Abilities.Neutral.Vanish.Cooldown", 5000);
        config.addDefault("Abilities.Neutral.Vanish.Duration", 7000);
        config.addDefault("Abilities.Neutral.Vanish.Range", 10);
        config.addDefault("Abilities.Neutral.Vanish.ChargeTime", 1500);
        config.addDefault("Abilities.Neutral.Vanish.ParticleDensity", 5);
        config.addDefault("Abilities.Neutral.Vanish.Extinguish", true);

        config.options().copyDefaults(true);
        plugin.saveConfig();
    }
}
