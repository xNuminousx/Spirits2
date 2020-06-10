package me.numin.spirits2.abilities.light.combos;

import com.projectkorra.projectkorra.ability.AddonAbility;
import com.projectkorra.projectkorra.ability.ComboAbility;
import com.projectkorra.projectkorra.ability.util.ComboManager;
import me.numin.spirits2.Spirits2;
import me.numin.spirits2.abilities.LightAbility;
import me.numin.spirits2.enumerations.AbilityType;
import me.numin.spirits2.utils.RemovalPolicy;
import me.numin.spirits2.enumerations.SpiritType;
import me.numin.spirits2.utils.StringUtils;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.ArrayList;

public class Rejuvenate extends LightAbility implements AddonAbility, ComboAbility {

    private RemovalPolicy removalPolicy;

    private long cooldown;

    public Rejuvenate(Player player) {
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
        return "Rejuvenate";
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
        return StringUtils.getSpiritColor(SpiritType.LIGHT) + "" + Spirits2.getInstance().getDescription().getAuthors();
    }

    @Override
    public String getVersion() {
        return StringUtils.getSpiritColor(SpiritType.LIGHT) + Spirits2.getInstance().getDescription().getVersion();
    }

    @Override
    public String getDescription() {
        return StringUtils.formatDescription(AbilityType.COMBO, SpiritType.LIGHT, "Create a ring of healing.");
    }

    @Override
    public String getInstructions() {
        return StringUtils.getSpiritColor(SpiritType.LIGHT) + "<Combo execution>";
    }

    @Override
    public Object createNewComboInstance(Player player) {
        return new Rejuvenate(player);
    }

    @Override
    public ArrayList<ComboManager.AbilityInformation> getCombination() {
        ArrayList<ComboManager.AbilityInformation> combo = new ArrayList<>();
        return combo;
    }
}
