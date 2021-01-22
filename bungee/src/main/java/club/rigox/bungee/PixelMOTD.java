package club.rigox.bungee;

import club.rigox.bungee.utils.Converter;
import club.rigox.bungee.utils.FileManager;
import club.rigox.bungee.utils.Placeholders;
import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.config.Configuration;

public final class PixelMOTD extends Plugin {
    public static PixelMOTD instance;

    private Converter converter;

    private Placeholders placeholders;

    private Configuration commandFile;
    private Configuration editFile;
    private Configuration modulesFile;
    private Configuration normalMotdFile;
    private Configuration settingsFile;
    private Configuration timerMotdFile;
    private Configuration whitelistMotdFile;

    @Override
    public void onEnable() {
        instance     = this;

        loadConfigs();

        converter    = new Converter();
        placeholders = new Placeholders(this);
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
}
