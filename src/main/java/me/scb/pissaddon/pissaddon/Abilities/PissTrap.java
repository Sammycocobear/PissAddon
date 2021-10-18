package me.scb.pissaddon.pissaddon.Abilities;

import com.projectkorra.projectkorra.Element;
import com.projectkorra.projectkorra.GeneralMethods;
import com.projectkorra.projectkorra.ProjectKorra;
import com.projectkorra.projectkorra.ability.AddonAbility;
import com.projectkorra.projectkorra.airbending.Suffocate;
import com.projectkorra.projectkorra.configuration.ConfigManager;
import com.projectkorra.projectkorra.util.DamageHandler;
import com.projectkorra.projectkorra.util.MovementHandler;
import me.scb.pissaddon.pissaddon.PissAbility;
import me.scb.pissaddon.pissaddon.PissSubElement;
import me.scb.pissaddon.pissaddon.Pissaddon;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.entity.*;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.permissions.Permission;
import org.bukkit.permissions.PermissionDefault;
import org.bukkit.util.Vector;

import java.util.concurrent.ThreadLocalRandom;

public class PissTrap extends PissAbility implements AddonAbility {
    private long cooldown;
    private long duration;
    private double damage;
    private double radius;
    private double sourcerange;
    private double hitbox;
    private long paraduration;

    private Location location;
    private long time;
    private boolean water;
    private double speed;
    private Vector direction;
    private boolean shootboolean;
    private Location origin;
    private double range;
    private int particleSize;
    private boolean Paralyze;
    private int particleCount;

    public PissTrap(Player player) {
        super(player);
        if (!hasAbility(player, PissSwirl.class)) {
            if (!this.bPlayer.isOnCooldown(this)) {
                if (this.bPlayer.canBend(this)) {
                    setFields();
                    this.location = GeneralMethods.getTargetedLocation(player, sourcerange);
                    this.origin = GeneralMethods.getTargetedLocation(player, sourcerange);
                    start();
                }
            }
        }
    }

    private void setFields() {
        this.duration = Pissaddon.getPlugin().getConfig().getLong("ExtraAbilities.Sammycocobear.PissTrap.duration");
        this.damage = Pissaddon.getPlugin().getConfig().getDouble("ExtraAbilities.Sammycocobear.PissTrap.damage");
        this.radius = Pissaddon.getPlugin().getConfig().getDouble("ExtraAbilities.Sammycocobear.PissTrap.radius");
        this.cooldown = Pissaddon.getPlugin().getConfig().getLong("ExtraAbilities.Sammycocobear.PissTrap.Cooldown");
        this.hitbox = Pissaddon.getPlugin().getConfig().getDouble("ExtraAbilities.Sammycocobear.PissTrap.hitbox");
        this.paraduration = Pissaddon.getPlugin().getConfig().getLong("ExtraAbilities.Sammycocobear.PissTrap.paraduration");
        this.water =Pissaddon.getPlugin().getConfig().getBoolean("ExtraAbilities.Sammycocobear.PissTrap.water");
        this.sourcerange =Pissaddon.getPlugin().getConfig().getDouble("ExtraAbilities.Sammycocobear.PissTrap.sourcerange");
        this.speed = Pissaddon.getPlugin().getConfig().getDouble("ExtraAbilities.Sammycocobear.PissTrap.speed");
        this.range = Pissaddon.getPlugin().getConfig().getDouble("ExtraAbilities.Sammycocobear.PissTrap.range");
        particleSize = Pissaddon.getPlugin().getConfig().getInt("ExtraAbilities.Sammycocobear.PissTrap.ParticleSize");
        Paralyze = Pissaddon.getPlugin().getConfig().getBoolean("ExtraAbilities.Sammycocobear.PissTrap.Paralyze");
        this.particleCount = Pissaddon.getPlugin().getConfig().getInt("ExtraAbilities.Sammycocobear.PissTrap.particleCount");

    }

    @Override
    public void progress() {
        if(!this.bPlayer.canBendIgnoreBindsCooldowns(this)){
            remove();
            return;
        }
        if (this.player.isDead() || !this.player.isOnline()) {
            this.remove();
            return;
        } else if (GeneralMethods.isRegionProtectedFromBuild(this, location)) {
            this.remove();
            return;
        }
        direction = player.getLocation().getDirection().multiply(speed);
        direction.setY(0);
        this.time = System.currentTimeMillis();
        if (this.time - this.getStartTime() > this.duration) {
            this.remove();
            return;
        }
        if (this.location.distanceSquared(this.origin) >= (this.range * this.range)) {
            this.remove();
            return;
        }
        if(GeneralMethods.isSolid(location.getBlock())){
            remove();
            return;
        }
        if(shootboolean && player.isSneaking()) location.add(direction);

        for (int i = 0; i < particleCount; i++){
            double x = Math.sin(i);
            double y = Math.cos(i);
            double z = 0;
            Vector vector = new Vector(x, z, y);
            vector.multiply(radius);
            location.add(vector);
            location.getWorld().spawnParticle(Particle.REDSTONE,location,2,0,0,0,0,new Particle.DustOptions(Color.fromRGB(255, 255, 0), particleSize));
            this.location.subtract(vector);
        }

            for (Entity entity : GeneralMethods.getEntitiesAroundPoint(this.location, this.hitbox)) {
                if (entity.getEntityId() == player.getEntityId()) continue;
                if (entity instanceof LivingEntity) {
                    LivingEntity lEntity = (LivingEntity) entity;
                    DamageHandler.damageEntity(lEntity, this.damage, this);
                    if (Paralyze) {
                        MovementHandler mh = new MovementHandler(lEntity, this);
                        mh.stopWithDuration(this.paraduration / 1000L * 20L, PissSubElement.PISS.getColor() + "* Trapped *");
                        lEntity.getWorld().playSound(entity.getLocation(), Sound.WEATHER_RAIN_ABOVE, 2F, 0F);
                    }
                    if (lEntity instanceof Creature) {
                        ((Creature) lEntity).setTarget(null);
                    }
                    if (lEntity instanceof Player) {
                        Player pEntity = (Player) lEntity;
                        if (Suffocate.isChannelingSphere(pEntity)) Suffocate.remove(pEntity);
                    }
                    this.remove();
                    return;

                }
            }



    }





    public void shoot(){
        shootboolean = true;
    }

    public static void bob(Player player) {
        PissTrap abil = getAbility(player, PissTrap.class);
        if (abil != null) abil.shoot();
    }


    @Override
    public void remove() {
        super.remove();
        bPlayer.addCooldown(this, cooldown);
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
        return "PissTrap";
    }

    @Override
    public Location getLocation() {
        return this.location;
    }

    @Override
    public void load() {

    }

    @Override
    public void stop() {
    }

    @Override
    public String getInstructions() {
        return "LeftClick the ground";
    }


    @Override
    public String getDescription() {
        return "Place down an trap made of piss. Hold shift to launch you trap, anyone stuck in the trap will be paralyzed for a set duration";
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
    public boolean isEnabled() {
        String path = "ExtraAbilities.Sammycocobear.PissTrap.Enabled";
        if (Pissaddon.getPlugin().getConfig().contains(path)) {
            return Pissaddon.getPlugin().getConfig().getBoolean(path);
        }
        return false;
    }

}
