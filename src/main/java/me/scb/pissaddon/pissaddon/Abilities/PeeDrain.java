package me.scb.pissaddon.pissaddon.Abilities;

import com.projectkorra.projectkorra.GeneralMethods;
import com.projectkorra.projectkorra.ability.AddonAbility;
import com.projectkorra.projectkorra.util.DamageHandler;
import me.scb.pissaddon.pissaddon.PissAbility;
import me.scb.pissaddon.pissaddon.PissListener;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import org.bukkit.event.HandlerList;
import org.bukkit.util.Vector;

import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

//PeeDrain: A spout  line of yellow particles appears from the ground damaging whoever walks into it.
//PeeDrain - Hold shift, aim and then release.
public class PeeDrain extends PissAbility implements AddonAbility {
    private PissListener listener;
    private Location location;
    private Vector direction;
    private double distancetraveled;
    private long time;
    private long duration;
    private long cooldown;


    public PeeDrain(Player player) {
        super(player);
        this.location = GeneralMethods.getTargetedLocation(player, 25.0D, new Material[0]);
        this.direction = player.getLocation().getDirection();
        this.direction.multiply(0.8D);
        this.bPlayer.addCooldown(this);
        this.distancetraveled = 0.0D;
        this.duration = 5000L;
        this.cooldown = 2000;
        if (this.bPlayer.isOnCooldown(this)) {
            return;
        }
        this.bPlayer.addCooldown("PeeDrain", this.cooldown);
        this.start();
    }

    @Override
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
            for (int i = 0; i < 90; ++i) {
                double x = 0;
                double y = Math.sin(i);
                double z = 0;
                Vector vector = new Vector(x, y, z);
                vector = vector.multiply(1.5D);
                this.location.add(vector);
                GeneralMethods.displayColoredParticle("ffff00", this.location,1,.001,.001,.001);
                this.location.subtract(vector);
                if (ThreadLocalRandom.current().nextInt(8) == 0) {
                    this.location.getWorld().playSound(this.location, Sound.WEATHER_RAIN_ABOVE, 0.1F, 1.5F);
                }
                this.time = System.currentTimeMillis();
                if (this.time - this.getStartTime() > this.duration) {
                    this.remove();
                    return;
                }
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
        return "PeeDrain";
    }

    @Override
    public Location getLocation() {
        return this.location;
    }

    @Override
    public void load() {
        this.listener = new PissListener();
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
