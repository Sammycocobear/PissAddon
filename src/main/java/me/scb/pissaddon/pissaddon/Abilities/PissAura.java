package me.scb.pissaddon.pissaddon.Abilities;

import com.projectkorra.projectkorra.GeneralMethods;
import com.projectkorra.projectkorra.ability.AddonAbility;
import com.projectkorra.projectkorra.ability.ComboAbility;
import com.projectkorra.projectkorra.ability.util.ComboManager.AbilityInformation;
import com.projectkorra.projectkorra.attribute.Attribute;
import com.projectkorra.projectkorra.util.ClickType;
import com.projectkorra.projectkorra.util.DamageHandler;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;

import me.scb.pissaddon.pissaddon.Config.Config;
import me.scb.pissaddon.pissaddon.PissAbility;
import me.scb.pissaddon.pissaddon.Pissaddon;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.permissions.Permission;
import org.bukkit.util.Vector;

public class PissAura extends PissAbility implements AddonAbility, ComboAbility {
    @Attribute("Cooldown")
    private long cooldown;
    private double damage;
    private double rotation;
    private double distancetraveled;
    private Set<Entity> hurt;
    private final double yaw;
    private final double pitch;
    private Permission perm;
    private Location location;
    private Location origin;
    private double distance;
    private Vector direction;
    private double cosX;
    private double sinX;
    private double cosY;
    private double sinY;
    private double phase;
    private double angleIncrease;
    private double radius;
    private double radiusIncrease;
    private double speed;
    private double maxRadius;
    private double sinWaveAmount;
    private int particleDensity;
    private int arrivalTime;

    public PissAura(Player player) {
        super(player);
        this.direction = player.getLocation().getDirection().normalize().multiply(0.8D);
        this.direction.multiply(0.8D);
        this.location = player.getEyeLocation();
        this.origin = player.getEyeLocation();
        this.rotation = 0.0D;
        this.bPlayer.addCooldown(this);
        this.distancetraveled = 0.0D;
        this.yaw = Math.toRadians((double)player.getLocation().getYaw());
        this.pitch = Math.toRadians((double)(player.getLocation().getPitch() - 90.0F));
        this.cosX = Math.cos(this.pitch);
        this.sinX = Math.sin(this.pitch);
        this.cosY = Math.cos(-this.yaw);
        this.sinY = Math.sin(-this.yaw);
        this.perm = new Permission("bending.ability.PissAura");
        this.hurt = new HashSet<>();
        if (!hasAbility(player, PissAura.class) && !this.bPlayer.isOnCooldown(this) && this.bPlayer.canBendIgnoreBinds(this)) {


            this.setFields();
            this.start();
        }

    }

    public void setFields() {
        this.location = player.getEyeLocation();
        this.origin = this.location.clone();
        this.direction = player.getLocation().getDirection();

        this.cosX = Math.cos(Math.toRadians((double)this.location.getPitch()));
        this.sinX = Math.sin(Math.toRadians((double)this.location.getPitch()));
        this.cosY = Math.cos(Math.toRadians((double)(-this.location.getYaw())));
        this.sinY = Math.sin(Math.toRadians((double)(-this.location.getYaw())));


        this.distance =  Pissaddon.getPlugin().getConfig().getDouble("ExtraAbilities.Sammycocobear.PissAura.distance");
        this.maxRadius =  Pissaddon.getPlugin().getConfig().getDouble("ExtraAbilities.Sammycocobear.PissAura.maxRadius");
        this.sinWaveAmount =  Pissaddon.getPlugin().getConfig().getDouble("ExtraAbilities.Sammycocobear.PissAura.sinWaveAmount");
        this.particleDensity =  Pissaddon.getPlugin().getConfig().getInt("ExtraAbilities.Sammycocobear.PissAura.ParticleDensity");
        this.cooldown =  Pissaddon.getPlugin().getConfig().getLong("ExtraAbilities.Sammycocobear.PissAura.cooldown");
        this.arrivalTime = Pissaddon.getPlugin().getConfig().getInt("ExtraAbilities.Sammycocobear.PissAura.arrivaltimeinticks");
        damage = Pissaddon.getPlugin().getConfig().getDouble("ExtraAbilities.Sammycocobear.PissAura.damage");

        this.phase = 0;
        this.angleIncrease = this.sinWaveAmount * 360 / this.arrivalTime;
        this.radius = 0;
        this.radiusIncrease = this.maxRadius / this.arrivalTime;
        this.speed = this.distance / this.arrivalTime;

    }



