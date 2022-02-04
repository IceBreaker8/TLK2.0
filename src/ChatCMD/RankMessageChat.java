package ChatCMD;

import Ranks.RankingSystem;
import Teams.TeamManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerChatEvent;

public class RankMessageChat implements Listener {

    @EventHandler
    public void playerChat(PlayerChatEvent e) {
        RankingSystem r = new RankingSystem();
        Player p = e.getPlayer();
        e.setCancelled(true);
        if (TeamManager.getTeam(p) == null) {
            tellConsole(r.getRank(p).getPrefix() + p.getName() + ChatColor.WHITE + ": "
                    + ChatColor.GRAY + e.getMessage());
        } else {
            tellConsole(TeamManager.getTeamPrefix(p) + ChatColor.WHITE + ""
                    + ChatColor.BOLD + "> " + r.getRank(p).getPrefix() + p.getName() + ChatColor.WHITE + ": "
                    + ChatColor.GRAY + e.getMessage());
        }
        if (TeamManager.getTeam(p) != null) {
            for (Player player : Bukkit.getOnlinePlayers()) {
                if (TeamManager.getTeam(player) == TeamManager.getTeam(p)) {
                    player.sendMessage(TeamManager.getTeamPrefix(p) + ChatColor.WHITE + ""
                            + ChatColor.BOLD + "> " + r.getRank(p).getPrefix() + p.getName() + ChatColor.WHITE + ": "
                            + ChatColor.GRAY + e.getMessage());
                }
            }
            return;
        }
        for (Player player : Bukkit.getOnlinePlayers()) {
            player.sendMessage(r.getRank(p).getPrefix() + p.getName() + ChatColor.WHITE + ": "
                    + ChatColor.GRAY + e.getMessage());
        }
    }

    public void tellConsole(String message) {
        Bukkit.getConsoleSender().sendMessage(message);
    }

}
