package me.numin.spirits2.utils;

import com.projectkorra.projectkorra.GeneralMethods;
import com.projectkorra.projectkorra.ability.Ability;
import com.projectkorra.projectkorra.ability.CoreAbility;
import org.bukkit.World;
import org.bukkit.entity.Player;

public class RemovalPolicy {

    private Ability ability;
    private Player player;
    private World world;

    private boolean mustSneak;

    public RemovalPolicy(Ability ability, Player player) {
        this.ability = ability;
        this.player = player;
        this.world = player.getWorld();
    }

    public RemovalPolicy(Ability ability, Player player, boolean mustSneak) {
        this.ability = ability;
        this.player = player;
        this.mustSneak = mustSneak;
        this.world = player.getWorld();
    }

    public boolean shouldRemove() {
        if (mustSneak)
            return !player.isSneaking();

        return !player.isOnline() || player.isDead() || player.getWorld() != world || GeneralMethods.isRegionProtectedFromBuild(player, player.getLocation()) ||
                ability == null || !CoreAbility.hasAbility(player, CoreAbility.getAbility(ability.getName()).getClass()) ||
                GeneralMethods.isRegionProtectedFromBuild(ability, ability.getLocation());
    }
}
