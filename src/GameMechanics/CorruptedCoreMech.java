package GameMechanics;

import GameModeCore.NexusDamage;
import ItemConstructorClass.ItemConstructor;
import Main.Main;
import Teams.TeamManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.inventory.ShapelessRecipe;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;

public class CorruptedCoreMech implements Listener {

    public static void addDiamondCore() {
        ItemConstructor itc = new ItemConstructor();
        ArrayList<String> lore = new ArrayList<>();
        lore.add(ChatColor.GRAY + "" + ChatColor.MAGIC + "JGZOIHAPOGIJ");
        ShapelessRecipe shapelessRecipe = new ShapelessRecipe(itc.MakeItem(ChatColor.AQUA + "" + ChatColor.BOLD
                + "Corrupted Diamond Core", Material.OBSIDIAN, lore));
        shapelessRecipe.addIngredient(1, Material.DRAGON_EGG);
        shapelessRecipe.addIngredient(1, Material.DIAMOND);
        Bukkit.getServer().addRecipe(shapelessRecipe);
    }

    @EventHandler
    public void onItemThrow(PlayerDropItemEvent e) {
        Player p = e.getPlayer();
        Item item = e.getItemDrop();
        if (TeamManager.getTeam(p) == null) return;
        if (TeamManager.getTeam(p) == "shamans") {
            if (item.getName().contains("obsidian")) {
                item.setPickupDelay(99999);
                new BukkitRunnable() {
                    @Override
                    public void run() {
                        item.getWorld().strikeLightningEffect(item.getLocation());
                        item.getWorld().playSound(item.getLocation(), Sound.ENTITY_LIGHTNING_THUNDER, 40, 1);
                        item.remove();
                        Bukkit.broadcastMessage(ChatColor.WHITE + "" + ChatColor.BOLD +
                                "Angel's wither have been weakened!");
                        NexusDamage.weakAngel = true;
                    }
                }.runTaskLater(Main.plugin, 20);
            }
        } else if (TeamManager.getTeam(p) == "angels") {
            if (item.getName().contains("obsidian")) {
                item.setPickupDelay(99999);
                new BukkitRunnable() {
                    @Override
                    public void run() {
                        item.getWorld().strikeLightningEffect(item.getLocation());
                        item.getWorld().playSound(item.getLocation(), Sound.ENTITY_LIGHTNING_THUNDER, 40, 1);
                        item.remove();
                        Bukkit.broadcastMessage(ChatColor.GREEN + "" + ChatColor.BOLD +
                                "Shaman's wither have been weakened!");
                        NexusDamage.weakShaman = true;
                    }
                }.runTaskLater(Main.plugin, 20);

            }
        }
    }
}
