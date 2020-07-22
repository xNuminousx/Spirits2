package me.numin.spirits2.listeners;

import com.projectkorra.projectkorra.BendingPlayer;
import com.projectkorra.projectkorra.ability.CoreAbility;
import me.numin.spirits2.abilities.spirit.Dash;
import me.numin.spirits2.abilities.spirit.Possess;
import me.numin.spirits2.abilities.spirit.Vanish;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerToggleSneakEvent;
import org.bukkit.inventory.EquipmentSlot;

import java.util.ArrayList;
import java.util.List;

public class AbilityListener implements Listener {

    private final List<Player> playersDroppingItems = new ArrayList<>();

    @EventHandler
    public void onItemDrop(PlayerDropItemEvent event) {
        Player player = event.getPlayer();
        BendingPlayer bPlayer = BendingPlayer.getBendingPlayer(player);

        if (bPlayer == null)
            return;

        if (bPlayer.getBoundAbility() == null)
            return;

        if (!playersDroppingItems.contains(player))
            playersDroppingItems.add(player);
    }

    @EventHandler
    public void onClick(PlayerInteractEvent event) {
        Player player = event.getPlayer();

        if (event.getHand() != EquipmentSlot.HAND)
            return;

        if (event.getAction() != Action.LEFT_CLICK_BLOCK && event.getAction() != Action.LEFT_CLICK_AIR)
            return;

        if (event.getAction() == Action.LEFT_CLICK_BLOCK && event.isCancelled())
            return;

        if (playersDroppingItems.contains(player)) {
            playersDroppingItems.remove(player);
            return;
        }

        BendingPlayer bPlayer = BendingPlayer.getBendingPlayer(player);

        if (bPlayer == null)
            return;

        if (bPlayer.getBoundAbilityName().equalsIgnoreCase("Dash") && !bPlayer.isOnCooldown("Dash"))
            new Dash(player);
    }

    @EventHandler
    public void onSneak(PlayerToggleSneakEvent event) {
        Player player = event.getPlayer();
        BendingPlayer bPlayer = BendingPlayer.getBendingPlayer(player);

        if (event.isCancelled() || bPlayer == null)
            return;

        if (bPlayer.getBoundAbilityName().equalsIgnoreCase("Possess") && !event.isSneaking() && !CoreAbility.hasAbility(player, Possess.class))
            new Possess(player);
        else if (bPlayer.getBoundAbilityName().equalsIgnoreCase("Vanish"))
            new Vanish(player);
    }
}
