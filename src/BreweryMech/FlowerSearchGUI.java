package BreweryMech;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class FlowerSearchGUI implements Listener {
    Flowers f = new Flowers();

    public Boolean checkFlowerType(ItemStack flower) {
        String s = flower.getItemMeta().getDisplayName();
        if (s.equalsIgnoreCase(f.getAthorianSnapdragon().getItemMeta().getDisplayName())) {
            return true;
        }
        if (s.equalsIgnoreCase(f.getFireflax().getItemMeta().getDisplayName())) {
            return true;
        }
        if (s.equalsIgnoreCase(f.getHighlandBovenir().getItemMeta().getDisplayName())) {
            return true;
        }
        if (s.equalsIgnoreCase(f.getVïrgilsûl().getItemMeta().getDisplayName())) {
            return true;
        }
        return false;
    }

    public void searchForFlowers(Player p) {
        Inventory flowerGUI = Bukkit.getServer().createInventory(null, 9 * 5,
                ChatColor.YELLOW + "" + ChatColor.BOLD + "Flowers");
        for (ItemStack item : p.getInventory().getContents()) {
            if (item == null) continue;
            if (!item.hasItemMeta()) continue;
            if (!item.getItemMeta().hasDisplayName()) continue;
            if (checkFlowerType(item)) {
                flowerGUI.addItem(item);
            }
        }
        p.openInventory(flowerGUI);
    }

    @EventHandler
    public void onInvClick(InventoryClickEvent e) {
        Player p = (Player) e.getWhoClicked();
        if (!e.getInventory().getTitle().contains("Flowers")) {
            return;
        }
        e.setCancelled(true);
        if (e.getCurrentItem() == null)
            return;
        if (!e.getCurrentItem().hasItemMeta())
            return;
        if (!e.getCurrentItem().getItemMeta().hasDisplayName())
            return;
        p.playSound(p.getLocation(), Sound.BLOCK_GRASS_BREAK, 5, 1);
        BreweryGUI b = new BreweryGUI();
        e.getCurrentItem().setAmount(1);
        b.displayBrewGUI(p, e.getCurrentItem());
    }
}
