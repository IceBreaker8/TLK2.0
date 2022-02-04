package GamePerks;

import Main.Main;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Sound;
import org.bukkit.entity.Entity;
import org.bukkit.scheduler.BukkitRunnable;

public class WolfPerk extends Perks {
    public WolfPerk() {
        perk = ChatColor.AQUA + "" + ChatColor.BOLD + "Wolf Howl";
        className = "WolfPerk";
    }

    @Override
    public void activatePerk(Entity p) {
        new BukkitRunnable() {
            @Override
            public void run() {
                p.getWorld().playSound(p.getLocation(), Sound.ENTITY_WOLF_HOWL, 5, 1);

            }
        }.runTaskLater(Main.plugin, 4);

    }
}
