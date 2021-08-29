package me.scb.pissaddon.pissaddon.Abilities;

import com.projectkorra.projectkorra.GeneralMethods;
import com.projectkorra.projectkorra.ability.AddonAbility;
import me.scb.pissaddon.pissaddon.PissAbility;
import me.scb.pissaddon.pissaddon.PissListener;
import me.scb.pissaddon.pissaddon.Pissaddon;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
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

    private double speedduration;

    public WaterSports(Player player) {
        super(player);
        period = 1;
        this.location = player.getEyeLocation();
        this.direction = player.getLocation().getDirection();
        this.direction.multiply(0.8D);
        iterations = 600;
        this.hurt = new HashSet();
        setfields();
        start();
    }

    private void setfields() {
        speedduration = Pissaddon.getPlugin().getConfig().getDouble("Extra");

    }

    @Override
    public void progress() {
        if(!this.bPlayer.canBend(this)){
            remove();
            return;
        }

    }
    private void particle() {
        Location location = player.getLocation();
        for (int i = 0; i < particles; i++) {
            Vector v = getRandomCircleVector().multiply(random.nextDouble() * 0.6d);
            v.setY(random.nextFloat() * 1.8);
            location.add(v);
            GeneralMethods.displayColoredParticle("ffffff", location);
            location.subtract(v);
        }
        this.player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 10, 10));
    }

    public static Vector getRandomCircleVector() {
        double rnd, x, z;
        rnd = random.nextDouble() * 2 * Math.PI;
        x = Math.cos(rnd);
        z = Math.sin(rnd);

        return new Vector(x, 0, z);
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
        return 0;
    }

    @Override
    public String getName() {
        return null;
    }

    @Override
    public Location getLocation() {
        return null;
    }

    @Override
    public void load() {

    }

    @Override
    public void stop() {

    }

    @Override
    public String getAuthor() {
        return null;
    }

    @Override
    public String getVersion() {
        return null;
    }
}
