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
        config.addDefault("DeathMessages.Default", "{victim} was slain by {attacker}'s {ability}");

        config.addDefault("ExtraAbilities.Sammycocobear.PissStream.cooldown", 2000);
        config.addDefault("ExtraAbilities.Sammycocobear.PissStream.distance", 20);
        config.addDefault("ExtraAbilities.Sammycocobear.PissStream.damage", 2);

        config.addDefault("ExtraAbilities.Sammycocobear.UTI.cooldown", 2000L);
        config.addDefault("ExtraAbilities.Sammycocobear.UTI.distance", 10.0D);
        config.addDefault("ExtraAbilities.Sammycocobear.UTI.damage", 5.0D);
        config.addDefault("ExtraAbilities.Sammycocobear.UTI.slowduration", 10);
        config.addDefault("ExtraAbilities.Sammycocobear.UTI.slowamp", 5);
        config.addDefault("ExtraAbilities.Sammycocobear.UTI.hitbox", 1);

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

        config.addDefault("ExtraAbilities.Sammycocobear.Tinkle.size", 10);
        config.addDefault("ExtraAbilities.Sammycocobear.Tinkle.cooldown", 2000);
        config.addDefault("ExtraAbilities.Sammycocobear.Tinkle.duration", 1000);
        config.addDefault("ExtraAbilities.Sammycocobear.Tinkle.hitbox", 1);
        config.addDefault("ExtraAbilities.Sammycocobear.Tinkle.damage",2);

        config.addDefault("ExtraAbilities.Sammycocobear.PeeDrain.particles", 90);
        config.addDefault("ExtraAbilities.Sammycocobear.PeeDrain.sourcerange", 15);
        config.addDefault("ExtraAbilities.Sammycocobear.PeeDrain.hitbox", 1);
        config.addDefault("ExtraAbilities.Sammycocobear.PeeDrain.cooldown", 2000);
        config.addDefault("ExtraAbilities.Sammycocobear.PeeDrain.size", 1);
        config.addDefault("ExtraAbilities.Sammycocobear.PeeDrain.duration", 1000);
        config.addDefault("ExtraAbilities.Sammycocobear.PeeDrain.knockback", 1);

        config.addDefault("ExtraAbilities.Sammycocobear.WaterSports.speedduration", 10);
        config.addDefault("ExtraAbilities.Sammycocobear.WaterSports.speedamp", 10);
        config.addDefault("ExtraAbilities.Sammycocobear.WaterSports.duration", 10000);
        config.addDefault("ExtraAbilities.Sammycocobear.WaterSports.cooldown", 2000);
        config.addDefault("ExtraAbilities.Sammycocobear.WaterSports.resistanceduration", 10);
        config.addDefault("ExtraAbilities.Sammycocobear.WaterSports.resistanceamp", 10);


        config.addDefault("ExtraAbilities.Sammycocobear.PissWave.distance", 10);
        config.addDefault("ExtraAbilities.Sammycocobear.PissWave.speed", 1);
        config.addDefault("ExtraAbilities.Sammycocobear.PissWave.damage", 2);
        config.addDefault("ExtraAbilities.Sammycocobear.PissWave.hitbox", 1);
        config.addDefault("ExtraAbilities.Sammycocobear.PissWave.cooldown", 2000);
        config.addDefault("ExtraAbilities.Sammycocobear.PissWave.height", 2);
        config.addDefault("ExtraAbilities.Sammycocobear.PissWave.width", 5);
        config.addDefault("Abilities.Piss.PissWave.DeathMessage", "{victim} was washed away by {attacker}'s {ability}");




        config.options().copyDefaults(true);
        Pissaddon.getPlugin().saveConfig();

    }

}
