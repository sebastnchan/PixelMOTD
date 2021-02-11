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

        String motdName   = plugin.getMotdUtils().getMotd(false);
        MotdType motdType = MotdType.NORMAL_MOTD;

        if (whitelistEnabled) {
            motdName = plugin.getMotdUtils().getMotd(true);
            motdType = MotdType.WHITELIST_MOTD;
        }

        ShowType showType = ShowType.WITHOUT_HEX;

        if (e.getConnection().getVersion() >= 735 && plugin.getMotdUtils().getHexStatus(motdType, motdName))
            showType = ShowType.HEX;

        Favicon favicon = null;

        String iconPath = plugin.getMotdConfig().getString(String.format("normal.motds.%s.custom-icon.name", motdName));

        if (plugin.getMotdUtils().getIconStatus(motdType, motdName)) {
            debug("Icon has been set!");
            favicon = Favicon.create(plugin.getServerIcon().getIcon(motdType, iconPath));
        }

        ServerPing.Protocol protocol = response.getVersion();
        if (plugin.getMotdUtils().isCustomProtocolEnabled(motdType)) {
            ServerPing.Protocol received = response.getVersion();

            received.setName(plugin.getMotdUtils().getCustomProtocol(motdType));

            if (plugin.getMotdUtils().getProtocolVersion(motdType)) {
                received.setProtocol(-1);
            }

            protocol = received;
        }

        ServerPing.PlayerInfo[] hoverLines = plugin.getMotdUtils().getHover(motdType, motdName);
        boolean hoverEnabled = plugin.getMotdUtils().isCustomHoverEnabled(motdType, motdName);

        ServerPing.Players players;
        if (hoverEnabled) {
            players = new ServerPing.Players(max, online, hoverLines);
        } else {
            players = new ServerPing.Players(max, online, response.getPlayers().getSample());
        }

        String line1 = plugin.getMotdUtils().getFirstLine(motdType, motdName, showType);
        String line2 = plugin.getMotdUtils().getSecondLine(motdType, motdName, showType);

        String motd = color(line1 + "\n" + line2);

        ServerPing result;

        if (showType == ShowType.HEX) {
            result = new ServerPing(protocol, players, new TextComponent(motd), favicon);
        } else {
            result = new ServerPing(protocol, players, new TextComponent(TextComponent.fromLegacyText(motd)), favicon);
        }

        e.setResponse(result);
    }
}
