package Teams;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.HashSet;
import java.util.UUID;

public class TeamChoose implements Listener {

    public static void giveTeamItems(Player p) {
        ItemStack item = new ItemStack(Material.LAPIS_BLOCK, 1);
        ItemMeta im = item.getItemMeta();
        im.setDisplayName(ChatColor.AQUA + "" + ChatColor.BOLD + "Angels Team");
        im.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        item.setItemMeta(im);
        // items 2
        ItemStack item2 = new ItemStack(Material.REDSTONE_BLOCK, 1);
        ItemMeta im2 = item2.getItemMeta();
        im2.setDisplayName(ChatColor.RED + "" + ChatColor.BOLD + "Shamans Team");
        im2.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        item2.setItemMeta(im2);
        // add all items
        p.getInventory().addItem(item);
        p.getInventory().addItem(item2);
    }

    @EventHandler
    public void onClassItemDrop(PlayerDropItemEvent e) {
        Player p = e.getPlayer();

        ItemStack item = e.getItemDrop().getItemStack();
        if (item == null) return;
        if (!item.hasItemMeta()) return;
        if (item.getItemMeta().hasDisplayName()) {
            if (item.getItemMeta().getDisplayName().contains("Shamans Team") ||
                    item.getItemMeta().getDisplayName().contains("Angels Team")) {
                e.setCancelled(true);
                return;
            }
        }
    }

    @EventHandler
    public void onDrop(InventoryClickEvent e) {
        ItemStack item = e.getCurrentItem();
        if (item == null) return;
        if (!item.hasItemMeta()) return;
        if (item.getItemMeta().hasDisplayName()) {
            if (item.getItemMeta().getDisplayName().contains("Shamans Team") ||
                    item.getItemMeta().getDisplayName().contains("Angels Team")) {
                e.setCancelled(true);
                return;
            }
        }

    }

    public static int shamanTeam = 0;
    public static int angelTeam = 0;

    @EventHandler
    public void onRightClickTeams(PlayerInteractEvent e) {
        Player p = e.getPlayer();

        ItemStack item = e.getItem();
        if (item == null)
            return;
        ItemMeta im = e.getItem().getItemMeta();
        if (im == null)
            return;
        Action action = e.getAction();
        ItemStack stack = e.getItem();
        if (stack == null)
            return;
        if (!(action == Action.RIGHT_CLICK_AIR || action == Action.RIGHT_CLICK_BLOCK))
            return;
        if (!stack.getItemMeta().hasDisplayName())
            return;
        if (stack.getItemMeta().getDisplayName().contains("Angels Team")) {
            if (TeamManager.angels.contains(p.getUniqueId())) {
                p.sendMessage(ChatColor.RED + "You are already on this team!");
                return;
            }
            if (angelTeam > shamanTeam) {
                p.sendMessage(ChatColor.RED + "You cannot join a team with more members than the other team!");
                return;
            }
            if (angelTeam >= 5) {
                p.sendMessage(ChatColor.RED + "You cannot join a full team!");
                return;
            }
            p.playSound(p.getLocation(), Sound.BLOCK_NOTE_PLING, 10, 1);
            p.sendMessage(ChatColor.AQUA + "" + ChatColor.BOLD + "You joined the Angels team!");
            TeamManager.setTeam(p, "angels");
            p.getInventory().clear();
            if (TeamManager.getTeam(p) != null) {
                angelTeam += 1;
                shamanTeam -= 1;
            } else {
                angelTeam += 1;
            }
            return;
        }
        if (stack.getItemMeta().getDisplayName().contains("Shamans Team")) {
            if (TeamManager.shamans.contains(p.getUniqueId())) {
                p.sendMessage(ChatColor.RED + "You are already on this team!");
                return;
            }
            if (angelTeam < shamanTeam) {
                p.sendMessage(ChatColor.RED + "You cannot join a team with more members than the other team!");
                return;
            }
            if (shamanTeam >= 5) {
                p.sendMessage(ChatColor.RED + "You cannot join a full team!");
                return;
            }
            p.playSound(p.getLocation(), Sound.BLOCK_NOTE_PLING, 10, 1);
            p.sendMessage(ChatColor.RED + "" + ChatColor.BOLD + "You joined the Shamans team!");
            TeamManager.setTeam(p, "shamans");
            p.getInventory().clear();
            if (TeamManager.getTeam(p) != null) {
                shamanTeam += 1;
                angelTeam -= 1;
            } else {
                shamanTeam += 1;
            }
            return;

        }
    }

}
