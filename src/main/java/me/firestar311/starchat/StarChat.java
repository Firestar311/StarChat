package me.firestar311.starchat;

import me.firestar311.starchat.hooks.PAPIHook;
import me.firestar311.starchat.hooks.VaultHook;
import me.firestar311.starchat.manager.ChatManager;
import me.firestar311.starchat.manager.ComplexChatManager;
import me.firestar311.starchat.manager.SimpleChatManager;
import me.firestar311.starlib.api.config.yaml.file.FileConfiguration;
import me.firestar311.starlib.api.config.yaml.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.List;

public class StarChat extends JavaPlugin {
    
    private PAPIHook papiHook;
    private VaultHook vaultHook;
    
    private ChatManager chatManager;
    
    private FileConfiguration config;
    
    public void onEnable() {
        saveResource("config.yml", false);
        File configFile = new File(getDataFolder(), "config.yml");
        this.config = YamlConfiguration.loadConfiguration(configFile);
        this.config.options().copyDefaults(true);
        this.config.options().parseComments();
        this.config.addDefault("mode", "simple");
        this.config.setComments("mode", List.of("There are two options for this setting, simple and complex", 
                "Simple (default) is just that, a simple chat system where you have a global format, a simple permission based staff chat, and a simple private message handling system.", 
                "Complex goes in a bit deeper and has configurable server based channels, and chatrooms that are controlled by other plugins, with some default chatroom types like private messaging."));
        this.config.addDefault("simple.global.displaynameformat", "{prefix}{name}{suffix}");
        this.config.addDefault("simple.global.format", "{name}: {message}");
        this.config.setComments("simple.global.format", List.of("This is the format used for messages sent in the global context.",
                "{name} (default) is used for just the player name.",
                "{displayname} uses the displaynameformat setting.",
                "{message} is the message sent by the player. See the message section for more options."));
        this.config.addDefault("simple.global.message.parsecolors", true);
        this.config.setComments("simple.global.message.parsecolors", List.of("This option is for controlling if colors are automatically parsed for the message.", 
                "True (default) means that colors will be parsed for every message.", 
                "False means that colors will not be parsed for every message."));
        this.config.addDefault("simple.global.message.permissioncolors", false);
        this.config.setComments("simple.global.message.permissioncolors", List.of("This option controls if permissions are used for each color. The parsecolors option must be true for this to apply.", 
                "False (default) means that all colors will be parsed regardless if the player has permission or not to use that color.", 
                "True means that colors are checked against a permission to see if they have permission to use that color.", 
                "For the permissions of colors, see the StarLib documentation."));
        this.config.addDefault("simple.staff.displaynameformat", "{prefix}{name}{suffix}");
        this.config.addDefault("simple.staff.format", "{name}: {message}");
        this.config.setComments("simple.staff.format", List.of("This is the format used for messages sent in the staff context.",
                "{name} (default) is used for just the player name.",
                "{displayname} uses the displaynameformat setting.",
                "{message} is the message sent by the player. See the message section for more options."));
        this.config.addDefault("simple.staff.message.parsecolors", true);
        this.config.setComments("simple.staff.message.parsecolors", List.of("This option is for controlling if colors are automatically parsed for the message.",
                "True (default) means that colors will be parsed for every message.",
                "False means that colors will not be parsed for every message."));
        this.config.addDefault("simple.staff.message.permissioncolors", false);
        this.config.setComments("simple.staff.message.permissioncolors", List.of("This option controls if permissions are used for each color. The parsecolors option must be true for this to apply.",
                "False (default) means that all colors will be parsed regardless if the player has permission or not to use that color.",
                "True means that colors are checked against a permission to see if they have permission to use that color.",
                "For the permissions of colors, see the StarLib documentation."));
        this.config.addDefault("simple.private.format", "{sender1} -> {sender2}: {message}");
        this.config.setComments("simple.private.format", List.of("This is the format used for messages sent in the private context.",
                "{sender1} is the option for the first player. This is dependent on who the message is being sent to.",
                "{sender2} is the option for the second player. This is dependent on who the message is being sent to.",
                "{message} is the message sent by the player. See the message section for more options.", 
                "How this works is that the sender1 variable will be replaced with who is sending this specific message.", 
                "The sender2 variable will be replaced with the one receiving the message."));
        this.config.addDefault("simple.private.selfformat", "&cme");
        this.config.setComments("simple.private.selfformat", List.of("This is the format for replacing the sender option with this for the self name."));
        this.config.addDefault("simple.private.otherformat", "{prefix}{name}{suffix}");
        this.config.setComments("simple.private.otherformat", List.of("This is the format for replacing the sender option with the other player's name."));
        this.config.addDefault("simple.private.message.parsecolors", true);
        this.config.setComments("simple.private.message.parsecolors", List.of("This option is for controlling if colors are automatically parsed for the message.",
                "True (default) means that colors will be parsed for every message.",
                "False means that colors will not be parsed for every message."));
        this.config.addDefault("simple.private.message.permissioncolors", false);
        this.config.setComments("simple.private.message.permissioncolors", List.of("This option controls if permissions are used for each color. The parsecolors option must be true for this to apply.",
                "False (default) means that all colors will be parsed regardless if the player has permission or not to use that color.",
                "True means that colors are checked against a permission to see if they have permission to use that color.",
                "For the permissions of colors, see the StarLib documentation."));
        this.config.save(configFile);
        
        if (this.config.getString("mode").equalsIgnoreCase("simple")) {
            this.chatManager = new SimpleChatManager(this);
        } else if (this.config.getString("mode").equalsIgnoreCase("complex")) {
            this.chatManager = new ComplexChatManager(this);
        } else {
            getLogger().warning("Invalid mode option provided in the config: " + this.config.getString("mode"));
            getServer().getPluginManager().disablePlugin(this);
            return;
        }
        
        getServer().getPluginManager().registerEvents(this.chatManager, this);
        
        if (getServer().getPluginManager().getPlugin("StarCore") == null) {
            getLogger().severe("StarCore not found, disabling plugin!");
            getServer().getPluginManager().disablePlugin(this);
            return;
        }
        
        if (getServer().getPluginManager().getPlugin("PlaceholderAPI") != null) {
            this.papiHook = new PAPIHook();
            getLogger().info("Hooked into PlaceholderAPI.");
        }
        
        if (getServer().getPluginManager().getPlugin("Vault") != null) {
            this.vaultHook = new VaultHook(this);
            getLogger().info("Hooked into Vault.");
        }
    }

    public FileConfiguration getConfiguration() {
        return config;
    }

    public PAPIHook getPapiHook() {
        return papiHook;
    }

    public VaultHook getVaultHook() {
        return vaultHook;
    }
}