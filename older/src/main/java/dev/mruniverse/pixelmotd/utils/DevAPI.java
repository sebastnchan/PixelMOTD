package dev.mruniverse.pixelmotd.utils;

import dev.mruniverse.pixelmotd.enums.MotdType;
import dev.mruniverse.pixelmotd.files.spigotControl;

public class DevAPI {
    public DevAPI() {
        latestMotdName = "exampleMotd1";
        latestMotdType = MotdType.NORMAL_MOTD;
    }
    public String latestMotdName;
    public MotdType latestMotdType;
    public String getLatestMotd() {
        return latestMotdName;
    }
    public MotdType getLatestMotdType() {
        return latestMotdType;
    }


}
