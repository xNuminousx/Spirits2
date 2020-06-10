package me.numin.spirits2.abilities.spirit;

import com.projectkorra.projectkorra.GeneralMethods;
import com.projectkorra.projectkorra.ability.AddonAbility;
import com.projectkorra.projectkorra.util.DamageHandler;
import me.numin.spirits2.Spirits2;
import me.numin.spirits2.enumerations.AbilityType;
import me.numin.spirits2.enumerations.SpiritType;
import me.numin.spirits2.utils.*;
import me.numin.spirits2.abilities.SpiritAbility;
import org.bukkit.*;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class Possess extends SpiritAbility implements AddonAbility {

    private ArmorStand armorStand;
    private Particle.DustOptions purple;
    private Entity target;
    private GameMode gameMode;
    private Location essence;
    private LocationUtils locationUtils;
    private RemovalPolicy removalPolicy;

    private boolean canPlayEssence;
    private boolean hasStarted;
    private boolean wasFlying;
    private double damage;
    private double range;
    private long cooldown;
    private long duration;
    private long time;

    public Possess(Player player) {
        super(player);

        if (!bPlayer.canBend(this))
            return;

        setFields();

        target = GeneralMethods.getTargetedEntity(player, range);
        if (target != null) {
            armorStand = initializeArmorStand();
            gameMode = player.getGameMode();
            locationUtils = new LocationUtils(player.getLocation(), target.getLocation());
            purple = new Particle.DustOptions(Color.fromRGB(130, 0, 193), 1);
            removalPolicy = new RemovalPolicy(this, player);
            time = System.currentTimeMillis();
            wasFlying = player.isFlying();

            ParticleUtils.animateVanish(player);

            player.setGameMode(GameMode.SPECTATOR);
            player.setSpectatorTarget(armorStand);
            start();
        }
    }

    public void setFields() {
        cooldown = 0;
        damage = 3;
        duration = 1000;
        range = 20;
    }

    @Override
    public void progress() {
        if (removalPolicy.shouldRemove()) {
            remove();
            return;
        }
        if (hasStarted && player.isSneaking()) {
            remove();
            return;
        }
        possessTarget();
    }

    private void possessTarget() {
        hasStarted = true;
        Location targetLocation = target.getLocation().add(0, 1, 0);

        if (System.currentTimeMillis() - time > duration) {
            DamageHandler.damageEntity(target, damage, this);
            target.getWorld().spawnParticle(Particle.CRIT, targetLocation, 5, 0.3, 1, 0.3, 0);
            remove();
        } else {
            playTargetEffects();
            if (canPlayEssence)
                playEssence();
            else
                target.getWorld().spawnParticle(Particle.DRAGON_BREATH, target.getLocation(), 1, 0.3, 1, 0.3, 0.02);
        }
    }

    private ArmorStand initializeArmorStand() {
        ArmorStand armorStand = player.getWorld().spawn(player.getLocation(), ArmorStand.class);
        armorStand.setVisible(false);
        armorStand.setGravity(false);
        armorStand.setCollidable(false);
        return armorStand;
    }

    private void playEssence() {
        essence = locationUtils.advanceToPoint(0.9);
        armorStand.teleport(essence);

        if (Math.random() < 0.05) {
            playSound();
            ParticleUtils.playSpiritParticles(player, essence, 1, 0.5, 0.5, 0.5, 0);
        }

        player.getWorld().spawnParticle(Particle.DRAGON_BREATH, essence, 1, 0.2, 0.2, 0.2, 0.02);
        player.getWorld().spawnParticle(Particle.PORTAL, essence, 1, 0, 0, 0, 1);
        player.getWorld().spawnParticle(Particle.REDSTONE, essence, 1, 0, 0, 0, 1, this.purple);

        for (Entity entity : GeneralMethods.getEntitiesAroundPoint(essence, 1.5)) {
            if (entity.equals(target)) {
                canPlayEssence = false;
                player.setSpectatorTarget(target);
                armorStand.remove();
                break;
            }
        }
    }

    public void playSound() {
        player.getWorld().playSound(target.getLocation(), Sound.ENTITY_EVOKER_CAST_SPELL, 0.1F, 0);
    }

    public void playTargetEffects() {
        if (target instanceof LivingEntity) {
            LivingEntity livingEntity = (LivingEntity) target;
            livingEntity.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 120, 2), true);
            livingEntity.addPotionEffect(new PotionEffect(PotionEffectType.CONFUSION, 100, 2), true);
        }

        if (Math.random() < 0.05) {
            playSound();
            if (essence != null)
                ParticleUtils.playSpiritParticles(player, essence, 1, 0.4, 1, 0.4, 0);
        }
    }

    @Override
    public void remove() {
        if (player.getSpectatorTarget() != null)
            player.setSpectatorTarget(null);

        player.setGameMode(gameMode);
        player.setFlying(wasFlying);

        if (armorStand != null)
            armorStand.remove();

        if (canPlayEssence)
            player.teleport(player.getLocation().add(0, 2, 0));

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
        return "Possess";
    }

    @Override
    public Location getLocation() {
        return essence != null ? essence : player.getLocation();
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
        return StringUtils.formatDescription(AbilityType.MOBILITY, SpiritType.NEUTRAL, "Jump inside the body of an entity.");
    }

    @Override
    public String getInstructions() {
        return StringUtils.getSpiritColor(SpiritType.NEUTRAL) + "Hold shift.";
    }
}
