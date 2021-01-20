package dev.mruniverse.pixelmotd.utils;

import dev.mruniverse.pixelmotd.enums.Blacklist;
import dev.mruniverse.pixelmotd.enums.Whitelist;

public class Extras {
    public static String getWorldPath(Whitelist path, String worldName) {
        if(path.equals(Whitelist.PLAYERS_NAME)) {
            return "modules.world-whitelist.worlds." + worldName + " .players-name";
        }
        if(path.equals(Whitelist.PLAYERS_UUID)) {
            return "modules.world-whitelist.worlds." + worldName + " .players-uuid";
        }
        if(path.equals(Whitelist.STATUS)) {
            return "modules.world-whitelist.worlds." + worldName + " .whitelist-status";
        }
        if(path.equals(Whitelist.REASON)) {
            return "modules.world-whitelist.worlds." + worldName + " .whitelist-reason";
        }
        return "modules.world-whitelist.worlds." + worldName + " .whitelist-author";
    }
    public static String getWorldPath(Blacklist path, String worldName) {
        if(path.equals(Blacklist.PLAYERS_NAME)) {
            return "modules.world-blacklist.worlds." + worldName + " .players-name";
        }
        if(path.equals(Blacklist.PLAYERS_UUID)) {
            return "modules.world-blacklist.worlds." + worldName + " .players-uuid";
        }
        if(path.equals(Blacklist.STATUS)) {
            return "modules.world-blacklist.worlds." + worldName + " .blacklist-status";
        }
        return "modules.world-blacklist.worlds." + worldName + " .blacklist-reason";
    }
    public static String getServerPath(Whitelist path, String worldName) {
        if(path.equals(Whitelist.PLAYERS_NAME)) {
            return "modules.server-whitelist.servers." + worldName + " .players-name";
        }
        if(path.equals(Whitelist.PLAYERS_UUID)) {
            return "modules.server-whitelist.servers." + worldName + " .players-uuid";
        }
        if(path.equals(Whitelist.STATUS)) {
            return "modules.server-whitelist.servers." + worldName + " .whitelist-status";
        }
        if(path.equals(Whitelist.REASON)) {
            return "modules.server-whitelist.servers." + worldName + " .whitelist-reason";
        }
        return "modules.server-whitelist.servers." + worldName + " .whitelist-author";
    }
    public static String getServerPath(Blacklist path, String worldName) {
        if(path.equals(Blacklist.PLAYERS_NAME)) {
            return "modules.server-blacklist.servers." + worldName + " .players-name";
        }
        if(path.equals(Blacklist.PLAYERS_UUID)) {
            return "modules.server-blacklist.servers." + worldName + " .players-uuid";
        }
        if(path.equals(Blacklist.STATUS)) {
            return "modules.server-blacklist.servers." + worldName + " .blacklist-status";
        }
        return "modules.server-blacklist.servers." + worldName + " .blacklist-reason";
    }
}
