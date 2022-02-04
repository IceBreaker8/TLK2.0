package ClassGUI;

import ClassUpgrades.SpellStringAdder;
import Classes.FrostBite;
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

public class FrostBiteSpellInv extends AaClassSpellShowcase implements Listener {
    @Override
    public Inventory getSpellGUI(Player player) {
        Inventory newInv = FrostBiteInv(player);
        fillInvWithGlass(newInv, 26);
        return skillSet(newInv, player);
    }

    SpellStringAdder sp = new SpellStringAdder();

    public void openClassInv(Player p) {
        p.openInventory(FrostBiteInv(p));
    }

    public ItemStack FrostBiteWeapon() {
        ItemConstructor itC = new ItemConstructor();
        ArrayList<String> lore5 = new ArrayList<String>();
        lore5.add(ChatColor.GRAY + "This forgotten ice fragment can");
        lore5.add(ChatColor.GRAY + "turn anyone to a frozen piece ");
        lore5.add(ChatColor.GRAY + "of ice on touch.");
        lore5.add("");
        lore5.add(ChatColor.DARK_RED + "> Class Item <");
        ItemStack limedye = new ItemStack(Material.INK_SACK, 1, (byte) 12);
        ItemStack item5 = itC.MakeItemByStack(ChatColor.AQUA + "" + ChatColor.BOLD + "Ice Fragment", limedye, lore5);
        return item5;
    }

    public Inventory FrostBiteInv(HumanEntity p) {
        FrostBite r = new FrostBite();
        ItemConstructor itC = new ItemConstructor();
        Inventory OccGUI = Bukkit.getServer().createInventory(null, 9 * 4,
                ChatColor.AQUA + "" + ChatColor.BOLD + "FrostBite Class");
        ///////////////////////////////////// Occultist class item //////////////////////////////////////////////////////
        fillInvWithGlass(OccGUI, 0);
        Player player = (Player) p;
        //death star spell
        ArrayList<String> lore1 = new ArrayList<String>();
        lore1.add("");
        lore1.add(ChatColor.RED + "Mana: " + ChatColor.WHITE + r.Mana1);
        lore1.add(ChatColor.RED + "Combo: " + ChatColor.WHITE + "Right-Right-Right");
        lore1.add(ChatColor.RED + "Description: " + ChatColor.WHITE + "Shoot a snow particle");
        lore1.add(ChatColor.WHITE + "at an enemy causing him to freeze for");
        lore1.add(ChatColor.WHITE + "one seconds, dealing " + r.spell1DMG + sp.dmgAdder(player) + " DMG.");
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
        lore2.add(ChatColor.RED + "Description: " + ChatColor.WHITE + "Summon an ice trail that ");
        lore2.add(ChatColor.WHITE + "follow the targetted enemy, spawning an ice spike on hit");
        lore2.add(ChatColor.WHITE + "dealing " + r.spell2DMG + sp.dmgAdder(player) + " DMG to the enemy, freezing");
        lore2.add(ChatColor.WHITE + "him for 2 seconds.");
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
        lore3.add(ChatColor.RED + "Description: " + ChatColor.WHITE + "Instantly transform into");
        lore3.add(ChatColor.WHITE + "a polar bear for " + (int) (r.spell3DMG / 20) + " seconds, if you hit an");
        lore3.add(ChatColor.WHITE + "enemy, you will deal 4 bonus DMG added to the");
        lore3.add(ChatColor.WHITE + "damage of the initial hit, knocking back the ");
        lore3.add(ChatColor.WHITE + "enemy.");
        lore3.add(ChatColor.WHITE + "You are invulnerable while tranformed and you");
        lore3.add(ChatColor.WHITE + "will gain back your normal form after your first hit.");
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
        lore4.add(ChatColor.RED + "Description: " + ChatColor.WHITE + "Summon a blizzard storm at your");
        lore4.add(ChatColor.WHITE + "location for " + (int) (r.ultDMG / 20) + " seconds that freezes");
        lore4.add(ChatColor.WHITE + "all enemies in the area.");
        ItemStack Epotion = new ItemStack(Material.POTION);
        PotionMeta pm3 = (PotionMeta) Epotion.getItemMeta();
        pm3.setBasePotionData(new PotionData(PotionType.INVISIBILITY));
        Epotion.setItemMeta(pm3);
        ItemStack item4 = itC.MakeItemByStack(ChatColor.GOLD + "" + ChatColor.BOLD + "Ultimate : " + ChatColor.AQUA + "" + ChatColor.BOLD + r.ultName, Epotion, lore4);
        OccGUI.setItem(13, item4);

        //Rift expansion spell
        OccGUI.setItem(15, FrostBiteWeapon());

        completeGUI(OccGUI);

        //open inv
        return OccGUI;


    }

    @EventHandler
    public void classChoosingEvent(InventoryClickEvent e) {
        HumanEntity p = e.getWhoClicked();
        Player player = (Player) p;
        if (!e.getInventory().getTitle().contains("FrostBite Class")) {
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
            ch.chooseClass(player, new FrostBite());
            if (!p.getInventory().contains(FrostBiteWeapon())) {
                p.getInventory().clear();
                p.getInventory().addItem(FrostBiteWeapon());
            }
            return;
        }

    }
}
