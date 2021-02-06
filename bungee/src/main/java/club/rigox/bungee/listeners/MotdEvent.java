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

import static club.rigox.bungee.utils.Logger.color;
import static club.rigox.bungee.utils.Logger.debug;

public class MotdEvent implements Listener {
    private final PixelMOTD plugin;

    Favicon                 favicon = null;
    ServerPing.Protocol     protocol;
    ServerPing.Players      players;
    ServerPing.PlayerInfo[] motdHover;

    String line1, line2, motd, showMotd;

    MotdType showMode;
    ShowType showType;

    boolean mHover;

    public MotdEvent(PixelMOTD plugin) {
        this.plugin = plugin;
        plugin.getProxy().getPluginManager().registerListener(plugin, this);
    }

    @EventHandler
    public void onProxyPingEvent(ProxyPingEvent e) {

        // Verify for cancelled motd to prevent errors.
        if (e instanceof Cancellable && ((Cancellable) e).isCancelled()) return;
        if (e.getResponse() == null) return;

        ServerPing response          = e.getResponse();
        PendingConnection connection = e.getConnection();

        if (connection == null) return;

        boolean whitelistEnabled     = plugin.getPlayersConfig().getBoolean("whitelist.toggle");

        int max    = response.getPlayers().getMax();
        int online = response.getPlayers().getOnline();

        if (whitelistEnabled) {
            showMotd = plugin.getMotdUtils().getMotd(true);
            showMode = MotdType.WHITELIST_MOTD;
        } else {
            showMotd = plugin.getMotdUtils().getMotd(false);
            showMode = MotdType.NORMAL_MOTD;
        }

        showType = ShowType.WITHOUT_HEX;

        if (e.getConnection().getVersion() >= 735) {
            if (plugin.getMotdUtils().getHexStatus(showMode, showMotd)) {
                showType = ShowType.HEX;
            }
        }

        // TODO ICON STATUS
        // TODO PLAYER STATUS

        if (plugin.getMotdUtils().isCustomProtocolEnabled(showMode)) {
            ServerPing.Protocol received = response.getVersion();

            received.setName(plugin.getMotdUtils().getCustomProtocol(showMode));

            if (plugin.getMotdUtils().getProtocolVersion(showMode)) {
                received.setProtocol(-1);
            }

            protocol = received;

        } else {
            protocol = response.getVersion();
        }

        motdHover = plugin.getMotdUtils().getHover(showMode);
        mHover    = plugin.getMotdUtils().isCustomHoverEnabled(showMode);

        if (mHover) {
            players = new ServerPing.Players(max, online, motdHover);
        } else {
            players = new ServerPing.Players(max, online, response.getPlayers().getSample());
        }

        line1 = plugin.getMotdUtils().getFirstLine(showMode, showMotd, showType);
        line2 = plugin.getMotdUtils().getSecondLine(showMode, showMotd, showType);

        motd = color(line1 + "\n" + line2);

        ServerPing result;

        if (showType == ShowType.HEX) {
            debug("ShowType HEX");
            result = new ServerPing(protocol, players, new TextComponent(motd), null);
        } else {
            debug("ShowType WITHOUT_HEX");
            result = new ServerPing(protocol, players, new TextComponent(TextComponent.fromLegacyText(motd)), null);
        }

        e.setResponse(result);
    }
}
