package me.numin.spirits2.inventories;

import com.projectkorra.projectkorra.ability.Ability;
import com.projectkorra.projectkorra.ability.CoreAbility;
import me.numin.spirits2.Spirits2;
import me.numin.spirits2.utils.SpiritElement;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import java.util.Collections;
import java.util.List;

public class AbilityHelper implements SpiritsInventory {

    private final InventoryCreator inventory;

    public AbilityHelper(Player player) {
        inventory = new InventoryCreator(player, getName(), 54);

        //TODO: Abilities are loading in randomly. Sort them by element.
        List<CoreAbility> abilities = Spirits2.getInstance().getPluginAbilities();
        for (int i = 0; i <= abilities.size() - 1; i++) {
            if (abilities.get(i) == null || i >= inventory.getSlots())
                break;

            Ability ability = abilities.get(i);
            String name = ability.getElement().getColor() + "" + ChatColor.BOLD + ability.getName();
            List<String> lore = Collections.singletonList("Click for more information!");
            Material icon = null;

            if (ability.getElement() == SpiritElement.SPIRIT)
                icon = Material.CYAN_GLAZED_TERRACOTTA;
            else if (ability.getElement() == SpiritElement.LIGHT_SPIRIT)
                icon = Material.LIGHT_BLUE_GLAZED_TERRACOTTA;
            else if (ability.getElement() == SpiritElement.DARK_SPIRIT)
                icon = Material.BLACK_GLAZED_TERRACOTTA;

            inventory.setItem(i, icon, name, lore);
        }
        inventory.openInventory();
    }

    @Override
    public InventoryCreator getInventory() {
        return inventory;
    }

    @Override
    public String getName() {
        return "Ability Guide";
    }
}
