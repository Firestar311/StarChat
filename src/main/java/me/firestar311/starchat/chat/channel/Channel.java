package me.firestar311.starchat.chat.channel;

import me.firestar311.starchat.chat.ChatSpace;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

// The main idea behind a channel is for persistent spaces that are defined by the server
public class Channel extends ChatSpace {
    protected String permission;
    protected boolean autoJoin;

    public Channel(int id, String displayName, String format, String permission) {
        super(id, displayName, format);
        this.permission = permission;
    }
    
    public Channel(int id, String displayName, String format, String permission, boolean autoJoin) {
        this(id, displayName, format, permission);
        this.autoJoin = autoJoin;
    }

    public String getPermission() {
        return permission;
    }

    public boolean isAutoJoin() {
        return autoJoin;
    }

    @Override
    public void sendMessage(CommandSender sender, String message) {
        String text = applyFormat(sender, message);
        
        for (Player player : Bukkit.getOnlinePlayers()) {
            player.sendMessage(text);
        }
    }
}