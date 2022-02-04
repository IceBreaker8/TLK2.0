package GameMechanics;

import Main.Main;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Random;

public class OreMineRespawn implements Listener {
    @EventHandler
    public void onPlayerBreakBlock(BlockBreakEvent event) {

        Player p = event.getPlayer();
        Block b = event.getBlock();
        if ((b.getType() == Material.LOG && b.getData() == 0)
                && ((p.getInventory().getItemInMainHand().getType().equals(Material.DIAMOND_AXE))
                || (p.getInventory().getItemInMainHand().getType().equals(Material.STONE_AXE))
                || (p.getInventory().getItemInMainHand().getType().equals(Material.GOLD_AXE))
                || (p.getInventory().getItemInMainHand().getType().equals(Material.IRON_AXE)))) {
            event.setCancelled(true);
            b.setType(Material.AIR);
            p.getInventory().addItem(new ItemStack(Material.LOG, 1, (short) 0));
            p.playSound(p.getLocation(), Sound.ENTITY_ITEM_PICKUP, 10, 1);
            new BukkitRunnable() {

                @Override
                public void run() {
                    b.setType(Material.LOG);
                    b.setData((byte) 0);

                }

            }.runTaskLater(Main.plugin, 20 * 10);
        }
        if ((b.getType() == Material.LOG && b.getData() == 1)
                && ((p.getInventory().getItemInMainHand().getType().equals(Material.DIAMOND_AXE))
                || (p.getInventory().getItemInMainHand().getType().equals(Material.STONE_AXE))
                || (p.getInventory().getItemInMainHand().getType().equals(Material.GOLD_AXE))
                || (p.getInventory().getItemInMainHand().getType().equals(Material.IRON_AXE)))) {
            event.setCancelled(true);
            b.setType(Material.AIR);
            p.getInventory().addItem(new ItemStack(Material.LOG, 1, (short) 1));
            p.playSound(p.getLocation(), Sound.ENTITY_ITEM_PICKUP, 10, 1);
            new BukkitRunnable() {

                @Override
                public void run() {
                    b.setType(Material.LOG);
                    b.setData((byte) 1);

                }

            }.runTaskLater(Main.plugin, 20 * 10);
        }
        if (p.getInventory().getItemInMainHand().getType().equals(Material.DIAMOND_SPADE)
                || p.getInventory().getItemInMainHand().getType().equals(Material.IRON_SPADE)
                || p.getInventory().getItemInMainHand().getType().equals(Material.STONE_SPADE)) {
            if (b.getType().equals(Material.SAND)) {
                event.setCancelled(true);
                b.setType(Material.COBBLESTONE);
                // TODO SAND
                p.getInventory().addItem(new ItemStack(Material.SAND, 1));
                //////////////////////////////////////////
                p.playSound(p.getLocation(), Sound.ENTITY_ITEM_PICKUP, 10, 1);
                new BukkitRunnable() {

                    @Override
                    public void run() {
                        b.setType(Material.SAND);
                    }

                }.runTaskLater(Main.plugin, 20 * 10);
            }
        }

        if (p.getInventory().getItemInMainHand().getType().equals(Material.GOLD_PICKAXE)
                || p.getInventory().getItemInMainHand().getType().equals(Material.DIAMOND_PICKAXE)
                || p.getInventory().getItemInMainHand().getType().equals(Material.IRON_PICKAXE)
                || p.getInventory().getItemInMainHand().getType().equals(Material.STONE_PICKAXE)) {
            if (b.getType().equals(Material.IRON_ORE)) {
                event.setCancelled(true);
                b.setType(Material.COBBLESTONE);
                p.getInventory().addItem(new ItemStack(Material.IRON_ORE));
                p.playSound(p.getLocation(), Sound.ENTITY_ITEM_PICKUP, 10, 1);
                new BukkitRunnable() {

                    @Override
                    public void run() {
                        b.setType(Material.IRON_ORE);

                    }

                }.runTaskLater(Main.plugin, 20 * 10);
            }
        }
        if (p.getInventory().getItemInMainHand().getType().equals(Material.GOLD_PICKAXE)
                || p.getInventory().getItemInMainHand().getType().equals(Material.DIAMOND_PICKAXE)
                || p.getInventory().getItemInMainHand().getType().equals(Material.IRON_PICKAXE)
                || p.getInventory().getItemInMainHand().getType().equals(Material.STONE_PICKAXE)) {
            if (b.getType().equals(Material.COAL_ORE)) {
                event.setCancelled(true);
                b.setType(Material.COBBLESTONE);
                p.getInventory().addItem(new ItemStack(Material.COAL));
                p.playSound(p.getLocation(), Sound.ENTITY_ITEM_PICKUP, 10, 1);
                new BukkitRunnable() {

                    @Override
                    public void run() {
                        b.setType(Material.COAL_ORE);

                    }

                }.runTaskLater(Main.plugin, 20 * 20);
            }
        }
        if (p.getInventory().getItemInMainHand().getType().equals(Material.GOLD_PICKAXE)
                || p.getInventory().getItemInMainHand().getType().equals(Material.DIAMOND_PICKAXE)
                || p.getInventory().getItemInMainHand().getType().equals(Material.IRON_PICKAXE)) {
            if (b.getType().equals(Material.GOLD_ORE)) {
                event.setCancelled(true);
                b.setType(Material.COBBLESTONE);
                p.getInventory().addItem(new ItemStack(Material.GOLD_ORE));
                p.playSound(p.getLocation(), Sound.ENTITY_ITEM_PICKUP, 10, 1);
                new BukkitRunnable() {

                    @Override
                    public void run() {
                        b.setType(Material.GOLD_ORE);

                    }

                }.runTaskLater(Main.plugin, 20 * 20);
            }
        }
        if (p.getInventory().getItemInMainHand().getType().equals(Material.IRON_PICKAXE)) {
            if (b.getType().equals(Material.DIAMOND_ORE)) {
                event.setCancelled(true);
                b.setType(Material.COBBLESTONE);
                p.getInventory().addItem(new ItemStack(Material.DIAMOND));
                p.playSound(p.getLocation(), Sound.ENTITY_ITEM_PICKUP, 10, 1);
                new BukkitRunnable() {

                    @Override
                    public void run() {
                        b.setType(Material.DIAMOND_ORE);

                    }

                }.runTaskLater(Main.plugin, 20 * 30);
            }
        }
    }
}
