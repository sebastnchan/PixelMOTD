package club.rigox.bungee.commands.subcommands;

import club.rigox.bungee.PixelMOTD;
import club.rigox.bungee.enums.KickType;
import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.*;
import net.md_5.bungee.api.CommandSender;

import java.util.List;

import static club.rigox.bungee.commands.CommandUtils.isUuid;
import static club.rigox.bungee.utils.FileManager.getMessageString;
import static club.rigox.bungee.utils.Logger.sendMessage;

@CommandAlias("bpmotd")
@CommandPermission("pixelmotd.whitelist.manage pixelmotd.admin")
public class WhitelistCommand extends BaseCommand {
    public PixelMOTD plugin;

    public WhitelistCommand (PixelMOTD plugin) {
        this.plugin = plugin;
    }

    @Subcommand("whitelist")
    public void sendWhitelistHelp(CommandSender sender) {
        sendMessage(sender, plugin.getMessagesConfig().getStringList("help.whitelist"));
    }

    @Subcommand("whitelist on")
    @CommandPermission("pixelmotd.whitelist.manage")
    public void onWhitelistEnable(CommandSender sender) {
        plugin.getCmdUtils().kickOnWhitelist(KickType.WHITELIST_UUID);
        plugin.getCmdUtils().kickOnWhitelist(KickType.WHITELIST_PLAYER);

        plugin.getDataConfig().set("whitelist.toggle", true);
        plugin.saveConfigs();

        sendMessage(sender, getMessageString("whitelist.enabled"));
    }

    @Subcommand("whitelist off")
    public void onWhitelistDisable(CommandSender sender) {
        plugin.getDataConfig().set("whitelist.toggle", false);
        plugin.saveConfigs();

        sendMessage(sender, getMessageString("whitelist.disabled"));
    }

    @Subcommand("whitelist add")
    @CommandCompletion("@players")
    public void onAdd(CommandSender sender, @Single String player) {
        List<String> uuidList   = plugin.getDataConfig().getStringList("whitelist.players-uuid");
        List<String> playerList = plugin.getDataConfig().getStringList("whitelist.players-name");

        if (isUuid(player)) {
            if (plugin.getDataConfig().get("whitelist.players-uuid") == null) {

                plugin.getCmdUtils().initEditList(KickType.WHITELIST_UUID, player);
                sendMessage(sender, String.format(getMessageString("whitelist.uuid.added"), player));
                return;
            }

            if (uuidList.contains(player)) {
                sendMessage(sender, String.format(getMessageString("whitelist.uuid.already"), player));
                return;
            }

            uuidList.add(player);

            plugin.getDataConfig().set("whitelist.players-uuid", uuidList);
            plugin.saveConfigs();

            sendMessage(sender, String.format(getMessageString("whitelist.uuid.added"), player));
            return;
        }

        if (plugin.getDataConfig().get("whitelist.players-name") == null) {
            plugin.getCmdUtils().initEditList(KickType.WHITELIST_PLAYER, player);
            sendMessage(sender, String.format(getMessageString("whitelist.player.added"), player));
            return;
        }

        if (playerList.contains(player)) {
            sendMessage(sender, String.format(getMessageString("whitelist.player.already"), player));
            return;
        }

        playerList.add(player);

        plugin.getDataConfig().set("whitelist.players-name", playerList);
        plugin.saveConfigs();

        sendMessage(sender, String.format(getMessageString("whitelist.player.added"), player));
    }

    @Subcommand("whitelist remove")
    @CommandCompletion("@players")
    public void onWhitelistRemove(CommandSender sender, String player) {
        List<String> uuidList   = plugin.getDataConfig().getStringList("whitelist.players-uuid");
        List<String> playerList = plugin.getDataConfig().getStringList("whitelist.players-name");

        if (isUuid(player)) {
            if (!uuidList.contains(player)) {
                sendMessage(sender, String.format(getMessageString("whitelist.uuid.not-on"), player));
                return;
            }

            uuidList.remove(player);

            plugin.getDataConfig().set("whitelist.players-uuid", uuidList);
            plugin.saveConfigs();

            plugin.getCmdUtils().kickOnWhitelist(KickType.WHITELIST_UUID);

            sendMessage(sender, String.format(getMessageString("whitelist.uuid.removed"), player));

            return;
        }

        if (!playerList.contains(player)) {
            sendMessage(sender, String.format(getMessageString("whitelist.player.not-on"), player));
            return;
        }

        playerList.remove(player);

        plugin.getDataConfig().set("whitelist.players-name", playerList);
        plugin.saveConfigs();

        plugin.getCmdUtils().kickOnWhitelist(KickType.WHITELIST_PLAYER);

        sendMessage(sender, String.format(getMessageString("whitelist.player.removed"), player));
    }
}
