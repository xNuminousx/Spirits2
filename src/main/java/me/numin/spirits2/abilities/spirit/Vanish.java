package me.numin.spirits2.abilities.spirit;

import com.projectkorra.projectkorra.ability.AddonAbility;
import me.numin.spirits2.Spirits2;
import me.numin.spirits2.abilities.SpiritAbility;
import me.numin.spirits2.enumerations.AbilityType;
import me.numin.spirits2.utils.RemovalPolicy;
import me.numin.spirits2.enumerations.SpiritType;
import me.numin.spirits2.utils.StringUtils;
import org.bukkit.Location;
import org.bukkit.entity.Player;

public class Vanish extends SpiritAbility implements AddonAbility {

    private RemovalPolicy removalPolicy;

    private long cooldown;

    public Vanish(Player player) {
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
        return "Vanish";
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
        return StringUtils.getSpiritColor(SpiritType.NEUTRAL) + "" + Spirits2.getInstance().getDescription().getAuthors();
    }

    @Override
    public String getVersion() {
        return StringUtils.getSpiritColor(SpiritType.NEUTRAL) + Spirits2.getInstance().getDescription().getVersion();
    }

    @Override
    public String getDescription() {
        return StringUtils.formatDescription(AbilityType.MOBILITY, SpiritType.NEUTRAL, "Disappear and reappear somewhere else.");
    }

    @Override
    public String getInstructions() {
        return StringUtils.getSpiritColor(SpiritType.NEUTRAL) + "Hold shift and teleport.";
    }
}
