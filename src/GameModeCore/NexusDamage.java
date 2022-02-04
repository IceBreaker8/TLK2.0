package GameModeCore;

import BossesSpawners.AngelAndShamanBosses;
import Classes.AaClassActionBar;
import GameMechanics.CrystalCore;
import Main.Main;
import Scoreboard.GameScoreboard;
import Scoreboard.PlayerScoreboard;
import Teams.TeamManager;
import org.bukkit.*;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.meta.FireworkMeta;
import org.bukkit.scheduler.BukkitRunnable;


public class NexusDamage implements Listener {

    static World w = Bukkit.getWorld("world");
    public Location endGameLocation = new Location(w, 84, 71, -218);
    public Location fireworkloc = new Location(w, 84, 70, -227);

    public static boolean weakAngel = false;
    public static boolean weakShaman = false;

    @EventHandler
    public void onNexusDmg(EntityDamageByEntityEvent e) {
        if (e.getDamager() instanceof Player) {
            Player damager = (Player) e.getDamager();
            Entity entity = e.getEntity();
            if (TeamManager.getTeam(damager) == null) return;
            if (entity instanceof Wither) {
                if (entity.getCustomName().contains("Angel") &&
                        TeamManager.getTeam(damager) == "shamans") {
                    if (CrystalCore.secondCrystalAlive) {
                        e.setCancelled(true);
                        return;
                    }
                    AaClassActionBar ac = new AaClassActionBar();
                    for (Player player : Bukkit.getServer().getOnlinePlayers()) {
                        if (TeamManager.getTeam(player) == "angels") {
                            ac.send(player, ChatColor.WHITE + "" + ChatColor.BOLD + "You wither is getting attacked!");
                            player.playSound(player.getLocation(), Sound.BLOCK_NOTE_PLING, 5, 1);
                        }
                    }
                    if (!weakAngel) damager.damage(3);
                    GameScoreboard g = new GameScoreboard();
                    if (weakAngel) {
                        GameScoreboard.angelsWither -= 15;
                    } else {
                        GameScoreboard.angelsWither -= 5;
                    }
                    g.appearScoreboard();
                    if (GameScoreboard.angelsWither <= 0) {
                        for (Player player : damager.getServer().getOnlinePlayers()) {
                            player.sendTitle(ChatColor.GREEN + "" + ChatColor.BOLD + "Shamans won the game!",
                                    ChatColor.GOLD + "" + ChatColor.BOLD + "Game Ended", 8, 20 * 10, 20);
                            player.teleport(endGameLocation);
                        }
                        g.deleteScoreboard();
                        endGameCounter();
                    }
                    return;
                }
            }
            if (entity instanceof Wither) {
                if (entity.getCustomName().contains("Shaman") &&
                        TeamManager.getTeam(damager) == "angels") {
                    if (CrystalCore.firstCrystalAlive) {
                        e.setCancelled(true);
                        return;
                    }
                    AaClassActionBar ac = new AaClassActionBar();
                    for (Player player : Bukkit.getServer().getOnlinePlayers()) {
                        if (TeamManager.getTeam(player) == "shamans") {
                            ac.send(player, ChatColor.GREEN + "" + ChatColor.BOLD + "You wither is getting attacked!");
                            player.playSound(player.getLocation(), Sound.BLOCK_NOTE_PLING, 5, 1);
                        }
                    }
                    if (!weakShaman) damager.damage(3);
                    GameScoreboard g = new GameScoreboard();
                    if (weakShaman) {
                        GameScoreboard.shamansWither -= 15;
                    } else {
                        GameScoreboard.shamansWither -= 5;
                    }
                    g.appearScoreboard();
                    if (GameScoreboard.shamansWither <= 0) {
                        for (Player player : damager.getServer().getOnlinePlayers()) {
                            player.sendTitle(ChatColor.WHITE + "" + ChatColor.BOLD + "Angels won the game!",
                                    ChatColor.GOLD + "" + ChatColor.BOLD + "Game Ended", 8, 20 * 10, 20);
                            player.teleport(endGameLocation);
                        }
                        g.deleteScoreboard();
                        endGameCounter();
                    }
                    return;
                }
            }
            if (entity != null && entity.getCustomName() != null &&
                    entity instanceof Wither && entity.getCustomName().contains("Shaman") &&
                    TeamManager.getTeam(damager) == "shamans") {
                e.setCancelled(true);
                return;
            }
            if (entity != null && entity.getCustomName() != null
                    && entity instanceof Wither && entity.getCustomName().contains("Angel") &&
                    TeamManager.getTeam(damager) == "angels") {
                e.setCancelled(true);
                return;
            }
        }
    }

