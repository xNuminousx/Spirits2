package me.numin.spirits2.inventories;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;

public class SubElementPicker implements SpiritsInventory{

    private final InventoryCreator inventory;

    public SubElementPicker(Player player) {
        int slots = 9, lightSlot = 2, darkSlot = 6;
        inventory = new InventoryCreator(player, getName(), slots);

        for (int i = 0; i < slots; i++) {
            if (i == lightSlot)
                inventory.setItem(i, Material.LIGHT_BLUE_GLAZED_TERRACOTTA, ChatColor.AQUA + "LightSpirit");
            else if (i == darkSlot)
                inventory.setItem(i, Material.BLACK_GLAZED_TERRACOTTA, ChatColor.BLUE + "DarkSpirit");
            else if (i == slots / 2)
                inventory.setItem(i, Material.NETHER_STAR, ChatColor.GOLD + "Select your sub-element");
            else
                inventory.setItem(i, Material.LIGHT_BLUE_GLAZED_TERRACOTTA, ChatColor.GRAY + "SPIRITS 2");
        }
        inventory.openInventory();
    }

    @Override
    public InventoryCreator getInventory() {
        return inventory;
    }

    @Override
    public String getName() {
        return "Choose Your Sub-Element";
    }
}
