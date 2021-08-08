package me.scb.pissaddon.pissaddon.Abilities;

import com.projectkorra.projectkorra.GeneralMethods;
import com.projectkorra.projectkorra.ProjectKorra;
import com.projectkorra.projectkorra.ability.AddonAbility;
import com.projectkorra.projectkorra.util.DamageHandler;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import me.scb.pissaddon.pissaddon.PissAbility;
import me.scb.pissaddon.pissaddon.PissStreamListener;
import me.scb.pissaddon.pissaddon.Pissaddon;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.bukkit.permissions.Permission;
import org.bukkit.permissions.PermissionDefault;
import org.bukkit.util.Vector;


public class GoldenShower extends PissAbility implements AddonAbility {
    private Permission perm;
    private PissStreamListener listener;
    private static final double DAMAGE = 2.0D;
    private static final double RANGE = 25.0D;
    private static final long COOLDOWN = 2000L;
    private Location location;
    private Vector direction;
    private double distancetraveled;
    private long time;
    private long duration;

    public GoldenShower(Player player) {
        super(player);
        this.location = GeneralMethods.getTargetedLocation(player, 25.0D, new Material[0]);
        this.direction = player.getLocation().getDirection();
        this.direction.multiply(0.8D);
        this.bPlayer.addCooldown(this);
        this.distancetraveled = 0.0D;
        this.duration = 5000L;
        this.start();
    }

    public void progress() {
        this.time = System.currentTimeMillis();
        if (!this.bPlayer.canBendIgnoreBindsCooldowns(this)) {
            this.remove();
        } else if (this.location.getBlock().getType().isSolid()) {
            this.remove();
        } else if (this.distancetraveled > 25.0D) {
            this.remove();
        } else {
            this.affectTargets();
            GeneralMethods.displayColoredParticle("FFFF00", this.location, 27, 2.0D, 2.0D, 2.0D);
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
        List<Entity> targets = GeneralMethods.getEntitiesAroundPoint(this.location, 2.0D);
        Iterator var2 = targets.iterator();

        while(var2.hasNext()) {
            Entity target = (Entity)var2.next();
            if (target.getUniqueId() != this.player.getUniqueId()) {
                target.setVelocity(this.direction);
                DamageHandler.damageEntity(target, 2.0D, this);
                target.setFireTicks(1);
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
        return 2000L;
    }

    public String getName() {
        return "GoldenShower";
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

    public String getDescription() {
        return "Piss Shower ";
    }
}