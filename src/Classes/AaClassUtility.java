package Classes;

import GameMechanics.ManaSystem;
import GameMechanics.UltChargeMechanics;
import Main.Main;
import Teams.TeamManager;
import net.minecraft.server.v1_12_R1.AttributeInstance;
import net.minecraft.server.v1_12_R1.EntityInsentient;
import net.minecraft.server.v1_12_R1.GenericAttributes;
import net.minecraft.server.v1_12_R1.PathEntity;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.craftbukkit.v1_12_R1.entity.CraftEntity;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Zombie;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.BlockIterator;
import org.bukkit.util.Vector;

public abstract class AaClassUtility {

    public Entity getTarget(Player player) {
        BlockIterator iterator = new BlockIterator(player.getWorld(), player
                .getLocation().toVector(), player.getEyeLocation()
                .getDirection(), 0, 100);
        Entity target = null;
        while (iterator.hasNext()) {
            Block item = iterator.next();
            for (Entity entity : player.getNearbyEntities(100, 100, 100)) {
                int acc = 1;
                for (int x = -acc; x < acc; x++)
                    for (int z = -acc; z < acc; z++)
                        for (int y = -acc; y < acc; y++)
                            if (entity.getLocation().getBlock()
                                    .getRelative(x, y, z).equals(item)) {
                                return target = entity;
                            }
            }
        }
        return target;
    }

    public void freezePlayer(Entity e, Integer time) {
        Location loc = e.getLocation();
        FrostBite.frozen.add(e.getUniqueId());
        new BukkitRunnable() {
            int count = 0;

            @Override
            public void run() {
                count += 1;
                e.teleport(loc);
                if (count == time) {
                    FrostBite.frozen.remove(e.getUniqueId());
                    cancel();

                }

            }
        }.runTaskTimer(Main.plugin, 0, 1);
    }

    public boolean thereAreNoEntities(Player p, Integer distance) {
        int count = 0;
        for (Entity entity : p.getWorld().getNearbyEntities(p.getLocation(), distance, distance, distance)) {
            if (!entity.equals(p) && entity.getType().isAlive()) {
                if (!(entity instanceof Zombie)) {
                    if (TeamManager.arentSameTeam(p, entity)) {
                        count += 1;
                    }
                }
            }
        }
        if (count == 0) {
            return true;
        }
        return false;
    }

    public void hidePlayers(Player p) {
        if (!p.isOnline()) return;
        for (Player player : p.getServer().getOnlinePlayers()) {
            player.hidePlayer(p);
        }
    }

    public void showPlayers(Player p) {
        if (!p.isOnline()) return;
        for (Player player : p.getServer().getOnlinePlayers()) {
            player.showPlayer(p);
        }
    }

    public void PetFollow(final Player player, final Entity pet, final double speed) {
        new BukkitRunnable() {
            public void run() {
                if ((!pet.isValid() || (!player.isOnline()))) {
                    this.cancel();
                }
                net.minecraft.server.v1_12_R1.Entity pett = ((CraftEntity) pet).getHandle();
                ((EntityInsentient) pett).getNavigation().a(1.00D);
                Object petf = ((CraftEntity) pet).getHandle();
                Location targetLocation = player.getLocation();
                PathEntity path;
                path = ((EntityInsentient) petf).getNavigation().a(targetLocation.getX() + 0.1, targetLocation.getY(), targetLocation.getZ() + 0.1);
                if (path != null) {
                    ((EntityInsentient) petf).getNavigation().a(path, 1D);
                    ((EntityInsentient) petf).getNavigation().a(1D);
                }
                AttributeInstance attributes = ((EntityInsentient) ((CraftEntity) pet).getHandle()).getAttributeInstance(GenericAttributes.MOVEMENT_SPEED);
                attributes.setValue(speed);
            }
        }.runTaskTimer(Main.plugin, 0L, 1);
    }

    public void removeMana(Player p, Integer Mana) {
        ManaSystem ma = new ManaSystem();
        ma.setMana(p, ma.getMana(p) - Mana);

    }

    public void removeUlt(Player p) {
        UltChargeMechanics u = new UltChargeMechanics();
        u.setUlt(p, 0);
    }

    public void setTarget(Player attacker, LivingEntity target) {
        target.damage(0.1, attacker);
        target.setVelocity(new Vector(0, 0, 0));

    }

    public boolean checkSpectator(Entity e) {
        if (e != null) {
            if (e.getType().isAlive()) {
                if (e instanceof Player) {
                    if (((Player) e).getGameMode().equals(GameMode.SPECTATOR)) {
                        return false;
                    }
                }
            }
        }
        return true;
    }
}
