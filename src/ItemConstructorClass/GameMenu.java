package ItemConstructorClass;


import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;

public class GameMenu {

    public static void makeGameMenu(Player p) {
        ItemConstructor itC = new ItemConstructor();
        ArrayList<String> lore = new ArrayList<String>();
        lore.add(ChatColor.WHITE + "" + ChatColor.BOLD + "Manipulating game mode mechanics..");
        ItemStack item = itC.MakeItem(ChatColor.AQUA + "" + ChatColor.BOLD + "TLK 2.0 GameMenu", Material.DIAMOND_AXE, lore);
        p.getInventory().addItem(item);
    }
}
