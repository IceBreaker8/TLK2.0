package GameMechanics;

import BossesSpawners.ChickenSpawner;
import Scoreboard.GameScoreboard;
import Teams.TeamManager;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

public class CrystalCore implements Listener {

    public World world = Bukkit.getWorld("world");

    public static Boolean firstCrystalAlive = true;
    public static Boolean secondCrystalAlive = true;

    public static Integer firstCrystalHp = 40;
    private Location firstCrystalLocation = new Location(world, 327, 74, -33); // shamans

    public static Integer secondCrystalHp = 40;
    private Location secondCrystalLocation = new Location(world, -106, 75, 23); // angels

    GameScoreboard gm = new GameScoreboard();

    @EventHandler
    public void onPlayerDestroyBlock(BlockBreakEvent e) {
        Block block = e.getBlock();
        Player player = e.getPlayer();
        if (TeamManager.getTeam(player) == null) return;
        if (block.getType().equals(Material.SEA_LANTERN)) {
            e.setCancelled(true);
            if (block.getLocation().distance(firstCrystalLocation) < 1) {
                if(TeamManager.getTeam(player) == "shamans") return;
                firstCrystalHp -= 1;
                firstCrystalLocation.getWorld().spawnParticle(Particle.FLAME, firstCrystalLocation
                        , 200, 1F, 1F, 1F, 0.3);
                gm.appearScoreboard();
                if (firstCrystalHp == 0) {
                    block.setType(Material.BEDROCK);
                    Bukkit.broadcastMessage(ChatColor.GREEN + "" + ChatColor.BOLD + "> Shamans " +
                            ChatColor.RED + "" + ChatColor.BOLD + "Crystal Tower got destroyed!");
                    ChickenSpawner ch = new ChickenSpawner();
                    ch.spawnChicken("angels");
                    firstCrystalAlive = false;
                    for (Player p : Bukkit.getOnlinePlayers()) {
                        p.playSound(p.getLocation(), Sound.ENTITY_ENDERDRAGON_FIREBALL_EXPLODE, 5, 1);
                    }
                }
            }
            if (block.getLocation().distance(secondCrystalLocation) < 1) {
                if(TeamManager.getTeam(player) == "angels") return;
                secondCrystalHp -= 1;
                secondCrystalLocation.getWorld().spawnParticle(Particle.FLAME, secondCrystalLocation
                        , 200, 1F, 1F, 1F, 0.3);
                gm.appearScoreboard();
                if (secondCrystalHp == 0) {
                    block.setType(Material.BEDROCK);
                    Bukkit.broadcastMessage(ChatColor.WHITE + "" + ChatColor.BOLD + "> Angels " +
                            ChatColor.RED + "" + ChatColor.BOLD + "Crystal Tower got destroyed!");
                    ChickenSpawner ch = new ChickenSpawner();
                    ch.spawnChicken("shamans");
                    secondCrystalAlive = false;
                    for (Player p : Bukkit.getOnlinePlayers()) {
                        p.playSound(p.getLocation(), Sound.ENTITY_ENDERDRAGON_FIREBALL_EXPLODE, 5, 1);
                    }
                }
            }
        }

    }
}
