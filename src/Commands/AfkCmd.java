package Commands;

import ClassGUI.AaClassHashMapChoosing;
import Ranks.RankingSystem;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerMoveEvent;

import java.util.HashMap;
import java.util.UUID;

public class AfkCmd implements CommandExecutor, Listener {

    public static HashMap<UUID, Boolean> afk = new HashMap<>();
    RankingSystem r = new RankingSystem();

    @EventHandler
    public void onPlayerChat(AsyncPlayerChatEvent e) {
        Player p = e.getPlayer();
        if (afk.containsKey(p.getUniqueId())) {
            afk.remove(p.getUniqueId());
            Bukkit.broadcastMessage(ChatColor.GREEN + "" + ChatColor.BOLD + "> " + r.getRank(p).getPrefix() + p.getName() +
                    ChatColor.DARK_GREEN + " is no longer AFK !" + ChatColor.GREEN + "" + ChatColor.BOLD + " <");
        }
    }

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent e) {
        Player p = e.getPlayer();
        if (afk.containsKey(p.getUniqueId())) {
            afk.remove(p.getUniqueId());
            p.setCollidable(true);
            Bukkit.broadcastMessage(ChatColor.GREEN + "" + ChatColor.BOLD + "> " + r.getRank(p).getPrefix() + p.getName() +
                    ChatColor.DARK_GREEN + " is no longer AFK !" + ChatColor.GREEN + "" + ChatColor.BOLD + " <");
        }
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(ChatColor.RED + "You cannot execute this command from console!");
            return true;
        }
        Player p = (Player) sender;
        AaClassHashMapChoosing player = new AaClassHashMapChoosing();
        if (cmd.getName().equalsIgnoreCase("afk")) {
            if (afk.containsKey(p.getUniqueId())) {
                p.sendMessage(ChatColor.RED + "You are already afk!");
            } else {
                afk.put(p.getUniqueId(), true);
                p.setCollidable(false);
                Bukkit.broadcastMessage(ChatColor.DARK_RED + "" + ChatColor.BOLD + "> " + r.getRank(p).getPrefix() + p.getName() +
                        ChatColor.DARK_RED + " is AFK !" + ChatColor.DARK_RED + "" + ChatColor.BOLD + " <");
                return true;
            }
        }
        return false;
    }
}
