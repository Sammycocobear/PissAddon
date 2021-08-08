package me.scb.pissaddon.pissaddon.Abilities;

import com.projectkorra.projectkorra.GeneralMethods;
import com.projectkorra.projectkorra.ProjectKorra;
import com.projectkorra.projectkorra.ability.AddonAbility;
import com.projectkorra.projectkorra.util.DamageHandler;
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

import java.util.Iterator;
import java.util.List;

//: Spreads on the ground a circle of yellow particles damaging whoever walks into it. Lasts for 4 seconds
//PissSplatter - Aim and hold shift
public class PissSplatter extends PissAbility implements AddonAbility {
    private long cooldown;
    private Permission perm;
    private PissStreamListener listener;
    private static final double DAMAGE = 1.0D;
    private static final double RANGE = 25.0D;
    private static final long COOLDOWN = 2000L;
    private Location location;
    private org.bukkit.util.Vector direction;
    private double distancetraveled;
    private Object Vector;
    private Object String;
    private final double cosX;
    private final double sinX;
    private final double cosY;
    private final double sinY;
    private final double yaw;
    private final double pitch;
    private long time;
    private long duration;

    public PissSplatter(Player player) {
        super(player);
        this.location = player.getLocation().clone().add(0.0D, 0.47673141357534D, 0.0D);
        this.direction = player.getLocation().getDirection().normalize().multiply(0.8D);
        this.direction.multiply(0.8D);
        this.distancetraveled = 0.0D;
        this.yaw = Math.toRadians((double)player.getLocation().getYaw());
        this.pitch = Math.toRadians((double)(player.getLocation().getPitch() - 90.0F));
        this.cosX = Math.cos(this.pitch);
        this.sinX = Math.sin(this.pitch);
        this.cosY = Math.cos(-this.yaw);
        this.sinY = Math.sin(-this.yaw);

        this.cooldown=2000;
        this.duration = 500L;
        this.bPlayer.addCooldown("PissSplatter", this.cooldown);
        this.start();
    }

    public void progress() {
        this.time = System.currentTimeMillis();
        if (this.time - this.getStartTime() > this.duration) {
            this.remove();
        } else if (!this.bPlayer.canBendIgnoreBindsCooldowns(this)) {
            this.remove();
        } else if (GeneralMethods.isSolid(this.location.getBlock())) {
            this.remove();
        } else {
            this.affectTargets();

            for(int i = 0; i < 360; ++i) {
                double x = Math.sin(i);
                double y = Math.cos(i);
                double z = 0;
                Vector vector = new Vector(x, z, y);
                vector = vector.multiply(4D);
                this.location.add(vector);
                GeneralMethods.displayColoredParticle("ffff00", this.location);
                this.location.subtract(vector);
            }

        }
    }


    private void affectTargets() {
        List<Entity> targets = GeneralMethods.getEntitiesAroundPoint(this.location, 3.0D);
        Iterator var2 = targets.iterator();

        while(var2.hasNext()) {
            Entity target = (Entity)var2.next();
            if (target.getUniqueId() != this.player.getUniqueId()) {
                int knockbackDistance = 0;
                target.setVelocity(GeneralMethods.getDirection(target.getLocation(), this.location).multiply(knockbackDistance));
                DamageHandler.damageEntity(target, 1.0D, this);
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
        return this.cooldown;
    }

    public String getName() {
        return "PissSplatter";
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
        return "Left click to unleash a deafening scream";
    }
}
