package me.numin.spirits2.inventories;

import com.projectkorra.projectkorra.Element.SubElement;
import me.numin.spirits2.utils.SpiritElement;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class SubElementPicker {

    public SubElementPicker(Player player) {
        int slots = 9, lightSlot = 2, darkSlot = 6;
        Inventory inventory = Bukkit.createInventory(player, slots, getInventoryName());

        for (int i = 0; i < slots; i++) {
            if (i == lightSlot)
                inventory.setItem(i, createItem(Material.LIGHT_BLUE_GLAZED_TERRACOTTA, "LightSpirit", ChatColor.AQUA, getItemLore(SpiritElement.LIGHT_SPIRIT)));
            else if (i == darkSlot)
                inventory.setItem(i, createItem(Material.BLACK_GLAZED_TERRACOTTA, "DarkSpirit", ChatColor.BLUE, getItemLore(SpiritElement.DARK_SPIRIT)));
            else if (i == slots / 2)
                inventory.setItem(i, createItem(Material.NETHER_STAR, "Select your sub-element.", ChatColor.GOLD, null));
            else
                inventory.setItem(i, createItem(Material.LIGHT_GRAY_STAINED_GLASS_PANE, "SPIRITS 2", ChatColor.GRAY, null));
        }

        player.openInventory(inventory);
        player.playSound(player.getLocation(), Sound.ENTITY_PIG_SADDLE, 1, (float) 0.7);
    }

    private List<String> getItemLore(SubElement element) {
        List<String> lore = new ArrayList<>();
        if (element == SpiritElement.LIGHT_SPIRIT) {
            lore.add("LightSpirits are peaceful, enlightened creatures.");
            lore.add("Specializations:");
            lore.add("- Healing");
            lore.add("- Defense");
            return lore;
        } else if (element == SpiritElement.DARK_SPIRIT) {
            lore.add("DarkSpirits are unbalanced, beacons of chaos.");
            lore.add("Specializations:");
            lore.add("- Damage");
            lore.add("- Speed");
            return lore;
        } else
            return null;
    }

    private ItemStack createItem(Material icon, String name, ChatColor color, List<String> lore) {
        ItemStack item = new ItemStack(icon);
        ItemMeta itemMeta = item.getItemMeta();
        assert itemMeta != null;
        itemMeta.setDisplayName(color + name);
        if (lore != null)
            itemMeta.setLore(lore);
        item.setItemMeta(itemMeta);
        return item;
    }

    public String getInventoryName() {
        return "Choose Your Sub-Element";
    }
}
