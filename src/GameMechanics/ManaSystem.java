package GameMechanics;

import ClassUpgrades.SkillPointsCore;
import Classes.AaClassComboes;
import Classes.AaClassActionBar;
import Main.Main;
import Teams.TeamManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Wither;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scoreboard.Team;

import java.util.HashMap;
import java.util.UUID;

public class ManaSystem extends SkillPointsCore implements Listener {

    public static HashMap<UUID, Integer> Mana = new HashMap<>();

    public void startManaDisplay() {
        new BukkitRunnable() {
            @Override
            public void run() {
                AaClassActionBar cAc = new AaClassActionBar();
                AaClassComboes a = new AaClassComboes();
                UltChargeMechanics u = new UltChargeMechanics();
                for (Player p : Bukkit.getWorld("world").getPlayers()) {
                    cAc.send(p, ChatColor.RED + "     " + ChatColor.BOLD + "" + (int) (p.getHealth()) + "/20❤      " +
                            u.getUltImage(p) + "      " + ChatColor.AQUA + "" + ChatColor.BOLD + getMana(p) + "/20⚙    ");
                }
            }
        }.runTaskTimer(Main.plugin, 0, 30);
    }

    public int getMana(Player p) {
        if (Mana.containsKey(p.getUniqueId())) {
            return Mana.get(p.getUniqueId());
        } else {
            Mana.put(p.getUniqueId(), 20);
            return 20;
        }
    }

    public void setMana(Player p, Integer mana) {
        if (Mana.containsKey(p.getUniqueId())) {
            if (mana >= 20) {
                Mana.replace(p.getUniqueId(), 20);
                return;
            }
            if (mana <= 0) {
                Mana.replace(p.getUniqueId(), 0);
                return;
            }
            Mana.replace(p.getUniqueId(), mana);
        }
    }

    @EventHandler
    public void onPlayerChargeMana(EntityDamageByEntityEvent e) {
        if (e.getDamager() instanceof Player) {
            Player p = (Player) e.getDamager();
            Entity victim = e.getEntity();
            if (TeamManager.getTeam(p) == null) return;
            if (victim instanceof Player) {
                if (TeamManager.getTeam(p) == TeamManager.getTeam((Player) victim)) return;
            }
            if (e.getDamage() < 2) {
                if (victim instanceof Wither) return;
                setMana(p, getMana(p) + 2 + getManaValue(p));
            }
        }
    }

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent e) {
        Player p = e.getEntity();
        setMana(p, 20);
    }
}
