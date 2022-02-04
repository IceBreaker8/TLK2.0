package Teams;

import org.bukkit.ChatColor;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import java.util.HashSet;
import java.util.UUID;

public class TeamManager {
    public static HashSet<UUID> angels = new HashSet<>(); // Angels
    public static HashSet<UUID> shamans = new HashSet<>(); // Shamans

    public static String getTeam(Player p) {
        if (angels.contains(p.getUniqueId())) {
            return "angels";
        } else if (shamans.contains(p.getUniqueId())) {
            return "shamans";
        }
        return null;
    }

    public static void setTeam(Player p, String team) {
        if (team == "shamans") {
            removeTeams(p);
            shamans.add(p.getUniqueId());
        } else if (team == "angels") {
            removeTeams(p);
            angels.add(p.getUniqueId());
        }
        setTeamPrefix(p);
    }

    public static void removeTeams(Player p) {
        angels.remove(p.getUniqueId());
        shamans.remove(p.getUniqueId());
    }

    public static String getTeamPrefix(Player p) {
        if (shamans.contains(p.getUniqueId())) {
            return ChatColor.DARK_GREEN + "" + ChatColor.BOLD + "Shaman ";
        } else if (angels.contains(p.getUniqueId())) {
            return ChatColor.WHITE + "" + ChatColor.BOLD + "Angel ";
        }
        return null;
    }

    public static ChatColor getTeamColor(Player p) {
        if (shamans.contains(p.getUniqueId())) {
            return ChatColor.GREEN;
        } else if (angels.contains(p.getUniqueId())) {
            return ChatColor.WHITE;
        }
        return null;
    }

    public static boolean arentSameTeam(Player p, Entity target) {
        if (!(target instanceof Player)) return true;
        if (getTeam(p) == null) return true;
        if (getTeam((Player) target) == null) return true;
        return !getTeam(p).equals(getTeam((Player) target));
    }

    public static void setTeamPrefix(Player p) {
        if (getTeam(p) == null) {
            p.setPlayerListName(p.getName());
            return;
        }
        p.setPlayerListName(getTeamPrefix(p) + ChatColor.WHITE + p.getName());
    }
}
