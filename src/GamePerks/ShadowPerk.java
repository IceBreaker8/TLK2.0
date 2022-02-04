package GamePerks;

import org.bukkit.ChatColor;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.entity.Entity;

public class ShadowPerk extends Perks {
    public ShadowPerk() {
        perk = ChatColor.DARK_GRAY + "" + ChatColor.BOLD + "Shadow";
        className = "ShadowPerk";
    }

    @Override
    public void activatePerk(Entity p) {
        p.getWorld().playSound(p.getLocation(), Sound.BLOCK_FIRE_EXTINGUISH, 5, 1);
        p.getWorld().spawnParticle(Particle.SMOKE_LARGE, p.getLocation(), 100, 0.8F, 1.2F, 0.8F, 0.1);

    }
}
