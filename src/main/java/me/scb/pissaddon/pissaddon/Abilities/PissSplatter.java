package me.scb.pissaddon.pissaddon.Abilities;

import com.projectkorra.projectkorra.GeneralMethods;
import com.projectkorra.projectkorra.ability.AddonAbility;
import com.projectkorra.projectkorra.util.DamageHandler;
import me.scb.pissaddon.pissaddon.PissAbility;
import me.scb.pissaddon.pissaddon.PissListener;
import me.scb.pissaddon.pissaddon.Pissaddon;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.bukkit.permissions.Permission;
import org.bukkit.util.Vector;

import java.util.Iterator;
import java.util.List;

//: Spreads on the ground a circle of yellow particles damaging whoever walks into it. Lasts for 4 seconds
//PissSplatter - Aim and hold shift
public class PissSplatter extends PissAbility implements AddonAbility {
    private long cooldown;
    private Permission perm;
    private PissListener listener;
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
    private double radius;
    private double hitbox;
    private double damage;
    private double angle;
    private double angleIncrease;
    private double particleCount;
    private int particleSize;

    public PissSplatter(Player player) {
        super(player);
        this.location = player.getLocation().clone().add(0.0D, 0.47673141357534D, 0.0D);
        this.direction = player.getLocation().getDirection().normalize().multiply(0.8D);
        this.distancetraveled = 0.0D;
        this.yaw = Math.toRadians((double)player.getLocation().getYaw());
        this.pitch = Math.toRadians((double)(player.getLocation().getPitch() - 90.0F));
        this.cosX = Math.cos(this.pitch);
        this.sinX = Math.sin(this.pitch);
        this.cosY = Math.cos(-this.yaw);
        this.sinY = Math.sin(-this.yaw);
        setfields();
        this.bPlayer.addCooldown(this, this.cooldown);
        this.start();
    }

    private void setfields() {
        duration = Pissaddon.getPlugin().getConfig().getLong("ExtraAbilities.Sammycocobear.PissSplatter.duration");
        radius = Pissaddon.getPlugin().getConfig().getDouble("ExtraAbilities.Sammycocobear.PissSplatter.radius");
        cooldown = Pissaddon.getPlugin().getConfig().getLong("ExtraAbilities.Sammycocobear.PissSplatter.cooldown");
        hitbox = Pissaddon.getPlugin().getConfig().getDouble("ExtraAbilities.Sammycocobear.PissSplatter.hitbox");
        damage = Pissaddon.getPlugin().getConfig().getDouble("ExtraAbilities.Sammycocobear.PissSplatter.damage");
        particleCount = Pissaddon.getPlugin().getConfig().getInt("ExtraAbilities.Sammycocobear.PissSplatter.particleCount");
        particleSize = Pissaddon.getPlugin().getConfig().getInt("ExtraAbilities.Sammycocobear.PissSplatter.ParticleSize");
        angle = 0;
        angleIncrease = 360 / particleCount;
    }

    public void progress() {
        this.time = System.currentTimeMillis();
        if (this.player.isDead() || !this.player.isOnline()) {
            this.remove();
            return;
        } else if (GeneralMethods.isRegionProtectedFromBuild(this, location)) {
            this.remove();
            return;
        }
        if (this.time - this.getStartTime() > this.duration) {
            this.remove();
        } else if (!this.bPlayer.canBendIgnoreBindsCooldowns(this)) {
            this.remove();
        } else if (GeneralMethods.isSolid(this.location.getBlock())) {
            this.remove();
        } else {

            for (int i = 0; i < 1; ++i) {
                double x = Math.sin(angle);
                double y = 0;
                double z = Math.cos(angle);
                angle += angleIncrease;
                Vector vector = new Vector(x,y,z);
                vector = vector.multiply(radius);
                this.location.add(vector);
                this.affectTargets(location);
                location.getWorld().spawnParticle(Particle.REDSTONE,location,2,0,0,0,0,new Particle.DustOptions(Color.fromRGB(255, 255, 0), 3) );
                this.location.subtract(vector);
            }
        }
    }


    private void affectTargets(Location location) {
        List<Entity> targets = GeneralMethods.getEntitiesAroundPoint(this.location, hitbox);
        Iterator var2 = targets.iterator();

        while(var2.hasNext()) {
            Entity target = (Entity)var2.next();
            if(target instanceof Arrow) return;
            if (target.getUniqueId() != this.player.getUniqueId()) {
                int knockbackDistance = 0;
                target.setVelocity(GeneralMethods.getDirection(target.getLocation(), this.location).multiply(knockbackDistance));
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
        return this.cooldown;
    }

    public String getName() {
        return "PissSplatter";
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
        return "Use your own piss to hold your opponent down in place.";
    }
    public String getInstructions() {
        return "<tap-shift>";
    }
    @Override
    public boolean isEnabled() {
        String path = "ExtraAbilities.Sammycocobear.PissSplatter.Enabled";
        if (Pissaddon.getPlugin().getConfig().contains(path)) {
            return Pissaddon.getPlugin().getConfig().getBoolean(path);
        }
        return false;
    }

}
