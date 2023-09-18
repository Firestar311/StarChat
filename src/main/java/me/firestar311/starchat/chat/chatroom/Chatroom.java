package me.firestar311.starchat.chat.chatroom;

import me.firestar311.starchat.chat.ChatContext;
import me.firestar311.starchat.chat.ChatSpace;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

import java.util.*;
import java.util.function.Function;

// The idea behind a room is for temporary spaces to communicate, like private messaging or group messaging, or other plugins like Town/Factions
//  It is best to configure a format and other settings, but leave the actual creation outside of configs
public class Chatroom extends ChatSpace {
    protected Visibility visibility;
    protected Map<UUID, Participant> participants = new HashMap<>();
    protected Set<UUID> bannedPlayers = new HashSet<>(), mutedPlayers = new HashSet<>();
    
    //pm format: [{sender} -> {receiver}] {message}

    public Chatroom(int id, String displayName, String format, Visibility visibility) {
        super(id, displayName, format);
        this.visibility = visibility;
        
        this.variableConsumers.put("{sender}", context -> {
            if (context.sender() instanceof ConsoleCommandSender) {
                return plugin.getConfig().getString("console.name");
            } else if (context.sender() instanceof Player player) {
                return player.getName();
            }
            return "";
        });

        this.variableConsumers.put("{receiver}", context -> context.target().getName());
    }

    public Visibility getVisibility() {
        return visibility;
    }

    public void setVisibility(Visibility visibility) {
        this.visibility = visibility;
    }

    @Override
    public void sendMessage(CommandSender sender, String message) {
        String text = applyFormat(sender, message);

        for (Map.Entry<UUID, Participant> entry : new HashSet<>(participants.entrySet())) {
            Player player = Bukkit.getPlayer(entry.getKey());
            if (player != null) {
                String playerText = text;
                for (Map.Entry<String, Function<ChatContext, String>> e : this.variableConsumers.entrySet()) {
                    playerText = playerText.replace(e.getKey(), e.getValue().apply(new ChatContext(this, sender, player, message)));
                }
                player.sendMessage(playerText);
            }
        }
    }

    public Map<UUID, Participant> getParticipants() {
        return new HashMap<>(participants);
    }

    public Set<UUID> getBannedPlayers() {
        return new HashSet<>(bannedPlayers);
    }

    public Set<UUID> getMutedPlayers() {
        return new HashSet<>(mutedPlayers);
    }
}
