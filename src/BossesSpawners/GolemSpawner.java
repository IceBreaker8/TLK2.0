package BossesSpawners;

import Main.Main;
import Scoreboard.GameScoreboard;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.scheduler.BukkitRunnable;

public class GolemSpawner {
    static World w = Bukkit.getServer().getWorld("world");

    public static Location golemRedstonePos = new Location(w, 110, 38, 222);

    public static void golemTimer() {
        GameScoreboard.golemTimer = 500;
        new BukkitRunnable() {
            @Override
            public void run() {
                GameScoreboard.golemTimer -= 1;
                if (GameScoreboard.golemTimer <= 0) {
                    GameScoreboard.appearScoreboard();
                    cancel();
                    spawnGolem();
                    return;
                }
                GameScoreboard.appearScoreboard();
            }
        }.runTaskTimer(Main.plugin, 20, 20);
    }

    public static void spawnGolem() {
        golemRedstonePos.getBlock().setType(Material.REDSTONE_BLOCK);
        golemRedstonePos.getBlock().setType(Material.AIR);


    }
}
