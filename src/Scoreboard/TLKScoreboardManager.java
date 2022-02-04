package Scoreboard;

import Classes.AaClassUtility;
import GameModeCore.GameModeStartPhase_I;
import Main.Main;
import Ranks.RankingSystem;
import Teams.TeamManager;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.scheduler.BukkitRunnable;

public class TLKScoreboardManager extends AaClassUtility implements Listener {

    private int minimumPlayers = 6;
    public static boolean gameCounterInitiated = false;
    public static Integer count = 60;
    public static boolean gameHasStarted = false;

    private World w = Bukkit.getWorld("world");
    private Location shamanSpawn = new Location(w, 339, 72, -11);
    private Location angelSpawn = new Location(w, -118, 73, 1);

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent e) {
        if (gameHasStarted) return;
        PlayerScoreboard.appearScoreboard();
    }


    Location lobby = new Location(w, 61, 43, -1220);

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e) {
        RankingSystem r = new RankingSystem();
        Player p = e.getPlayer();
        if (r.getRank(p) != null) {
            e.setJoinMessage(ChatColor.GOLD + "" + ChatColor.BOLD + "> " + r.getRank(p).getPrefix() + p.getName() +
                    ChatColor.GRAY + " has joined!" + ChatColor.GOLD + "" + ChatColor.BOLD + " <");
        }
        p.setInvulnerable(false);
        for (Player player : p.getServer().getOnlinePlayers()) {
            showPlayers(p);
        }
        if (!gameHasStarted) {
            p.setLevel(100);
            p.getInventory().clear();
            PlayerScoreboard.appearScoreboard();
            p.teleport(lobby);
            p.setGameMode(GameMode.ADVENTURE);
            p.setInvulnerable(true);
        } else {
            GameScoreboard.appearScoreboard();
            if (TeamManager.getTeam(p) == null) {
                p.teleport(shamanSpawn);
                p.setGameMode(GameMode.SPECTATOR);
                p.sendMessage(ChatColor.GOLD + "You can spectate until the game ends, ghosting is bannable!");
            }
            if (TeamManager.getTeam(p) != null) {
                if (TeamManager.getTeam(p) == "shamans") {
                    TeamManager.setTeamPrefix(p);
                    p.teleport(shamanSpawn);
                }
                if (TeamManager.getTeam(p) == "angels") {
                    TeamManager.setTeamPrefix(p);
                    p.teleport(angelSpawn);
                }
            }
        }
        if (gameHasStarted) return;
        if ((Bukkit.getOnlinePlayers().size() >= minimumPlayers) && (gameCounterInitiated == false)) {
            gameCounterInitiated = true;
            new BukkitRunnable() {
                @Override
                public void run() {

                    PlayerScoreboard.appearScoreboard();
                    if (Bukkit.getOnlinePlayers().size() < minimumPlayers) {
                        gameCounterInitiated = false;
                        count = 30;
                        cancel();
                    }
                    if (count == 0) {
                        GameModeStartPhase_I g = new GameModeStartPhase_I();
                        g.countdown();
                        gameHasStarted = true;
                        for (Player player : Bukkit.getOnlinePlayers()) {
                            player.playSound(player.getLocation(), Sound.BLOCK_NOTE_PLING, 5, 1);
                        }
                        PlayerScoreboard.appearScoreboard();
                        cancel();
                        return;
                    }
                    count -= 1;
                    if (count <= 10) {
                        for (Player player : Bukkit.getOnlinePlayers()) {
                            player.playSound(player.getLocation(), Sound.UI_BUTTON_CLICK, 5, 1);
                        }
                    }
                }
            }.runTaskTimer(Main.plugin, 0, 20);

        }
    }

    public static String getMessage() {
        if (gameHasStarted) {
            return ChatColor.GREEN + "" + ChatColor.BOLD + "Choose a team..";
        } else if (gameCounterInitiated) {
            return ChatColor.AQUA + "" + ChatColor.BOLD + "Game Starting in " + count;
        }
        return ChatColor.AQUA + "" + ChatColor.BOLD + "Waiting for players..";
    }

}
