package me.scb.pissaddon.pissaddon.Config;

import me.scb.pissaddon.pissaddon.Pissaddon;
import org.bukkit.configuration.file.FileConfiguration;

public class ConfigPath {
    public static Config ConfigPath;

    public ConfigPath() {
        this.PissConfig();
        ConfigPath = new Config("Config.yml");
    }




    private void PissConfig() {
        FileConfiguration config = Pissaddon.getPlugin().getConfig();
        config.addDefault("ExtraAbilities.Sammycocobear.PissStream.cooldown", 2000);
        config.addDefault("ExtraAbilities.Sammycocobear.PissStream.distance", 20);
        config.addDefault("ExtraAbilities.Sammycocobear.PissStream.damage", 2);
        config.addDefault("ExtraAbilities.Sammycocobear.UTI.cooldown", 2000L);
        config.addDefault("ExtraAbilities.Sammycocobear.UTI.distance", 10.0D);
        config.addDefault("ExtraAbilities.Sammycocobear.UTI.damage", 5.0D);
        config.addDefault("ExtraAbilities.Sammycocobear.PissAura.cooldown", 2000L);
        config.addDefault("ExtraAbilities.Sammycocobear.PissAura.distance", 25.0D);
        config.addDefault("ExtraAbilities.Sammycocobear.PissAura.damage", 5.0D);
        config.addDefault("ExtraAbilities.Sammycocobear.PissAura.speed", 0.7D);
        config.addDefault("ExtraAbilities.Sammycocobear.PissAura.ParticleDensity", 10);
        config.addDefault("ExtraAbilities.Sammycocobear.PissAura.sinWaveAmount", 3.25);
        config.addDefault("ExtraAbilities.Sammycocobear.PissSlide.distance", 20);
        config.addDefault("ExtraAbilities.Sammycocobear.PissSlide.cooldown",2000);
        config.addDefault("ExtraAbilities.Sammycocobear.UrinalInfection.cooldown", 2000);
        config.addDefault("ExtraAbilities.Sammycocobear.UrinalInfection.distance", 10);
        config.addDefault("ExtraAbilities.Sammycocobear.UrinalInfection.damage", 2);
        config.addDefault("ExtraAbilities.Sammycocobear.SplitStream.damage", 2);
        config.addDefault("ExtraAbilities.Sammycocobear.SplitStream.cooldown", 2000);

        config.options().copyDefaults(true);
        Pissaddon.getPlugin().saveConfig();

    }

}
