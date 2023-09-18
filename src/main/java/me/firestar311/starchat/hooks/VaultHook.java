package me.firestar311.starchat.hooks;

import me.firestar311.starchat.StarChat;
import net.milkbowl.vault.chat.Chat;
import org.bukkit.entity.Player;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.ServicesManager;

public class VaultHook {
    private Chat chat;

    public VaultHook(StarChat plugin) {
        ServicesManager servicesManager = plugin.getServer().getServicesManager();
        RegisteredServiceProvider<Chat> csp = servicesManager.getRegistration(Chat.class);
        if (csp != null) {
            chat = csp.getProvider();
        }
    }

    public String replaceVariables(Player player, String text) {
        if (chat != null) {
            String prefix = "", suffix = "";
            String playerPrefix = chat.getPlayerPrefix(player);
            String playerSuffix = chat.getPlayerSuffix(player);
            String primaryGroup = chat.getPrimaryGroup(player);
            if (playerPrefix != null && !playerPrefix.isEmpty()) {
                prefix = playerPrefix;
            }

            if (playerSuffix != null && !playerSuffix.isEmpty()) {
                suffix = playerSuffix;
            }

            if (primaryGroup != null && !primaryGroup.isEmpty()) {
                String groupPrefix = chat.getGroupPrefix(player.getWorld(), primaryGroup);
                String groupSuffix = chat.getGroupSuffix(player.getWorld(), primaryGroup);
                if (groupPrefix != null && !groupPrefix.isEmpty() && prefix.isEmpty()) {
                    prefix = groupPrefix;
                }
                if (groupSuffix != null && !groupSuffix.isEmpty() && suffix.isEmpty()) {
                    suffix = groupSuffix;
                }
            }

            return text.replace("{prefix}", prefix).replace("{suffix}", suffix);
        }
        return text;
    }
}