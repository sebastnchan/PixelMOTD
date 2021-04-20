package club.rigox.bungee.listeners;

import club.rigox.bungee.PixelMOTD;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.event.PostLoginEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

import java.util.List;

import static club.rigox.bungee.utils.Logger.color;
import static club.rigox.bungee.utils.Logger.info;

public class WhitelistEvent implements Listener {
    private final PixelMOTD plugin;

    public WhitelistEvent(PixelMOTD plugin) {
        this.plugin = plugin;
        plugin.getProxy().getPluginManager().registerListener(plugin, this);
        info("&eWhitelist &7& &eblacklist &7listener loaded");
    }

    @EventHandler
    public void onPostLoginEvent(PostLoginEvent e) {
        String connectionName        = e.getPlayer().getName();
        String connectionUuid        = e.getPlayer().getUniqueId().toString();

        String bypassPermission      = plugin.getConfig().getString("whitelist.permision-bypass");

        List<String> whitelistPlayer = plugin.getDataConfig().getStringList("whitelist.players-name");
        List<String> whitelistUuid   = plugin.getDataConfig().getStringList("whitelist.players-uuid");
        List<String> whitelistMsg    = plugin.getMessagesConfig().getStringList("whitelist.kick-message");

        boolean whitelistToggle = plugin.getDataConfig().getBoolean("whitelist.toggle");

        if (e.getPlayer().hasPermission(bypassPermission)) {
            return;
        }

        if (whitelistToggle && !whitelistPlayer.contains(connectionName) && !whitelistUuid.contains(connectionUuid)) {
            String kickReason = plugin.getConverter().fromListToString(whitelistMsg);
            e.getPlayer().disconnect(
                    new TextComponent(color(kickReason
                            .replace("%whitelist_author%", plugin.getPlaceholders().getWhitelistAuthor())
                            .replace("%type%","Server")))
            );
        }

        List<String> blacklistPlayer = plugin.getDataConfig().getStringList("blacklist.players-name");
        List<String> blacklistUuid   = plugin.getDataConfig().getStringList("blacklist.players-uuid");
        List<String> blacklistMsg    = plugin.getMessagesConfig().getStringList("blacklist.kick-message");

        if (blacklistPlayer.contains(connectionName) || blacklistUuid.contains(connectionUuid)) {
            String kickReason = plugin.getConverter().fromListToString(blacklistMsg);

            e.getPlayer().disconnect(new TextComponent(color(kickReason)));
        }

    }
}
