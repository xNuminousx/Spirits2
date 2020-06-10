package me.numin.spirits2.enumerations;

import com.projectkorra.projectkorra.BendingPlayer;
import me.numin.spirits2.utils.SpiritElement;
import org.bukkit.entity.Player;

public enum SpiritType {

    DARK, LIGHT, NEUTRAL;

    public static SpiritType getSpiritType(Player player) {
        BendingPlayer bPlayer = BendingPlayer.getBendingPlayer(player);

        if (bPlayer == null)
            return null;

        if (bPlayer.hasSubElement(SpiritElement.LIGHT_SPIRIT) && bPlayer.hasSubElement(SpiritElement.DARK_SPIRIT)) return NEUTRAL;
        else if (bPlayer.hasSubElement(SpiritElement.LIGHT_SPIRIT)) return LIGHT;
        else if (bPlayer.hasSubElement(SpiritElement.DARK_SPIRIT)) return DARK;
        else return null;
    }
}
