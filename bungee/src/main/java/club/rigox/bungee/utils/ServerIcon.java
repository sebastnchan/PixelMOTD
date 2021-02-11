package club.rigox.bungee.utils;

import club.rigox.bungee.PixelMOTD;
import club.rigox.bungee.enums.MotdType;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;

import static club.rigox.bungee.utils.Logger.error;
import static club.rigox.bungee.utils.Logger.warn;

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

            int height = favicon.getHeight();
            int width  = favicon.getWidth();

            if (height != 64 || width != 64) {
                error("Icon is not a 64x64 resolution. Copying default icon...");

                if (!iconPath.renameTo(new File(iconPath + ".1"))) {
                    error("Cannot rename file.");
                }

                Files.copy(nullIcon, iconPath.toPath());
            }

        } catch (IOException e) {
            error(String.format("Something weird happened while getting %s favicon. Error: %s", icon, e));
        }

        return favicon;
    }
}
