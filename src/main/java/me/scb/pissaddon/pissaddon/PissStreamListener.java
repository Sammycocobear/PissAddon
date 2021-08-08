package me.scb.pissaddon.pissaddon;

import com.projectkorra.projectkorra.BendingPlayer;
import com.projectkorra.projectkorra.ability.CoreAbility;
import me.scb.pissaddon.pissaddon.Abilities.*;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerToggleSneakEvent;

public class PissStreamListener implements Listener {
    public PissStreamListener() {
    }

    @EventHandler
    public void OnClick(PlayerInteractEvent event) {
        if (event.getAction() == Action.LEFT_CLICK_AIR || event.getAction() == Action.LEFT_CLICK_BLOCK) {
            Player player = event.getPlayer();
            BendingPlayer bPlayer = BendingPlayer.getBendingPlayer(player);
            if (bPlayer.canBend(CoreAbility.getAbility(PissStream.class))) {
                new PissStream(player);
            }
            if (bPlayer.canBend(CoreAbility.getAbility(KidneyStones.class))) {
                new KidneyStones(player);
            }
            if (bPlayer.canBend(CoreAbility.getAbility(GoldenShower.class))) {
                new GoldenShower(player);
            }
            if (bPlayer.getBoundAbilityName().equalsIgnoreCase("PissSwirl")) {
                new PissSwirl(player);
            }
            if (bPlayer.getBoundAbilityName().equalsIgnoreCase("UrinalInfection")) {
                new UrinalInfection(player);
            }
            if (bPlayer.getBoundAbilityName().equalsIgnoreCase("PeeDrain")) {
                new PeeDrain(player);
            }
            if (bPlayer.getBoundAbilityName().equalsIgnoreCase("PeeRocket")) {
                new PeeRocket(player);
            }
            if (bPlayer.getBoundAbilityName().equalsIgnoreCase("PeeDash")) {
                new PeeDash(player);
            }
            if (bPlayer.getBoundAbilityName().equalsIgnoreCase("UTI")) {
                new UTI(player);
            }
        }
    }

    @EventHandler
    public void onSneak(PlayerToggleSneakEvent event) {
        Player player = event.getPlayer();
        BendingPlayer bPlayer = BendingPlayer.getBendingPlayer(player);
        if (bPlayer != null) {
            CoreAbility coreAbil = bPlayer.getBoundAbility();
            String abil = bPlayer.getBoundAbilityName();
            if (coreAbil != null) {
                if (abil.equalsIgnoreCase("PissSlide") && bPlayer.canBend(CoreAbility.getAbility(PissSlide.class)) && !CoreAbility.hasAbility(player, PissSlide.class)) {
                    new PissSlide(player);
                }
                if (abil.equalsIgnoreCase("PissSplatter") && bPlayer.canBend(CoreAbility.getAbility(PissSplatter.class)) && !CoreAbility.hasAbility(player, PissSplatter.class)) {
                    new PissSplatter(player);

                }
                if (bPlayer.getBoundAbilityName().equalsIgnoreCase("PeeRocket")) {
                    new PeeRocket(player);
                }
            }
        }
    }

}







