package BreweryMech;

import ItemConstructorClass.ItemConstructor;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;

public class Flowers {

    ItemConstructor im = new ItemConstructor();

    public ItemStack getHighlandBovenir() {
        ArrayList<String> lore = new ArrayList<>();
        lore.add(ChatColor.GRAY + "Used to brew health potions");
        return im.MakeItem(ChatColor.GREEN + "" + ChatColor.BOLD + "Highland Bovenir",
                Material.RED_ROSE, lore);
    }

    public ItemStack getAthorianSnapdragon() {
        ArrayList<String> lore = new ArrayList<>();
        lore.add(ChatColor.GRAY + "Used to brew mana potions");
        ItemStack flower = new ItemStack(Material.RED_ROSE, 1, (short) 1);
        return im.MakeItemByStack(ChatColor.AQUA + "" + ChatColor.BOLD + "Athorian Snapdragon",
                flower, lore);
    }

    public ItemStack getFireflax() {
        ArrayList<String> lore = new ArrayList<>();
        lore.add(ChatColor.GRAY + "Used to brew damage potions");
        ItemStack flower = new ItemStack(Material.RED_ROSE, 1, (short) 4);
        return im.MakeItemByStack(ChatColor.DARK_RED + "" + ChatColor.BOLD + "Fireflax",
                flower, lore);
    }

    public ItemStack getVïrgilsûl() {
        ArrayList<String> lore = new ArrayList<>();
        lore.add(ChatColor.GRAY + "Used to brew ultimate charge potions");
        ItemStack flower = new ItemStack(Material.RED_ROSE, 1, (short) 8);
        return im.MakeItemByStack(ChatColor.WHITE + "" + ChatColor.BOLD + "Vïrgil sûl",
                flower, lore);
    }

    public void addItems(Player p) {
        p.getInventory().addItem(getFireflax());
        p.getInventory().addItem(getAthorianSnapdragon());
        p.getInventory().addItem(getHighlandBovenir());
        p.getInventory().addItem(getVïrgilsûl());

    }
}
