package BossesSpawners;

import org.bukkit.*;
import org.bukkit.entity.Chicken;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Wither;

public class ChickenSpawner {

    World w = Bukkit.getServer().getWorld("world");

    private Location shamanRedstoneChikken = new Location(w, 341, 70, -166);
    private Location angelRedstoneChikken = new Location(w, -94, 67, -168);

    public void spawnChicken(String team) {
        if (team == "shamans") {
            shamanRedstoneChikken.getBlock().setType(Material.REDSTONE_BLOCK);
            shamanRedstoneChikken.getBlock().setType(Material.AIR);

        }
        if (team == "angels") {
            angelRedstoneChikken.getBlock().setType(Material.REDSTONE_BLOCK);
            angelRedstoneChikken.getBlock().setType(Material.AIR);

        }
    }
}
