package me.numin.spirits2.utils;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;

public class InventoryCreator {

    public Inventory inventory;
    public Player player;
    public String name;
    public int slots;

    public InventoryCreator(Player player, String name, int slots) {
        this.name = name;
        this.player = player;
        this.slots = slots;

        inventory = Bukkit.createInventory(player, slots, name);
    }

    public void closeInventory() {
        player.playSound(player.getLocation(), Sound.ENTITY_HORSE_SADDLE, 0.5F, 0);
        player.closeInventory();
    }

    public Inventory getInventory() {
        return inventory;
    }

    public String getName() {
        return name;
    }

    public int getSlots() {
        return slots;
    }

    public void openInventory() {
        player.playSound(player.getLocation(), Sound.ENTITY_HORSE_SADDLE, 0.5F, 0);
        player.openInventory(getInventory());
    }

    public void setItem(int slot, Material icon, String name) {
        ItemStack itemStack = new ItemStack(icon);
        itemStack.getItemMeta().setDisplayName(name);
        getInventory().setItem(slot, itemStack);
    }

    public void setItem(int slot, Material icon, String name, List<String> lore) {
        ItemStack itemStack = new ItemStack(icon);
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setDisplayName(name);
        itemMeta.setLore(lore);
        itemStack.setItemMeta(itemMeta);
        getInventory().setItem(slot, itemStack);
    }
}
