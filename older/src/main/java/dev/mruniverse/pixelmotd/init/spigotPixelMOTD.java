package dev.mruniverse.pixelmotd.init;

import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import dev.mruniverse.pixelmotd.commands.spigotCommand;
import dev.mruniverse.pixelmotd.enums.Files;
import dev.mruniverse.pixelmotd.enums.SaveMode;

import dev.mruniverse.pixelmotd.bstats.bukkit.Metrics;

import dev.mruniverse.pixelmotd.enums.initMode;
import dev.mruniverse.pixelmotd.listeners.spigotEvents;
import dev.mruniverse.pixelmotd.listeners.spigotMotd;
import dev.mruniverse.pixelmotd.files.FileManager;
import dev.mruniverse.pixelmotd.files.spigotControl;

import dev.mruniverse.pixelmotd.utils.HexManager;
import dev.mruniverse.pixelmotd.utils.PixelUpdater;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

public class spigotPixelMOTD extends JavaPlugin implements Listener {
    private static spigotPixelMOTD instance;
    private static HexManager hManager;
    private static FileManager fManager;
    public static HexManager getHex() {
        return hManager;
    }
    @Override
    public void onLoad() {
        instance = this;
        long temporalTimer = System.currentTimeMillis();
        fManager = new FileManager(initMode.SPIGOT_VERSION);
        fManager.loadFiles();
        fManager.loadConfiguration();
        spigotControl.save(SaveMode.ALL);
        hManager = new HexManager();
        hManager.setHex(spigotControl.getControl(Files.SETTINGS).getBoolean("settings.hexColors"));
        if(spigotControl.getControl(Files.SETTINGS).getBoolean("settings.update-check")) {
            PixelUpdater updater = new PixelUpdater(false, 37177);
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
        sendConsole("All files loaded in &b" + (System.currentTimeMillis() - temporalTimer) + "&fms.");
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
        if(cantWork()) {
            reportDependencies();
        }
        getCommand("pmotd").setExecutor(new spigotCommand("pmotd"));
        Bukkit.getPluginManager().registerEvents(new spigotEvents(),this);
        getCommand("pixelmotd").setExecutor(new spigotCommand("pixelmotd"));
        ProtocolManager protocolManager = ProtocolLibrary.getProtocolManager();
        protocolManager.addPacketListener((new spigotMotd()).getPacketAdapter());
        sendConsole("All events loaded in &b" + (System.currentTimeMillis() - temporalTimer) + "&fms.");
    }
    private boolean cantWork() {
        if (Bukkit.getServer().getPluginManager().getPlugin("PlaceholderAPI") != null) {
            usingPAPI();
        }
        return Bukkit.getServer().getPluginManager().getPlugin("ProtocolLib") == null;
    }
    private void usingPAPI() {
        if(!spigotControl.getControl(Files.SETTINGS).getBoolean("hooks.PlaceholderAPI")) {
            sendConsole("If you want you can enable PlaceholderAPI hook, you have installed PlaceholderAPI.");
            return;
        }
        sendConsole("You have enabled PlaceholderAPI hook, Amazing :).");
    }
    public static FileManager getFiles() {
        return fManager;
    }
    private void reportDependencies() {
        getServer().getConsoleSender().sendMessage(color("&c[Pixel MOTD] &fPixelMOTD Need ProtocolLib to work."));
    }
    public static void redIssue() {
        instance.getServer().getConsoleSender().sendMessage(color("&b[Pixel MOTD] &fCan't connect to SpigotMC and bStats, please check host internet or disable plugin autoUpdater and bStats to hide this message."));
    }
    public static void motdIssue(String type,String name) {
        sendConsole("Can't generate a correct motd, Latest issue was generated by the next motd: (" + type + "-" + name + ")");
    }
    private static String color(String message) {
        return ChatColor.translateAlternateColorCodes('&',message);
    }
    public static spigotPixelMOTD getInstance() {
        return instance;
    }
    public static void sendConsole(String message) {
        instance.getServer().getConsoleSender().sendMessage(color("&b[Pixel MOTD] &f" + message));
    }
}
