package me.scb.pissaddon.pissaddon.Abilities;

import com.projectkorra.projectkorra.GeneralMethods;
import com.projectkorra.projectkorra.ProjectKorra;
import com.projectkorra.projectkorra.ability.AddonAbility;
import com.projectkorra.projectkorra.configuration.ConfigManager;
import com.projectkorra.projectkorra.util.DamageHandler;
import me.scb.pissaddon.pissaddon.PissAbility;
import me.scb.pissaddon.pissaddon.PissListener;
import me.scb.pissaddon.pissaddon.Pissaddon;
import org.bukkit.Location;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Entity;
import org.bukkit.entity.FallingBlock;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.bukkit.util.Vector;

import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
//done
public class PissSwirl extends PissAbility implements AddonAbility {
    private int phase;
    private final int particleDensity;
    private final int angleIncrease;
    private double radius;
    private long cooldown;
    private Location location;
    private Location origin;
    private Vector direction;
    private double speed;
    private int distance;
    private double damage;
    private double hitbox;

    private double rotation;
    private Set<Entity> hurt;


    public PissSwirl(Player player) {
        super(player);
        this.direction = player.getLocation().getDirection();
        this.location = player.getLocation().clone().add(0.0D, 0.47673141357534D, 0.0D);
        this.origin = player.getLocation().clone().add(0.0D, 0.47673141357534D, 0.0D);
        this.distance = 25;
        this.rotation = 0.0D;
        this.hurt = new HashSet();
        this.origin = player.getLocation().clone().add(0.0D, 0.47673141357534D, 0.0D);
        this.angleIncrease = 5;
        this.particleDensity = 5;
        this.phase = 0;
        if (!hasAbility(player, PissSwirl.class)) {
            if (!this.bPlayer.isOnCooldown(this)) {
                if (this.bPlayer.canBend(this)) {
                    setfields();
                    this.bPlayer.addCooldown(this, this.cooldown);
                    this.start();
                }
            }
        }
    }

    private void setfields() {
        radius = Pissaddon.getPlugin().getConfig().getDouble("ExtraAbilities.Sammycocobear.PissSwirl.radius");
        cooldown = Pissaddon.getPlugin().getConfig().getLong("ExtraAbilities.Sammycocobear.PissSwirl.cooldown");
        speed = Pissaddon.getPlugin().getConfig().getDouble("ExtraAbilities.Sammycocobear.PissSwirl.speed");
        damage = Pissaddon.getPlugin().getConfig().getDouble("ExtraAbilities.Sammycocobear.PissSwirl.damage");
        hitbox = Pissaddon.getPlugin().getConfig().getDouble("ExtraAbilities.Sammycocobear.PissSwirl.hitbox");


    }

    public void progress() {
        if(!this.bPlayer.canBendIgnoreBindsCooldowns(this)){
            remove();
            return;
        }
        if (this.player.isDead() || !this.player.isOnline()) {
            this.remove();
            return;
        } else if (GeneralMethods.isRegionProtectedFromBuild(this, location)) {
            this.remove();
            return;
        }
        if (this.location.distanceSquared(this.origin) >= (double)(this.distance * this.distance)) {
            this.remove();
        } else {
            this.affectTargets();
            for (int i = 0; i < this.particleDensity; i++) {
                double x = this.radius * Math.sin(Math.toRadians(phase));
                double y = this.radius * Math.cos(Math.toRadians(phase));
                double z = 0;
                Vector vector = new Vector(x, y, z);
                vector = vector.multiply(2D);
                double yaw = Math.toRadians((double)(-this.location.getYaw()));
                double pitch = Math.toRadians((double)this.location.getPitch());
                double oldX = vector.getX();
                double oldY = vector.getY();
                double oldZ = vector.getZ();
                vector.setY(oldY * Math.cos(pitch) - oldZ * Math.sin(pitch));
                vector.setZ(oldY * Math.sin(pitch) + oldZ * Math.cos(pitch));
                oldZ = vector.getZ();
                vector.setX(oldX * Math.cos(yaw) + oldZ * Math.sin(yaw));
                vector.setZ(-oldX * Math.sin(yaw) + oldZ * Math.cos(yaw));
                this.location.add(vector);
                GeneralMethods.displayColoredParticle("ffff00", this.location);
                this.location.subtract(vector);
                this.location.add(this.direction.normalize().multiply(this.speed).multiply(1.0 / this.particleDensity));
                this.phase += this.angleIncrease;
            }
        }
    }
    private void affectTargets() {
        List<Entity> targets = GeneralMethods.getEntitiesAroundPoint(this.location, hitbox);
        Iterator var2 = targets.iterator();

        while (var2.hasNext()) {
            Entity target = (Entity) var2.next();
            if( target instanceof Arrow || target instanceof FallingBlock) return;
            if (target.getUniqueId() != this.player.getUniqueId() ) {
                target.setVelocity(this.direction);
                if (!this.hurt.contains(target)) {
                    DamageHandler.damageEntity(target, damage, this);
                    this.hurt.add(target);
                }

                target.setVelocity(this.direction);
                this.remove();

            }

        }
    }
    public void remove() {
        super.remove();
        this.hurt.clear();
        this.bPlayer.addCooldown("PissSwirl", this.cooldown);
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
        return "PissSwirl";
    }

    public Location getLocation() {
        return this.location;
    }

    public void load() {
        ProjectKorra.plugin.getServer().getPluginManager().registerEvents(new PissListener(), ProjectKorra.plugin);

    }
    public String getInstructions() {
        return "<left-click>";
    }

    public String getDescription() {
        return "Sent a swirling gush of piss at your opponent.";
    }    public void stop() {
        HandlerList.unregisterAll(new PissListener());
    }

    public String getAuthor() {
        return "Sammycocobear";
    }

    public String getVersion() {
        return Pissaddon.getVersion();
    }

    @Override
    public boolean isEnabled() {
        String path = "ExtraAbilities.Sammycocobear.Tinkle.Enabled";
        if (Pissaddon.getPlugin().getConfig().contains(path)) {
            return Pissaddon.getPlugin().getConfig().getBoolean(path);
        }
        return false;
    }

}

