package me.scb.pissaddon.pissaddon.Abilities;

import com.projectkorra.projectkorra.GeneralMethods;
import com.projectkorra.projectkorra.ability.AddonAbility;
import com.projectkorra.projectkorra.ability.CoreAbility;
import com.projectkorra.projectkorra.util.DamageHandler;
import me.scb.pissaddon.pissaddon.PissAbility;
import me.scb.pissaddon.pissaddon.PissListener;
import me.scb.pissaddon.pissaddon.Pissaddon;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Entity;
import org.bukkit.entity.FallingBlock;
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
    private long time;
    private long duration;
    private long cooldown;
    private double hitbox;
    private double sourcerange;
    private double size;
    private double angleIncrease;
    private double particleCount;
    private double angle;
    private double kb; // knockback


    public PeeDrain(Player player) {
        super(player);

        if (this.bPlayer.isOnCooldown(this)) {
            return;
        }
        if (CoreAbility.hasAbility(player, PeeDrain.class)){
            return;
        }
        setfields();
        this.location = GeneralMethods.getTargetedLocation(player, sourcerange, new Material[0]);
        this.direction = player.getLocation().getDirection();
        this.direction.multiply(kb);
        this.bPlayer.addCooldown("PeeDrain", this.cooldown);
        this.start();
    }

    private void setfields() {
        cooldown = Pissaddon.getPlugin().getConfig().getLong("ExtraAbilities.Sammycocobear.PeeDrain.cooldown");
        duration = Pissaddon.getPlugin().getConfig().getLong("ExtraAbilities.Sammycocobear.PeeDrain.duration");
        hitbox = Pissaddon.getPlugin().getConfig().getDouble("ExtraAbilities.Sammycocobear.PeeDrain.hitbox");
        sourcerange = Pissaddon.getPlugin().getConfig().getDouble("ExtraAbilities.Sammycocobear.PeeDrain.sourcerange");
        size = Pissaddon.getPlugin().getConfig().getDouble("ExtraAbilities.Sammycocobear.PeeDrain.size");
        kb = Pissaddon.getPlugin().getConfig().getDouble("ExtraAbilities.Sammycocobear.PeeDrain.Knockback");
        particleCount = Pissaddon.getPlugin().getConfig().getInt("ExtraAbilities.Sammycocobear.PeeDrain.ParticleCount");
        angle = 0;
        angleIncrease = 360 / particleCount;
    }

    @Override
    public void progress() {
        this.time = System.currentTimeMillis();
        if (this.player.isDead() || !this.player.isOnline()) {
            this.remove();
            return;
        } else if (GeneralMethods.isRegionProtectedFromBuild(this, location)) {
            this.remove();
            return;
        }
        if (!this.bPlayer.canBendIgnoreBindsCooldowns(this)) {
            this.remove();
        } else if (this.location.getBlock().getType().isSolid()) {
            this.remove();
        } else {
            this.affectTargets();
            for (int i = 0; i < particleCount; ++i) {
                double x = 0;
                double y = Math.sin(angle);
                double z = 0;
                angle+= angleIncrease;
                Vector vector = new Vector(x, y, z);
                vector = vector.multiply(size);

                this.location.add(vector);
                GeneralMethods.displayColoredParticle("ffff00", this.location,1,.001,.001,.001);
                this.location.subtract(vector);
                if (ThreadLocalRandom.current().nextInt(8) == 0) {
                    this.location.getWorld().playSound(this.location, Sound.WEATHER_RAIN_ABOVE, 0.001F, 1.5F);
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
        List<Entity> targets = GeneralMethods.getEntitiesAroundPoint(this.location, hitbox);
        Iterator var2 = targets.iterator();

        while(var2.hasNext()) {
            Entity target = (Entity)var2.next();
            if( target instanceof Arrow || target instanceof FallingBlock) return;
            if (target.getUniqueId() != this.player.getUniqueId()) {
                target.setVelocity(direction);
                DamageHandler.damageEntity(target, 2.0D, this);
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
        return Pissaddon.getVersion();
    }

    @Override
    public String getDescription() {
        return "Send a fountain of piss gushing onto your opponent.";
    }
    public String getInstructions() {
        return "<left click> an area close to your opponent";
    }
    @Override
    public boolean isEnabled() {
        String path = "ExtraAbilities.Sammycocobear.PeeDrain.Enabled";
        if (Pissaddon.getPlugin().getConfig().contains(path)) {
            return Pissaddon.getPlugin().getConfig().getBoolean(path);
        }
        return false;
    }

}
