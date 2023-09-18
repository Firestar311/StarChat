package me.firestar311.starchat.chat;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public record ChatContext(ChatSpace space, CommandSender sender, Player target, String message) {
}