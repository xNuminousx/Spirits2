package me.numin.spirits2.listeners;

import com.projectkorra.projectkorra.BendingPlayer;
import com.projectkorra.projectkorra.Element;
import com.projectkorra.projectkorra.GeneralMethods;
import com.projectkorra.projectkorra.event.PlayerChangeElementEvent;
import me.numin.spirits2.utils.SpiritElement;
import me.numin.spirits2.Spirits2;
import me.numin.spirits2.inventories.SubElementPicker;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.ItemStack;

public class ProjectKorraListener implements Listener {

    private SubElementPicker subPicker;
    private boolean hasSelected = false;

    @EventHandler
    public void onElementChange(PlayerChangeElementEvent event) {
        Player player = event.getTarget();
        BendingPlayer bPlayer = BendingPlayer.getBendingPlayer(player);

        if (bPlayer == null)
            return;

        if (event.getElement() == SpiritElement.SPIRIT)
            subPicker = new SubElementPicker(player);
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        Player player = (Player)event.getWhoClicked();
        BendingPlayer bPlayer = BendingPlayer.getBendingPlayer(player);

        if (bPlayer == null || subPicker == null)
            return;

        if (!event.getView().getTitle().equalsIgnoreCase(subPicker.getName())) {
            return;
        }

        if ((event.getCurrentItem() == null) ||
                (event.getCurrentItem().equals(new ItemStack(Material.AIR))) ||
                event.getCurrentItem().getItemMeta() == null ||
                event.getCurrentItem().getItemMeta().getDisplayName().isEmpty()) {
            event.setCancelled(true);
            return;
        }

        if (event.getCurrentItem().getItemMeta().getDisplayName().contains("LightSpirit")) {
            event.setCancelled(true);
            selectElement(player, SpiritElement.LIGHT_SPIRIT);
        } else if (event.getCurrentItem().getItemMeta().getDisplayName().contains("DarkSpirit")) {
            event.setCancelled(true);
            selectElement(player, SpiritElement.DARK_SPIRIT);
        } else {
            event.setCancelled(true);
            return;
        }
        subPicker.getInventory().closeInventory();
    }

    @EventHandler
    public void onInventoryClose(InventoryCloseEvent event) {
        Player player = (Player)event.getPlayer();
        BendingPlayer bPlayer = BendingPlayer.getBendingPlayer(player);

        if (bPlayer == null || subPicker == null)
            return;

        if (!event.getView().getTitle().equalsIgnoreCase(subPicker.getName()))
            return;

        if (!hasSelected && bPlayer.hasElement(SpiritElement.SPIRIT)) {
            bPlayer.getElements().remove(SpiritElement.SPIRIT);
            GeneralMethods.saveElements(bPlayer);
            GeneralMethods.sendBrandingMessage(player, ChatColor.RED + "You didn't select a sub-element.");
        }
    }

    private void selectElement(Player player, Element.SubElement subElement) {
        BendingPlayer bPlayer = BendingPlayer.getBendingPlayer(player);

        if (subElement.equals(SpiritElement.LIGHT_SPIRIT))
            player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_CHIME, 1, 1);
        else if (subElement.equals(SpiritElement.DARK_SPIRIT))
            player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_CHIME, 1, -1);

        try {
            bPlayer.addSubElement(subElement);
            GeneralMethods.saveSubElements(bPlayer);
            GeneralMethods.sendBrandingMessage(player, subElement.getColor() + "You are now a " + subElement.getName());
            hasSelected = true;
        } catch (Exception e) {
            hasSelected = false;
            Spirits2.getInstance().getLogger().info("Failed to grant " + subElement.getName() + " for " + player.getName());
            e.printStackTrace();
        }
    }
}
