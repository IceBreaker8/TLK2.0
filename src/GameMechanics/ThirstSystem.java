package GameMechanics;

import Main.Main;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.ExperienceOrb;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.EntitySpawnEvent;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

public class ThirstSystem implements Listener {

    public static int MaxThirstLevel = 100;
    private int thirstTimer = 20 * 10;
    private int thirstLevelPerDrink = 50;
    private int thirstDamage = 4;

    public void setThirst(Player p, Integer thirst) {
        if (thirst > 100) {
            p.setLevel(100);
            return;
        }
        p.setLevel(thirst);
    }

    public Integer getThirst(Player p) {
        return (int) p.getLevel();
    }

    public void consumeWater() {
        new BukkitRunnable() {
            @Override
            public void run() {
                for (Player player : Bukkit.getServer().getOnlinePlayers()) {
                    if (player.getGameMode().equals(GameMode.SPECTATOR)) continue;
                    if (getThirst(player) == 0) {
                        player.setLevel(0);
                        player.damage(thirstDamage);
                        player.sendMessage(ChatColor.AQUA + "" + "You need to drink water!");
                        continue;
                    }
                    setThirst(player, getThirst(player) - 1);

                }
            }
        }.runTaskTimer(Main.plugin, thirstTimer, thirstTimer);
    }

    @EventHandler
    public void onPlayerDrinkWater(PlayerItemConsumeEvent e) {
        Player p = e.getPlayer();
        ItemStack item = e.getItem();
        if (e.getItem().getType() == Material.POTION && e.getItem().getDurability() == 0) {
            if (e.getItem().hasItemMeta() && e.getItem().getItemMeta().hasDisplayName()) {
                if (e.getItem().getItemMeta().getDisplayName().contains("Potable Water")) {
                    setThirst(p, getThirst(p) + thirstLevelPerDrink);
                }
            }
        }
    }

    @EventHandler
    public void onDeathXp(EntityDeathEvent e) {
        e.setDroppedExp(0);
    }

    @EventHandler
    public void onExpSpawn(EntitySpawnEvent e) {
        if (e.getEntity() instanceof ExperienceOrb) {
            e.setCancelled(true);
        }
    }

}
