package ItemConstructorClass;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;

public class ItemConstructor {

    public ItemStack MakeItem(String itemName, Material itemMaterial, ArrayList lore) {
        ItemStack item = new ItemStack(itemMaterial, 1);
        ItemMeta im = item.getItemMeta();
        im.spigot().setUnbreakable(true);
        im.setDisplayName(itemName);
        im.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        im.removeItemFlags();
        im.addItemFlags(ItemFlag.HIDE_POTION_EFFECTS);
        im.setLore(lore);
        im.addItemFlags(ItemFlag.HIDE_ATTRIBUTES, ItemFlag.HIDE_UNBREAKABLE);
        im.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        item.setItemMeta(im);
        //

        return item;
    }

    public ItemStack MakeItemByStack(String itemName, ItemStack item, ArrayList lore) {
        ItemMeta im = item.getItemMeta();
        im.setDisplayName(itemName);
        im.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        im.removeItemFlags();
        im.addItemFlags(ItemFlag.HIDE_POTION_EFFECTS);
        im.setLore(lore);
        item.setItemMeta(im);
        return item;
    }
}
