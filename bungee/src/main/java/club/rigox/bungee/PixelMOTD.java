package club.rigox.bungee;

import club.rigox.bungee.commands.CommandUtils;
import club.rigox.bungee.commands.PixelCommand;
import club.rigox.bungee.commands.WhitelistCommand;
import club.rigox.bungee.enums.ConfigType;
import club.rigox.bungee.listeners.WhitelistEvent;
import club.rigox.bungee.utils.Converter;
import club.rigox.bungee.utils.FileManager;
import club.rigox.bungee.utils.Placeholders;
import co.aikar.commands.BungeeCommandManager;
import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.config.Configuration;

import java.io.IOException;
import java.util.Locale;

import static club.rigox.bungee.utils.Logger.debug;
import static club.rigox.bungee.utils.Logger.warn;

public final class PixelMOTD extends Plugin {
    public static PixelMOTD instance;

    private Converter converter;

    private FileManager manager;

    private Placeholders placeholders;

    private CommandUtils cmdUtils;

    public Configuration commandFile;
    public Configuration editFile;
    public Configuration modulesFile;
    public Configuration normalMotdFile;
    public Configuration settingsFile;
    public Configuration timerMotdFile;
    public Configuration whitelistMotdFile;

    // NEW FILES
    public Configuration messagesConfig;

    @Override
    public void onEnable() {
        instance     = this;

        manager      = new FileManager(this);
        cmdUtils     = new CommandUtils(this);

        loadConfigs();
        registerListeners();
        registerCommands();

        converter    = new Converter();
        placeholders = new Placeholders(this);
    }

    @Override
    public void onDisable() {

    }

    private void registerListeners() {
        new WhitelistEvent(this);
    }

    public void loadConfigs() {
        commandFile         = manager.loadConfig("command");
        editFile            = manager.loadConfig("edit");
        modulesFile         = manager.loadConfig("modules");
        normalMotdFile      = manager.loadConfig("normal-motd");
        settingsFile        = manager.loadConfig("settings");
        timerMotdFile       = manager.loadConfig("timer-motd");
        whitelistMotdFile   = manager.loadConfig("whitelist-motd");

        messagesConfig      = manager.loadConfig("messages");

        debug("Configs has been loaded!");
    }

    public void reloadConfigs() {
        manager.reloadConfig(ConfigType.EDITABLE);
        manager.reloadConfig(ConfigType.COMMAND);
        manager.reloadConfig(ConfigType.MODULES);
        manager.reloadConfig(ConfigType.NORMAL_MOTD);
        manager.reloadConfig(ConfigType.SETTINGS);
        manager.reloadConfig(ConfigType.TIMER_MOTD);
        manager.reloadConfig(ConfigType.WHITELIST_MOTD);

        manager.reloadConfig(ConfigType.MESSAGES);
    }

    public void registerCommands() {
        BungeeCommandManager manager = new BungeeCommandManager(this);

        manager.enableUnstableAPI("brigadier");

        manager.registerCommand(new PixelCommand(this));
        manager.registerCommand(new WhitelistCommand(this));

        manager.addSupportedLanguage(Locale.ENGLISH);

        try {
            manager.getLocales().loadYamlLanguageFile("messages.yml", Locale.ENGLISH);
        } catch (IOException e) {
            warn(String.format("A error occurred while copying the config messages.yml to the plugin data folder. Error: %s", e));
            e.printStackTrace();
        }

    }

    public Configuration getEditableFile() {
        return editFile;
    }

    public Configuration getCommandFile() {
        return commandFile;
    }

    public Configuration getModulesFile() {
        return modulesFile;
    }

    public Configuration getNormalMotdFile() {
        return normalMotdFile;
    }

    public Configuration getSettingsFile() {
        return settingsFile;
    }

    public Configuration getTimerMotdFile() {
        return timerMotdFile;
    }

    public Configuration getWhitelistMotdFile() {
        return whitelistMotdFile;
    }

    public Configuration getMessagesConfig() {
        return messagesConfig;
    }

    public Converter getConverter() {
        return converter;
    }

    public Placeholders getPlaceholders() {
        return placeholders;
    }

    public FileManager getManager() {
        return manager;
    }

    public CommandUtils getCmdUtils() {
        return cmdUtils;
    }
}
