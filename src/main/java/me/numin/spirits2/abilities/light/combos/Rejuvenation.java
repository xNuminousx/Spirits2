package me.numin.spirits2.abilities.light.combos;

import com.projectkorra.projectkorra.BendingPlayer;
import com.projectkorra.projectkorra.GeneralMethods;
import com.projectkorra.projectkorra.ability.AddonAbility;
import com.projectkorra.projectkorra.ability.ComboAbility;
import com.projectkorra.projectkorra.ability.util.ComboManager;
import com.projectkorra.projectkorra.util.ClickType;
import com.projectkorra.projectkorra.util.DamageHandler;
import me.numin.spirits2.Spirits2;
import me.numin.spirits2.abilities.LightAbility;
import me.numin.spirits2.enumerations.AbilityType;
import me.numin.spirits2.utils.ParticleUtils;
import me.numin.spirits2.utils.RemovalPolicy;
import me.numin.spirits2.enumerations.SpiritType;
import me.numin.spirits2.utils.SpiritElement;
import me.numin.spirits2.utils.StringUtils;
import org.bukkit.Color;
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

public class Rejuvenation extends LightAbility implements AddonAbility, ComboAbility {

    private Location center, location, loc2, loc3;
    private RemovalPolicy removalPolicy;

    private boolean damageDarkSpirits;
    private boolean damageMonsters;
    private double damage;
    private double radius;
    private double t;
    private int currPoint;
    private int effectInterval;
    private long cooldown;
    private long duration;
    private long time;

    public Rejuvenation(Player player) {
        super(player);

        if (!bPlayer.canBend(this))
            return;

        setFields();
        start();
    }

    public void setFields() {
        cooldown = 0;
        damage = 3;
        damageDarkSpirits = true;
        damageMonsters = true;
        duration = 5000;
        effectInterval = 5;
        radius = 8;

        location = player.getLocation();
        center = location.clone();
        loc2 = location.clone();
        loc3 = location.clone();
        removalPolicy = new RemovalPolicy(this, player);
        time = System.currentTimeMillis();
    }

    @Override
    public void progress() {
        if (removalPolicy.shouldRemove()) {
            remove();
            return;
        }

        if (!bPlayer.canBendIgnoreBindsCooldowns(this)) {
            remove();
            return;
        }
        if (System.currentTimeMillis() > time + duration) {
            remove();
            return;
        }
        spawnCircle();
        grabEntities();
    }

    private void spawnCircle() {
        ParticleUtils.animatePolygon(location, 8, radius, 0.2, Particle.SPELL_INSTANT);
        for (int i = 0; i < 6; i++) {
            currPoint += 360 / 300;
            if (currPoint > 360) {
                currPoint = 0;
            }
            double angle = currPoint * Math.PI / 180;
            double x = radius * Math.cos(angle);
            double z = radius * Math.sin(angle);
            loc2.add(x, 0, z);
            loc2.getWorld().spawnParticle(Particle.END_ROD, loc2, 1, 0, 0, 0, 0);
            loc2.subtract(x, 0, z);

            loc3.add(-x, 0, -z);
            loc3.getWorld().spawnParticle(Particle.END_ROD, loc3, 1, 0, 0, 0, 0);
            loc3.subtract(-x, 0, -z);

        }
        t += Math.PI / 32;
        if (!(t >= Math.PI * 4)) {
            for (double i = 0; i <= Math.PI * 2; i += Math.PI / 1.2) {
                double x = 0.5 * (Math.PI * 4 - t) * Math.cos(t - i);
                double y = 0.4 * t;
                double z = 0.5 * (Math.PI * 4 - t) * Math.sin(t - i);
                location.add(x, y, z);
                ParticleUtils.playSpiritParticles(SpiritType.LIGHT, location, 1, 0, 0, 0, 0);
                player.getWorld().spawnParticle(Particle.REDSTONE, location, 1, 0.1, 0.1, 0.1, 0, new Particle.DustOptions(Color.fromRGB(255, 255, 255), 1));
                location.subtract(x, y, z);
            }
        }
        location.getWorld().spawnParticle(Particle.ENCHANTMENT_TABLE, location, 10, (float)radius / 2, 0.4F, (float)radius / 2, 0);
    }

    private void grabEntities() {
        for (Entity entity : GeneralMethods.getEntitiesAroundPoint(center, radius)) {
            if (entity instanceof LivingEntity) {
                healEntities(entity);
            }
        }
    }

    private void healEntities(Entity entity) {
        if (new Random().nextInt(effectInterval) == 0) {
            if (entity instanceof Player) {
                Player ePlayer = (Player)entity;
                BendingPlayer bEntity = BendingPlayer.getBendingPlayer(ePlayer);
                if (!bEntity.hasSubElement(SpiritElement.DARK_SPIRIT)) {
                    ePlayer.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 120, 0));
                    ePlayer.getWorld().spawnParticle(Particle.HEART, ePlayer.getLocation().add(0, 2, 0), 1, 0, 0, 0, 0);
                } else if (damageDarkSpirits) {
                    DamageHandler.damageEntity(ePlayer, damage, this);
                }
            } else if (entity instanceof Monster && damageMonsters) {
                DamageHandler.damageEntity(entity, damage, this);
            } else if (entity instanceof LivingEntity) {
                LivingEntity le = (LivingEntity)entity;
                le.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 120, 0));
                le.getWorld().spawnParticle(Particle.HEART, le.getLocation().add(0, 2, 0), 1, 0, 0, 0, 0);
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
    public String getName() {
        return "Rejuvenation";
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
        return StringUtils.getSpiritColor(SpiritType.LIGHT) + "" + Spirits2.getInstance().getDescription().getAuthors();
    }

    @Override
    public String getVersion() {
        return StringUtils.getSpiritColor(SpiritType.LIGHT) + Spirits2.getInstance().getDescription().getVersion();
    }

    @Override
    public String getDescription() {
        return StringUtils.formatDescription(AbilityType.COMBO, SpiritType.LIGHT, "Create a ring of healing.");
    }

    @Override
    public String getInstructions() {
        return StringUtils.getSpiritColor(SpiritType.LIGHT) + "<Combo execution>";
    }

    @Override
    public Object createNewComboInstance(Player player) {
        return new Rejuvenation(player);
    }

    //TODO: Reconsider combo activation.
    @Override
    public ArrayList<ComboManager.AbilityInformation> getCombination() {
        ArrayList<ComboManager.AbilityInformation> combo = new ArrayList<>();
        combo.add(new ComboManager.AbilityInformation("Possess", ClickType.LEFT_CLICK));
        return combo;
    }
}
