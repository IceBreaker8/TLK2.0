package BreweryMech;

import ClassGUI.AaClassHashMapChoosing;
import GameMechanics.ManaSystem;
import GameMechanics.UltChargeMechanics;
import Main.Main;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashSet;
import java.util.UUID;

public class PotionEffects implements Listener {

    @EventHandler
    public void onDrink(PlayerItemConsumeEvent e) {
        Player p = e.getPlayer();
        Potions pot = new Potions();
        if (e.getItem().equals(pot.getAthorianSnapdragonPot())) {
            e.setCancelled(true);
            e.getPlayer().getInventory().remove(e.getItem());
            manaEffect(p);
            return;
        }
        if (e.getItem().equals(pot.getFireflaxPot())) {
            e.setCancelled(true);
            e.getPlayer().getInventory().remove(e.getItem());
            damageEffect(p);
            return;
        }
        if (e.getItem().equals(pot.getHighlandBovenirPot())) {
            e.setCancelled(true);
            e.getPlayer().getInventory().remove(e.getItem());
            healthEffect(p);
            return;
        }
        if (e.getItem().equals(pot.getVïrgilsûlPot())) {
            if (AaClassHashMapChoosing.getTlkClass(p) == null) {
                p.sendMessage(ChatColor.RED + "You can't drink the bottle if you don't have a chosen class!");
                p.playSound(p.getLocation(), Sound.ENTITY_BLAZE_DEATH, 5, 1);
                return;
            }
            e.setCancelled(true);
            e.getPlayer().getInventory().remove(e.getItem());
            ultEffect(p);
            return;
        }
    }

    private void manaEffect(Player p) {
        ManaSystem m = new ManaSystem();
        new BukkitRunnable() {
            int count = 0;

            @Override
            public void run() {
                count += 1;
                m.setMana(p, m.getMana(p) + 4);
                if (count == 10) {
                    cancel();
                    return;
                }
            }
        }.runTaskTimer(Main.plugin, 0, 20);
    }

    private void healthEffect(Player p) {
        p.sendMessage(ChatColor.GREEN + "" + ChatColor.BOLD + "Added 16 health points!");
        if ((p.getHealth() + 16) > p.getMaxHealth()) {
            p.setHealth(p.getMaxHealth());
            return;
        }
        p.setHealth(p.getHealth() + 16);
    }

    public HashSet<UUID> dmg = new HashSet<>();

    private void damageEffect(Player p) {
        p.sendMessage(ChatColor.DARK_RED + "" + ChatColor.BOLD + "Increased damage by 6 points for 8 seconds!");
        dmg.add(p.getUniqueId());
        new BukkitRunnable() {
            @Override
            public void run() {
                dmg.remove(p.getUniqueId());
            }
        }.runTaskLater(Main.plugin, 20 * 8);
    }

    @EventHandler
    public void onPlayerHit(EntityDamageByEntityEvent e) {
        Entity damager = e.getDamager();
        if (damager instanceof Player) {
            if (dmg.contains(damager.getUniqueId())) {
                e.setDamage(e.getDamage() + 6);
            }
        }

    }

    private void ultEffect(Player p) {
        UltChargeMechanics u = new UltChargeMechanics();
        u.setUlt(p, 100);
    }
}
