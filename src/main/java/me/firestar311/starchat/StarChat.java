package me.firestar311.starchat;

import me.firestar311.starchat.hooks.PAPIHook;
import me.firestar311.starchat.hooks.VaultHook;
import org.bukkit.plugin.java.JavaPlugin;

public class StarChat extends JavaPlugin {
    
    private PAPIHook papiHook;
    private VaultHook vaultHook;
    
    public void onEnable() {
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
    }

    public PAPIHook getPapiHook() {
        return papiHook;
    }

    public VaultHook getVaultHook() {
        return vaultHook;
    }
}