    @Override
    public void progress() {
        if (this.location.distanceSquared(this.origin) >= (double)(this.distance * this.distance)) {
            this.remove();
        } else if (this.location.getBlock().getType().isSolid()) {
            this.remove();
        } else {
            for(int i = 0; i < this.particleDensity; i++) {
                double angle = Math.toRadians(phase);
                double x = this.radius * Math.sin(-angle);
                double y = 0;
                double z = 0;
                Vector vector = new Vector(x, y, z);
                vector = this.rotateAroundAxisX(vector, this.cosX, this.sinX);
                vector = this.rotateAroundAxisY(vector, this.cosY, this.sinY);
                this.location.add(vector);
                this.affectTargets();
                GeneralMethods.displayColoredParticle("FFFF00", this.location);
                this.location.subtract(vector);

                this.location.add(this.direction.normalize().multiply(this.speed).multiply(1.0 / this.particleDensity));
                this.phase += this.angleIncrease / this.particleDensity;
                this.radius += this.radiusIncrease / this.particleDensity;
            }
            if (ThreadLocalRandom.current().nextInt(6) == 0) {
                this.location.getWorld().playSound(this.location, Sound.WEATHER_RAIN_ABOVE, 0.5F, 1.0F);
            }
        }
    }


    private void affectTargets() {
        List<Entity> targets = GeneralMethods.getEntitiesAroundPoint(this.location, 1);
        Iterator var2 = targets.iterator();

        while(var2.hasNext()) {
            Entity target = (Entity)var2.next();
            if (target.getUniqueId() != this.player.getUniqueId()) {
                target.setVelocity(this.direction);
                target.setFireTicks(1);
                if (!this.hurt.contains(target)) {
                    DamageHandler.damageEntity(target, this.damage, this);
                    this.hurt.add(target);
                }

                target.setVelocity(this.direction);
                target.setFireTicks(1);
                this.remove();
            }
        }

    }

    private Vector rotateAroundAxisX(Vector v, double cos, double sin) {
        double y = v.getY() * cos - v.getZ() * sin;
        double z = v.getY() * sin + v.getZ() * cos;
        return v.setY(y).setZ(z);
    }

    private Vector rotateAroundAxisY(Vector v, double cos, double sin) {
        double x = v.getX() * cos + v.getZ() * sin;
        double z = v.getX() * -sin + v.getZ() * cos;
        return v.setX(x).setZ(z);
    }


    public void remove() {
        super.remove();
        this.hurt.clear();
        this.bPlayer.addCooldown("PissAura", this.cooldown);
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
        return "PissAura";
    }

    public Location getLocation() {
        return this.location;
    }

    public void load() {

    }

    public void stop() {
    }

    public String getAuthor() {
        return "Sammycocobear";
    }

    public String getVersion() {
        return "1.0.0";
    }

    public String getInstructions() {
        return "PissStream(HoldShift)->PissSlide(LeftClick)->PissSlide(LeftClick)";
    }

    public String getDescription() {
        return "Send a speedy spike of zig-zagging pee towards your opponent.";
    }

    public Object createNewComboInstance(Player player) {
        return new PissAura(player);
    }

    public ArrayList getCombination() {
        return new ArrayList<>(Arrays.asList(new AbilityInformation("PissStream", ClickType.SHIFT_DOWN), new AbilityInformation("PissSlide", ClickType.LEFT_CLICK), new AbilityInformation("PissSlide", ClickType.LEFT_CLICK)));
    }
}
