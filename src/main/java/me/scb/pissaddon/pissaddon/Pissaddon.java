package me.scb.pissaddon.pissaddon;

import com.projectkorra.projectkorra.ability.CoreAbility;
import me.scb.pissaddon.pissaddon.Config.Config;
import me.scb.pissaddon.pissaddon.Config.ConfigPath;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Logger;

public final class Pissaddon extends JavaPlugin {
    public static Pissaddon plugin;
    private static String author;
    private static String version;
    private static Logger log;

    public Pissaddon() {
        plugin = this;
        log = this.getLogger();
        version = "1.0.0";
        author = "Sammycocobear";
    }

    public void onEnable() {
        plugin = this;
        new ConfigPath();
        CoreAbility.registerPluginAbilities(this, "me.scb.pissaddon.pissaddon.Abilities");
        this.getServer().getPluginManager().registerEvents(new PissStreamListener(), this);
    }

    public void onDisable() {
        plugin.getLogger().info("Successfully disabled PissAddon");
    }


    public static Pissaddon getPlugin() {
        return plugin;
    }
    public static void reload() {
        getPlugin().reloadConfig();
        ConfigPath.ConfigPath.reloadConfig();
        getLog().info("PissAddon Reloaded.");
    }
    public static String getAuthor() {
        return author;
    }

    public static String getVersion() {
        return version;
    }

    public static Logger getLog() {
        return log;
    }

}

