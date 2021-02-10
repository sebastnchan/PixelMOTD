package club.rigox.bungee.utils;

import club.rigox.bungee.PixelMOTD;
import club.rigox.bungee.enums.MotdType;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class ServerIcon {
    private final PixelMOTD plugin;

//    private File normalIcon;
//    private File whitelistIcon;

    public ServerIcon(PixelMOTD plugin) {
        this.plugin = plugin;
    }

    public BufferedImage getIcon(MotdType motdType) {
        BufferedImage favicon = null;

        try {
            if (motdType == MotdType.NORMAL_MOTD) {
                favicon = ImageIO.read(new File(plugin.getDataFolder(), "normal.png"));
            }

            if (motdType == MotdType.WHITELIST_MOTD) {
                favicon = ImageIO.read(new File(plugin.getDataFolder(), "whitelist.png"));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return favicon;
    }
//    private void loadFolder(File folderToLoad, String folderName) {
//        boolean result = false;
//
//        if (!folderToLoad.exists()) {
//            result = folderToLoad.mkdir();
//        }
//
//        if(result) {
//            debug("Folder: &b" + folderName + "&f created!");
//        }
//    }
//
//    public void loadIcons() {
//        normalIcon    = new File(plugin.getDataFolder(), "ServerIcon");
//        loadFolder(normalIcon, "ServerIcon");
//
//        whitelistIcon = new File(plugin.getDataFolder(), "ServerIcon");
//        loadFolder(whitelistIcon, "ServerIcon");
//    }
//
//    public File getFile(MotdType motdType) {
//        switch (motdType) {
//            case NORMAL_MOTD:
//                debug(normalIcon.toString());
//                return normalIcon;
//            case WHITELIST_MOTD:
//                debug(whitelistIcon.toString());
//                return whitelistIcon;
//            case TIMER_MOTD:
//                 TODO
//                break;
//            default:
//                break;
//        }
//        return null;
//    }
//
//    public File getIcons(MotdType motdType) {
//        File iconFolder = new File(normalIcon, "normal");
//        switch (motdType) {
//            case NORMAL_MOTD:
//                iconFolder = new File(normalIcon, "normal");
//                break;
//            case WHITELIST_MOTD:
//                iconFolder = new File(whitelistIcon, "whitelist");
//                break;
//            case TIMER_MOTD:
//                 TODO
//                break;
//            default:
//                break;
//        }
//        if (!iconFolder.exists()) loadFolder(iconFolder, "ServerIcon");
//        debug(iconFolder.toString());
//        return iconFolder;
//    }
}
