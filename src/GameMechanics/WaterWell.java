package GameMechanics;

import ItemConstructorClass.ItemConstructor;
import Main.Main;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.potion.PotionData;
import org.bukkit.potion.PotionType;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.Map;

public class WaterWell implements Listener {

    private World world = Bukkit.getWorld("world");

    private Location firstCauldron = new Location(world, -109, 77, 52);
    private Location secondCauldron = new Location(world, 280, 76, -48);
    private boolean firstCauldronAllow = true;
    private boolean secondCauldronAllow = true;

    @EventHandler
    public void onBlockClick(PlayerInteractEvent event) {
        Player p = event.getPlayer();
        if (event.getAction() == Action.RIGHT_CLICK_BLOCK) {
            Block block = event.getClickedBlock();
            if (block.getType() == Material.CAULDRON) {
                if (p.getItemInHand() == null || p.getItemInHand().getType().equals(Material.AIR)) {
                    if (block.getLocation().distance(firstCauldron) < 1) {
                        moveWaterBucket(p, firstCauldron, 7, 1);
                    } else if (block.getLocation().distance(secondCauldron) < 1) {
                        moveWaterBucket(p, secondCauldron, 7, 2);
                    }
                } else if (p.getItemInHand().getType().equals(Material.GLASS_BOTTLE)) {
                    return;
                } else {
                    p.sendMessage(ChatColor.RED + "Use it with your bare hands!");
                }
            }
        }
    }

    public boolean getState(int verif) {
        if (verif == 1) {
            return firstCauldronAllow;
        }
        return secondCauldronAllow;
    }

    public void setState(int verif, boolean state) {
        if (verif == 1) {
            firstCauldronAllow = state;
            return;
        }
        secondCauldronAllow = state;
    }

    public void moveWaterBucket(Player p, Location bucketLoc, int height, int state) {
        if (getState(state) == false) {
            p.sendMessage(ChatColor.RED + "You need to wait for it to ascend again!");
            return;
        }
        setState(state, false);
        new BukkitRunnable() {
            int desc = 0;
            int asc = 0;

            @Override
            public void run() {
                if (desc == height) {
                    asc += 1;
                    byte data = bucketLoc.getBlock().getData();
                    bucketLoc.add(0, 1, 0);
                    bucketLoc.clone().subtract(0, 1, 0).getBlock().setType(Material.AIR);
                    bucketLoc.getBlock().setType(Material.CAULDRON);
                    if (asc == 1) {
                        bucketLoc.getBlock().setData((byte) 3);
                    } else {
                        bucketLoc.getBlock().setData((byte) data);
                    }
                    bucketLoc.getWorld().playSound(bucketLoc, Sound.BLOCK_PISTON_EXTEND, 5, 2);
                    if (asc == height) {
                        setState(state, true);
                        cancel();
                    }
                }
                if (desc < height) {
                    desc += 1;
                    if (desc == height - 2) {
                        bucketLoc.getWorld().playSound(bucketLoc, Sound.ENTITY_GENERIC_SPLASH, 5, 1);
                    }
                    bucketLoc.getWorld().playSound(bucketLoc, Sound.BLOCK_PISTON_CONTRACT, 5, 2);

                    byte data = bucketLoc.getBlock().getData();
                    bucketLoc.subtract(0, 1, 0);
                    bucketLoc.clone().add(0, 1, 0).getBlock().setType(Material.IRON_FENCE);
                    bucketLoc.getBlock().setType(Material.CAULDRON);
                    bucketLoc.getBlock().setData((byte) data);
                }
            }
        }.runTaskTimer(Main.plugin, 0, 10);
    }


    @EventHandler
    public void onRightClick(PlayerInteractEvent e) {
        Player p = e.getPlayer();
        if (e.getAction() == Action.RIGHT_CLICK_BLOCK) {
            if (e.getClickedBlock().getType().equals(Material.CAULDRON) &&
                    (e.getClickedBlock().getData() != 0) && !p.isSneaking()) {
                if (p.getInventory().getItemInMainHand().getType().equals(Material.GLASS_BOTTLE)) {
                    if (e.getClickedBlock().getLocation().distance(firstCauldron) < 1) {
                        new BukkitRunnable() {
                            @Override
                            public void run() {
                                removeItemStack(p, p.getInventory());
                                addPotableWater(p);
                            }
                        }.runTaskLater(Main.plugin, 1);
                        return;
                    }
                    if (e.getClickedBlock().getLocation().distance(secondCauldron) < 1) {
                        new BukkitRunnable() {
                            @Override
                            public void run() {
                                removeItemStack(p, p.getInventory());
                                addPotableWater(p);
                            }
                        }.runTaskLater(Main.plugin, 1);
                        return;
                    }
                }
            }
        }
    }

    public void removeItemStack(Player p, Inventory inv) {
        for (ItemStack item : inv.getContents()) {
            if (item != null) {
                if (item.getType().equals(Material.POTION) && item.getData().getData() == 0) {
                    if (item.hasItemMeta()) {
                        if (!item.getItemMeta().hasLore()) {
                            consumeItem(p, 1, item);
                            break;
                        }
                    }
                }
            }
        }
    }

    public void addPotableWater(Player p) {
        ItemConstructor iC = new ItemConstructor();
        ItemStack bottle = new ItemStack(Material.POTION, 1);
        PotionMeta meta = (PotionMeta) bottle.getItemMeta();
        meta.clearCustomEffects();
        meta.setBasePotionData(new PotionData(PotionType.WATER));
        bottle.setItemMeta(meta);
        ArrayList<String> lore = new ArrayList<>();
        lore.add(ChatColor.GRAY + "Restores 50% of your thirst level");
        p.getInventory().addItem(iC.MakeItemByStack(ChatColor.AQUA + "" + ChatColor.BOLD + "Potable Water",
                bottle, lore));
    }

    public boolean consumeItem(Player player, int count, ItemStack mat) {
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
