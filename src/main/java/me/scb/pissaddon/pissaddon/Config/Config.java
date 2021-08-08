package me.scb.pissaddon.pissaddon.Config;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;

import me.scb.pissaddon.pissaddon.Pissaddon;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

public class Config {
    public static Config ConfigPath;
    private final Path path;
    private final FileConfiguration config;

    public Config(String name) {
        this.path = Paths.get(Pissaddon.getPlugin().getDataFolder().toString(), name);
        this.config = YamlConfiguration.loadConfiguration(this.path.toFile());
        this.reloadConfig();
    }

    private void createConfig() {
        try {
            Files.createFile(this.path);
        } catch (IOException var2) {
            var2.printStackTrace();
        }

    }

    public FileConfiguration getConfig() {
       return this.config;
    }

    public void reloadConfig() {
        if (Files.notExists(this.path, new LinkOption[0])) {
            this.createConfig();
        }

        try {
            this.config.load(this.path.toFile());
        } catch (Exception var2) {
            var2.printStackTrace();
        }

    }

    public void saveConfig() {
        try {
            this.config.options().copyDefaults(true);
            this.config.save(this.path.toFile());
        } catch (Exception var2) {
            var2.printStackTrace();
        }

    }

}
