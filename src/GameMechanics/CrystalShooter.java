package GameMechanics;

import Main.Main;
import Teams.TeamManager;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

public class CrystalShooter {

    public World world = Bukkit.getWorld("world");

    private Location firstCrystalTowerLoc = new Location(world, 327, 74, -33); // shamans
    private Location firstShootLoc = new Location(world, 328, 95, -34);

    private Location secondCrystalTowerLoc = new Location(world, -106, 75, 23); // angels
    private Location secondShootLoc = new Location(world, -107, 96, 24);


    private Integer crystalTowerRange = 40;
    public static double towerDamage = 18;


    //TODO ADD TEAM NEUTRALITY AND BOSS IMMUNITY
    public void crystalTowerOn() {
        new BukkitRunnable() {

            @Override
            public void run() {
                if ((CrystalCore.firstCrystalAlive == false) && (CrystalCore.secondCrystalAlive == false)) {
                    cancel();
                    return;
                }
                for (Player p : Bukkit.getOnlinePlayers()) {
                    if (p.getLocation().distance(firstCrystalTowerLoc) < crystalTowerRange) {
                        if (p.getGameMode() == GameMode.SURVIVAL) {
                            if (CrystalCore.firstCrystalAlive == true) {
                                if (TeamManager.getTeam(p) != null) {
                                    if (TeamManager.getTeam(p) == "angels") {
                                        firstShootLoc.getWorld().playSound(firstShootLoc,
                                                Sound.ENTITY_ENDERDRAGON_FIREBALL_EXPLODE, 5, 1);
                                        firstShootLoc.getWorld().spawnParticle(Particle.FLAME, firstShootLoc
                                                , 1000, 1F, 1F, 1F, 0.3);
                                        p.damage(towerDamage);
                                        shootPlayer(p.getLocation(), firstShootLoc, 0.4);
                                        break;
                                    }
                                }
                            }
                        }
                    }
                }
                for (Player p : Bukkit.getOnlinePlayers()) {
                    if (p.getLocation().distance(secondCrystalTowerLoc) < crystalTowerRange) {
                        if (p.getGameMode() == GameMode.SURVIVAL) {
                            if (CrystalCore.secondCrystalAlive == true) {
                                if (TeamManager.getTeam(p) != null) {
                                    if (TeamManager.getTeam(p) == "shamans") {
                                        secondShootLoc.getWorld().playSound(secondShootLoc,
                                                Sound.ENTITY_ENDERDRAGON_FIREBALL_EXPLODE, 5, 1);
                                        secondShootLoc.getWorld().spawnParticle(Particle.FLAME, secondShootLoc
                                                , 1000, 1F, 1F, 1F, 0.3);
                                        p.damage(towerDamage);
                                        shootPlayer(p.getLocation(), secondShootLoc, 0.4);
                                        break;
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }.runTaskTimer(Main.plugin, 10, 60);
    }

    public void shootPlayer(Location point1, Location point2, double space) {
        World world = point1.getWorld();
        double distance = point1.distance(point2);
        Vector p1 = point1.toVector();
        Vector p2 = point2.toVector();
        Vector vector = p2.clone().subtract(p1).normalize().multiply(space);
        double length = 0;
        for (; length < distance; p1.add(vector)) {
            world.spawnParticle(Particle.FLAME, p1.getX(), p1.getY(), p1.getZ(), 4, 0.05F,
                    0.05F, 0.05F, 0.01);
            length += space;
        }
    }

}
