package Commands;

import ClassGUI.AaClassHashMapChoosing;
import ClassUpgrades.SkillPointsCore;
import ClassUpgrades.SkillPointsObtainer;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ClassSpellsAndSkillUpgrades extends SkillPointsObtainer implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(ChatColor.RED + "You cannot execute this command from console!");
            return true;
        }
        Player p = (Player) sender;
        AaClassHashMapChoosing player = new AaClassHashMapChoosing();
        if (cmd.getName().equalsIgnoreCase("spells")) {
            if (player.getTlkClass(p) == null) {
                p.sendMessage(ChatColor.RED + "You didn't choose a class yet!");
                p.playSound(p.getLocation(), Sound.ENTITY_BLAZE_DEATH, 5, 1);
                return true;
            }
            p.openInventory(player.getTlkClass(p).getClassInv().getSpellGUI(p));
            return true;
        }
        if (cmd.getName().equalsIgnoreCase("sp")) {
            if (player.getTlkClass(p) == null) {
                p.sendMessage(ChatColor.RED + "You didn't choose a class yet!");
                p.playSound(p.getLocation(), Sound.ENTITY_BLAZE_DEATH, 5, 1);
                return true;
            }
            p.sendMessage(ChatColor.DARK_GREEN + "> You have "
                    + getSkillPoints(p) + " Skill Points");
            return true;
        }

        return false;
    }
}
