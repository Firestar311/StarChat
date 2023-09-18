package me.firestar311.starchat.chat;

import me.firestar311.starchat.StarChat;
import me.firestar311.starchat.objects.AuditEntry;
import me.firestar311.starchat.objects.ChatEntry;
import me.firestar311.starlib.spigot.utils.color.ColorUtils;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.function.Function;

public abstract class ChatSpace {
    
    protected static final StarChat plugin = StarChat.getPlugin(StarChat.class);
    
    protected int id;
    protected String displayName, senderFormat, systemFormat = "{message}";
    protected SortedSet<ChatEntry> chatLog = new TreeSet<>();
    protected SortedSet<AuditEntry> auditLog = new TreeSet<>();
    protected Map<String, Function<ChatContext, String>> variableConsumers = new HashMap<>();

    public ChatSpace(int id, String displayName, String senderFormat) {
        this.id = id;
        this.displayName = displayName;
        this.senderFormat = senderFormat;
        variableConsumers.put("{name}", context -> {
            if (context.sender() instanceof ConsoleCommandSender) {
                return plugin.getConfig().getString("console.name");
            } else if (context.sender() instanceof Player player) {
                return player.getName();
            }
            return "";
        });
        variableConsumers.put("{displayname}", context -> {
            if (context.sender() instanceof ConsoleCommandSender) {
                return plugin.getConfig().getString("console.displayname");
            } else if (context.sender() instanceof Player player) {
                return player.getDisplayName();
            }
            return "";
        });
        variableConsumers.put("{message}", context -> {
            if (plugin.getConfig().getBoolean("use-color-permissions")) {
                return ColorUtils.color(context.sender(), context.message());
            }
            return ColorUtils.color(context.message());
        });
    }

    /**
     * This will send a message based on a command sender and text. <br>
     * This should handle Player and ConsoleCommandSender
     * @param sender The sender of the message - Can be null
     * @param message The message to send
     */
    public abstract void sendMessage(CommandSender sender, String message);

    /**
     * Sends a system like message. This is for without a sender.
     * @param message The message to send
     */
    public void sendMessage(String message) {
        sendMessage(null, message);
    }
    
    protected String applyFormat(CommandSender sender, String message) {
        String format = sender != null ? senderFormat : systemFormat;
        if (plugin.getPapiHook() != null && sender instanceof Player player) {
            plugin.getPapiHook().setPlaceholders(player, format);
        }

        if (plugin.getVaultHook() != null && sender instanceof Player player) {
            plugin.getVaultHook().replaceVariables(player, format);
        }

        ChatContext context = new ChatContext(this, sender, null, message);
        for (Map.Entry<String, Function<ChatContext, String>> entry : this.variableConsumers.entrySet()) {
            if (format.contains(entry.getKey())) {
                format = format.replace(entry.getKey(), entry.getValue().apply(context));
            }
        }
        return format;
    }
    
    public int getId() {
        return id;
    }

    public String getDisplayName() {
        return displayName;
    }

    public String getSenderFormat() {
        return senderFormat;
    }

    public SortedSet<ChatEntry> getChatLog() {
        return new TreeSet<>(chatLog);
    }

    public SortedSet<AuditEntry> getAuditLog() {
        return new TreeSet<>(auditLog);
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public void setSenderFormat(String senderFormat) {
        this.senderFormat = senderFormat;
    }

    public void setSystemFormat(String systemFormat) {
        this.systemFormat = systemFormat;
    }

    public String getSystemFormat() {
        return systemFormat;
    }
}