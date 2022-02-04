package GameModeCore;

import Main.Main;
import Scoreboard.GameScoreboard;
import Scoreboard.PlayerScoreboard;
import Scoreboard.TLKScoreboardManager;
import Teams.TeamChoose;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class GameModeStartPhase_I {

    World w = Bukkit.getWorld("world");
    Location oldTlkMap = new Location(w, 1191, 30, 267);

    public void countdown() {
        TLKScoreboardManager.gameHasStarted = true;
        TLKScoreboardManager.gameCounterInitiated = true;
        for (Player p : Bukkit.getServer().getOnlinePlayers()) {
            p.teleport(oldTlkMap);
            p.setLevel(100);
            PlayerScoreboard.appearScoreboard();
        }
        for (Player p : Bukkit.getServer().getOnlinePlayers()) {
            p.setGameMode(GameMode.ADVENTURE);
            TeamChoose.giveTeamItems(p);
            p.sendMessage(ChatColor.GOLD + "" + ChatColor.BOLD + "Game starts in " + 30 + " seconds..");
        }
        new BukkitRunnable() {
            int count = 30;

            @Override
            public void run() {
                count -= 1;
                if (count == 0) {
                    for (Player p : Bukkit.getServer().getOnlinePlayers()) {
                        p.sendMessage(ChatColor.GOLD + "" + ChatColor.BOLD + "Game Started!");
                        p.playSound(p.getLocation(), Sound.BLOCK_NOTE_PLING, 5, 1);
                        p.getInventory().clear();
                    }
                    GameModeStartPhase_II g = new GameModeStartPhase_II();
                    g.initiateTeleportSequence();
                    cancel();
                    return;
                }
                if (count <= 10) {
                    for (Player p : Bukkit.getServer().getOnlinePlayers()) {
                        p.playSound(p.getLocation(), Sound.UI_BUTTON_CLICK, 5, 1);
                        p.sendMessage(ChatColor.GOLD + "" + ChatColor.BOLD + "Game starts in " + count + " seconds..");
                    }
                }
            }
        }.runTaskTimer(Main.plugin, 20, 20);
    }
}
