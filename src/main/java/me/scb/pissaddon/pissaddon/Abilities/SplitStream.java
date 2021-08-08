package me.scb.pissaddon.pissaddon.Abilities;

import com.projectkorra.projectkorra.ability.AddonAbility;
import com.projectkorra.projectkorra.configuration.ConfigManager;
import me.scb.pissaddon.pissaddon.PissAbility;
import org.bukkit.Location;
import org.bukkit.entity.Player;

//Sends a pee blast and then it quickly splits in 3 directions.
//Split stream - Hold shift on PissBlast, then switch to miscontrol and click.
public class SplitStream extends PissAbility implements AddonAbility {
    private long cooldown;

    public SplitStream(Player player) {
        super(player);
        setfields();
        start();
    }

    private void setfields() {
        this.cooldown = ConfigManager.getConfig().getLong("ExtraAbilities.Sammycocobear.SplitStream.cooldown");
    }

    @Override
    public void progress() {
        if(this.bPlayer.isOnCooldown(this)){
            return;
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
