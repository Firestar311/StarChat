package me.firestar311.starchat.manager;

import me.firestar311.starchat.StarChat;
import org.bukkit.command.CommandSender;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class ComplexChatManager implements ChatManager {
    private StarChat plugin;
    
    public ComplexChatManager(StarChat plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerChat(AsyncPlayerChatEvent e) {
        
    }

    public StarChat getPlugin() {
        return this.plugin;
    }

    public void handleGlobalChat(CommandSender sender, String message) {
        
    }

    public void handleStaffChat(CommandSender sender, String message) {

    }

    public void handlePrivateChat(CommandSender sender, String message) {

    }
}
