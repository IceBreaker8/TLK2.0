package GameMechanics;

import ClassGUI.AaClassHashMapChoosing;
import ClassUpgrades.SkillPointsCore;
import Teams.TeamManager;
import org.bukkit.ChatColor;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

import java.util.HashMap;
import java.util.UUID;

public class UltChargeMechanics extends SkillPointsCore implements Listener {
    public static HashMap<UUID, Integer> Ult = new HashMap<>();

    public int getUlt(Player p) {
        if (Ult.containsKey(p.getUniqueId())) {
            return Ult.get(p.getUniqueId());
        }
        Ult.put(p.getUniqueId(), 0);
        return 0;

    }

    public void setUlt(Player p, Integer ultCount) {
        if (Ult.containsKey(p.getUniqueId())) {
            Ult.replace(p.getUniqueId(), ultCount);
        }
    }

    public boolean canUlt(Player p) {
        if (getUltImage(p).contains("ULTREADY")) {
            return true;
        }
        return false;
    }

    public String getUltImage(Player p) {
        if (AaClassHashMapChoosing.getTlkClass(p) == null) return "[--------]";
        if (getUlt(p) >= 100) {
            return ChatColor.GRAY + "" + ChatColor.BOLD + "[" + AaClassHashMapChoosing.getTlkClass(p).classColor + "" + ChatColor.BOLD + "ULTREADY" + ChatColor.GRAY + "" + ChatColor.BOLD + "]";
        }
        if (getUlt(p) >= 84) {
            return ChatColor.GRAY + "" + "[" + AaClassHashMapChoosing.getTlkClass(p).classColor + "" + ChatColor.BOLD + "-------" + ChatColor.GRAY + "" + ChatColor.BOLD + "-]";
        }
        if (getUlt(p) >= 72) {
            return ChatColor.GRAY + "" + "[" + AaClassHashMapChoosing.getTlkClass(p).classColor + "" + ChatColor.BOLD + "------" + ChatColor.GRAY + "" + ChatColor.BOLD + "--]";
        }
        if (getUlt(p) >= 60) {
            return ChatColor.GRAY + "" + "[" + AaClassHashMapChoosing.getTlkClass(p).classColor + "" + ChatColor.BOLD + "-----" + ChatColor.GRAY + "" + ChatColor.BOLD + "---]";
        }
        if (getUlt(p) >= 48) {
            return ChatColor.GRAY + "" + "[" + AaClassHashMapChoosing.getTlkClass(p).classColor + "" + ChatColor.BOLD + "----" + ChatColor.GRAY + "" + ChatColor.BOLD + "----]";
        }
        if (getUlt(p) >= 36) {
            return ChatColor.GRAY + "" + "[" + AaClassHashMapChoosing.getTlkClass(p).classColor + "" + ChatColor.BOLD + "---" + ChatColor.GRAY + "" + ChatColor.BOLD + "-----]";
        }
        if (getUlt(p) >= 24) {
            return ChatColor.GRAY + "" + "[" + AaClassHashMapChoosing.getTlkClass(p).classColor + "" + ChatColor.BOLD + "--" + ChatColor.GRAY + "" + ChatColor.BOLD + "------]";
        }
        if (getUlt(p) >= 12) {
            return ChatColor.GRAY + "" + "[" + AaClassHashMapChoosing.getTlkClass(p).classColor + "" + ChatColor.BOLD + "-" + ChatColor.GRAY + "" + ChatColor.BOLD + "-------]";
        }
        if (getUlt(p) >= 0) {
            return ChatColor.GRAY + "" + ChatColor.BOLD + "[--------]";
        }
        return "";
    }

    @EventHandler
    public void onPlayerChargeUlt(EntityDamageByEntityEvent e) {
        Entity entity = e.getEntity();
        Entity damager = e.getDamager();
        if (damager instanceof Player && entity instanceof Player) {
            if (TeamManager.arentSameTeam((Player) damager, (Player) entity))
                setUlt((Player) damager, getUlt((Player) damager) + 2 + getUltValue((Player) damager));
        }
    }
}
