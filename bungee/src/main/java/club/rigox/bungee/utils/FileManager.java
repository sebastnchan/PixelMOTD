package club.rigox.bungee.utils;

import club.rigox.bungee.PixelMOTD;
import net.md_5.bungee.config.Configuration;
import net.md_5.bungee.config.ConfigurationProvider;
import net.md_5.bungee.config.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;

import static club.rigox.bungee.utils.Logger.info;
import static club.rigox.bungee.utils.Logger.warn;

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
            saveConfig(configName);
        }

        Configuration cnf = null;
        try {
            cnf = ConfigurationProvider.getProvider(YamlConfiguration.class).load(configFile);
        } catch (Exception e) {
            warn(String.format("A error occurred while loading the settings file. Error: %s", e));
            e.printStackTrace();
        }

        info(String.format("&7File &e%s.yml &7has been loaded", configName));
        return cnf;
    }

    public void saveConfig(String configName) {
        File folderDir = plugin.getDataFolder();
        File file = new File(folderDir, configName + ".yml");

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

    public Configuration reloadConfig(String file, Configuration configuration) {

        File path = new File(plugin.getDataFolder(), file + ".yml");
        try {
            ConfigurationProvider.getProvider(YamlConfiguration.class).save(configuration, path);

            return ConfigurationProvider.getProvider(YamlConfiguration.class).load(
                    new File(plugin.getDataFolder(), file + ".yml"));
        } catch (IOException e) {
            warn(String.format("A error occurred while copying the config %s.yml to the plugin data folder. Error: %s", file, e));
            e.printStackTrace();
        }

        return null;
    }

    public void createFolders(String path) {
        File folderDir = new File(plugin.getDataFolder(), path);

        if (!folderDir.exists()) {
            folderDir.mkdir();
        }
    }

    public static String getMessageString(String path) {
        return PixelMOTD.instance.getMessagesConfig().getString(path);
    }
}
