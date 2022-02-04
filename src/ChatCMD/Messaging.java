package ChatCMD;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandExecutor;

import java.util.HashMap;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

public class Messaging implements CommandExecutor {
    public HashMap<UUID, Integer> cooldownTimeP;
    public HashMap<UUID, BukkitRunnable> cooldownTaskP;

    public Messaging() {
        cooldownTimeP = new HashMap<UUID, Integer>();
        cooldownTaskP = new HashMap<UUID, BukkitRunnable>();
    }

    public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(org.bukkit.ChatColor.RED + "You cannot send messages via console!");
            return true;
        }
        // /msg command
        Player p = (Player) sender;
        if (cmd.getName().equalsIgnoreCase("msg")) {
            if (args.length == 0) {
                p.sendMessage(ChatColor.RED + "Enter a Player name!");
                return true;
            }
            Player target = Bukkit.getServer().getPlayer(args[0]);
            if (target == null) {
                p.sendMessage(ChatColor.RED + "Player doesn't exist!");
                return true;
            }
            if (args.length <= 1) {
                p.sendMessage(ChatColor.RED + "Enter a valid Message!");
                return true;
            }
            if (target.getName() == p.getName()) {
                p.sendMessage(ChatColor.RED + "Are you talking to yourself? Weird... ");
                return true;
            }
            StringBuilder sb = new StringBuilder();
            for (int i = 1; i < args.length; i++) {
                sb.append(args[i]).append(" ");
            }
            String allArgs = sb.toString().trim();
            p.sendMessage(ChatColor.WHITE + p.getName() + ChatColor.DARK_GREEN + " ➤ " + ChatColor.WHITE
                    + target.getName() + ChatColor.WHITE + " : " + allArgs);
            target.sendMessage(ChatColor.WHITE + p.getName() + ChatColor.DARK_GREEN + " ➤ " + ChatColor.WHITE
                    + target.getName() + ChatColor.WHITE + " : " + allArgs);
            target.playSound(target.getLocation(), Sound.BLOCK_NOTE_PLING, 20, 1);

            return true;
        }

        return false;
    }

}
