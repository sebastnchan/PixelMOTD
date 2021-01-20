package club.rigox.bungee.utils;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.ComponentBuilder;

public class Logger {
    /**
     * Colorize a string provided to method
     * for proxies.
     *
     * @param message Message to transform.
     * @return transformed message with colors.
     */
    public static String color(String message) {
        return ChatColor.translateAlternateColorCodes('&', message);
    }

    /**
     * Send a error message to console.
     * @param message message to send.
     */
    public static void error(String message) {
        sendMessage("&f[&cERROR &7| &fPixel MOTD] " + message);
    }

    /**
     * Send a warn message to console.
     * @param message message to send.
     */
    public static void warn(String message) {
        sendMessage("&f[&eWARN &7| &fPixel MOTD] " + message);
    }

    /**
     * Send a debug message to console.
     * @param message message to send.
     */
    public static void debug(String message) {
        sendMessage("&f[&9DEBUG &7| &fPixel MOTD] " + message);
    }

    /**
     * Send a info message to console.
     * @param message message to send.
     */
    public static void info(String message) {
        sendMessage("&f[&bINFO &7| &fPixel MOTD] " + message);
    }

    /**
     * Sends a message to a Proxied Player.
     *
     * @param player Proxied Player
     * @param message Message to send.
     */
    public static void sendMessage(CommandSender player, String message) {
        player.sendMessage(new ComponentBuilder(color(message)).create());
    }

    /**
     * Used to other methods and prevent this copy pasta
     * to those methods.
     *
     * @param message Provided message
     */
    private static void sendMessage(String message) {
        ProxyServer.getInstance().getConsole().sendMessage(new ComponentBuilder(color(message)).create());
    }
}