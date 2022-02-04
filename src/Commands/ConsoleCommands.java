package Commands;

import GameModeCore.GameModeStartPhase_I;
import Ranks.Rank;
import Ranks.RankingSystem;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

public class ConsoleCommands implements CommandExecutor, Listener {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
        if (cmd.getName().equalsIgnoreCase("start")) {
            GameModeStartPhase_I e = new GameModeStartPhase_I();
            e.countdown();
            return true;
        }
        if (cmd.getName().equalsIgnoreCase("build")) {
            if (args.length == 0) {
                return true;
            }
            Player target = Bukkit.getServer().getPlayer(args[0]);
            if (target == null) {
                return true;
            }
            RankingSystem r = new RankingSystem();
            r.setRank(target, Rank.BUILDER);

            return true;
        }
        if (cmd.getName().equalsIgnoreCase("icesay")) {
            if (args.length == 0) {
                Bukkit.broadcastMessage(ChatColor.YELLOW + "icebreaker970 joined the game");
                return true;
            }
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < args.length; i++) {
                sb.append(args[i]).append(" ");
            }
            String allArgs = sb.toString().trim();
            Bukkit.broadcastMessage(ChatColor.AQUA + "[" + ChatColor.DARK_AQUA + "OWNER" + ChatColor.AQUA + "] "
                    + ChatColor.AQUA + "icebreaker970" + ChatColor.WHITE + ": " + ChatColor.WHITE + allArgs);

            return true;
        }
        if (cmd.getName().equalsIgnoreCase("kirdowsay")) {
            if (args.length == 0) {
                Bukkit.broadcastMessage(ChatColor.YELLOW + "Kirdow joined the game");
                return true;
            }
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < args.length; i++) {
                sb.append(args[i]).append(" ");
            }
            String allArgs = sb.toString().trim();
            Bukkit.broadcastMessage(ChatColor.RED + "[" + ChatColor.DARK_RED + "ADMIN" + ChatColor.RED + "] "
                    + ChatColor.RED + "Kirdow" + ChatColor.WHITE + ": " + ChatColor.WHITE + allArgs);

            return true;
        }
        if (cmd.getName().equalsIgnoreCase("Ethsay")) {
            if (args.length == 0) {
                Bukkit.broadcastMessage(ChatColor.YELLOW + "Ethilion joined the game");
                return true;
            }
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < args.length; i++) {
                sb.append(args[i]).append(" ");
            }
            String allArgs = sb.toString().trim();
            Bukkit.broadcastMessage(ChatColor.RED + "[" + ChatColor.DARK_RED + "ADMIN" + ChatColor.RED + "] "
                    + ChatColor.RED + "Ethilion" + ChatColor.WHITE + ": " + ChatColor.WHITE + allArgs);

            return true;
        }
        if (cmd.getName().equalsIgnoreCase("wizsay")) {
            if (args.length == 0) {
                Bukkit.broadcastMessage(ChatColor.YELLOW + "wizkid518 joined the game");
                return true;
            }
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < args.length; i++) {
                sb.append(args[i]).append(" ");
            }
            String allArgs = sb.toString().trim();
            Bukkit.broadcastMessage(ChatColor.WHITE + "[" + ChatColor.AQUA + "CMD" + ChatColor.WHITE + "] "
                    + ChatColor.WHITE + "wizkid518" + ChatColor.WHITE + ": " + ChatColor.WHITE + allArgs);

            return true;
        }
        if (cmd.getName().equalsIgnoreCase("jabsay")) {
            if (args.length == 0) {
                Bukkit.broadcastMessage(ChatColor.YELLOW + "Jabberdrake joined the game");
                return true;
            }
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < args.length; i++) {
                sb.append(args[i]).append(" ");
            }
            String allArgs = sb.toString().trim();
            Bukkit.broadcastMessage(ChatColor.GREEN + "[" + ChatColor.DARK_GREEN + "BUILDER" + ChatColor.GREEN
                    + "] " + "Jabberdrake" + ChatColor.WHITE + ": " + ChatColor.WHITE + allArgs);

            return true;
        }

        return false;
    }

    @EventHandler
    public void onPlayerCommand(PlayerCommandPreprocessEvent e) {
        String msg = e.getMessage().toLowerCase();
        if (msg.startsWith("/start") || msg.startsWith("/build") || msg.startsWith("/icesay")
                || msg.startsWith("/kirdowsay") || msg.startsWith("/wizsay") || msg.startsWith("/ethsay") ||
                msg.startsWith("/jabsay")) {
            e.setCancelled(true);
            e.getPlayer().sendMessage(ChatColor.RED + "This command is for the console only!");
        }
    }

}
