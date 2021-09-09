package me.scb.pissaddon.pissaddon.Abilities;

import com.projectkorra.projectkorra.GeneralMethods;
import com.projectkorra.projectkorra.ability.AddonAbility;
import com.projectkorra.projectkorra.util.DamageHandler;
import me.scb.pissaddon.pissaddon.PissAbility;
import me.scb.pissaddon.pissaddon.PissListener;
import me.scb.pissaddon.pissaddon.Pissaddon;
import org.bukkit.Location;
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
        this.bPlayer.addCooldown("PissSplatter", this.cooldown);
        this.start();
    }

    private void setfields() {
        duration = Pissaddon.getPlugin().getConfig().getLong("ExtraAbilities.Sammycocobear.PissSplatter.duration");
        radius = Pissaddon.getPlugin().getConfig().getDouble("ExtraAbilities.Sammycocobear.PissSplatter.radius");
        cooldown = Pissaddon.getPlugin().getConfig().getLong("ExtraAbilities.Sammycocobear.PissSplatter.cooldown");
        hitbox = Pissaddon.getPlugin().getConfig().getDouble("ExtraAbilities.Sammycocobear.PissSplatter.hitbox");
        damage = Pissaddon.getPlugin().getConfig().getDouble("ExtraAbilities.Sammycocobear.PissSplatter.damage");

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

            for(int i = 0; i < 360; ++i) {
                double x = Math.sin(i);
                double y = Math.cos(i);
                double z = 0;
                Vector vector = new Vector(x, z, y);
                vector = vector.multiply(radius);
                this.location.add(vector);
                this.affectTargets(location);
                GeneralMethods.displayColoredParticle("ffff00", this.location);
                this.location.subtract(vector);
            }

        }
    }


    private void affectTargets(Location location) {
        List<Entity> targets = GeneralMethods.getEntitiesAroundPoint(this.location, hitbox);
        Iterator var2 = targets.iterator();

        while(var2.hasNext()) {
            Entity target = (Entity)var2.next();
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
        return "1.0.0";
    }

    public String getDescription() {
        return "Use your own piss to hold your opponent down in place.";
    }
    public String getInstructions() {
        return "<tap-shift>";
    }
    @Override
    public boolean isEnabled() {
        return Pissaddon.getPlugin().getConfig().getBoolean("ExtraAbilities.Sammycocobear.PissSplatter.Enabled");
    }
}
