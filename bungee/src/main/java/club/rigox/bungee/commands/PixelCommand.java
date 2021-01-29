package club.rigox.bungee.commands;

import club.rigox.bungee.PixelMOTD;
import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;

@CommandAlias("bpmotd")
public class PixelCommand extends BaseCommand {
    public PixelMOTD plugin;

    public PixelCommand (PixelMOTD plugin) {
        this.plugin = plugin;
    }

}
