package club.rigox.bungee.listeners;

import club.rigox.bungee.PixelMOTD;
import club.rigox.bungee.enums.MotdType;
import club.rigox.bungee.enums.ShowType;
import net.md_5.bungee.api.Favicon;
import net.md_5.bungee.api.ServerPing;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.PendingConnection;
import net.md_5.bungee.api.event.ProxyPingEvent;
import net.md_5.bungee.api.plugin.Cancellable;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import static club.rigox.bungee.utils.Logger.*;

public class MotdEvent implements Listener {
    private final PixelMOTD plugin;

    public MotdEvent(PixelMOTD plugin) {
        this.plugin = plugin;
        plugin.getProxy().getPluginManager().registerListener(plugin, this);
        info("&eMOTD &7listener loaded");
    }

    @EventHandler
    public void onProxyPingEvent(ProxyPingEvent e) {

        // Verify for cancelled motd to prevent errors.
        if (e instanceof Cancellable && ((Cancellable) e).isCancelled()) return;
        if (e.getResponse() == null) return;

        ServerPing response          = e.getResponse();
        PendingConnection connection = e.getConnection();

        if (connection == null) return;

        boolean whitelistEnabled     = plugin.getDataConfig().getBoolean("whitelist.toggle");

        int max    = response.getPlayers().getMax();
        int online = response.getPlayers().getOnline();

        String   showMotd;
        MotdType showMode;

        showMotd = plugin.getMotdUtils().getMotd(false);
        showMode = MotdType.NORMAL_MOTD;

        if (whitelistEnabled) {
            showMotd = plugin.getMotdUtils().getMotd(true);
            showMode = MotdType.WHITELIST_MOTD;
        }

        ShowType showType = ShowType.WITHOUT_HEX;

        if (e.getConnection().getVersion() >= 735 && plugin.getMotdUtils().getHexStatus(showMode, showMotd))
            showType = ShowType.HEX;

        // TODO ICON STATUS
        // TODO PLAYER STATUS

        Favicon favicon = null;
        if (plugin.getMotdUtils().getIconStatus(showMode)) {
            debug("Icon has been set!");
            favicon = Favicon.create(plugin.getServerIcon().getIcon(showMode));
        }
//        if (plugin.getMotdUtils().getIconStatus(showMode)) {
//
//            File[] icons = plugin.getServerIcon().getFile(showMode).listFiles();
//            File[] icons = plugin.getServerIcon().getIcons(showMode).listFiles();
//            List<File> validIcons = new ArrayList<>();
//
//            if (icons != null && icons.length != 0) {
//                for (File image : icons) {
//                    if (com.google.common.io.Files.getFileExtension(image.getPath()).equals("png")) {
//                        validIcons.add(image);
//                    }
//                }
//                if (validIcons.size() != 0) {
//                    BufferedImage image = getImage(validIcons.get(new Random().nextInt(validIcons.size())));
//                    if (image != null) {
//                        favicon = Favicon.create(image);
//                    } else {
//                        favicon = response.getFaviconObject();
//                    }
//                } else {
//                    favicon = response.getFaviconObject();
//                }
//            } else {
//                favicon = response.getFaviconObject();
//            }
//        }

        ServerPing.Protocol protocol;
        protocol = response.getVersion();

        if (plugin.getMotdUtils().isCustomProtocolEnabled(showMode)) {
            ServerPing.Protocol received = response.getVersion();

            received.setName(plugin.getMotdUtils().getCustomProtocol(showMode));

            if (plugin.getMotdUtils().getProtocolVersion(showMode)) {
                received.setProtocol(-1);
            }

            protocol = received;

        }

        ServerPing.PlayerInfo[] motdHover = plugin.getMotdUtils().getHover(showMode, showMotd);
        boolean mHover = plugin.getMotdUtils().isCustomHoverEnabled(showMode, showMotd);

        ServerPing.Players players;
        if (mHover) {
            players = new ServerPing.Players(max, online, motdHover);
        } else {
            players = new ServerPing.Players(max, online, response.getPlayers().getSample());
        }

        String line1 = plugin.getMotdUtils().getFirstLine(showMode, showMotd, showType);
        String line2 = plugin.getMotdUtils().getSecondLine(showMode, showMotd, showType);

        String motd = color(line1 + "\n" + line2);

        ServerPing result;

        if (showType == ShowType.HEX) {
            debug("ShowType HEX");
            result = new ServerPing(protocol, players, new TextComponent(motd), favicon);
        } else {
            debug("ShowType WITHOUT_HEX");
            result = new ServerPing(protocol, players, new TextComponent(TextComponent.fromLegacyText(motd)), favicon);
        }

        e.setResponse(result);
    }

    private BufferedImage getImage(File file) {
        try {
            return ImageIO.read(file);
        } catch(IOException e) {
            error(String.format("An error ocurred while reading favicon. Error: %s", e));
            e.printStackTrace();
        }
        return null;
    }
}
