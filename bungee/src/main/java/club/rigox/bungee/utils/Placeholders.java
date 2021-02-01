package club.rigox.bungee.utils;

import club.rigox.bungee.PixelMOTD;

public class Placeholders {
    private final PixelMOTD plugin;

    public Placeholders(PixelMOTD plugin) {
        this.plugin = plugin;
    }

    public String getWhitelistAuthor() {
        String author             = plugin.getPlayersConfig().getString("whitelist.author");
        String customAuthor       = plugin.getPlayersConfig().getString("whitelist.custom-console-name.name");

        boolean customAuthorCheck = plugin.getPlayersConfig().getBoolean("whitelist.custom-console-name.toggle");

        if (!author.equalsIgnoreCase("CONSOLE"))
            return author;

        if (customAuthorCheck)
            return customAuthor;

        return "Console";
    }
}
