package club.rigox.bungee.commands;

import club.rigox.bungee.PixelMOTD;
import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.CommandPermission;
import co.aikar.commands.annotation.HelpCommand;
import co.aikar.commands.annotation.Subcommand;
import net.md_5.bungee.api.CommandSender;

import static club.rigox.bungee.utils.FileManager.getMessageString;
import static club.rigox.bungee.utils.Logger.sendMessage;

@CommandAlias("bpmotd")
@CommandPermission("pixelmotd.admin")
public class PixelCommand extends BaseCommand {
    public PixelMOTD plugin;

    public PixelCommand (PixelMOTD plugin) {
        this.plugin = plugin;
    }

    @HelpCommand
    public void onHelp(CommandSender sender) {
        sendMessage(sender, plugin.getMessagesConfig().getStringList("help.main"));
    }

    @Subcommand("reload")
    public void onReload(CommandSender sender) {
        plugin.loadConfigs();
        sendMessage(sender, getMessageString("reloaded"));
    }
}
