package club.rigox.bungee;

import club.rigox.bungee.enums.ConfigType;
import club.rigox.bungee.utils.FileManager;
import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.config.Configuration;

import java.io.File;

import static club.rigox.bungee.utils.Logger.warn;

public final class PixelMOTD extends Plugin {
    public static PixelMOTD instance;

    private Configuration commandFile;
    private Configuration editFile;
    private Configuration modulesFile;
    private Configuration normalMotdFile;
    private Configuration settingsFile;
    private Configuration timerMotdFile;
    private Configuration whitelistMotdFile;

    @Override
    public void onEnable() {
        instance = this;

        loadConfigs();
    }

    @Override
    public void onDisable() {

    }

    public void loadConfigs() {
        FileManager manager = new FileManager(this);

        commandFile         = manager.loadConfig("command");
        editFile            = manager.loadConfig("edit");
        modulesFile         = manager.loadConfig("modules");
        normalMotdFile      = manager.loadConfig("normal-motd");
        settingsFile        = manager.loadConfig("settings");
        timerMotdFile       = manager.loadConfig("timer-motd");
        whitelistMotdFile   = manager.loadConfig("whitelist-motd");
    }

    public Configuration get(ConfigType configType) {
        switch (configType) {
            case COMMAND:
                return commandFile;
            case EDITABLE:
                return editFile;
            case MODULES:
                return modulesFile;
            case NORMAL_MOTD:
                return normalMotdFile;
            case SETTINGS:
                return settingsFile;
            case TIMER_MOTD:
                return timerMotdFile;
            case WHITELIST_MOTD:
                return whitelistMotdFile;
            default:
                warn(String.format("ConfigType %s doesn't exists!", configType));
                return null;
        }
    }
}
