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

        config.addDefault("Abilities.Neutral.Dash.Description", "With short bursts of energy, you're able to quickly dash from place to place.");
        config.addDefault("Abilities.Neutral.Dash.Instructions", "Left-click to dash in the direction you are looking.");
        config.addDefault("Abilities.Neutral.Dash.Enabled", true);
        config.addDefault("Abilities.Neutral.Dash.Cooldown", 500);
        config.addDefault("Abilities.Neutral.Dash.Push", 1);

        config.addDefault("Abilities.Neutral.Vanish.Description", "Spirits have the unique ability to disappear and reappear somewhere nearby. This ability allows you to dissolve yourself and teleport where you're looking.");
        config.addDefault("Abilities.Neutral.Vanish.Instructions", "Hold shift until you disappear. Release shift to teleport or continue holding to stay invisible for a short duration.");
        config.addDefault("Abilities.Neutral.Vanish.Enabled", true);
        config.addDefault("Abilities.Neutral.Vanish.Cooldown", 5000);
        config.addDefault("Abilities.Neutral.Vanish.Duration", 7000);
        config.addDefault("Abilities.Neutral.Vanish.Range", 10);
        config.addDefault("Abilities.Neutral.Vanish.ChargeTime", 1500);
        config.addDefault("Abilities.Neutral.Vanish.ParticleDensity", 5);
        config.addDefault("Abilities.Neutral.Vanish.Extinguish", true);

        config.addDefault("Abilities.Neutral.Combo.Phase.Description", "Dissolve your essence entirely to achieve invisibility and flight for a short duration of time.");
        config.addDefault("Abilities.Neutral.Combo.Phase.Instructions", "Left-click with Vanish twice.");
        config.addDefault("Abilities.Neutral.Combo.Phase.Enabled", true);
        config.addDefault("Abilities.Neutral.Combo.Phase.Cooldown", 5000);
        config.addDefault("Abilities.Neutral.Combo.Phase.CooldownMultiplier", 1.5);
        config.addDefault("Abilities.Neutral.Combo.Phase.Duration", 2000);
        config.addDefault("Abilities.Neutral.Combo.Phase.MinimumHealth", 3);

        config.options().copyDefaults(true);
        plugin.saveConfig();
    }
}
