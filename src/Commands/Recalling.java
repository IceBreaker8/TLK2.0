package Commands;

import Main.Main;
import Teams.TeamManager;
import org.bukkit.*;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashMap;
import java.util.UUID;

public class Recalling implements CommandExecutor {

    public static HashMap<UUID, Integer> recallCooldown = new HashMap<>();
    private int cooldown = 180;

    private World w = Bukkit.getWorld("world");
    private Location shamanSpawn = new Location(w, 339, 72, -11);
    private Location angelSpawn = new Location(w, -118, 73, 1);

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(ChatColor.RED + "You cannot execute this command from console!");
            return true;
        }
        Player p = (Player) sender;
        if (cmd.getName().equalsIgnoreCase("recall")) {
            if (TeamManager.getTeam(p) == null) {
                p.sendMessage(ChatColor.RED + "Only team members can use this command!");
                return true;
            }
            tpPlayer(p);
            return true;
        }
        return false;
    }

    public void tpPlayer(Player p) {
        if (recallCooldown.containsKey(p.getUniqueId())) {
            p.sendMessage(ChatColor.RED + "You must wait " + recallCooldown.get(p.getUniqueId())
                    + " seconds to use this again!");
            return;
        }
        Location loc = p.getLocation();
        new BukkitRunnable() {
            int t = 6;

            @Override
            public void run() {
                Location loc1 = p.getLocation();
                t = t - 1;
                p.addPotionEffect(new PotionEffect(PotionEffectType.CONFUSION, 20 * 999, 10));

                if (playerMove(p, loc, loc1)) {
                    if (p.hasPotionEffect(PotionEffectType.CONFUSION)) {
                        p.removePotionEffect(PotionEffectType.CONFUSION);
                    }
                    p.sendMessage(ChatColor.RED + "The teleport cancelled as you moved...");
                    cancel();
                }
                if (t > 0 && (playerMove(p, loc, loc1) == false)) {
                    p.playSound(p.getLocation(), Sound.BLOCK_NOTE_HAT, 10, 1);
                    p.sendMessage(ChatColor.GOLD + "" + ChatColor.BOLD + "Teleporting to base in " + ChatColor.WHITE
                            + "" + ChatColor.BOLD + t + ChatColor.WHITE + "" + ChatColor.BOLD + "..");
                }
                if (t == 0 && (playerMove(p, loc, loc1) == false)) {
                    cancel();
                    cooldown(p);
                    p.playSound(p.getLocation(), Sound.BLOCK_NOTE_PLING, 10, 1);
                    p.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 20 * 2, 10));
                    if (p.hasPotionEffect(PotionEffectType.CONFUSION)) {
                        p.removePotionEffect(PotionEffectType.CONFUSION);
                    }
                    if (TeamManager.getTeam(p) == "angels") {
                        p.sendMessage(ChatColor.AQUA + "" + ChatColor.BOLD + "Teleported successfully!");

                        p.teleport(angelSpawn);
                    } else if (TeamManager.getTeam(p) == "shamans") {
                        p.sendMessage(ChatColor.DARK_GREEN + "" + ChatColor.BOLD + "Teleported successfully!");
                        p.teleport(shamanSpawn);
                    }
                }

            }
        }.runTaskTimer(Main.plugin, 0, 20);
    }

    public boolean playerMove(Player p, Location loc, Location loc1) {
        if (loc.distance(loc1) > 0.1) {
            return true;
        }
        return false;
    }

    public void cooldown(Player p) {
        recallCooldown.put(p.getUniqueId(), cooldown);
        new BukkitRunnable() {

            @Override
            public void run() {
                if (recallCooldown.get(p.getUniqueId()) <= 0) {
                    recallCooldown.remove(p.getUniqueId());
                    p.playSound(p.getLocation(), Sound.BLOCK_NOTE_PLING, 10, 1);
                    p.sendMessage(ChatColor.GOLD + "" + ChatColor.BOLD + "You can teleport to base again now!");
                    this.cancel();
                }

                recallCooldown.put(p.getUniqueId(), recallCooldown.get(p.getUniqueId()) - 1);

            }

        }.runTaskTimer(Main.plugin, 0, 20);
    }
}
