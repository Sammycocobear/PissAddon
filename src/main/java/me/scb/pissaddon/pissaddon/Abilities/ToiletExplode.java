package me.scb.pissaddon.pissaddon.Abilities;

import com.projectkorra.projectkorra.GeneralMethods;
import com.projectkorra.projectkorra.ability.AddonAbility;
import com.projectkorra.projectkorra.ability.ComboAbility;
import com.projectkorra.projectkorra.ability.util.ComboManager;
import com.projectkorra.projectkorra.util.ClickType;
import com.projectkorra.projectkorra.util.DamageHandler;
import me.scb.pissaddon.pissaddon.PissAbility;
import me.scb.pissaddon.pissaddon.PissListener;
import me.scb.pissaddon.pissaddon.Pissaddon;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;
import java.util.*;

public class ToiletExplode extends PissAbility implements AddonAbility, ComboAbility {
    private final HashSet hurt;
    private double damage;
    private Location location;
    private long cooldown;
    private PissListener listener;
    private long time;
    private long duration;
    private double t;
    private Vector direction;
    private Location loc;
    private double height;
    private double width;


    public ToiletExplode(Player player) {
        super(player);
        this.location = player.getLocation().clone().add(0.0D, 0.47673141357534D, 0.0D);
        this.t = 0;
        loc = player.getLocation();
        this.hurt = new HashSet();
        this.direction = player.getLocation().getDirection().normalize().multiply(0.8D);
        this.direction.multiply(0.8D);
        if (this.bPlayer.isOnCooldown(this)) {
            return;
        }
        setfields();
        this.bPlayer.addCooldown("ToiletExplode", this.cooldown);
        start();
    }

    private void setfields() {
        damage = Pissaddon.getPlugin().getConfig().getDouble("ExtraAbilities.Sammycocobear.ToiletExplode.damage");
        cooldown = Pissaddon.getPlugin().getConfig().getLong("ExtraAbilities.Sammycocobear.ToiletExplode.cooldown");
        duration = Pissaddon.getPlugin().getConfig().getLong("ExtraAbilities.Sammycocobear.ToiletExplode.duration");
        height = Pissaddon.getPlugin().getConfig().getDouble("ExtraAbilities.Sammycocobear.ToiletExplode.height");
        width = Pissaddon.getPlugin().getConfig().getDouble("ExtraAbilities.Sammycocobear.ToiletExplode.width");

    }


    @Override
    public void progress() {
        long time = System.currentTimeMillis();
        if (this.time - this.getStartTime() < this.duration) {
            math();
        }
        player.setFallDistance(0);
    }





    public void math() {
        if (this.time - this.getStartTime() > this.duration) {
            this.remove();
            return;
        }
        t += 0.1 * Math.PI;
        for (double theta = 0; theta <= 2 * Math.PI; theta = theta + Math.PI / 32) {
            double x = t * (Math.cos(theta)*width);
            double y = (-4 * Math.exp(-0.1 * t) * Math.sin(t) + 1.5 * height);
            double z = (t * Math.sin(theta) * width);
            loc.add(x, y, z);
            this.affectTargets(location);
            GeneralMethods.displayColoredParticle("ffff00", loc);
            GeneralMethods.displayColoredParticle("964B00", loc);
            loc.subtract(x, y, z);
            this.time = System.currentTimeMillis();

        }
    }

    private void affectTargets(Location location) {
        List<Entity> targets = GeneralMethods.getEntitiesAroundPoint(this.location, 10);
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
            }
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
        return "ToiletExplode";
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
    public String getAuthor() {
        return "Sammycocobear";
    }

    @Override
    public String getVersion() {
        return "1.0.0";
    }
    public String getInstructions() {
        return "PissSwirl(Tap Shift)->GoldenShower(Tap Shift)->UrinalInfection(Left Click)";
    }

    public String getDescription() {
        return "Your piss slams into the ground which causes an explosion.";
    }

    @Override
    public Object createNewComboInstance(Player player) {
        return new ToiletExplode(player);
    }

    @Override
    public ArrayList<ComboManager.AbilityInformation> getCombination() {
        return new ArrayList(Arrays.asList(
                new ComboManager.AbilityInformation("PissSwirl", ClickType.SHIFT_DOWN),
                new ComboManager.AbilityInformation("PissSwirl", ClickType.SHIFT_UP),
                new ComboManager.AbilityInformation("GoldenShower", ClickType.SHIFT_DOWN),
                new ComboManager.AbilityInformation("GoldenShower", ClickType.SHIFT_UP),
                new ComboManager.AbilityInformation("UrinalInfection", ClickType.LEFT_CLICK)));
    }
}
