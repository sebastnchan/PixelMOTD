package club.rigox.bungee.listeners;

import club.rigox.bungee.PixelMOTD;
import club.rigox.bungee.enums.ConfigType;
import net.md_5.bungee.api.event.LoginEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

public class ProxyEvent implements Listener {
    private final PixelMOTD plugin;

    public ProxyEvent (PixelMOTD plugin) {
        this.plugin = plugin;
        plugin.getProxy().getPluginManager().registerListener(plugin, this);
    }

    @EventHandler
    public void onLogin(LoginEvent e) {
        String whitelistMode = plugin.get(ConfigType.EDITABLE).getString("whitelist.check-mode");

        if (plugin.get(ConfigType.EDITABLE).getString("whitelist.check-mode").equalsIgnoreCase("LoginEvent")) {

        }

    }
}
