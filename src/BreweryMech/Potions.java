package BreweryMech;

import ItemConstructorClass.ItemConstructor;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.potion.PotionData;
import org.bukkit.potion.PotionType;

import java.util.ArrayList;

public class Potions {

    ItemConstructor iC = new ItemConstructor();

    public ItemStack getHighlandBovenirPot() {
        ItemStack bottle = new ItemStack(Material.POTION, 1);
        PotionMeta meta = (PotionMeta) bottle.getItemMeta();
        meta.clearCustomEffects();
        meta.setBasePotionData(new PotionData(PotionType.JUMP));
        bottle.setItemMeta(meta);
        ArrayList<String> lore = new ArrayList<>();
        lore.add(ChatColor.GRAY + "Regenerate 16 health points to your maximum health");
        return iC.MakeItemByStack(ChatColor.AQUA + "" + ChatColor.BOLD + "Highland Bovenir Essence", bottle, lore);
    }
    public ItemStack getAthorianSnapdragonPot() {
        ItemStack bottle = new ItemStack(Material.POTION, 1);
        PotionMeta meta = (PotionMeta) bottle.getItemMeta();
        meta.clearCustomEffects();
        meta.setBasePotionData(new PotionData(PotionType.NIGHT_VISION));
        bottle.setItemMeta(meta);
        ArrayList<String> lore = new ArrayList<>();
        lore.add(ChatColor.GRAY + "Regenerate 4 mana per second for 20 seconds");
        return iC.MakeItemByStack(ChatColor.AQUA + "" + ChatColor.BOLD + "Athorian Snapdragon Essence", bottle, lore);
    }
    public ItemStack getFireflaxPot() {
        ItemStack bottle = new ItemStack(Material.POTION, 1);
        PotionMeta meta = (PotionMeta) bottle.getItemMeta();
        meta.clearCustomEffects();
        meta.setBasePotionData(new PotionData(PotionType.INSTANT_DAMAGE));
        bottle.setItemMeta(meta);
        ArrayList<String> lore = new ArrayList<>();
        lore.add(ChatColor.GRAY + "Increase your damage by 6 for 8 seconds");
        return iC.MakeItemByStack(ChatColor.AQUA + "" + ChatColor.BOLD + "Fireflax Essence", bottle, lore);
    }
    public ItemStack getVïrgilsûlPot() {
        ItemStack bottle = new ItemStack(Material.POTION, 1);
        PotionMeta meta = (PotionMeta) bottle.getItemMeta();
        meta.clearCustomEffects();
        meta.setBasePotionData(new PotionData(PotionType.INSTANT_DAMAGE));
        bottle.setItemMeta(meta);
        ArrayList<String> lore = new ArrayList<>();
        lore.add(ChatColor.GRAY + "Fully replenish your ultimate charge");
        return iC.MakeItemByStack(ChatColor.AQUA + "" + ChatColor.BOLD + "Vïrgil sûl Essence", bottle, lore);
    }
}
