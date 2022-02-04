package Commands;

import ItemConstructorClass.GameMenu;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class GameMenuCMD implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(ChatColor.DARK_RED + "You cannot execute this command from console!");
            return true;
        }
        final Player p = (Player) sender;
        if (cmd.getName().equalsIgnoreCase("gm")) {
            if (p.getName().equals("icebreaker970")) {
                GameMenu.makeGameMenu(p);
            } else {
                p.sendMessage(ChatColor.DARK_RED + "You don't have permission to run this command!");
            }
            return true;
        }
        return false;
    }

}
