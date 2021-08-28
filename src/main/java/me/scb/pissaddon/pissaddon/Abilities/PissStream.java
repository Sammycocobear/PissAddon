package me.scb.pissaddon.pissaddon.Abilities;

import com.projectkorra.projectkorra.GeneralMethods;
import com.projectkorra.projectkorra.ProjectKorra;
import com.projectkorra.projectkorra.ability.AddonAbility;
import com.projectkorra.projectkorra.configuration.ConfigManager;
import com.projectkorra.projectkorra.util.DamageHandler;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;
import me.scb.pissaddon.pissaddon.PissAbility;
import me.scb.pissaddon.pissaddon.PissStreamListener;
import me.scb.pissaddon.pissaddon.Pissaddon;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.bukkit.permissions.Permission;
import org.bukkit.permissions.PermissionDefault;
import org.bukkit.util.Vector;

public class PissStream extends PissAbility implements AddonAbility {
    private long cooldown;
    private double damage;
    private int distance;
    private Permission perm;
    private PissStreamListener listener;
    private Location location;
    private Vector direction;
    private double distancetraveled;
    private Set<Entity> hurt;

    public PissStream(Player player) {
        super(player);
        this.location = player.getLocation().clone().add(0.0D, 0.47673141357534D, 0.0D);
        this.direction = player.getLocation().getDirection();
        this.direction.multiply(0.8D);
        this.distancetraveled = 0.0D;
        this.hurt = new HashSet();
        this.perm = new Permission("bending.ability.PissStream");
        if (!hasAbility(player, PissStream.class) && !this.bPlayer.isOnCooldown(this) && this.bPlayer.canBendIgnoreBinds(this)) {
        }

        this.setfields();
        this.bPlayer.addCooldown("PissStream", this.cooldown);
        this.start();
    }

    private void setfields() {
        this.cooldown = ConfigManager.getConfig().getLong("ExtraAbilities.Sammycocobear.PsssStream.cooldown");
        this.distance = ConfigManager.getConfig().getInt("ExtraAbilities.Sammycocobear.PissStream.distance");
        this.damage = ConfigManager.getConfig().getDouble("ExtraAbilities.Sammycocobear.PissStream.damage");
    }

    public void progress() {
        if (!this.bPlayer.canBendIgnoreBindsCooldowns(this)) {
            this.remove();
        } else if (this.location.getBlock().getType().isSolid()) {
            this.remove();
        } else if (this.distancetraveled > distance) {
            this.remove();
        } else {
            this.affectTargets();

            GeneralMethods.displayColoredParticle("ffff00", this.location, 1, 0.1D, 0.1D, 0.1D);
            if (ThreadLocalRandom.current().nextInt(6) == 0) {
                this.location.getWorld().playSound(this.location, Sound.WEATHER_RAIN, 0.1F, 1.0F);
            }
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

    public void remove() {
        super.remove();
        this.hurt.clear();
    }

    public boolean isSneakAbility() {
        return false;
    }

    public boolean isHarmlessAbility() {
        return false;
    }

    public long getCooldown() {
        return this.cooldown;
    }

    public String getName() {
        return "PissStream";
    }

    public Location getLocation() {
        return this.location;
    }

    public void load() {
        this.listener = new PissStreamListener();

    }

    public void stop() {
        HandlerList.unregisterAll(this.listener);
    }

    public String getAuthor() {
        return "Sammycocobear";
    }

    public String getVersion() {
        return "1.0.0";
    }
}