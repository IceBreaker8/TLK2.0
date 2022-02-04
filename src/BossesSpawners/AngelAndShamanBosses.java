package BossesSpawners;

import Main.Main;
import org.bukkit.*;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Wither;
import org.bukkit.scheduler.BukkitRunnable;

public class AngelAndShamanBosses {
    World w = Bukkit.getServer().getWorld("world");

    private Location shamanSpawnLoc = new Location(w, 360, 77, 0);
    private Location angelSpawnLoc = new Location(w, -139, 78, -10);

    public void spawnShamanBoss() {
        Chunk c = w.getChunkAt(shamanSpawnLoc);
        w.loadChunk(c);
        new BukkitRunnable() {
            @Override
            public void run() {
                Wither shaman = (Wither) w.spawnEntity(shamanSpawnLoc, EntityType.WITHER);
                shaman.setAI(false);
                shaman.setCustomName(ChatColor.GREEN + "" + ChatColor.BOLD + "Shaman team Boss");
                shaman.setCustomNameVisible(true);
                shaman.setMaxHealth(1400);
                shaman.setHealth(1400);
            }
        }.runTaskLater(Main.plugin, 4);
    }

    public void spawnAngelBoss() {
        Chunk c = w.getChunkAt(angelSpawnLoc);
        w.loadChunk(c);
        new BukkitRunnable() {
            @Override
            public void run() {
                Wither angel = (Wither) w.spawnEntity(angelSpawnLoc, EntityType.WITHER);
                angel.setAI(false);
                angel.setCustomName(ChatColor.WHITE + "" + ChatColor.BOLD + "Angel team Boss");
                angel.setCustomNameVisible(true);
                angel.setMaxHealth(1400);
                angel.setHealth(1400);
            }
        }.runTaskLater(Main.plugin, 4);
    }


    public void killBosses() {
        Chunk c = w.getChunkAt(shamanSpawnLoc);
        w.loadChunk(c);
        for (Entity entity : Bukkit.getWorld("world").getEntities()) {
            if (entity.getType().isAlive()) {
                if (entity instanceof Wither) {
                    entity.remove();
                }
            }
        }
    }

}
