package club.rigox.bungee.utils;

import club.rigox.bungee.PixelMOTD;
import net.md_5.bungee.config.Configuration;
import net.md_5.bungee.config.ConfigurationProvider;
import net.md_5.bungee.config.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;

import static club.rigox.bungee.utils.Logger.*;

public class FileManager {
    private final PixelMOTD plugin;

    public FileManager (PixelMOTD plugin) {
        this.plugin = plugin;
    }

    /**
     * Creates a config File if it doesn't exists,
     * reloads if specified file exists.
     *
     * @param configName config to create/reload.
     */
    public Configuration loadConfig(String configName) {
        File configFile = new File(plugin.getDataFolder().getPath(), configName + ".yml");

        if (!configFile.exists()) {
            plugin.getDataFolder().mkdirs();
            saveConfig(configName);
            debug(configName + ".yml config was created!");
        }

        Configuration cnf = null;
        try {
            cnf = ConfigurationProvider.getProvider(YamlConfiguration.class).load(configFile);
        } catch (Exception e) {
            error(String.format("A error occurred while loading the settings file. Error: %s", e));
            e.printStackTrace();
        }
        return cnf;
    }


    public void saveConfig(String configName) {
        File folderDir = plugin.getDataFolder();
        File file      = new File(plugin.getDataFolder(), configName + ".yml");

        if (!folderDir.exists()) {
            folderDir.mkdir();
        }

        if (!file.exists()) {
            try (InputStream in = plugin.getResourceAsStream(configName + ".yml")) {
                Files.copy(in, file.toPath());
            } catch (IOException e) {
                warn(String.format("A error occurred while copying the config %s to the plugin data folder. Error: %s", configName, e));
                e.printStackTrace();
            }
        }
    }
}
