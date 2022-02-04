package BreweryMech;

import GameMechanics.WaterWell;
import ItemConstructorClass.ItemConstructor;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.Map;

public class BreweryGUI implements Listener {

    @EventHandler
    public void onBrew(PlayerInteractEvent e) {
        Player p = e.getPlayer();
        if (e.getAction() != Action.RIGHT_CLICK_BLOCK) return;
        if (!e.getClickedBlock().getType().equals(Material.BREWING_STAND)) return;
        e.setCancelled(true);
        displayBrewGUI(p, null);
    }

    public void displayBrewGUI(Player p, ItemStack itemStack) {
        ItemConstructor itC = new ItemConstructor();
        Inventory breweryGUI = Bukkit.getServer().createInventory(null, 9 * 4,
                ChatColor.GREEN + "" + ChatColor.BOLD + "Brewery");
        for (int count = 0; count < 36; count++) {
            ItemStack i = new ItemStack(Material.STAINED_GLASS_PANE, 1, (short) 15);
            breweryGUI.setItem(count, i);
        }
        breweryGUI.setItem(13, itC.MakeItem(ChatColor.GREEN + "" + ChatColor.BOLD + "BREW -->",
                Material.BREWING_STAND_ITEM, null));
        breweryGUI.setItem(10, itemStack);
        breweryGUI.setItem(16, null);
        breweryGUI.setItem(31, itC.MakeItem(ChatColor.AQUA + "" + ChatColor.BOLD + "Flowers Menu",
                Material.FLOWER_POT_ITEM, null));
        p.openInventory(breweryGUI);

    }

    @EventHandler
    public void onInvClick(InventoryClickEvent e) {
        Player p = (Player) e.getWhoClicked();
        if (!e.getInventory().getTitle().contains("Brewery")) {
            return;
        }
        e.setCancelled(true);
        if (e.getCurrentItem() == null)
            return;
        if (!e.getCurrentItem().hasItemMeta())
            return;
        if (!e.getCurrentItem().getItemMeta().hasDisplayName())
            return;
        if (e.getCurrentItem().getItemMeta().getDisplayName().contains("BREW -->")) {
            //TODO GLASS BOTTLE
            if (!p.getInventory().containsAtLeast(new ItemStack(Material.GLASS_BOTTLE), 1)) {
                p.sendMessage(ChatColor.RED + "You need at least one glass bottle to brew this potion!");
                p.playSound(p.getLocation(), Sound.ENTITY_BLAZE_DEATH, 5, 1);
                return;
            }
            if (e.getClickedInventory().getItem(10) == null) {
                p.playSound(p.getLocation(), Sound.BLOCK_ANVIL_PLACE, 5, 1);
                p.sendMessage(ChatColor.RED + "There is no current active flower!");
                return;
            }
            brew(p, e.getInventory());
        }
        if (e.getCurrentItem().getItemMeta().getDisplayName().contains("Flowers Menu")) {
            p.playSound(p.getLocation(), Sound.BLOCK_GRASS_BREAK, 5, 1);
            FlowerSearchGUI f = new FlowerSearchGUI();
            f.searchForFlowers(p);
        }
    }

    private void brew(Player p, Inventory inv) {
        p.playSound(p.getLocation(), Sound.BLOCK_BREWING_STAND_BREW, 5, 1);
        ItemStack item = inv.getItem(10);
        clearFlower(p, inv.getItem(10));
        clearBottle(p);
        inv.setItem(10, null);
        potionConstructor(p, inv, item);

    }

    public void clearFlower(Player p, ItemStack flower) {
        for (ItemStack item : p.getInventory().getContents()) {
            if (item != null) {
                if (item.hasItemMeta()) {
                    if (item.getItemMeta().getDisplayName().contains(flower.getItemMeta().getDisplayName())) {
                        clearItem(p, 1, item);
                        break;
                    }
                }
            }
        }
    }
    public void clearBottle(Player p) {
        for (ItemStack item : p.getInventory().getContents()) {
            if (item != null) {
               if(item.getType().equals(Material.GLASS_BOTTLE)){
                   clearItem(p, 1, item);
                   break;
               }
            }
        }
    }

    private void potionConstructor(Player p, Inventory inv, ItemStack flower) {
        Potions pot = new Potions();
        Flowers f = new Flowers();
        if (flower.equals(f.getHighlandBovenir())) {
            inv.setItem(16, pot.getHighlandBovenirPot());
            p.getInventory().addItem(pot.getHighlandBovenirPot());
            return;
        }
        if (flower.equals(f.getAthorianSnapdragon())) {
            inv.setItem(16, pot.getAthorianSnapdragonPot());
            p.getInventory().addItem(pot.getAthorianSnapdragonPot());
            return;
        }
        if (flower.equals(f.getFireflax())) {
            inv.setItem(16, pot.getFireflaxPot());
            p.getInventory().addItem(pot.getFireflaxPot());
            return;
        }
        if (flower.equals(f.getVïrgilsûl())) {
            inv.setItem(16, pot.getVïrgilsûlPot());
            p.getInventory().addItem(pot.getVïrgilsûlPot());
            return;
        }

    }

    public boolean clearItem(Player player, int count, ItemStack mat) {
        Map<Integer, ? extends ItemStack> ammo = player.getInventory().all(mat);

        int found = 0;
        for (ItemStack stack : ammo.values())
            found += stack.getAmount();
        if (count > found)
            return false;

        for (Integer index : ammo.keySet()) {
            ItemStack stack = ammo.get(index);

            int removed = Math.min(count, stack.getAmount());
            count -= removed;

            if (stack.getAmount() == removed)
                player.getInventory().setItem(index, null);
            else
                stack.setAmount(stack.getAmount() - removed);

            if (count <= 0)
                break;
        }
        player.updateInventory();
        return true;
    }
}
