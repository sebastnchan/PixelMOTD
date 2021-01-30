package club.rigox.bungee.commands;

import club.rigox.bungee.PixelMOTD;
import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.CommandPermission;
import co.aikar.commands.annotation.Subcommand;
import net.md_5.bungee.api.CommandSender;

import static club.rigox.bungee.utils.Logger.sendMessage;

@CommandAlias("bpmotd")
public class PixelCommand extends BaseCommand {
    public PixelMOTD plugin;

    public PixelCommand (PixelMOTD plugin) {
        this.plugin = plugin;
    }

    @Subcommand("reload")
    @CommandPermission("pixelmotd.admin")
    public void onReload(CommandSender sender) {
        plugin.reloadConfigs();

        sendMessage(sender, "&aConfig has been reloaded!");
    }

}
