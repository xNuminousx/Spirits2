package me.numin.spirits2.utils;

import com.projectkorra.projectkorra.Element;
import com.projectkorra.projectkorra.Element.SubElement;
import me.numin.spirits2.Spirits2;
import org.bukkit.ChatColor;

public class SpiritElement {

    public static Element SPIRIT;
    public static SubElement LIGHT_SPIRIT, DARK_SPIRIT;

    public SpiritElement() {
        SPIRIT = new Element("Spirit", null, Spirits2.getInstance()) {
            @Override
            public ChatColor getColor() {
                return ChatColor.DARK_AQUA;
            }
            @Override
            public String getPrefix() {
                return getColor() + "[Spirit]";
            }
        };
        LIGHT_SPIRIT = new SubElement("LightSpirit", SPIRIT, null, Spirits2.getInstance()) {
            @Override
            public ChatColor getColor() {
                return ChatColor.AQUA;
            }
            @Override
            public String getPrefix() {
                return getColor() + "[LightSpirit]";
            }
        };
        DARK_SPIRIT = new SubElement("DarkSpirit", SPIRIT, null, Spirits2.getInstance()) {
            @Override
            public ChatColor getColor() {
                return ChatColor.BLUE;
            }
            @Override
            public String getPrefix() {
                return getColor() + "[DarkSpirit]";
            }
        };
    }
}
