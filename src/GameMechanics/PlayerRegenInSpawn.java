package GameMechanics;

import Teams.TeamManager;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

public class PlayerRegenInSpawn implements Listener {

    World w = Bukkit.getServer().getWorld("world");

    private Location shamanSpawnLoc = new Location(w, 360, 77, 0);
    private Location angelSpawnLoc = new Location(w, -139, 78, -10);

    @EventHandler
    public void regenPlayer(PlayerMoveEvent e) {
        Player p = e.getPlayer();
        if (TeamManager.getTeam(p) == null) return;
        if (p.getLocation().distance(shamanSpawnLoc) < 34 && TeamManager.getTeam(p) == "shamans") {
            if (p.isOnGround()) {
                healPlayer(p, 0.08F);
            }
        }
        if (p.getLocation().distance(angelSpawnLoc) < 34 && TeamManager.getTeam(p) == "angels") {
            if (p.isOnGround()) {
                healPlayer(p, 0.12F);
            }
        }

    }

    public void healPlayer(Player p, float heal) {
        if (p.getHealth() <= (p.getMaxHealth() - heal)) {
            p.setHealth(p.getHealth() + heal);
        } else if (p.getHealth() > (p.getMaxHealth() - heal) && p.getHealth() < p.getMaxHealth()) {
            p.setHealth(p.getMaxHealth());
        }
    }
}
