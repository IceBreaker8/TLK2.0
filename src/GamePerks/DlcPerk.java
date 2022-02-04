package GamePerks;

import Main.Main;
import org.bukkit.*;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Item;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

public class DlcPerk extends Perks {
    public DlcPerk() {
        perk = ChatColor.GOLD + "" + ChatColor.BOLD + "Da Last Chikken";
        className = "DlcPerk";
    }

    @Override
    public void activatePerk(Entity p) {
        p.getWorld().spawnParticle(Particle.DRAGON_BREATH, p.getLocation(), 200, 0.8F, 0.8F, 0.8F, 0.1);
        p.getWorld().playSound(p.getLocation(), Sound.ENTITY_CHICKEN_EGG, 5, 1);
        p.getWorld().playSound(p.getLocation(), Sound.ENTITY_CHICKEN_DEATH, 5, 1);
        p.getWorld().spawnParticle(Particle.CLOUD, p.getLocation().clone().add(0, 1, 0), 80, 0.8F, 0.8F, 0.8F, 0.1);
        Location loc = p.getLocation();
        Item b1 = p.getWorld().dropItem(loc.add(0, 0.25, 0), new ItemStack(Material.EGG, 1));
        Item b2 = p.getWorld().dropItem(loc.add(0, 0.25, 0), new ItemStack(Material.EGG, 1));
        Item b3 = p.getWorld().dropItem(loc.add(0, 0.25, 0), new ItemStack(Material.EGG, 1));
        Item b4 = p.getWorld().dropItem(loc.add(0, 0.25, 0), new ItemStack(Material.EGG, 1));
        Item b5 = p.getWorld().dropItem(loc.add(0, 0.25, 0), new ItemStack(Material.EGG, 1));
        Item b6 = p.getWorld().dropItem(loc.add(0, 0.25, 0), new ItemStack(Material.EGG, 1));
        Item b7 = p.getWorld().dropItem(loc.add(0, 0.25, 0), new ItemStack(Material.EGG, 1));
        Item b8 = p.getWorld().dropItem(loc.add(0, 0.25, 0), new ItemStack(Material.EGG, 1));
        Item b9 = p.getWorld().dropItem(loc.add(0, 0.25, 0), new ItemStack(Material.EGG, 1));
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

