package me.scb.pissaddon.pissaddon.Abilities;

import com.projectkorra.projectkorra.GeneralMethods;
import com.projectkorra.projectkorra.ProjectKorra;
import com.projectkorra.projectkorra.ability.AddonAbility;
import com.projectkorra.projectkorra.util.DamageHandler;
import me.scb.pissaddon.pissaddon.PissAbility;
import me.scb.pissaddon.pissaddon.PissListener;
import me.scb.pissaddon.pissaddon.Pissaddon;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.bukkit.util.Vector;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;


public class Tinkle extends PissAbility implements AddonAbility {
    protected final List<Float> rndF;
    protected final List<Double> rndAngle;
    private Location location;
    public static final Random random = ThreadLocalRandom.current();
    private PissListener listener;
    static public final float PI = 3.1415927f;
    static public final float degreesToRadians = PI / 180;
    protected int step = 0;
    public float length = 4;
    public int stepsPerIteration = 2;
    public int particles = 30;
    public int arcs = 20;
    public float pitch = .1f;
    public int iterations;
    private long time;
    public int period;
    private long cooldown;
    private long duration;
    private double hitbox;
    private Set<Entity> hurt;
    private double damage;

    private double size;
    private Vector direction;

    public Tinkle(Player player) {
        super(player);
        period = 2;
        iterations = 200;
        rndF = new ArrayList<>(arcs);
        rndAngle = new ArrayList<>(arcs);
        this.hurt = new HashSet();
        if (this.bPlayer.isOnCooldown(this)) {
            return;
        }
        setfields();

        start();
    }

    private void setfields() {
        duration = Pissaddon.getPlugin().getConfig().getLong("ExtraAbilities.Sammycocobear.Tinkle.duration");
        cooldown= Pissaddon.getPlugin().getConfig().getLong("ExtraAbilities.Sammycocobear.Tinkle.cooldown");
        size = Pissaddon.getPlugin().getConfig().getDouble("ExtraAbilities.Sammycocobear.Tinkle.size");
        hitbox =  Pissaddon.getPlugin().getConfig().getDouble("ExtraAbilities.Sammycocobear.Tinkle.hitbox");
        damage = Pissaddon.getPlugin().getConfig().getDouble("ExtraAbilities.Sammycocobear.Tinkle.damage");

    }

    public static double getRandomAngle() {
        return random.nextDouble() * 2 * Math.PI;
    }

    @Override
    public void progress() {

        time = System.currentTimeMillis();
        if (this.time - this.getStartTime() > this.duration) {
            this.remove();
            return;
        }
        if(!this.player.isSneaking()){
            remove();
            return;
        }

        for (int j = 0; j < stepsPerIteration; j++) {
            if (step % particles == 0) {
                rndF.clear();
                rndAngle.clear();
            }
            while (rndF.size() < arcs) {
                rndF.add(random.nextFloat());
            }
            while (rndAngle.size() < arcs) {
                rndAngle.add(getRandomAngle());
            }

            this.location = player.getLocation().clone().add(0.0D, 0.47673141357534D, 0.0D);
            for (int i = 0; i < this.arcs; ++i) {
                float pitch = rndF.get(i) * 2 * this.pitch - this.pitch;
                float x = (step % particles) * length / particles;
                float y = (float) (pitch * Math.pow(x, 2));
                Vector v = new Vector(x, y, 0);
                v.multiply(size);
                rotateAroundAxisX(v, rndAngle.get(i));
                rotateAroundAxisZ(v, -location.getPitch() * degreesToRadians);
                rotateAroundAxisY(v, -(location.getYaw() + 90) * degreesToRadians);

                location.add(v);
                this.affectTargets(location);
                GeneralMethods.displayColoredParticle("ffff00",location);
                location.subtract(v);
            }

            ++this.step;
        }

    }

    private void affectTargets( Location location ) {
        this.direction = player.getLocation().getDirection().normalize().multiply(0.8D);


        List<Entity> targets = GeneralMethods.getEntitiesAroundPoint(this.location, hitbox);
        Iterator var2 = targets.iterator();

        while (var2.hasNext()) {
            Entity target = (Entity) var2.next();
            if (target.getUniqueId() != this.player.getUniqueId()) {

                target.setVelocity(this.direction);
                if (!this.hurt.contains(target)) {
                    DamageHandler.damageEntity(target, damage, this);
                    this.hurt.add(target);
                }

                target.setVelocity(this.direction);
                this.remove();
            }
        }
    }

    public static final Vector rotateAroundAxisX(Vector v, double angle) {
        double y, z, cos, sin;
        cos = Math.cos(angle);
        sin = Math.sin(angle);
        y = v.getY() * cos - v.getZ() * sin;
        z = v.getY() * sin + v.getZ() * cos;
        return v.setY(y).setZ(z);
    }

    public static final Vector rotateAroundAxisY(Vector v, double angle) {
        double x, z, cos, sin;
        cos = Math.cos(angle);
        sin = Math.sin(angle);
        x = v.getX() * cos + v.getZ() * sin;
        z = v.getX() * -sin + v.getZ() * cos;
        return v.setX(x).setZ(z);
    }

    public static final Vector rotateAroundAxisZ(Vector v, double angle) {
        double x, y, cos, sin;
        cos = Math.cos(angle);
        sin = Math.sin(angle);
        x = v.getX() * cos - v.getY() * sin;
        y = v.getX() * sin + v.getY() * cos;
        return v.setX(x).setY(y);
    }
    public void remove(){
        super.remove();
        this.bPlayer.addCooldown(this, cooldown);
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
        return "Tinkle";
    }

    @Override
    public Location getLocation() {
        return location;
    }

    @Override
    public void load() {

        ProjectKorra.plugin.getServer().getPluginManager().registerEvents(listener,  ProjectKorra.plugin);

    }

    @Override
    public void stop() {
        HandlerList.unregisterAll(listener);

    }
    public String getInstructions() {
        return "<hold-shift>";
    }

    public String getDescription() {
        return "Send a lightning fast stream of piss that sprays out at the end to hit your opponent.";
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
