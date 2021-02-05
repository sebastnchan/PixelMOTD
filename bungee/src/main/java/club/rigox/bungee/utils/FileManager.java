package club.rigox.bungee.utils;

import club.rigox.bungee.PixelMOTD;
import club.rigox.bungee.enums.ConfigType;
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
        }

        Configuration cnf = null;
        try {
            cnf = ConfigurationProvider.getProvider(YamlConfiguration.class).load(configFile);
        } catch (Exception e) {
            warn(String.format("A error occurred while loading the settings file. Error: %s", e));
            e.printStackTrace();
        }
        return cnf;
    }

    public void saveConfig(String configName) {
        File folderDir = plugin.getDataFolder();
        File file = new File(plugin.getDataFolder(), configName + ".yml");

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

    public static String getMessageString(String path) {
        return PixelMOTD.instance.getMessagesConfig().getString(path);
    }

    public void reloadConfig(ConfigType type) {
        try {
            switch (type) {
                case PLAYERS:
                    File players = new File(plugin.getDataFolder(), "players.yml");

                    ConfigurationProvider.getProvider(YamlConfiguration.class).save(plugin.playersConfig, players);
                    plugin.playersConfig = ConfigurationProvider.getProvider(YamlConfiguration.class).load(players);
                    return;
                case MOTD:
                    File motd = new File(plugin.getDataFolder(), "motd.yml");

                    ConfigurationProvider.getProvider(YamlConfiguration.class).save(plugin.motdConfig, motd);
                    plugin.motdConfig = ConfigurationProvider.getProvider(YamlConfiguration.class).load(motd);
                    return;

                case MESSAGES:
                    File message = new File(plugin.getDataFolder(), "messages.yml");

                    ConfigurationProvider.getProvider(YamlConfiguration.class).save(plugin.messagesConfig, message);
                    plugin.messagesConfig = ConfigurationProvider.getProvider(YamlConfiguration.class).load(message);
                    return;

                case COMMAND:
                    File command = new File(plugin.getDataFolder(), "command.yml");

                    ConfigurationProvider.getProvider(YamlConfiguration.class).save(plugin.commandFile, command);
                    plugin.commandFile = ConfigurationProvider.getProvider(YamlConfiguration.class).load(command);
                    return;

                case TIMER_MOTD:
                    File timer = new File(plugin.getDataFolder(), "timer-motd.yml");

                    ConfigurationProvider.getProvider(YamlConfiguration.class).save(plugin.timerMotdFile, timer);
                    plugin.timerMotdFile = ConfigurationProvider.getProvider(YamlConfiguration.class).load(timer);
                    return;

                case MODULES:
                    File modules = new File(plugin.getDataFolder(), "modules.yml");

                    ConfigurationProvider.getProvider(YamlConfiguration.class).save(plugin.modulesFile, modules);
                    plugin.modulesFile = ConfigurationProvider.getProvider(YamlConfiguration.class).load(modules);
                    return;

                case SETTINGS:
                    File settings = new File(plugin.getDataFolder(), "settings.yml");

                    ConfigurationProvider.getProvider(YamlConfiguration.class).save(plugin.settingsFile, settings);
                    plugin.settingsFile = ConfigurationProvider.getProvider(YamlConfiguration.class).load(settings);
                    return;

                case WHITELIST_MOTD:
                    File whitelistMotd = new File(plugin.getDataFolder(), "whitelist-motd.yml");

                    ConfigurationProvider.getProvider(YamlConfiguration.class).save(plugin.whitelistMotdFile, whitelistMotd);
                    plugin.whitelistMotdFile = ConfigurationProvider.getProvider(YamlConfiguration.class).load(whitelistMotd);
                    return;

                case NORMAL_MOTD:
                    File normalMotd = new File(plugin.getDataFolder(), "normal-motd.yml");

                    ConfigurationProvider.getProvider(YamlConfiguration.class).save(plugin.normalMotdFile, normalMotd);
                    plugin.normalMotdFile = ConfigurationProvider.getProvider(YamlConfiguration.class).load(normalMotd);
                    return;

                default:
                    error("Something went wrong. Please notify it to the plugin author.");
            }
        } catch (IOException e) {
            warn(String.format("A error occurred while copying the config %s to the plugin data folder. Error: %s", type, e));
            e.printStackTrace();

        }
    }
}
