package Scoreboard;

import ClassGUI.AaClassHashMapChoosing;
import ClassUpgrades.SkillPointsCore;
import GameMechanics.CrystalCore;
import Teams.TeamManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.*;

public class GameScoreboard {

    public static int shamansWither = 1000;
    public static int angelsWither = 1000;
    public static int golemTimer = 500;
    public static int tlkTimer = 950;


    public static Scoreboard getScoreboard(Player p) {
        ScoreboardManager m = Bukkit.getScoreboardManager();
        Scoreboard b = m.getNewScoreboard();
        ////////
        Team shaman = b.registerNewTeam("shamans");
        Team angel = b.registerNewTeam("angels");
        shaman.setPrefix(ChatColor.DARK_GREEN + "" + ChatColor.BOLD + "Shaman " + ChatColor.GREEN);
        angel.setPrefix(ChatColor.WHITE + "" + ChatColor.BOLD + "Angel " + ChatColor.GRAY);
        shaman.setOption(Team.Option.NAME_TAG_VISIBILITY, Team.OptionStatus.ALWAYS);
        angel.setOption(Team.Option.NAME_TAG_VISIBILITY, Team.OptionStatus.ALWAYS);
        ////////
        for (Player player : Bukkit.getOnlinePlayers()) {
            if (TeamManager.getTeam(player) == null) continue;
            if (TeamManager.getTeam(player) == "shamans") {
                shaman.addPlayer(player);
            } else {
                angel.addPlayer(player);
            }
        }
        Objective o = b.registerNewObjective("gameObjective", "dummy");
        o.setDisplaySlot(DisplaySlot.SIDEBAR);

        o.setDisplayName(ChatColor.DARK_AQUA + "" + ChatColor.BOLD + "⚔ The Last Knight 2.0 ⚔");
        // SCORES
        Score sR = o.getScore(ChatColor.GREEN + "" + ChatColor.BOLD + "Shamans Team " + ChatColor.WHITE);
        sR.setScore(shamansWither);
        Score sB = o.getScore(ChatColor.WHITE + "" + ChatColor.BOLD + "Angels Team " + ChatColor.WHITE);
        sB.setScore(angelsWither);
        Score LB = o.getScore(ChatColor.GRAY + "" + ChatColor.BOLD + "Golem Spawn Time " + ChatColor.WHITE);
        LB.setScore(golemTimer);
        Score sCH = o.getScore(ChatColor.GOLD + "" + ChatColor.BOLD + "Knight Spawn Time " + ChatColor.WHITE);
        sCH.setScore(tlkTimer);

        Score se = o.getScore(ChatColor.GREEN + "" + ChatColor.BOLD + "> Shamans " +
                ChatColor.RED + "" + ChatColor.BOLD + "Crystal Tower");
        se.setScore(CrystalCore.firstCrystalHp);
        Score th = o.getScore(ChatColor.WHITE + "" + ChatColor.BOLD + "> Angels " +
                ChatColor.RED + "" + ChatColor.BOLD + "Crystal Tower");
        th.setScore(CrystalCore.secondCrystalHp);

        Score s = o.getScore("");
        s.setScore(-1);

        Score second = o.getScore(ChatColor.GOLD + "" + ChatColor.BOLD + "> Class : " +
                getTlkColor(p) + "" + ChatColor.BOLD + AaClassHashMapChoosing.getClass(p));
        second.setScore(-1);
        Score third = o.getScore(ChatColor.AQUA + "" + ChatColor.BOLD + "> Skill Points : " +
                ChatColor.WHITE + "" + ChatColor.BOLD + +SkillPointsCore.getSkillPoints(p));
        third.setScore(-1);
        return b;
    }

    public static ChatColor getTlkColor(Player p) {
        if (AaClassHashMapChoosing.getTlkClass(p) == null) {
            return ChatColor.WHITE;
        }
        return AaClassHashMapChoosing.getTlkClass(p).classColor;
    }

    public static void appearScoreboard() {
        deleteScoreboard();
        for (Player p : Bukkit.getOnlinePlayers()) {
            if (getScoreboard(p) == null)
                return;
            p.setScoreboard(getScoreboard(p));

        }
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




