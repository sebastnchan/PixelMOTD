package club.rigox.bungee.utils;

import club.rigox.bungee.PixelMOTD;

public class Placeholders {
    private final PixelMOTD plugin;

    public Placeholders(PixelMOTD plugin) {
        this.plugin = plugin;
    }

    public String getWhitelistAuthor() {
        String author             = plugin.getEditableFile().getString("whitelist.author");
        String customAuthor       = plugin.getEditableFile().getString("whitelist.customConsoleName.name");

        boolean customAuthorCheck = plugin.getEditableFile().getBoolean("whitelist.customConsoleName.toggle");

        if (!author.equalsIgnoreCase("CONSOLE"))
            return author;

        if (customAuthorCheck)
            return customAuthor;

        return "Console";
    }
}
