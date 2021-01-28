package club.rigox.bungee.commands;

import club.rigox.bungee.PixelMOTD;
import club.rigox.bungee.enums.ConfigType;
import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.*;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.TextComponent;

import java.io.IOException;

import static club.rigox.bungee.utils.Logger.color;

@CommandAlias("bpmotd")
public class PixelCommand extends BaseCommand {
    public PixelMOTD plugin;

    public PixelCommand (PixelMOTD plugin) {
        this.plugin = plugin;
    }

    @Subcommand("whitelist")
    @CommandPermission("pixelmotd.command.whitelist.toggle")
    public void onWhitelist(CommandSender sender, @Optional String server, @Single @Optional String toggle) {
        if (server == null && toggle == null) {
            sender.sendMessage(new TextComponent(color("&aWhitelist command help.")));
            return;
        }

        if (toggle == null) {
            sender.sendMessage(new TextComponent(color("Global whitelist status")));
            return;
        }

        if (toggle.equalsIgnoreCase("on")) {
            return;
        }

        if (toggle.equalsIgnoreCase("off")) {
            return;
        }
    }

    @Subcommand("whitelist global")
    @CommandPermission("pixelmotd.command.whitelist.toggle")
    @CommandCompletion("on|off")
    public void onGlobalWhitelist(CommandSender sender, @Optional @Single String toggle) throws IOException {
        if (toggle == null) {
            sender.sendMessage(new TextComponent(color("Global whitelist status")));
            return;
        }

        if (toggle.equalsIgnoreCase("off")) {
            plugin.getEditableFile().set("whitelist.toggle", false);

            plugin.loadConfigs();
            plugin.reloadEditable();

            sender.sendMessage(new TextComponent(color("&cWhitelist disabled.")));
            return;
        }

        if (toggle.equalsIgnoreCase("on")) {
            plugin.getEditableFile().set("whitelist.toggle", true);

            plugin.loadConfigs();
            plugin.reloadEditable();

            sender.sendMessage(new TextComponent(color("&aWhitelist enabled.")));
        }
    }

    // TODO Whitelist per server
}
