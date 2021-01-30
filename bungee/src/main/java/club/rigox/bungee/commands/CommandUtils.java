package club.rigox.bungee.commands;

import club.rigox.bungee.PixelMOTD;
import club.rigox.bungee.enums.ConfigType;
import club.rigox.bungee.enums.KickType;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static club.rigox.bungee.utils.Logger.*;

public class CommandUtils {
    private final PixelMOTD plugin;

    static final String UUID_PATTERN = "([a-f0-9]{8}(-[a-f0-9]{4}){4}[a-f0-9]{8})";

    public CommandUtils (PixelMOTD plugin) {
        this.plugin = plugin;
    }

    public static boolean isUuid(String str) {
        Pattern pattern = Pattern.compile(UUID_PATTERN);
        Matcher matcher = pattern.matcher(str);

        debug(String.format("UUID Match for %s is %s", str, matcher.matches()));
        return matcher.matches();
    }

    public void initEditList(KickType type, String toAdd) {
        switch (type) {
            case WHITELIST_UUID:
                List<String> whitelistUuid = new ArrayList<>();
                whitelistUuid.add(toAdd);

                plugin.getEditableFile().set("whitelist.players-uuid", whitelistUuid);
                plugin.getManager().reloadConfig(ConfigType.EDITABLE);
                debug("Editable was reloaded (UUID)");

                return;
            case WHITELIST_PLAYER:
                List<String> whitelistPlayer = new ArrayList<>();
                whitelistPlayer.add(toAdd);

                plugin.getEditableFile().set("whitelist.players-name", whitelistPlayer);
                plugin.getManager().reloadConfig(ConfigType.EDITABLE);
                debug("Editable was reloaded (Player)");

                return;
            default:
                error("Something wrong happen. Please inform this to the author.");
        }
    }

    public void kickOnWhitelist(KickType type) {
        List<String> whitelistMsg       = plugin.getEditableFile().getStringList("whitelist.kick-message");

        List<String> whitelistedPlayers = plugin.getEditableFile().getStringList("whitelist.players-name");
        List<String> whitelistedUuids   = plugin.getEditableFile().getStringList("whitelist.players-uuid");

        String kickReason               = plugin.getConverter().fromListToString(whitelistMsg);

        for (ProxiedPlayer player : plugin.getProxy().getPlayers()) {
            switch (type) {
                case WHITELIST_UUID:
                    if (!whitelistedUuids.contains(player.getUniqueId().toString())) {
                        if (whitelistedPlayers.contains(player.toString())) {
                            return;
                        }

                        player.disconnect(new TextComponent(color(kickReason
                                .replace("%whitelist_author%", plugin.getPlaceholders().getWhitelistAuthor()))
                                .replace("%type%", "Server")));
                        return;
                    }

                case WHITELIST_PLAYER:
                    if (!whitelistedPlayers.contains(player.toString())) {
                        if (whitelistedUuids.contains(player.getUniqueId().toString())) {
                            return;
                        }

                        player.disconnect(new TextComponent(color(kickReason
                                .replace("%whitelist_author%", plugin.getPlaceholders().getWhitelistAuthor()))
                                .replace("%type%", "Server")));
                        return;
                    }
            }
        }

    }
}
