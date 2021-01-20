package dev.mruniverse.pixelmotd.commands;

import dev.mruniverse.pixelmotd.enums.*;
import dev.mruniverse.pixelmotd.files.spigotControl;
import dev.mruniverse.pixelmotd.init.spigotPixelMOTD;
import dev.mruniverse.pixelmotd.utils.Extras;
import dev.mruniverse.pixelmotd.utils.spigotUtils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class spigotCommand implements CommandExecutor {
    private final String cmd;

    public spigotCommand(String command) {
        this.cmd = command;
    }
    private String getUniqueId(CommandSender sender) {
        if(sender instanceof Player) {
            Player player = (Player)sender;
            return player.getUniqueId().toString();
        }
        return "??";
    }
    private boolean hasPermission(CommandSender sender, String permission) {
        if(sender instanceof Player) {
            Player player = (Player)sender;
            if (!player.hasPermission(permission)) {
                spigotUtils.sendColored(player, spigotUtils.getPermissionMessage(permission));
            }
            return player.hasPermission(permission);
        }
        return true;
    }
    private String getStatus(String location) {
        if(location.equalsIgnoreCase("Global")) {
            if(spigotControl.getWhitelistStatus()) {
                return spigotControl.getControl(Files.COMMAND).getString("command.status.on");
            }
            return spigotControl.getControl(Files.COMMAND).getString("command.status.off");
        }
        if(spigotControl.getControl(Files.MODULES).get("modules.world-whitelist.worlds." + location +".whitelist-status") != null) {
            if(spigotControl.getControl(Files.MODULES).getBoolean("modules.world-whitelist.worlds." + location +".whitelist-status")) {
                return spigotControl.getControl(Files.COMMAND).getString("command.status.on");
            }
            return spigotControl.getControl(Files.COMMAND).getString("command.status.off");
        }
        return spigotControl.getControl(Files.COMMAND).getString("command.status.off");
    }
    private String getAuthor(String location) {
        if(spigotControl.getControl(Files.MODULES).get("modules.world-whitelist.worlds." + location +".whitelist-author") != null) {
            return spigotControl.getControl(Files.COMMAND).getString("modules.world-whitelist.worlds." + location +".whitelist-author");
        }
        return "Console";
    }
    @SuppressWarnings("all")
    private String getOnline(String playerName) {
        if(spigotPixelMOTD.getInstance().getServer().getPlayer(playerName) != null) {
            try {
                if (Objects.requireNonNull(spigotPixelMOTD.getInstance().getServer().getPlayer(playerName)).isOnline()) {
                    return spigotControl.getControl(Files.COMMAND).getString("command.online-status.online").replace("%server%",Objects.requireNonNull(spigotPixelMOTD.getInstance().getServer().getPlayer(playerName).getWorld().getName()));
                }
                return spigotControl.getControl(Files.COMMAND).getString("command.online-status.offline");
            }catch(Throwable throwable) {
                return spigotControl.getControl(Files.COMMAND).getString("command.online-status.offline");
            }
        }

        return spigotControl.getControl(Files.COMMAND).getString("command.online-status.offline");
    }
    private void sendMain(CommandSender sender) {
        for(String lines : spigotControl.getControl(Files.COMMAND).getStringList("command.help")) {
            if(lines.contains("%cmd%")) lines = lines.replace("%cmd%", cmd);
            if(lines.contains("%author%")) lines = lines.replace("%author%", spigotPixelMOTD.getInstance().getDescription().getAuthors().toString());
            if(lines.contains("%version%")) lines = lines.replace("%version%", spigotPixelMOTD.getInstance().getDescription().getVersion());
            spigotUtils.sendColored(sender,lines);
        }
    }
    @SuppressWarnings({"NullableProblems", "ConstantConditions"})
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        try {
            if (spigotControl.isCommandEnabled()) {
                if (args.length == 0 || args[0].equalsIgnoreCase("help")) {
                    if (hasPermission(sender, "pixelmotd.command.help")) {
                        sendMain(sender);
                    }
                    return true;
                }
                //motdLists.add("&6/%cmd% &e- &fMain Command");
                //if (bungeeMode) {
                //    motdLists.add("&6/%cmd% whitelist (global-serverName) [on-off]");
                //} else {
                //    motdLists.add("&6/%cmd% whitelist (global-worldName) [on-off]");
                //}
                //motdLists.add("&6/%cmd% add (whitelist-blacklist) (playerName-playerUUID) &e- &fadd a player to your list.");
                //motdLists.add("&6/%cmd% remove (whitelist-blacklist) (playerName-playerUUID) &e- &fremove a player from your list.");
                //motdLists.add("&6/%cmd% reload (all-settings-edit-modules-cmd-motds)");
                //motdLists.add("&6/%cmd% modules toggle (moduleName)");
                //motdLists.add("&6/%cmd% modules info (moduleName)");
                //motdLists.add("&6/%cmd% modules list");
                //motdLists.add("&6/%cmd% externalModules toggle (moduleName)");
                //motdLists.add("&6/%cmd% externalModules info (moduleName)");
                //motdLists.add("&6/%cmd% externalModules list");
                if (args[0].equalsIgnoreCase("whitelist")) {
                    if (args.length == 1) {
                        if (hasPermission(sender, "pixelmotd.command.whitelist.toggle")) {
                            sendMain(sender);
                            return true;
                        }
                        return true;
                    }
                    if (args[1].equalsIgnoreCase("global")) {
                        if (args.length == 2) {
                            for(String lines : spigotControl.getControl(Files.COMMAND).getStringList("command.whitelist.list.top")) {
                                if(lines.contains("%cmd%")) lines = lines.replace("%cmd%", cmd);
                                if(lines.contains("%author%")) lines = lines.replace("%author%", spigotControl.getWhitelistAuthor());
                                if(lines.contains("%version%")) lines = lines.replace("%version%", spigotPixelMOTD.getInstance().getDescription().getVersion());
                                if(lines.contains("%whitelist%")) lines = lines.replace("%whitelist%", "Global");
                                if(lines.contains("%status%")) lines = lines.replace("%status%", getStatus("Global"));
                                if(lines.contains("<isUser>")) lines = lines.replace("<isUser>","");
                                if(lines.contains("%your_uuid%")) lines = lines.replace("%your_uuid%",getUniqueId(sender));
                                spigotUtils.sendColored(sender,lines);
                            }
                            for(String players : spigotControl.getControl(Files.EDITABLE).getStringList("whitelist.players-name")) {
                                String line= spigotControl.getControl(Files.COMMAND).getString("command.whitelist.list.playersNameFormat");
                                if(line == null) line = "&e&l* &8[&7%online_status%&8] &7%player_name%";
                                if(line.contains("%online_status%")) line = line.replace("%online_status%",getOnline(players));
                                if(line.contains("%player_name%")) line = line.replace("%player_name%",players);
                                spigotUtils.sendColored(sender,line);
                            }
                            for(String lines : spigotControl.getControl(Files.COMMAND).getStringList("command.whitelist.list.mid")) {
                                if(lines.contains("%cmd%")) lines = lines.replace("%cmd%", cmd);
                                if(lines.contains("%author%")) lines = lines.replace("%author%", spigotControl.getWhitelistAuthor());
                                if(lines.contains("%version%")) lines = lines.replace("%version%", spigotPixelMOTD.getInstance().getDescription().getVersion());
                                if(lines.contains("%whitelist%")) lines = lines.replace("%whitelist%", "Global");
                                if(lines.contains("%status%")) lines = lines.replace("%status%", getStatus("Global"));
                                if(lines.contains("<isUser>")) lines = lines.replace("<isUser>","");
                                if(lines.contains("%your_uuid%")) lines = lines.replace("%your_uuid%",getUniqueId(sender));
                                spigotUtils.sendColored(sender,lines);
                            }
                            for(String uuids : spigotControl.getControl(Files.EDITABLE).getStringList("whitelist.players-uuid")) {
                                String line= spigotControl.getControl(Files.COMMAND).getString("command.whitelist.list.playersUuidFormat");
                                if(line == null) line = "&e&l* &8[&7UUID&8] &7%player_uuid%";
                                if(line.contains("%player_uuid%")) line = line.replace("%online_status%","??");
                                if(line.contains("%player_name%")) line = line.replace("%player_name%","??");
                                if(line.contains("%player_uuid%")) line = line.replace("%player_uuid%",uuids);
                                spigotUtils.sendColored(sender,line);
                            }
                            for(String lines : spigotControl.getControl(Files.COMMAND).getStringList("command.whitelist.list.bot")) {
                                if(lines.contains("%cmd%")) lines = lines.replace("%cmd%", cmd);
                                if(lines.contains("%author%")) lines = lines.replace("%author%", spigotControl.getWhitelistAuthor());
                                if(lines.contains("%version%")) lines = lines.replace("%version%", spigotPixelMOTD.getInstance().getDescription().getVersion());
                                if(lines.contains("%whitelist%")) lines = lines.replace("%whitelist%", "Global");
                                if(lines.contains("%status%")) lines = lines.replace("%status%", getStatus("Global"));
                                if(lines.contains("<isUser>")) lines = lines.replace("<isUser>","");
                                if(lines.contains("%your_uuid%")) lines = lines.replace("%your_uuid%",getUniqueId(sender));
                                spigotUtils.sendColored(sender,lines);
                            }
                            return true;
                        }
                        if(args[2].equalsIgnoreCase("on")) {
                            spigotControl.getControl(Files.EDITABLE).set("whitelist.toggle",true);
                            spigotControl.getControl(Files.EDITABLE).set("whitelist.author","Console");
                            spigotControl.save(SaveMode.EDITABLE);
                            spigotControl.reloadFile(SaveMode.EDITABLE);
                            spigotUtils.sendColored(sender,spigotControl.getControl(Files.EDITABLE).getString("messages.whitelist-enabled"));
                            return true;
                        }
                        if(args[2].equalsIgnoreCase("off")) {
                            spigotControl.getControl(Files.EDITABLE).set("whitelist.toggle",false);
                            spigotControl.getControl(Files.EDITABLE).set("whitelist.author","Console");
                            spigotControl.save(SaveMode.EDITABLE);
                            spigotControl.reloadFile(SaveMode.EDITABLE);
                            spigotUtils.sendColored(sender,spigotControl.getControl(Files.EDITABLE).getString("messages.whitelist-disabled"));
                            return true;
                        }
                        return true;
                    }
                    if (args.length == 2) {
                        for(String lines : spigotControl.getControl(Files.COMMAND).getStringList("command.whitelist.list.top")) {
                            if(lines.contains("%cmd%")) lines = lines.replace("%cmd%", cmd);
                            if(lines.contains("%author%")) lines = lines.replace("%author%", getAuthor(args[1]));
                            if(lines.contains("%version%")) lines = lines.replace("%version%", spigotPixelMOTD.getInstance().getDescription().getVersion());
                            if(lines.contains("%whitelist%")) lines = lines.replace("%whitelist%", args[1]);
                            if(lines.contains("%status%")) lines = lines.replace("%status%", getStatus(args[1]));
                            if(lines.contains("<isUser>")) lines = lines.replace("<isUser>","");
                            if(lines.contains("%your_uuid%")) lines = lines.replace("%your_uuid%",getUniqueId(sender));
                            spigotUtils.sendColored(sender,lines);
                        }
                        for(String players : spigotUtils.getPlayers(WhitelistMembers.NAMEs,args[1])) {
                            String line= spigotControl.getControl(Files.COMMAND).getString("command.whitelist.list.playersNameFormat");
                            if(line == null) line = "&e&l* &8[&7%online_status%&8] &7%player_name%";
                            if(line.contains("%online_status%")) line = line.replace("%online_status%",getOnline(players));
                            if(line.contains("%player_name%")) line = line.replace("%player_name%",players);
                            spigotUtils.sendColored(sender,line);
                        }
                        for(String lines : spigotControl.getControl(Files.COMMAND).getStringList("command.whitelist.list.mid")) {
                            if(lines.contains("%cmd%")) lines = lines.replace("%cmd%", cmd);
                            if(lines.contains("%author%")) lines = lines.replace("%author%", getAuthor(args[1]));
                            if(lines.contains("%version%")) lines = lines.replace("%version%", spigotPixelMOTD.getInstance().getDescription().getVersion());
                            if(lines.contains("%whitelist%")) lines = lines.replace("%whitelist%", args[1]);
                            if(lines.contains("%status%")) lines = lines.replace("%status%", getStatus(args[1]));
                            if(lines.contains("<isUser>")) lines = lines.replace("<isUser>","");
                            if(lines.contains("%your_uuid%")) lines = lines.replace("%your_uuid%",getUniqueId(sender));
                            spigotUtils.sendColored(sender,lines);
                        }
                        for(String uuids : spigotUtils.getPlayers(WhitelistMembers.UUIDs,args[1])) {
                            String line= spigotControl.getControl(Files.COMMAND).getString("command.whitelist.list.playersUuidFormat");
                            if(line == null) line = "&e&l* &8[&7UUID&8] &7%player_uuid%";
                            if(line.contains("%player_uuid%")) line = line.replace("%online_status%","??");
                            if(line.contains("%player_name%")) line = line.replace("%player_name%","??");
                            if(line.contains("%player_uuid%")) line = line.replace("%player_uuid%",uuids);
                            spigotUtils.sendColored(sender,line);
                        }
                        for(String lines : spigotControl.getControl(Files.COMMAND).getStringList("command.whitelist.list.bot")) {
                            if(lines.contains("%cmd%")) lines = lines.replace("%cmd%", cmd);
                            if(lines.contains("%author%")) lines = lines.replace("%author%", getAuthor(args[1]));
                            if(lines.contains("%version%")) lines = lines.replace("%version%", spigotPixelMOTD.getInstance().getDescription().getVersion());
                            if(lines.contains("%whitelist%")) lines = lines.replace("%whitelist%", args[1]);
                            if(lines.contains("%status%")) lines = lines.replace("%status%", getStatus(args[1]));
                            if(lines.contains("<isUser>")) lines = lines.replace("<isUser>","");
                            if(lines.contains("%your_uuid%")) lines = lines.replace("%your_uuid%",getUniqueId(sender));
                            spigotUtils.sendColored(sender,lines);
                        }
                        return true;
                    }
                    if(args[2].equalsIgnoreCase("on")) {
                        spigotControl.getControl(Files.MODULES).set(Extras.getWorldPath(Whitelist.STATUS,args[1]),true);
                        if(sender instanceof Player) {
                            spigotControl.getControl(Files.MODULES).set(Extras.getWorldPath(Whitelist.AUTHOR, args[1]), sender.getName());
                        } else {
                            spigotControl.getControl(Files.MODULES).set(Extras.getWorldPath(Whitelist.AUTHOR, args[1]), "Console");
                        }
                        spigotControl.save(SaveMode.MODULES);
                        spigotControl.reloadFile(SaveMode.MODULES);
                        spigotUtils.sendColored(sender,spigotControl.getControl(Files.EDITABLE).getString("messages.whitelist-enabled"));
                        return true;
                    }
                    if(args[2].equalsIgnoreCase("off")) {
                        spigotControl.getControl(Files.MODULES).set(Extras.getWorldPath(Whitelist.STATUS,args[1]),false);
                        if(sender instanceof Player) {
                            spigotControl.getControl(Files.MODULES).set(Extras.getWorldPath(Whitelist.AUTHOR, args[1]), sender.getName());
                        } else {
                            spigotControl.getControl(Files.MODULES).set(Extras.getWorldPath(Whitelist.AUTHOR, args[1]), "Console");
                        }
                        spigotControl.save(SaveMode.MODULES);
                        spigotControl.reloadFile(SaveMode.MODULES);
                        spigotUtils.sendColored(sender,spigotControl.getControl(Files.EDITABLE).getString("messages.whitelist-disabled"));
                        return true;
                        //getServerPath
                    }
                    return true;
                }
                if (args[0].equalsIgnoreCase("blacklist")) {
                    if (args.length == 1) {
                        if (hasPermission(sender, "pixelmotd.command.blacklist.toggle")) {
                            sendMain(sender);
                            return true;
                        }
                        return true;
                    }
                    if (args[1].equalsIgnoreCase("global")) {
                        if (args.length == 2) {
                            boolean userMessage;
                            for(String lines : spigotControl.getControl(Files.COMMAND).getStringList("command.blacklist.list.top")) {
                                userMessage = false;
                                if(lines.contains("%cmd%")) lines = lines.replace("%cmd%", cmd);
                                if(lines.contains("%author%")) lines = lines.replace("%author%", "??");
                                if(lines.contains("%version%")) lines = lines.replace("%version%", spigotPixelMOTD.getInstance().getDescription().getVersion());
                                if(lines.contains("%blacklist%")) lines = lines.replace("%blacklist%", "Global");
                                if(lines.contains("%status%")) lines = lines.replace("%status%", getStatus("Global"));
                                if(lines.contains("<isUser>")) {
                                    lines = lines.replace("<isUser>","");
                                    userMessage = true;
                                }
                                if(lines.contains("%your_uuid%")) lines = lines.replace("%your_uuid%",getUniqueId(sender));
                                if(userMessage) {
                                    if(sender instanceof Player) {
                                        spigotUtils.sendColored(sender, lines);
                                    }
                                } else {
                                    spigotUtils.sendColored(sender, lines);
                                }
                            }
                            for(String players : spigotControl.getControl(Files.EDITABLE).getStringList("blacklist.players-name")) {
                                String line= spigotControl.getControl(Files.COMMAND).getString("command.blacklist.list.playersNameFormat");
                                if(line == null) line = "&e&l* &8[&7%online_status%&8] &7%player_name%";
                                if(line.contains("%online_status%")) line = line.replace("%online_status%",getOnline(players));
                                if(line.contains("%player_name%")) line = line.replace("%player_name%",players);
                                spigotUtils.sendColored(sender,line);
                            }
                            for(String lines : spigotControl.getControl(Files.COMMAND).getStringList("command.blacklist.list.mid")) {
                                userMessage = false;
                                if(lines.contains("%cmd%")) lines = lines.replace("%cmd%", cmd);
                                if(lines.contains("%author%")) lines = lines.replace("%author%", "??");
                                if(lines.contains("%version%")) lines = lines.replace("%version%", spigotPixelMOTD.getInstance().getDescription().getVersion());
                                if(lines.contains("%blacklist%")) lines = lines.replace("%blacklist%", "Global");
                                if(lines.contains("%status%")) lines = lines.replace("%status%", getStatus("Global"));
                                if(lines.contains("<isUser>")) {
                                    lines = lines.replace("<isUser>","");
                                    userMessage = true;
                                }
                                if(lines.contains("%your_uuid%")) lines = lines.replace("%your_uuid%",getUniqueId(sender));
                                if(userMessage) {
                                    if(sender instanceof Player) {
                                        spigotUtils.sendColored(sender, lines);
                                    }
                                } else {
                                    spigotUtils.sendColored(sender, lines);
                                }
                            }
                            for(String uuids : spigotControl.getControl(Files.EDITABLE).getStringList("blacklist.players-uuid")) {
                                String line= spigotControl.getControl(Files.COMMAND).getString("command.blacklist.list.playersUuidFormat");
                                if(line == null) line = "&e&l* &8[&7UUID&8] &7%player_uuid%";
                                if(line.contains("%player_uuid%")) line = line.replace("%online_status%","??");
                                if(line.contains("%player_name%")) line = line.replace("%player_name%","??");
                                if(line.contains("%player_uuid%")) line = line.replace("%player_uuid%",uuids);
                                spigotUtils.sendColored(sender,line);
                            }
                            for(String lines : spigotControl.getControl(Files.COMMAND).getStringList("command.blacklist.list.bot")) {
                                userMessage = false;
                                if(lines.contains("%cmd%")) lines = lines.replace("%cmd%", cmd);
                                if(lines.contains("%author%")) lines = lines.replace("%author%", "??");
                                if(lines.contains("%version%")) lines = lines.replace("%version%", spigotPixelMOTD.getInstance().getDescription().getVersion());
                                if(lines.contains("%blacklist%")) lines = lines.replace("%blacklist%", "Global");
                                if(lines.contains("%status%")) lines = lines.replace("%status%", getStatus("Global"));
                                if(lines.contains("<isUser>")) {
                                    lines = lines.replace("<isUser>","");
                                    userMessage = true;
                                }
                                if(lines.contains("%your_uuid%")) lines = lines.replace("%your_uuid%",getUniqueId(sender));
                                if(userMessage) {
                                    if(sender instanceof Player) {
                                        spigotUtils.sendColored(sender, lines);
                                    }
                                } else {
                                    spigotUtils.sendColored(sender, lines);
                                }
                            }
                            return true;
                        }
                        if(args[2].equalsIgnoreCase("on")) {
                            spigotControl.getControl(Files.EDITABLE).set("blacklist.toggle",true);
                            spigotControl.getControl(Files.EDITABLE).set("blacklist.author","Console");
                            spigotControl.save(SaveMode.EDITABLE);
                            spigotControl.reloadFile(SaveMode.EDITABLE);
                            spigotUtils.sendColored(sender,spigotControl.getControl(Files.EDITABLE).getString("messages.blacklist-enabled"));
                            return true;
                        }
                        if(args[2].equalsIgnoreCase("off")) {
                            spigotControl.getControl(Files.EDITABLE).set("blacklist.toggle",false);
                            spigotControl.getControl(Files.EDITABLE).set("blacklist.author","Console");
                            spigotControl.save(SaveMode.EDITABLE);
                            spigotControl.reloadFile(SaveMode.EDITABLE);
                            spigotUtils.sendColored(sender,spigotControl.getControl(Files.EDITABLE).getString("messages.blacklist-disabled"));
                            return true;
                        }
                    }
                    if (args.length == 2) {
                        boolean userMessage;
                        for(String lines : spigotControl.getControl(Files.COMMAND).getStringList("command.blacklist.list.top")) {
                            userMessage=false;
                            if(lines.contains("%cmd%")) lines = lines.replace("%cmd%", cmd);
                            if(lines.contains("%author%")) lines = lines.replace("%author%", "??");
                            if(lines.contains("%version%")) lines = lines.replace("%version%", spigotPixelMOTD.getInstance().getDescription().getVersion());
                            if(lines.contains("%blacklist%")) lines = lines.replace("%blacklist%", args[1]);
                            if(lines.contains("%status%")) lines = lines.replace("%status%", getStatus(args[1]));
                            if(lines.contains("<isUser>")) {
                                lines = lines.replace("<isUser>","");
                                userMessage = true;
                            }
                            if(lines.contains("%your_uuid%")) lines = lines.replace("%your_uuid%",getUniqueId(sender));
                            if(userMessage) {
                                if(sender instanceof Player) {
                                    spigotUtils.sendColored(sender, lines);
                                }
                            } else {
                                spigotUtils.sendColored(sender, lines);
                            }
                        }
                        for(String players : spigotUtils.getPlayers(BlacklistMembers.NAMEs,args[1])) {
                            String line= spigotControl.getControl(Files.COMMAND).getString("command.blacklist.list.playersNameFormat");
                            if(line == null) line = "&e&l* &8[&7%online_status%&8] &7%player_name%";
                            if(line.contains("%online_status%")) line = line.replace("%online_status%",getOnline(players));
                            if(line.contains("%player_name%")) line = line.replace("%player_name%",players);
                            spigotUtils.sendColored(sender,line);
                        }
                        for(String lines : spigotControl.getControl(Files.COMMAND).getStringList("command.blacklist.list.mid")) {
                            userMessage=false;
                            if(lines.contains("%cmd%")) lines = lines.replace("%cmd%", cmd);
                            if(lines.contains("%author%")) lines = lines.replace("%author%", "??");
                            if(lines.contains("%version%")) lines = lines.replace("%version%", spigotPixelMOTD.getInstance().getDescription().getVersion());
                            if(lines.contains("%blacklist%")) lines = lines.replace("%blacklist%", args[1]);
                            if(lines.contains("%status%")) lines = lines.replace("%status%", getStatus(args[1]));
                            if(lines.contains("<isUser>")) {
                                lines = lines.replace("<isUser>","");
                                userMessage=true;
                            }
                            if(lines.contains("%your_uuid%")) lines = lines.replace("%your_uuid%",getUniqueId(sender));
                            if(userMessage) {
                                if(sender instanceof Player) {
                                    spigotUtils.sendColored(sender, lines);
                                }
                            } else {
                                spigotUtils.sendColored(sender, lines);
                            }
                        }
                        for(String uuids : spigotUtils.getPlayers(BlacklistMembers.UUIDs,args[1])) {
                            String line= spigotControl.getControl(Files.COMMAND).getString("command.blacklist.list.playersUuidFormat");
                            if(line == null) line = "&e&l* &8[&7UUID&8] &7%player_uuid%";
                            if(line.contains("%player_uuid%")) line = line.replace("%online_status%","??");
                            if(line.contains("%player_name%")) line = line.replace("%player_name%","??");
                            if(line.contains("%player_uuid%")) line = line.replace("%player_uuid%",uuids);
                            spigotUtils.sendColored(sender,line);
                        }
                        for(String lines : spigotControl.getControl(Files.COMMAND).getStringList("command.blacklist.list.bot")) {
                            userMessage = false;
                            if(lines.contains("%cmd%")) lines = lines.replace("%cmd%", cmd);
                            if(lines.contains("%author%")) lines = lines.replace("%author%", "??");
                            if(lines.contains("%version%")) lines = lines.replace("%version%", spigotPixelMOTD.getInstance().getDescription().getVersion());
                            if(lines.contains("%blacklist%")) lines = lines.replace("%blacklist%", args[1]);
                            if(lines.contains("%status%")) lines = lines.replace("%status%", getStatus(args[1]));
                            if(lines.contains("<isUser>")) {
                                lines = lines.replace("<isUser>","");
                                userMessage = true;
                            }
                            if(lines.contains("%your_uuid%")) lines = lines.replace("%your_uuid%",getUniqueId(sender));
                            if(userMessage) {
                                if(sender instanceof Player) {
                                    spigotUtils.sendColored(sender, lines);
                                }
                            } else {
                                spigotUtils.sendColored(sender, lines);
                            }
                        }
                        return true;
                    }
                    if(args.length == 3) {
                        if(args[2].equalsIgnoreCase("on")) {
                            spigotControl.getControl(Files.MODULES).set(Extras.getWorldPath(Blacklist.STATUS,args[1]),true);
                            spigotControl.save(SaveMode.MODULES);
                            spigotControl.reloadFile(SaveMode.MODULES);
                            spigotUtils.sendColored(sender,spigotControl.getControl(Files.EDITABLE).getString("messages.status-enabled").replace("%type%","world").replace("%value%",args[1]));
                            return true;
                        }
                        if(args[2].equalsIgnoreCase("off")) {
                            spigotControl.getControl(Files.MODULES).set(Extras.getWorldPath(Blacklist.STATUS,args[1]),false);
                            spigotControl.save(SaveMode.MODULES);
                            spigotControl.reloadFile(SaveMode.MODULES);
                            spigotUtils.sendColored(sender,spigotControl.getControl(Files.EDITABLE).getString("messages.status-disabled").replace("%type%","world").replace("%value%",args[1]));
                            return true;
                        }
                    }
                }
                if (args[0].equalsIgnoreCase("add")) {
                    if (hasPermission(sender, "pixelmotd.command.whitelist.add")) {
                        if (args.length == 1) {
                            sendMain(sender);
                            return true;
                        }
                        if (args[1].equalsIgnoreCase("whitelist")) {
                            if (args.length == 2) {
                                sendMain(sender);
                                return true;
                            }
                            if (args.length == 3) {
                                sendMain(sender);
                                return true;
                            }
                            if(args[2].equalsIgnoreCase("Global")) {
                                if (args[3].contains("-")) {
                                    if (spigotControl.getControl(Files.EDITABLE).get("whitelist.players-uuid") != null) {
                                        spigotUtils.sendColored(sender, getMsg(Objects.requireNonNull(spigotControl.getControl(Files.EDITABLE).getString("messages.whitelist-player-add")).replace("%type%", "UUID").replace("%player%", args[3])));
                                        List<String> list = spigotControl.getControl(Files.EDITABLE).getStringList("whitelist.players-uuid");
                                        list.add(args[3]);
                                        spigotControl.getControl(Files.EDITABLE).set("whitelist.players-uuid", list);
                                        spigotControl.save(SaveMode.EDITABLE);
                                        spigotControl.reloadFile(SaveMode.EDITABLE);
                                        return true;
                                    }
                                    spigotUtils.sendColored(sender, getMsg(Objects.requireNonNull(spigotControl.getControl(Files.EDITABLE).getString("messages.whitelist-player-add")).replace("%type%", "UUID").replace("%player%", args[3])));
                                    List<String> list = new ArrayList<>();
                                    list.add(args[3]);
                                    spigotControl.getControl(Files.EDITABLE).set("whitelist.players-uuid", list);
                                    spigotControl.save(SaveMode.EDITABLE);
                                    spigotControl.reloadFile(SaveMode.EDITABLE);
                                    return true;
                                }
                                if (spigotControl.getControl(Files.EDITABLE).get("whitelist.players-name") != null) {
                                    if (!spigotControl.getControl(Files.EDITABLE).getStringList("whitelist.players-name").contains(args[3])) {
                                        spigotUtils.sendColored(sender, getMsg(Objects.requireNonNull(spigotControl.getControl(Files.EDITABLE).getString("messages.whitelist-player-add")).replace("%type%", "Player").replace("%player%", args[3])));
                                        List<String> list = spigotControl.getControl(Files.EDITABLE).getStringList("whitelist.players-name");
                                        list.add(args[3]);
                                        spigotControl.getControl(Files.EDITABLE).set("whitelist.players-name", list);
                                        spigotControl.save(SaveMode.EDITABLE);
                                        spigotControl.reloadFile(SaveMode.EDITABLE);
                                        return true;
                                    }
                                    spigotUtils.sendColored(sender, getMsg(Objects.requireNonNull(spigotControl.getControl(Files.EDITABLE).getString("messages.already-whitelisted")).replace("%type%", "Player").replace("%player%", args[3])));
                                    return true;
                                }
                                spigotUtils.sendColored(sender, getMsg(Objects.requireNonNull(spigotControl.getControl(Files.EDITABLE).getString("messages.whitelist-player-add")).replace("%type%", "Player").replace("%player%", args[3])));
                                List<String> list = new ArrayList<>();
                                list.add(args[2]);
                                spigotControl.getControl(Files.EDITABLE).set("whitelist.players-name", list);
                                spigotControl.save(SaveMode.EDITABLE);
                                spigotControl.reloadFile(SaveMode.EDITABLE);
                                return true;
                            }
                            if (args[3].contains("-")) {
                                if (!spigotUtils.getPlayers(WhitelistMembers.UUIDs,args[2]).contains(args[3])) {
                                    spigotUtils.sendColored(sender, getMsg(Objects.requireNonNull(spigotControl.getControl(Files.EDITABLE).getString("messages.whitelist-player-add")).replace("%type%", "UUID").replace("%player%", args[3])));
                                    List<String> list = spigotUtils.getPlayers(WhitelistMembers.UUIDs, args[2]);
                                    list.add(args[3]);
                                    spigotControl.getControl(Files.EDITABLE).set(Extras.getWorldPath(Whitelist.PLAYERS_UUID, args[2]), list);
                                    spigotControl.save(SaveMode.EDITABLE);
                                    spigotControl.reloadFile(SaveMode.EDITABLE);
                                    return true;
                                }
                            }
                            if (!spigotUtils.getPlayers(WhitelistMembers.NAMEs,args[2]).contains(args[3])) {
                                spigotUtils.sendColored(sender, getMsg(Objects.requireNonNull(spigotControl.getControl(Files.EDITABLE).getString("messages.whitelist-player-add")).replace("%type%", "Player").replace("%player%", args[3])));
                                List<String> list = spigotUtils.getPlayers(WhitelistMembers.NAMEs,args[2]);
                                list.add(args[3]);
                                spigotControl.getControl(Files.EDITABLE).set(Extras.getWorldPath(Whitelist.PLAYERS_NAME,args[2]), list);
                                spigotControl.save(SaveMode.EDITABLE);
                                spigotControl.reloadFile(SaveMode.EDITABLE);
                                return true;
                            }
                            spigotUtils.sendColored(sender, getMsg(Objects.requireNonNull(spigotControl.getControl(Files.EDITABLE).getString("messages.already-whitelisted")).replace("%type%", "Player").replace("%player%", args[3])));
                            return true;
                        }
                        if (args[1].equalsIgnoreCase("blacklist")) {
                            if (args.length == 2) {
                                sendMain(sender);
                                return true;
                            }
                            if (args.length == 3) {
                                sendMain(sender);
                                return true;
                            }
                            if(args[2].equalsIgnoreCase("Global")) {
                                if (args[3].contains("-")) {
                                    if (spigotControl.getControl(Files.EDITABLE).get("blacklist.players-uuid") != null) {
                                        List<String> list = spigotControl.getControl(Files.EDITABLE).getStringList("blacklist.players-uuid");
                                        list.add(args[3]);
                                        spigotControl.getControl(Files.EDITABLE).set("blacklist.players-uuid", list);
                                        spigotUtils.sendColored(sender, Objects.requireNonNull(spigotControl.getControl(Files.EDITABLE).getString("messages.blacklist-player-add")).replace("%type%", "UUID").replace("%player%", args[3]));
                                        spigotControl.save(SaveMode.EDITABLE);
                                        spigotControl.reloadFile(SaveMode.EDITABLE);
                                        return true;
                                    }
                                    spigotUtils.sendColored(sender, Objects.requireNonNull(spigotControl.getControl(Files.EDITABLE).getString("messages.blacklist-player-add")).replace("%type%", "UUID").replace("%player%", args[3]));
                                    List<String> list = new ArrayList<>();
                                    list.add(args[3]);
                                    spigotControl.getControl(Files.EDITABLE).set("blacklist.players-uuid", list);
                                    spigotControl.save(SaveMode.EDITABLE);
                                    spigotControl.reloadFile(SaveMode.EDITABLE);
                                    return true;
                                }
                                if (spigotControl.getControl(Files.EDITABLE).get("blacklist.players-name") != null) {
                                    List<String> list = spigotControl.getControl(Files.EDITABLE).getStringList("blacklist.players-name");
                                    list.add(args[3]);
                                    spigotUtils.sendColored(sender, Objects.requireNonNull(spigotControl.getControl(Files.EDITABLE).getString("messages.blacklist-player-add")).replace("%type%", "Player").replace("%player%", args[3]));
                                    spigotControl.getControl(Files.EDITABLE).set("blacklist.players-name", list);
                                    spigotControl.save(SaveMode.EDITABLE);
                                    spigotControl.reloadFile(SaveMode.EDITABLE);
                                    return true;
                                }
                                List<String> list = new ArrayList<>();
                                list.add(args[3]);
                                spigotUtils.sendColored(sender, Objects.requireNonNull(spigotControl.getControl(Files.EDITABLE).getString("messages.blacklist-player-add")).replace("%type%", "Player").replace("%player%", args[3]));
                                spigotControl.getControl(Files.EDITABLE).set("blacklist.players-name", list);
                                spigotControl.save(SaveMode.EDITABLE);
                                spigotControl.reloadFile(SaveMode.EDITABLE);
                                return true;
                            }
                            if (args[3].contains("-")) {
                                if (!spigotUtils.getPlayers(BlacklistMembers.UUIDs,args[2]).contains(args[3])) {
                                    List<String> list = spigotUtils.getPlayers(BlacklistMembers.UUIDs, args[2]);
                                    list.add(args[3]);
                                    spigotControl.getControl(Files.EDITABLE).set(Extras.getWorldPath(Blacklist.PLAYERS_UUID, args[2]), list);
                                    spigotUtils.sendColored(sender, Objects.requireNonNull(spigotControl.getControl(Files.EDITABLE).getString("messages.blacklist-player-add")).replace("%type%", "UUID").replace("%player%", args[3]));
                                    spigotControl.save(SaveMode.EDITABLE);
                                    spigotControl.reloadFile(SaveMode.EDITABLE);
                                    return true;
                                }
                                spigotUtils.sendColored(sender, getMsg(Objects.requireNonNull(spigotControl.getControl(Files.EDITABLE).getString("messages.already-blacklisted")).replace("%type%", "UUID").replace("%player%", args[3])));
                                return true;
                            }
                            if (!spigotUtils.getPlayers(BlacklistMembers.NAMEs,args[2]).contains(args[3])) {
                                List<String> list = spigotUtils.getPlayers(BlacklistMembers.NAMEs,args[2]);
                                list.add(args[3]);
                                spigotUtils.sendColored(sender, Objects.requireNonNull(spigotControl.getControl(Files.EDITABLE).getString("messages.blacklist-player-add")).replace("%type%", "Player").replace("%player%", args[3]));
                                spigotControl.getControl(Files.EDITABLE).set(Extras.getWorldPath(Blacklist.PLAYERS_NAME,args[2]), list);
                                spigotControl.save(SaveMode.EDITABLE);
                                spigotControl.reloadFile(SaveMode.EDITABLE);
                                return true;
                            }
                            spigotUtils.sendColored(sender, getMsg(Objects.requireNonNull(spigotControl.getControl(Files.EDITABLE).getString("messages.already-blacklisted")).replace("%type%", "Player").replace("%player%", args[3])));
                            return true;
                        }
                    }
                    return true;
                }
                if (args[0].equalsIgnoreCase("remove")) {
                    if (hasPermission(sender, "pixelmotd.command.whitelist.remove")) {
                        if (args.length == 1) {
                            sendMain(sender);
                            return true;
                        }
                        if (args[1].equalsIgnoreCase("whitelist")) {
                            if (args.length == 2) {
                                sendMain(sender);
                                return true;
                            }
                            if (args.length == 3) {
                                sendMain(sender);
                                return true;
                            }
                            if(args[2].equalsIgnoreCase("Global")) {
                                if (args[3].contains("-")) {
                                    if (spigotControl.getControl(Files.EDITABLE).get("whitelist.players-uuid") != null) {
                                        spigotUtils.sendColored(sender, getMsg(Objects.requireNonNull(spigotControl.getControl(Files.EDITABLE).getString("messages.whitelist-player-remove")).replace("%type%", "UUID").replace("%player%", args[3])));
                                        List<String> list = spigotControl.getControl(Files.EDITABLE).getStringList("whitelist.players-uuid");
                                        list.remove(args[3]);
                                        spigotControl.getControl(Files.EDITABLE).set("whitelist.players-uuid", list);
                                        spigotControl.save(SaveMode.EDITABLE);
                                        spigotControl.reloadFile(SaveMode.EDITABLE);
                                        return true;
                                    }
                                    spigotUtils.sendColored(sender, getMsg(Objects.requireNonNull(spigotControl.getControl(Files.EDITABLE).getString("messages.not-whitelisted")).replace("%type%", "UUID").replace("%player%", args[3])));
                                }
                                if (spigotControl.getControl(Files.EDITABLE).get("whitelist.players-name") != null) {
                                    if (spigotControl.getControl(Files.EDITABLE).getStringList("whitelist.players-name").contains(args[3])) {
                                        spigotUtils.sendColored(sender, getMsg(Objects.requireNonNull(spigotControl.getControl(Files.EDITABLE).getString("messages.whitelist-player-remove")).replace("%type%", "Player").replace("%player%", args[3])));
                                        List<String> list = spigotControl.getControl(Files.EDITABLE).getStringList("whitelist.players-name");
                                        list.remove(args[3]);
                                        spigotControl.getControl(Files.EDITABLE).set("whitelist.players-name", list);
                                        spigotControl.save(SaveMode.EDITABLE);
                                        spigotControl.reloadFile(SaveMode.EDITABLE);
                                        return true;
                                    }
                                    spigotUtils.sendColored(sender, getMsg(Objects.requireNonNull(spigotControl.getControl(Files.EDITABLE).getString("messages.not-whitelisted")).replace("%type%", "Player").replace("%player%", args[3])));
                                    return true;
                                }
                                spigotUtils.sendColored(sender, getMsg(Objects.requireNonNull(spigotControl.getControl(Files.EDITABLE).getString("messages.not-whitelisted")).replace("%type%", "Player").replace("%player%", args[3])));
                                return true;
                            }
                            if (args[3].contains("-")) {
                                if (spigotUtils.getPlayers(WhitelistMembers.UUIDs,args[2]).contains(args[3])) {
                                    spigotUtils.sendColored(sender, getMsg(Objects.requireNonNull(spigotControl.getControl(Files.EDITABLE).getString("messages.whitelist-player-remove")).replace("%type%", "UUID").replace("%player%", args[3])));
                                    List<String> list = spigotUtils.getPlayers(WhitelistMembers.UUIDs, args[2]);
                                    list.remove(args[3]);
                                    spigotControl.getControl(Files.EDITABLE).set(Extras.getWorldPath(Whitelist.PLAYERS_UUID, args[2]), list);
                                    spigotControl.save(SaveMode.EDITABLE);
                                    spigotControl.reloadFile(SaveMode.EDITABLE);
                                    return true;
                                }
                            }
                            if (spigotUtils.getPlayers(WhitelistMembers.NAMEs,args[2]).contains(args[3])) {
                                spigotUtils.sendColored(sender, getMsg(Objects.requireNonNull(spigotControl.getControl(Files.EDITABLE).getString("messages.whitelist-player-remove")).replace("%type%", "Player").replace("%player%", args[3])));
                                List<String> list = spigotUtils.getPlayers(WhitelistMembers.NAMEs,args[2]);
                                list.remove(args[3]);
                                spigotControl.getControl(Files.EDITABLE).set(Extras.getWorldPath(Whitelist.PLAYERS_NAME,args[2]), list);
                                spigotControl.save(SaveMode.EDITABLE);
                                spigotControl.reloadFile(SaveMode.EDITABLE);
                                return true;
                            }
                            spigotUtils.sendColored(sender, getMsg(Objects.requireNonNull(spigotControl.getControl(Files.EDITABLE).getString("messages.not-whitelisted")).replace("%type%", "Player").replace("%player%", args[3])));
                            return true;
                        }
                        if (args[1].equalsIgnoreCase("blacklist")) {
                            if (args.length == 2) {
                                sendMain(sender);
                                return true;
                            }
                            if (args.length == 3) {
                                sendMain(sender);
                                return true;
                            }
                            if(args[2].equalsIgnoreCase("Global")) {
                                if (args[3].contains("-")) {
                                    if (spigotControl.getControl(Files.EDITABLE).get("blacklist.players-uuid") != null) {
                                        List<String> list = spigotControl.getControl(Files.EDITABLE).getStringList("blacklist.players-uuid");
                                        list.add(args[3]);
                                        spigotControl.getControl(Files.EDITABLE).set("blacklist.players-uuid", list);
                                        spigotUtils.sendColored(sender, Objects.requireNonNull(spigotControl.getControl(Files.EDITABLE).getString("messages.blacklist-player-remove")).replace("%type%", "UUID").replace("%player%", args[3]));
                                        spigotControl.save(SaveMode.EDITABLE);
                                        spigotControl.reloadFile(SaveMode.EDITABLE);
                                        return true;
                                    }
                                    spigotUtils.sendColored(sender, Objects.requireNonNull(spigotControl.getControl(Files.EDITABLE).getString("messages.blacklist-player-remove")).replace("%type%", "UUID").replace("%player%", args[3]));
                                    List<String> list = new ArrayList<>();
                                    list.add(args[3]);
                                    spigotControl.getControl(Files.EDITABLE).set("blacklist.players-uuid", list);
                                    spigotControl.save(SaveMode.EDITABLE);
                                    spigotControl.reloadFile(SaveMode.EDITABLE);
                                    return true;
                                }
                                if (spigotControl.getControl(Files.EDITABLE).get("blacklist.players-name") != null) {
                                    List<String> list = spigotControl.getControl(Files.EDITABLE).getStringList("blacklist.players-name");
                                    list.add(args[3]);
                                    spigotUtils.sendColored(sender, Objects.requireNonNull(spigotControl.getControl(Files.EDITABLE).getString("messages.blacklist-player-remove")).replace("%type%", "Player").replace("%player%", args[3]));
                                    spigotControl.getControl(Files.EDITABLE).set("blacklist.players-name", list);
                                    spigotControl.save(SaveMode.EDITABLE);
                                    spigotControl.reloadFile(SaveMode.EDITABLE);
                                    return true;
                                }
                                List<String> list = new ArrayList<>();
                                list.add(args[3]);
                                spigotUtils.sendColored(sender, Objects.requireNonNull(spigotControl.getControl(Files.EDITABLE).getString("messages.blacklist-player-remove")).replace("%type%", "Player").replace("%player%", args[3]));
                                spigotControl.getControl(Files.EDITABLE).set("blacklist.players-name", list);
                                spigotControl.save(SaveMode.EDITABLE);
                                spigotControl.reloadFile(SaveMode.EDITABLE);
                                return true;
                            }
                            if (args[3].contains("-")) {
                                if (spigotUtils.getPlayers(BlacklistMembers.UUIDs,args[2]).contains(args[3])) {
                                    List<String> list = spigotUtils.getPlayers(BlacklistMembers.UUIDs, args[2]);
                                    list.remove(args[3]);
                                    spigotControl.getControl(Files.EDITABLE).set(Extras.getWorldPath(Blacklist.PLAYERS_UUID, args[2]), list);
                                    spigotUtils.sendColored(sender, Objects.requireNonNull(spigotControl.getControl(Files.EDITABLE).getString("messages.blacklist-player-remove")).replace("%type%", "UUID").replace("%player%", args[3]));
                                    spigotControl.save(SaveMode.EDITABLE);
                                    spigotControl.reloadFile(SaveMode.EDITABLE);
                                    return true;
                                }
                                spigotUtils.sendColored(sender, getMsg(Objects.requireNonNull(spigotControl.getControl(Files.EDITABLE).getString("messages.not-blacklisted")).replace("%type%", "UUID").replace("%player%", args[3])));
                                return true;
                            }
                            if (spigotUtils.getPlayers(BlacklistMembers.NAMEs,args[2]).contains(args[3])) {
                                List<String> list = spigotUtils.getPlayers(BlacklistMembers.NAMEs,args[2]);
                                list.remove(args[3]);
                                spigotUtils.sendColored(sender, Objects.requireNonNull(spigotControl.getControl(Files.EDITABLE).getString("messages.blacklist-player-remove")).replace("%type%", "Player").replace("%player%", args[3]));
                                spigotControl.getControl(Files.EDITABLE).set(Extras.getWorldPath(Blacklist.PLAYERS_NAME,args[2]), list);
                                spigotControl.save(SaveMode.EDITABLE);
                                spigotControl.reloadFile(SaveMode.EDITABLE);
                                return true;
                            }
                            spigotUtils.sendColored(sender, getMsg(Objects.requireNonNull(spigotControl.getControl(Files.EDITABLE).getString("messages.not-blacklisted")).replace("%type%", "Player").replace("%player%", args[3])));
                            return true;
                        }
                    }
                }
                if(args[0].equalsIgnoreCase("modules")) {
                    spigotUtils.sendColored(sender,"&cCurrently working");
                    //modules setup
                }
                if(args[0].equalsIgnoreCase("externalModules")) {
                    spigotUtils.sendColored(sender,"&cCurrently working");
                    //modules setup
                }
                //(all-settings-edit-modules-cmd-motds)
                if (args[0].equalsIgnoreCase("reload")) {
                    if (hasPermission(sender, "pixelmotd.command.reload")) {
                        if (args.length == 1) {
                            sendMain(sender);
                            return true;
                        }
                        if (args[1].equalsIgnoreCase("all")) {
                            long timeMS = System.currentTimeMillis();
                            spigotControl.reloadFile(SaveMode.ALL);
                            String reload = spigotControl.getControl(Files.EDITABLE).getString("messages.reload");
                            if (reload == null) reload = "";
                            if (reload.contains("<ms>"))
                                reload = reload.replace("<ms>", (System.currentTimeMillis() - timeMS) + "");
                            if (reload.contains("<saveMode>")) reload = reload.replace("<saveMode>", "ALL");
                            spigotUtils.sendColored(sender, reload);
                            return true;
                        }
                        if (args[1].equalsIgnoreCase("cmd")) {
                            long timeMS = System.currentTimeMillis();
                            spigotControl.reloadFile(SaveMode.COMMAND);
                            String reload = spigotControl.getControl(Files.EDITABLE).getString("messages.reload");
                            if (reload == null) reload = "";
                            if (reload.contains("<ms>"))
                                reload = reload.replace("<ms>", (System.currentTimeMillis() - timeMS) + "");
                            if (reload.contains("<saveMode>")) reload = reload.replace("<saveMode>", "COMMANDS");
                            spigotUtils.sendColored(sender, reload);
                            return true;
                        }
                        if (args[1].equalsIgnoreCase("edit")) {
                            long timeMS = System.currentTimeMillis();
                            spigotControl.reloadFile(SaveMode.EDITABLE);
                            String reload = spigotControl.getControl(Files.EDITABLE).getString("messages.reload");
                            if (reload == null) reload = "";
                            if (reload.contains("<ms>"))
                                reload = reload.replace("<ms>", (System.currentTimeMillis() - timeMS) + "");
                            if (reload.contains("<saveMode>")) reload = reload.replace("<saveMode>", "EDIT");
                            spigotUtils.sendColored(sender, reload);
                            return true;
                        }
                        if (args[1].equalsIgnoreCase("modules")) {
                            long timeMS = System.currentTimeMillis();
                            spigotControl.reloadFile(SaveMode.MODULES);
                            String reload = spigotControl.getControl(Files.EDITABLE).getString("messages.reload");
                            if (reload == null) reload = "";
                            if (reload.contains("<ms>"))
                                reload = reload.replace("<ms>", (System.currentTimeMillis() - timeMS) + "");
                            if (reload.contains("<saveMode>")) reload = reload.replace("<saveMode>", "MODULES");
                            spigotUtils.sendColored(sender, reload);
                            return true;
                        }
                        if (args[1].equalsIgnoreCase("motds")) {
                            long timeMS = System.currentTimeMillis();
                            spigotControl.reloadFile(SaveMode.MOTDS);
                            String reload = spigotControl.getControl(Files.EDITABLE).getString("messages.reload");
                            if (reload == null) reload = "";
                            if (reload.contains("<ms>"))
                                reload = reload.replace("<ms>", (System.currentTimeMillis() - timeMS) + "");
                            if (reload.contains("<saveMode>")) reload = reload.replace("<saveMode>", "MOTDS");
                            spigotUtils.sendColored(sender, reload);
                            return true;
                        }
                    }
                }
                return true;
            }
            return true;
        } catch(Throwable throwable) {
            spigotUtils.sendColored(sender,"&cPixelMOTD found a error using this command. Check console and report this error to the developer, please enable option &lshow-detailed-errors &cfor more info for the developer.");
            if(spigotControl.isDetailed()) {
                spigotPixelMOTD.sendConsole("&a[Pixel MOTD] [Detailed Error] Information:");
                if(throwable.getMessage() != null) {
                    spigotPixelMOTD.sendConsole("&a[Pixel MOTD] Message: " + throwable.getMessage());
                }
                if(throwable.getLocalizedMessage() != null) {
                    spigotPixelMOTD.sendConsole("&a[Pixel MOTD] LocalizedMessage: " + throwable.getLocalizedMessage());
                }
                if(throwable.getStackTrace() != null) {
                    spigotPixelMOTD.sendConsole("&a[Pixel MOTD] StackTrace: ");
                    for(StackTraceElement line : throwable.getStackTrace()) {
                        spigotPixelMOTD.sendConsole("&a[Pixel MOTD] (" + line.getLineNumber() + ") " + line.toString());
                    }
                }
                if(throwable.getSuppressed() != null) {
                    spigotPixelMOTD.sendConsole("&a[Pixel MOTD] Suppressed: " + Arrays.toString(throwable.getSuppressed()));
                }
                if(throwable.getClass().getName() != null) {
                    spigotPixelMOTD.sendConsole("&a[Pixel MOTD] Class: " + throwable.getClass().getName() + ".class");
                }
                spigotPixelMOTD.sendConsole("&a[Pixel MOTD] Plugin version:" + spigotPixelMOTD.getInstance().getDescription().getVersion());
                spigotPixelMOTD.sendConsole("&a[Pixel MOTD] --------------- [Detailed Error]");
            }
        }
        return true;
    }
    private String getMsg(String message) {
        if(message == null) return "";
        return message;
    }
}
