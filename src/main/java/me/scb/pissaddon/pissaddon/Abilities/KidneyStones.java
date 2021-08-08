package me.scb.pissaddon.pissaddon.Abilities;

import com.projectkorra.projectkorra.GeneralMethods;
import com.projectkorra.projectkorra.ProjectKorra;
import com.projectkorra.projectkorra.ability.AddonAbility;
import com.projectkorra.projectkorra.configuration.ConfigManager;
import com.projectkorra.projectkorra.util.DamageHandler;
import me.scb.pissaddon.pissaddon.PissAbility;
import me.scb.pissaddon.pissaddon.PissStreamListener;
import me.scb.pissaddon.pissaddon.Pissaddon;
import net.minecraft.server.v1_16_R3.BlockBase;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.bukkit.permissions.Permission;
import org.bukkit.permissions.PermissionDefault;
import org.bukkit.util.Vector;

import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;
//*
//in progress rn xD uhh working on pissaura first

public class KidneyStones extends PissAbility implements AddonAbility {
    private long cooldown;
    private double damage;
    private int distance;
    private Permission perm;
    private PissStreamListener listener;
    private Location location;
    private Vector direction;
    private double distancetraveled;
    private Set<Entity> hurt;



    public KidneyStones(Player player) {
        super(player);
        this.location = player.getLocation().clone().add(0.0D, 0.47673141357534D, 0.0D);
        this.hurt = new HashSet();
        this.perm = new Permission("bending.ability.KidneyStones");

    }
    private void setfields() {
        this.cooldown = ConfigManager.getConfig().getLong("ExtraAbilities.Sammycocobear.KidneyStones.cooldown");
        this.distance = ConfigManager.getConfig().getInt("ExtraAbilities.Sammycocobear.KidneyStones.distance");
        this.damage = ConfigManager.getConfig().getDouble("ExtraAbilities.Sammycocobear.KidneyStones.damage");
    }

    @Override
    public void progress() {
        if (!this.bPlayer.canBendIgnoreBindsCooldowns(this)) {
            this.remove();
        } else if (this.location.getBlock().getType().isSolid()) {
            this.remove();
        } else if (this.distancetraveled > 45.0D) {
            this.remove();
        } else {
            this.affectTargets();
            GeneralMethods.displayColoredParticle("000000", this.location);
            if (ThreadLocalRandom.current().nextInt(6) == 0) {
            }

            this.location.getWorld().playSound(this.location, Sound.WEATHER_RAIN, 0.1F, 1.0F);
            this.location.add(this.direction);
            this.distancetraveled += this.direction.length();
        }

    }
    private void affectTargets() {
        List<Entity> targets = GeneralMethods.getEntitiesAroundPoint(this.location, 1.0D);
        Iterator var2 = targets.iterator();

        while(var2.hasNext()) {
            Entity target = (Entity)var2.next();
            if (target.getUniqueId() != this.player.getUniqueId()) {
                if (!this.hurt.contains(target)) {
                    DamageHandler.damageEntity(target, this.damage, this);
                    this.hurt.add(target);
                }

                target.setVelocity(this.direction);
                target.setFireTicks(1);
                this.remove();
            }
        }

    }

    @Override
    public boolean isSneakAbility() {
        return false;
    }

    @Override
    public boolean isHarmlessAbility() {
        return false;
    }

    @Override
    public long getCooldown() {
        return this.cooldown;
    }

    @Override
    public String getName() {
        return "KidneyStones";
    }

    @Override
    public Location getLocation() {
        return this.location;
    }

    @Override
    public void load() {
        this.listener = new PissStreamListener();
        ConfigManager.getConfig().addDefault("ExtraAbilities.Sammycocobear.KidneyStones.cooldown", 2000L);
        ConfigManager.getConfig().addDefault("ExtraAbilities.Sammycocobear.KidneyStones.distance", 10.0D);
        ConfigManager.getConfig().addDefault("ExtraAbilities.Sammycocobear.KidneyStones.damage", 5.0D);
        ConfigManager.defaultConfig.save();
    }

    @Override
    public void stop() {
        HandlerList.unregisterAll(this.listener);
    }

    @Override
    public String getAuthor() {
        return "Sammycocobear";
    }

    @Override
    public String getVersion() {
        return "1.0.0";
    }
}
