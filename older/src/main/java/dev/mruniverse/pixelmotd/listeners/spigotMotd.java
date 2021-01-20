package dev.mruniverse.pixelmotd.listeners;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.events.ListenerPriority;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketEvent;
import com.comphenix.protocol.wrappers.WrappedServerPing;
import dev.mruniverse.pixelmotd.enums.*;
import dev.mruniverse.pixelmotd.files.spigotControl;
import dev.mruniverse.pixelmotd.init.spigotPixelMOTD;
import dev.mruniverse.pixelmotd.manager.WrappedStatus;
import dev.mruniverse.pixelmotd.utils.spigotUtils;
import org.bukkit.entity.Player;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

@SuppressWarnings({"UnstableApiUsage", "CatchMayIgnoreException"})
public class spigotMotd {
    private final PacketAdapter packetAdapter = new PacketAdapter(spigotPixelMOTD.getInstance(), ListenerPriority.HIGH, PacketType.Status.Server.SERVER_INFO) {
        public void onPacketSending(PacketEvent e) {
            if (e.getPacketType() != PacketType.Status.Server.SERVER_INFO)
                return;
            if(e.isCancelled()) return;
            WrappedStatus packet = new WrappedStatus(e.getPacket());
            WrappedServerPing ping = packet.getJsonResponse();

            //** load MotdType
            MotdType motdType;
            ShowType showType;

            //* load strings & integers
            String line1,line2,motd,motdName;
            int max,online;
            max = ping.getPlayersMaximum();
            online = ping.getPlayersOnline();

                if (spigotControl.getControl(Files.EDITABLE).getBoolean("whitelist.toggle")) {
                    motdName = spigotControl.getMotd(true);
                    motdType = MotdType.WHITELIST_MOTD;
                } else {
                    motdName = spigotControl.getMotd(false);
                    motdType = MotdType.NORMAL_MOTD;
                }
                showType = ShowType.FIRST;

                //* Motd Version Setup
                if (ProtocolLibrary.getProtocolManager().getProtocolVersion(e.getPlayer()) >= 721) {
                    if (spigotUtils.getHexMotdStatus(motdType, motdName)) {
                        showType = ShowType.SECOND;
                    }
                }
            try {
                //* Favicon Setup
                //* Custom Server Icon Setup
                if(spigotUtils.getIconStatus(motdType,motdName,false)) {
                    File[] icons;
                    if(spigotUtils.getIconStatus(motdType,motdName,true)) {
                        icons = spigotUtils.getIcons(motdType,motdName).listFiles();
                    } else {
                        if(motdType.equals(MotdType.NORMAL_MOTD)) {
                            icons = spigotPixelMOTD.getFiles().getFile(Icons.NORMAL).listFiles();
                        } else {
                            icons = spigotPixelMOTD.getFiles().getFile(Icons.WHITELIST).listFiles();
                        }
                    }
                    if (icons != null && icons.length != 0) {
                        List<File> validIcons = new ArrayList<>();
                        for (File image : icons) {
                            if (com.google.common.io.Files.getFileExtension(image.getPath()).equals("png")) {
                                validIcons.add(image);
                            }
                        }
                        if (validIcons.size() != 0) {
                            WrappedServerPing.CompressedImage image = getImage(validIcons.get(new Random().nextInt(validIcons.size())));
                            if (image != null) ping.setFavicon(image);
                        }
                    }
                }
                //* Players Setup
                if(spigotUtils.getPlayersStatus(motdType,motdName)) {
                    if(spigotUtils.getPlayersMode(motdType,motdName).equals(ValueMode.ADD)) {
                        max = online + 1;
                    }
                    if(spigotUtils.getPlayersMode(motdType,motdName).equals(ValueMode.CUSTOM)) {
                        max = spigotUtils.getPlayersValue(motdType,motdName);
                    }
                    if(spigotUtils.getPlayersMode(motdType,motdName).equals(ValueMode.HALF)) {
                        if(online >= 2) {
                            max = online / 2;
                        } else {
                            max = 0;
                        }
                    }
                    if(spigotUtils.getPlayersMode(motdType,motdName).equals(ValueMode.HALF_ADD)) {
                        int add;
                        if(online >= 2) {
                            add = online / 2;
                        } else {
                            add = 0;
                        }
                        max = online + add;
                    }
                    if(spigotUtils.getPlayersMode(motdType,motdName).equals(ValueMode.EQUAL)) {
                        max = online;
                    }
                }
                if(spigotUtils.getOnlineStatus(motdType,motdName)) {
                    if(spigotUtils.getOnlineMode(motdType,motdName).equals(ValueMode.ADD)) {
                        online = online + 1;
                    }
                    if(spigotUtils.getOnlineMode(motdType,motdName).equals(ValueMode.CUSTOM)) {
                        online = spigotUtils.getOnlineValue(motdType,motdName);
                    }
                    if(spigotUtils.getOnlineMode(motdType,motdName).equals(ValueMode.HALF)) {
                        if(online >= 2) {
                            online = online / 2;
                        } else {
                            online = 0;
                        }
                    }
                    if(spigotUtils.getOnlineMode(motdType,motdName).equals(ValueMode.HALF_ADD)) {
                        int add;
                        if(online >= 2) {
                            add = online / 2;
                        } else {
                            add = 0;
                        }
                        online = online + add;
                    }
                    if(spigotUtils.getOnlineMode(motdType,motdName).equals(ValueMode.EQUAL)) {
                        online = max;
                    }
                }
                ping.setPlayersOnline(online);
                ping.setPlayersMaximum(max);
                //* Motd hover Setup
                if (spigotUtils.getHoverStatus(motdType, motdName)) {
                    ping.setPlayers(spigotUtils.getHover(motdType, motdName, online, max));
                }
                if (spigotUtils.getProtocolStatus(motdType, motdName)) {
                    ping.setVersionName(spigotPixelMOTD.getHex().applyColor(spigotUtils.replaceProtocolVariables(spigotUtils.getProtocolMessage(motdType, motdName), online, max, getName(e.getPlayer().getName(), e.getPlayer()))));
                    if (spigotUtils.getProtocolVersion(motdType, motdName)) { ping.setVersionProtocol(-1); } else { ping.setVersionProtocol(ProtocolLibrary.getProtocolManager().getProtocolVersion(e.getPlayer()));}
                }

                //* Motd Setup
                line1 = spigotUtils.getLine1(motdType, motdName, showType);
                line2 = spigotUtils.getLine2(motdType, motdName, showType);
                motd = spigotUtils.replaceVariables(line1, online, max) + "\n" + spigotUtils.replaceVariables(line2, online, max);
                ping.setMotD(motd);

            } catch(Throwable ignored) {
                spigotPixelMOTD.motdIssue(motdType.name(), motdName);
                if(spigotControl.isDetailed()) {
                    spigotPixelMOTD.sendConsole("&a[Pixel MOTD] [Detailed Error] Information: ");
                    //if(ignored.getCause().toString() != null) {
                    //    spigotPixelMOTD.sendConsole("&a[Pixel MOTD] Cause: " + ignored.getCause().toString());
                    //}
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
                    if(Arrays.toString(ignored.getSuppressed()) != null) {
                        spigotPixelMOTD.sendConsole("&a[Pixel MOTD] Suppressed: " + Arrays.toString(ignored.getSuppressed()));
                    }
                    spigotPixelMOTD.sendConsole("&a[Pixel MOTD] Class: " + ignored.getClass().getName() +".class");
                    spigotPixelMOTD.sendConsole("&a[Pixel MOTD] Plugin version:" + spigotPixelMOTD.getInstance().getDescription().getVersion());
                    spigotPixelMOTD.sendConsole("&a[Pixel MOTD] --------------- [Detailed Error]");
                }
            }
        }
    };
    private WrappedServerPing.CompressedImage getImage(File file) {
        try {
            return WrappedServerPing.CompressedImage.fromPng(ImageIO.read(file));
        } catch(IOException exception) {
            reportBadImage(file.getPath());
            return null;
        }
    }
    private void reportBadImage(String filePath) {
        spigotPixelMOTD.sendConsole("Can't read image: &b" + filePath + "&f. Please check image size: 64x64 or check if the image isn't corrupted.");
    }
    private String getName(String userName, Player player) {
        if (userName.contains("UNKNOWN") &&
                userName.contains(player.getAddress() + ""))
            userName = spigotControl.getControl(Files.SETTINGS).getString("settings.defaultUnknownUserName");
        return userName;
    }
    @SuppressWarnings("unused")
    public PacketAdapter getPacketAdapter() {
        return this.packetAdapter;
    }
}