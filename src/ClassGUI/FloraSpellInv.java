package ClassGUI;

import ClassUpgrades.SpellStringAdder;
import Classes.Flora;
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

public class FloraSpellInv extends AaClassSpellShowcase implements Listener {

    @Override
    public Inventory getSpellGUI(Player p) {
        Inventory newInv = FloraInv(p);
        fillInvWithGlass(newInv, 26);
        return skillSet(newInv, p);
    }

    SpellStringAdder sp = new SpellStringAdder();

    public void openClassInv(Player p) {
        p.openInventory(FloraInv(p));
    }

    public ItemStack FloraWeapon() {
        ItemConstructor itC = new ItemConstructor();
        ArrayList<String> lore5 = new ArrayList<String>();
        lore5.add(ChatColor.GRAY + "This essence of the holy tree was");
        lore5.add(ChatColor.GRAY + "found by angels before the major");
        lore5.add(ChatColor.GRAY + "war.");
        lore5.add("");
        lore5.add(ChatColor.DARK_RED + "> Class Item <");
        ItemStack greendye = new ItemStack(Material.INK_SACK, 1, (byte) 10);
        ItemStack Flora = itC.MakeItemByStack(ChatColor.GREEN + "" + ChatColor.BOLD + "Tree Essence", greendye, lore5);
        return Flora;
    }

    public Inventory FloraInv(HumanEntity p) {
        Flora r = new Flora();
        ItemConstructor itC = new ItemConstructor();
        Inventory OccGUI = Bukkit.getServer().createInventory(null, 9 * 4,
                ChatColor.GREEN + "" + ChatColor.BOLD + "Flora Class");
        ///////////////////////////////////// Occultist class item //////////////////////////////////////////////////////
        fillInvWithGlass(OccGUI, 0);
        Player player = (Player) p;
        //death star spell
        ArrayList<String> lore1 = new ArrayList<String>();
        lore1.add("");
        lore1.add(ChatColor.RED + "Mana: " + ChatColor.WHITE + r.Mana1);
        lore1.add(ChatColor.RED + "Combo: " + ChatColor.WHITE + "Right-Right-Right");
        lore1.add(ChatColor.RED + "Description: " + ChatColor.WHITE + "Root an enemy for 1 seconds");
        lore1.add(ChatColor.WHITE + "and summons and green star upon him,  ");
        lore1.add(ChatColor.WHITE + "causing an explosion to all nearby enemies ");
        lore1.add(ChatColor.WHITE + "for " + r.spell1DMG + sp.dmgAdder(player) + " DMG.");
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
        lore2.add(ChatColor.RED + "Description: " + ChatColor.WHITE + "Transform into a parrot,");
        lore2.add(ChatColor.WHITE + "you are able to fly for 4 seconds, healing yourself");
        lore2.add(ChatColor.WHITE + "and all nearby allies for " + r.spell2DMG + " hp each second.");
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
        lore3.add(ChatColor.RED + "Description: " + ChatColor.WHITE + "Send your targetted ally");
        lore3.add(ChatColor.WHITE + "a life essence, healing him for " + r.spell3DMG + " hp.");
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
        lore4.add(ChatColor.RED + "Description: " + ChatColor.WHITE + "Call the mighty forest souls");
        lore4.add(ChatColor.WHITE + "to revive all nearby allies in a radius of");
        lore4.add(ChatColor.WHITE + "" + r.ultDMG + " blocks, giving them absorption for 2 seconds.");
        lore4.add(ChatColor.WHITE + "");
        ItemStack Epotion = new ItemStack(Material.POTION);
        PotionMeta pm3 = (PotionMeta) Epotion.getItemMeta();
        pm3.setBasePotionData(new PotionData(PotionType.INVISIBILITY));
        Epotion.setItemMeta(pm3);
        ItemStack item4 = itC.MakeItemByStack(ChatColor.GOLD + "" + ChatColor.BOLD + "Ultimate : " + ChatColor.AQUA + "" + ChatColor.BOLD + r.ultName, Epotion, lore4);
        OccGUI.setItem(13, item4);

        OccGUI.setItem(15, FloraWeapon());
        completeGUI(OccGUI);

        //open inv
        return OccGUI;


    }

    @EventHandler
    public void classChoosingEvent(InventoryClickEvent e) {
        HumanEntity p = e.getWhoClicked();
        Player player = (Player) p;
        if (!e.getInventory().getTitle().contains("Flora Class")) {
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
            ch.chooseClass(player, new Flora());
            if (!p.getInventory().contains(FloraWeapon())) {
                p.getInventory().clear();
                p.getInventory().addItem(FloraWeapon());
            }
            return;
        }

    }
}
