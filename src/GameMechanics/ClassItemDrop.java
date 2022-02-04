package GameMechanics;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.inventory.ItemStack;

public class ClassItemDrop implements Listener {

    @EventHandler
    public void onClassItemDrop(PlayerDropItemEvent e) {
        Player p = e.getPlayer();

        ItemStack item = e.getItemDrop().getItemStack();
        if (item == null) return;
        if(!item.hasItemMeta()) return;
        if (item.getItemMeta().hasLore()) {
            for (String lore : item.getItemMeta().getLore()) {
                if (lore.contains(ChatColor.DARK_RED + "> Class Item <")) {
                    e.setCancelled(true);
                }
            }
        }
    }

    @EventHandler
    public void onDrop(InventoryClickEvent e) {
        ItemStack item = e.getCurrentItem();
        if (item == null) return;
        if(!item.hasItemMeta()) return;
        if (item.getItemMeta().hasLore()) {
            for (String lore : item.getItemMeta().getLore()) {
                if (lore.contains(ChatColor.DARK_RED + "> Class Item <")) {
                    e.setCancelled(true);
                }
            }
        }

    }
}
