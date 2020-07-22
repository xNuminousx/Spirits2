package me.numin.spirits2.utils;

import com.projectkorra.projectkorra.BendingPlayer;
import me.numin.spirits2.enumerations.SpiritType;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

public class ParticleUtils {

    public static void animateVanish(Entity entity) {
        Location location = entity.getLocation().add(0, 1, 0);

        if (entity instanceof Player) {
            Player player = (Player)entity;
            BendingPlayer bPlayer = BendingPlayer.getBendingPlayer(player);

            if (bPlayer.hasElement(SpiritElement.SPIRIT))
                playSpiritParticles(player, player.getLocation(), 2, 0.5, 0.5, 0.5, 0);
        }

        entity.getWorld().playSound(location, Sound.ENTITY_ILLUSIONER_PREPARE_BLINDNESS, 0.3F, -1);
        entity.getWorld().spawnParticle(Particle.DRAGON_BREATH, location, 20, 0, 0, 0, 0.09);
        entity.getWorld().spawnParticle(Particle.PORTAL, location, 30, 0, 0, 0, 2);
    }

    public static void playSpiritParticles(SpiritType spiritType, Location location, int amount, double x, double y, double z, double speed) {
        if (spiritType == SpiritType.NEUTRAL) {
            Particle.DustOptions teal = new Particle.DustOptions(Color.fromRGB(0, 176, 180), 1);
            location.getWorld().spawnParticle(Particle.CRIT_MAGIC, location, amount, x, y, z, speed);
            location.getWorld().spawnParticle(Particle.REDSTONE, location, amount, x, y, z, teal);
        } else if (spiritType == SpiritType.LIGHT) {
            Particle.DustOptions white = new Particle.DustOptions(Color.fromRGB(255, 255, 255), 1);
            location.getWorld().spawnParticle(Particle.SPELL_INSTANT, location, amount, x, y, z, speed);
            location.getWorld().spawnParticle(Particle.REDSTONE, location, amount, x, y, z, white);
        } else if (spiritType == SpiritType.DARK) {
            Particle.DustOptions black = new Particle.DustOptions(Color.fromRGB(0, 0, 0), 1);
            location.getWorld().spawnParticle(Particle.SPELL_WITCH, location, amount, x, y, z, speed);
            location.getWorld().spawnParticle(Particle.REDSTONE, location, amount, x, y, z, black);
        }
    }

    public static void playSpiritParticles(Player player, Location location, int amount, double x, double y, double z, double speed) {
        SpiritType spiritType = SpiritType.getSpiritType(player);

        if (spiritType == null)
            return;

        playSpiritParticles(spiritType, location, amount, x, y, z,speed);
    }
}
