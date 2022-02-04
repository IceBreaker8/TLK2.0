package ClassGUI;

import ClassUpgrades.SpellStringAdder;
import Classes.Riftor;
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

public class RiftorSpellInv extends AaClassSpellShowcase implements Listener {

    @Override
    public Inventory getSpellGUI(Player player) {
        Inventory newInv = riftorInv(player);
        fillInvWithGlass(newInv, 26);
        return skillSet(newInv, player);
    }

    SpellStringAdder sp = new SpellStringAdder();

    public void openClassInv(Player p) {
        p.openInventory(riftorInv(p));
    }

    public ItemStack RiftorWeapon() {
        ItemConstructor itC = new ItemConstructor();
        ArrayList<String> lore5 = new ArrayList<String>();
        lore5.add(ChatColor.GRAY + "A powerful tool capable of opening rifts");
        lore5.add(ChatColor.GRAY + "between worlds.");
        lore5.add("");
        lore5.add(ChatColor.DARK_RED + "> Class Item <");
        ItemStack item5 = itC.MakeItem(ChatColor.DARK_PURPLE + "" + ChatColor.BOLD + "RiftMaker", Material.FLINT_AND_STEEL, lore5);
        return item5;
    }

    public Inventory riftorInv(HumanEntity p) {
        Riftor r = new Riftor();
        ItemConstructor itC = new ItemConstructor();
        Inventory riftorGUI = Bukkit.getServer().createInventory(null, 9 * 4,
                ChatColor.DARK_PURPLE + "" + ChatColor.BOLD + "Riftor Class");
        ///////////////////////////////////// riftor class item //////////////////////////////////////////////////////
        fillInvWithGlass(riftorGUI, 0);
        Player player = (Player) p;
        //switch spell
        ArrayList<String> lore1 = new ArrayList<String>();
        lore1.add("");
        lore1.add(ChatColor.RED + "Mana: " + ChatColor.WHITE + r.Mana1);
        lore1.add(ChatColor.RED + "Combo: " + ChatColor.WHITE + "Right-Right-Right");
        lore1.add(ChatColor.RED + "Description: " + ChatColor.WHITE + "Open two rifts that makes");
        lore1.add(ChatColor.WHITE + "you switch locations between you and your ");
        lore1.add(ChatColor.WHITE + "enemy, dealing " + r.spell1DMG + sp.dmgAdder(player) + " DMG. Enemy switched will get");
        lore1.add(ChatColor.WHITE + "blindness for 2 seconds.");
        ItemStack potion = new ItemStack(Material.POTION);
        PotionMeta pm = (PotionMeta) potion.getItemMeta();
        pm.setBasePotionData(new PotionData(PotionType.WATER));
        potion.setItemMeta(pm);
        ItemStack item1 = itC.MakeItemByStack(ChatColor.WHITE + "" + ChatColor.BOLD + "First Spell : " + ChatColor.AQUA + "" + ChatColor.BOLD + r.spell1Name, potion, lore1);
        riftorGUI.setItem(10, item1);

        // Bidimentional projectiles spell
        ArrayList<String> lore2 = new ArrayList<String>();
        lore2.add("");
        lore2.add(ChatColor.RED + "Mana: " + ChatColor.WHITE + r.Mana2);
        lore2.add(ChatColor.RED + "Combo: " + ChatColor.WHITE + "Right-Left-Right");
        lore2.add(ChatColor.RED + "Description: " + ChatColor.WHITE + "Summons two portals behind");
        lore2.add(ChatColor.WHITE + "the targetted enemy and launch two projectiles");
        lore2.add(ChatColor.WHITE + "towards him dealing each " + r.spell2DMG + sp.dmgAdder(player) + " DMG on explosion.");
        ItemStack Hpotion = new ItemStack(Material.POTION);
        PotionMeta pm1 = (PotionMeta) Hpotion.getItemMeta();
        pm1.setBasePotionData(new PotionData(PotionType.INSTANT_HEAL));
        Hpotion.setItemMeta(pm1);
        ItemStack item2 = itC.MakeItemByStack(ChatColor.WHITE + "" + ChatColor.BOLD + "Second Spell : " + ChatColor.AQUA + "" + ChatColor.BOLD + r.spell2Name, Hpotion, lore2);
        riftorGUI.setItem(11, item2);

        // rift walk spell
        ArrayList<String> lore3 = new ArrayList<String>();
        lore3.add("");
        lore3.add(ChatColor.RED + "Mana: " + ChatColor.WHITE + r.Mana3);
        lore3.add(ChatColor.RED + "Combo: " + ChatColor.WHITE + "Right-Left-Left");
        lore3.add(ChatColor.RED + "Description: " + ChatColor.WHITE + "Summons two portals in");
        lore3.add(ChatColor.WHITE + "front of you allowing to jump between rifts,");
        lore3.add(ChatColor.WHITE + "by teleporting between the portals for " + (int) (r.spell3DMG / 20) + " seconds.");
        ItemStack Lpotion = new ItemStack(Material.POTION);
        PotionMeta pm2 = (PotionMeta) Lpotion.getItemMeta();
        pm2.setBasePotionData(new PotionData(PotionType.JUMP));
        Lpotion.setItemMeta(pm2);
        ItemStack item3 = itC.MakeItemByStack(ChatColor.WHITE + "" + ChatColor.BOLD + "Third Spell : " + ChatColor.AQUA + "" + ChatColor.BOLD + r.spell3Name, Lpotion, lore3);
        riftorGUI.setItem(12, item3);

        //Rift expansion spell
        ArrayList<String> lore4 = new ArrayList<String>();
        lore4.add("");
        lore4.add(ChatColor.RED + "Mana: " + ChatColor.WHITE + "20");
        lore4.add(ChatColor.RED + "Combo: " + ChatColor.WHITE + "Right-Right-Left");

        lore4.add(ChatColor.RED + "Description: " + ChatColor.WHITE + "Open an expanded rift ");
        lore4.add(ChatColor.WHITE + "above you that sucks up enemies, pulling");
        lore4.add(ChatColor.WHITE + "them towards it ");
        ItemStack Epotion = new ItemStack(Material.POTION);
        PotionMeta pm3 = (PotionMeta) Epotion.getItemMeta();
        pm3.setBasePotionData(new PotionData(PotionType.INVISIBILITY));
        Epotion.setItemMeta(pm3);
        ItemStack item4 = itC.MakeItemByStack(ChatColor.GOLD + "" + ChatColor.BOLD + "Ultimate : " + ChatColor.AQUA + "" + ChatColor.BOLD + r.ultName, Epotion, lore4);
        riftorGUI.setItem(13, item4);

        //Rift expansion spell
        riftorGUI.setItem(15, RiftorWeapon());
        completeGUI(riftorGUI);
        // open inv
        return riftorGUI;


    }

    @EventHandler
    public void classChoosingEvent(InventoryClickEvent e) {
        HumanEntity p = e.getWhoClicked();
        Player player = (Player) p;
        if (!e.getInventory().getTitle().contains("Riftor Class")) {
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
            ch.chooseClass(player, new Riftor());
            if (!p.getInventory().contains(RiftorWeapon())) {
                p.getInventory().clear();
                p.getInventory().addItem(RiftorWeapon());
            }
            return;
        }

    }


}
