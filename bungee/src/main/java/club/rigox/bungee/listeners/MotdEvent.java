package club.rigox.bungee.listeners;

import club.rigox.bungee.PixelMOTD;
import net.md_5.bungee.api.Favicon;
import net.md_5.bungee.api.ServerPing;
import net.md_5.bungee.api.connection.PendingConnection;
import net.md_5.bungee.api.event.ProxyPingEvent;
import net.md_5.bungee.api.plugin.Cancellable;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;
import net.md_5.bungee.protocol.Protocol;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MotdEvent implements Listener {
    private final PixelMOTD plugin;

    public MotdEvent(PixelMOTD plugin) {
        this.plugin = plugin;
    }

    public String getMotd(boolean isWhitelisted) {
        List<String> getMotd = new ArrayList<>();

        if (isWhitelisted) {
            getMotd.addAll(plugin.getMotdConfig().getSection("whitelist.motds").getKeys());
            return getMotd.get(new Random().nextInt(getMotd.size()));
        }

        getMotd.addAll(plugin.getMotdConfig().getSection("normal.motds").getKeys());
        return getMotd.get(new Random().nextInt(getMotd.size()));
    }

    @EventHandler
    public void onProxyPingEvent(ProxyPingEvent e) {

        // Verify for cancelled motd to prevent errors.
        if (e instanceof Cancellable && ((Cancellable) e).isCancelled()) return;
        if (e.getResponse() == null) return;

        // Server ping.
        Favicon                 favicon = null;
        Protocol                protocol;
        ServerPing.Players      players;
        ServerPing.PlayerInfo[] motdHover;

        ServerPing response          = e.getResponse();
        PendingConnection connection = e.getConnection();

        // String & integers.
        String line1, line2, motd;

        int max    = response.getPlayers().getMax();
        int online = response.getPlayers().getOnline();

        boolean whitelistEnabled     = plugin.getPlayersConfig().getBoolean("whitelist-enabled");

        getMotd(whitelistEnabled);

        // TODO Motd HEX color compatibility
    }
}
