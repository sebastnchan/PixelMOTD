package club.rigox.bungee.utils;

import club.rigox.bungee.PixelMOTD;
import club.rigox.bungee.enums.MotdType;
import club.rigox.bungee.enums.ShowType;
import net.md_5.bungee.api.ServerPing;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Motd {
    private final PixelMOTD plugin;

    public Motd(PixelMOTD plugin) {
        this.plugin = plugin;
    }

    public String getMotd(boolean isWhitelisted) {
        List<String> getMotd = new ArrayList<>();

        if (isWhitelisted) {
            getMotd.addAll(plugin.getMotdConfig().getSection("whitelist.motds").getKeys());
            return getMotd.get(new Random().nextInt(getMotd.size()));
        }

        getMotd.addAll(plugin.getMotdConfig().getSection("normal.motds").getKeys());
        return getMotd.get(new Random().nextInt(getMotd.size()));
    }

    public boolean getHexStatus(MotdType motdType, String motdName) {
        switch (motdType) {
            case NORMAL_MOTD:
                return plugin.getMotdConfig().getBoolean(String.format("normal.motds.%s.with-hex.enable", motdName));
            case WHITELIST_MOTD:
                return plugin.getMotdConfig().getBoolean(String.format("whitelist.motds.%s.with-hex.enable", motdName));
//          TODO  case TIMER_MOTD:
//          TODO      return plugin.getMotdConfig().getBoolean(String.format("timer.motds.%s.with-hex.enable"));
            default:
                break;
        }
        return false;
    }

    public String getFirstLine(MotdType motdType, String motdName, ShowType showType) {
        switch (motdType) {
            case NORMAL_MOTD:
                if (showType == ShowType.HEX) {
                    return plugin.getMotdConfig().getString(String.format("normal.motds.%s.with-hex.line-1", motdName));
                }

                return plugin.getMotdConfig().getString(String.format("normal.motds.%s.line-1", motdName));

            case WHITELIST_MOTD:
                if (showType == ShowType.HEX) {
                    return plugin.getMotdConfig().getString(String.format("whitelist.motds.%s.with-hex.line-1", motdName));
                }
                return plugin.getMotdConfig().getString(String.format("whitelist.motds.%s.line-1", motdName));

            // TODO TIMER MOTD
        }
        return null;
    }

    public String getSecondLine(MotdType motdType, String motdName, ShowType showType) {
        switch (motdType) {
            case NORMAL_MOTD:
                if (showType == ShowType.HEX) {
                    return plugin.getMotdConfig().getString(String.format("normal.motds.%s.with-hex.line-2", motdName));
                }
                return plugin.getMotdConfig().getString(String.format("normal.motds.%s.line-2", motdName));

            case WHITELIST_MOTD:
                if (showType == ShowType.HEX) {
                    return plugin.getMotdConfig().getString(String.format("whitelist.motds.%s.with-hex.line-2", motdName));
                }
                return plugin.getMotdConfig().getString(String.format("whitelist.motds.%s.line-2", motdName));

            // TODO TIMER MOTD
        }
        return null;
    }

    public boolean isCustomProtocolEnabled(MotdType motdType) {
        if (motdType == MotdType.NORMAL_MOTD) {
            return plugin.getMotdConfig().getBoolean("normal.settings.custom-protocol.enable");
        }

        if (motdType == MotdType.WHITELIST_MOTD) {
            return plugin.getMotdConfig().getBoolean("whitelist.settings.custom-protocol.enable");
        }

        return false;
    }

    public boolean getProtocolVersion(MotdType motdType) {
        if (motdType == MotdType.NORMAL_MOTD) {
            return plugin.getMotdConfig().getBoolean("normal.settings.custom-protocol.change-protocol-version");
        }

        if (motdType == MotdType.WHITELIST_MOTD) {
            return plugin.getMotdConfig().getBoolean("whitelist.settings.custom-protocol.change-protocol-version");
        }

        return false;
    }

    public String getCustomProtocol(MotdType motdType) {
        if (motdType == MotdType.NORMAL_MOTD) {
            return plugin.getMotdConfig().getString("normal.settings.custom-protocol-name");
        }

        if (motdType == MotdType.WHITELIST_MOTD) {
            return plugin.getMotdConfig().getString("whitelist.settings.custom-protocol-name");
        }

        return null;
    }

    private ServerPing.PlayerInfo[] addHoverLine(ServerPing.PlayerInfo[] player, ServerPing.PlayerInfo info) {
        ServerPing.PlayerInfo[] hoverText = new ServerPing.PlayerInfo[player.length + 1];

        System.arraycopy(player, 0, hoverText, 0, player.length);

        hoverText[player.length] = info;
        return hoverText;
    }

    public ServerPing.PlayerInfo[] getHover(MotdType motdType, String motdName) {
        ServerPing.PlayerInfo[] hoverToShow = new ServerPing.PlayerInfo[0];

        int ids = 0;

        String type = "normal";

        if (motdType == MotdType.WHITELIST_MOTD) {
            type = "whitelist";
        }

        for (String line : plugin.getMotdConfig().getStringList(String.format("%s.motds.%s.hover.message", type, motdName))) {
            hoverToShow = addHoverLine(hoverToShow, new ServerPing.PlayerInfo((line.replace("&","§")), String.valueOf(ids)));
            ids++;
        }

        hoverToShow = addHoverLine(hoverToShow, new ServerPing.PlayerInfo("", ""));
        return hoverToShow;
    }

    public boolean isCustomHoverEnabled(MotdType motdType, String motdName) {
        switch (motdType) {
            case NORMAL_MOTD:
                return plugin.getMotdConfig().getBoolean(String.format("normal.motds.%s.hover.toggle", motdName));
            case WHITELIST_MOTD:
                return plugin.getMotdConfig().getBoolean(String.format("whitelist.motds.%s.hover.toggle", motdName));
            case TIMER_MOTD:
                // TODO
        }
        return true;
    }

    public boolean getIconStatus(MotdType motdType) {
        if (motdType == MotdType.NORMAL_MOTD) {
            return plugin.getMotdConfig().getBoolean("normal.settings.icon");
        }

        if (motdType == MotdType.WHITELIST_MOTD) {
            return plugin.getMotdConfig().getBoolean("whitelist.settings.icon");
        }
        return false;
    }
}
