package club.rigox.bungee.commands;

import club.rigox.bungee.PixelMOTD;
import club.rigox.bungee.enums.ConfigType;
import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.*;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;

import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import static club.rigox.bungee.commands.CommandUtils.isUuid;
import static club.rigox.bungee.utils.Logger.*;

@CommandAlias("bpmotd")
public class PixelCommand extends BaseCommand {
    public PixelMOTD plugin;

    public PixelCommand (PixelMOTD plugin) {
        this.plugin = plugin;
    }

//    @Subcommand("whitelist")
//    @CommandPermission("pixelmotd.command.whitelist.toggle")
//    public void onWhitelist(CommandSender sender, @Optional String server, @Single @Optional String toggle) {
//        if (server == null && toggle == null) {
//            sender.sendMessage(new TextComponent(color("&aWhitelist command help.")));
//            return;
//        }
//
//        if (toggle == null) {
//            sender.sendMessage(new TextComponent(color("Global whitelist status")));
//            return;
//        }
//
//        if (toggle.equalsIgnoreCase("on")) {
//            return;
//        }
//
//        if (toggle.equalsIgnoreCase("off")) {
//            return;
//        }
//    }

    @Subcommand("whitelist")
    @CommandPermission("pixelmotd.command.whitelist.toggle")
    @CommandCompletion("on|off")
    public void onGlobalWhitelist(CommandSender sender, @Optional @Single String toggle) {
        if (toggle == null) {
            sender.sendMessage(new TextComponent(color("Global whitelist status")));
            return;
        }

        if (toggle.equalsIgnoreCase("off")) {
            plugin.getEditableFile().set("whitelist.toggle", false);
            plugin.getManager().reloadConfig(ConfigType.EDITABLE);

            sender.sendMessage(new TextComponent(color("&cWhitelist disabled.")));
            return;
        }

        if (toggle.equalsIgnoreCase("on")) {
            plugin.getEditableFile().set("whitelist.toggle", true);
            plugin.getManager().reloadConfig(ConfigType.EDITABLE);

            sender.sendMessage(new TextComponent(color("&aWhitelist enabled.")));
        }
    }

    @Subcommand("add")
    @CommandPermission("pixelmotd.command.add")
    @CommandCompletion("whitelist|blacklist ")
    public void onAdd(CommandSender sender, String type, @Single String player) {
        if (type.equalsIgnoreCase("whitelist")) {
            if (isUuid(player)) {
                List<String> uuidList = plugin.getEditableFile().getStringList("whitelist.players-uuid");

                if (plugin.getEditableFile().get("whitelist.players-uuid") == null) {
                    List<String> list = new ArrayList<>();
                    list.add(player);

                    plugin.getEditableFile().set("whitelist.players-uuid", list);
                    plugin.getManager().reloadConfig(ConfigType.EDITABLE);

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

                return;
            }

            List<String> list = new ArrayList<>();
            list.add(player);

            plugin.getEditableFile().set("whitelist.players-name", list);
            plugin.getManager().reloadConfig(ConfigType.EDITABLE);

            sendMessage(sender, String.format("Player %s has been added to the whitelist!", player));
            return;
        }

        if (type.equalsIgnoreCase("blacklist")) {
            return;
        }
    }
}
