package me.scb.pissaddon.pissaddon.Abilities;

import com.projectkorra.projectkorra.GeneralMethods;
import com.projectkorra.projectkorra.ability.AddonAbility;
import com.projectkorra.projectkorra.configuration.ConfigManager;
import com.projectkorra.projectkorra.util.DamageHandler;
import me.scb.pissaddon.pissaddon.PissAbility;
import me.scb.pissaddon.pissaddon.PissListener;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.bukkit.util.Vector;

import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

//Sends a pee blast and then it quickly splits in 3 directions.
//Split stream - Hold shift on PissBlast, then switch to miscontrol and click.
public class SplitStream extends PissAbility implements AddonAbility {
    private long cooldown;
    private Location location;
    private Set<Entity> hurt;
    private double damage;
    private Vector direction;
    private PissListener listener;

    public SplitStream(Player player) {
        super(player);
        this.location = player.getLocation().clone().add(0.0D, 0.47673141357534D, 0.0D);
        this.direction = player.getLocation().getDirection();

        this.hurt = new HashSet();
        setfields();
        start();
    }

    private void setfields() {
        this.cooldown = ConfigManager.getConfig().getLong("ExtraAbilities.Sammycocobear.SplitStream.cooldown");
        this.damage = ConfigManager.getConfig().getDouble("ExtraAbilities.Sammycocobear.SplitStream.damage");
    }
    private  void leftstream() {
        for (int t = 0; t < 360; ++t) {
            double x = Math.sin(t)*Math.cos(t);
            double y = 0;
            double z = 10 * Math.sin(t)*Math.sin(t);
            Vector vec1 = new Vector(x,y,z);
            this.location.add(vec1);
            GeneralMethods.displayColoredParticle("ffff00", location);
            location.subtract(vec1);


        }
    }
    private void rightstream(){
        for (int t = 0; t < 360; ++t) {
            double x = -(Math.sin(t)*Math.cos(t));
            double y = -(0);
            double z = (10 * Math.sin(t)*Math.sin(t));
            Vector vec2 = new Vector(x,y,z);
            location.add(vec2);
            GeneralMethods.displayColoredParticle("ffff00", location);
            location.subtract(vec2);
        }
    }



    @Override
    public void progress() {
        if(this.bPlayer.isOnCooldown(this)){
            return;
        }
        this.affectTargets();
        this.rightstream();
        this.leftstream();
        GeneralMethods.displayColoredParticle("ffff00", this.location, 1, 0.1D, 0.1D, 0.1D);



    }
    private void affectTargets() {
        List<Entity> targets = GeneralMethods.getEntitiesAroundPoint(this.location, 1.0D);
        Iterator var2 = targets.iterator();

        while(var2.hasNext()) {
            Entity target = (Entity)var2.next();
            if (target.getUniqueId() != this.player.getUniqueId()) {
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
        return "SplitStream";
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
