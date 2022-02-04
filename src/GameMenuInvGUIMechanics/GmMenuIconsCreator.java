package GameMenuInvGUIMechanics;

import ItemConstructorClass.ItemConstructor;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;

public class GmMenuIconsCreator {

    public void createGmMenu(Player p) {
        ItemConstructor itC = new ItemConstructor();
        Inventory gamemenu = Bukkit.getServer().createInventory(null, 9 * 5,
                ChatColor.WHITE + "" + ChatColor.BOLD + "GameMenu");
        ArrayList<String> lore1 = new ArrayList<String>();
        // item 1 : Shamans
        gamemenu.setItem(0, itC.MakeItem(ChatColor.GREEN + "" + ChatColor.BOLD + "Shamans", Material.EMERALD_BLOCK, lore1));
        // item 2 : Angels
        gamemenu.setItem(1, itC.MakeItem(ChatColor.WHITE + "" + ChatColor.BOLD + "Angels", Material.WOOL, lore1));
        // item 3 :
        gamemenu.setItem(2, itC.MakeItem(ChatColor.RED + "" + ChatColor.BOLD + "Remove Team", Material.REDSTONE_BLOCK, lore1));
        //display Mana
        gamemenu.setItem(3, itC.MakeItem(ChatColor.AQUA + "" + ChatColor.BOLD + "Display Mana", Material.LAPIS_BLOCK, lore1));
        //activate bonfire
        gamemenu.setItem(4, itC.MakeItem(ChatColor.DARK_RED + "" + ChatColor.BOLD + "Activate Bonfire", Material.FIREBALL, lore1));
        //activate smelter
        gamemenu.setItem(5, itC.MakeItem(ChatColor.GRAY + "" + ChatColor.BOLD + "Activate Smelter", Material.COAL_BLOCK, lore1));
        //thirst maximize
        gamemenu.setItem(6, itC.MakeItem(ChatColor.AQUA + "" + ChatColor.BOLD + "Activate Thirst", Material.WATER_BUCKET, lore1));
        //scoreboard activation
        gamemenu.setItem(7, itC.MakeItem(ChatColor.GOLD + "" + ChatColor.BOLD + "Activate Scoreboard", Material.SIGN, lore1));
        //scoreboard deletion
        gamemenu.setItem(8, itC.MakeItem(ChatColor.RED + "" + ChatColor.BOLD + "Delete Scoreboard", Material.BLAZE_POWDER, lore1));
        //Player scoreboard activation
        gamemenu.setItem(9, itC.MakeItem(ChatColor.GOLD + "" + ChatColor.BOLD + "Activate Player Scoreboard", Material.SIGN, lore1));
        //Player scoreboard deletion
        gamemenu.setItem(10, itC.MakeItem(ChatColor.RED + "" + ChatColor.BOLD + "Delete Player Scoreboard", Material.BLAZE_POWDER, lore1));
        //Climbing system
        gamemenu.setItem(11, itC.MakeItem(ChatColor.RED + "" + ChatColor.BOLD + "Climbing System", Material.FISHING_ROD, lore1));
        //shooting crystal
        gamemenu.setItem(12, itC.MakeItem(ChatColor.BLUE + "" + ChatColor.BOLD + "Shooting Crystal", Material.PRISMARINE_CRYSTALS, lore1));
        //allow class table
        gamemenu.setItem(13, itC.MakeItem(ChatColor.DARK_AQUA + "" + ChatColor.BOLD + "Allow Class Table", Material.ENCHANTMENT_TABLE, lore1));
        //shaman boss spawn
        gamemenu.setItem(14, itC.MakeItem(ChatColor.GREEN + "" + ChatColor.BOLD + "Spawn Shaman Boss", Material.EMERALD_BLOCK, lore1));
        //angel boss spawn
        gamemenu.setItem(15, itC.MakeItem(ChatColor.WHITE + "" + ChatColor.BOLD + "Spawn Angel Boss", Material.IRON_BLOCK, lore1));
        //kill bosses
        gamemenu.setItem(16, itC.MakeItem(ChatColor.DARK_RED + "" + ChatColor.BOLD + "Kill Bosses", Material.SKULL_ITEM, lore1));
        //get flowers
        gamemenu.setItem(17, itC.MakeItem(ChatColor.DARK_GREEN + "" + ChatColor.BOLD + "Flowers", Material.FLOWER_POT_ITEM, lore1));
        //golem spawn timer
        gamemenu.setItem(18, itC.MakeItem(ChatColor.DARK_GRAY + "" + ChatColor.BOLD + "Golem", Material.RED_ROSE, lore1));
        //start game
        gamemenu.setItem(19, itC.MakeItem(ChatColor.GOLD + "" + ChatColor.BOLD + "Start game", Material.GOLD_BLOCK, lore1));
        //restore map
        gamemenu.setItem(20, itC.MakeItem(ChatColor.GREEN + "" + ChatColor.BOLD + "Restore Map", Material.EMERALD_BLOCK, lore1));
        p.openInventory(gamemenu);

    }
}
