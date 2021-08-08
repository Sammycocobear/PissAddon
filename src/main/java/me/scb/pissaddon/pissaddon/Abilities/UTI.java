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
import org.bukkit.entity.Entity;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.bukkit.permissions.Permission;
import org.bukkit.permissions.PermissionDefault;
import org.bukkit.permissions.Permission;
import org.bukkit.permissions.PermissionDefault;
import org.bukkit.util.Vector;

public class UTI extends PissAbility implements AddonAbility {
    private long cooldown;
    private long time;
    private Set<Entity> hurt;
    private Location location;
    private Vector direction;
    private Permission perm;
    private PissStreamListener listener;

    public UTI(Player player) {

        super(player);
        this.cooldown = 2000;
        this.location = this.player.getLocation();
        this.direction = player.getLocation().getDirection();
        this.perm = new Permission("bending.ability.UTI");
        if (this.bPlayer.isOnCooldown(this)) {
            return;
        }

        start();
    }

    @Override
    public void progress() {

        this.time = System.currentTimeMillis();
        Location loc = player.getLocation().clone().add(0.0D, 0.47673141357534D, 0.0D);
        this.affectTargets();
        double t = 0;
        t = t + 0.1 * Math.PI;
        for (double theta = 0; theta <= 2 * Math.PI; theta = theta + Math.PI / 32) {
            double x = t * Math.cos(theta);
            double y = t * Math.sin(theta);
            double z = -4 * Math.exp(-0.1 * t) * Math.sin(t) + 1.5;
            Vector vector = new Vector(x, y, z);
            double yaw = Math.toRadians((double)(-this.location.getYaw()));
            double pitch = Math.toRadians((double)this.location.getPitch());
            double oldX = vector.getX();
            double oldY = vector.getY();
            double oldZ = vector.getZ();
            vector.setY(oldY * Math.cos(pitch) - oldZ * Math.sin(pitch));
            vector.setZ(oldY * Math.sin(pitch) + oldZ * Math.cos(pitch));
            oldY = vector.getY();
            oldZ = vector.getZ();
            vector.setX(oldX * Math.cos(yaw) + oldZ * Math.sin(yaw));
            vector.setZ(-oldX * Math.sin(yaw) + oldZ * Math.cos(yaw));
            this.location.add(vector);
            GeneralMethods.displayColoredParticle("ffff00", loc);
            this.location.add(vector);
            player.setFallDistance(-10000);
        }
        if (t > 20) {
            player.setFallDistance(Float.MIN_VALUE);
            this.remove();
        }
    }
    private void affectTargets() {
        List<Entity> targets = GeneralMethods.getEntitiesAroundPoint(this.location, 1.0D);
        Iterator var2 = targets.iterator();

        while (var2.hasNext()) {
            Entity target = (Entity) var2.next();
            if (target.getUniqueId() != this.player.getUniqueId()) {

                target.setVelocity(this.direction);
                target.setFireTicks(1);
                if (!this.hurt.contains(target)) {
                    DamageHandler.damageEntity(target, 10.0D, this);
                    this.hurt.add(target);
                }

                target.setVelocity(this.direction);
                target.setFireTicks(0);
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
        return "UTI";
    }

    @Override
    public Location getLocation() {
        return this.location;
    }

    @Override
    public void load() {
        this.listener = new PissStreamListener();
    }

    @Override
    public void stop() {
        HandlerList.unregisterAll(new PissStreamListener());

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

