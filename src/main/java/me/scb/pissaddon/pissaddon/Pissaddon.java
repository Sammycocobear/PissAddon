package me.scb.pissaddon.pissaddon;

import com.projectkorra.projectkorra.ability.CoreAbility;
import me.scb.pissaddon.pissaddon.CMDS.cmds;
import me.scb.pissaddon.pissaddon.Config.ConfigPath;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Logger;

public final class Pissaddon extends JavaPlugin {
    public static Pissaddon plugin;
    private static String author;
    private static Logger log;


    public Pissaddon() {
        plugin = this;
        log = this.getLogger();
        author = "Sammycocobear";
    }

    public void onEnable() {
        plugin = this;
        new ConfigPath();
        CoreAbility.registerPluginAbilities(this, "me.scb.pissaddon.pissaddon.Abilities");
        new cmds();
        this.getServer().getPluginManager().registerEvents(new PissListener(), this);
    }

    public void onDisable() {
        plugin.getLogger().info("Successfully disabled PissAddon");
    }


    public static Pissaddon getPlugin() {
        return plugin;
    }

    public static void reload() { getPlugin().reloadConfig();
        ConfigPath.ConfigPath.reloadConfig();
        getLog().info("PissAddon Reloaded.");
    }
    public static String getAuthor() {
        return author;
    }

    public static String getVersion() {
        return "1.1.1";
    }

    public static Logger getLog() {
        return log;
    }

}

