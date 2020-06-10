package me.numin.spirits2.abilities.dark.combos;

import com.projectkorra.projectkorra.ability.AddonAbility;
import com.projectkorra.projectkorra.ability.ComboAbility;
import com.projectkorra.projectkorra.ability.util.ComboManager;
import me.numin.spirits2.Spirits2;
import me.numin.spirits2.abilities.DarkAbility;
import me.numin.spirits2.enumerations.AbilityType;
import me.numin.spirits2.utils.RemovalPolicy;
import me.numin.spirits2.enumerations.SpiritType;
import me.numin.spirits2.utils.StringUtils;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.ArrayList;

public class Infest extends DarkAbility implements AddonAbility, ComboAbility {

    private RemovalPolicy removalPolicy;

    private long cooldown;

    public Infest(Player player) {
        super(player);

        if (!bPlayer.canBend(this))
            return;

        setFields();
        start();
    }

    public void setFields() {
        cooldown = 0;

        removalPolicy = new RemovalPolicy(this, player);
    }

    @Override
    public void progress() {
        if (removalPolicy.shouldRemove()) {
            remove();
            return;
        }
    }

    @Override
    public boolean isSneakAbility() {
        return false;
    }

    @Override
    public boolean isHarmlessAbility() {
        return false;
    }

    @Override
    public boolean isIgniteAbility() {
        return false;
    }

    @Override
    public boolean isExplosiveAbility() {
        return false;
    }

    @Override
    public long getCooldown() {
        return cooldown;
    }

    @Override
    public String getName() {
        return "Infest";
    }

    @Override
    public Location getLocation() {
        return null;
    }

    @Override
    public void load() {

    }

    @Override
    public void stop() {

    }

    @Override
    public String getAuthor() {
        return StringUtils.getSpiritColor(SpiritType.DARK) + "" + Spirits2.getInstance().getDescription().getAuthors();
    }

    @Override
    public String getVersion() {
        return StringUtils.getSpiritColor(SpiritType.DARK) + Spirits2.getInstance().getDescription().getVersion();
    }

    @Override
    public String getDescription() {
        return StringUtils.formatDescription(AbilityType.COMBO, SpiritType.DARK, "Create a ring of chaos.");
    }

    @Override
    public String getInstructions() {
        return StringUtils.getSpiritColor(SpiritType.DARK) + "<Combo execution>";
    }

    @Override
    public Object createNewComboInstance(Player player) {
        return new Infest(player);
    }

    @Override
    public ArrayList<ComboManager.AbilityInformation> getCombination() {
        return null;
    }
}
