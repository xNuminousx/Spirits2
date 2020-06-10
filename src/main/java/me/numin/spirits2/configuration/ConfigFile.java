package me.numin.spirits2.configuration;

import java.io.File;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import me.numin.spirits2.Spirits2;

public class ConfigFile {

    private Spirits2 plugin;

    private File file;
    public FileConfiguration config;

    public ConfigFile(String name) {
        this(new File(name + ".yml"));
    }

    public ConfigFile(File file) {
        this.plugin = Spirits2.getInstance();
        this.file = new File(plugin.getDataFolder() + File.separator + file);
        this.config = YamlConfiguration.loadConfiguration(this.file);
        reloadConfig();
    }

    public void createConfig() {
        if (!file.getParentFile().exists()) {
            try {
                file.getParentFile().mkdir();
                plugin.getLogger().info("Generating new directory for " + file.getName());
            } catch (Exception e) {
                plugin.getLogger().info("Failed to generate directory");
                e.printStackTrace();
            }
        }

        if (!file.exists()) {
            try {
                file.createNewFile();
                plugin.getLogger().info("Generating new " + file.getName());
            } catch (Exception e) {
                plugin.getLogger().info("Failed to generate " + file.getName());
                e.printStackTrace();
            }
        }
    }

    public FileConfiguration getConfig() {
        return config;
    }

    public void reloadConfig() {
        createConfig();
        try {
            config.load(file);
            plugin.getLogger().info("Loading configuration");
        } catch (Exception e) {
            plugin.getLogger().info("Failed to reload " + file.getName());
            e.printStackTrace();
        }
    }

    public void saveConfig() {
        try {
            config.options().copyDefaults(true);
            config.save(file);
            plugin.getLogger().info("Successfully saved configuration");
        } catch (Exception e) {
            plugin.getLogger().info("Failed to save configuration");
            e.printStackTrace();
        }
    }
}