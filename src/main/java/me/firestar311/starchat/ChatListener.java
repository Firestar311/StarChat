package me.firestar311.starchat;

import me.firestar311.starlib.spigot.utils.color.ColorUtils;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class ChatListener implements Listener {
    private StarChat plugin;

    public ChatListener(StarChat plugin) {
        this.plugin = plugin;
    }
    
    /*
        {channelname} - Channel Name - Handled by ChatListener
     */
    
    @EventHandler
    public void onChat(AsyncPlayerChatEvent e) {
        String format = plugin.getConfig().getString("global.format");
        if (plugin.getPapiHook() != null) {
            plugin.getPapiHook().setPlaceholders(e.getPlayer(), format);
        }
        
        if (plugin.getVaultHook() != null) {
            plugin.getVaultHook().replaceVariables(e.getPlayer(), format);
        }
        
        format = format.replace("{name}", e.getPlayer().getName());
        format = format.replace("{displayname}", e.getPlayer().getDisplayName());
        format = format.replace("{message}", e.getMessage());
        if (plugin.getConfig().getBoolean("use-color-permissions")) {
            format = ColorUtils.color(e.getPlayer(), format);
        } else {
            format = ColorUtils.color(format);
        }
        e.setFormat(format);
    }
}
