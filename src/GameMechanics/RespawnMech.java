package GameMechanics;

import Classes.Flora;
import Main.Main;
import Teams.TeamManager;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

public class RespawnMech implements Listener {

    private int respawnTime = 6 * 20;
    private World w = Bukkit.getWorld("world");
    private Location shamanSpawn = new Location(w, 339, 72, -11);
    private Location angelSpawn = new Location(w, -118, 73, 1);
    Location lobby = new Location(w, 61, 43, -1220);

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent e) {
        if (e.getEntity() instanceof Player) {
            Player p = (Player) e.getEntity();
            Flora.revive.put(p, p.getLocation());
            for (ItemStack item : p.getInventory().getContents()) {
                if (item != null) {
                    if ((item.getType().equals(Material.IRON_INGOT) || (item.getType().equals(Material.GOLD_INGOT)
                            || (item.getType().equals(Material.FEATHER)) || (item.getType().equals(Material.DIAMOND))
                            || (item.getType().equals(Material.COAL)) || (item.getType().equals(Material.MAGMA_CREAM))))) {
                        item.setAmount(0);
                    }
                }
            }
            p.setHealth(p.getMaxHealth());
            e.setDeathMessage(null);
            Location loc = p.getLocation();
            respawnRun(p, loc);
        }
    }

    public void respawnRun(Player p, Location pLoc) {
        p.sendTitle(ChatColor.DARK_RED + "" + ChatColor.RED + "You have Died!",
                ChatColor.WHITE + "" + ChatColor.BOLD + "Respawning in " + ChatColor.GREEN + "" + ChatColor.BOLD + ""
                        + "6" + ChatColor.WHITE + "" + ChatColor.BOLD + "" + "...",
                0, 20 * 3, 20);
        p.teleport(p.getLocation().add(0, 50, 0));
        p.setAllowFlight(true);
        p.setFlying(true);
        p.setGameMode(GameMode.SPECTATOR);
        new BukkitRunnable() {
            int t = respawnTime + 20;

            @Override
            public void run() {
                p.getWorld().spawnParticle(Particle.CLOUD, pLoc.clone().add(0, 1, 0), 10, 0.1F, 0.1F, 0.1F, 0.001);
                if (t == 20) {
                    Flora.revive.remove(p);
                    p.setGameMode(GameMode.SURVIVAL);
                    p.setFlying(false);
                    p.setAllowFlight(false);
                    p.showPlayer(p);
                    p.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 6 * 20, 80));
                    p.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 6 * 20, 80));
                    p.sendTitle(null, null, 0, 0, 0);
                    p.playSound(p.getLocation(), Sound.BLOCK_NOTE_PLING, 10, 1);
                    if (TeamManager.getTeam(p) != null) {
                        if (TeamManager.getTeam(p) == "shamans") {
                            p.teleport(shamanSpawn);
                        }
                        if (TeamManager.getTeam(p) == "angels") {
                            p.teleport(angelSpawn);
                        }
                    } else {
                        p.teleport(lobby);
                    }

                    p.setLevel(100);
                    cancel();
                    return;
                }
                if (t > 0) {

                    p.sendTitle(ChatColor.DARK_RED + "" + ChatColor.RED + "You have Died!",
                            ChatColor.WHITE + "" + ChatColor.BOLD + "Respawning in " + ChatColor.GREEN + ""
                                    + ChatColor.BOLD + "" + (int) (t / 20) + ChatColor.WHITE + "" + ChatColor.BOLD + "" + "...",
                            0, 20 * 3, 20);
                }
                // TODO reviving
                if (Flora.rez.contains(p.getUniqueId())) {
                    p.sendMessage(ChatColor.GOLD + "" + ChatColor.BOLD + "You have been revived!");
                    p.teleport(Flora.revive.get(p));
                    Flora.revive.remove(p);
                    Flora.rez.remove(p.getUniqueId());
                    p.setGameMode(GameMode.SURVIVAL);
                    p.setFlying(false);
                    p.setAllowFlight(false);
                    p.setLevel(100);
                    p.sendTitle(null, null, 0, 0, 0);
                    p.addPotionEffect(new PotionEffect(PotionEffectType.ABSORPTION, 2 * 20, 2));
                    p.getWorld().playSound(p.getLocation(), Sound.ITEM_TOTEM_USE, 5, 1);
                    p.getWorld().spawnParticle(Particle.TOTEM, p.getLocation(), 200, 0.001F, 0.001F, 0.001F, 1);
                    cancel();
                    return;
                }

                t -= 1;

            }

        }.runTaskTimer(Main.plugin, 0, 1);
    }
}
