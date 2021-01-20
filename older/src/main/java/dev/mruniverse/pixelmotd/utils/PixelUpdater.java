package dev.mruniverse.pixelmotd.utils;

import dev.mruniverse.pixelmotd.files.bungeeControl;
import dev.mruniverse.pixelmotd.files.spigotControl;
import dev.mruniverse.pixelmotd.init.bungeePixelMOTD;
import dev.mruniverse.pixelmotd.init.spigotPixelMOTD;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.Arrays;
import javax.net.ssl.HttpsURLConnection;


public class PixelUpdater {
    private final String currentVersion;
    private String newestVersion;
    public PixelUpdater(boolean isBungee,int projectID) {
        if(isBungee) {
            currentVersion = bungeePixelMOTD.getInstance().getDescription().getVersion();
        } else {
            currentVersion = spigotPixelMOTD.getInstance().getDescription().getVersion();
        }
        try {
            URL url = new URL("https://api.spigotmc.org/legacy/update.php?resource=" + projectID);
            HttpsURLConnection connection;
            connection = (HttpsURLConnection) url.openConnection();
            connection.connect();
            BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = br.readLine()) != null) {
                sb.append(line).append("\n");
            }
            br.close();
            newestVersion = sb.toString();
        } catch (IOException ignored) {
            if(isBungee) {
                bungeePixelMOTD.redIssue();
                if(bungeeControl.isDetailed()) {
                    bungeePixelMOTD.sendConsole("&a[Pixel MOTD] [Detailed Error] Information:");
                    if(ignored.getMessage() != null) {
                        bungeePixelMOTD.sendConsole("&a[Pixel MOTD] Message: " + ignored.getMessage());
                    }
                    if(ignored.getLocalizedMessage() != null) {
                        bungeePixelMOTD.sendConsole("&a[Pixel MOTD] LocalizedMessage: " + ignored.getLocalizedMessage());
                    }
                    if(ignored.getStackTrace() != null) {
                        bungeePixelMOTD.sendConsole("&a[Pixel MOTD] StackTrace: ");
                        for(StackTraceElement line : ignored.getStackTrace()) {
                            bungeePixelMOTD.sendConsole("&a[Pixel MOTD] (" + line.getLineNumber() + ") " + line.toString());
                        }
                    }
                    if(ignored.getSuppressed() != null) {
                        bungeePixelMOTD.sendConsole("&a[Pixel MOTD] Suppressed: " + Arrays.toString(ignored.getSuppressed()));
                    }
                    if(ignored.getClass() != null) {
                        bungeePixelMOTD.sendConsole("&a[Pixel MOTD] Class: " + ignored.getClass().getName());
                    }
                    bungeePixelMOTD.sendConsole("&a[Pixel MOTD] Plugin version:" + bungeePixelMOTD.getInstance().getDescription().getVersion());
                    bungeePixelMOTD.sendConsole("&a[Pixel MOTD] --------------- [Detailed Error]");
                }
            } else {
                spigotPixelMOTD.redIssue();
                if(spigotControl.isDetailed()) {
                    spigotPixelMOTD.sendConsole("&a[Pixel MOTD] [Detailed Error] Information:");
                    if(ignored.getMessage() != null) {
                        spigotPixelMOTD.sendConsole("&a[Pixel MOTD] Message: " + ignored.getMessage());
                    }
                    if(ignored.getLocalizedMessage() != null) {
                        spigotPixelMOTD.sendConsole("&a[Pixel MOTD] LocalizedMessage: " + ignored.getLocalizedMessage());
                    }
                    if(ignored.getStackTrace() != null) {
                        spigotPixelMOTD.sendConsole("&a[Pixel MOTD] StackTrace: ");
                        for(StackTraceElement line : ignored.getStackTrace()) {
                            spigotPixelMOTD.sendConsole("&a[Pixel MOTD] (" + line.getLineNumber() + ") " + line.toString());
                        }
                    }
                    if(ignored.getSuppressed() != null) {
                        spigotPixelMOTD.sendConsole("&a[Pixel MOTD] Suppressed: " + Arrays.toString(ignored.getSuppressed()));
                    }
                    if(ignored.getClass() != null) {
                        spigotPixelMOTD.sendConsole("&a[Pixel MOTD] Class: " + ignored.getClass().getName());
                    }
                    spigotPixelMOTD.sendConsole("&a[Pixel MOTD] Plugin version:" + spigotPixelMOTD.getInstance().getDescription().getVersion());
                    spigotPixelMOTD.sendConsole("&a[Pixel MOTD] --------------- [Detailed Error]");
                }
            }
        }
    }
    public String getVersionResult() {
        String update;
        String[] installed;
        if(currentVersion == null) {
            return "RED_PROBLEM";
        }
        update = currentVersion;
        if(currentVersion.contains(".")) update= currentVersion.replace(".","");
        installed= update.split("-");
        if(installed[1] != null) {
            if(installed[1].toLowerCase().contains("pre")) {
                if(installed[1].toLowerCase().contains("alpha")) {
                    return "PRE_ALPHA_VERSION";
                }
                return "PRE_RELEASE";
            }
            if(installed[1].toLowerCase().contains("alpha")) {
                return "ALPHA_VERSION";
            }
            if(installed[1].toLowerCase().contains("release")) {
                return "RELEASE";
            }
        }
        return "RELEASE";
    }
    public String getUpdateResult() {
        int using,latest;
        String update;
        String[] installed, spigot;
        //Version Verificator

        if(currentVersion == null || newestVersion == null) {
            return "RED_PROBLEM";
        }

        //Version Setup

        //* First Setup

        update= currentVersion;
        if(currentVersion.contains(".")) update= currentVersion.replace(".","");
        installed= update.split("-");
        update= newestVersion;
        if(newestVersion.contains(".")) update= newestVersion.replace(".","");
        spigot= update.split("-");

        //* Second Setup

        using= Integer.parseInt(installed[0]);
        latest= Integer.parseInt(spigot[0]);

        //Result Setup
        if(using == latest) {
            if(installed[1].equalsIgnoreCase(spigot[1])) {
                return "UPDATED";
            }
            return "NEW_VERSION";
        }
        if(using < latest) {
            return "NEW_VERSION";
        }
        if(using > latest) {
            if(installed[1].toLowerCase().contains("pre")) {
                if(installed[1].toLowerCase().contains("alpha")) {
                    return "PRE_ALPHA_VERSION";
                }
                return "BETA_VERSION";
            }
            if(installed[1].toLowerCase().contains("alpha")) {
                return "ALPHA_VERSION";
            }
        }
        return "RED_PROBLEM";
    }
}
