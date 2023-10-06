package me.firestar311.starchat.manager;

import me.firestar311.starchat.StarChat;
import org.bukkit.command.CommandSender;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public interface ChatManager extends Listener {
    void onPlayerChat(AsyncPlayerChatEvent e);
    StarChat getPlugin();
    void handleGlobalChat(CommandSender sender, String message);
    void handleStaffChat(CommandSender sender, String message);
    void handlePrivateChat(CommandSender sender, String message);
}
