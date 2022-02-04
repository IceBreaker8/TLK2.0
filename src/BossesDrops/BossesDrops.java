package BossesDrops;

import BossesSpawners.GolemSpawner;
import BossesSpawners.TlkSpawn;
import BreweryMech.Flowers;
import ClassUpgrades.SkillPointsObtainer;
import ItemConstructorClass.ItemConstructor;
import Scoreboard.PlayerScoreboard;
import Teams.TeamManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;

import java.util.Random;

public class BossesDrops implements Listener {
    ItemConstructor itC = new ItemConstructor();

    @EventHandler
    public void entityDed(EntityDeathEvent e) {
        Entity entity = e.getEntity();
        Entity killer = e.getEntity().getKiller();
        if (entity instanceof MagmaCube) {
            e.setDroppedExp(0);
            e.getDrops().clear();
            entity.getWorld().dropItemNaturally(entity.getLocation(),
                    itC.MakeItem(ChatColor.RED + "" + ChatColor.BOLD +
                            "Lava Essence", Material.MAGMA_CREAM, null));
        }
        if (entity instanceof Rabbit) {
            Random r = new Random();
            int ran = r.nextInt(24);
            if (ran == 22) {
                Flowers f = new Flowers();
                entity.getWorld().dropItemNaturally(entity.getLocation(), f.getHighlandBovenir());
            }
        }
        if (entity instanceof Golem) {
            if (killer instanceof Player) {
                if (TeamManager.getTeam((Player) killer) != null) {
                    if (TeamManager.getTeam((Player) killer) == "shamans") {
                        Bukkit.broadcastMessage(ChatColor.GREEN + "" + ChatColor.BOLD +
                                "Shamans" + ChatColor.DARK_GRAY + "" + ChatColor.BOLD + " have killed the Golem boss!");
                    } else {
                        Bukkit.broadcastMessage(ChatColor.WHITE + "" + ChatColor.BOLD +
                                "Angels" + ChatColor.DARK_GRAY + "" + ChatColor.BOLD + " have killed the Golem boss!");
                    }
                    for (Player player : Bukkit.getServer().getOnlinePlayers()) {
                        player.playSound(player.getLocation(), Sound.ENTITY_IRONGOLEM_DEATH, 5, 1);
                    }
                    GolemSpawner.golemTimer();
                    e.setDroppedExp(0);
                    e.getDrops().clear();
                    ((Player) killer).getInventory().addItem(itC.MakeItem(
                            ChatColor.DARK_RED + "" + ChatColor.BOLD +
                                    "Corruption Core", Material.DRAGON_EGG, null));
                    Random r = new Random();
                    int ran = r.nextInt(5);
                    if (ran == 2) {
                        Flowers f = new Flowers();
                        entity.getWorld().dropItemNaturally(entity.getLocation(), f.getFireflax());
                    }
                }
            }
        }
        if (entity instanceof Skeleton) {
            if (killer instanceof Player) {
                if (TeamManager.getTeam((Player) killer) != null) {
                    if (TeamManager.getTeam((Player) killer) == "shamans") {
                        for (Player player : Bukkit.getServer().getOnlinePlayers()) {
                            if (TeamManager.getTeam(player) != null) {
                                if (TeamManager.getTeam(player) == "shamans") {
                                    SkillPointsObtainer s = new SkillPointsObtainer();
                                    s.addSkillPoints(player, 6);
                                    player.sendMessage(ChatColor.DARK_GREEN + "+ " + 6 + " Skill Point");
                                    PlayerScoreboard.appearScoreboardForOnePlayer(player);
                                }
                            }
                        }
                        Bukkit.broadcastMessage(ChatColor.GREEN + "" + ChatColor.BOLD +
                                "Shamans" + ChatColor.GOLD + "" + ChatColor.BOLD + " have killed The Last Knight boss!");
                    } else if (TeamManager.getTeam((Player) killer) == "angels") {
                        for (Player player : Bukkit.getServer().getOnlinePlayers()) {
                            if (TeamManager.getTeam(player) != null) {
                                if (TeamManager.getTeam(player) == "angels") {
                                    SkillPointsObtainer s = new SkillPointsObtainer();
                                    s.addSkillPoints(player, 6);
                                    player.sendMessage(ChatColor.DARK_GREEN + "+ " + 6 + " Skill Point");
                                    PlayerScoreboard.appearScoreboardForOnePlayer(player);
                                }
                            }
                        }
                        Bukkit.broadcastMessage(ChatColor.WHITE + "" + ChatColor.BOLD +
                                "Angels" + ChatColor.GOLD + "" + ChatColor.BOLD + " have killed The Last Knight boss!");
                    }
                    for (Player player : Bukkit.getServer().getOnlinePlayers()) {
                        player.playSound(player.getLocation(), Sound.ENTITY_IRONGOLEM_DEATH, 5, 1);
                    }
                    TlkSpawn.tlkTimer();
                    e.setDroppedExp(0);
                    e.getDrops().clear();
                    Random r = new Random();
                    int ran = r.nextInt(8);
                    if (ran == 2) {
                        Flowers f = new Flowers();
                        entity.getWorld().dropItemNaturally(entity.getLocation(), f.getAthorianSnapdragon());
                    }
                }
            }
        }
    }
}
