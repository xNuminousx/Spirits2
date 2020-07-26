package me.numin.spirits2.abilities.spirit.combos;

import com.projectkorra.projectkorra.ability.AddonAbility;
import com.projectkorra.projectkorra.ability.ComboAbility;
import com.projectkorra.projectkorra.ability.CoreAbility;
import com.projectkorra.projectkorra.ability.util.ComboManager;
import com.projectkorra.projectkorra.util.ClickType;
import me.numin.spirits2.Spirits2;
import me.numin.spirits2.abilities.SpiritAbility;
import me.numin.spirits2.enumerations.AbilityType;
import me.numin.spirits2.enumerations.SpiritType;
import me.numin.spirits2.utils.ParticleUtils;
import me.numin.spirits2.utils.RemovalPolicy;
import me.numin.spirits2.utils.StringUtils;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.entity.Player;
import sun.security.provider.ConfigFile;

import java.util.ArrayList;
import java.util.Random;

public class Levitation  extends SpiritAbility implements AddonAbility, ComboAbility {

    private Location origin;
    private RemovalPolicy removalPolicy;

    private boolean applyLevitationCD;
    private boolean applyPhaseCD;
    private boolean doDynamicCooldowns;
    private boolean wasFlying;
    private double allowedHealthLoss;
    private double initialHealth;
    private double levitationMultiplier;
    private double phaseMultiplier;
    private double range;
    private long cooldown;
    private long duration;
    private long time;

    public Levitation(Player player) {
        super(player);

        if (!bPlayer.canBendIgnoreBinds(this) || CoreAbility.hasAbility(player, Levitation.class))
            return;

        player.teleport(player.getLocation().add(0, 0.3, 0));
        setFields();
        start();
    }

    public void setFields() {
        cooldown = Spirits2.getInstance().getConfig().getLong("Abilities.Neutral.Combo.Levitation.Cooldown");
        duration = Spirits2.getInstance().getConfig().getLong("Abilities.Neutral.Combo.Levitation.Duration");
        range = Spirits2.getInstance().getConfig().getDouble("Abilities.Neutral.Combo.Levitation.Range");
        allowedHealthLoss = Spirits2.getInstance().getConfig().getDouble("Abilities.Neutral.Combo.Levitation.AllowedHealthLoss");
        doDynamicCooldowns = Spirits2.getInstance().getConfig().getBoolean("Abilities.Neutral.Combo.Levitation.DynamicCooldowns.Enabled");
        applyLevitationCD = Spirits2.getInstance().getConfig().getBoolean("Abilities.Neutral.Combo.Levitation.DynamicCooldowns.DoLevitation");
        levitationMultiplier = Spirits2.getInstance().getConfig().getDouble("Abilities.Neutral.Combo.Levitation.DynamicCooldowns.LevitationMultiplier");
        applyPhaseCD = Spirits2.getInstance().getConfig().getBoolean("Abilities.Neutral.Combo.Levitation.DynamicCooldowns.DoPhase");
        phaseMultiplier = Spirits2.getInstance().getConfig().getDouble("Abilities.Neutral.Combo.Levitation.DynamicCooldowns.PhaseMultiplier");

        initialHealth = player.getHealth();
        origin = player.getLocation();
        removalPolicy = new RemovalPolicy(this, player);
        time = System.currentTimeMillis();
        wasFlying = player.isFlying();
    }

    @Override
    public void progress() {
        if (removalPolicy.shouldRemove()) {
            remove();
            return;
        }

        if (origin.distance(player.getLocation()) > range || player.getHealth() < (initialHealth - allowedHealthLoss) || System.currentTimeMillis() > time + duration) {
            remove();
            return;
        }

        player.setFlying(true);
        playEffects();
    }

    private void playEffects() {
        Location location = player.getEyeLocation();
        player.getWorld().spawnParticle(Particle.DRAGON_BREATH, location, 1, 0.2, 0.6, 0.2, 0.01);

        if (new Random().nextInt(10) == 1)
            ParticleUtils.playSpiritParticles(player, location, 3, 0.4, 0.6, 0.4, 0);
    }

    @Override
    public void remove() {
        player.setFlying(wasFlying);

        if (applyPhaseCD && doDynamicCooldowns) {
            duration = System.currentTimeMillis() - time;
            long phaseCooldown = (long) (duration * phaseMultiplier);
            bPlayer.addCooldown("Phase", phaseCooldown);
        }

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
        if (!doDynamicCooldowns) {
            return cooldown;
        } else {
            duration = System.currentTimeMillis() - time;
            if (applyLevitationCD)
                return (long) (duration * levitationMultiplier);
            else return cooldown;
        }
    }

    @Override
    public String getName() {
        return "Levitation";
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
                Spirits2.getInstance().getConfig().getString("Abilities.Neutral.Combo.Levitation.Description"));
    }

    public String getInstructions() {
        return StringUtils.getSpiritColor(SpiritType.NEUTRAL) +
                Spirits2.getInstance().getConfig().getString("Abilities.Neutral.Combo.Levitation.Instructions");
    }

    @Override
    public Object createNewComboInstance(Player player) {
        return new Levitation(player);
    }

    @Override
    public ArrayList<ComboManager.AbilityInformation> getCombination() {
        ArrayList<ComboManager.AbilityInformation> combo = new ArrayList<>();
        combo.add(new ComboManager.AbilityInformation("Dash", ClickType.SHIFT_DOWN));
        combo.add(new ComboManager.AbilityInformation("Dash", ClickType.SHIFT_UP));
        return combo;
    }

    @Override
    public boolean isEnabled() {
        return Spirits2.getInstance().getConfig().getBoolean("Abilities.Neutral.Combo.Levitation.Enabled");
    }
}
