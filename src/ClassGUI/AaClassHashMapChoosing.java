package ClassGUI;

import Classes.TlkClass;
import Main.Main;
import com.sun.org.apache.xpath.internal.operations.Bool;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashMap;
import java.util.HashSet;
import java.util.UUID;

public class AaClassHashMapChoosing {
    public static HashMap<UUID, TlkClass> clazz = new HashMap<>();

    public static TlkClass getTlkClass(Player player) {
        if (clazz.containsKey(player.getUniqueId())) {
            return clazz.get(player.getUniqueId());
        }
        return null;
    }

    public static String getClass(Player p) {
        if (clazz.containsKey(p.getUniqueId())) {
            return clazz.get(p.getUniqueId()).className;
        }
        return "Default";
    }

    public static HashSet<Player> playerHasChosenClass = new HashSet<>();

    public void chooseClass(Player p, TlkClass TLKc) {
        if (uniCheck(TLKc, p) == false) {
            p.playSound(p.getLocation(), Sound.ENTITY_ENDERDRAGON_GROWL, 10, 1);
            clazz.remove(p.getUniqueId());
            clazz.put(p.getUniqueId(), TLKc);
            p.getInventory().clear();
            Bukkit.broadcastMessage(TLKc.classColor + "" + ChatColor.BOLD + p.getName() +
                    ChatColor.WHITE + "" + ChatColor.BOLD + " is now " + "" + TLKc.classSpec + "..");
            p.getOpenInventory().close();
            playerHasChosenClass.add(p);
            new BukkitRunnable() {
                @Override
                public void run() {
                    addItems(p);
                }
            }.runTaskLater(Main.plugin, 1);
        }
    }

    public void addItems(Player p) {
        p.getInventory().addItem(new ItemStack(Material.STONE_PICKAXE, 1));
        p.getInventory().addItem(new ItemStack(Material.STONE_AXE, 1));
        p.getInventory().addItem(new ItemStack(Material.STONE_SPADE, 1));
        p.getInventory().addItem(new ItemStack(Material.FISHING_ROD, 1));

    }

    public static boolean uniCheck(TlkClass C, Player p) {
        if (clazz.isEmpty()) {
            return false;
        }
        if (clazz.containsKey(p.getUniqueId()) && clazz.get(p.getUniqueId()).toString().contains(C.className)) {
            p.sendMessage(ChatColor.RED + "You can't pick the same class twice!");
            p.playSound(p.getLocation(), Sound.ENTITY_BLAZE_DEATH, 1, 1);
            return true;
        }
        for (Player player : p.getServer().getOnlinePlayers()) {
            if (!(player.equals(p))) {
                if (clazz.containsKey(player.getUniqueId()) && clazz.get(player.getUniqueId()).toString().contains(C.className)) {
                    p.sendMessage(ChatColor.RED + "A player has already picked that class!");
                    p.playSound(p.getLocation(), Sound.ENTITY_BLAZE_DEATH, 1, 1);
                    return true;
                }
            }
        }
        return false;
    }
}

