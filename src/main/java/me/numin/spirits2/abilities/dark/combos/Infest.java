package me.numin.spirits2.abilities.dark.combos;

import com.projectkorra.projectkorra.BendingPlayer;
import com.projectkorra.projectkorra.GeneralMethods;
import com.projectkorra.projectkorra.ability.AddonAbility;
import com.projectkorra.projectkorra.ability.ComboAbility;
import com.projectkorra.projectkorra.ability.util.ComboManager;
import com.projectkorra.projectkorra.util.ClickType;
import com.projectkorra.projectkorra.util.DamageHandler;
import me.numin.spirits2.Spirits2;
import me.numin.spirits2.abilities.DarkAbility;
import me.numin.spirits2.enumerations.AbilityType;
import me.numin.spirits2.utils.ParticleUtils;
import me.numin.spirits2.utils.RemovalPolicy;
import me.numin.spirits2.enumerations.SpiritType;
import me.numin.spirits2.utils.SpiritElement;
import me.numin.spirits2.utils.StringUtils;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Monster;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.ArrayList;
import java.util.Random;

public class Infest extends DarkAbility implements AddonAbility, ComboAbility {

    private Location center;
    private Location location;
    private RemovalPolicy removalPolicy;

    private boolean healDarkSpirits;
    private boolean aidMonsters;
    private double damage;
    private int interval;
    private int point;
    private int radius;
    private long cooldown;
    private long duration;
    private long time;

    public Infest(Player player) {
        super(player);

        if (!bPlayer.canBend(this))
            return;

        setFields();
        start();
    }

    public void setFields() {
        cooldown = 0;
        damage = 1.5;
        duration = 10000;
        interval = 10;
        radius = 5;
        healDarkSpirits = true;
        aidMonsters = true;

        location = player.getLocation();
        center = location.clone();
        removalPolicy = new RemovalPolicy(this, player);
        time = System.currentTimeMillis();
    }

    @Override
    public void progress() {
        if (removalPolicy.shouldRemove()) {
            remove();
            return;
        }
        if (System.currentTimeMillis() > time + duration) {
            remove();
            return;
        }

        rotateCircle();
        grabEntities();
    }

    Location location2 = location.clone();
    Location location3 = location.clone();
    private void rotateCircle() {
        ParticleUtils.animatePolygon(location, 8, radius, 0.2, Particle.SPELL_WITCH);

        for (int i = 0; i < 6; i++) {
            point += 360/300;
            if (point > 360) {
                point = 0;
            }
            double angle = point * Math.PI / 180;
            double c = radius * Math.cos(angle);
            double s = radius * Math.sin(angle);
            location2.add(c, 0, s);
            location2.getWorld().spawnParticle(Particle.SMOKE_NORMAL, location2, 1, 0, 0, 0);
            location2.subtract(c, 0, s);

            location3.add(s, 0, c);
            location3.getWorld().spawnParticle(Particle.SMOKE_NORMAL, location3, 1, 0, 0, 0);
            location3.subtract(s, 0, c);
        }

        location.getWorld().spawnParticle(Particle.DRAGON_BREATH, location, 1, (float) radius / 2, 0.4, (float)radius / 2, 0.01);
    }

    private void grabEntities() {
        for (Entity entity : GeneralMethods.getEntitiesAroundPoint(center, radius)) {
            if (entity instanceof LivingEntity) {
                infestEntity((LivingEntity)entity);
            }
        }
    }

    private void infestEntity(LivingEntity entity) {
        Location entityLoc = entity.getLocation().add(0, 1, 0);
        if (new Random().nextInt(interval) == 0) {
            if (entity instanceof Player) {
                Player ePlayer = (Player) entity;
                BendingPlayer bEntity = BendingPlayer.getBendingPlayer(ePlayer);

                if (bEntity != null && bEntity.hasElement(SpiritElement.DARK_SPIRIT)) {
                    if (healDarkSpirits) {
                        ePlayer.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 120, 1));
                        player.spawnParticle(Particle.HEART, player.getLocation().add(0, 1, 0), 1, 0, 0, 0);
                    }
                }
            } else if (entity instanceof Monster && aidMonsters) {
                entity.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 120, 1));
                entity.getWorld().spawnParticle(Particle.VILLAGER_ANGRY, entityLoc, 1, 0, 0, 0);
            } else {
                DamageHandler.damageEntity(entity, damage, this);
                entity.getWorld().spawnParticle(Particle.PORTAL, entityLoc, 5, 0, 0, 0, 1.5);
            }
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
    public double getCollisionRadius() {
        return radius;
    }

    @Override
    public String getName() {
        return "Infest";
    }

    @Override
    public Location getLocation() {
        return center;
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
        return StringUtils.formatDescription(AbilityType.COMBO, SpiritType.DARK, "Create a ring of chaos.");
    }

    @Override
    public String getInstructions() {
        return StringUtils.getSpiritColor(SpiritType.DARK) + "<Combo execution>";
    }

    @Override
    public Object createNewComboInstance(Player player) {
        return new Infest(player);
    }

    @Override
    public ArrayList<ComboManager.AbilityInformation> getCombination() {
        ArrayList<ComboManager.AbilityInformation> combo = new ArrayList<ComboManager.AbilityInformation>();
        combo.add(new ComboManager.AbilityInformation("Intoxicate", ClickType.SHIFT_DOWN));
        combo.add(new ComboManager.AbilityInformation("Intoxicate", ClickType.RIGHT_CLICK_BLOCK));
        combo.add(new ComboManager.AbilityInformation("Intoxicate", ClickType.SHIFT_UP));
        return combo;
    }
}
