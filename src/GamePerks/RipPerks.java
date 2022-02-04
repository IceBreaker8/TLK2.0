package GamePerks;

import org.bukkit.ChatColor;
import org.bukkit.Effect;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.entity.Entity;

public class RipPerks extends Perks {
    public RipPerks() {
        perk = ChatColor.DARK_RED + "" + ChatColor.BOLD + "RIP";
        className = "RipPerks";
    }

    @Override
    public void activatePerk(Entity p) {
        p.getWorld().spawnParticle(Particle.FLAME, p.getLocation(), 100, 0.8F, 0.8F, 0.8F, 0.1);
        p.getWorld().spawnParticle(Particle.EXPLOSION_LARGE, p.getLocation(), 40, 0.8F, 0.8F, 0.8F, 0.1);
        p.getWorld().playSound(p.getLocation(), Sound.ENTITY_GENERIC_EXPLODE, 5, 1);
    }
}
