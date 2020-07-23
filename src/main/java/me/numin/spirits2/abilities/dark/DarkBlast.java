package me.numin.spirits2.abilities.dark;

import com.projectkorra.projectkorra.GeneralMethods;
import com.projectkorra.projectkorra.ability.AddonAbility;
import com.projectkorra.projectkorra.util.DamageHandler;
import me.numin.spirits2.Spirits2;
import me.numin.spirits2.abilities.DarkAbility;
import me.numin.spirits2.enumerations.AbilityType;
import me.numin.spirits2.enumerations.ActivationType;
import me.numin.spirits2.enumerations.SpiritType;
import me.numin.spirits2.utils.*;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;

public class DarkBlast extends DarkAbility implements AddonAbility {

    private ActivationType activationType;
    private Entity target;
    private Location blastLocation;
    private Location origin;
    private Particle.DustOptions purple;
    private Particle.DustOptions black;
    private RemovalPolicy removalPolicy;
    private Vector direction;

    private boolean canBurst;
    private boolean canHeal;
    private boolean controllable;
    private boolean hasReached;
    private double damage;
    private double radius;
    private double range;
    private int potionDuration;
    private int potionPower;
    private long cooldown;
    private long maxSelectionTime;
    private long startTime;

    public DarkBlast(Player player, ActivationType activationType) {
        super(player);
        this.activationType = activationType;

        if (!bPlayer.canBend(this))
            return;

        if (activationType == ActivationType.SHIFT && hasAbility(player, DarkBlast.class)) {
            DarkBlast darkBlast = getAbility(player, DarkBlast.class);
            if (darkBlast.getTarget() != null) {
                Entity targetedEntity = GeneralMethods.getTargetedEntity(player, darkBlast.getRange());

                if (targetedEntity == null || !targetedEntity.equals(darkBlast.getTarget()))
                    return;

                darkBlast.canHeal = true;
            }
        } else {
            startTime = System.currentTimeMillis();
            setFields();
            start();
        }
    }

    public void setFields() {
        cooldown = 0;
        controllable = false;
        damage = 3;
        radius = 2;
        range = 10;
        maxSelectionTime = 5000;
        potionDuration = 3;
        potionPower = 1;

        blastLocation = player.getEyeLocation().add(0, 0, 0);
        direction = player.getLocation().getDirection();
        origin = player.getLocation();
        removalPolicy = new RemovalPolicy(this, player);
        purple = new Particle.DustOptions(Color.fromRGB(150, 0, 216), 1);
        black = new Particle.DustOptions(Color.fromRGB(0, 0, 0), 1);
    }

    @Override
    public void progress() {
        if (removalPolicy.shouldRemove()) {
            remove();
            return;
        }

        showSelectedTarget();

        if (activationType == ActivationType.CLICK) {
            shootBlast(false);
            for (Entity entity : GeneralMethods.getEntitiesAroundPoint(blastLocation, radius)) {
                if (entity instanceof LivingEntity && !entity.getUniqueId().equals(player.getUniqueId()) && !(entity instanceof ArmorStand)) {
                    DamageHandler.damageEntity(entity, damage, this);
                    player.getWorld().spawnParticle(Particle.DRAGON_BREATH, target.getLocation().add(0, 1, 0), 10, 0, 0, 0, 0.2);
                    remove();
                }
            }
        } else {
            if (target == null) {
                shootBlast(true);
                for (Entity entity : GeneralMethods.getEntitiesAroundPoint(blastLocation, radius)) {
                    if (entity instanceof LivingEntity && !entity.getUniqueId().equals(player.getUniqueId()) && !(entity instanceof ArmorStand))
                        target = entity;
                }
            } else {
                if (player.getLocation().distance(target.getLocation()) > range || (System.currentTimeMillis() - startTime > maxSelectionTime && !canHeal))
                    remove();
            }
        }

        if (canHeal) {
            if (!hasReached) {
                blastLocation = new LocationUtils(player.getEyeLocation(), target.getLocation().add(0, 1, 0)).advanceToPoint(0.3);

                player.getWorld().spawnParticle(Particle.REDSTONE, blastLocation, 2, 0.1, 0.1, 0.1, purple);

                if (player.getLocation().distance(target.getLocation()) > range ||
                        origin.distance(target.getLocation()) > range ||
                        GeneralMethods.isSolid(blastLocation.getBlock()) ||
                        blastLocation.getBlock().isLiquid() ||
                        !player.isSneaking()) {
                    remove();
                    return;
                }

                for (Entity entity : GeneralMethods.getEntitiesAroundPoint(blastLocation, 1)) {
                    if (target.getUniqueId().equals(entity.getUniqueId()))
                        hasReached = true;
                }
            } else {
                LivingEntity livingEntity = (LivingEntity) target;
                livingEntity.addPotionEffect(new PotionEffect(PotionEffectType.POISON,
                        20 * this.potionDuration, this.potionPower, false, true, false));
                remove();
            }
        }
    }

    public void shootBlast(boolean isHealing) {
        if (controllable)
            direction = player.getLocation().getDirection();

        blastLocation = new LocationUtils(blastLocation, direction).advanceToDirection(1);
        player.getWorld().spawnParticle(Particle.TOWN_AURA, blastLocation, 10, 0.1, 0.1, 0.1, 1);
        player.spawnParticle(Particle.REDSTONE, blastLocation, 2, 0.2, 0.2, 0.2, black);

        if (isHealing)
            player.spawnParticle(Particle.REDSTONE, blastLocation, 2, 0.2, 0.2, 0.2, purple);

        if (canBurst) {
            canBurst = false;
            player.getWorld().spawnParticle(Particle.DRAGON_BREATH, blastLocation, 10, 0, 0, 0, 0.1);
        }

        if (origin.distance(blastLocation) > range || GeneralMethods.isSolid(blastLocation.getBlock()) || blastLocation.getBlock().isLiquid()) {
            remove();
        }
    }

    public void showSelectedTarget() {
        if (target != null)
            player.getWorld().spawnParticle(
                    Particle.TOWN_AURA, target.getLocation().add(0, 1, 0),
                    10, 0.5, 1, 0.5, 0);
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
        return "DarkBlast";
    }

    @Override
    public Location getLocation() {
        return blastLocation;
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
        return StringUtils.formatDescription(AbilityType.OFFENSE, SpiritType.DARK, "Shoot a blast of chaos.");
    }

    @Override
    public String getInstructions() {
        return StringUtils.getSpiritColor(SpiritType.DARK) + "Left-click to shoot.";
    }

    public double getRange() {
        return range;
    }

    public Entity getTarget() {
        return target;
    }
}
