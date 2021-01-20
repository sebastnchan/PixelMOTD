package dev.mruniverse.pixelmotd.files;

import dev.mruniverse.pixelmotd.enums.*;
import dev.mruniverse.pixelmotd.init.bungeePixelMOTD;
import dev.mruniverse.pixelmotd.init.spigotPixelMOTD;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class FileManager {
    private final boolean bungeeMode;
    public static File dataFolder, serverIcon, normalIcon, whitelistIcon, Normal, Whitelist, Settings, Editable, Modules, Timer, Command  = null;

    // Extras (Added in 8.8.6-Alpha3)

    // Class Load - bungeeManagement & DataFolder Setup.

    public FileManager(initMode startMode) {
        bungeeMode= startMode.equals(initMode.BUNGEE_VERSION);
        if(bungeeMode) {
            dataFolder = bungeePixelMOTD.getInstance().getDataFolder();
            return;
        }
        dataFolder = spigotPixelMOTD.getInstance().getDataFolder();
    }
    private String callConvert(String path) {
        if(bungeeMode) {
            return path.replace("%type%","server");
        }
        return path.replace("%type%","world");
    }
    private boolean getMotdControl(MotdType motdType) {
        if(motdType.equals(MotdType.WHITELIST_MOTD)) {
            if (bungeeMode) {
                return bungeeControl.callMotds(MotdType.WHITELIST_MOTD);
            }
            return spigotControl.callMotds(MotdType.WHITELIST_MOTD);
        }
        if(motdType.equals(MotdType.TIMER_MOTD)) {
            if (bungeeMode) {
                return bungeeControl.callMotds(MotdType.TIMER_MOTD);
            }
            return spigotControl.callMotds(MotdType.TIMER_MOTD);
        }
        if (bungeeMode) {
            return bungeeControl.callMotds(MotdType.NORMAL_MOTD);
        }
        return spigotControl.callMotds(MotdType.NORMAL_MOTD);
    }
    private boolean callEventsExists() {
        if (bungeeMode) {
            return bungeeControl.getControl(Files.SETTINGS).contains("events");
        }
        return spigotControl.getControl(Files.SETTINGS).contains("events");
    }
    private void callMotdVerificator(MotdType motdType) {
        if(motdType.equals(MotdType.WHITELIST_MOTD)) {
            if (bungeeMode) {
                bungeeControl.loadMotdPaths(MotdType.WHITELIST_MOTD);
                return;
            }
            spigotControl.loadMotdPaths(MotdType.WHITELIST_MOTD);
        }
        if(motdType.equals(MotdType.TIMER_MOTD)) {
            if (bungeeMode) {
                bungeeControl.loadMotdPaths(MotdType.TIMER_MOTD);
                return;
            }
            spigotControl.loadMotdPaths(MotdType.TIMER_MOTD);
        }
        if (bungeeMode) {
            bungeeControl.loadMotdPaths(MotdType.NORMAL_MOTD);
            return;
        }
        spigotControl.loadMotdPaths(MotdType.NORMAL_MOTD);
    }
    private void callDataFolder() {
        if(bungeeMode) {
            dataFolder = bungeePixelMOTD.getInstance().getDataFolder();
            return;
        }
        dataFolder = spigotPixelMOTD.getInstance().getDataFolder();
    }
    private void callMotdGeneration(MotdType motdType) {
        List<Object> stringList = new ArrayList<Object>();
        if(motdType.equals(MotdType.WHITELIST_MOTD)) {
            stringList.add("     &c&lPIXEL MOTD");
            stringList.add("&7SpigotMC Plugin v%plugin_version%");
            stringList.add("");
            stringList.add("&c&lInformation:");
            stringList.add("  &7Whitelist by: &f%whitelist_author%");
            stringList.add("  &7Spigot ID: &f37177");
            stringList.add("  &7Discord: &fMrUniverse#2556");
            stringList.add("  &7Online: &f%online%");
            stringList.add("  &frigox.club/discord/dev");
            stringList.add("");
            addConfig(Files.WHITELIST_MOTD, "whitelist.exampleMotd1.line1", "&8» &aPixelMOTD v%plugin_version% &7| &aSpigotMC");
            addConfig(Files.WHITELIST_MOTD, "whitelist.exampleMotd1.line2", "&f&oThis server is in whitelist. (1.8-1.15 Motd)");
            addConfig(Files.WHITELIST_MOTD, "whitelist.exampleMotd1.otherSettings.customHover.toggle", true);
            addConfig(Files.WHITELIST_MOTD, "whitelist.exampleMotd1.otherSettings.customHover.hover", stringList);
            addConfig(Files.WHITELIST_MOTD, "whitelist.exampleMotd1.otherSettings.customIcon.toggle", true);
            addConfig(Files.WHITELIST_MOTD, "whitelist.exampleMotd1.otherSettings.customIcon.customFile", false);
            addConfig(Files.WHITELIST_MOTD, "whitelist.exampleMotd1.otherSettings.customProtocol.toggle", true);
            addConfig(Files.WHITELIST_MOTD, "whitelist.exampleMotd1.otherSettings.customProtocol.changeProtocolVersion", false);
            addConfig(Files.WHITELIST_MOTD, "whitelist.exampleMotd1.otherSettings.customProtocol.protocol","PixelMotd Security");
            addConfig(Files.WHITELIST_MOTD, "whitelist.exampleMotd1.otherSettings.customHexMotd.toggle", true);
            addConfig(Files.WHITELIST_MOTD, "whitelist.exampleMotd1.otherSettings.customHexMotd.line1", "&8» &cPixelMOTD v%plugin_version% &7| &cSpigotMC");
            addConfig(Files.WHITELIST_MOTD, "whitelist.exampleMotd1.otherSettings.customHexMotd.line2", "&f&oWhitelist Mode (1.16+ Motd)");
            addConfig(Files.WHITELIST_MOTD, "whitelist.exampleMotd1.otherSettings.customOnlinePlayers.toggle", false);
            addConfig(Files.WHITELIST_MOTD, "whitelist.exampleMotd1.otherSettings.customOnlinePlayers.mode", "HALF-ADD");
            addConfig(Files.WHITELIST_MOTD, "whitelist.exampleMotd1.otherSettings.customMaxPlayers.toggle", true);
            addConfig(Files.WHITELIST_MOTD, "whitelist.exampleMotd1.otherSettings.customMaxPlayers.mode","HALF");
            stringList = new ArrayList<Object>();
            stringList.add(2021);
            stringList.add(2022);
            addConfig(Files.WHITELIST_MOTD, "whitelist.exampleMotd1.otherSettings.customOnlinePlayers.values",stringList);
            addConfig(Files.WHITELIST_MOTD, "whitelist.exampleMotd1.otherSettings.customMaxPlayers.values",stringList);
            return;
        }
        if(motdType.equals(MotdType.NORMAL_MOTD)) {
            stringList.add("     &9&lPIXEL MOTD");
            stringList.add("&7SpigotMC Plugin v%plugin_version%");
            stringList.add("");
            stringList.add("&b&lInformation:");
            stringList.add("  &7Version: &f%plugin_version%");
            stringList.add("  &7Spigot ID: &f37177");
            stringList.add("  &7Discord: &fMrUniverse#2556");
            stringList.add("  &7Online: &f%online%&7/&f%max%");
            stringList.add("  &frigox.club/discord/dev");
            stringList.add("");
            addConfig(Files.NORMAL_MOTD, "normal.exampleMotd1.line1", "&b&lPixelMOTD v%plugin_version%");
            addConfig(Files.NORMAL_MOTD, "normal.exampleMotd1.line2", "&f&oThis motd only appear for 1.8 - 1.15");
            addConfig(Files.NORMAL_MOTD, "normal.exampleMotd1.otherSettings.customHover.toggle", true);
            addConfig(Files.NORMAL_MOTD, "normal.exampleMotd1.otherSettings.customHover.hover", stringList);
            addConfig(Files.NORMAL_MOTD, "normal.exampleMotd1.otherSettings.customIcon.toggle", true);
            addConfig(Files.NORMAL_MOTD, "normal.exampleMotd1.otherSettings.customIcon.customFile", false);
            addConfig(Files.NORMAL_MOTD, "normal.exampleMotd1.otherSettings.customProtocol.toggle", true);
            addConfig(Files.NORMAL_MOTD, "normal.exampleMotd1.otherSettings.customProtocol.changeProtocolVersion", false);
            addConfig(Files.NORMAL_MOTD, "normal.exampleMotd1.otherSettings.customProtocol.protocol","PixelMotd System");
            addConfig(Files.NORMAL_MOTD, "normal.exampleMotd1.otherSettings.customHexMotd.toggle", true);
            addConfig(Files.NORMAL_MOTD, "normal.exampleMotd1.otherSettings.customHexMotd.line1", "&b&lPixelMOTD v%plugin_version%");
            addConfig(Files.NORMAL_MOTD, "normal.exampleMotd1.otherSettings.customHexMotd.line2", "&f&oThis motd only appear for 1.16+");
            addConfig(Files.NORMAL_MOTD, "normal.exampleMotd1.otherSettings.customOnlinePlayers.toggle", false);
            addConfig(Files.NORMAL_MOTD, "normal.exampleMotd1.otherSettings.customOnlinePlayers.mode", "HALF-ADD");
            addConfig(Files.NORMAL_MOTD, "normal.exampleMotd1.otherSettings.customMaxPlayers.toggle", true);
            addConfig(Files.NORMAL_MOTD, "normal.exampleMotd1.otherSettings.customMaxPlayers.mode","HALF-ADD");
            stringList = new ArrayList<Object>();
            stringList.add(2021);
            stringList.add(2022);
            addConfig(Files.NORMAL_MOTD, "normal.exampleMotd1.otherSettings.customOnlinePlayers.values",stringList);
            addConfig(Files.NORMAL_MOTD, "normal.exampleMotd1.otherSettings.customMaxPlayers.values",stringList);
            return;
        }
        stringList.add("     &9&lPIXEL MOTD");
        stringList.add("&7This is a timer motd");
        stringList.add("&7When you enable 1 motd");
        stringList.add("&7And you have 1 event with");
        stringList.add("&7the same name it will be");
        stringList.add("&7Sync. And when an event");
        stringList.add("&7End, it will execute commands");
        stringList.add("&7By the console automatically!");
        stringList.add("&frigox.club/discord/dev");
        stringList.add("");
        addConfig(Files.TIMER_MOTD, "timers.exampleMotd1.enabled", false);
        addConfig(Files.TIMER_MOTD, "timers.exampleMotd1.line1", "&6&l%event_timeLeft%");
        addConfig(Files.TIMER_MOTD, "timers.exampleMotd1.line2", "&f&oThis is a timer motd");
        addConfig(Files.TIMER_MOTD, "timers.exampleMotd1.otherSettings.customHover.toggle", true);
        addConfig(Files.TIMER_MOTD, "timers.exampleMotd1.otherSettings.customHover.hover", stringList);
        addConfig(Files.TIMER_MOTD, "timers.exampleMotd1.otherSettings.customIcon.toggle", true);
        addConfig(Files.TIMER_MOTD, "timers.exampleMotd1.otherSettings.customIcon.customFile", false);
        addConfig(Files.TIMER_MOTD, "timers.exampleMotd1.otherSettings.customProtocol.toggle", true);
        addConfig(Files.TIMER_MOTD, "timers.exampleMotd1.otherSettings.customProtocol.changeProtocolVersion", false);
        addConfig(Files.TIMER_MOTD, "timers.exampleMotd1.otherSettings.customProtocol.protocol","PixelMotd System");
        addConfig(Files.TIMER_MOTD, "timers.exampleMotd1.otherSettings.customHexMotd.toggle", true);
        addConfig(Files.TIMER_MOTD, "timers.exampleMotd1.otherSettings.customHexMotd.line1", "&6&l%event_timeLeft%");
        addConfig(Files.TIMER_MOTD, "timers.exampleMotd1.otherSettings.customHexMotd.line2", "&f&oThis motd only appear for 1.16+");
        addConfig(Files.TIMER_MOTD, "timers.exampleMotd1.otherSettings.customOnlinePlayers.toggle", false);
        addConfig(Files.TIMER_MOTD, "timers.exampleMotd1.otherSettings.customOnlinePlayers.mode", "HALF-ADD");
        addConfig(Files.TIMER_MOTD, "timers.exampleMotd1.otherSettings.customMaxPlayers.toggle", true);
        addConfig(Files.TIMER_MOTD, "timers.exampleMotd1.otherSettings.customMaxPlayers.mode","HALF-ADD");
        stringList = new ArrayList<Object>();
        stringList.add("/pmotd whitelist off");
        stringList.add("/alert Maintenance off automatically!");
        addConfig(Files.TIMER_MOTD, "timers.exampleMotd1.timerSettings.commandsToExecute",stringList);
        stringList = new ArrayList<Object>();
        stringList.add(2021);
        stringList.add(2022);
        addConfig(Files.TIMER_MOTD, "timers.exampleMotd1.otherSettings.customOnlinePlayers.values",stringList);
        addConfig(Files.TIMER_MOTD, "timers.exampleMotd1.otherSettings.customMaxPlayers.values",stringList);
    }
    private File getDataFolder() {
        if(dataFolder == null) callDataFolder();
        return dataFolder;
    }


    // File Verificator - Message System & Folder Verificator.


    private void loadFile(File fileToLoad,String fileName) {
        boolean result = false;
        if(!fileToLoad.exists()) {
            try {
                result = fileToLoad.createNewFile();
            } catch (IOException exception) {
                reportError();
            }
            if(result) {
                sendConsole("File: &b" + fileName + "&f created!");
            }
        }
    }
    private void sendConsole(String message) {
        if(bungeeMode) {
            bungeePixelMOTD.sendConsole(message);
            return;
        }
        spigotPixelMOTD.sendConsole(message);
    }
    public void loadFolder(File folderToLoad,String folderName) {
        boolean result = false;
        if(!folderToLoad.exists()) result = folderToLoad.mkdir();
        if(result) {
            sendConsole("Folder: &b" + folderName + "&f created!");
        }
    }

    //load settings.yml and other files path's.
    public void loadFiles() {
        loadFolder(getDataFolder(),"Main Folder");
        //File motdFiles, NormalFile, WhitelistFile;
        serverIcon = new File(getDataFolder(),"ServerIcon");
        loadFolder(serverIcon, "Server Icon");
        normalIcon = new File(serverIcon,"Normal");
        loadFolder(normalIcon, "Normal");
        whitelistIcon = new File(serverIcon,"Whitelist");
        loadFolder(whitelistIcon,"Whitelist File");
        Command = new File(getDataFolder(),"command.yml");
        Normal = new File(getDataFolder(),"normal-motd.yml");
        Whitelist = new File(getDataFolder(), "whitelist-motd.yml");
        Editable = new File(getDataFolder(), "edit.yml");
        Timer = new File(getDataFolder(), "timer-motd.yml");
        Modules = new File(getDataFolder(),"modules.yml");
        Settings = new File(getDataFolder(),"settings.yml");
        loadFile(Normal,"normal-motd.yml");
        loadFile(Command,"command.yml");
        loadFile(Whitelist,"whitelist-motd.yml");
        loadFile(Timer,"timer-motd.yml");
        loadFile(Editable,"edit.yml");
        loadFile(Settings,"settings.yml");
        loadFile(Modules,"modules.yml");
    }


    //Path Loader & AddConfig

    public void loadConfiguration() {
        if (getMotdControl(MotdType.NORMAL_MOTD)) {
            callMotdGeneration(MotdType.NORMAL_MOTD);
        } else {
            callMotdVerificator(MotdType.NORMAL_MOTD);
        }
        if (getMotdControl(MotdType.WHITELIST_MOTD)) {
            callMotdGeneration(MotdType.WHITELIST_MOTD);
        } else {
            callMotdVerificator(MotdType.WHITELIST_MOTD);
        }
        if (getMotdControl(MotdType.TIMER_MOTD)) {
            callMotdGeneration(MotdType.TIMER_MOTD);
        } else {
            callMotdVerificator(MotdType.TIMER_MOTD);
        }
        addConfig(Files.SETTINGS, "settings.update-check", true);
        addConfig(Files.SETTINGS, "settings.hexColors", true);
        addConfig(Files.SETTINGS, "settings.checkAlphaUpdates", true);
        addConfig(Files.SETTINGS, "settings.defaultUnknownUserName", "UnknownPlayer");
        addConfig(Files.SETTINGS, "settings.warn-external-modules", true);
        addConfig(Files.SETTINGS, "settings.show-detailed-errors", false);
        addConfig(Files.SETTINGS, "settings.toggle-dev-api", true);
        addConfig(Files.SETTINGS, "settings.notify-for-external-modules", true);
        addConfig(Files.SETTINGS, "settings.customExternalModulesFolder.toggle", true);
        addConfig(Files.SETTINGS, "settings.customExternalModulesFolder.customFolder", "ExternalModules");
        addConfig(Files.SETTINGS, "hooks.PlaceholderAPI", false);
        addConfig(Files.EDITABLE, "messages.reload", "&aThe plugin was reloaded correctly in <ms>ms.");
        addConfig(Files.EDITABLE, "messages.no-perms", "&cYou need permission <permission> for this command.");
        addConfig(Files.EDITABLE, "messages.whitelist-enabled", "&aWhitelist Status now is &b&lENABLED&a.");
        addConfig(Files.EDITABLE, "messages.whitelist-disabled", "&aWhitelist Status now is &c&lDISABLED&a.");
        addConfig(Files.EDITABLE, "messages.status-enabled", "&aThe %list% status of the %type% &b%value% &anow is &b&lENABLED&a.");
        addConfig(Files.EDITABLE, "messages.status-disabled", "&aThe %list% status of the %type% &b%value% &anow is &c&lDISABLED&a.");
        addConfig(Files.EDITABLE, "messages.whitelist-player-add", "&a%type% &e%player% &awas &badded &ato the whitelist.");
        addConfig(Files.EDITABLE, "messages.whitelist-player-remove", "&a%type% &e%player% &awas &cremoved &afrom the whitelist.");
        addConfig(Files.EDITABLE, "messages.not-whitelisted", "&a%type% &e%player% &ais not in the whitelist!");
        addConfig(Files.EDITABLE, "messages.already-whitelisted", "&a%type% &e%player% &ais already in the whitelist!");
        addConfig(Files.EDITABLE, "messages.blacklist-player-add", "&a%type% &e%player% &awas &badded &ato the blacklist.");
        addConfig(Files.EDITABLE, "messages.blacklist-player-remove", "&a%type% &e%player% &awas &cremoved &afrom the blacklist.");
        addConfig(Files.EDITABLE, "messages.not-blacklisted", "&a%type% &e%player% &ais not in the blacklist!");
        addConfig(Files.EDITABLE, "messages.already-blacklisted", "&a%type% &e%player% &ais already in the blacklist!");
        List<String> motdLists = new ArrayList<String>();
        addConfig(Files.EDITABLE, "whitelist.toggle", false);
        addConfig(Files.EDITABLE, "whitelist.author", "Console");
        addConfig(Files.EDITABLE, "whitelist.customConsoleName.toggle", true);
        addConfig(Files.EDITABLE, "whitelist.customConsoleName.name", "Console");
        addConfig(Files.EDITABLE, "whitelist.permissionBypass", "pixelmotd.whitelist.bypass");
        addConfig(Files.EDITABLE, "whitelist.check-mode", "LoginEvent");
        motdLists.add(" ");
        motdLists.add("&cYou were removed from the %type%!");
        motdLists.add("&cWhitelist Status Enabled by %whitelist_author%");
        motdLists.add(" ");
        addConfig(Files.EDITABLE, "whitelist.kick-message", motdLists);
        motdLists = new ArrayList<String>();
        motdLists.add("md_5");
        addConfig(Files.EDITABLE, "whitelist.players-name", motdLists);
        motdLists = new ArrayList<String>();
        motdLists.add("0-0-0-0");
        addConfig(Files.EDITABLE, "whitelist.players-uuid", motdLists);
        addConfig(Files.EDITABLE, "blacklist.toggle", false);
        motdLists = new ArrayList<String>();
        motdLists.add(" ");
        motdLists.add("&cYou were removed from the %type%!");
        motdLists.add("&cYou're in the black list of the server!");
        motdLists.add("&eYour nick: &f%nick%");
        motdLists.add(" ");
        addConfig(Files.EDITABLE, "blacklist.kick-message", motdLists);
        motdLists = new ArrayList<String>();
        motdLists.add("md_5");
        addConfig(Files.EDITABLE, "blacklist.players-name", motdLists);
        motdLists = new ArrayList<String>();
        motdLists.add("0-0-0-0");
        addConfig(Files.EDITABLE, "blacklist.players-uuid", motdLists);
        if (!callEventsExists()) {
            addConfig(Files.SETTINGS, "events.exampleEvent.eventName", "Example Event");
            addConfig(Files.SETTINGS, "events.exampleEvent.eventDate", "12/21/20 23:59:00");
            addConfig(Files.SETTINGS, "events.exampleEvent.TimeZone", "ECT");
            addConfig(Files.SETTINGS, "events.exampleEvent.format-Type", "FIRST");
            addConfig(Files.SETTINGS, "events.exampleEvent.endMessage", "&cThe event finished.");
        }
        addConfig(Files.SETTINGS, "timings.second", "second");
        addConfig(Files.SETTINGS, "timings.seconds", "seconds");
        addConfig(Files.SETTINGS, "timings.minute", "minute");
        addConfig(Files.SETTINGS, "timings.minutes", "minutes");
        addConfig(Files.SETTINGS, "timings.hour", "hour");
        addConfig(Files.SETTINGS, "timings.hours", "hours");
        addConfig(Files.SETTINGS, "timings.day", "day");
        addConfig(Files.SETTINGS, "timings.days", "days");
        addConfig(Files.SETTINGS, "timings.week", "week");
        addConfig(Files.SETTINGS, "timings.weeks", "weeks");
        addConfig(Files.MODULES, "modules.block-users.enabled", false);
        motdLists.add("MyNameIsBlocked");
        motdLists.add("ImAHacker");
        addConfig(Files.MODULES, "modules.block-users.ignoreCase", true);
        addConfig(Files.MODULES, "modules.block-users.blockedUsers", motdLists);
        motdLists = new ArrayList<String>();
        motdLists.add("&b------------- &aPixelMOTD &b-------------");
        motdLists.add("&fYou has been kicked from the server");
        motdLists.add("&fYour name is in the blackList");
        motdLists.add("&b%blocked_name%");
        motdLists.add("&b------------- &aPixelMOTD &b-------------");
        addConfig(Files.MODULES, "modules.block-users.kickMessage", motdLists);
        addConfig(Files.MODULES, "modules.block-words-in-name.enabled", false);
        addConfig(Files.MODULES, "modules.block-words-in-name.ignoreCase", false);
        motdLists = new ArrayList<String>();
        motdLists.add("hacker");
        motdLists.add("123456789");
        motdLists.add("racist");
        motdLists.add("Stupid");
        addConfig(Files.MODULES, "modules.block-words-in-name.blockedWords", motdLists);
        motdLists = new ArrayList<String>();
        motdLists.add("&b------------- &aPixelMOTD &b-------------");
        motdLists.add("&fYou has been kicked from the server");
        motdLists.add("&fYour name has a blocked word.");
        motdLists.add("&b%blocked_word%");
        motdLists.add("&b------------- &aPixelMOTD &b-------------");
        addConfig(Files.MODULES, "modules.block-words-in-name.kickMessage", motdLists);
        addConfig(Files.MODULES, "modules.%type%-whitelist.toggle", false);
        motdLists = new ArrayList<String>();
        motdLists.add("&b------------ &aPixelMotd &b------------");
        motdLists.add("&aThis %type% is currently in whitelist.");
        motdLists.add("&c%type%: &f%value%");
        motdLists.add("&cWhitelist by: &f%whitelist_author%");
        motdLists.add("&cReason: &f%whitelist_reason%");
        motdLists.add("&b------------ &aPixelMotd &b------------");
        addConfig(Files.MODULES, "modules.%type%-whitelist.kickMessage", motdLists);
        addConfig(Files.MODULES, "modules.%type%-whitelist.%type%s.%type%Name.whitelist-status", false);
        addConfig(Files.MODULES, "modules.%type%-whitelist.%type%s.%type%Name.whitelist-author", "RetiredUniverse44");
        addConfig(Files.MODULES, "modules.%type%-whitelist.%type%s.%type%Name.whitelist-reason", "This %type% is whitelisted!");
        motdLists = new ArrayList<String>();
        motdLists.add("md_5");
        addConfig(Files.MODULES, "modules.%type%-whitelist.%type%s.%type%Name.players-name", motdLists);
        motdLists = new ArrayList<String>();
        motdLists.add("0-0-0-0");
        addConfig(Files.MODULES, "modules.%type%-whitelist.%type%s.%type%Name.players-uuid", motdLists);
        motdLists = new ArrayList<String>();
        motdLists.add("&b------------ &aPixelMotd &b------------");
        motdLists.add("&aYou are in %type%'s blacklist.");
        motdLists.add("&c%type%: &f%value%");
        motdLists.add("&cReason: &f%blacklist_reason%");
        motdLists.add("&b------------ &aPixelMotd &b------------");
        addConfig(Files.MODULES, "modules.%type%-blacklist.toggle", false);
        addConfig(Files.MODULES, "modules.%type%-blacklist.kickMessage", motdLists);
        addConfig(Files.MODULES, "modules.%type%-blacklist.%type%s.%type%Name.blacklist-status", false);
        addConfig(Files.MODULES, "modules.%type%-blacklist.%type%s.%type%Name.blacklist-reason", "You're a bad player!");
        motdLists = new ArrayList<String>();
        motdLists.add("md_5");
        addConfig(Files.MODULES, "modules.%type%-blacklist.%type%s.%type%Name.players-name", motdLists);
        motdLists = new ArrayList<String>();
        motdLists.add("0-0-0-0");
        addConfig(Files.MODULES, "modules.%type%-blacklist.%type%s.%type%Name.players-uuid", motdLists);

        addConfig(Files.COMMAND, "command.online-status.online","Online &8(&7%server%&8)");
        addConfig(Files.COMMAND, "command.online-status.offline","Offline");
        addConfig(Files.COMMAND, "command.status.on","Enabled");
        addConfig(Files.COMMAND, "command.status.off","Disabled");
        motdLists = new ArrayList<String>();
        motdLists.add("pmotd");
        if(bungeeMode) motdLists.add("bpmotd");
        motdLists.add("pixelmotd");
        motdLists.add("pixelM");
        addConfig(Files.COMMAND, "command.list",motdLists);
        motdLists = new ArrayList<String>();
        motdLists.add("&b------------ &aPixelMotd &b------------");
        motdLists.add("&e/%cmd% &e- &fMain Command");
        if (bungeeMode) {
            motdLists.add("&e/%cmd% whitelist (global-serverName) [on-off]");
            motdLists.add("&e/%cmd% blacklist (global-serverName) [on-off]");
            motdLists.add("&e/%cmd% add (whitelist-blacklist) (Global-serverName) (playerName-playerUUID) &e- &fadd a player to your list.");
            motdLists.add("&e/%cmd% remove (whitelist-blacklist) (Global-serverName) (playerName-playerUUID) &e- &fremove a player from your list.");
        } else {
            motdLists.add("&e/%cmd% whitelist (global-worldName) [on-off]");
            motdLists.add("&e/%cmd% blacklist (global-worldName) [on-off]");
            motdLists.add("&e/%cmd% add (whitelist-blacklist) (Global-worldName) (playerName-playerUUID) &e- &fadd a player to your list.");
            motdLists.add("&e/%cmd% remove (whitelist-blacklist) (Global-worldName) (playerName-playerUUID) &e- &fremove a player from your list.");
        }
        motdLists.add("&e/%cmd% reload (all-settings-edit-modules-cmd-motds)");
        motdLists.add("&e/%cmd% modules toggle (moduleName)");
        motdLists.add("&e/%cmd% modules info (moduleName)");
        motdLists.add("&e/%cmd% modules list");
        motdLists.add("&e/%cmd% externalModules toggle (moduleName)");
        motdLists.add("&e/%cmd% externalModules info (moduleName)");
        motdLists.add("&e/%cmd% externalModules list");
        motdLists.add("&b------------ &aPixelMotd &b------------");
        addConfig(Files.COMMAND, "command.help", motdLists);
        motdLists = new ArrayList<String>();
        motdLists.add("&b------------ &aPixelMotd &b------------");
        motdLists.add("&e%whitelist% - Whitelist");
        motdLists.add("&fStatus: &7%status%");
        motdLists.add(" ");
        motdLists.add("&fPlayers Name:");
        addConfig(Files.COMMAND, "command.whitelist.list.top",motdLists);
        addConfig(Files.COMMAND, "command.whitelist.list.playersNameFormat","&e&l* &8[&7%online_status%&8] &7%player_name%");
        motdLists = new ArrayList<String>();
        motdLists.add(" ");
        motdLists.add("&fPlayers UUID:");
        addConfig(Files.COMMAND, "command.whitelist.list.mid",motdLists);
        addConfig(Files.COMMAND, "command.whitelist.list.playersUuidFormat","&e&l* &8[&7UUID&8] &7%player_uuid%");
        motdLists = new ArrayList<String>();
        motdLists.add(" ");
        motdLists.add("<isUser>&fYour UUID: &7%your_uuid%");
        motdLists.add("&b------------ &aPixelMotd &b------------");
        addConfig(Files.COMMAND, "command.whitelist.list.bot",motdLists);

        motdLists = new ArrayList<String>();
        motdLists.add("&b------------ &aPixelMotd &b------------");
        motdLists.add("&e%blacklist% - Blacklist");
        motdLists.add("&fStatus: &7%status%");
        motdLists.add(" ");
        motdLists.add("&fPlayers Name:");
        addConfig(Files.COMMAND, "command.blacklist.list.top",motdLists);
        addConfig(Files.COMMAND, "command.blacklist.list.playersNameFormat","&e&l* &8[&7%online_status%&8] &7%player_name%");
        motdLists = new ArrayList<String>();
        motdLists.add(" ");
        motdLists.add("&fPlayers UUID:");
        addConfig(Files.COMMAND, "command.blacklist.list.mid",motdLists);
        addConfig(Files.COMMAND, "command.blacklist.list.playersUuidFormat","&e&l* &8[&7UUID&8] &7%player_uuid%");
        motdLists = new ArrayList<String>();
        motdLists.add(" ");
        motdLists.add("<isUser>&fYour UUID: &7%your_uuid%");
        motdLists.add("&b------------ &aPixelMotd &b------------");
        addConfig(Files.COMMAND, "command.blacklist.list.bot",motdLists);

        motdLists = new ArrayList<String>();
        motdLists.add("&b------------ &aPixelMotd &b------------");
        motdLists.add("&6PixelMOTD - Module List");
        motdLists.add(" ");
        motdLists.add("&fPlugin Modules:");
        addConfig(Files.COMMAND, "command.modules.list.top",motdLists);
        addConfig(Files.COMMAND, "command.modules.list.moduleNameFormat","&e&l* &8[&7%status%&8] &7%module_name%");
        motdLists = new ArrayList<String>();
        motdLists.add(" ");
        motdLists.add("&b------------ &aPixelMotd &b------------");
        addConfig(Files.COMMAND, "command.modules.list.bot",motdLists);
        motdLists = new ArrayList<String>();
        motdLists.add("&b------------ &aPixelMotd &b------------");
        motdLists.add("&6PixelMOTD - External Module List");
        motdLists.add(" ");
        motdLists.add("&fExternal Modules:");
        addConfig(Files.COMMAND, "command.externalModules.list.top",motdLists);
        addConfig(Files.COMMAND, "command.externalModules.list.moduleNameFormat","&e&l* &8[&7%status%&8] &7%module_name%");
        motdLists = new ArrayList<String>();
        motdLists.add(" ");
        motdLists.add("&b------------ &aPixelMotd &b------------");
        addConfig(Files.COMMAND, "command.externalModules.list.bot",motdLists);
        motdLists = new ArrayList<String>();
        motdLists.add("&b------------ &aPixelMotd &b------------");
        motdLists.add("&6%module_name% - External Module");
        motdLists.add(" ");
        motdLists.add("&fVersion: &e%module_version%");
        motdLists.add("&fAuthor: &e%module_author%");
        motdLists.add("&fStatus: &e%status%");
        motdLists.add(" ");
        motdLists.add("&b------------ &aPixelMotd &b------------");
        addConfig(Files.COMMAND, "command.externalModules.info",motdLists);
        motdLists = new ArrayList<String>();
        motdLists.add("&b------------ &aPixelMotd &b------------");
        motdLists.add("&6%module_name% - Plugin Module");
        motdLists.add(" ");
        motdLists.add("&fVersion: &e%module_version%");
        motdLists.add("&fStatus: &e%status%");
        motdLists.add(" ");
        motdLists.add("&b------------ &aPixelMotd &b------------");
        addConfig(Files.COMMAND, "command.modules.info",motdLists);
    }


    public void addConfig(Files fileToControl, String path, Object value) {
        path = callConvert(path);
        if(!bungeeMode) {
            if(fileToControl.equals(Files.WHITELIST_MOTD)) {
                if(!spigotControl.getControl(Files.WHITELIST_MOTD).contains(path)) {
                    spigotControl.getControl(Files.WHITELIST_MOTD).set(path,value);
                }
                return;
            }
            if(fileToControl.equals(Files.COMMAND)) {
                if(!spigotControl.getControl(Files.COMMAND).contains(path)) {
                    spigotControl.getControl(Files.COMMAND).set(path,value);
                }
                return;
            }
            if(fileToControl.equals(Files.NORMAL_MOTD)) {
                if(!spigotControl.getControl(Files.NORMAL_MOTD).contains(path)) {
                    spigotControl.getControl(Files.NORMAL_MOTD).set(path,value);
                }
                return;
            }
            if(fileToControl.equals(Files.EDITABLE)) {
                if(!spigotControl.getControl(Files.EDITABLE).contains(path)) {
                    spigotControl.getControl(Files.EDITABLE).set(path,value);
                }
                return;
            }
            if(fileToControl.equals(Files.MODULES)) {
                if(!spigotControl.getControl(Files.MODULES).contains(path)) {
                    spigotControl.getControl(Files.MODULES).set(path,value);
                }
                return;
            }
            if(fileToControl.equals(Files.SETTINGS)) {
                if(!spigotControl.getControl(Files.SETTINGS).contains(path)) {
                    spigotControl.getControl(Files.SETTINGS).set(path,value);
                }
                return;
            }
            return;
        }
        if(fileToControl.equals(Files.WHITELIST_MOTD)) {
            if(!bungeeControl.getControl(Files.WHITELIST_MOTD).contains(path)) {
                bungeeControl.getControl(Files.WHITELIST_MOTD).set(path,value);
            }
            return;
        }
        if(fileToControl.equals(Files.NORMAL_MOTD)) {
            if(!bungeeControl.getControl(Files.NORMAL_MOTD).contains(path)) {
                bungeeControl.getControl(Files.NORMAL_MOTD).set(path,value);
            }
            return;
        }
        if(fileToControl.equals(Files.COMMAND)) {
            if(!bungeeControl.getControl(Files.COMMAND).contains(path)) {
                bungeeControl.getControl(Files.COMMAND).set(path,value);
            }
            return;
        }
        if(fileToControl.equals(Files.EDITABLE)) {
            if(!bungeeControl.getControl(Files.EDITABLE).contains(path)) {
                bungeeControl.getControl(Files.EDITABLE).set(path,value);
            }
            return;
        }
        if(fileToControl.equals(Files.MODULES)) {
            if(!bungeeControl.getControl(Files.MODULES).contains(path)) {
                bungeeControl.getControl(Files.MODULES).set(path,value);
            }
            return;
        }
        if(fileToControl.equals(Files.TIMER_MOTD)) {
            if(!bungeeControl.getControl(Files.TIMER_MOTD).contains(path)) {
                bungeeControl.getControl(Files.TIMER_MOTD).set(path,value);
            }
            return;
        }
        if(fileToControl.equals(Files.SETTINGS)) {
            if(!bungeeControl.getControl(Files.SETTINGS).contains(path)) {
                bungeeControl.getControl(Files.SETTINGS).set(path,value);
            }
        }
    }
    public File getFile(Files fileToGet) {
        if(fileToGet.equals(Files.SETTINGS)) {
            return Settings;
        }
        if(fileToGet.equals(Files.MODULES)) {
            return Modules;
        }
        if(fileToGet.equals(Files.COMMAND)) {
            return Command;
        }
        if(fileToGet.equals(Files.EDITABLE)) {
            return Editable;
        }
        if(fileToGet.equals(Files.NORMAL_MOTD)) {
            return Normal;
        }
        if(fileToGet.equals(Files.TIMER_MOTD)) {
            return Timer;
        }
        return Whitelist;
    }
    public File getFile(Icons iconToGet) {
        if(iconToGet.equals(Icons.NORMAL)) {
            return normalIcon;
        }
        if(iconToGet.equals(Icons.WHITELIST)) {
            return whitelistIcon;
        }
        return serverIcon;
    }

    private void reportError() {
        sendConsole("The plugin can't load or save configuration files!");
    }
    public void reportControlError() {
        sendConsole("The plugin can't load or save configuration files! (Bungee | Spigot Control Issue - Caused by: IO Exception)");
    }
    public void reportBungeeGetControlError() {
        sendConsole("The plugin can't load or save configuration files! (Bungee Control Issue - Caused by: One plugin is using bad the <getControl() from FileManager.class>)");
    }
    public void reportSpigotGetControlError() {
        sendConsole("The plugin can't load or save configuration files! (Spigot Control Issue - Caused by: One plugin is using bad the <getControl() from FileManager.class>)");
    }
}
