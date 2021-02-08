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

    private static final String UUID_PATTERN = "([a-f0-9]{8}(-[a-f0-9]{4}){4}[a-f0-9]{8})";

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

                plugin.getPlayersConfig().set("whitelist.players-uuid", whitelistUuid);
                plugin.reloadConfigs();

                debug("Editable was reloaded (UUID - Whitelist)");
                break;

            case WHITELIST_PLAYER:
                List<String> whitelistPlayer = new ArrayList<>();
                whitelistPlayer.add(toAdd);

                plugin.getPlayersConfig().set("whitelist.players-name", whitelistPlayer);
                plugin.reloadConfigs();

                debug("Editable was reloaded (Player - Whitelist)");
                break;

            case BLACKLIST_PLAYER:
                List<String> blacklistPlayer = new ArrayList<>();
                blacklistPlayer.add(toAdd);

                plugin.getPlayersConfig().set("blacklist.players-name", blacklistPlayer);
                plugin.reloadConfigs();

                debug("Editable was reloaded (Player - Blacklist)");
                break;

            case BLACKLIST_UUID:
                List<String> blacklistUUID = new ArrayList<>();
                blacklistUUID.add(toAdd);

                plugin.getPlayersConfig().set("blacklist.players-uuid", blacklistUUID);
                plugin.loadConfigs();

                debug("Editable was reloaded (UUID - Blacklist)");
                break;

            default:
                error("Something wrong happen. Please inform this to the author.");
        }
    }

    public void kickOnWhitelist(KickType type) {
        List<String> whitelistMsg       = plugin.getMessagesConfig().getStringList("whitelist.kick-message");
        List<String> blacklistMsg       = plugin.getMessagesConfig().getStringList("blacklist.kick-message");

        List<String> whitelistedPlayers = plugin.getPlayersConfig().getStringList("whitelist.players-name");
        List<String> whitelistedUuids   = plugin.getPlayersConfig().getStringList("whitelist.players-uuid");

        List<String> blacklistedPlayers = plugin.getPlayersConfig().getStringList("blacklist.players-name");
        List<String> blacklistedUuids   = plugin.getPlayersConfig().getStringList("blacklist.players-uuid");

        String whitelistReason               = plugin.getConverter().fromListToString(whitelistMsg);
        String blacklistReason               = plugin.getConverter().fromListToString(blacklistMsg);

        for (ProxiedPlayer player : plugin.getProxy().getPlayers()) {
            switch (type) {
                case WHITELIST_UUID:
                    if (!whitelistedUuids.contains(player.getUniqueId().toString())) {
                        if (whitelistedPlayers.contains(player.toString())) {
                            return;
                        }

                        player.disconnect(new TextComponent(color(whitelistReason
                                .replace("%whitelist_author%", plugin.getPlaceholders().getWhitelistAuthor()))));
                        break;
                    }

                case WHITELIST_PLAYER:
                    if (!whitelistedPlayers.contains(player.toString())) {
                        if (whitelistedUuids.contains(player.getUniqueId().toString())) {
                            return;
                        }

                        player.disconnect(new TextComponent(color(whitelistReason
                                .replace("%whitelist_author%", plugin.getPlaceholders().getWhitelistAuthor()))));
                        break;
                    }

                case BLACKLIST:
                    if (blacklistedUuids.contains(player.getUniqueId().toString()) ||
                        blacklistedPlayers.contains(player.toString())) {

                        player.disconnect(new TextComponent(color(blacklistReason)));
                        break;
                    }

                default:
                    break;
            }
        }

    }
}
