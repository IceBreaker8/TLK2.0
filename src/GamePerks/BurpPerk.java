package GamePerks;

import Main.Main;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Sound;
import org.bukkit.entity.Entity;
import org.bukkit.scheduler.BukkitRunnable;

public class BurpPerk extends Perks {
    public BurpPerk() {
        perk = ChatColor.WHITE + "" + ChatColor.BOLD + "Burp";
        className = "BurpPerk";

    }

    @Override
    public void activatePerk(Entity p) {
        new BukkitRunnable() {
            @Override
            public void run() {
                p.getWorld().playSound(p.getLocation(), Sound.ENTITY_PLAYER_BURP, 5, 1);

            }
        }.runTaskLater(Main.plugin, 4);
    }
}
