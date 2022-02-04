package GameMechanics;

import Scoreboard.TLKScoreboardManager;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

public class WaterDamage implements Listener {

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent e) {
        Player p = e.getPlayer();
        Material m = p.getLocation().getBlock().getType();
        if (TLKScoreboardManager.gameHasStarted) {
            if (m == Material.STATIONARY_WATER || m == Material.WATER) {
                p.damage(4);
            }
        }
    }

}
