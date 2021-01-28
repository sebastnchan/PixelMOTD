package club.rigox.bungee.commands;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static club.rigox.bungee.utils.Logger.debug;

public class CommandUtils {
    static final String UUID_PATTERN = "([a-f0-9]{8}(-[a-f0-9]{4}){4}[a-f0-9]{8})";

    public static boolean isUuid(String str) {
        Pattern pattern = Pattern.compile(UUID_PATTERN);
        Matcher matcher = pattern.matcher(str);

        debug(String.format("Match for %s is %s", str, matcher.matches()));
        return matcher.matches();
    }
}
