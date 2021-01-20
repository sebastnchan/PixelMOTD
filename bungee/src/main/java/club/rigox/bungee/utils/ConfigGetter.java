package club.rigox.bungee.utils;

import club.rigox.bungee.PixelMOTD;
import club.rigox.bungee.enums.ConfigType;

import java.util.Objects;

import static club.rigox.bungee.utils.Logger.error;

public class ConfigGetter {
    private static final PixelMOTD plugin = PixelMOTD.instance;

    /**
     * Get String of specified
     * @param configType get specified file type
     * @param path string path
     * @return string specified if not null.
     */
    public static String getString(ConfigType configType, String path) {
        String getString = plugin.get(configType).getString(path);

        if (getString == null) {
            error(String.format("The folowing path %s doesn't exists on %s! Please add it.", path, configType));
            return "&cPlease inform this error to an administrator and let it know to review the console.";
        }

        return Objects.requireNonNull(getString);
    }
}
