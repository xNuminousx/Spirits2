package me.numin.spirits2.abilities.spirit;

import com.projectkorra.projectkorra.GeneralMethods;
import com.projectkorra.projectkorra.ability.AddonAbility;
import me.numin.spirits2.Spirits2;
import me.numin.spirits2.abilities.SpiritAbility;
import me.numin.spirits2.enumerations.AbilityType;
import me.numin.spirits2.utils.ParticleUtils;
import me.numin.spirits2.utils.RemovalPolicy;
import me.numin.spirits2.enumerations.SpiritType;
import me.numin.spirits2.utils.StringUtils;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.Random;

public class Vanish extends SpiritAbility implements AddonAbility {

    private Location origin;
    private RemovalPolicy removalPolicy;

    private boolean hasAppliedInvisibility;
    private boolean extinguish;
    private boolean isCharged;
    private double range;
    private int particleFrequency;
    private long chargeTime;
    private long cooldown;
    private long duration;
    private long time;

    public Vanish(Player player) {
        super(player);

        if (!bPlayer.canBend(this))
            return;

        setFields();
        start();
    }

    public void setFields() {
        hasAppliedInvisibility = false;
        time = System.currentTimeMillis();

        chargeTime = Spirits2.getInstance().getConfig().getLong("Abilities.Neutral.Vanish.ChargeTime");
        cooldown = Spirits2.getInstance().getConfig().getLong("Abilities.Neutral.Vanish.Cooldown");
        duration = Spirits2.getInstance().getConfig().getLong("Abilities.Neutral.Vanish.Duration");
        extinguish = Spirits2.getInstance().getConfig().getBoolean("Abilities.Neutral.Vanish.Extinguish");
        particleFrequency = Spirits2.getInstance().getConfig().getInt("Abilities.Neutral.Vanish.ParticleDensity");
        range = Spirits2.getInstance().getConfig().getDouble("Abilities.Neutral.Vanish.Range");

        origin = player.getLocation();

        removalPolicy = new RemovalPolicy(this, player);
    }

    @Override
    public void progress() {
        if (removalPolicy.shouldRemove()) {
            remove();
            return;
        }
        if (!isCharged) {
            if (player.isSneaking()) {
                if (System.currentTimeMillis() > time + chargeTime)
                    isCharged = true;
                else if (new Random().nextInt(particleFrequency) == 0)
                    player.getWorld().spawnParticle(Particle.DRAGON_BREATH, player.getLocation().add(0, 1, 0), 1, 0, 0, 0, 0.09);
            } else
                remove();
        } else {
            if (player.isSneaking()) {
                playAnimation();
                if ((origin.distance(player.getLocation()) > range) || (System.currentTimeMillis() > time + chargeTime + duration)) {
                    player.getWorld().playSound(player.getLocation(), Sound.ENTITY_ENDERMAN_TELEPORT, 0.5F, -1);
                    remove();
                }
            } else {
                Location targetLocation = GeneralMethods.getTargetedLocation(player, range);
                player.teleport(targetLocation);
                player.getWorld().playSound(player.getLocation(), Sound.ENTITY_ENDERMAN_TELEPORT, 0.5F, -1);
                remove();
            }
            if (extinguish)
                player.setFireTicks(-1);
        }
    }

    private void playAnimation() {
        if (new Random().nextInt(particleFrequency) == 0) {
            ParticleUtils.playSpiritParticles(player, player.getLocation().add(0, 1, 0), 1, 0.5, 0.5, 0.5, 0);
        }
        if (!hasAppliedInvisibility) {
            player.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, (int)duration, 2), true);
            player.getWorld().playSound(player.getLocation(), Sound.ENTITY_ENDERMAN_TELEPORT, 0.5F, -1);
            ParticleUtils.animateVanish(player);
            hasAppliedInvisibility = true;
        }
    }

    @Override
    public void remove() {
        if (player.hasPotionEffect(PotionEffectType.INVISIBILITY))
            player.removePotionEffect(PotionEffectType.INVISIBILITY);

        if (isCharged)
            ParticleUtils.animateVanish(player);

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
        return "Vanish";
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
                Spirits2.getInstance().getConfig().getString("Abilities.Neutral.Vanish.Description"));
    }

    @Override
    public String getInstructions() {
        return StringUtils.getSpiritColor(SpiritType.NEUTRAL) +
                Spirits2.getInstance().getConfig().getString("Abilities.Neutral.Vanish.Instructions");
    }

    @Override
    public boolean isEnabled() {
        return Spirits2.getInstance().getConfig().getBoolean("Abilities.Neutral.Vanish.Enabled");
    }
}
