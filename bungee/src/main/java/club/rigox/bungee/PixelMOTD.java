package club.rigox.bungee;

import club.rigox.bungee.commands.CommandUtils;
import club.rigox.bungee.commands.PixelCommand;
import club.rigox.bungee.commands.subcommands.BlacklistCommand;
import club.rigox.bungee.commands.subcommands.WhitelistCommand;
import club.rigox.bungee.enums.ConfigType;
import club.rigox.bungee.listeners.MotdEvent;
import club.rigox.bungee.listeners.WhitelistEvent;
import club.rigox.bungee.utils.Converter;
import club.rigox.bungee.utils.FileManager;
import club.rigox.bungee.utils.Motd;
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

    private Motd motdUtils;

//    public Configuration commandFile;
//    public Configuration editFile;
//    public Configuration modulesFile;
//    public Configuration normalMotdFile;
//    public Configuration settingsFile;
//    public Configuration timerMotdFile;
//    public Configuration whitelistMotdFile;

    // NEW FILES
    private Configuration messagesConfig;
    private Configuration dataConfig;
    private Configuration motdConfig;
    private Configuration config;

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
        messagesConfig      = manager.loadConfig("messages");
        dataConfig          = manager.loadConfig("data");
        motdConfig          = manager.loadConfig("motd");
        config              = manager.loadConfig("config");

        debug("Configs has been loaded!");
    }

    public void saveConfigs() {
        dataConfig  = manager.reloadConfig("data", dataConfig);
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

    public Configuration getMessagesConfig() {
        return messagesConfig;
    }

    public Configuration getDataConfig() {
        return dataConfig;
    }

    public Configuration getConfig() {
        return config;
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
