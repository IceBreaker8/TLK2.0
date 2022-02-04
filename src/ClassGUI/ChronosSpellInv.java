package ClassGUI;

import ClassUpgrades.SpellStringAdder;
import Classes.Chronos;
import ItemConstructorClass.ItemConstructor;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.potion.PotionData;
import org.bukkit.potion.PotionType;

import java.util.ArrayList;

public class ChronosSpellInv extends AaClassSpellShowcase implements Listener {

    @Override
    public Inventory getSpellGUI(Player p) {
        Inventory newInv = ChroInv(p);
        fillInvWithGlass(newInv, 26);
        return skillSet(newInv, p);
    }

    SpellStringAdder sp = new SpellStringAdder();

    public void openClassInv(Player p) {
        p.openInventory(ChroInv(p));
    }

    public ItemStack ChronosWeapon() {
        ItemConstructor itC = new ItemConstructor();
        ArrayList<String> lore5 = new ArrayList<String>();
        lore5.add(ChatColor.GRAY + "An Ancient Clock found by Occultists");
        lore5.add(ChatColor.GRAY + "in the Ancient Watch Tower.");
        lore5.add("");
        lore5.add(ChatColor.DARK_RED + "> Class Item <");
        ItemStack item5 = itC.MakeItem(ChatColor.YELLOW + "" + ChatColor.BOLD + "Ancient Clock", Material.WATCH, lore5);
        return item5;
    }

