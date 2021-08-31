package me.scb.pissaddon.pissaddon.Config;

import com.projectkorra.projectkorra.ProjectKorra;
import com.projectkorra.projectkorra.configuration.ConfigManager;
import me.scb.pissaddon.pissaddon.Pissaddon;
import org.bukkit.configuration.file.FileConfiguration;

public class ConfigPath {
    public static Config ConfigPath;

    public ConfigPath() {
        this.PissConfig();
        ConfigPath = new Config("Config.yml");
        this.deathmessages();
    }

    private void deathmessages(){

        ConfigManager.languageConfig.get().addDefault("Abilities.Water.PissWave.DeathMessage", "{victim} was washed away by {attacker}'s {ability}");
        ConfigManager.languageConfig.get().addDefault("Abilities.Water.GoldenShower.DeathMessage", "{victim} drowned in {attacker}'s {ability}" + " kinky");
        ConfigManager.languageConfig.get().addDefault("Abilities.Water.PissAura.DeathMessage", "{victim} was boiled to death {attacker}'s {ability}");
        ConfigManager.languageConfig.get().addDefault("Abilities.Water.PissSplatter.DeathMessage", "{victim} was washed away by {attacker}'s {ability}");
        ConfigManager.languageConfig.get().addDefault("Abilities.Water.PissStream.DeathMessage", "{victim} drank too much of {attacker}'s {ability}");
        ConfigManager.languageConfig.get().addDefault("Abilities.Water.PissSwirl.DeathMessage", "{victim} was washed away by {attacker}'s {ability}");
        ConfigManager.languageConfig.get().addDefault("Abilities.Water.SplitStream.DeathMessage", "{attacker}'s {ability} went up too many of {victim}'s");
        ConfigManager.languageConfig.get().addDefault("Abilities.Water.Tinkle.DeathMessage", "{victim} was washed away by {attacker}'s {ability}");
        ConfigManager.languageConfig.get().addDefault("Abilities.Water.ToiletExplode.DeathMessage", "{victim} was exploded by {attacker}'s fat shit");
        ConfigManager.languageConfig.get().addDefault("Abilities.Water.UrinalInfection.DeathMessage", "{victim} was burned to death when {attacker}'s gave {victim} their {ability}");
        ConfigManager.languageConfig.get().addDefault("Abilities.Water.UTI.DeathMessage", "{victim} was choked to death by the stench of {attacker}'s {ability}");



        ConfigManager.languageConfig.save();

    }




    private void PissConfig() {

        FileConfiguration config = Pissaddon.getPlugin().getConfig();
        ProjectKorra.plugin.getConfig().addDefault("suss", "sus");

        config.addDefault("ExtraAbilities.Sammycocobear.PissStream.cooldown", 2000);
        config.addDefault("ExtraAbilities.Sammycocobear.PissStream.distance", 20);
        config.addDefault("ExtraAbilities.Sammycocobear.PissStream.damage", 2);
        config.addDefault("ExtraAbilities.Sammycocobear.PissStream.hitbox",1);

        config.addDefault("ExtraAbilities.Sammycocobear.UTI.cooldown", 2000L);
        config.addDefault("ExtraAbilities.Sammycocobear.UTI.distance", 10.0D);
        config.addDefault("ExtraAbilities.Sammycocobear.UTI.damage", 5.0D);
        config.addDefault("ExtraAbilities.Sammycocobear.UTI.slowduration", 10);
        config.addDefault("ExtraAbilities.Sammycocobear.UTI.slowamp", 5);
        config.addDefault("ExtraAbilities.Sammycocobear.UTI.hitbox", 1);

        config.addDefault("ExtraAbilities.Sammycocobear.PissAura.cooldown", 2000L);
        config.addDefault("ExtraAbilities.Sammycocobear.PissAura.distance", 25.0D);
        config.addDefault("ExtraAbilities.Sammycocobear.PissAura.damage", 5.0D);
        config.addDefault("ExtraAbilities.Sammycocobear.PissAura.ParticleDensity", 10);
        config.addDefault("ExtraAbilities.Sammycocobear.PissAura.sinWaveAmount", 3.25);
        config.addDefault("ExtraAbilities.Sammycocobear.PissAura.arrivaltimeinticks", 20);
        config.addDefault("ExtraAbilities.Sammycocobear.PissAura.maxRadius", 10);
        config.addDefault("ExtraAbilities.Sammycocobear.PissAura.damage", 2);

        config.addDefault("ExtraAbilities.Sammycocobear.PissSlide.distance", 20);
        config.addDefault("ExtraAbilities.Sammycocobear.PissSlide.cooldown",2000);

        config.addDefault("ExtraAbilities.Sammycocobear.UrinalInfection.cooldown", 2000);
        config.addDefault("ExtraAbilities.Sammycocobear.UrinalInfection.distance", 10);
        config.addDefault("ExtraAbilities.Sammycocobear.UrinalInfection.damage", 2);
        config.addDefault("ExtraAbilities.Sammycocobear.UrinalInfection.poisonamp", 3);
        config.addDefault("ExtraAbilities.Sammycocobear.UrinalInfection.poisonduration", 10);

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

        config.addDefault("ExtraAbilities.Sammycocobear.ToiletExplode.width", 1);
        config.addDefault("ExtraAbilities.Sammycocobear.ToiletExplode.height", 1);
        config.addDefault("ExtraAbilities.Sammycocobear.ToiletExplode.cooldown", 2000);
        config.addDefault("ExtraAbilities.Sammycocobear.ToiletExplode.damage", 1);
        config.addDefault("ExtraAbilities.Sammycocobear.ToiletExplode.duration", 2000);

        config.addDefault("ExtraAbilities.Sammycocobear.PissSplatter.cooldown", 2000);
        config.addDefault("ExtraAbilities.Sammycocobear.PissSplatter.duration", 2000);
        config.addDefault("ExtraAbilities.Sammycocobear.PissSplatter.radius", 2);
        config.addDefault("ExtraAbilities.Sammycocobear.PissSplatter.hitbox", 1);

        config.addDefault("ExtraAbilities.Sammycocobear.GoldenShower.hitbox", 2);
        config.addDefault("ExtraAbilities.Sammycocobear.GoldenShower.radius", 2);
        config.addDefault("ExtraAbilities.Sammycocobear.GoldenShower.particles", 27);
        config.addDefault("ExtraAbilities.Sammycocobear.GoldenShower.damage", 1);
        config.addDefault("ExtraAbilities.Sammycocobear.GoldenShower.cooldown", 5000);
        config.addDefault("ExtraAbilities.Sammycocobear.GoldenShower.sourcerange", 12);
        config.addDefault("ExtraAbilities.Sammycocobear.GoldenShower.duration", 2000);

        config.addDefault("ExtraAbilities.Sammycocobear.WaterSports.jumpvelo", 2);
        config.addDefault("ExtraAbilities.Sammycocobear.WaterSports.height", 1);
        config.addDefault("ExtraAbilities.Sammycocobear.WaterSports.cooldown", 2000);




        config.options().copyDefaults(true);
        Pissaddon.getPlugin().saveConfig();

    }

}
