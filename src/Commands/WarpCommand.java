package Commands;

import ClassGUI.AaClassHashMapChoosing;
import Scoreboard.TLKScoreboardManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class WarpCommand implements CommandExecutor {
    World w = Bukkit.getWorld("world");
    Location oldTlkMap = new Location(w, 1191, 30, 267);
    Location lobby = new Location(w, 61, 43, -1220);
    Location tlk2Map = new Location(w, 6, 74, 40);
    Location build = new Location(w, -10298, 7, -10102);


    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(ChatColor.RED + "You cannot execute this command from console!");
            return true;
        }
        if (TLKScoreboardManager.gameHasStarted == true) return true;
        Player p = (Player) sender;
        if (!p.isOp()) return true;
        AaClassHashMapChoosing player = new AaClassHashMapChoosing();
        if (cmd.getName().equalsIgnoreCase("warp")) {
            if (args.length == 0) {
                p.sendMessage(ChatColor.GRAY + "Available locations: oldtlkmap , tlk2map , lobby , build");
                return true;
            }
            switch (args[0].toLowerCase()) {
                case "oldtlkmap":
                    p.teleport(oldTlkMap);
                    break;
                case "tlk2map":
                    p.teleport(tlk2Map);
                    break;
                case "lobby":
                    p.teleport(lobby);
                    break;
                case "build":
                    p.teleport(build);
                    break;
                default:
                    p.sendMessage(ChatColor.RED + "Location unknown, try these locations: "
                            + ChatColor.GRAY + "oldtlkmap , tlk2map , lobby , build");

            }
            return true;
        }
        return false;
    }
}
