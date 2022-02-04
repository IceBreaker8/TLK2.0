package GamePerks;

import net.md_5.bungee.api.ChatColor;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.entity.Entity;

public class VillagerDeath extends Perks {
    public VillagerDeath() {
        perk = ChatColor.DARK_AQUA + "" + ChatColor.BOLD + "Villager Death";
        className = "VillagerDeath";
    }

    @Override
    public void activatePerk(Entity p) {
        p.getWorld().spawnParticle(Particle.VILLAGER_ANGRY, p.getLocation().clone().add(0, 1, 0), 40, 0.4F, 0.4F, 0.4F, 0.1);
        p.getWorld().playSound(p.getLocation(), Sound.ENTITY_VILLAGER_DEATH, 5, 1);


    }
}
