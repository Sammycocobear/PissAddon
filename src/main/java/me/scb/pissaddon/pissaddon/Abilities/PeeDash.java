package me.scb.pissaddon.pissaddon.Abilities;
import com.projectkorra.projectkorra.GeneralMethods;
import com.projectkorra.projectkorra.ability.AddonAbility;
import com.projectkorra.projectkorra.attribute.Attribute;
import me.scb.pissaddon.pissaddon.FallDamageHelper;
import me.scb.pissaddon.pissaddon.PissAbility;
import me.scb.pissaddon.pissaddon.PissListener;
import me.scb.pissaddon.pissaddon.Pissaddon;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.bukkit.util.Vector;

public class PeeDash extends PissAbility implements AddonAbility {
    private Location location;
    @Attribute("Height")
    private double height;
    @Attribute("Cooldown")
    private long cooldown;
    private PissListener listener;
    private double jump;


    public PeeDash(Player player) {
        super(player);
        if (this.bPlayer.canBend(this)) {
            this.location = player.getLocation();
            if (this.bPlayer.isOnCooldown(this)) {
                return;
            }
            setfields();
            bPlayer.addCooldown(this, this.cooldown);
            this.start();
        }
    }

    private void setfields() {
        jump = Pissaddon.getPlugin().getConfig().getDouble("ExtraAbilities.Sammycocobear.PeeDash.jumpvelo");
        height = Pissaddon.getPlugin().getConfig().getDouble("ExtraAbilities.Sammycocobear.PeeDash.height");
        cooldown = Pissaddon.getPlugin().getConfig().getLong("ExtraAbilities.Sammycocobear.PeeDash.cooldown");
    }

    @Override
    public void progress() {
        if (this.player.isDead() || !this.player.isOnline()) {
            this.remove();
            return;
        } else if (GeneralMethods.isRegionProtectedFromBuild(this, location)) {
            this.remove();
            return;
        }
        if (!this.bPlayer.canBendIgnoreBindsCooldowns(this)) {
            remove();
            return;
        }
        Vector vector = this.player.getLocation().getDirection().normalize().multiply((float) this.jump);
        vector.setY((float) this.height);
        this.player.setVelocity(vector);
        this.player.getLocation();
        GeneralMethods.displayColoredParticle("ffff00", this.location, 30, 0.5D, 1.0D, 0.5D);
        this.bPlayer.addCooldown(this, this.cooldown);
        this.remove();
    }

    public void  remove(){
        super.remove();
        FallDamageHelper.addFallDamageCap(player, 0);
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
        return "PeeDash";
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
    public String getInstructions() {
        return "Launch yourself with your pee in a quick dash motion.";
    }

    public String getDescription() {
        return "<left-click>";
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
        String path = "ExtraAbilities.Sammycocobear.PeeDash.Enabled";
        if (Pissaddon.getPlugin().getConfig().contains(path)) {
            return Pissaddon.getPlugin().getConfig().getBoolean(path);
        }
        return false;
    }


}
