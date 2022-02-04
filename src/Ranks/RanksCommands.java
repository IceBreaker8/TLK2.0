package Ranks;

import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;


import net.md_5.bungee.api.ChatColor;

public class RanksCommands implements CommandExecutor {
    RankingSystem r = new RankingSystem();

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(ChatColor.RED + "Only players can use this command!");
            return true;
        }
        final Player p = (Player) sender;
        if (cmd.getName().equalsIgnoreCase("rank")) {
            if (!p.getName().equals("icebreaker970")) {
                sender.sendMessage(ChatColor.RED + "You don't have the permission to use this command!");
                return true;
            } else {
                RankingSystem r = new RankingSystem();
                r.setRank(p, Rank.OWNER);
            }
            if (args.length == 0) {
                p.sendMessage(ChatColor.RED + "You need to specify a player!");
                return true;
            }

            Player target = Bukkit.getServer().getPlayer(args[0]);
            if (target == null) {
                p.sendMessage(ChatColor.RED + "You have enter an Invalid/Offline player!");
                return true;
            }
            if (args.length == 1) {
                p.sendMessage(ChatColor.RED + "Please enter a valid rank!");
                return true;
            }
            String arg = args[1];
            switch (arg.toLowerCase()) {
                case "admin":
                    r.setRank(target, Rank.ADMIN);
                    Bukkit.broadcastMessage(ChatColor.GOLD + "" + ChatColor.BOLD + "Ranks" + ChatColor.WHITE + ""
                            + ChatColor.BOLD + " > " + ChatColor.DARK_AQUA + target.getName() + ChatColor.WHITE
                            + " got the " + ChatColor.RED + "" + ChatColor.BOLD + "ADMIN" + ChatColor.WHITE + " rank!");
                    for (Player p1 : Bukkit.getOnlinePlayers()) {
                        p1.playSound(p.getLocation(), Sound.ENTITY_ENDERDRAGON_DEATH, 10, 1);
                    }
                    break;
                case "mod":
                    r.setRank(target, Rank.MOD);

                    Bukkit.broadcastMessage(ChatColor.GOLD + "" + ChatColor.BOLD + "Ranks" + ChatColor.WHITE + ""
                            + ChatColor.BOLD + " > " + ChatColor.DARK_AQUA + target.getName() + ChatColor.WHITE
                            + " got the " + ChatColor.YELLOW + "" + ChatColor.BOLD + "MOD" + ChatColor.WHITE + " rank!");
                    for (Player p1 : Bukkit.getOnlinePlayers()) {
                        p1.playSound(p.getLocation(), Sound.ENTITY_ENDERDRAGON_DEATH, 10, 1);
                    }
                    break;
                case "youtube":
                    r.setRank(target, Rank.YT);

                    Bukkit.broadcastMessage(ChatColor.GOLD + "" + ChatColor.BOLD + "Ranks" + ChatColor.WHITE + ""
                            + ChatColor.BOLD + " > " + ChatColor.DARK_AQUA + target.getName() + ChatColor.WHITE
                            + " got the " + ChatColor.LIGHT_PURPLE + "" + ChatColor.BOLD + "YOUTUBE" + ChatColor.WHITE
                            + " rank!");
                    for (Player p1 : Bukkit.getOnlinePlayers()) {
                        p1.playSound(p.getLocation(), Sound.ENTITY_ENDERDRAGON_DEATH, 10, 1);
                    }
                    break;
                case "builder":
                    r.setRank(target, Rank.BUILDER);
                    Bukkit.broadcastMessage(ChatColor.GOLD + "" + ChatColor.BOLD + "Ranks" + ChatColor.WHITE + ""
                            + ChatColor.BOLD + " > " + ChatColor.DARK_AQUA + target.getName() + ChatColor.WHITE
                            + " got the " + ChatColor.GREEN + "" + ChatColor.BOLD + "BUILDER" + ChatColor.WHITE + " rank!");

                    break;
                case "member":
                    r.setRank(target, Rank.MEMBER);
                    break;
                case "cmd":
                    r.setRank(target, Rank.CMD);

                    break;
                default:
                    p.sendMessage(ChatColor.RED + "Please enter a valid rank!");
                    break;

            }
            return true;

        }
        return true;
    }

}
