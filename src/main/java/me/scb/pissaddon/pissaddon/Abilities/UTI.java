package me.scb.pissaddon.pissaddon.Abilities;
import com.projectkorra.projectkorra.GeneralMethods;
import com.projectkorra.projectkorra.ability.AddonAbility;
import com.projectkorra.projectkorra.ability.ComboAbility;
import com.projectkorra.projectkorra.ability.util.ComboManager;
import com.projectkorra.projectkorra.util.ClickType;
import com.projectkorra.projectkorra.util.DamageHandler;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

import me.scb.pissaddon.pissaddon.PissAbility;
import me.scb.pissaddon.pissaddon.PissListener;
import me.scb.pissaddon.pissaddon.Pissaddon;
import org.bukkit.Sound;
import org.bukkit.entity.Entity;
import org.bukkit.Location;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.bukkit.permissions.Permission;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;

public class UTI extends PissAbility implements AddonAbility, ComboAbility {
    private long cooldown;
    private long time;
    private Set<Entity> hurt;
    private Location location;
    private Vector direction;
    private Permission perm;
    private PissListener listener;
    private double distancetraveled;
    private double distance;
    private double hitbox;
    private double damage;
    private int slowamp;
    private int slowduration;


    public UTI(Player player) {

        super(player);
        this.location = player.getLocation().clone().add(0.0D, 0.47673141357534D, 0.0D);
        direction = player.getLocation().getDirection();
        this.distancetraveled = 0.0D;
        hurt = new HashSet<>();

        if (this.bPlayer.isOnCooldown(this)) {
            return;
        }

        setfields();
        start();
    }

    private void setfields() {
        damage = Pissaddon.getPlugin().getConfig().getDouble("ExtraAbilities.Sammycocobear.UTI.damage");
        hitbox = Pissaddon.getPlugin().getConfig().getDouble("ExtraAbilities.Sammycocobear.UTI.hitbox");
        cooldown = Pissaddon.getPlugin().getConfig().getLong("ExtraAbilities.Sammycocobear.UTI.cooldown");
        distance = Pissaddon.getPlugin().getConfig().getDouble("ExtraAbilities.Sammycocobear.UTI.distance");
        slowamp = Pissaddon.getPlugin().getConfig().getInt("ExtraAbilities.Sammycocobear.UTI.slowamp");
        slowduration = Pissaddon.getPlugin().getConfig().getInt("ExtraAbilities.Sammycocobear.UTI.slowduration");
    }

    public void progress() {
        if (this.player.isDead() || !this.player.isOnline()) {
            this.remove();
            return;
        } else if (GeneralMethods.isRegionProtectedFromBuild(this, location)) {
            this.remove();
            return;
        }
        if (!this.bPlayer.canBendIgnoreBindsCooldowns(this)) {
            this.remove();
        } else if (this.location.getBlock().getType().isSolid()) {
            this.remove();
        } else if (this.distancetraveled > distance) {
            this.remove();
        } else {
            this.affectTargets();
            GeneralMethods.displayColoredParticle("964B00", this.location, 1, 0.1D, 0.1D, 0.1D);
            if (ThreadLocalRandom.current().nextInt(6) == 0) {
                this.location.getWorld().playSound(this.location, Sound.WEATHER_RAIN, 0.1F, 1.0F);
            }
            this.location.add(this.direction);
            this.distancetraveled += this.direction.length();
        }

    }


private void affectTargets() {
    List<Entity> targets = GeneralMethods.getEntitiesAroundPoint(this.location, hitbox);
    Iterator var2 = targets.iterator();

    while (var2.hasNext()) {
        Entity target = (Entity) var2.next();
        if (target.getUniqueId() != this.player.getUniqueId()) {
            if (target instanceof LivingEntity) {
                ((LivingEntity) target).addPotionEffect(new PotionEffect(PotionEffectType.SLOW,slowduration,slowamp));
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
}


    public void  remove(){
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
        return "UTI";
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
        HandlerList.unregisterAll(new PissListener());

    }
//Send your viral piss infection to your opponent.
    @Override
    public String getAuthor() {
        return "Sammycocobear";
    }

    @Override
    public String getVersion() {
        return Pissaddon.getVersion();
    }

    public String getInstructions() {
        return "UrinalInfection -> Tapshift, Tinkle -> LeftClick";
    }

    public String getDescription() {
        return "Send your viral piss infection to your opponent.";
    }

    @Override
    public Object createNewComboInstance(Player player) {
        return new UTI(player);
    }

    @Override
    public ArrayList<ComboManager.AbilityInformation> getCombination() {
        return new ArrayList(Arrays.asList(
                new ComboManager.AbilityInformation("UrinalInfection", ClickType.SHIFT_DOWN),
                new ComboManager.AbilityInformation("UrinalInfection", ClickType.SHIFT_UP),
                new ComboManager.AbilityInformation("Tinkle", ClickType.LEFT_CLICK)));
    }
    @Override
    public boolean isEnabled() {
        String path = "ExtraAbilities.Sammycocobear.UTI.Enabled";
        if (Pissaddon.getPlugin().getConfig().contains(path)) {
            return Pissaddon.getPlugin().getConfig().getBoolean(path);
        }
        return false;
    }

}


