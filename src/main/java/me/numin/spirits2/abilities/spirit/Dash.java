package me.numin.spirits2.abilities.spirit;

import com.projectkorra.projectkorra.ability.AddonAbility;
import me.numin.spirits2.Spirits2;
import me.numin.spirits2.abilities.SpiritAbility;
import me.numin.spirits2.enumerations.AbilityType;
import me.numin.spirits2.enumerations.SpiritType;
import me.numin.spirits2.utils.*;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

public class Dash extends SpiritAbility implements AddonAbility {

    private Location location;
    private RemovalPolicy removalPolicy;
    private Vector direction;

    private long cooldown;
    private long push;

    public Dash(Player player) {
        super(player);

        if (!bPlayer.canBend(this))
            return;

        setFields();
        start();
    }

    public void setFields() {
        cooldown = Spirits2.getInstance().getConfig().getLong("Abilities.Neutral.Dash.Cooldown");
        push = Spirits2.getInstance().getConfig().getLong("Abilities.Neutral.Dash.Push");
        location = player.getLocation();
        removalPolicy = new RemovalPolicy(this, player);
        direction = location.getDirection();
    }

    @Override
    public void progress() {
        if (removalPolicy.shouldRemove()) {
            remove();
            return;
        }
        if (bPlayer.isOnCooldown(this))
            return;

        direction.normalize().multiply(push);
        player.setVelocity(direction);
        location.getWorld().playSound(location, Sound.ENTITY_ELDER_GUARDIAN_HURT, 1.5F, 0.5F);
        location.getWorld().playSound(location, Sound.ENTITY_PLAYER_ATTACK_SWEEP, 0.3F, 0.5F);
        ParticleUtils.playSpiritParticles(player, player.getLocation(), 10, 0.5, 0.5, 0.5, 0);
        bPlayer.addCooldown(this);
        remove();
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
        return "Dash";
    }

    @Override
    public Location getLocation() {
        return player.getLocation();
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
        return StringUtils.formatDescription(AbilityType.MOBILITY, SpiritType.NEUTRAL,
                Spirits2.getInstance().getConfig().getString("Abilities.Neutral.Dash.Description"));
    }

    @Override
    public String getInstructions() {
        return StringUtils.getSpiritColor(SpiritType.NEUTRAL) +
                Spirits2.getInstance().getConfig().getString("Abilities.Neutral.Dash.Instructions");
    }

    @Override
    public boolean isEnabled() {
        return Spirits2.getInstance().getConfig().getBoolean("Abilities.Neutral.Dash.Enabled");
    }
}
