package dev.mruniverse.pixelmotd.listeners;

import dev.mruniverse.pixelmotd.enums.*;

import static dev.mruniverse.pixelmotd.files.bungeeControl.getControl;
import static dev.mruniverse.pixelmotd.files.bungeeControl.getWhitelistAuthor;

import dev.mruniverse.pixelmotd.files.bungeeControl;
import dev.mruniverse.pixelmotd.utils.Extras;
import dev.mruniverse.pixelmotd.utils.PixelConverter;
import dev.mruniverse.pixelmotd.utils.bungeeUtils;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.LoginEvent;
import net.md_5.bungee.api.event.PostLoginEvent;
import net.md_5.bungee.api.event.ServerConnectEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;
import java.util.ArrayList;
import java.util.List;

public class bungeeEvents implements Listener {
    @EventHandler
    public void onLoginEvent(LoginEvent event) {
        if(event.isCancelled()) return;
        //database - Setup
        //bungeePixelMOTD.getInstance().getDataManager().setAddress(event.getConnection().getVirtualHost().getAddress(), event.getConnection().getName());
        //whitelist - blacklist and modules - Setup
        if(getControl(Files.EDITABLE).getString("whitelist.check-mode").equalsIgnoreCase("LoginEvent")) {
            if (getControl(Files.EDITABLE).getBoolean("whitelist.toggle")) {
                if (!getControl(Files.EDITABLE).getStringList("whitelist.players-name").contains(event.getConnection().getName()) && !getControl(Files.EDITABLE).getStringList("whitelist.players-uuid").contains(event.getConnection().getUniqueId().toString())) {
                    String kickReason = PixelConverter.StringListToString(getControl(Files.EDITABLE).getStringList("whitelist.kick-message"));
                    event.setCancelReason(new TextComponent(ChatColor.translateAlternateColorCodes('&', kickReason.replace("%whitelist_author%", getWhitelistAuthor()).replace("%type%", "Server"))));
                }
                return;
            }
        }
        if(getControl(Files.EDITABLE).getBoolean("blacklist.toggle")) {
            if(getControl(Files.EDITABLE).getStringList("blacklist.players-name").contains(event.getConnection().getName()) || getControl(Files.EDITABLE).getStringList("blacklist.players-uuid").contains(event.getConnection().getUniqueId().toString())) {
                String kickReason = PixelConverter.StringListToString(getControl(Files.EDITABLE).getStringList("blacklist.kick-message"));
                event.setCancelReason(new TextComponent(ChatColor.translateAlternateColorCodes('&', kickReason.replace("%nick%", event.getConnection().getName()).replace("%type%","Server"))));
                return;
            }
        }
        if(getControl(Files.MODULES).getBoolean("modules.block-users.enabled")) {
            if(getControl(Files.MODULES).getBoolean("modules.block-users.ignoreCase")) {
                String name = event.getConnection().getName().toLowerCase();
                List<String> blackList = new ArrayList<>();
                for(String nameToLow : getControl(Files.MODULES).getStringList("modules.block-users.blockedUsers")) {
                    blackList.add(nameToLow.toLowerCase());
                }
                if(blackList.contains(name)) {
                    String kickMsg = PixelConverter.StringListToString(getControl(Files.MODULES).getStringList("modules.block-users.kickMessage"));
                    event.setCancelled(true);
                    event.setCancelReason(new TextComponent(ChatColor.translateAlternateColorCodes('&', kickMsg.replace("%blocked_name%",name))));
                    return;
                }
            } else {
                if(getControl(Files.MODULES).getStringList("modules.block-users.blockedUsers").contains(event.getConnection().getName())) {
                    String kickMsg = PixelConverter.StringListToString(getControl(Files.MODULES).getStringList("modules.block-users.kickMessage"));
                    event.setCancelled(true);
                    event.setCancelReason(new TextComponent(ChatColor.translateAlternateColorCodes('&', kickMsg.replace("%blocked_name%",event.getConnection().getName()))));
                    return;
                }
            }
        }
        if(getControl(Files.MODULES).getBoolean("modules.block-words-in-name.enabled")) {
            boolean magicalEdition = false;
            String blockedWord = "";
            if(getControl(Files.MODULES).getBoolean("modules.block-words-in-name.ignoreCase")) {
                String name = event.getConnection().getName().toLowerCase();
                for(String nameToLow : getControl(Files.MODULES).getStringList("modules.block-words-in-name.blockedWords")) {
                    if(name.contains(nameToLow.toLowerCase())) {
                        magicalEdition = true;
                        blockedWord = nameToLow.toLowerCase();
                    }
                }
                if(magicalEdition) {
                    String kickMsg = PixelConverter.StringListToString(getControl(Files.MODULES).getStringList("modules.block-words-in-name.kickMessage"));
                    event.setCancelled(true);
                    event.setCancelReason(new TextComponent(ChatColor.translateAlternateColorCodes('&', kickMsg.replace("%blocked_word%",blockedWord))));
                }

            } else {
                for(String name : getControl(Files.MODULES).getStringList("modules.block-words-in-name.blockedWords")) {
                    if(event.getConnection().getName().contains(name)) {
                        magicalEdition = true;
                    }
                }
                if(magicalEdition) {
                    String kickMsg = PixelConverter.StringListToString(getControl(Files.MODULES).getStringList("modules.block-words-in-name.kickMessage"));
                    event.setCancelled(true);
                    event.setCancelReason(new TextComponent(ChatColor.translateAlternateColorCodes('&', kickMsg.replace("%blocked_word%",blockedWord))));
                }
            }
        }
    }
    @EventHandler
    public void onPostLoginEvent(PostLoginEvent event) {
        if(getControl(Files.EDITABLE).getString("whitelist.check-mode").equalsIgnoreCase("LoginEvent")) {
            if(getControl(Files.EDITABLE).getBoolean("whitelist.toggle")) {
                if(!getControl(Files.EDITABLE).getStringList("whitelist.players-name").contains(event.getPlayer().getName()) && !getControl(Files.EDITABLE).getStringList("whitelist.players-uuid").contains(event.getPlayer().getUniqueId().toString())) {
                    String kickReason = PixelConverter.StringListToString(getControl(Files.EDITABLE).getStringList("whitelist.kick-message"));
                    event.getPlayer().disconnect(new TextComponent(ChatColor.translateAlternateColorCodes('&', kickReason.replace("%whitelist_author%", getWhitelistAuthor()).replace("%type%","Server"))));
                }
            }
        }
    }

