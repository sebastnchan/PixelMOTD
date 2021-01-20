package dev.mruniverse.pixelmotd.init;

import dev.mruniverse.pixelmotd.bstats.bungee.Metrics;
import dev.mruniverse.pixelmotd.commands.bungeeCommand;
import dev.mruniverse.pixelmotd.enums.Files;
import dev.mruniverse.pixelmotd.enums.SaveMode;
import dev.mruniverse.pixelmotd.enums.initMode;
import dev.mruniverse.pixelmotd.listeners.bungeeEvents;
import dev.mruniverse.pixelmotd.listeners.bungeeMotd;
import dev.mruniverse.pixelmotd.files.FileManager;
import dev.mruniverse.pixelmotd.files.bungeeControl;
import dev.mruniverse.pixelmotd.utils.HexManager;
import dev.mruniverse.pixelmotd.utils.PixelUpdater;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.api.plugin.Plugin;

public class bungeePixelMOTD extends Plugin implements Listener {
    private static bungeePixelMOTD instance;
    private static FileManager fManager;
    private static HexManager hManager;
    @Override
    public void onLoad() {
        instance = this;
        fManager = new FileManager(initMode.BUNGEE_VERSION);
        fManager.loadFiles();
        hManager = new HexManager();
        fManager.loadConfiguration();
        bungeeControl.save(SaveMode.ALL);

        hManager.setHex(bungeeControl.getControl(Files.SETTINGS).getBoolean("settings.hexColors"));
        for(String command : bungeeControl.getControl(Files.COMMAND).getStringList("command.list")) {
            getProxy().getPluginManager().registerCommand(this,new bungeeCommand(command));
        }
        if(bungeeControl.getControl(Files.SETTINGS).getBoolean("settings.update-check")) {
            PixelUpdater updater = new PixelUpdater(true, 37177);
            String UpdateResult = updater.getUpdateResult();
            if (UpdateResult.equalsIgnoreCase("UPDATED")) {
                sendConsole("&aYou're using latest version of PixelMOTD, You're Awesome!");
                String versionResult = updater.getVersionResult();
                if(versionResult.equalsIgnoreCase("RED_PROBLEM")) {
                    sendConsole("&aPixelMOTD can't connect to WiFi to check plugin version.");
                } else if(versionResult.equalsIgnoreCase("PRE_ALPHA_VERSION")) {
                    sendConsole("&cYou are Running a &aPre Alpha version&c, it is normal to find several errors, please report these errors so that they can be solved. &eWARNING: &cI (MrUniverse) recommend a Stable version, PreAlpha aren't stable versions!");
                } else if(versionResult.equalsIgnoreCase("ALPHA_VERSION")) {
                    sendConsole("&bYou are Running a &aAlpha version&b, it is normal to find several errors, please report these errors so that they can be solved.");
                } else if(versionResult.equalsIgnoreCase("RELEASE")) {
                    sendConsole("&aYou are Running a &bRelease Version&a, this is a stable version, awesome!");
                } else if(versionResult.equalsIgnoreCase("PRE_RELEASE")) {
                    sendConsole("&aYou are Running a &bPreRelease Version&a, this is a stable version but is not the final version or don't have finished all things of the final version, but is a stable version,awesome!");
                }
            } else if (UpdateResult.equalsIgnoreCase("NEW_VERSION")) {
                sendConsole("&aA new update is available: &bhttps://www.spigotmc.org/resources/37177/");
            } else if (UpdateResult.equalsIgnoreCase("BETA_VERSION")) {
                sendConsole("&aYou are Running a Pre-Release version, please report bugs ;)");
            } else if (UpdateResult.equalsIgnoreCase("RED_PROBLEM")) {
                sendConsole("&aPixelMOTD can't connect to WiFi to check plugin version.");
            } else if (UpdateResult.equalsIgnoreCase("ALPHA_VERSION")) {
                sendConsole("&bYou are Running a &aAlpha version&b, it is normal to find several errors, please report these errors so that they can be solved.");
            } else if (UpdateResult.equalsIgnoreCase("PRE_ALPHA_VERSION")) {
                sendConsole("&cYou are Running a &aPre Alpha version&c, it is normal to find several errors, please report these errors so that they can be solved. &eWARNING: &cI (MrUniverse) recommend a Stable version, PreAlpha aren't stable versions!");
            }
        }
        sendConsole("All files loaded");
    }
    @Override
    public void onDisable() {
        sendConsole("The plugin was unloaded.");
    }
    @Override
    public void onEnable() {
        long temporalTimer = System.currentTimeMillis();
        int pluginId = 8509;
        Metrics metrics = new Metrics(this, pluginId);
        sendConsole("Metrics: &b" + metrics.isEnabled());
        getProxy().getPluginManager().registerListener(this, new bungeeEvents());
        getProxy().getPluginManager().registerListener(this, new bungeeMotd());
        sendConsole("All events loaded in &b" + (System.currentTimeMillis() - temporalTimer) + "&fms.");
    }
    public static FileManager getFiles() {
        return fManager;
    }
    public static bungeePixelMOTD getInstance() { return instance; }
    public static HexManager getHex() { return hManager; }
    public static void redIssue() {
        instance.getProxy().getConsole().sendMessage(new TextComponent(ChatColor.translateAlternateColorCodes('&',"&b[Pixel MOTD] &fCan't connect to SpigotMC and bStats, please check host internet or disable plugin autoUpdater and bStats to hide this message.")));
    }
    public static void sendConsole(String message) {
        instance.getProxy().getConsole().sendMessage(new TextComponent(ChatColor.translateAlternateColorCodes('&',"&b[Pixel MOTD] &f" + message)));
    }
}

