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
            final String prefix = Spirits2.getInstance().getConfig().getString("Language.Prefixes.Spirit.Prefix");
            final String sColor = Spirits2.getInstance().getConfig().getString("Language.Prefixes.Spirit.Color");
            final ChatColor color = ChatColor.valueOf(sColor);

            @Override
            public ChatColor getColor() {
                return color == null ? ChatColor.DARK_AQUA : color;
            }
            @Override
            public String getPrefix() {
                return prefix;
            }
        };
        LIGHT_SPIRIT = new SubElement("LightSpirit", SPIRIT, null, Spirits2.getInstance()) {
            final String prefix = Spirits2.getInstance().getConfig().getString("Language.Prefixes.LightSpirit.Prefix");
            final String sColor = Spirits2.getInstance().getConfig().getString("Language.Prefixes.LightSpirit.Color");
            final ChatColor color = ChatColor.valueOf(sColor);

            @Override
            public ChatColor getColor() {
                return color == null ? ChatColor.AQUA : color;
            }
            @Override
            public String getPrefix() {
                return prefix;
            }
        };
        DARK_SPIRIT = new SubElement("DarkSpirit", SPIRIT, null, Spirits2.getInstance()) {
            final String prefix = Spirits2.getInstance().getConfig().getString("Language.Prefixes.DarkSpirit.Prefix");
            final String sColor = Spirits2.getInstance().getConfig().getString("Language.Prefixes.DarkSpirit.Color");
            final ChatColor color = ChatColor.valueOf(sColor);

            @Override
            public ChatColor getColor() {
                return color == null ? ChatColor.BLUE : color;
            }
            @Override
            public String getPrefix() {
                return prefix;
            }
        };
    }

    public static ChatColor getSecondaryColor(Element element) {
        if (element == SPIRIT)
            return ChatColor.BLUE;
        else if (element == LIGHT_SPIRIT)
            return ChatColor.WHITE;
        else
            return ChatColor.DARK_GRAY;

    }
}
