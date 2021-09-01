package me.scb.pissaddon.pissaddon.Abilities;

import com.projectkorra.projectkorra.GeneralMethods;
import com.projectkorra.projectkorra.ProjectKorra;
import com.projectkorra.projectkorra.ability.AddonAbility;
import com.projectkorra.projectkorra.ability.CoreAbility;
import me.scb.pissaddon.pissaddon.PissAbility;
import me.scb.pissaddon.pissaddon.PissListener;
import me.scb.pissaddon.pissaddon.Pissaddon;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;

public class WaterSports extends PissAbility implements AddonAbility {
    public int period;

    public int iterations;

    public static final Random random = ThreadLocalRandom.current();

    private Location location;

    private Set<Entity> hurt;

    private double damage;

    private Vector direction;

    public int particles = 10;

    private PissListener listener;

    private long time;

    private long duration;

    private int speedduration;

    private int speedamp;

    private int resistanceamp;

    private int resistanceduration;

    private long cooldown;



    public WaterSports(Player player) {
        super(player);
        period = 1;
        this.location = player.getEyeLocation();
        this.direction = player.getLocation().getDirection();
        this.direction.multiply(0.8D);
        iterations = 600;
        this.hurt = new HashSet();
        if (CoreAbility.hasAbility(player, WaterSports.class)){
            return;
        }
        if(bPlayer.isOnCooldown(this)){
            return;
        }
        setfields();
        start();
    }

    private void setfields() {
        speedduration = Pissaddon.getPlugin().getConfig().getInt("ExtraAbilities.Sammycocobear.WaterSports.speedduration");
        duration = Pissaddon.getPlugin().getConfig().getLong("ExtraAbilities.Sammycocobear.WaterSports.duration");
        speedamp = Pissaddon.getPlugin().getConfig().getInt("ExtraAbilities.Sammycocobear.WaterSports.speedamp");
        resistanceamp = Pissaddon.getPlugin().getConfig().getInt("ExtraAbilities.Sammycocobear.WaterSports.resistanceamp");
        resistanceduration = Pissaddon.getPlugin().getConfig().getInt("ExtraAbilities.Sammycocobear.WaterSports.resistanceduration");
        cooldown = Pissaddon.getPlugin().getConfig().getLong("ExtraAbilities.Sammycocobear.WaterSports.cooldown");

    }

    @Override
    public void progress() {
        this.time = System.currentTimeMillis();
        if (this.time - this.getStartTime() > this.duration) {
            this.remove();
            return;
        }
        if(!this.bPlayer.canBendIgnoreBindsCooldowns(this)){
            remove();
            return;
        }

        particle();

    }
    private void particle() {
        Location location = player.getLocation();
        for (int i = 0; i < particles; i++) {
            Vector v = getRandomCircleVector().multiply(random.nextDouble() * 0.6d);
            v.setY(random.nextFloat() * 1.8);
            location.add(v);
            GeneralMethods.displayColoredParticle("ffff00", location);
            location.subtract(v);
        }
        this.player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, speedduration, speedamp));
        this.player.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, resistanceduration, resistanceamp));
    }

    public static Vector getRandomCircleVector() {
        double rnd, x, z;
        rnd = random.nextDouble() * 2 * Math.PI;
        x = Math.cos(rnd);
        z = Math.sin(rnd);

        return new Vector(x, 0, z);
    }
    public void remove(){
        super.remove();
        this.bPlayer.addCooldown(this, cooldown);
        this.player.removePotionEffect(PotionEffectType.SPEED);
        this.player.removePotionEffect(PotionEffectType.DAMAGE_RESISTANCE);

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
        return cooldown;
    }

    @Override
    public String getName() {
        return "WaterSports";
    }

    @Override
    public Location getLocation() {
        return location;
    }

    @Override
    public void load() {
        ProjectKorra.plugin.getServer().getPluginManager().registerEvents(listener, ProjectKorra.plugin);


    }

    @Override
    public void stop() {
        HandlerList.unregisterAll(listener);

    }
    public String getInstructions() {
        return "<left-click>";
    }

    public String getDescription() {
        return "Cover yourself in piss to give yourself protection & super speed.";
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
