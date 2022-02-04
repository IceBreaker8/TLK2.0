package GamePerks;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.PlayerDeathEvent;

public class AaPlayerDeathPerkActivate implements Listener {
    @EventHandler
    public void onPlayerDeath(EntityDeathEvent e) {
        // if (!(e.getEntity() instanceof Player)) return;
        if (!(e.getEntity().getKiller() instanceof Player)) return;
        Player killer = e.getEntity().getKiller();
        if (!AaPerksInventory.playerPerk.containsKey(killer.getUniqueId())) return;
        Perks perk = AaPerksInventory.playerPerk.get(killer.getUniqueId());
        perk.activatePerk(e.getEntity());

    }
}
