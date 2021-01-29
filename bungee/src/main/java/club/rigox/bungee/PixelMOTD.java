package club.rigox.bungee;

import club.rigox.bungee.commands.CommandUtils;
import club.rigox.bungee.commands.PixelCommand;
import club.rigox.bungee.commands.WhitelistCommand;
import club.rigox.bungee.listeners.WhitelistEvent;
import club.rigox.bungee.utils.Converter;
import club.rigox.bungee.utils.FileManager;
import club.rigox.bungee.utils.Placeholders;
import co.aikar.commands.BungeeCommandManager;
import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.config.Configuration;

import static club.rigox.bungee.utils.Logger.debug;

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

        debug("Configs has been loaded!");
    }

    public void registerCommands() {
        BungeeCommandManager manager = new BungeeCommandManager(this);

        manager.registerCommand(new PixelCommand(this));
        manager.registerCommand(new WhitelistCommand(this));
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
