package club.rigox.bungee.listeners;

import club.rigox.bungee.PixelMOTD;
import club.rigox.bungee.utils.Converter;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.event.LoginEvent;
import net.md_5.bungee.api.event.PostLoginEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

import java.util.List;

import static club.rigox.bungee.utils.Logger.color;
import static club.rigox.bungee.utils.Logger.debug;

public class WhitelistEvent implements Listener {
    private final PixelMOTD plugin;

    public WhitelistEvent(PixelMOTD plugin) {
        this.plugin = plugin;
        plugin.getProxy().getPluginManager().registerListener(plugin, this);
    }

    @EventHandler
    public void onLogin(LoginEvent e) {
        debug("asd");
        if (e.isCancelled()) return;

        debug("Llegamos aki");
        String connectionName        = e.getConnection().getName();
        String connectionUuid        = e.getConnection().getUniqueId().toString();

        List<String> whitelistPlayer = plugin.getEditableFile().getStringList("whitelist.players-name");
        List<String> whitelistUuid   = plugin.getEditableFile().getStringList("whitelist.players-uuid");
        List<String> whitelistMsg    = plugin.getEditableFile().getStringList("whitelist.kick-message");

        boolean whitelistCheck       = plugin.getEditableFile().getString("whitelist.check-mode").equalsIgnoreCase("LoginEvent");
        boolean whitelistToggle      = plugin.getEditableFile().getBoolean("whitelist.toggle");

        debug(String.valueOf(whitelistCheck));
        debug(String.valueOf(whitelistToggle));

        if (whitelistCheck && whitelistToggle) {
            if (!whitelistPlayer.contains(connectionName) && !whitelistUuid.contains(connectionUuid)) {
                String kickReason = plugin.getConverter().fromListToString(whitelistMsg);

                e.setCancelReason(new TextComponent(color(kickReason
                        .replace("%whitelist_author%", plugin.getPlaceholders().getWhitelistAuthor())
                        .replace("%type%", "Server"))));
                debug("Toy aki");
            }
            return;
        }

        List<String> blacklistPlayer = plugin.getEditableFile().getStringList("blacklist.players-name");
        List<String> blacklistUuid   = plugin.getEditableFile().getStringList("blacklist.players-uuid");
        List<String> blacklistMsg    = plugin.getEditableFile().getStringList("blacklist.kick-message");

        boolean blacklistToggle      = plugin.getEditableFile().getBoolean("blacklist.toggle");

        if (blacklistToggle) {
            if (blacklistPlayer.contains(connectionName) || blacklistUuid.contains(connectionUuid)) {
                String kickReason = plugin.getConverter().fromListToString(blacklistMsg);

                e.setCancelReason(new TextComponent(color(kickReason
                        .replace("%nick%", connectionName))
                        .replace("%type%", "Server")));
            }
        }

        // TODO Block words in name
    }

    @EventHandler
    public void onPostLoginEvent(PostLoginEvent event) {
        if(plugin.getEditableFile().getString("whitelist.check-mode").equalsIgnoreCase("LoginEvent")) {
            if(plugin.getEditableFile().getBoolean("whitelist.toggle")) {
                if(!plugin.getEditableFile().getStringList("whitelist.players-name").contains(event.getPlayer().getName()) && !plugin.getEditableFile().getStringList("whitelist.players-uuid").contains(event.getPlayer().getUniqueId().toString())) {
                    String kickReason = plugin.getConverter().fromListToString(plugin.getEditableFile().getStringList("whitelist.kick-message"));
                    event.getPlayer().disconnect(new TextComponent(ChatColor.translateAlternateColorCodes('&', kickReason.replace("%whitelist_author%", plugin.getPlaceholders().getWhitelistAuthor()).replace("%type%","Server"))));
                }
            }
        }
    }
}
