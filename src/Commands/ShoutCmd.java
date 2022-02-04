package Commands;

import Teams.TeamManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ShoutCmd implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(ChatColor.RED + "You cannot execute this command from console!");
            return true;
        }
        Player p = (Player) sender;
        if (cmd.getName().equalsIgnoreCase("shout")) {
            if (TeamManager.getTeam(p) != null) {
                if (args.length == 0) {
                    p.sendMessage(ChatColor.RED + "Enter a valid message!");
                    return true;
                }
                StringBuilder sb = new StringBuilder();
                for (int i = 0; i < args.length; i++) {
                    sb.append(args[i]).append(" ");
                }
                String allArgs = sb.toString().trim();
                Bukkit.broadcastMessage(TeamManager.getTeamColor(p) + "" + ChatColor.BOLD +
                        TeamManager.getTeam(p).toUpperCase() + " " + ChatColor.GRAY +""+
                        ChatColor.BOLD+ p.getName() + ": " + ChatColor.WHITE
                        + allArgs);
            }
            return true;
        }
        return false;
    }
}
