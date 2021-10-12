package me.scb.pissaddon.pissaddon.Abilities;

import com.projectkorra.projectkorra.GeneralMethods;
import com.projectkorra.projectkorra.ProjectKorra;
import com.projectkorra.projectkorra.ability.AddonAbility;
import com.projectkorra.projectkorra.ability.CoreAbility;
import com.projectkorra.projectkorra.util.DamageHandler;
import me.scb.pissaddon.pissaddon.PissAbility;
import me.scb.pissaddon.pissaddon.PissListener;
import me.scb.pissaddon.pissaddon.Pissaddon;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import java.util.*;

public class PissWave extends PissAbility implements AddonAbility {
    public Color cloudColor = null;


    public Vector velocity = new Vector();

    protected final Collection<Vector> waterCache, cloudCache;


    public int particlesFront = 10;

    /**
     * Amount of particles forming the back
     */
    public int particlesBack = 10;
//20
    public int rows = 20;


    public float lengthFront = 1.5f;


    public float lengthBack = 3;


    public float depthFront = 1;


    public float heightBack = .5f;

    public float height;
    //5

//2
    public float width;

    static public final float PI = 3.1415927f;

    static public final float degreesToRadians = PI / 180;

    private PissListener listener;
    private Set<Entity> hurt;

    protected boolean firstStep = true;
    private Location location;
    private double damage;
    private double hitbox;
    private Vector direction;
    private double distancetraveled;
    private double distance;
    private Location origin;
    private double speed;
    private long cooldown;

    public PissWave(Player player) {
        super(player);
        waterCache = new HashSet<>();
        cloudCache = new HashSet<>();
        location = player.getLocation();
        this.hurt = new HashSet();
        this.origin = player.getEyeLocation();
        distancetraveled = 25;
 
        this.direction = player.getLocation().getDirection().normalize().multiply(0.8D);
        if (CoreAbility.hasAbility(player, PissWave.class)) return;
        if(this.bPlayer.isOnCooldown(this)){
            return;
        }

        setfields();
        start();
    }

    private void setfields() {

        hitbox = Pissaddon.getPlugin().getConfig().getInt("ExtraAbilities.Sammycocobear.PissWave.hitbox");
        speed = Pissaddon.getPlugin().getConfig().getDouble("ExtraAbilities.Sammycocobear.PissWave.speed");
        damage = Pissaddon.getPlugin().getConfig(). getInt("ExtraAbilities.Sammycocobear.PissWave.damage");
        distance = Pissaddon.getPlugin().getConfig().getDouble("ExtraAbilities.Sammycocobear.PissWave.distance");
        cooldown = Pissaddon.getPlugin().getConfig().getLong("ExtraAbilities.Sammycocobear.PissWave.cooldown");
        width = Pissaddon.getPlugin().getConfig().getInt("ExtraAbilities.Sammycocobear.PissWave.width");
        height = Pissaddon.getPlugin().getConfig().getInt("ExtraAbilities.Sammycocobear.PissWave.height");


    }

    @Override
    public void progress() {
        if (this.location.distanceSquared(origin) >= (double) (this.distance * this.distance)) {
            this.remove();
        }
        if (firstStep) {
            velocity.copy(location.getDirection().setY(0).normalize().multiply(speed));
            invalidate(location);
        }
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

        location.add(velocity);
        for (Vector v : cloudCache) {
            location.add(v);
            affectTargets(location);
            GeneralMethods.displayColoredParticle("ffffff", location, 1, 0.1D, 0.1D, 0.1D);
            location.subtract(v);
        }
        for (Vector v : waterCache) {
            location.add(v);
            affectTargets(location);
            GeneralMethods.displayColoredParticle("ffff00", location, 1, 0.1D, 0.1D, 0.1D);
            location.subtract(v);
        }
    }



    public void invalidate(Location location) {
        firstStep = false;
        waterCache.clear();
        cloudCache.clear();

        Vector s1 = new Vector(-lengthFront, 0, 0);
        Vector s2 = new Vector(lengthBack, 0, 0);
        Vector h = new Vector(-0.5 * lengthFront, height, 0);

        Vector n1, n2, n_s1ToH, n_s2ToH, c1, c2, s1ToH, s2ToH;
        float len_s1ToH, len_s2ToH, yaw;

        s1ToH = h.clone().subtract(s1);
        c1 = s1.clone().add(s1ToH.clone().multiply(0.5));
        len_s1ToH = (float) s1ToH.length();
        n_s1ToH = s1ToH.clone().multiply(1f / len_s1ToH);
        n1 = new Vector(s1ToH.getY(), -s1ToH.getX(), 0).normalize();
        if (n1.getX() < 0) n1.multiply(-1);
        s2ToH = h.clone().subtract(s2);
        c2 = s2.clone().add(s2ToH.clone().multiply(0.5));
        len_s2ToH = (float) s2ToH.length();
        n_s2ToH = s2ToH.clone().multiply(1f / len_s2ToH);
        n2 = new Vector(s2ToH.getY(), -s2ToH.getX(), 0).normalize();
        if (n2.getX() < 0) n2.multiply(-1);

        yaw = (-location.getYaw() + 90) * degreesToRadians;


        for (int i = 0; i < particlesFront; i++) {
            float ratio = (float) i / particlesFront;
            float x = (ratio - .5f) * len_s1ToH;
            float y = (float) (-depthFront / Math.pow((len_s1ToH / 2), 2) * Math.pow(x, 2) + depthFront);
            Vector v = c1.clone();
            v.add(n_s1ToH.clone().multiply(x));
            v.add(n1.clone().multiply(y));
            for (int j = 0; j < rows; j++) {
                float z = ((float) j / rows - .5f) * width;
                Vector vec = v.clone().setZ(v.getZ() + z);
                rotateAroundAxisY(vec, yaw);
                if (i == 0 || i == particlesFront - 1) cloudCache.add(vec);
                else waterCache.add(vec);
            }
        }
        for (int i = 0; i < particlesBack; i++) {
            float ratio = (float) i / particlesBack;
            float x = (ratio - .5f) * len_s2ToH;
            float y = (float) (-heightBack / Math.pow((len_s2ToH / 2), 2) * Math.pow(x, 2) + heightBack);
            Vector v = c2.clone();
            v.add(n_s2ToH.clone().multiply(x));
            v.add(n2.clone().multiply(y));
            for (int j = 0; j < rows; j++) {
                float z = ((float) j / rows - .5f) * width;
                Vector vec = v.clone().setZ(v.getZ() + z);
                rotateAroundAxisY(vec, yaw);
                if (i == particlesFront - 1) cloudCache.add(vec);
                else waterCache.add(vec);
            }
        }
    }
    public static final Vector rotateAroundAxisY(Vector v, double angle) {
        double x, z, cos, sin;
        cos = Math.cos(angle);
        sin = Math.sin(angle);
        x = v.getX() * cos + v.getZ() * sin;
        z = v.getX() * -sin + v.getZ() * cos;
        return v.setX(x).setZ(z);
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
        return "PissWave";
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

    }



    public String getInstructions() {
        return "<left-click>";
    }

    public String getDescription() {
        return "Project a tsunami of piss at your opponent. ";
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
        String path = "ExtraAbilities.Sammycocobear.PissWave.Enabled";
        if (Pissaddon.getPlugin().getConfig().contains(path)) {
            return Pissaddon.getPlugin().getConfig().getBoolean(path);
        }
        return false;
    }
}
