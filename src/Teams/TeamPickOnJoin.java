package Teams;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class TeamPickOnJoin implements Listener {

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e) {
        Player p = e.getPlayer();
        if (TeamManager.getTeam(p) == null) return;
            TeamManager.setTeamPrefix(p);
            //TODO TP TO SPAWN AND STUFF
    }

}
