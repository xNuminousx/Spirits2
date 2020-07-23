package me.numin.spirits2.abilities.spirit.combos;

import com.projectkorra.projectkorra.ability.AddonAbility;
import com.projectkorra.projectkorra.ability.ComboAbility;
import com.projectkorra.projectkorra.ability.util.ComboManager.AbilityInformation;
import com.projectkorra.projectkorra.util.ClickType;
import me.numin.spirits2.Spirits2;
import me.numin.spirits2.abilities.SpiritAbility;
import me.numin.spirits2.enumerations.AbilityType;
import me.numin.spirits2.enumerations.SpiritType;
import me.numin.spirits2.utils.*;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.entity.Player;

import java.util.ArrayList;

public class Phase extends SpiritAbility implements AddonAbility, ComboAbility {

    private GameMode gameMode;
    private RemovalPolicy removalPolicy;

    private double minHealth;
    private long cooldown;
    private long cooldownMultiplier;
    private long duration;
    private long time;

    public Phase(Player player) {
        super(player);

        if (!bPlayer.canBendIgnoreBinds(this))
            return;

        setFields();
        if (player.getHealth() < minHealth)
            return;

        ParticleUtils.animateVanish(player);
        time = System.currentTimeMillis();
        start();
    }

    public void setFields() {
        cooldown = Spirits2.getInstance().getConfig().getLong("Abilities.Neutral.Combo.Phase.Cooldown");
        cooldownMultiplier = Spirits2.getInstance().getConfig().getLong("Abilities.Neutral.Combo.Phase.CooldownMultiplier");
        duration = Spirits2.getInstance().getConfig().getLong("Abilities.Neutral.Combo.Phase.Duration");
        minHealth = Spirits2.getInstance().getConfig().getDouble("Abilities.Neutral.Combo.Phase.MinimumHealth");

        gameMode = player.getGameMode();
        removalPolicy = new RemovalPolicy(this, player);
    }

    @Override
    public void progress() {
        if (removalPolicy.shouldRemove()) {
            remove();
            return;
        }

        player.setGameMode(GameMode.SPECTATOR);
        player.spawnParticle(Particle.PORTAL, player.getLocation(), 1, 0.3, 0.3, 0.3, 0.03);

        if (System.currentTimeMillis() - time > duration) {
            remove();
        }
    }

    @Override
    public void remove() {
        long flightTime = System.currentTimeMillis() - time;
        cooldown = flightTime * cooldownMultiplier;

        ParticleUtils.animateVanish(player);
        player.setGameMode(gameMode);
        bPlayer.addCooldown(this);

        super.remove();
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
        return "Phase";
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

    public String getDescription() {
        return StringUtils.formatDescription(AbilityType.COMBO, SpiritType.NEUTRAL,
                Spirits2.getInstance().getConfig().getString("Abilities.Neutral.Combo.Phase.Description"));
    }

    public String getInstructions() {
        return StringUtils.getSpiritColor(SpiritType.NEUTRAL) +
                Spirits2.getInstance().getConfig().getString("Abilities.Neutral.Combo.Phase.Instructions");
    }

    @Override
    public Object createNewComboInstance(Player player) {
        return new Phase(player);
    }

    //TODO: Reconsider combo activation.
    @Override
    public ArrayList<AbilityInformation> getCombination() {
        ArrayList<AbilityInformation> combo = new ArrayList<>();
        combo.add(new AbilityInformation("Vanish", ClickType.LEFT_CLICK));
        combo.add(new AbilityInformation("Vanish", ClickType.LEFT_CLICK));
        return combo;
    }

    @Override
    public boolean isEnabled() {
        return Spirits2.getInstance().getConfig().getBoolean("Abilities.Neutral.Combo.Phase.Enabled");
    }
}
