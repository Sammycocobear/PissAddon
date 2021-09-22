package me.scb.pissaddon.pissaddon;

import com.projectkorra.projectkorra.Element;
import com.projectkorra.projectkorra.ability.Ability;
import com.projectkorra.projectkorra.ability.SubAbility;
import com.projectkorra.projectkorra.ability.WaterAbility;
import org.bukkit.entity.Player;

public abstract class PissAbility extends WaterAbility implements SubAbility {
    public PissAbility(Player player) {
        super(player);
    }

    public Class<? extends Ability> getParentAbility() {
        return WaterAbility.class;
    }

    public Element getElement() {
        return PissSubElement.PISS;
    }

}

