package me.numin.spirits2.listeners;

import com.projectkorra.projectkorra.BendingPlayer;
import com.projectkorra.projectkorra.ability.Ability;
import com.projectkorra.projectkorra.ability.CoreAbility;
import me.numin.spirits2.inventories.AbilityHelper;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

public class AbilityHelperListener implements Listener {

    public static AbilityHelper abilityHelper;

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        Player player = (Player)event.getWhoClicked();
        BendingPlayer bPlayer = BendingPlayer.getBendingPlayer(player);

        if (bPlayer == null || abilityHelper == null)
            return;

        if (!event.getView().getTitle().equalsIgnoreCase(abilityHelper.getInventoryName())) {
            return;
        }

        if ((event.getCurrentItem() == null) ||
                (event.getCurrentItem().equals(new ItemStack(Material.AIR))) ||
                event.getCurrentItem().getItemMeta() == null ||
                event.getCurrentItem().getItemMeta().getDisplayName().isEmpty()) {
            event.setCancelled(true);
            return;
        }

        String itemName = event.getCurrentItem().getItemMeta().getDisplayName();
        Ability ability = CoreAbility.getAbility(ChatColor.stripColor(itemName));

        if (ability == null) {
            event.setCancelled(true);
            return;
        }

        ChatColor eleColor = ability.getElement().getColor();

        player.sendMessage(eleColor + "" + ChatColor.BOLD + itemName);
        player.sendMessage("");
        player.sendMessage(ability.getDescription());
        player.sendMessage("");
        player.sendMessage(eleColor + "" + ChatColor.BOLD + "Instructions: " + ChatColor.GRAY + ability.getInstructions());
        player.sendMessage("");
        player.sendMessage(ChatColor.GRAY + "" + ChatColor.ITALIC + "To bind this ability, use this command: /bending bind " + ability.getName().toLowerCase());
        abilityHelper.getInventoryCreator().closeInventory();
    }
}
