package GameMechanics;

import ClassGUI.AaClassChooseGUI;
import ItemConstructorClass.ItemConstructor;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapelessRecipe;

import java.util.ArrayList;

public class RecipesPages implements Listener {
    void fillInvWithGlass(Inventory inv, int start) {
        for (int count = start; count < 45; count++) {
            ItemStack i = new ItemStack(Material.STAINED_GLASS_PANE, 1, (short) 15);
            inv.setItem(count, i);
        }
    }

    public Inventory createGrappleGUI() {
        ItemConstructor itc = new ItemConstructor();
        Inventory inv = Bukkit.getServer().createInventory(null, 9 * 5, ChatColor.WHITE +
                "" + ChatColor.BOLD + "Grappling Hook Recipe");
        fillInvWithGlass(inv, 0);
        //First line
        inv.setItem(10, new ItemStack(Material.IRON_INGOT));
        inv.setItem(11, new ItemStack(Material.IRON_INGOT));
        inv.setItem(12, new ItemStack(Material.STICK));
        //Second line
        inv.setItem(19, null);
        inv.setItem(20, new ItemStack(Material.STRING));
        inv.setItem(21, new ItemStack(Material.STICK));
        //Third line
        inv.setItem(28, null);
        inv.setItem(29, new ItemStack(Material.STRING));
        inv.setItem(30, new ItemStack(Material.STICK));
        //Main item for crafting
        ArrayList<String> lore = new ArrayList<>();
        lore.add(ChatColor.GRAY + "Used to climb mountains efficiently");
        inv.setItem(24, itc.MakeItem(ChatColor.DARK_AQUA + "" + ChatColor.BOLD + "Grappling Hook", Material.FISHING_ROD
                , lore));
        return inv;
    }

    public Inventory createStringGUI() {
        ItemConstructor itc = new ItemConstructor();
        Inventory inv = Bukkit.getServer().createInventory(null, 9 * 5, ChatColor.WHITE +
                "" + ChatColor.BOLD + "String");
        fillInvWithGlass(inv, 0);
        //First line
        inv.setItem(10, null);
        inv.setItem(11, null);
        inv.setItem(12, null);
        //Second line
        inv.setItem(19, null);
        inv.setItem(20, new ItemStack(Material.FEATHER));
        inv.setItem(21, null);
        //Third line
        inv.setItem(28, null);
        inv.setItem(29, null);
        inv.setItem(30, null);
        //Main item for crafting
        inv.setItem(24, new ItemStack(Material.STRING, 2));
        return inv;
    }

    public Inventory createCoreGUI() {
        ItemConstructor itc = new ItemConstructor();
        Inventory inv = Bukkit.getServer().createInventory(null, 9 * 5, ChatColor.WHITE +
                "" + ChatColor.BOLD + "Corrupted Diamond Core");
        fillInvWithGlass(inv, 0);
        //First line
        inv.setItem(10, null);
        inv.setItem(11, null);
        inv.setItem(12, null);
        //Second line
        inv.setItem(19, null);
        inv.setItem(20, itc.MakeItem(ChatColor.DARK_RED + "" + ChatColor.BOLD +
                "Corruption Core", Material.DRAGON_EGG, null));
        inv.setItem(21, new ItemStack(Material.DIAMOND));
        //Third line
        inv.setItem(28, null);
        inv.setItem(29, null);
        inv.setItem(30, null);
        //Main item for crafting
        ArrayList<String> lore = new ArrayList<>();
        lore.add(ChatColor.GRAY + "" + ChatColor.MAGIC + "JGZOIHAPOGIJ");
        inv.setItem(24, itc.MakeItem(ChatColor.AQUA + "" + ChatColor.BOLD
                + "Corrupted Diamond Core", Material.OBSIDIAN, lore));
        return inv;
    }

    public Inventory mainRecipesGUI() {
        ItemConstructor itc = new ItemConstructor();
        Inventory inv = Bukkit.getServer().createInventory(null, 9 * 5, ChatColor.WHITE +
                "" + ChatColor.BOLD + "Recipes");
        fillInvWithGlass(inv, 0);
        //Fishing hook
        ArrayList<String> lore = new ArrayList<>();
        lore.add(ChatColor.GRAY + "Used to climb mountains efficiently");
        inv.setItem(20, itc.MakeItem(ChatColor.DARK_AQUA + "" + ChatColor.BOLD + "Grappling Hook", Material.FISHING_ROD
                , lore));
        //String
        inv.setItem(22, itc.MakeItem(ChatColor.WHITE + "String", Material.STRING, null));
        //Corrupted Diamond Core
        ArrayList<String> lore1 = new ArrayList<>();
        lore1.add(ChatColor.GRAY + "" + ChatColor.MAGIC + "JGZOIHAPOGIJ");
        inv.setItem(24, itc.MakeItem(ChatColor.AQUA + "" + ChatColor.BOLD
                + "Corrupted Diamond Core", Material.OBSIDIAN, lore1));
        return inv;
    }

    @EventHandler
    public void onBlockRightClick(PlayerInteractEvent e) {
        Player p = e.getPlayer();
        if (e.getAction() == Action.RIGHT_CLICK_BLOCK) {
            if (e.getClickedBlock().getType() != Material.BOOKSHELF) return;
            e.setCancelled(true);
            p.openInventory(mainRecipesGUI());
        }
    }

    @EventHandler
    public void clickRecipes(InventoryClickEvent e) {
        HumanEntity p = e.getWhoClicked();
        Player player = (Player) p;
        if (e.getInventory().getTitle().contains("Grappling Hook")) {
            e.setCancelled(true);
        }
        if (e.getInventory().getTitle().contains("String")) {
            e.setCancelled(true);
        }
        if (e.getInventory().getTitle().contains("Corrupted Diamond Core")) {
            e.setCancelled(true);
        }
        if (!e.getInventory().getTitle().contains("Recipes")) {
            return;
        }
        e.setCancelled(true);
        if (e.getCurrentItem() != null && e.getCurrentItem().hasItemMeta() &&
                e.getCurrentItem().getItemMeta().getDisplayName().contains("Grappling Hook")) {
            p.openInventory(createGrappleGUI());
            ((Player) p).playSound(p.getLocation(), Sound.BLOCK_NOTE_PLING, 5, 1);
            return;
        }
        if (e.getCurrentItem() != null && e.getCurrentItem().hasItemMeta() &&
                e.getCurrentItem().getItemMeta().getDisplayName().contains("String")) {
            p.openInventory(createStringGUI());
            ((Player) p).playSound(p.getLocation(), Sound.BLOCK_NOTE_PLING, 5, 1);
            return;
        }
        if (e.getCurrentItem() != null && e.getCurrentItem().hasItemMeta() &&
                e.getCurrentItem().getItemMeta().getDisplayName().contains("Corrupted Diamond Core")) {
            p.openInventory(createCoreGUI());
            ((Player) p).playSound(p.getLocation(), Sound.BLOCK_NOTE_PLING, 5, 1);
            return;
        }
    }
}
