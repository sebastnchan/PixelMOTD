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

    private Favicon favicon = null;

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

        String   showMotd;
        MotdType showMode;
        if (whitelistEnabled) {
            showMotd = plugin.getMotdUtils().getMotd(true);
            showMode = MotdType.WHITELIST_MOTD;
        } else {
            showMotd = plugin.getMotdUtils().getMotd(false);
            showMode = MotdType.NORMAL_MOTD;
        }

        ShowType showType = ShowType.WITHOUT_HEX;

        if (e.getConnection().getVersion() >= 735 && plugin.getMotdUtils().getHexStatus(showMode, showMotd))
            showType = ShowType.HEX;

        // TODO ICON STATUS
        // TODO PLAYER STATUS

        if (plugin.getMotdUtils().getIconStatus(showMode)) {

        }

        ServerPing.Protocol protocol;
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

        ServerPing.PlayerInfo[] motdHover = plugin.getMotdUtils().getHover(showMode);
        boolean mHover = plugin.getMotdUtils().isCustomHoverEnabled(showMode);

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
            result = new ServerPing(protocol, players, new TextComponent(motd), null);
        } else {
            debug("ShowType WITHOUT_HEX");
            result = new ServerPing(protocol, players, new TextComponent(TextComponent.fromLegacyText(motd)), null);
        }

        e.setResponse(result);
    }
}
