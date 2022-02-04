package ClassGUI;

import Classes.*;
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

import java.util.ArrayList;

public class AaClassChooseGUI implements Listener {

    public void openClassGUI(Player p) {
        ItemConstructor itC = new ItemConstructor();
        Inventory classChoose = Bukkit.getServer().createInventory(null, 9 * 5,
                ChatColor.WHITE + "" + ChatColor.BOLD + "Class Menu");
        // riftor class item :
        ArrayList<String> lore1 = new ArrayList<String>();
        lore1.add("");
        lore1.add(ChatColor.RED + "Speciality: " + ChatColor.WHITE + "Damage / Support");
        lore1.add(ChatColor.RED + "Description: " + ChatColor.WHITE + "You are the master of the rift");
        lore1.add(ChatColor.WHITE + "support allies by controlling dimensions to teleport, ");
        lore1.add(ChatColor.WHITE + "confuse your enemies and dealing medium damage.");
        lore1.add("");
        lore1.add(ChatColor.GOLD + "> Click to showcase spells <");
        ItemStack riftor = itC.MakeItem(ChatColor.DARK_PURPLE + "" + ChatColor.BOLD + "Riftor : The Dimension Breacher", Material.FLINT_AND_STEEL, lore1);
        classChoose.setItem(0, riftor);
        // Occultist class item:
        ArrayList<String> lore2 = new ArrayList<String>();
        lore2.add("");
        lore2.add(ChatColor.RED + "Speciality: " + ChatColor.WHITE + "Damage / Crowd Control");
        lore2.add(ChatColor.RED + "Description: " + ChatColor.WHITE + "You are the eye of darkness,");
        lore2.add(ChatColor.WHITE + "you possess dark magic power that allows you to");
        lore2.add(ChatColor.WHITE + "manipulate , damage and scare enemies with your");
        lore2.add(ChatColor.WHITE + "HexTotem.");
        lore2.add("");
        lore2.add(ChatColor.GOLD + "> Click to showcase spells <");
        ItemStack Occultist = itC.MakeItem(ChatColor.DARK_RED + "" + ChatColor.BOLD + "Occultist : The Eye Of Darkness", Material.TOTEM, lore2);
        classChoose.setItem(1, Occultist);
        // Chronos class item:
        ArrayList<String> lore3 = new ArrayList<String>();
        lore3.add("");
        lore3.add(ChatColor.RED + "Speciality: " + ChatColor.WHITE + "Support / Defense");
        lore3.add(ChatColor.RED + "Description: " + ChatColor.WHITE + "You are the Master Of Time,");
        lore3.add(ChatColor.WHITE + "control time to manipulate your enemies and ");
        lore3.add(ChatColor.WHITE + "confuse them as well as damaging them using your");
        lore3.add(ChatColor.WHITE + "Ancient Clock");
        lore3.add("");
        lore3.add(ChatColor.GOLD + "> Click to showcase spells <");
        ItemStack Chronos = itC.MakeItem(ChatColor.YELLOW + "" + ChatColor.BOLD + "Chronos : The Master Of Time", Material.WATCH, lore3);
        classChoose.setItem(2, Chronos);

        // FrostBite class item:
        ArrayList<String> lore4 = new ArrayList<String>();
        lore4.add("");
        lore4.add(ChatColor.RED + "Speciality: " + ChatColor.WHITE + "Damage / Crowd Control");
        lore4.add(ChatColor.RED + "Description: " + ChatColor.WHITE + "You are the bender of ");
        lore4.add(ChatColor.WHITE + "ice, use your powerful spells to freeze and");
        lore4.add(ChatColor.WHITE + "damage your enemies on the battlefield.");
        lore4.add("");
        lore4.add(ChatColor.GOLD + "> Click to showcase spells <");
        ItemStack limedye = new ItemStack(Material.INK_SACK, 1, (byte) 12);
        ItemStack FrostBite = itC.MakeItemByStack(ChatColor.AQUA + "" + ChatColor.BOLD + "FrostBite : The Ice Bender", limedye, lore4);
        classChoose.setItem(3, FrostBite);

        // Gluon class item:
        ArrayList<String> lore5 = new ArrayList<String>();
        lore5.add("");
        lore5.add(ChatColor.RED + "Speciality: " + ChatColor.WHITE + "Tank / Crowd Control");
        lore5.add(ChatColor.RED + "Description: " + ChatColor.WHITE + "You are the gravity master, ");
        lore5.add(ChatColor.WHITE + "control gravity to manipulate your enemies");
        lore5.add(ChatColor.WHITE + "and damage them in the battlefield.");
        lore5.add("");
        lore5.add(ChatColor.GOLD + "> Click to showcase spells <");
        ItemStack Gluon = itC.MakeItem(ChatColor.WHITE + "" + ChatColor.BOLD + "Gluon : The Gravity Controler", Material.END_ROD, lore5);
        classChoose.setItem(4, Gluon);

        // Flora class item:
        ArrayList<String> lore6 = new ArrayList<String>();
        lore6.add("");
        lore6.add(ChatColor.RED + "Speciality: " + ChatColor.WHITE + "Healer / Support");
        lore6.add(ChatColor.RED + "Description: " + ChatColor.WHITE + "You are the goddess");
        lore6.add(ChatColor.WHITE + "of nature, heal and support your allies in the");
        lore6.add(ChatColor.WHITE + "battlefield, as well as damaging enemies.");
        lore6.add("");
        lore6.add(ChatColor.GOLD + "> Click to showcase spells <");
        ItemStack greendye = new ItemStack(Material.INK_SACK, 1, (byte) 10);
        ItemStack Flora = itC.MakeItemByStack(ChatColor.GREEN + "" + ChatColor.BOLD + "Flora : The Goddess Of Nature", greendye, lore6);
        classChoose.setItem(5, Flora);

        // alp class item:
        Alp alp = new Alp();
        ArrayList<String> lore7 = new ArrayList<String>();
        lore7.add("");
        lore7.add(ChatColor.RED + "Speciality: " + ChatColor.WHITE + "Damage / Crowd Control");
        lore7.add(ChatColor.RED + "Description: " + ChatColor.WHITE + "You are the mighty");
        lore7.add(ChatColor.WHITE + "warrior, use your fighting techniques");
        lore7.add(ChatColor.WHITE + "using your sharp sword to intimidate ");
        lore7.add(ChatColor.WHITE + "and overwhelm your enemies on the battlefield.");
        lore7.add("");
        lore7.add(ChatColor.GOLD + "> Click to showcase spells <");
        ItemStack alpItem = itC.MakeItem(alp.classColor + "" + ChatColor.BOLD + "Alp : The Sharp Edge", Material.IRON_SWORD, lore7);
        classChoose.setItem(6, alpItem);

        // itzal class item:
        Itzal itzal = new Itzal();
        ArrayList<String> lore8 = new ArrayList<String>();
        lore8.add("");
        lore8.add(ChatColor.RED + "Speciality: " + ChatColor.WHITE + "Damage / Healer");
        lore8.add(ChatColor.RED + "Description: " + ChatColor.WHITE + "You are the shadow,");
        lore8.add(ChatColor.WHITE + "use your shadow techniques to damage");
        lore8.add(ChatColor.WHITE + "your enemies with style, you have the");
        lore8.add(ChatColor.WHITE + "ability to camouflage yourself as well");
        lore8.add(ChatColor.WHITE + "well as healing your wounds.");
        lore8.add("");
        lore8.add(ChatColor.GOLD + "> Click to showcase spells <");
        ItemStack itzalItem = itC.MakeItem(itzal.classColor + "" + ChatColor.BOLD + "Itzal : The Hidden Shadow", Material.FIREWORK_CHARGE, lore8);
        classChoose.setItem(7, itzalItem);

        // verun class item:
        Verun verun = new Verun();
        ArrayList<String> lore9 = new ArrayList<String>();
        lore9.add("");
        lore9.add(ChatColor.RED + "Speciality: " + ChatColor.WHITE + "Damage / Tank");
        lore9.add(ChatColor.RED + "Description: " + ChatColor.WHITE + "You are the desert");
        lore9.add(ChatColor.WHITE + "scorpion, use your deadly venom to");
        lore9.add(ChatColor.WHITE + "infect and damage enemies in the");
        lore9.add(ChatColor.WHITE + "battlefield. You are able to transform");
        lore9.add(ChatColor.WHITE + "into different phases to fortity yourself.");
        lore9.add("");
        lore9.add(ChatColor.GOLD + "> Click to showcase spells <");
        ItemStack verunItem = itC.MakeItem(verun.classColor + "" + ChatColor.BOLD + "Verun : The Desert Scorpion", Material.SHEARS, lore9);
        classChoose.setItem(8, verunItem);

        // Guardian class item:
        Poseidon guardian = new Poseidon();
        ArrayList<String> lore10 = new ArrayList<String>();
        lore10.add("");
        lore10.add(ChatColor.RED + "Speciality: " + ChatColor.WHITE + "Tank / Healer");
        lore10.add(ChatColor.RED + "Description: " + ChatColor.WHITE + "You are the king");
        lore10.add(ChatColor.WHITE + "of the oceans, your spells allow you");
        lore10.add(ChatColor.WHITE + "to heal your allies and support them ");
        lore10.add(ChatColor.WHITE + "in the battlefield, damaging enemies with");
        lore10.add(ChatColor.WHITE + "your deadly waves.");
        lore10.add("");
        lore10.add(ChatColor.GOLD + "> Click to showcase spells <");
        ItemStack Guardian = itC.MakeItem(guardian.classColor + "" + ChatColor.BOLD + "Poseidon : The Protector Of Seas", Material.PRISMARINE_SHARD, lore10);
        classChoose.setItem(9, Guardian);

        // Soldier class item:
        Soldier Soldier = new Soldier();
        ArrayList<String> lore11 = new ArrayList<String>();
        lore11.add("");
        lore11.add(ChatColor.RED + "Speciality: " + ChatColor.WHITE + "Damage / Crowd Control");
        lore11.add(ChatColor.RED + "Description: " + ChatColor.WHITE + "A new sort of fighter,");
        lore11.add(ChatColor.WHITE + "hailing from a far-off land that has remained locked ");
        lore11.add(ChatColor.WHITE + "away from the world. Hailing from many battles previous,");
        lore11.add(ChatColor.WHITE + "he have immense knowledge of the ways of the war.");
        lore11.add("");
        lore11.add(ChatColor.GOLD + "> Click to showcase spells <");
        ItemStack sol = itC.MakeItem(Soldier.classColor + "" + ChatColor.BOLD + "Soldier : The Brave Fighter", Material.DIAMOND_AXE, lore11);
        classChoose.setItem(10, sol);


        p.openInventory(classChoose);
    }

