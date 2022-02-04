package GameMechanics;

import Main.Main;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

public class BonfireMech implements Listener {


    private World w = Bukkit.getServer().getWorld("world");
    private Location angelBonfire = new Location(w, -94, 72, -16);
    private Location shamanBonfire = new Location(w, 311, 74, -30);

    public HashMap<UUID, Integer> cooldownTimeP = new HashMap<UUID, Integer>();
    public HashMap<UUID, BukkitRunnable> cooldownTaskP = new HashMap<UUID, BukkitRunnable>();

    public void activateBonfires() {
        bonfireSH();
        bonfireAN();
    }

    @EventHandler
    public void onMove(PlayerMoveEvent e) {
        Player p = e.getPlayer();
        if (p.getGameMode().equals(GameMode.SPECTATOR)) {
            if (p.getLocation().getY() < 118) {
                p.teleport(new Location(p.getWorld(), -46, 120, -32));
                p.sendMessage(ChatColor.RED + "Dont't get too close!");
                return;
            }
        }

    }

    public void bonfireSH() {
        new BukkitRunnable() {
            Location loc = shamanBonfire.clone().subtract(0, 0.5, 0);
            double stick = 0;

            @Override
            public void run() {
                for (Entity e : loc.getWorld().getEntities()) {
                    if (e instanceof Item) {
                        for (Location loc1 : getStickDropLoc((Item) e, shamanBonfire)) {
                            if (e.getLocation().distance(loc1) < 1.2) {
                                if (((Item) e).getItemStack().getType().equals(Material.STICK)) {
                                    e.remove();
                                    stick += 1;
                                    break;
                                }
                            }
                        }
                    }
                }
                if (stick == 6) {
                    new BukkitRunnable() {
                        @Override
                        public void run() {
                            for (Block block : getBlocks(shamanBonfire.clone().subtract(0, 1, 0).getBlock(), 6)) {
                                if (block.getType().equals(Material.NETHERRACK)) {
                                    block.getLocation().clone().add(0, 1, 0).getBlock().setType(Material.FIRE);
                                }
                            }
                        }
                    }.runTaskTimer(Main.plugin, 0, 8);
                    this.cancel();
                }
            }
        }.runTaskTimer(Main.plugin, 0, 10);
    }

    public void bonfireAN() {
        new BukkitRunnable() {
            Location loc = angelBonfire.clone().subtract(0, 0.5, 0);
            double stick = 0;

            @Override
            public void run() {
                for (Entity e : loc.getWorld().getEntities()) {
                    if (e instanceof Item) {
                        for (Location loc1 : getStickDropLoc((Item) e, angelBonfire)) {
                            if (e.getLocation().distance(loc1) < 1.2) {
                                if (((Item) e).getItemStack().getType().equals(Material.STICK)) {
                                    e.remove();
                                    stick += 1;
                                    break;
                                }
                            }
                        }
                    }
                }
                if (stick == 6) {
                    new BukkitRunnable() {
                        @Override
                        public void run() {
                            for (Block block : getBlocks(angelBonfire.clone().subtract(0, 1, 0).getBlock(), 6)) {
                                if (block.getType().equals(Material.NETHERRACK)) {
                                    block.getLocation().clone().add(0, 1, 0).getBlock().setType(Material.FIRE);
                                }
                            }
                        }
                    }.runTaskTimer(Main.plugin, 0, 8);
                    this.cancel();
                }
            }
        }.runTaskTimer(Main.plugin, 0, 10);
    }

    private ArrayList<Location> getStickDropLoc(Item stick, Location loc) {
        ArrayList<Location> location = new ArrayList<Location>();
        for (Block block : getBlocks(loc.clone().subtract(0, 1, 0).getBlock(), 6)) {
            if (block.getType().equals(Material.NETHERRACK)) {
                location.add(block.getLocation().clone().add(0, 1, 0));
            }
        }
        return location;
    }

    public ArrayList<Block> getBlocks(Block start, int radius) {
        ArrayList<Block> blocks = new ArrayList<Block>();
        for (double x = start.getLocation().getX() - radius; x <= start.getLocation().getX() + radius; x++) {
            for (double y = start.getLocation().getY() - radius; y <= start.getLocation().getY() + radius; y++) {
                for (double z = start.getLocation().getZ() - radius; z <= start.getLocation().getZ() + radius; z++) {
                    Location loc = new Location(start.getWorld(), x, y, z);
                    if (loc.getBlock().getType().isSolid()) {
                        if (loc.getBlock().getY() == start.getY()) {
                            blocks.add(loc.getBlock());
                        }
                    }
                }
            }
        }
        return blocks;
    }

