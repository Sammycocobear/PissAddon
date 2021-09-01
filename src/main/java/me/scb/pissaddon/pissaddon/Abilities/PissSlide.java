package me.scb.pissaddon.pissaddon.Abilities;

import com.projectkorra.projectkorra.GeneralMethods;
import com.projectkorra.projectkorra.ProjectKorra;
import com.projectkorra.projectkorra.ability.AddonAbility;

import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import me.scb.pissaddon.pissaddon.FallDamageHelper;
import me.scb.pissaddon.pissaddon.PissAbility;
import me.scb.pissaddon.pissaddon.PissListener;
import me.scb.pissaddon.pissaddon.Pissaddon;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.bukkit.permissions.Permission;
import org.bukkit.util.Vector;

public class PissSlide extends PissAbility implements AddonAbility {
    private Permission perm;
    private PissListener listener;
    private Vector direction;
    private double distancetraveled;
    private long cooldown;
    private int distance;
    private Location location;
    private double speed;


    public PissSlide(Player player) {
        super(player);
        this.bPlayer.addCooldown(this);
        this.distancetraveled = 0.0D;
        setfields();
        this.start();
    }
    private void setfields() {
        this.cooldown = Pissaddon.getPlugin().getConfig().getLong("ExtraAbilities.Sammycocobear.PissSlide.cooldown");
        this.distance = Pissaddon.getPlugin().getConfig().getInt("ExtraAbilities.Sammycocobear.PissSlide.distance");
        speed = Pissaddon.getPlugin().getConfig().getDouble("ExtraAbilities.Sammycocobear.PissSlide.speed");
    }

    public void progress() {
        this.location = player.getLocation().clone().add(0.0D, 0.47673141357534D, 0.0D);
        this.direction = player.getEyeLocation().getDirection().normalize().multiply(speed);
        if (!this.bPlayer.canBendIgnoreBindsCooldowns(this)) {
            this.remove();
        } else if (this.location.getBlock().getType().isSolid()) {
            this.remove();
        } else if (this.distancetraveled > distance) {
            this.remove();
        }else {
            this.affectTargets();
            this.player.setFallDistance(0.1F);
            this.player.setGliding(true);
            GeneralMethods.displayColoredParticle("FFA500", this.location, 8, 0.1D, 0.1D, 0.1D);
            if (ThreadLocalRandom.current().nextInt(6) == 0) {
                this.location.getWorld().playSound(this.location, Sound.WEATHER_RAIN, 0.1F, 1.0F);
            }
            this.location.add(this.direction);
            this.distancetraveled += this.direction.length();
        }
    }

    private void affectTargets() {
        this.location = player.getLocation().clone().add(0.0D, 0.47673141357534D, 0.0D);
        this.direction = player.getEyeLocation().getDirection().normalize().multiply(speed   );
        List<Entity> targets = GeneralMethods.getEntitiesAroundPoint(this.location, 2);
        Iterator var2 = targets.iterator();

        while(var2.hasNext()) {
            Entity target = (Entity) var2.next();
            if (target.getUniqueId() == this.player.getUniqueId()) {
                target.setVelocity(this.direction);
            }
        }

    }
    public void remove(){
        super.remove();
        this.bPlayer.addCooldown(this,cooldown);
        FallDamageHelper.addFallDamageCap(player, 0 );

    }


    public boolean isSneakAbility() {
        return true;
    }

    public boolean isHarmlessAbility() {
        return false;
    }

    public long getCooldown() {
        return this.cooldown;
    }

    public String getName() {
        return "PissSlide";
    }

    public Location getLocation() {
        this.location = player.getLocation().clone().add(0.0D, 0.47673141357534D, 0.0D);
        return this.location;
    }

    public void load() {
        Pissaddon.plugin.getServer().getPluginManager().registerEvents(this.listener, ProjectKorra.plugin);
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
        return "Use your piss to zoom across the floor to get to the toilet in time.";
    }

    public String getInstructions() {
        return "<tap-shift>";
    }
}
