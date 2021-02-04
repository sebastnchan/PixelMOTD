package club.rigox.bungee.utils;

import net.md_5.bungee.api.ChatColor;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static club.rigox.bungee.utils.Logger.color;
import static club.rigox.bungee.utils.Logger.debug;

public class NewHexColor {
    private static final Pattern HEX = Pattern.compile("#[a-fA-F0-9]{6}");

    public String formatToHex(String message) {
        Matcher match = HEX.matcher(message);

        while (match.find()) {
            String color = message.substring(match.start(), match.end());
            message      = message.replace(color, ChatColor.of(color) + "");
            match        = HEX.matcher(message);
        }

        debug("formatToHex method was called.");
        return color(message);
    }
}
