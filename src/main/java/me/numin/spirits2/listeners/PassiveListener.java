package me.numin.spirits2.listeners;

import com.projectkorra.projectkorra.BendingPlayer;
import com.projectkorra.projectkorra.Element;
import me.numin.spirits2.utils.SpiritElement;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;

public class PassiveListener implements Listener {

    @EventHandler
    public void onFallDamage(EntityDamageEvent event) {
        if (event.getEntity() instanceof Player) {
            Player player = (Player)event.getEntity();
            BendingPlayer bPlayer = BendingPlayer.getBendingPlayer(player);

            if (event.isCancelled() || bPlayer == null || bPlayer.hasElement(Element.AIR) || bPlayer.hasElement(Element.EARTH))
                return;

            if (bPlayer.hasElement(SpiritElement.SPIRIT) && event.getCause() == EntityDamageEvent.DamageCause.FALL) {
                event.setDamage(0);
                event.setCancelled(true);
            }
        }
    }
}
