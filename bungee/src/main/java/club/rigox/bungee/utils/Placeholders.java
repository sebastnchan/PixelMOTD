package club.rigox.bungee.utils;

import club.rigox.bungee.PixelMOTD;

public class Placeholders {
    private final PixelMOTD plugin;

    public Placeholders(PixelMOTD plugin) {
        this.plugin = plugin;
    }

    public String getWhitelistAuthor() {
        String author             = plugin.getDataConfig().getString("whitelist.author");
        String customAuthor       = plugin.getDataConfig().getString("whitelist.custom-console-name.name");

        boolean customAuthorCheck = plugin.getDataConfig().getBoolean("whitelist.custom-console-name.toggle");

        if (!author.equalsIgnoreCase("CONSOLE"))
            return author;

        if (customAuthorCheck)
            return customAuthor;

        return "Console";
    }
}