    public Inventory ChroInv(HumanEntity p) {
        Chronos r = new Chronos();
        ItemConstructor itC = new ItemConstructor();
        Inventory OccGUI = Bukkit.getServer().createInventory(null, 9 * 4,
                ChatColor.YELLOW + "" + ChatColor.BOLD + "Chronos Class");
        ///////////////////////////////////// Occultist class item //////////////////////////////////////////////////////
        fillInvWithGlass(OccGUI, 0);
        Player player = (Player) p;
        //death star spell
        ArrayList<String> lore1 = new ArrayList<String>();
        lore1.add("");
        lore1.add(ChatColor.RED + "Mana: " + ChatColor.WHITE + r.Mana1);
        lore1.add(ChatColor.RED + "Combo: " + ChatColor.WHITE + "Right-Right-Right");
        lore1.add(ChatColor.RED + "Description: " + ChatColor.WHITE + "Throw three clocks in");
        lore1.add(ChatColor.WHITE + "your direction, each one deals " + r.spell1DMG + sp.dmgAdder(player) + " DMG and  ");
        lore1.add(ChatColor.WHITE + "confusion for 10 seconds.");
        ItemStack potion = new ItemStack(Material.POTION);
        PotionMeta pm = (PotionMeta) potion.getItemMeta();
        pm.setBasePotionData(new PotionData(PotionType.WATER));
        potion.setItemMeta(pm);
        ItemStack item1 = itC.MakeItemByStack(ChatColor.WHITE + "" + ChatColor.BOLD + "First Spell : " + ChatColor.AQUA + "" + ChatColor.BOLD + r.spell1Name, potion, lore1);
        OccGUI.setItem(10, item1);

        // possession spell
        ArrayList<String> lore2 = new ArrayList<String>();
        lore2.add("");
        lore2.add(ChatColor.RED + "Mana: " + ChatColor.WHITE + r.Mana2);
        lore2.add(ChatColor.RED + "Combo: " + ChatColor.WHITE + "Right-Left-Right");
        lore2.add(ChatColor.RED + "Description: " + ChatColor.WHITE + "Freeze time in a ");
        lore2.add(ChatColor.WHITE + "small radius of your location, stopping all ");
        lore2.add(ChatColor.WHITE + "actions for " + (int) (r.spell2DMG / 20) + " seconds.");
        ItemStack Hpotion = new ItemStack(Material.POTION);
        PotionMeta pm1 = (PotionMeta) Hpotion.getItemMeta();
        pm1.setBasePotionData(new PotionData(PotionType.INSTANT_HEAL));
        Hpotion.setItemMeta(pm1);
        ItemStack item2 = itC.MakeItemByStack(ChatColor.WHITE + "" + ChatColor.BOLD + "Second Spell : " + ChatColor.AQUA + "" + ChatColor.BOLD + r.spell2Name, Hpotion, lore2);
        OccGUI.setItem(11, item2);

        // intimidation spell
        ArrayList<String> lore3 = new ArrayList<String>();
        lore3.add("");
        lore3.add(ChatColor.RED + "Mana: " + ChatColor.WHITE + r.Mana3);
        lore3.add(ChatColor.RED + "Combo: " + ChatColor.WHITE + "Right-Left-Left");
        lore3.add(ChatColor.RED + "Description: " + ChatColor.WHITE + "Travels back " + r.spell3DMG + " seconds");
        lore3.add(ChatColor.WHITE + "to your last location after marking it,");
        lore3.add(ChatColor.WHITE + "restoring previous health and mana.");
        ItemStack Lpotion = new ItemStack(Material.POTION);
        PotionMeta pm2 = (PotionMeta) Lpotion.getItemMeta();
        pm2.setBasePotionData(new PotionData(PotionType.JUMP));
        Lpotion.setItemMeta(pm2);
        ItemStack item3 = itC.MakeItemByStack(ChatColor.WHITE + "" + ChatColor.BOLD + "Third Spell : " + ChatColor.AQUA + "" + ChatColor.BOLD + r.spell3Name, Lpotion, lore3);
        OccGUI.setItem(12, item3);

        //Armageddon spell
        ArrayList<String> lore4 = new ArrayList<String>();
        lore4.add("");
        lore4.add(ChatColor.RED + "Mana: " + ChatColor.WHITE + "20");
        lore4.add(ChatColor.RED + "Combo: " + ChatColor.WHITE + "Right-Right-Left");
        lore4.add(ChatColor.RED + "Description: " + ChatColor.WHITE + "Speed up time in an ");
        lore4.add(ChatColor.WHITE + "area and decay it. Allies inside the area gain");
        lore4.add(ChatColor.WHITE + "resistance and damage boost, while enemies");
        lore4.add(ChatColor.WHITE + "gain weakness for 4 seconds. Decayed zone");
        lore4.add(ChatColor.WHITE + "will be restored in " + (int) (r.ultDMG / 20) + " seconds.");
        ItemStack Epotion = new ItemStack(Material.POTION);
        PotionMeta pm3 = (PotionMeta) Epotion.getItemMeta();
        pm3.setBasePotionData(new PotionData(PotionType.INVISIBILITY));
        Epotion.setItemMeta(pm3);
        ItemStack item4 = itC.MakeItemByStack(ChatColor.GOLD + "" + ChatColor.BOLD + "Ultimate : " + ChatColor.AQUA + "" + ChatColor.BOLD + r.ultName, Epotion, lore4);
        OccGUI.setItem(13, item4);
        //Rift expansion spell
        OccGUI.setItem(15, ChronosWeapon());
        completeGUI(OccGUI);
        //open inv
        return OccGUI;
    }

    @EventHandler
    public void classChoosingEvent(InventoryClickEvent e) {
        HumanEntity p = e.getWhoClicked();
        Player player = (Player) p;
        if (!e.getInventory().getTitle().contains("Chronos Class")) {
            return;
        }
        e.setCancelled(true);
        if (e.getCurrentItem() == null)
            return;
        if (!e.getCurrentItem().hasItemMeta())
            return;
        if (!e.getCurrentItem().getItemMeta().hasDisplayName())
            return;

        if (e.getCurrentItem().getItemMeta().getDisplayName().contains("Class Menu")) {
            player.playSound(player.getLocation(), Sound.BLOCK_NOTE_PLING, 10, 1);
            AaClassChooseGUI c = new AaClassChooseGUI();
            c.openClassGUI(player);
            return;
        }
        if (e.getCurrentItem().getItemMeta().getDisplayName().contains("CONFIRM")) {
            AaClassHashMapChoosing ch = new AaClassHashMapChoosing();
            ch.chooseClass(player, new Chronos());
            if (!p.getInventory().contains(ChronosWeapon())) {
                p.getInventory().clear();
                p.getInventory().addItem(ChronosWeapon());
            }
            return;
        }
    }

}
