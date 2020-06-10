package me.numin.spirits2.utils;

import me.numin.spirits2.enumerations.AbilityType;
import me.numin.spirits2.enumerations.SpiritType;
import org.bukkit.ChatColor;

public class StringUtils {

    public static String formatDescription(AbilityType abilityType, SpiritType spiritType, String message) {
        String abilityTypeString = abilityType.toString().substring(0, 1).toUpperCase() + abilityType.toString().substring(1).toLowerCase();
        ChatColor headingColor = getSpiritColor(spiritType);
        ChatColor bodyColor = null;

        switch (spiritType) {
            case NEUTRAL: bodyColor = ChatColor.DARK_AQUA;
            break;
            case LIGHT: bodyColor = ChatColor.WHITE;
            break;
            case DARK: bodyColor = ChatColor.DARK_RED;
        }
        return headingColor + "" + ChatColor.BOLD + abilityTypeString + ": " + bodyColor + message;
    }

    public static ChatColor getSpiritColor(SpiritType spiritType) {
        switch (spiritType) {
            case NEUTRAL: return ChatColor.BLUE;
            case LIGHT: return ChatColor.AQUA;
            case DARK: return ChatColor.DARK_GRAY;
            default: return null;
        }
    }
}
