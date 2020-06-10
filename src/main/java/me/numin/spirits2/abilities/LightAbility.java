package me.numin.spirits2.abilities;

import com.projectkorra.projectkorra.Element;
import com.projectkorra.projectkorra.ability.Ability;
import com.projectkorra.projectkorra.ability.SubAbility;
import me.numin.spirits2.utils.SpiritElement;
import org.bukkit.entity.Player;

public abstract class LightAbility extends SpiritAbility implements SubAbility {

    public LightAbility(Player player) {
        super(player);
    }

    @Override
    public Class<? extends Ability> getParentAbility() {
        return SpiritAbility.class;
    }

    @Override
    public Element getElement() {
        return SpiritElement.LIGHT_SPIRIT;
    }
}
