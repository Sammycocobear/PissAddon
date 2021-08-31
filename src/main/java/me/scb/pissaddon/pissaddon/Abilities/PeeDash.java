package me.scb.pissaddon.pissaddon.Abilities;
import com.projectkorra.projectkorra.GeneralMethods;
import com.projectkorra.projectkorra.ability.AddonAbility;
import com.projectkorra.projectkorra.attribute.Attribute;
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
    private int height;
    @Attribute("Cooldown")
    private long cooldown;
    private PissListener listener;
    private int jump;


    public PeeDash(Player player) {
        super(player);
        if (this.bPlayer.canBend(this)) {
            this.location = player.getLocation();
            if(this.bPlayer.isOnCooldown(this)){
                return;
            }
            setfields();
            bPlayer.addCooldown(this,this.cooldown);
            this.start();
        }
    }

    private void setfields() {
        jump = Pissaddon.getPlugin().getConfig().getInt("ExtraAbilities.Sammycocobear.WaterSports.jumpvelo");
        height = Pissaddon.getPlugin().getConfig().getInt("ExtraAbilities.Sammycocobear.WaterSports.height");
        cooldown = Pissaddon.getPlugin().getConfig().getInt("ExtraAbilities.Sammycocobear.WaterSports.cooldown");
    }

    @Override
    public void progress() {
        if(!this.bPlayer.canBendIgnoreBindsCooldowns(this)){
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

    @Override
    public String getAuthor() {
        return "Sammycocobear";
    }

    @Override
    public String getVersion() {
        return "1.0.0";
    }
}
