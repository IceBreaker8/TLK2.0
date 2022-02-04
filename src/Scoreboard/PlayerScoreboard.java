package Scoreboard;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.*;

public class PlayerScoreboard {

    public static Scoreboard getPlayerScoreboard() {
        ScoreboardManager m = Bukkit.getScoreboardManager();
        Scoreboard b1 = m.getNewScoreboard();

        Objective o = b1.registerNewObjective("gameObjective", "dummy");
        o.setDisplaySlot(DisplaySlot.SIDEBAR);

        o.setDisplayName(ChatColor.DARK_AQUA + "" + ChatColor.BOLD + "Welcome to TLK2.0 Server");
        // SCORES
        Score first = o.getScore(ChatColor.WHITE + "       ");
        first.setScore(7);
        Score second = o.getScore(TLKScoreboardManager.getMessage() + "");
        second.setScore(6);
        Score third = o.getScore(ChatColor.RED + "             ");
        third.setScore(5);
        Score fourth = o.getScore(ChatColor.GRAY + "" + ChatColor.BOLD + "Players");
        fourth.setScore(4);
        Score fifth = o.getScore(ChatColor.WHITE + "" + Bukkit.getOnlinePlayers().size() + "/12");
        fifth.setScore(3);
        Score sixth = o.getScore("             ");
        sixth.setScore(2);
        Score seventh = o.getScore(ChatColor.GOLD + "" + ChatColor.BOLD + "Game Mode:");
        seventh.setScore(1);
        Score eighth = o.getScore(ChatColor.DARK_AQUA + "" + ChatColor.BOLD + "⚔ The Last Knight 2.0 ⚔");
        eighth.setScore(0);
        return b1;

    }

    public static void appearScoreboard() {
        for (Player p : Bukkit.getOnlinePlayers()) {
            if (getPlayerScoreboard() == null)
                return;
            p.setScoreboard(getPlayerScoreboard());

        }
    }

    public static void appearScoreboardForOnePlayer(Player p) {
        p.setScoreboard(getPlayerScoreboard());
    }

    public static void deleteScoreboard() {
        ScoreboardManager m = Bukkit.getScoreboardManager();
        Scoreboard bnull = m.getNewScoreboard();

        Objective onull = bnull.registerNewObjective("nullObjective", "dummy");
        onull.setDisplaySlot(DisplaySlot.SIDEBAR);

        onull.setDisplayName("");
        for (Player p : Bukkit.getOnlinePlayers()) {
            p.setScoreboard(bnull);
        }
    }


}
