package Teams;

import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class TeamFire implements Listener {
    @EventHandler
    public void onTeamFire(EntityDamageByEntityEvent e) {
        Entity victim = e.getEntity();
        Entity damager = e.getDamager();
        if (victim == null) return;
        if (damager == null) return;
        if (!(victim instanceof Player)) return;
        if (!(damager instanceof Player)) return;
        if (((Player) damager).getInventory().getItemInMainHand().getType().equals(Material.STONE_AXE) ||
                ((Player) damager).getInventory().getItemInMainHand().getType().equals(Material.STONE_SPADE) ||
                ((Player) damager).getInventory().getItemInMainHand().getType().equals(Material.STONE_PICKAXE)
                || ((Player) damager).getInventory().getItemInMainHand().getType().equals(Material.IRON_PICKAXE) ||
                ((Player) damager).getInventory().getItemInMainHand().getType().equals(Material.GOLD_PICKAXE) ||
                ((Player) damager).getInventory().getItemInMainHand().getType().equals(Material.DIAMOND_PICKAXE)) {
            e.setCancelled(true);
            return;
        }
        if (!TeamManager.arentSameTeam((Player) victim, damager)) {
            e.setCancelled(true);
            return;
        }
    }
}
