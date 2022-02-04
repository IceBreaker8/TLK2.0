package BossesSpawners;

import GameMechanics.CrystalShooter;
import Main.Main;
import Scoreboard.GameScoreboard;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class TlkSpawn {

    static World w = Bukkit.getServer().getWorld("world");

    public static Location tlkRedstone = new Location(w, 98, 68, 20);

    public static boolean nerf = false;

    public static void tlkTimer() {
        GameScoreboard.tlkTimer = 950;
        new BukkitRunnable() {
            @Override
            public void run() {
                GameScoreboard.tlkTimer -= 1;
                if (GameScoreboard.tlkTimer <= 0) {
                    GameScoreboard.appearScoreboard();
                    spawnTlk();
                    //CRYSTAL NERF
                    if (nerf == false) {
                        nerf = true;
                        Bukkit.broadcastMessage(ChatColor.AQUA + "" + ChatColor.BOLD + "> Crystal Towers damage are lowered to 2 DMG per shot! <");
                        for (Player player : Bukkit.getServer().getOnlinePlayers()) {
                            player.playSound(player.getLocation(), Sound.BLOCK_GLASS_BREAK, 5, 1);
                        }
                    }
                    cancel();
                    return;
                }
                GameScoreboard.appearScoreboard();
            }
        }.runTaskTimer(Main.plugin, 20, 20);
    }

    public static void spawnTlk() {
        CrystalShooter.towerDamage = 2;
        tlkRedstone.getBlock().setType(Material.REDSTONE_BLOCK);
        tlkRedstone.getBlock().setType(Material.AIR);
    }
}
