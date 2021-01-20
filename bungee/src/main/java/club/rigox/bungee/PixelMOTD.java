package club.rigox.bungee;

import club.rigox.bungee.utils.FileManager;
import net.md_5.bungee.api.plugin.Plugin;

public final class PixelMOTD extends Plugin {
    public static PixelMOTD instance;

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

        manager.loadConfig("command");
        manager.loadConfig("edit");
        manager.loadConfig("modules");
        manager.loadConfig("normal-motd");
        manager.loadConfig("settings");
        manager.loadConfig("timer-motd");
        manager.loadConfig("whitelist-motd");
    }
}
