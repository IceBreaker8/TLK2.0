package ClassGUI;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

public class AaClassTable implements Listener {

    public static Boolean allowEnchTable = true;

    @EventHandler
    public void onEnchTableClick(PlayerInteractEvent e) {
        final Player p = e.getPlayer();
        if (e.getAction() == Action.RIGHT_CLICK_BLOCK && e.getClickedBlock().getType() == Material.ENCHANTMENT_TABLE) {
            e.setCancelled(true);
            if (allowEnchTable == true) {
                if (AaClassHashMapChoosing.playerHasChosenClass.contains(p)) return;
                AaClassChooseGUI c = new AaClassChooseGUI();
                c.openClassGUI(p);
                return;
            }
        }
    }
}
