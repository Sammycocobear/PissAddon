package me.scb.pissaddon.pissaddon.Abilities;

import com.projectkorra.projectkorra.GeneralMethods;
import com.projectkorra.projectkorra.ability.AddonAbility;
import com.projectkorra.projectkorra.ability.CoreAbility;
import com.projectkorra.projectkorra.util.DamageHandler;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import me.scb.pissaddon.pissaddon.PissAbility;
import me.scb.pissaddon.pissaddon.PissListener;
import me.scb.pissaddon.pissaddon.Pissaddon;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Panda;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.bukkit.permissions.Permission;
import org.bukkit.util.Vector;


public class GoldenShower extends PissAbility implements AddonAbility {
    private Permission perm;
    private PissListener listener;
    private static final double DAMAGE = 2.0D;
    private static final double RANGE = 25.0D;
    private static final long COOLDOWN = 2000L;
    private Location location;
    private Vector direction;
    private double distancetraveled;
    private long time;
    private long duration;
    private long cooldown;
    private double sourcerange;
    private int particles;
    private int radius;
    private double damage;
    private double hitbox;


    public GoldenShower(Player player) {
        super(player);
        setfields();
        this.location = GeneralMethods.getTargetedLocation(player, sourcerange, new Material[0]);
        this.direction = player.getLocation().getDirection();
        this.direction.multiply(0.8D);
        this.bPlayer.addCooldown(this);
        this.distancetraveled = 0.0D;
        this.duration = 5000L;
        if (CoreAbility.hasAbility(player, GoldenShower.class)){
            return;
        }
        this.bPlayer.addCooldown(this);
        this.start();
    }

    private void setfields() {
        duration = Pissaddon.getPlugin().getConfig().getLong("ExtraAbilities.Sammycocobear.GoldenShower.duration");
        sourcerange = Pissaddon.getPlugin().getConfig().getDouble("ExtraAbilities.Sammycocobear.GoldenShower.sourcerange");
        cooldown = Pissaddon.getPlugin().getConfig().getLong("ExtraAbilities.Sammycocobear.GoldenShower.cooldown");
        particles = Pissaddon.getPlugin().getConfig().getInt("ExtraAbilities.Sammycocobear.GoldenShower.particles");
        radius = Pissaddon.getPlugin().getConfig().getInt("ExtraAbilities.Sammycocobear.GoldenShower.radius");
        hitbox = Pissaddon.getPlugin().getConfig().getDouble("ExtraAbilities.Sammycocobear.GoldenShower.hitbox");
        damage = Pissaddon.getPlugin().getConfig().getDouble("ExtraAbilities.Sammycocobear.GoldenShower.damage");

    }

    public void progress() {
        if (this.player.isDead() || !this.player.isOnline()) {
            this.remove();
            return;
        } else if (GeneralMethods.isRegionProtectedFromBuild(this, location)) {
            this.remove();
            return;
        }
        this.time = System.currentTimeMillis();
        if (!this.bPlayer.canBendIgnoreBindsCooldowns(this)) {
            this.remove();
        } else if (this.location.getBlock().getType().isSolid()) {
            this.remove();
        } else if (this.distancetraveled > 25.0D) {
            this.remove();
        } else {
            this.affectTargets();
            GeneralMethods.displayColoredParticle("FFFF00", this.location, particles, radius, radius, radius);
            if (ThreadLocalRandom.current().nextInt(6) == 0) {
                this.location.getWorld().playSound(this.location, Sound.WEATHER_RAIN_ABOVE, 0.1F, 1.0F);
            }

            this.time = System.currentTimeMillis();
            if (this.time - this.getStartTime() > this.duration) {
                this.remove();
                return;
            }
        }

    }

    private void affectTargets() {
        List<Entity> targets = GeneralMethods.getEntitiesAroundPoint(this.location, hitbox);
        Iterator var2 = targets.iterator();

        while(var2.hasNext()) {
            Entity target = (Entity)var2.next();
            if (target.getUniqueId() != this.player.getUniqueId()) {
                DamageHandler.damageEntity(target, damage, this);
            }
        }

    }


    public boolean isSneakAbility() {
        return false;
    }

    public boolean isHarmlessAbility() {
        return false;
    }

    public long getCooldown() {
        return cooldown;
    }

    public String getName() {
        return "GoldenShower";
    }

    public Location getLocation() {
        return this.location;
    }

    public void load() {
        this.listener = new PissListener();
    }

    public void stop() {
        HandlerList.unregisterAll(this.listener);
    }

    public String getAuthor() {
        return "Sammycocobear";
    }

    public String getVersion() {
        return Pissaddon.getVersion();
    }

    public String getDescription() {
        return "Send a storm of cloudy pee on your opponent";
    }
    public String getInstructions() {
        return "<left-click>";
    }
    @Override
    public boolean isEnabled() {
        String path = "ExtraAbilities.Sammycocobear.GoldenShower.Enabled";
        if (Pissaddon.getPlugin().getConfig().contains(path)) {
            return Pissaddon.getPlugin().getConfig().getBoolean(path);
        }
        return false;
    }


}