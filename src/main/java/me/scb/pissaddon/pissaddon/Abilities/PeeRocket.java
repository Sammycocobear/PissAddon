package me.scb.pissaddon.pissaddon.Abilities;

import com.projectkorra.projectkorra.GeneralMethods;
import com.projectkorra.projectkorra.ability.AddonAbility;
import me.scb.pissaddon.pissaddon.PissAbility;
import me.scb.pissaddon.pissaddon.PissListener;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class PeeRocket extends PissAbility implements AddonAbility {
    private Location location;
    private long cooldown;
    private PissListener listener;
    private long time;
    private long duration;



    public PeeRocket(Player player) {
        super(player);
        this.location = player.getLocation().clone().add(0.0D, 0.47673141357534D, 0.0D);
        this.cooldown = 3000;
        this.duration = 2000;
        if (this.bPlayer.isOnCooldown(this)) {
            return;
        }
        this.bPlayer.addCooldown(this, this.cooldown);
        start();
    }

    @Override
    public void progress() {
        if(!this.bPlayer.canBendIgnoreBindsCooldowns(this)){
            remove();
            return;
        }
        this.player.setFallDistance(0.0F);
        this.time = System.currentTimeMillis();
        Location loc = player.getLocation().clone().add(0.0D, 0.47673141357534D, 0.0D);
        double t = 0;
        t = t + 0.1 * Math.PI;
        for (double theta = 0; theta <= 2 * Math.PI; theta = theta + Math.PI / 32) {
            double x = t * Math.cos(theta);
            double y = -4*Math.exp(-0.1 * t) * Math.sin(t) + 1.5;
            double z = t * Math.sin(theta);
            player.addPotionEffect(new PotionEffect(PotionEffectType.LEVITATION, 10, 10));
            loc.add(x, y, z);
            GeneralMethods.displayColoredParticle("ffff00", loc);
            loc.subtract(x, y, z);
            player.setFallDistance(-10000);
        }
        if (t>20){
            this.remove();
        }
        if (this.time - this.getStartTime() > this.duration) {
            this.remove();
            return;
        }

    }
    public void remove() {
        super.remove();
        this.bPlayer.addCooldown(this);
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
        return "PeeRocket";
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
