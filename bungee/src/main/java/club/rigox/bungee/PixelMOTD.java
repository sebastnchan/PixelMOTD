package club.rigox.bungee;

import club.rigox.bungee.commands.subcommands.BlacklistCommand;
import club.rigox.bungee.commands.CommandUtils;
import club.rigox.bungee.commands.PixelCommand;
import club.rigox.bungee.commands.subcommands.WhitelistCommand;
import club.rigox.bungee.enums.ConfigType;
import club.rigox.bungee.listeners.MotdEvent;
import club.rigox.bungee.listeners.WhitelistEvent;
import club.rigox.bungee.utils.*;
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

    private Motd motdUtils;

    public Configuration commandFile;
    public Configuration editFile;
    public Configuration modulesFile;
    public Configuration normalMotdFile;
    public Configuration settingsFile;
    public Configuration timerMotdFile;
    public Configuration whitelistMotdFile;

    // NEW FILES
    public Configuration messagesConfig;
    public Configuration playersConfig;
    public Configuration motdConfig;

    @Override
    public void onEnable() {
        instance     = this;

        manager      = new FileManager(this);
        cmdUtils     = new CommandUtils(this);
        motdUtils    = new Motd(this);

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
        new MotdEvent(this);
    }

    public void loadConfigs() {
//        commandFile         = manager.loadConfig("command");
//        modulesFile         = manager.loadConfig("modules");
//        normalMotdFile      = manager.loadConfig("normal-motd");
//        settingsFile        = manager.loadConfig("settings");
//        timerMotdFile       = manager.loadConfig("timer-motd");
//        whitelistMotdFile   = manager.loadConfig("whitelist-motd");

        messagesConfig      = manager.loadConfig("messages");
        playersConfig       = manager.loadConfig("players");
        motdConfig          = manager.loadConfig("motd");

        debug("Configs has been loaded!");
    }

    public void reloadConfigs() {
        manager.reloadConfig(ConfigType.COMMAND);
        manager.reloadConfig(ConfigType.MODULES);
        manager.reloadConfig(ConfigType.NORMAL_MOTD);
        manager.reloadConfig(ConfigType.SETTINGS);
        manager.reloadConfig(ConfigType.TIMER_MOTD);
        manager.reloadConfig(ConfigType.WHITELIST_MOTD);

        manager.reloadConfig(ConfigType.MESSAGES);
        manager.reloadConfig(ConfigType.PLAYERS);
        manager.reloadConfig(ConfigType.MOTD);
    }

    public void registerCommands() {
        BungeeCommandManager manager = new BungeeCommandManager(this);

        manager.enableUnstableAPI("brigadier");

        manager.registerCommand(new PixelCommand(this));
        manager.registerCommand(new WhitelistCommand(this));
        manager.registerCommand(new BlacklistCommand(this));

//        manager.addSupportedLanguage(Locale.ENGLISH);
//
//        try {
//        manager.getLocales().loadYamlLanguageFile("messages.yml", Locale.ENGLISH);
//        } catch (IOException e) {
//            warn(String.format("A error occurred while copying the config messages.yml to the plugin data folder. Error: %s", e));
//            e.printStackTrace();
//        }

    }

//    public Configuration getEditableFile() {
//        return editFile;
//    }
//
//    public Configuration getCommandFile() {
//        return commandFile;
//    }
//
//    public Configuration getModulesFile() {
//        return modulesFile;
//    }
//
//    public Configuration getNormalMotdFile() {
//        return normalMotdFile;
//    }
//
//    public Configuration getSettingsFile() {
//        return settingsFile;
//    }
//
//    public Configuration getTimerMotdFile() {
//        return timerMotdFile;
//    }

//    public Configuration getWhitelistMotdFile() {
//        return whitelistMotdFile;
//    }

    public Configuration getMessagesConfig() {
        return messagesConfig;
    }

    public Configuration getPlayersConfig() {
        return playersConfig;
    }

    public Configuration getMotdConfig() {
        return motdConfig;
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

    public Motd getMotdUtils() {
        return motdUtils;
    }
}
