package ClassUpgrades;

import Scoreboard.GameScoreboard;
import Scoreboard.PlayerScoreboard;
import Teams.TeamManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;

public class SkillPointPerKill extends SkillPointsObtainer implements Listener {

    public static int skillPointPerKill = 1;

    @EventHandler
    public void onPlayerKill(EntityDeathEvent e) {
        Entity killer = e.getEntity().getKiller();
        Entity ded = e.getEntity();
        if (killer != null && killer instanceof Player) {
            if (ded instanceof Player) {
                if (TeamManager.getTeam((Player) killer) == null) return;
                for (Player player : Bukkit.getServer().getOnlinePlayers()) {
                    if (TeamManager.getTeam(player) != null &&
                            TeamManager.getTeam(player) == TeamManager.getTeam((Player) killer)) {
                        addSkillPoints(player, skillPointPerKill);
                        player.sendMessage(ChatColor.DARK_GREEN + "+ " + skillPointPerKill + " Skill Point");
                        GameScoreboard.appearScoreboard();
                    }
                }
            }
        }

    }
}
