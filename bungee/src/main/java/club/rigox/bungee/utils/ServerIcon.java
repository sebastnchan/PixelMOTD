package club.rigox.bungee.utils;

import club.rigox.bungee.PixelMOTD;
import club.rigox.bungee.enums.MotdType;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;

import static club.rigox.bungee.utils.Logger.*;

public class ServerIcon {
    private final PixelMOTD plugin;

//    private File normalIcon;
//    private File whitelistIcon;

    public ServerIcon(PixelMOTD plugin) {
        this.plugin = plugin;
    }

    public BufferedImage getIcon(MotdType motdType, String icon) {
        BufferedImage favicon = null;
        InputStream nullIcon  = plugin.getResourceAsStream("not-set.png");

        plugin.createFolders();

        try {
            String directoryPrefix = "normal";

            if (motdType == MotdType.WHITELIST_MOTD) {
                directoryPrefix = "whitelist";
            }

            File iconPath = new File(plugin.getDataFolder(), directoryPrefix + "-icons/" + icon);

            if (!iconPath.exists()) {
                warn(String.format("Favicon %s doesn't exists on %s-icons folder! Creating one...",
                        icon,
                        directoryPrefix));

                Files.copy(nullIcon, iconPath.toPath());
            }

            favicon = ImageIO.read(iconPath);

        } catch (IOException e) {
            error(String.format("Something weird happened while getting %s favicon. Error: %s", icon, e));
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
