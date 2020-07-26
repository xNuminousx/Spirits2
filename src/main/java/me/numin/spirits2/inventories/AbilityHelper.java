package me.numin.spirits2.inventories;

import com.projectkorra.projectkorra.ability.Ability;
import com.projectkorra.projectkorra.ability.CoreAbility;
import me.numin.spirits2.Spirits2;
import me.numin.spirits2.utils.InventoryCreator;
import me.numin.spirits2.utils.SpiritElement;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import java.util.Collections;
import java.util.List;

public class AbilityHelper {

    private final InventoryCreator inventoryCreator;

    public AbilityHelper(Player player) {
        inventoryCreator = new InventoryCreator(player, getInventoryName(), 54);

        //TODO: Abilities are loading in randomly. Sort them by element.
        List<CoreAbility> spiritAbilities = Spirits2.getInstance().getSpiritAbilities();
        for (int i = 0; i <= spiritAbilities.size() - 1; i++) {
            if (spiritAbilities.get(i) == null || i >= inventoryCreator.getSlots())
                break;

            Ability ability = spiritAbilities.get(i);
            String name = ability.getElement().getColor() + "" + ChatColor.BOLD + ability.getName();
            List<String> lore = Collections.singletonList("Click for more information!");
            Material icon = null;

            if (ability.getElement() == SpiritElement.SPIRIT)
                icon = Material.LIGHT_BLUE_GLAZED_TERRACOTTA;
            else if (ability.getElement() == SpiritElement.LIGHT_SPIRIT)
                icon = Material.CYAN_GLAZED_TERRACOTTA;
            else if (ability.getElement() == SpiritElement.DARK_SPIRIT)
                icon = Material.BLACK_GLAZED_TERRACOTTA;

            inventoryCreator.setItem(i, icon, name, lore);
        }
        inventoryCreator.openInventory();
    }

    public InventoryCreator getInventoryCreator() {
        return inventoryCreator;
    }

    public String getInventoryName() {
        return "Ability Guide";
    }
}