    @EventHandler
    public void onServerSwitch(ServerConnectEvent event) {
        if(event.isCancelled()) return;
        String name = event.getTarget().getName();
        ProxiedPlayer player = event.getPlayer();
        if(getControl(Files.MODULES).getBoolean("modules.servers-whitelist.toggle")) {
            if(bungeeControl.getControl(Files.MODULES).contains(Extras.getServerPath(Whitelist.STATUS,name))) {
                if(!bungeeUtils.getPlayers(WhitelistMembers.NAMEs,name).contains(player.getName()) || !bungeeUtils.getPlayers(WhitelistMembers.UUIDs,name).contains(player.getUniqueId().toString())) {
                    for (String message : getControl(Files.MODULES).getStringList("modules.servers-whitelist.kickMessage")) {
                        message = message.replace("%whitelist_author%", getControl(Files.MODULES).getString(Extras.getServerPath(Whitelist.AUTHOR,name)))
                            .replace("%whitelist_reason%", getControl(Files.MODULES).getString(Extras.getServerPath(Whitelist.REASON,name))).replace("%type%","server").replace("%value%",name);
                        player.sendMessage(new TextComponent(ChatColor.translateAlternateColorCodes('&', message)));
                    }
                    event.setCancelled(true);
                }
            }
        }
        if(event.isCancelled()) return;
        if(getControl(Files.MODULES).getBoolean("modules.servers-blacklist.toggle")) {
            if(bungeeControl.getControl(Files.MODULES).contains(Extras.getServerPath(Blacklist.STATUS,name))) {
                if(!bungeeUtils.getPlayers(BlacklistMembers.NAMEs,name).contains(player.getName()) || !bungeeUtils.getPlayers(BlacklistMembers.UUIDs,name).contains(player.getUniqueId().toString())) {
                    for (String message : getControl(Files.MODULES).getStringList("modules.servers-blacklist.kickMessage")) {
                        message = message.replace("%blacklist_author%", "??")
                                .replace("%blacklist_reason%", getControl(Files.MODULES).getString(Extras.getServerPath(Blacklist.REASON,name))).replace("%type%","server").replace("%value%",name);
                        player.sendMessage(new TextComponent(ChatColor.translateAlternateColorCodes('&', message)));
                    }
                    event.setCancelled(true);
                }
            }
        }
    }
}
