package GameMechanics;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerFishEvent;
import org.bukkit.inventory.ItemStack;

public class FishingEvent implements Listener {

    @EventHandler
    public void onPlayerFish(PlayerFishEvent event) {

        final Player p = event.getPlayer();
        final String state = event.getState().name();

        if (state.equalsIgnoreCase("CAUGHT_FISH")) {
            event.getCaught().remove();
            p.getInventory().addItem(new ItemStack(Material.RAW_FISH, 8));
            p.updateInventory();
        }
    }
}
