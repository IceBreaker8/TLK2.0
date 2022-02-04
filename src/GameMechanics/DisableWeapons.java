package GameMechanics;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.PrepareItemCraftEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class DisableWeapons implements Listener {
    @EventHandler
    public void craftItem(PrepareItemCraftEvent e) {
        ItemStack sugar = new ItemStack(Material.BOW);
        ItemStack sugar1 = new ItemStack(Material.ARROW);
        ItemStack sugar2 = new ItemStack(Material.BUCKET);
        ItemStack sugar3 = new ItemStack(Material.FLINT_AND_STEEL);
        ItemStack sugar4 = new ItemStack(Material.BOAT);
        ItemStack sugar5 = new ItemStack(Material.SHIELD);
        ItemStack sugar6 = new ItemStack(Material.DIAMOND_AXE);
        ItemStack sugar9 = new ItemStack(Material.DIAMOND_SPADE);
        ItemStack sugar10 = new ItemStack(Material.DIAMOND_SWORD);
        ItemStack sugar8 = new ItemStack(Material.GOLD_AXE);
        ItemStack sugar11 = new ItemStack(Material.GOLD_SWORD);
        ItemStack sugar13 = new ItemStack(Material.GOLD_SPADE);
        ItemStack sugar12 = new ItemStack(Material.IRON_SPADE);
        ItemStack sugar7 = new ItemStack(Material.IRON_AXE);
        ItemStack sugar14 = new ItemStack(Material.IRON_SWORD);
        ItemStack sugar15 = new ItemStack(Material.MINECART);

        if (e.getRecipe() == null) return;
        if (e.getRecipe().getResult() == null) return;
        if (e.getRecipe().getResult().getType() == null) return;
        Material itemType = e.getRecipe().getResult().getType();
        if (itemType.equals(sugar.getType()) || itemType.equals(sugar1.getType()) || itemType.equals(sugar2.getType())
                || itemType.equals(sugar3.getType()) || itemType.equals(sugar4.getType()) || itemType.equals(sugar5.getType())
                || itemType.equals(sugar6.getType()) || itemType.equals(sugar7.getType()) ||
                itemType.equals(sugar8.getType()) || itemType.equals(sugar9.getType()) || itemType.equals(sugar10.getType())
                || itemType.equals(sugar11.getType()) || itemType.equals(sugar12.getType()) ||
                itemType.equals(sugar13.getType()) || itemType.equals(sugar14.getType()) ||
                itemType.equals(sugar15.getType())) {
            e.getInventory().setResult(new ItemStack(Material.AIR));
        }
    }
}
