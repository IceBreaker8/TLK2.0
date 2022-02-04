package BossesSpawners;

import org.bukkit.ChatColor;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;

public class MobCustomNameChanger implements Listener {
    @EventHandler
    public void onCreatureSpawn(CreatureSpawnEvent e) {
        Entity entity = e.getEntity();
        if (entity instanceof Rabbit) {
            entity.setCustomName(ChatColor.WHITE + "" + ChatColor.BOLD + "Wild Rabbit");
        }
        if (entity instanceof Chicken) {
            entity.setCustomName(ChatColor.YELLOW + "" + ChatColor.BOLD + "Chikken");
        }
        if (entity instanceof MagmaCube) {
            entity.setCustomName(ChatColor.DARK_RED + "" + ChatColor.BOLD + "Magmar");
        }
        if (entity instanceof Golem) {
            entity.setCustomName(ChatColor.DARK_GRAY + "" + ChatColor.BOLD + "Golem");
        }
        if (entity instanceof Skeleton) {
            entity.setCustomName(ChatColor.GOLD + "" + ChatColor.BOLD + "The Last Knight");
        }

    }
}
