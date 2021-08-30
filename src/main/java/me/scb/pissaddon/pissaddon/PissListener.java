package me.scb.pissaddon.pissaddon;

import com.projectkorra.projectkorra.BendingPlayer;
import com.projectkorra.projectkorra.Element;
import com.projectkorra.projectkorra.ProjectKorra;
import com.projectkorra.projectkorra.ability.Ability;
import com.projectkorra.projectkorra.ability.CoreAbility;
import com.projectkorra.projectkorra.configuration.ConfigManager;
import com.projectkorra.projectkorra.event.BendingReloadEvent;
import com.projectkorra.projectkorra.event.EntityBendingDeathEvent;
import com.projectkorra.projectkorra.object.HorizontalVelocityTracker;
import com.projectkorra.projectkorra.util.TempArmor;
import me.scb.pissaddon.pissaddon.Abilities.*;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerToggleSneakEvent;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Arrays;
import java.util.HashMap;

public class PissListener implements Listener {
    private static final HashMap<Player, String> BENDING_PLAYER_DEATH = new HashMap<>();
    private static final HashMap<Entity, Ability> BENDING_ENTITY_DEATH = new HashMap<>(); // Entities killed by Bending.

    public PissListener() {
    }

    @EventHandler
    public void OnClick(PlayerInteractEvent event) {
        if (event.getAction() == Action.LEFT_CLICK_AIR || event.getAction() == Action.LEFT_CLICK_BLOCK) {
            Player player = event.getPlayer();
            BendingPlayer bPlayer = BendingPlayer.getBendingPlayer(player);
            if (bPlayer.canBend(CoreAbility.getAbility(PissStream.class))) {
                new PissStream(player);
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
            if (bPlayer.getBoundAbilityName().equalsIgnoreCase("PeeDash")) {
                new PeeDash(player);
            }
            if (bPlayer.getBoundAbilityName().equalsIgnoreCase("SplitStream")) {
                new SplitStream(player);
            }
            if (bPlayer.getBoundAbilityName().equalsIgnoreCase("WaterSports")) {
                new WaterSports(player);
            }
            if (bPlayer.getBoundAbilityName().equalsIgnoreCase("PissWave")) {
                new PIssWave(player);
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
                if (bPlayer.getBoundAbilityName().equalsIgnoreCase("Tinkle")) {
                    new Tinkle(player);
                }
            }
        }
    }

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onPKReload(final BendingReloadEvent event) {
        Bukkit.getScheduler().runTaskLater(Pissaddon.getPlugin(), Pissaddon::reload, 1);
    }

    @EventHandler(priority = EventPriority.NORMAL, ignoreCancelled = true)
    public void onPlayerDeath(final PlayerDeathEvent event) {
        if (!(event.getEntity() instanceof Player)) {
            return;
        }

        if (event.getEntity().getKiller() != null) {
            if (BENDING_PLAYER_DEATH.containsKey(event.getEntity())) {
                String message = Pissaddon.getPlugin().getConfig().getString("DeathMessages.Default");
                final String ability = BENDING_PLAYER_DEATH.get(event.getEntity());
                final String tempAbility = ChatColor.stripColor(ability).replaceAll(" ", "");
                final CoreAbility coreAbil = CoreAbility.getAbility(tempAbility);
                Element element = null;

                if (coreAbil != null) {
                    element = coreAbil.getElement();
                }

                if (HorizontalVelocityTracker.hasBeenDamagedByHorizontalVelocity(event.getEntity()) && Arrays.asList(HorizontalVelocityTracker.abils).contains(tempAbility)) {
                    if (ConfigManager.languageConfig.get().contains("Abilities." + element.getName() + "." + tempAbility + ".HorizontalVelocityDeath")) {
                        message = ConfigManager.languageConfig.get().getString("Abilities." + element.getName() + "." + tempAbility + ".HorizontalVelocityDeath");
                    }
                } else if (element != null) {
                    if (element instanceof Element.SubElement) {
                        element = ((Element.SubElement) element).getParentElement();
                    }
                    if (ConfigManager.languageConfig.get().contains("Abilities." + element.getName() + "." + tempAbility + ".DeathMessage")) {
                        message = Pissaddon.getPlugin().getConfig().getString("Abilities." + element.getName() + "." + tempAbility + ".DeathMessage");
                    } else if (ConfigManager.languageConfig.get().contains("Abilities." + element.getName() + ".Combo." + tempAbility + ".DeathMessage")) {
                        message = Pissaddon.getPlugin().getConfig().getString("Abilities." + element.getName() + ".Combo." + tempAbility + ".DeathMessage");
                    }

                }
                message = message.replace("{victim}", event.getEntity().getName()).replace("{attacker}", event.getEntity().getKiller().getName()).replace("{ability}", ability);
                event.setDeathMessage(message);
                BENDING_PLAYER_DEATH.remove(event.getEntity());
            }
        }
    }

    @EventHandler(priority = EventPriority.NORMAL)
    public void onEntityBendingDeath(final EntityBendingDeathEvent event) {
        BENDING_ENTITY_DEATH.put(event.getEntity(), event.getAbility());
        if (event.getEntity() instanceof Player) {
            if (ConfigManager.languageConfig.get().getBoolean("DeathMessages.Enabled")) {
                final Ability ability = event.getAbility();
                if (ability == null) {
                    return;
                }

                BENDING_PLAYER_DEATH.put((Player) event.getEntity(), ability.getElement().getColor() + ability.getName());
                final Player player = (Player) event.getEntity();

                new BukkitRunnable() {
                    @Override
                    public void run() {
                        BENDING_PLAYER_DEATH.remove(player);
                    }
                }.runTaskLater(ProjectKorra.plugin, 20);
            }
        }
    }
}









