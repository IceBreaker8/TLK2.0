package GameMechanics;

import Main.Main;
import org.bukkit.*;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class SmelterMech implements Listener {
    private World w = Bukkit.getServer().getWorld("world");

    private Location shamanSmelterLocation = new Location(w, 273, 70, 165);
    private Location shamanSmelterActivation = new Location(w, 273, 68.5, 168);
    private Location shamanLavaStart = new Location(w, 270, 72, 167);

    private Location angelSmelterLocation = new Location(w, -15, 70, 151);
    private Location angelSmelterActivation = new Location(w, -15, 68.5, 154);
    private Location humanLavaStart = new Location(w, -12, 72, 153);

    public HashMap<UUID, Integer> cooldownTimeP = new HashMap<UUID, Integer>();
    public HashMap<UUID, BukkitRunnable> cooldownTaskP = new HashMap<UUID, BukkitRunnable>();

    public void activateSmelter() {
        smelter(shamanSmelterActivation, shamanSmelterLocation, shamanLavaStart);
        smelter(angelSmelterActivation, angelSmelterLocation, humanLavaStart);
    }

    public void smelter(Location active, Location loc, Location loc2) {
        new BukkitRunnable() {

            @Override
            public void run() {
                for (Entity e : active.getWorld().getEntities()) {
                    if (e.getLocation().distance(active) < 1.6) {
                        if (e instanceof Item) {
                            e.remove();
                            if (((Item) e).getItemStack().getType().equals(Material.COAL_BLOCK)) {
                                e.remove();
                                active.clone().add(0, 1, 0).getBlock().setType(Material.COAL_BLOCK);
                                loc.getWorld().playSound(loc, Sound.BLOCK_ANVIL_USE, 60, 1);
                                /*Bukkit.broadcastMessage(ChatColor.WHITE + "" + ChatColor.BOLD + "> "
                                        + ChatColor.DARK_AQUA + "The" + ChatColor.DARK_RED + "" + ChatColor.BOLD
                                        + " Ancient Smelter" + ChatColor.DARK_AQUA + " got activated!");*/
                                loc2.getBlock().setType(Material.LAVA);
                                cancel();

                            }
                        }
                    }

                }

            }
        }.runTaskTimer(Main.plugin, 0, 10);
    }

    @EventHandler
    public void smeltOres(PlayerInteractEvent e) {
        final Player p = e.getPlayer();

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
        if (!(action == Action.LEFT_CLICK_AIR || action == Action.LEFT_CLICK_BLOCK))
            return;
        if ((stack.getType().equals(Material.COAL_ORE) || stack.getType().equals(Material.DIAMOND_ORE)
                || stack.getType().equals(Material.GOLD_ORE) || stack.getType().equals(Material.IRON_ORE))
                && cooldownTaskP.containsKey(p.getUniqueId())) {
            p.sendMessage(ChatColor.RED + "You can't throw ores rapidly!");
            return;
        }
        if ((stack.getType().equals(Material.COAL_ORE) || stack.getType().equals(Material.DIAMOND_ORE)
                || stack.getType().equals(Material.GOLD_ORE) || stack.getType().equals(Material.IRON_ORE))) {

            e.setCancelled(true);
            ItemStack ore = (ItemStack) p.getInventory().getItemInMainHand();
            if (ore.getType().equals(Material.DIAMOND_ORE)) {
                Item thrown = p.getWorld().dropItem(p.getEyeLocation(), new ItemStack(Material.DIAMOND_ORE, 1));
                thrown.setVelocity(p.getEyeLocation().getDirection().multiply(0.5F));
                thrown.setPickupDelay(20 * 3);
                thrown.setInvulnerable(true);
                new BukkitRunnable() {

                    @Override
                    public void run() {
                        if (thrown.getFireTicks() > 0 && playerIsNearby(p)) {
                            thrown.remove();
                            p.getInventory().addItem(new ItemStack(Material.DIAMOND, 1));
                            p.playSound(p.getLocation(), Sound.BLOCK_ANVIL_USE, 10, 1);

                        }

                    }

                }.runTaskLater(Main.plugin, 20 * 2);
            }

            if (ore.getType().equals(Material.IRON_ORE)) {
                Item thrown = p.getWorld().dropItem(p.getEyeLocation(), new ItemStack(Material.IRON_ORE, 1));
                thrown.setVelocity(p.getEyeLocation().getDirection().multiply(0.5F));
                thrown.setPickupDelay(20 * 3);
                thrown.setInvulnerable(true);
                new BukkitRunnable() {

                    @Override
                    public void run() {
                        if (thrown.getFireTicks() > 0 && playerIsNearby(p)) {
                            thrown.remove();

                            p.getInventory().addItem(new ItemStack(Material.IRON_INGOT, 3));
                            p.playSound(p.getLocation(), Sound.BLOCK_ANVIL_USE, 10, 1);

                        }

                    }

                }.runTaskLater(Main.plugin, 20 * 2);
            }
            if (ore.getType().equals(Material.COAL_ORE)) {
                Item thrown = p.getWorld().dropItem(p.getEyeLocation(), new ItemStack(Material.COAL_ORE, 1));
                thrown.setVelocity(p.getEyeLocation().getDirection().multiply(0.5F));
                thrown.setPickupDelay(20 * 4);
                thrown.setInvulnerable(true);
                new BukkitRunnable() {

                    @Override
                    public void run() {
                        if (thrown.getFireTicks() > 0 && playerIsNearby(p)) {
                            thrown.remove();
                            p.getInventory().addItem(new ItemStack(Material.COAL, 1));
                            p.playSound(p.getLocation(), Sound.BLOCK_ANVIL_USE, 10, 1);
                        }

                    }

                }.runTaskLater(Main.plugin, 20 * 2);
            }
            if (ore.getType().equals(Material.GOLD_ORE)) {
                Item thrown = p.getWorld().dropItem(p.getEyeLocation(), new ItemStack(Material.GOLD_ORE, 1));
                thrown.setVelocity(p.getEyeLocation().getDirection().multiply(0.5F));
                thrown.setPickupDelay(20 * 3);
                thrown.setInvulnerable(true);
                new BukkitRunnable() {

                    @Override
                    public void run() {
                        if (thrown.getFireTicks() > 0 && playerIsNearby(p)) {
                            thrown.remove();
                            p.getInventory().addItem(new ItemStack(Material.GOLD_INGOT, 3));
                            p.playSound(p.getLocation(), Sound.BLOCK_ANVIL_USE, 10, 1);

                        }

                    }

                }.runTaskLater(Main.plugin, 20 * 2);

            }

            // REMOVE ORE
            if (ore.getType().equals(Material.DIAMOND_ORE)) {
                consumeItem(p, 1, Material.DIAMOND_ORE);
            }

            if (ore.getType().equals(Material.IRON_ORE)) {
                consumeItem(p, 1, Material.IRON_ORE);
            }
            if (ore.getType().equals(Material.COAL_ORE)) {
                consumeItem(p, 1, Material.COAL_ORE);
            }
            if (ore.getType().equals(Material.GOLD_ORE)) {
                consumeItem(p, 1, Material.GOLD_ORE);
            }
            // COOLDOWN
            cooldownTimeP.put(p.getUniqueId(), 3);

            cooldownTaskP.put(p.getUniqueId(), new BukkitRunnable() {

                public void run() {
                    if ((cooldownTimeP.get(p.getUniqueId()) > 0)) {
                    }

                    cooldownTimeP.put(p.getUniqueId(), cooldownTimeP.get(p.getUniqueId()) - 1);
                    if ((cooldownTimeP.get(p.getUniqueId()) == -1)) {
                        p.playSound(p.getLocation(), Sound.BLOCK_NOTE_PLING, 10, 1);
                        cooldownTimeP.remove(p.getUniqueId());
                        cooldownTaskP.remove(p.getUniqueId());
                        cancel();
                    }
                }

            });

            cooldownTaskP.get(p.getUniqueId()).runTaskTimer(Main.plugin, 0, 20);
        }

    }

    public boolean playerIsNearby(Player p) {
        if (p.getLocation().distance(shamanSmelterLocation) < 6 || p.getLocation().distance(angelSmelterLocation) < 6) {
            return true;
        }
        return false;
    }

    public boolean consumeItem(Player player, int count, Material mat) {
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
