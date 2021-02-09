package club.rigox.bungee.utils;

import club.rigox.bungee.PixelMOTD;
import club.rigox.bungee.enums.MotdType;

import java.io.File;

import static club.rigox.bungee.utils.Logger.debug;

public class ServerIcon {
    private final PixelMOTD plugin;

    private File normalIcon;
    private File whitelistIcon;

    public ServerIcon(PixelMOTD plugin) {
        this.plugin = plugin;
    }
    private void loadFolder(File folderToLoad, String folderName) {
        boolean result = false;

        if (!folderToLoad.exists()) {
            result = folderToLoad.mkdir();
        }

        if(result) {
            debug("Folder: &b" + folderName + "&f created!");
        }
    }

    public void loadIcons() {
        normalIcon    = new File(plugin.getDataFolder(), "ServerIcon");
        loadFolder(normalIcon, "ServerIcon");

        whitelistIcon = new File(plugin.getDataFolder(), "ServerIcon");
        loadFolder(whitelistIcon, "ServerIcon");
    }

    public File getFile(MotdType motdType) {
        switch (motdType) {
            case NORMAL_MOTD:
                return normalIcon;
            case WHITELIST_MOTD:
                return whitelistIcon;
            case TIMER_MOTD:
                // TODO
                break;
            default:
                break;
        }
        return null;
    }

    public File getIcons(MotdType motdType) {
        File iconFolder = new File(normalIcon, "normal");
        switch (motdType) {
            case NORMAL_MOTD:
                iconFolder = new File(normalIcon, "normal");
                break;
            case WHITELIST_MOTD:
                iconFolder = new File(whitelistIcon, "whitelist");
                break;
            case TIMER_MOTD:
                // TODO
                break;
            default:
                break;
        }
        if (!iconFolder.exists()) loadFolder(iconFolder, "ServerIcon");
        return iconFolder;
    }
}
