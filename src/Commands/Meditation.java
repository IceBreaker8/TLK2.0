package Commands;

import GameMechanics.ManaSystem;
import Main.Main;
import Teams.TeamManager;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class Meditation implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(ChatColor.RED + "You cannot execute this command from console!");
            return true;
        }
        Player p = (Player) sender;
        if (cmd.getName().equalsIgnoreCase("med")) {
            if (TeamManager.getTeam(p) == null) {
                p.sendMessage(ChatColor.RED + "Only team members can use this command!");
                return true;
            }
            meditate(p);
            return true;
        }
        return false;
    }

    private boolean hasMoved(Player p, Location start, Location end) {
        return !start.equals(end);
    }

    private void meditate(Player p) {
        Location pLoc = p.getLocation();
        new BukkitRunnable() {
            int count = 0;

            @Override
            public void run() {
                if (count == 10) {
                    p.sendMessage(ChatColor.GREEN + "" + ChatColor.BOLD + "Meditation is complete!");
                    p.playSound(p.getLocation(),Sound.ITEM_TOTEM_USE , 5, 1);
                    p.spawnParticle(Particle.TOTEM,
                            p.getLocation().clone().add(0, 1,0 ), 300, 0.5F, 0.5F, 0.5F, 1);
                    p.setHealth(p.getMaxHealth());
                    ManaSystem m = new ManaSystem();
                    m.setMana(p, 20);
                    cancel();
                    return;
                }
                p.playSound(p.getLocation(), Sound.UI_BUTTON_CLICK, 5, 1);
                p.spawnParticle(Particle.TOTEM,
                        p.getLocation().clone().add(0, 1,0 ), 300, 0.5F, 0.5F, 0.5F, 0.1);
                count += 1;
                if (hasMoved(p, pLoc, p.getLocation())) {
                    p.sendMessage(ChatColor.RED + "Meditation cancelled due to moving!");
                    p.playSound(p.getLocation(), Sound.ENTITY_BLAZE_DEATH, 5, 1);
                    cancel();
                    return;
                }

            }
        }.runTaskTimer(Main.plugin, 0, 20);
    }
}
