package GameModeCore;

import ClassGUI.AaClassHashMapChoosing;
import Teams.TeamManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

public class DeathMessages implements Listener {

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent e) {
        Player victim = e.getEntity();
        if (victim instanceof Player) {
            Player killer = e.getEntity().getKiller();
            if(!(victim instanceof Player)) return;
            if(!(killer instanceof Player)) return;
            if (TeamManager.getTeam(killer) == null) return;
            if (AaClassHashMapChoosing.getTlkClass(killer) == null) return;
            if (TeamManager.getTeam(victim) == null) return;
            if (AaClassHashMapChoosing.getTlkClass(victim) == null) return;
            Bukkit.broadcastMessage(AaClassHashMapChoosing.getTlkClass(killer).classColor + "" + ChatColor.BOLD +
                    AaClassHashMapChoosing.getTlkClass(killer).className + " " +
                    TeamManager.getTeamColor(killer) + killer.getName() + ChatColor.WHITE + " eliminated " +
                    AaClassHashMapChoosing.getTlkClass(victim).classColor + "" + ChatColor.BOLD +
                    AaClassHashMapChoosing.getTlkClass(victim).className + " " +
                    TeamManager.getTeamColor(victim) + victim.getName() + ChatColor.WHITE);
        }

    }

}
