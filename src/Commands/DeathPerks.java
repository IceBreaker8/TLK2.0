package Commands;

import GamePerks.AaPerksInventory;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class DeathPerks implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(ChatColor.RED + "You cannot execute this command from console!");
            return true;
        }
        Player p = (Player) sender;
        if (cmd.getName().equals("deathperks") || cmd.getName().equals("dp")) {
            AaPerksInventory perk = new AaPerksInventory();
            perk.openInv(p);
            return true;
        }
        return false;
    }
}