    Location lobby = new Location(w, 61, 43, -1220);

    public void endGameCounter() {
        restoreMap();
        for (Player player : Bukkit.getServer().getOnlinePlayers()) {
            player.getInventory().clear();
            player.setMaxHealth(20);
            player.setPlayerListName(player.getName());
            player.setHealth(20);
            player.setInvulnerable(true);
            player.setFoodLevel(20);
            player.setGameMode(GameMode.SURVIVAL);
            player.setAllowFlight(false);
            player.setFlying(false);
        }
        Bukkit.broadcastMessage(ChatColor.GOLD + "" + ChatColor.BOLD + "Game restarting in 20 seconds..");
        new BukkitRunnable() {
            int count = 0;

            @Override
            public void run() {
                count += 1;
                Firework f = (Firework) fireworkloc.getWorld().spawn(fireworkloc, Firework.class);
                FireworkMeta fm = f.getFireworkMeta();
                fm.addEffect(FireworkEffect.builder()
                        .flicker(false)
                        .trail(false)
                        .withColor(Color.fromRGB(16755200))
                        .withColor(Color.fromRGB(5592575))
                        .withFade(Color.fromRGB(5592575))
                        .build());
                fm.setPower(0);
                f.setFireworkMeta(fm);
                f.detonate();
                if (count == 20) {
                    for (Player player : Bukkit.getServer().getOnlinePlayers()) {
                        player.kickPlayer(ChatColor.AQUA + "" +
                                ChatColor.BOLD + "Want to play again? Then rejoin the server!");
                    }
                    cancel();
                    Bukkit.getServer().reload();
                    return;
                }
            }
        }.runTaskTimer(Main.plugin, 0, 20);
    }

    public static void restoreMap() {
        //CRYSTALS
        new Location(w, -106, 75, 23).getBlock().setType(Material.SEA_LANTERN);
        new Location(w, 327, 74, -33).getBlock().setType(Material.SEA_LANTERN);
        //BONFIRE
        new Location(w, 311, 74, -29).getBlock().setType(Material.AIR);
        new Location(w, 312, 74, -29).getBlock().setType(Material.AIR);
        new Location(w, 312, 74, -30).getBlock().setType(Material.AIR);
        new Location(w, 311, 74, -30).getBlock().setType(Material.AIR);
        new Location(w, -94, 72, -16).getBlock().setType(Material.AIR);
        new Location(w, -95, 72, -16).getBlock().setType(Material.AIR);
        new Location(w, -95, 72, -15).getBlock().setType(Material.AIR);
        new Location(w, -94, 72, -15).getBlock().setType(Material.AIR);
        //BOSSES
        AngelAndShamanBosses a = new AngelAndShamanBosses();
        //CHICKEN SPAWNERS
        new Location(w, 341, 71, -165).getBlock().setType(Material.AIR);
        new Location(w, -94, 68, -167).getBlock().setType(Material.AIR);
        //SMELTER RESTORATION
        new Location(w, 270, 72, 167).getBlock().setType(Material.AIR);
        new Location(w, 271, 72, 167).getBlock().setType(Material.AIR);
        new Location(w, 273, 69, 168).getBlock().setType(Material.AIR);

        new Location(w, -12, 72, 153).getBlock().setType(Material.AIR);
        new Location(w, -13, 72, 153).getBlock().setType(Material.AIR);
        new Location(w, -15, 69, 154).getBlock().setType(Material.AIR);
        a.killBosses();
        killMiniBosses();
    }

    public static void killMiniBosses() {
        Chunk c = w.getChunkAt(new Location(w, 98, 69, 26));
        w.loadChunk(c);
        Chunk c1 = w.getChunkAt(new Location(w, 110, 40, 218));
        w.loadChunk(c1);
        for (Entity entity : Bukkit.getWorld("world").getEntities()) {
            if (entity instanceof Item) entity.remove();
            if (entity.getType().isAlive() && !(entity instanceof Player)) {
                entity.remove();
            }
        }
    }
}
