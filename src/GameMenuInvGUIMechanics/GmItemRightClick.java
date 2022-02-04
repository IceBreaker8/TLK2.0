package GameMenuInvGUIMechanics;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class GmItemRightClick implements Listener {

    @EventHandler
    public void onRightClickGmItem(PlayerInteractEvent e) {
        final Player p = e.getPlayer();

        ItemStack item = e.getItem();
        if (item == null)
            return;
        ItemMeta im = e.getItem().getItemMeta();
        if (im == null)
            return;
        Action action = e.getAction();
        ItemStack stack = e.getItem();
        if (stack == null)
            return;
        if (!(action == Action.RIGHT_CLICK_AIR || action == Action.RIGHT_CLICK_BLOCK))
            return;
        if (im.getLore() == null)
            return;

        for (String lore : im.getLore()) {
            if (lore.contains(ChatColor.DARK_PURPLE + "" + ChatColor.BOLD + "Class Item" + ChatColor.DARK_RED)) {
                return;
            }
        }
        if (!(stack.getItemMeta().hasDisplayName()))
            return;
        if (stack.getItemMeta().getDisplayName().contains("TLK 2.0 GameMenu")) {
            if(!p.getName().equals("icebreaker970")) return;
            GmMenuIconsCreator op = new GmMenuIconsCreator();
            op.createGmMenu(p);
        }
    }
}
