package club.rigox.bungee.commands;

import club.rigox.bungee.PixelMOTD;
import club.rigox.bungee.enums.ConfigType;
import club.rigox.bungee.enums.KickType;
import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.CommandPermission;
import co.aikar.commands.annotation.Single;
import co.aikar.commands.annotation.Subcommand;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.TextComponent;

import java.util.List;

import static club.rigox.bungee.commands.CommandUtils.isUuid;
import static club.rigox.bungee.utils.Logger.color;
import static club.rigox.bungee.utils.Logger.sendMessage;

@CommandAlias("bpmotd")
public class PixelCommand extends BaseCommand {
    public PixelMOTD plugin;

    public PixelCommand (PixelMOTD plugin) {
        this.plugin = plugin;
    }

    @Subcommand("whitelist on")
    @CommandPermission("pixelmotd.command.whitelist.toggle")
    public void onWhitelistEnable(CommandSender sender) {
        plugin.getEditableFile().set("whitelist.toggle", true);
        plugin.getManager().reloadConfig(ConfigType.EDITABLE);

        sender.sendMessage(new TextComponent(color("&aWhitelist enabled.")));
    }

    @Subcommand("whitelist off")
    @CommandPermission("pixelmotd.command.whitelist.toggle")
    public void onWhitelistDisable(CommandSender sender) {
        plugin.getEditableFile().set("whitelist.toggle", false);
        plugin.getManager().reloadConfig(ConfigType.EDITABLE);

        sender.sendMessage(new TextComponent(color("&cWhitelist disabled.")));
    }

    @Subcommand("whitelist add")
    @CommandPermission("pixelmotd.command.add")
    public void onAdd(CommandSender sender, @Single String player) {
        List<String> uuidList   = plugin.getEditableFile().getStringList("whitelist.players-uuid");
        List<String> playerList = plugin.getEditableFile().getStringList("whitelist.players-name");

        if (isUuid(player)) {

            if (plugin.getEditableFile().get("whitelist.players-uuid") == null) {

                plugin.getCmdUtils().initEditList(KickType.WHITELIST_UUID, player);
                sendMessage(sender, String.format("UUID %s has been added to the whitelist!", player));
                return;
            }

            if (uuidList.contains(player)) {
                sendMessage(sender, String.format("%s uuid is already on the whitelist!", player));
                return;
            }

            uuidList.add(player);

            plugin.getEditableFile().set("whitelist.players-uuid", uuidList);
            plugin.getManager().reloadConfig(ConfigType.EDITABLE);

            sendMessage(sender, String.format("UUID %s has been added to the whitelist!", player));
            return;
        }

        if (plugin.getEditableFile().get("whitelist.players-name") == null) {
            plugin.getCmdUtils().initEditList(KickType.WHITELIST_PLAYER, player);
            sendMessage(sender, String.format("Player %s has been added to the whitelist!", player));
            return;
        }

        playerList.add(player);

        plugin.getEditableFile().set("whitelist.players-name", playerList);
        plugin.getManager().reloadConfig(ConfigType.EDITABLE);

        sendMessage(sender, String.format("Player %s has been added to the whitelist!", player));
    }
}
