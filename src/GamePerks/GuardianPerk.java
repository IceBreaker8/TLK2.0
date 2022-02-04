package GamePerks;

import Main.Main;
import org.bukkit.*;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Item;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

public class GuardianPerk extends Perks {
    public GuardianPerk() {
        perk = ChatColor.BLUE + "" + ChatColor.BOLD + "Guardian";
        className = "GuardianPerk";
    }

    @Override
    public void activatePerk(Entity p) {
        p.getLocation().getWorld().playSound(p.getLocation(), Sound.ENTITY_ELDER_GUARDIAN_HURT, 7, 1);
        p.getLocation().getWorld().spawnParticle(Particle.WATER_SPLASH, p.getLocation(), 1000, 1F, 2F, 1F, 0.01);

        Location loc = p.getLocation();
        Item b1 = p.getWorld().dropItem(loc.add(0, 0.25, 0), new ItemStack(Material.PRISMARINE_SHARD, 1));
        Item b2 = p.getWorld().dropItem(loc.add(0, 0.25, 0), new ItemStack(Material.PRISMARINE_SHARD, 1));
        Item b3 = p.getWorld().dropItem(loc.add(0, 0.25, 0), new ItemStack(Material.PRISMARINE_SHARD, 1));
        Item b4 = p.getWorld().dropItem(loc.add(0, 0.25, 0), new ItemStack(Material.PRISMARINE_SHARD, 1));
        Item b5 = p.getWorld().dropItem(loc.add(0, 0.25, 0), new ItemStack(Material.PRISMARINE_SHARD, 1));
        Item b6 = p.getWorld().dropItem(loc.add(0, 0.25, 0), new ItemStack(Material.PRISMARINE_SHARD, 1));
        Item b7 = p.getWorld().dropItem(loc.add(0, 0.25, 0), new ItemStack(Material.PRISMARINE_SHARD, 1));
        Item b8 = p.getWorld().dropItem(loc.add(0, 0.25, 0), new ItemStack(Material.PRISMARINE_SHARD, 1));
        Item b9 = p.getWorld().dropItem(loc.add(0, 0.25, 0), new ItemStack(Material.PRISMARINE_SHARD, 1));
        ////
        b1.setPickupDelay(20 * 9999);
        b2.setPickupDelay(20 * 9999);
        b3.setPickupDelay(20 * 9999);
        b4.setPickupDelay(20 * 9999);
        b5.setPickupDelay(20 * 9999);
        b6.setPickupDelay(20 * 9999);
        b7.setPickupDelay(20 * 9999);
        b8.setPickupDelay(20 * 9999);
        b9.setPickupDelay(20 * 9999);
        ////
        b1.setVelocity(new Vector(0, 0.2, 0.1));
        b2.setVelocity(new Vector(0.1, 0.2, 0));
        b3.setVelocity(new Vector(0.1, 0.2, 0.1));
        b4.setVelocity(new Vector(0, 0.2, -0.1));
        b5.setVelocity(new Vector(-0.1, 0.2, 0));
        b6.setVelocity(new Vector(-0.1, 0.2, -0.1));
        b7.setVelocity(new Vector(-0.1, 0.2, 0.1));
        b8.setVelocity(new Vector(0.1, 0.2, -0.1));
        b9.setVelocity(new Vector(0.1, 0.2, -0.1));
        new BukkitRunnable() {

            @Override
            public void run() {
                b1.remove();
                b2.remove();
                b3.remove();
                b4.remove();
                b5.remove();
                b6.remove();
                b7.remove();
                b8.remove();
                b9.remove();
            }

        }.runTaskLater(Main.plugin, 16);


    }
}
