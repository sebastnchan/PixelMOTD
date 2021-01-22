package club.rigox.bungee.listeners;

import club.rigox.bungee.PixelMOTD;
import net.md_5.bungee.api.event.LoginEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

import java.util.List;

public class WhitelistEvent implements Listener {
    private final PixelMOTD plugin;

    public WhitelistEvent(PixelMOTD plugin) {
        this.plugin = plugin;
        plugin.getProxy().getPluginManager().registerListener(plugin, this);
    }

    @EventHandler
    public void onLogin(LoginEvent e) {
        if (e.isCancelled()) return;

        String connectionName        = e.getConnection().getName();
        String connectionUuid        = e.getConnection().getUniqueId().toString();

        List<String> whitelistPlayer = plugin.getEditableFile().getStringList("whitelist.players-name");
        List<String> whitelistUuid   = plugin.getEditableFile().getStringList("whitelist.players-uuid");
        List<String> kickMessage     = plugin.getEditableFile().getStringList("whitelist.kick-message");

        boolean whitelistCheck       = plugin.getEditableFile().getString("whitelist.check-mode").equalsIgnoreCase("LoginEvent");
        boolean whitelistToggle      = plugin.getEditableFile()     .getBoolean("whitelist.toggle");

        if (whitelistCheck && whitelistToggle) {
            if (!whitelistPlayer.contains(connectionName) || !whitelistUuid.contains(connectionUuid)) {
                String kickReason = plugin.getConverter().fromListToString(kickMessage);
//                e.setCancelReason(new TextComponent(ChatColor.translateAlternateColorCodes('&', kickReason.replace("%whitelist_author%", getWhitelistAuthor()).replace("%type%", "Server"))));
//                e.setCancelReason(new TextComponent(color(kickReason.replace("%whitelist_author%", get))));
            }

        }

    }
}
