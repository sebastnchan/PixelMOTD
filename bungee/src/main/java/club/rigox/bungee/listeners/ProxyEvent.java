package club.rigox.bungee.listeners;

import club.rigox.bungee.PixelMOTD;
import net.md_5.bungee.api.plugin.Listener;

public class ProxyEvent implements Listener {
    private final PixelMOTD plugin;

    public ProxyEvent (PixelMOTD plugin) {
        this.plugin = plugin;
    }
}
