package club.rigox.bungee;

import club.rigox.bungee.enums.ConfigType;
import club.rigox.bungee.utils.Converter;
import club.rigox.bungee.utils.FileManager;
import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.config.Configuration;

import java.io.File;
import java.util.Objects;

import static club.rigox.bungee.utils.Logger.warn;

public final class PixelMOTD extends Plugin {
    public static PixelMOTD instance;

    private Converter converter;

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

        new Converter();
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
                if (commandFile == null) loadConfigs();
                return Objects.requireNonNull(commandFile);
            case EDITABLE:
                if (editFile == null) loadConfigs();
                return Objects.requireNonNull(editFile);
            case MODULES:
                if (modulesFile == null) loadConfigs();
                return Objects.requireNonNull(modulesFile);
            case NORMAL_MOTD:
                if (normalMotdFile == null) loadConfigs();
                return Objects.requireNonNull(normalMotdFile);
            case SETTINGS:
                if (settingsFile == null) loadConfigs();
                return Objects.requireNonNull(settingsFile);
            case TIMER_MOTD:
                if (timerMotdFile == null) loadConfigs();
                return Objects.requireNonNull(timerMotdFile);
            case WHITELIST_MOTD:
                if (whitelistMotdFile == null) loadConfigs();
                return Objects.requireNonNull(whitelistMotdFile);
            default:
                warn(String.format("ConfigType %s doesn't exists!", configType));
                return null;
        }
    }

    public Converter getConverter() {
        return converter;
    }
}
