package club.rigox.bungee.commands.subcommands;

import club.rigox.bungee.PixelMOTD;
import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.*;
import net.md_5.bungee.api.CommandSender;

import static club.rigox.bungee.utils.Logger.sendMessage;

@CommandAlias("bpmotd")
@CommandPermission("pixelmotd.blacklist.manage")
public class BlacklistCommand extends BaseCommand {
    private final PixelMOTD plugin;

    public BlacklistCommand(PixelMOTD plugin) {
        this.plugin = plugin;
    }

    @Subcommand("blacklist")
    public void sendWhitelistHelp(CommandSender sender) {
        sendMessage(sender, plugin.getMessagesConfig().getStringList("help.blacklist"));
    }

    @Subcommand("add")
    @CommandCompletion("@players")
    public void onBlacklistAdd(CommandSender sender, @Single String player) {
    }
}
