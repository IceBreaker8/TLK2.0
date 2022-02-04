package GameMechanics;

import GameMenuInvGUIMechanics.GameMenuMechs;
import ItemConstructorClass.ItemConstructor;
import Main.Main;
import org.bukkit.*;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.event.player.PlayerFishEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.ShapelessRecipe;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.UUID;

public class ClimbingTool implements Listener {

    private Integer maxHeight = 94;

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent e) {
        Player p = e.getPlayer();
        hookStatus.remove(p.getUniqueId());
        switched.remove(p.getUniqueId());
        hooked.remove(p.getUniqueId());

    }

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent e) {
        if (GameMenuMechs.activateClimb == false) return;
        Player p = e.getPlayer();
        if (p.getLocation().getY() > maxHeight) {
            switched.remove(p.getUniqueId());
            p.addPotionEffect(new PotionEffect(PotionEffectType.JUMP, 99999, 128));
        } else {
            if (!switched.contains(p.getUniqueId())) {
                if (p.getInventory().getHeldItemSlot() == 8) {
                    p.getInventory().setHeldItemSlot(p.getInventory().getHeldItemSlot() - 1);
                    switched.add(p.getUniqueId());
                } else if (p.getInventory().getHeldItemSlot() == 0) {
                    p.getInventory().setHeldItemSlot(p.getInventory().getHeldItemSlot() + 1);
                    switched.add(p.getUniqueId());
                } else {
                    p.getInventory().setHeldItemSlot(p.getInventory().getHeldItemSlot() + 1);
                    switched.add(p.getUniqueId());
                }

            }
            hookStatus.remove(p.getUniqueId());
            hooked.remove(p.getUniqueId());
            for (PotionEffect effect : p.getActivePotionEffects())
                p.removePotionEffect(effect.getType());
        }

    }

    public static HashMap<UUID, Boolean> hooked = new HashMap<>();
    public static HashMap<UUID, Integer> hookStatus = new HashMap<>();
    public static HashSet<UUID> switched = new HashSet<>();

    @EventHandler
    public void onFish(PlayerFishEvent e) {
        if (GameMenuMechs.activateClimb == false) return;
        Player p = e.getPlayer();
        if (p.getInventory().getItemInMainHand().getType().equals(Material.FISHING_ROD)) {
            if (!p.getInventory().getItemInMainHand().hasItemMeta()) return;
            if (p.getInventory().getItemInMainHand().getItemMeta().getDisplayName() == null) return;
            if (p.getInventory().getItemInMainHand().getItemMeta().getLore() == null) return;
            if (p.getInventory().getItemInMainHand().getItemMeta().getDisplayName().contains("Grappling Hook")) {
                if (p.getLocation().getY() < maxHeight) return;
                if (hookStatus.containsKey(p.getUniqueId())) {
                    if (hookStatus.get(p.getUniqueId()) == 1) {
                        hookStatus.put(p.getUniqueId(), 0);
                    } else {
                        hookStatus.put(p.getUniqueId(), 1);
                    }
                } else {
                    hookStatus.put(p.getUniqueId(), 0);
                }
                if (hooked.containsKey(p.getUniqueId())) {
                    if (hooked.get(p.getUniqueId()) == true) {
                        if (e.getHook().getLocation().getY() > p.getLocation().getY()) {
                            Location hookLoc = e.getHook().getLocation();
                            Location newLoc = hookLoc.clone().subtract(0, 1, 0).
                                    getBlock().getLocation().clone().add(0, 1, 0);
                            p.teleport(new Location(p.getWorld(), newLoc.getX(), newLoc.getY(), newLoc.getZ()
                                    , p.getLocation().getYaw(), p.getLocation().getPitch()));
                            hooked.remove(p.getUniqueId());
                            return;
                        } else {
                            p.sendMessage(ChatColor.RED + "You need to climb up with it, not to go down!");
                            hooked.remove(p.getUniqueId());
                            return;
                        }
                    }
                }
            }
        }
    }

    @EventHandler
    public void hookOnGround(ProjectileLaunchEvent e) {
        if (GameMenuMechs.activateClimb == false) return;
        if (!e.getEntityType().equals(EntityType.FISHING_HOOK)) return;
        if (!(e.getEntity().getShooter() instanceof Player)) return;
        Player p = (Player) e.getEntity().getShooter();
        if (!p.getInventory().getItemInMainHand().hasItemMeta()) return;
        if (p.getInventory().getItemInMainHand().getItemMeta().getDisplayName() == null) return;
        if (p.getInventory().getItemInMainHand().getItemMeta().getLore() == null) return;
        if (p.getInventory().getItemInMainHand().getItemMeta().getDisplayName().contains("Grappling Hook")) {

            if (p.getLocation().getY() < maxHeight) return;
            new BukkitRunnable() {
                int count = 0;

                @Override
                public void run() {
                    if (p.getLocation().getY() < maxHeight) {
                        cancel();
                        return;
                    }
                    if (hookStatus.containsKey(p.getUniqueId())) {
                        if (hookStatus.get(p.getUniqueId()) == 1) {
                            p.sendMessage(ChatColor.YELLOW + "" + ChatColor.BOLD + "> Hook Unattached <");
                            cancel();
                            return;
                        }
                    }
                    if (e.getEntity().isOnGround()) {
                        p.sendMessage(ChatColor.GREEN + "" + ChatColor.BOLD + "> Attached <");
                        p.playSound(p.getLocation(), Sound.BLOCK_NOTE_PLING, 5, 1);
                        hooked.put(p.getUniqueId(), true);
                        cancel();
                        return;
                    }
                    count += 1;

                    if (count == 80) {
                        p.sendMessage(ChatColor.RED + "" + ChatColor.BOLD + "> Hook Lost <");
                        hookStatus.put(p.getUniqueId(), 0);
                        cancel();
                        return;
                    }

                }
            }.runTaskTimer(Main.plugin, 10, 1);
        }
    }

    public static void grapplingHookRecipe() {
        ArrayList<String> lore = new ArrayList<>();
        lore.add(ChatColor.GRAY + "Used to climb mountains efficiently");
        ItemConstructor iC = new ItemConstructor();
        ShapedRecipe hrecipe = new ShapedRecipe(iC.MakeItem(ChatColor.DARK_AQUA + "" + ChatColor.BOLD + "Grappling Hook", Material.FISHING_ROD
                , lore));
        hrecipe.shape("@@!",
                "?#!",
                "?#!");
        hrecipe.setIngredient('@', Material.IRON_INGOT);
        hrecipe.setIngredient('!', Material.STICK);
        hrecipe.setIngredient('#', Material.STRING);
        Bukkit.getServer().addRecipe(hrecipe);

        ShapelessRecipe precipe = new ShapelessRecipe(new ItemStack(Material.STRING, 2));
        precipe.addIngredient(1, Material.FEATHER);
        Bukkit.getServer().addRecipe(precipe);
    }
}