    @EventHandler
    public void classChoosingEvent(InventoryClickEvent e) {
        HumanEntity p = e.getWhoClicked();
        if (!e.getInventory().getTitle().contains(ChatColor.WHITE + "" + ChatColor.BOLD + "Class Menu")) {
            return;
        }
        if (e.getCurrentItem() == null)
            return;
        if (!e.getCurrentItem().hasItemMeta())
            return;
        if (!e.getCurrentItem().getItemMeta().hasDisplayName())
            return;
        e.setCancelled(true);
        Player player = (Player) p;
        if (e.getCurrentItem().getItemMeta().getDisplayName().contains("Riftor")) {
            player.playSound(player.getLocation(), Sound.BLOCK_NOTE_PLING, 10, 1);
            RiftorSpellInv rif = new RiftorSpellInv();
            rif.openClassInv(player);
            return;
        }
        if (e.getCurrentItem().getItemMeta().getDisplayName().contains("Occultist")) {
            player.playSound(player.getLocation(), Sound.BLOCK_NOTE_PLING, 10, 1);
            OccultistSpellInv rif = new OccultistSpellInv();
            rif.openClassInv(player);
            return;
        }
        if (e.getCurrentItem().getItemMeta().getDisplayName().contains("Chronos")) {
            player.playSound(player.getLocation(), Sound.BLOCK_NOTE_PLING, 10, 1);
            ChronosSpellInv rif = new ChronosSpellInv();
            rif.openClassInv(player);
            return;
        }
        if (e.getCurrentItem().getItemMeta().getDisplayName().contains("FrostBite")) {
            player.playSound(player.getLocation(), Sound.BLOCK_NOTE_PLING, 10, 1);
            FrostBiteSpellInv rif = new FrostBiteSpellInv();
            rif.openClassInv(player);
            return;
        }
        if (e.getCurrentItem().getItemMeta().getDisplayName().contains("Gluon")) {
            player.playSound(player.getLocation(), Sound.BLOCK_NOTE_PLING, 10, 1);
            GluonSpellInv rif = new GluonSpellInv();
            rif.openClassInv(player);
            return;
        }
        if (e.getCurrentItem().getItemMeta().getDisplayName().contains("Flora")) {
            player.playSound(player.getLocation(), Sound.BLOCK_NOTE_PLING, 10, 1);
            FloraSpellInv rif = new FloraSpellInv();
            rif.openClassInv(player);
            return;
        }
        if (e.getCurrentItem().getItemMeta().getDisplayName().contains("Alp")) {
            player.playSound(player.getLocation(), Sound.BLOCK_NOTE_PLING, 10, 1);
            AlpSpellInv rif = new AlpSpellInv();
            rif.openClassInv(player);
            return;
        }
        if (e.getCurrentItem().getItemMeta().getDisplayName().contains("Itzal")) {
            player.playSound(player.getLocation(), Sound.BLOCK_NOTE_PLING, 10, 1);
            ItzalSpellInv rif = new ItzalSpellInv();
            rif.openClassInv(player);
            return;
        }
        if (e.getCurrentItem().getItemMeta().getDisplayName().contains("Verun")) {
            player.playSound(player.getLocation(), Sound.BLOCK_NOTE_PLING, 10, 1);
            VerunSpellInv rif = new VerunSpellInv();
            rif.openClassInv(player);
            return;
        }
        if (e.getCurrentItem().getItemMeta().getDisplayName().contains("Poseidon")) {
            player.playSound(player.getLocation(), Sound.BLOCK_NOTE_PLING, 10, 1);
            PoseidonSpellInv rif = new PoseidonSpellInv();
            rif.openClassInv(player);
            return;
        }
        if (e.getCurrentItem().getItemMeta().getDisplayName().contains("Soldier")) {
            player.playSound(player.getLocation(), Sound.BLOCK_NOTE_PLING, 10, 1);
            SoldierSpellInv rif = new SoldierSpellInv();
            rif.openClassInv(player);
            return;
        }


    }
}