    @EventHandler
    public void cookFish(PlayerInteractEvent e) {
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
        if (!(action == Action.RIGHT_CLICK_AIR || action == Action.RIGHT_CLICK_BLOCK))
            return;
        if (stack.getType().equals(Material.RAW_FISH) && cooldownTaskP.containsKey(p.getUniqueId())) {
            p.sendMessage(ChatColor.RED + "You can't throw fish rapidly!");
            return;
        }
        if (stack.getType().equals(Material.RAW_FISH) && !(cooldownTaskP.containsKey(p.getUniqueId()))) {
            e.setCancelled(true);
            Item fish = p.getWorld().dropItem(p.getEyeLocation(), new ItemStack(Material.RAW_FISH, 1));
            fish.setVelocity(p.getEyeLocation().getDirection().multiply(0.3F));
            fish.setPickupDelay(20 * 3);
            cookingTimer(p, fish);
            // REMOVE FISH
            for (ItemStack item1 : p.getInventory().getContents()) {
                if (item1 != null) {
                    if (item1.getType().equals(Material.RAW_FISH)) {
                        item1.setAmount(item1.getAmount() - 1);
                    }
                }
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
        if (stack.getType().equals(Material.RABBIT) && !(cooldownTaskP.containsKey(p.getUniqueId()))) {
            e.setCancelled(true);
            Item rabbit = p.getWorld().dropItem(p.getEyeLocation(), new ItemStack(Material.RABBIT, 1));
            rabbit.setVelocity(p.getEyeLocation().getDirection().multiply(0.3F));
            rabbit.setPickupDelay(20 * 3);
            cookingTimerRabbit(p, rabbit);
            // REMOVE FISH
            for (ItemStack item1 : p.getInventory().getContents()) {
                if (item1 != null) {
                    if (item1.getType().equals(Material.RABBIT)) {
                        item1.setAmount(item1.getAmount() - 1);
                    }
                }
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

    public boolean playerIsNearby1(Player p) {
        if (p.getLocation().distance(angelBonfire) < 4) {
            return true;
        }
        return false;
    }

    public boolean playerIsNearby2(Player p) {
        if (p.getLocation().distance(shamanBonfire) < 4) {
            return true;
        }
        return false;
    }

    public void cookingTimer(Player p, Item fish) {
        new BukkitRunnable() {
            @Override
            public void run() {
                if (fish.getFireTicks() > 0) {
                    cancel();
                    cookingAnimation(p, fish);
                }
            }
        }.runTaskTimer(Main.plugin, 0, 5);
    }

    public void cookingTimerRabbit(Player p, Item fish) {
        new BukkitRunnable() {
            @Override
            public void run() {
                if (fish.getFireTicks() > 0) {
                    cancel();
                    cookingAnimationRabbit(p, fish);
                }
            }
        }.runTaskTimer(Main.plugin, 0, 5);
    }

    private void cookingAnimation(Player p, Item fish) {
        Location loc = fish.getLocation();
        fish.remove();
        // NEW
        Item animation = loc.getWorld().dropItem(loc, new ItemStack(Material.RAW_FISH, 1));
        animation.setInvulnerable(true);
        animation.setPickupDelay(99 * 20);
        new BukkitRunnable() {
            double t = 0;

            @Override
            public void run() {
                t = t + 1;
                animation.setVelocity(new Vector(0, 0.5, 0));

                if (t == 2) {
                    animation.remove();
                    if (playerIsNearby1(p) || playerIsNearby2(p)) {
                        p.getInventory().addItem(new ItemStack(Material.COOKED_FISH, 1));
                        p.playSound(p.getLocation(), Sound.ENTITY_ITEM_PICKUP, 10, 1);
                    } else {
                        p.sendMessage(ChatColor.RED + "Your fish got burnt as you were too far to pick it up... ");
                    }
                    cancel();
                }
            }

        }.runTaskTimer(Main.plugin, 0, 20 * 2);
    }

    private void cookingAnimationRabbit(Player p, Item fish) {
        Location loc = fish.getLocation();
        fish.remove();
        // NEW
        Item animation = loc.getWorld().dropItem(loc, new ItemStack(Material.RABBIT, 1));
        animation.setInvulnerable(true);
        animation.setPickupDelay(99 * 20);
        new BukkitRunnable() {
            double t = 0;

            @Override
            public void run() {
                t = t + 1;
                animation.setVelocity(new Vector(0, 0.5, 0));

                if (t == 2) {
                    animation.remove();
                    if (playerIsNearby1(p) || playerIsNearby2(p)) {
                        p.getInventory().addItem(new ItemStack(Material.COOKED_RABBIT, 1));
                        p.playSound(p.getLocation(), Sound.ENTITY_ITEM_PICKUP, 10, 1);
                    } else {
                        p.sendMessage(ChatColor.RED + "Your rabbit got burnt as you were too far to pick it up... ");
                    }
                    cancel();
                }
            }

        }.runTaskTimer(Main.plugin, 0, 20 * 2);
    }

    @EventHandler
    public void brokenFurnace(PlayerInteractEvent e) {
        Player p = e.getPlayer();
        Action action = e.getAction();
        if (action == Action.RIGHT_CLICK_BLOCK && e.getClickedBlock().getType().equals(Material.FURNACE)) {
            e.setCancelled(true);
            p.sendMessage(ChatColor.RED + "This furnace seems broken, I need to find another way to cook..");
            return;
        }
        if (action == Action.RIGHT_CLICK_BLOCK && e.getClickedBlock().getType().equals(Material.CHEST)) {
            e.setCancelled(true);
            p.sendMessage(ChatColor.RED + "I can't open this chest, something is blocking it..");
            return;
        }

    }
}
