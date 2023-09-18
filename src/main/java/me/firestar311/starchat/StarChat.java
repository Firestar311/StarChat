package me.firestar311.starchat;

import me.firestar311.starchat.hooks.PAPIHook;
import me.firestar311.starchat.hooks.VaultHook;
import org.bukkit.plugin.java.JavaPlugin;

public class StarChat extends JavaPlugin {
    
    private PAPIHook papiHook;
    private VaultHook vaultHook;
    
    public void onEnable() {
        getConfig().addDefault("enable-channels", true);
        getConfig().addDefault("enable-chatrooms", true);
        getConfig().addDefault("log-global", true);
        getConfig().addDefault("log-channels", true);
        getConfig().addDefault("log-chatrooms", true);
        getConfig().addDefault("use-color-permissions", true);
        getConfig().addDefault("console.name", "Console");
        getConfig().addDefault("console.displayname", "&4Console");
        getConfig().addDefault("global.format", "&f<{displayname}> {message}");
        getConfig().addDefault("global.usechannel", false);
        getConfig().options().copyDefaults(true);
        saveDefaultConfig();
        
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
        
        getServer().getPluginManager().registerEvents(new ChatListener(this), this);
    }

    public PAPIHook getPapiHook() {
        return papiHook;
    }

    public VaultHook getVaultHook() {
        return vaultHook;
    }
}