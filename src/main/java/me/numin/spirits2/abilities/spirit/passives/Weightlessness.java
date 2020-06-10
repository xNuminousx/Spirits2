package me.numin.spirits2.abilities.spirit.passives;

import com.projectkorra.projectkorra.ability.PassiveAbility;
import me.numin.spirits2.abilities.SpiritAbility;
import org.bukkit.Location;
import org.bukkit.entity.Player;

public class Weightlessness extends SpiritAbility implements PassiveAbility {

    public Weightlessness(Player player) {
        super(player);
    }

    @Override
    public void progress() {

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
        return 0;
    }

    @Override
    public String getName() {
        return "Weightlessness";
    }

    @Override
    public String getDescription() {
        return "Desc";
    }

    @Override
    public String getInstructions() {
        return "Inst";
    }

    @Override
    public Location getLocation() {
        return null;
    }

    @Override
    public boolean isInstantiable() {
        return false;
    }

    @Override
    public boolean isProgressable() {
        return false;
    }
}
