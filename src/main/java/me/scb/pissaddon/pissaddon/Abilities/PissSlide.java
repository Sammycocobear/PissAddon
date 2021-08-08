package me.scb.pissaddon.pissaddon.Abilities;

import com.projectkorra.projectkorra.GeneralMethods;
import com.projectkorra.projectkorra.ProjectKorra;
import com.projectkorra.projectkorra.ability.AddonAbility;
import com.projectkorra.projectkorra.configuration.ConfigManager;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import me.scb.pissaddon.pissaddon.PissAbility;
import me.scb.pissaddon.pissaddon.PissStreamListener;
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
    private PissStreamListener listener;

    private Location location;
    private Vector direction;
    private double distancetraveled;
    private long cooldown;
    private int distance;


    public PissSlide(Player player) {
        super(player);
        this.location = player.getLocation().clone().add(0.0D, 0.47673141357534D, 0.0D);
        this.direction = player.getLocation().getDirection();
        this.direction.multiply(0.8D);
        this.bPlayer.addCooldown(this);
        this.distancetraveled = 0.0D;
        setfields();
        this.bPlayer.addCooldown("PissSlide", this.cooldown);
        this.start();
    }
    private void setfields() {
        this.cooldown = ConfigManager.getConfig().getLong("ExtraAbilities.Sammycocobear.PissSlide.cooldown");
        this.distance = ConfigManager.getConfig().getInt("ExtraAbilities.Sammycocobear.PissSlide.distance");
    }

    public void progress() {
        if (!this.bPlayer.canBendIgnoreBindsCooldowns(this)) {
            this.remove();
        } else if (this.location.getBlock().getType().isSolid()) {
            this.remove();
        } else if (this.distancetraveled > distance) {
            this.remove();
        }else {
            this.affectTargets();
            this.player.setFallDistance(0.0F);
            this.player.setGliding(true);
            GeneralMethods.displayColoredParticle("FFA500", this.location, 8, 0.1D, 0.1D, 0.1D);
            if (ThreadLocalRandom.current().nextInt(6) == 0) {
            }

            this.location.getWorld().playSound(this.location, Sound.WEATHER_RAIN, 0.1F, 1.0F);
            this.location.add(this.direction);
            this.distancetraveled += this.direction.length();
        }
    }

    private void affectTargets() {
        List<Entity> targets = GeneralMethods.getEntitiesAroundPoint(this.location, 2);
        Iterator var2 = targets.iterator();

        while(var2.hasNext()) {
            Entity target = (Entity)var2.next();
            target.setVelocity(this.direction);
        }

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
        return "Powerful PissStream coming out of your Vagina to kill everyone.";
    }

    public String getInstructions() {
        return "left click to shoot piss from your vagina up your arm";
    }
}
